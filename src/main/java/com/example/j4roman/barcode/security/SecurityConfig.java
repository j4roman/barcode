package com.example.j4roman.barcode.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private XAuthCodeAuthenticationProvider xAuthCodeAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new XAuthCodeAuthenticationProcessingFilter("/**"), AnonymousAuthenticationFilter.class);
        http.authenticationProvider(xAuthCodeAuthenticationProvider);
        http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/manage/**").hasAuthority(XAuthCodeRoles.ADMIN.toString())
                .and()
                .authorizeRequests()
                .antMatchers("/**").hasAnyAuthority(XAuthCodeRoles.ADMIN.toString(), XAuthCodeRoles.USER.toString());
        http.exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler);
    }
}
