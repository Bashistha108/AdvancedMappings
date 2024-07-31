//package com.ende.FinalAdvancedMappings.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//
//import javax.sql.DataSource;
//
//@Configuration
//public class SecurityConfig {
//    private DataSource dataSource;
//
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource){
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT p.email, pd.password, p.enabled FROM professors p " +
//                                                       "JOIN professor_details pd ON p.professor_details_id = pd.id WHERE p.email=?");
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT p.email, pd.role AS authority FROM professors p JOIN professor_details pd ON p.professor_details_id = pd.id WHERE p.email=?");
//        return jdbcUserDetailsManager;
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests(configurer->configurer
//                        .requestMatchers("/", "/professor-professor-manage", "/professor-home").hasAnyRole("PHYSICS", "MATH", "TEST","DEAN","CHEMISTRY", "STUDENT","VERTRETER","CS")
//                        .anyRequest()
//                        .authenticated())
//
//                .formLogin(form->form
//                        .loginPage("/login-page")
//                        .loginProcessingUrl("/authenticateTheUser")
//                        .permitAll()
//                )
//                .logout(logout->logout.permitAll()
//                )
//                .exceptionHandling(
//                        configurer->configurer
//                                .accessDeniedPage("/access-denied")
//                );
//
//        return http.build();
//
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
