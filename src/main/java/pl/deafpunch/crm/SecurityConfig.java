package pl.deafpunch.crm;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pl.deafpunch.crm.service.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource	dataSource;
	
	/**
	 * TEMPORARY SOLUTION
	 */
	
	@Override
	public	void	configure(AuthenticationManagerBuilder	auth)	throws	Exception	{
		
					auth.inMemoryAuthentication()
									.withUser("user").password("user").roles("USER")
									.and()
									.withUser("admin").password("admin").roles("ADMIN")
									.and()
									.withUser("moderator").password("moderator").roles("MODERATOR");
	}
	
	@Override
	protected	void	configure(HttpSecurity	http)	throws	Exception	{
					http.authorizeRequests()
									.antMatchers("/login").permitAll()
									.antMatchers("/gentelella/**").permitAll()
									.antMatchers("/**").authenticated()
									.antMatchers("/admin/**").hasRole("ADMIN")
									.anyRequest()
									.permitAll()
									.and().formLogin()
					                .loginPage("/login")
									.defaultSuccessUrl("/index")
					                .permitAll()
					                .and()
					                .logout()
					                .permitAll()
					                .logoutSuccessUrl("/login")
					                .and()
					                .exceptionHandling().accessDeniedPage("/403");
	}
	
	@Bean
	public	BCryptPasswordEncoder	passwordEncoder()	{
					return	new	BCryptPasswordEncoder();
	}
	
	@Bean
	public	SpringDataUserDetailsService	customUserDetailsService()	{
					return	new	SpringDataUserDetailsService();
	}								
}