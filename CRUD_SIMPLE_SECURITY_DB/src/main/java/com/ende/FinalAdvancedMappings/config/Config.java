
package com.ende.FinalAdvancedMappings.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@EnableWebSecurity
@Service
public class Config implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

   
    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

 

    @Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    // Try to find the user in the professors table first
    CustomUserDetails userDetails = loadUserFromProfessors(email);
    if (userDetails != null) {
        return userDetails;
    }

    // If not found, try to find the user in the students table
    userDetails = loadUserFromStudents(email);
    if (userDetails != null) {
        return userDetails;
    }

    throw new UsernameNotFoundException("Email not found: " + email);
}

@SuppressWarnings("deprecation")
private CustomUserDetails loadUserFromProfessors(String email) {
    try {
        return jdbcTemplate.queryForObject(
                "SELECT p.id, p.email, pd.password, p.enabled FROM professors p " +
                        "JOIN professor_details pd ON p.professor_details_id = pd.id WHERE p.email=?",
                new Object[]{email},
                (rs, rowNum) -> {
                    int id = rs.getInt("id");
                    String userEmail = rs.getString("email");
                    String password = rs.getString("password");
                    boolean enabled = rs.getBoolean("enabled");
                    List<GrantedAuthority> authorities = jdbcTemplate.query(
                            "SELECT pd.role AS authority FROM professors p " +
                                    "JOIN professor_details pd ON p.professor_details_id = pd.id WHERE p.email=?",
                            new Object[]{userEmail},
                            (authRs, authRowNum) -> new SimpleGrantedAuthority(authRs.getString("authority"))
                    );
                    return new CustomUserDetails(id, userEmail, password, authorities);
                }
        );
    } catch (EmptyResultDataAccessException e) {
        return null;
    }
}

@SuppressWarnings("deprecation")
private CustomUserDetails loadUserFromStudents(String email) {
    try {
        return jdbcTemplate.queryForObject(
                "SELECT s.id, s.email, sd.password, s.enabled FROM students s " +
                        "JOIN student_details sd ON s.student_details_id = sd.id WHERE s.email=?",
                new Object[]{email},
                (rs, rowNum) -> {
                    int id = rs.getInt("id");
                    String userEmail = rs.getString("email");
                    String password = rs.getString("password");
                    boolean enabled = rs.getBoolean("enabled");
                    List<GrantedAuthority> authorities = jdbcTemplate.query(
                            "SELECT sd.role AS authority FROM students s " +
                                    "JOIN student_details sd ON s.student_details_id = sd.id WHERE s.email=?",
                            new Object[]{userEmail},
                            (authRs, authRowNum) -> new SimpleGrantedAuthority(authRs.getString("authority"))
                    );
                    return new CustomUserDetails(id, userEmail, password, authorities);
                }
        );
    } catch (EmptyResultDataAccessException e) {
        return null;
    }
}


    // Bean for SecurityFilterChain with custom success handler
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/", "/professor-professor-manage", "/professor-home", "/student-home").hasAnyRole("PHYSICS", "MATH", "TEST", "DEAN", "CHEMISTRY", "STUDENT", "VERTRETER", "CS")
                        .anyRequest()
                        .authenticated())
                .formLogin(form -> form
                        .loginPage("/login-page")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successHandler(customAuthenticationSuccessHandler) // Use custom success handler
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(configurer -> configurer
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }

}
