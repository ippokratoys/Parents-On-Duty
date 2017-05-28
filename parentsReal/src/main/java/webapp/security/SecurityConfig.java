package webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
				.withUser("mama").password("1234").roles("PARENT")
			.and()
				.withUser("paido").password("3210").roles("ORGANIZER");
		
		//.jdbcAuthentication();//Must continue this
	}
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.authorizeRequests()
				.antMatchers("/event/book").hasRole("PARENT")
			.and()
				.formLogin()
			.and()
				.logout();
			
	}
}
