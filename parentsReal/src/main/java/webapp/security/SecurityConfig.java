package webapp.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final String USER_NAME_QUERY="select email,pwd,active    from login      where email=?";
	private final String USER_ROLE_QUERY="select infos.email,infos.role "+"from login infos "+" where infos.email = ?";

	private PasswordEncoder myPasswordEncoderValue=null;
	@Autowired
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public PasswordEncoder myPasswordEncoder(){
		myPasswordEncoderValue =new BCryptPasswordEncoder();
		return myPasswordEncoderValue;
	}

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
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.authorizeRequests()
				.antMatchers("/event/book").hasAuthority("PARENT")
				.antMatchers("/organiser/ticket").hasAnyAuthority("PARENT","ADMIN")
				.antMatchers("/user/ticket").hasAnyAuthority("PARENT","ADMIN")
				.antMatchers("/organiser/**").hasAuthority("ORGANISER")
				.antMatchers("/user/**").hasAuthority("PARENT")
				.antMatchers("/admin/**").hasAuthority("ADMIN")
				.antMatchers("/file/certificate").hasAuthority("ADMIN")
				.antMatchers("/profile**").hasAnyAuthority( "PARENT", "ORGANISER", "ADMIN" )
				.antMatchers("**").permitAll()
			.and()
				.formLogin().loginPage("/login").permitAll()
			.and()
				.logout().logoutSuccessUrl("/login?logout").permitAll();
			
	}

}
