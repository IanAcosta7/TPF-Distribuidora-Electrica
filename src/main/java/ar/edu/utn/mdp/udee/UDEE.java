package ar.edu.utn.mdp.udee;

import ar.edu.utn.mdp.udee.controller.ElectricMeterController;
import ar.edu.utn.mdp.udee.controller.MeasurementController;
import ar.edu.utn.mdp.udee.controller.UserController;
import ar.edu.utn.mdp.udee.util.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class UDEE {

	public static void main(String[] args) {
		SpringApplication.run(UDEE.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			httpSecurity.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, UserController.PATH + UserController.LOGIN_PATH).permitAll()
					.antMatchers(HttpMethod.POST, MeasurementController.PATH).permitAll()
					.anyRequest().hasRole("EMPLOYEE");
		}
	}

}
