package org.wecancodeit.pantryplus2electricboogaloo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http //
				.authorizeRequests() //
				.antMatchers("/", "/css/**", "/images/**", "/js/**", "/oauth2/authorization/google", "/about-us")
				.permitAll() //
				.anyRequest().authenticated() //
				.and() //
				.oauth2Login().loginPage("/oauth2/authorization/google");
	}

}
