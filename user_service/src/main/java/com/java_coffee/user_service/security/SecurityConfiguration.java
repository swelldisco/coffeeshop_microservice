package com.java_coffee.user_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.java_coffee.user_service.exceptions.AuthEntrypoint;
import com.java_coffee.user_service.user.UserServiceImpl;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private UserServiceImpl userService;

    // @Autowired
    // private AuthenticationFilter authFilter;

    // @Autowired
    // private AuthEntrypoint authEntrypoint;

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     return http
    //         .cors(Customizer.withDefaults())
    //         .csrf(c -> c.disable())
    //         .authorizeHttpRequests(
    //             auth -> auth
    //                 .requestMatchers(HttpMethod.POST, "/login").permitAll()
    //                 .requestMatchers(HttpMethod.GET, "api_v1/users/loadTestData").permitAll()
    //                 .anyRequest()
    //                 .authenticated())
    //         
    //         .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
    //         .exceptionHandling((ex) -> ex.authenticationEntryPoint(authEntrypoint))
    //         .build();

    //     // return http
    //     //     .cors(c->c.disable())
    //     //     .csrf(c->c.disable())
    //     //     .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().permitAll())
    //     // .build();
            
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(c->c.disable())
            .cors(c->c.disable())
            .authorizeHttpRequests((auth) -> auth.anyRequest().permitAll()).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean 
    public Argon2PasswordEncoder argon2PasswordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }


    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(argon2PasswordEncoder());
    }
    
}
