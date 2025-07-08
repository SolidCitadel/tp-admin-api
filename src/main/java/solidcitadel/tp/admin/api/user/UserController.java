package solidcitadel.tp.admin.api.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import solidcitadel.tp.admin.api.jwt.JwtUtil;
import solidcitadel.tp.admin.api.security.UserDetailsImpl;
import solidcitadel.tp.admin.api.user.dto.LoginRequestDto;
import solidcitadel.tp.admin.api.user.dto.SignupRequestDto;
import solidcitadel.tp.admin.api.user.dto.UserResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        try{
            userService.login(requestDto, res);
            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        UserResponseDto responseDto = new UserResponseDto(user.getId(), user.getUsername(), user.getRole());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        userService.logout(response);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외 처리
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        // 서비스 로직
        try{
            userService.signup(requestDto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reissue")
    public ResponseEntity<String> reissueToken(HttpServletRequest req, HttpServletResponse res){
        try {
            userService.reissueToken(req, res);
            return ResponseEntity.ok("Access Token reissued");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
