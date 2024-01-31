package dev.kush.security2.config;

import dev.kush.security2.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;

    public SecurityConfig(PasswordEncoder passwordEncoder, CustomerService customerService) {
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers("/secured")
                        .authenticated()
                        .anyRequest()
                        .permitAll()
        )
                .authenticationProvider(daoAuthenticationProvider())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults());


        return http.build();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(customerService);
        return daoAuthenticationProvider;
    }



}
