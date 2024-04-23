package com.example.erp.config;

import com.example.erp.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    // 注入密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //注入身份验证管理器
    //上面分别注入了UserDetailsService和PasswordEncoder，这两个Bean是Spring Security进行身份验证所必须的
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 配置跨域、CSRF、认证、授权等
    // 这是Spring Security的跨域配置规则，优先级更高，两者同时配置时以security的配置为准
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8080"); // 允许该域名的跨域请求
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // 允许GET、POST、PUT、DELETE请求
        configuration.addAllowedHeader("*"); // 允许所有请求头
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 为所有路径的请求添加以上的跨域配置
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource());
                }) // 启用CORS并继续后续配置
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //禁用session
                .csrf(csrf -> csrf.disable()) // CSRF通常通过在请求中包含一个CSRF令牌来防止跨站请求伪造(cookie)。在Spring Security中，默认启用了CSRF保护。这里
                //通过 .csrf().disable() 禁用了 CSRF 保护。在很多前后端分离的应用中，因为使用了如 JWT 的 token 机制来处理身份验证，所以可以禁用 CSRF。
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/user/login").permitAll() // 允许对登录API的访问,无论登录没登录都能访问
//                        .requestMatchers("/test/**").anonymous() //允许匿名访问该路径，不需要认证，只有未登录的用户才能访问，登录的用户不能访问
//                        .requestMatchers("/test/**").hasAuthority("admin") // 使用默认的权限表达式，只有admin角色的用户才能访问
                        .anyRequest().authenticated() // 其他请求都需要认证，即登录后才能访问
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // 禁用 HTTP Basic 认证
                .formLogin(formLogin -> formLogin.disable()) // 禁用表单登录
                // 配置过滤器
                // 在UsernamePasswordAuthenticationFilter之前添加JwtAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置异常处理
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint) // 认证异常处理
                        .accessDeniedHandler(accessDeniedHandler) // 授权异常处理
                );

        return http.build();
    }

}
