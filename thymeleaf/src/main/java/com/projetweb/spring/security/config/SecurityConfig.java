package com.projetweb.spring.security.config;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.projetweb.spring.security.controller.LoggingAccessDeniedHandler;
import com.projetweb.spring.security.dao.UserDao;
import com.projetweb.spring.security.model.User;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;
    
	@Autowired
	private UserDao userDao;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(
                            "/",
                            "/js/**",
                            "/css/**",
                            "/img/**",
                            "/webjars/**",
                            "/productList",
                            "/inscription",
                            "/cart",
                            "/addToCart/**",
                            "/removeToCart/**",
                            "/h2-console/**").permitAll()
                    .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/admin/**, /user/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);
        
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	System.out.println("----------------CONFIGURATION---------------------");
    	auth.userDetailsService(inMemoryUserDetailsManager());
    	List<User> users = userDao.findAll();
    	
    	for(User user : users) {
    		auth.inMemoryAuthentication()
    		.withUser(user.getEmail()).password(user.getPassword()).roles(user.getRole())
    		.and();
    	}
        
    }
    
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        final Properties users = new Properties();
        users.put("user","pass,ROLE_USER,enabled");
        return new InMemoryUserDetailsManager(users);
    }

}