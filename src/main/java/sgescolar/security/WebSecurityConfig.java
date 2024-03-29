package sgescolar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import sgescolar.security.jwt.JwtTokenUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
	private final String[] PUBLIC = {
		"/*", "/swagger-ui/**", "/v3/api-docs/**",  "/img/**", "/lib/**", "/font/**",  "/sistema/**", "/componentes/**", "/css/**", "/fonts/**", 
		"/h2-console/**", 
		"/api/login/entrar", "/api/planejamento/anexo/**", "/api/relatorio/**"
	};
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
			.addFilterBefore( new JwtRequestFilter( jwtTokenUtil ), UsernamePasswordAuthenticationFilter.class )
			.authorizeRequests()
				.antMatchers( PUBLIC ).permitAll() 								
				.anyRequest().authenticated();
		
		http.headers().frameOptions().disable();
	}
	
}
