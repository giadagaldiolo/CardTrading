package ch.supsi.web.cardgames.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/" ).permitAll()
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/card/new").authenticated()
                        .requestMatchers("/wishlist/**").authenticated()
                        .requestMatchers("/cart/**").authenticated()
                        .requestMatchers("/card/*/edit").hasRole("ADMIN")
                        .requestMatchers("/card/*/delete").hasRole("ADMIN")
                        .requestMatchers("/card/**").permitAll()
                        .requestMatchers("/cards/**").permitAll()
                        .requestMatchers("/news").permitAll()
                        .requestMatchers("/CSS/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/fonts/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/scripts/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    PasswordEncoder BCPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}