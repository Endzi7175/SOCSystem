package com.sbnz.SIEMCenter2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(
			AuthenticationManagerBuilder authenticationManagerBuilder)
			throws Exception {
		
		authenticationManagerBuilder
				.userDetailsService(this.userDetailsService).passwordEncoder(
						passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean()
			throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter
				.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}
	
	 @Override
	    protected void configure(final HttpSecurity http) throws Exception {
	        // @formatter:off
	        http
	            .csrf().disable()
	            .authorizeRequests()
	                .antMatchers("/login*","/login*", "/logout*", "/signin/**", "/signup/**", "/customLogin",
	                        "/user/registration*", "/registrationConfirm*", "/expiredAccount*", "/registration*",
	                        "/badUser*", "/user/resendRegistrationToken*" ,"/forgetPassword*", "/user/resetPassword*",
	                        "/user/changePassword*", "/emailError*", "/resources/**","/old/user/registration*","/successRegister*","/qrcode*").permitAll()
	                .antMatchers("/user/updatePassword*","/user/savePassword*","/updatePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
	                .anyRequest().hasAuthority("READ_PRIVILEGE");

	    // @formatter:on
	    }	

}
