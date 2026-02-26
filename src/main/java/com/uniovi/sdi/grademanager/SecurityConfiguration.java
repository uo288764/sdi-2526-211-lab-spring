package com.uniovi.sdi.grademanager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/script/**", "/", "/signup", "/login/**").permitAll()

                        .requestMatchers("/mark/add").hasAuthority("ROLE_PROFESSOR")
                        .requestMatchers("/mark/edit/*").hasAuthority("ROLE_PROFESSOR")
                        .requestMatchers("/mark/delete/*").hasAuthority("ROLE_PROFESSOR")
                        .requestMatchers("/mark/list/update").hasAnyAuthority("ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN")
                        .requestMatchers("/mark/*/resend").hasAuthority("ROLE_STUDENT")
                        .requestMatchers("/mark/*/noresend").hasAuthority("ROLE_STUDENT")
                        .requestMatchers("/mark/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN")

                        .requestMatchers("/home", "/home/update").authenticated()

                        .requestMatchers("/user/**").hasAuthority("ROLE_ADMIN")

                        .requestMatchers("/department/add").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/department/edit/*").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/department/delete/*").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/department/details/*").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN")
                        .requestMatchers("/departments").authenticated()

                        .requestMatchers("/professor/add").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/professor/edit/*").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/department/delete/*").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/department/details/*").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN")
                        .requestMatchers("/department").authenticated()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/home", true)
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .securityContext(securityContext -> securityContext
                        .requireExplicitSave(true)
                );
        return http.build();
    }
}