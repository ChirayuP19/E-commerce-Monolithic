package com.ecommerce.project.security;

import com.ecommerce.project.securityjwt.AuthEntryPointJwt;
import com.ecommerce.project.securityjwt.AuthTokenFilter;
import com.ecommerce.project.securityjwt.oauthHandler.CustomOAuth2SuccessHandler;
import com.ecommerce.project.securityjwt.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig  {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    @Lazy
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Bean
    public AuthTokenFilter authTokenJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception->exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorized->
                        authorized.requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
//                                .requestMatchers("/api/v1/public/**").permitAll()
                                .requestMatchers("/api/v1/admin/**").permitAll()
                                .requestMatchers("/api/v1/test/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .requestMatchers("/api/v1/auth/oauth2/success").permitAll()
                                .requestMatchers("/oauth2/authorization/**").permitAll()
                        .anyRequest().authenticated())
                        .oauth2Login(oauth2->oauth2.successHandler(customOAuth2SuccessHandler));

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authTokenJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers(
                heards->heards.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring().requestMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
                ));
    }

}
