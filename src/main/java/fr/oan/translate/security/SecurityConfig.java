package fr.oan.translate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import fr.oan.translate.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	UnauthorizedEntryPoint unauthorizedEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.userDetailsService(new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().addFilterAfter(new AuthenticationTokenProcessingFilter(userService),
				SecurityContextPersistenceFilter.class)

				.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and().authorizeRequests()
				.antMatchers("/").permitAll().antMatchers("/favicon.ico").permitAll()
				// J'autorise les POST - api/secure uniquement si authentifié
				.antMatchers(HttpMethod.POST, "/api/secure/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/api/secure/**").authenticated()
				.antMatchers(HttpMethod.DELETE, "/api/secure/**").authenticated()
				.antMatchers(HttpMethod.GET, "/api/secure/**").authenticated()
				// // J'autorise les GET aux anonymes
				.antMatchers("/api/**").permitAll().and().exceptionHandling().and().anonymous().and().servletApi().and()
				.headers().cacheControl();
	}

	// protected void configure(HttpSecurity http) throws Exception {
	// http.exceptionHandling()
	// .and()
	// .anonymous()
	// .and()
	// .servletApi()
	// .and()
	// .headers()
	// .cacheControl()
	// .and()
	// .authorizeRequests()
	//
	// // allow anonymous resource requests
	// .antMatchers("/")
	// .permitAll()
	// .antMatchers("/favicon.ico")
	// .permitAll()
	//
	// // J'autorise les POST - api/secure uniquement si authentifié
	// .antMatchers(HttpMethod.POST, "/api/secure/**")
	// .authenticated()
	// .antMatchers(HttpMethod.PUT, "/api/secure/**")
	// .authenticated()
	// .antMatchers(HttpMethod.DELETE, "/api/secure/**")
	// .authenticated()
	// .antMatchers(HttpMethod.GET, "/api/secure/**")
	// .authenticated()
	//
	// // J'autorise les GET aux anonymes
	// .antMatchers("/api/**")
	// .permitAll()
	//
	// .and()
	// .addFilterAfter(
	// new AuthenticationTokenProcessingFilter(userService),
	// SecurityContextPersistenceFilter.class)
	//
	// .exceptionHandling()
	// .authenticationEntryPoint(unauthorizedEntryPoint)
	//
	// .and().csrf().disable();
	//
	// }
}
