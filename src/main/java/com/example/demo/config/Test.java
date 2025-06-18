// package com.example.demo.config;

// import com.example.demo.repository.UserRepository;
// import com.example.demo.entity.User;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// import java.util.Collections;

// @Configuration
// @EnableWebSecurity
// public class Test {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
//                 .anyRequest().authenticated()
//             )
//             .formLogin(Customizer.withDefaults())
//             .logout(logout -> logout
//                 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                 .logoutSuccessUrl("/login?logout")
//                 .permitAll()
//             );

//         return http.build();
//     }

//     @Bean
//     public UserDetailsService userDetailsService(UserRepository userRepository) {
//         return username -> {
//             User user = userRepository.findByUsername(username);
//             if (user == null) {
//                 throw new UsernameNotFoundException("User not found");
//             }

//             return new org.springframework.security.core.userdetails.User(
//                 user.getUsername(),
//                 user.getPassword(),
//                 Collections.singletonList(new SimpleGrantedAuthority("USER"))
//             );
//         };
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }
