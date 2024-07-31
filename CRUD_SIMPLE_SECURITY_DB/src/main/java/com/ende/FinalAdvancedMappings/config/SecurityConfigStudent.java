//package com.ende.FinalAdvancedMappings.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class SecurityConfigStudent {
//
//    private DataSource dataSource;
//
//    @Bean
//    public UserDetailsManager userDetailsManagerStudent(DataSource dataSource){
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT s.email, sd.password FROM students s " +
//                "JOIN student_details sd ON s.student_details_id = sd.id WHERE s.email=?");
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT s.email, sd.role AS authority FROM students s JOIN student_details sd ON s.students_details_id = sd.id WHERE s.email=?");
//        return jdbcUserDetailsManager;
//    }
//    @Bean
//    public SecurityFilterChain filterChainStudent(HttpSecurity http) throws Exception{
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
