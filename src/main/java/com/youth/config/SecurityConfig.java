package com.youth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.youth.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	MemberService memberService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage("/members/login")
			.defaultSuccessUrl("/")
			.usernameParameter("email")
			.failureUrl("/members/login/error")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
			.logoutSuccessUrl("/");
		
		
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
		
		http.authorizeRequests()
		.antMatchers("/", "/members/**", "/freeboard/list", "/refboard/list").permitAll()
		.antMatchers("/admin/**").access("hasRole('ADMIN')")
		.antMatchers("/freeboard/write","/freeboard/write/action")
			.access("hasRole('ADMIN') or hasRole('USER')")
		.antMatchers("/refboard/write", "/refboard/write/action")
			.access("hasRole('ADMIN')")
		
		
			
		
			
		.anyRequest().permitAll();
		
		http.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		
		http.csrf().ignoringAntMatchers("/freeboard/write/action");
		http.csrf().ignoringAntMatchers("/faq/write/action");
		http.csrf().ignoringAntMatchers("/notice/write/action");
		http.csrf().ignoringAntMatchers("/faq/delete");
		http.csrf().ignoringAntMatchers("/reffile/delete.ajax");
	
		
		
	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService)
		.passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/png/**");
	}
}
