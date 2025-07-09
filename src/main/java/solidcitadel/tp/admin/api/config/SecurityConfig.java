package solidcitadel.tp.admin.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import solidcitadel.tp.admin.api.jwt.JwtAuthenticationFilter;
import solidcitadel.tp.admin.api.jwt.JwtUtil;
import solidcitadel.tp.admin.api.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        String[] writeList = {"/api/login", "/api/signup", "/api/reissue"};
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService, writeList);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf(AbstractHttpConfigurer::disable);

        // 기본 Session 방식 비활성화
        http.sessionManagement((sessionManagement)
                -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // resources 와 특정 api 는 허용
        http.authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/api/login", "/api/signup", "/api/reissue").permitAll()
                        .requestMatchers("/api/test").permitAll()
                        .anyRequest().authenticated()
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
