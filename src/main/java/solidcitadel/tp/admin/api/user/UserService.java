package solidcitadel.tp.admin.api.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import solidcitadel.tp.admin.api.jwt.JwtUtil;
import solidcitadel.tp.admin.api.user.dto.LoginRequestDto;
import solidcitadel.tp.admin.api.user.dto.SignupRequestDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${admin.token}")
    private String adminToken;

    @Transactional
    public void login(LoginRequestDto request, HttpServletResponse res) {
        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("User not found")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        // Access Token
        String accessToken = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
        jwtUtil.addAccessTokenToCookie(accessToken, res);

        // Refresh Token
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername());
        user.updateRefreshToken(refreshToken);
        jwtUtil.addRefreshTokenToCookie(refreshToken, res);
    }

    @Transactional
    public void signup(SignupRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 이름 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }

        // ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (!requestDto.getAdminToken().isBlank()){
            if (!adminToken.equals(requestDto.getAdminToken())){
                throw new IllegalArgumentException("Admin token does not match");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
        userRepository.save(user);
    }

    public void logout(HttpServletResponse res) {
        jwtUtil.deleteAccessTokenFromCookie(res);
    }

    @Transactional
    public void reissueToken(HttpServletRequest req, HttpServletResponse res) {
        String refreshTokenFromCookie = jwtUtil.getRefreshTokenFromCookie(req);
        if(!StringUtils.hasText(refreshTokenFromCookie)){
            throw new IllegalArgumentException("Refresh token not found");
        }

        if(!jwtUtil.validateToken(refreshTokenFromCookie)){
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String username = jwtUtil.getUserInfoFromToken(refreshTokenFromCookie).getSubject();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found"));

        // DB에 저작된 Refresh Token 과 일치하는지 확인
        if(!user.getRefreshToken().equals(refreshTokenFromCookie)){
            throw new IllegalArgumentException("Refresh token does not match");
        }

        // 새로운 Access Token 발급
        String newAccessToken = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
        jwtUtil.addAccessTokenToCookie(newAccessToken, res);
    }
}
