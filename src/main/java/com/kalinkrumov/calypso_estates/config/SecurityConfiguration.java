package com.kalinkrumov.calypso_estates.config;

import com.kalinkrumov.calypso_estates.model.entity.User;
import com.kalinkrumov.calypso_estates.model.entity.UserRole;
import com.kalinkrumov.calypso_estates.model.enums.UserRoleEnum;
import com.kalinkrumov.calypso_estates.repository.UserRepository;
import com.kalinkrumov.calypso_estates.service.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    //Here we have to expose 3 things
    //1. PasswordEncoder
    //2. SecurityFilterChain
    //3. UserDetailService

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/lib/**", "/img/**", "/files/**").permitAll()
                .antMatchers("/about", "/properties", "/contacts").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/users/login", "/users/register").anonymous()
//                .antMatchers("/pages/moderators").hasRole(UserRoleEnum.MODERATOR.name())
                .antMatchers("/pages/admins").hasRole(UserRoleEnum.MODERATOR.name())
                .antMatchers("/amenities/edit/**").hasRole(UserRoleEnum.MODERATOR.name())
                .antMatchers("/pages/all","/pages/edit/**", "/pages/delete/**").hasRole(UserRoleEnum.MODERATOR.name())
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/users/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/").failureForwardUrl("/users/login-error")
                .and()
                .logout().logoutUrl("/users/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID");

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new AppUserDetailsService(userRepository);
    }

}
