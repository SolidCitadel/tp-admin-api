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
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import solidcitadel.tp.admin.api.security.UserDetailsServiceImpl;

import java.io.IOException;

@Slf4j(topic = "JWT_FILTER")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final String[] whiteList;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        PatternMatchUtils.simpleMatch(whiteList, requestURI);
        log.debug("shouldNotFilter: method={}, uri={}", request.getMethod(), request.getRequestURI());
        return PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtUtil.getAccessTokenFromCookie(request);
        log.debug("Filter Started for URI: {}", request.getRequestURI());
        log.debug("Token from cookie: {}", token != null ? "exists" : "null");

        if(StringUtils.hasText(token)) {
            log.info("JWT token: {}", token);

            if (!jwtUtil.validateToken(token)) {
                log.error("Token Error");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(token);
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
