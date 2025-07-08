package solidcitadel.tp.admin.api.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import solidcitadel.tp.admin.api.security.UserDetailsServiceImpl;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    private final RequestMatcher loginPathMatcher;
    private final RequestMatcher registerPathMatcher;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        boolean loginMatch = loginPathMatcher.matches(request);
        boolean registerMatch = registerPathMatcher.matches(request);
        log.debug("shouldNotFilter: loginMatch={}, registerMatch={}, method={}, uri={}",
                  loginMatch, registerMatch, request.getMethod(), request.getRequestURI());
        return loginMatch || registerMatch;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtUtil.getAccessTokenFromCookie(request);
        log.debug("Filter Started for URI: {}", request.getRequestURI());
        log.debug("Token from cookie: {}", token != null ? "exists" : "null");

        if(StringUtils.hasText(token)) {

            String tokenValue = jwtUtil.substringToken(token);
            log.info("JWT token: {}", tokenValue);

            if (!jwtUtil.validateToken(tokenValue)) {
                log.error("Token Error");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
            log.debug("User info from token: {}", info.getSubject());

            try {
                setAuthentication(info.getSubject());
                log.debug("Authentication set successfully for user: {}", info.getSubject());
            } catch (Exception e){
                log.error("인증 오류: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }
        else {
            log.debug("JWT token is empty for URI: {}", request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
