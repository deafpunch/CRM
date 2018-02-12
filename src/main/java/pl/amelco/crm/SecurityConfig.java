package pl.amelco.crm;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pl.amelco.crm.service.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource	dataSource;
	
	
	@Override
	protected	void	configure(HttpSecurity	http)	throws	Exception	{
					http.authorizeRequests()
//									.antMatchers("/all").permitAll()
//									.antMatchers("/product").authenticated()
//									.antMatchers("/restricted").hasRole("ADMIN")
//									.antMatchers("/moderator/**").hasAnyRole("MODERATOR")
									.and().formLogin()
					                .loginPage("/login")
									.defaultSuccessUrl("/dashboard")
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