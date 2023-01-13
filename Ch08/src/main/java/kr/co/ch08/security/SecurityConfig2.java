package kr.co.ch08.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@EnableWebSecurity
public class SecurityConfig2 {

	@Autowired
	private SecurityUserService service;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll(); // 모든 자원에 대해서 모든 사용자 접근 허용
		http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN"); // admin 하위 모든 링크에 대해서 ADMIN에게만 접근 허용 
		http.authorizeHttpRequests().antMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER"); // member 하위 모든 링크에 대해서 ADMIN, MEMBER에게만 접근 허용
		http.authorizeHttpRequests().antMatchers("/user2/loginSuccess").hasAnyRole("3", "4", "5"); // /user2/loginSuccess는 ADMIN만 접근 허용
		
		// 자동 로그인 설정
		http.rememberMe()
			.key("autoUser")
			.rememberMeParameter("autoUid")
			.tokenValiditySeconds(600)
			.userDetailsService(service);
			
		// 사이트 위변조 요청 방지
		http.csrf().disable();
		
		// 로그인 설정
		http.formLogin()
		.loginPage("/user2/login")
		.defaultSuccessUrl("/user2/loginSuccess")
		.failureUrl("/user2/login?success=100)")
		.usernameParameter("uid")
		.passwordParameter("pass");
		
		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/user2/logout"))
		.logoutSuccessUrl("/user2/login?success=200");
		
		return http.build();
	}
	
	@Bean
    public PasswordEncoder  PasswordEncoder () {
        return new BCryptPasswordEncoder();
    }
	
}