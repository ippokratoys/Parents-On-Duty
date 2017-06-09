package webapp.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final String USER_NAME_QUERY="select email,pwd,1    from login      where email=?";
	private final String USER_ROLE_QUERY="select infos.email,infos.role "+"from login infos "+" where infos.email = ?";

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//		auth.inMemoryAuthentication()
//				.withUser("mama").password("1234").roles("PARENT")
//			.and()
//				.withUser("paido").password("3210").roles("ORGANIZER")
//			.and()
//				.withUser("mpampas").password("42069").roles("PARENT");
		
		auth.jdbcAuthentication()
			.usersByUsernameQuery(this.USER_NAME_QUERY)
			.authoritiesByUsernameQuery(this.USER_ROLE_QUERY)
			.dataSource(dataSource);
//			.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.authorizeRequests()
				.antMatchers("/event/book").hasAuthority("PARENT")
				.antMatchers("/user/**").hasAuthority("PARENT")
				.antMatchers("user/organiser*/*").hasAuthority("ORGANISER")
				.antMatchers("user/customer*/*").hasAuthority("PARENT")
				.antMatchers("**").permitAll()
			.and()
				.formLogin().loginPage("/login").permitAll()
			.and()
				.logout().logoutSuccessUrl("/login?logout").permitAll();
			
	}
}
