package kr.co.farmstory.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfigration {

	@Autowired
	private SecurityUserService service;

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll();
		http.authorizeHttpRequests().antMatchers("/list").hasAnyRole("2", "3", "4", "5");
		http.authorizeHttpRequests().antMatchers("/write", "/view", "/modify").hasAnyRole("3", "4", "5");

		// 사이트 위변조 요청 방지
		http.csrf().disable();

		// 자동 로그인 설정 [rememberMe] - 스프링에서 제공하는 자동로그인 기능
		http.rememberMe()
			.rememberMeParameter("remember-me") // default: remember-me, checkbox 등의 이름과 맞춰야함
			.tokenValiditySeconds(3600) // 쿠키의 만료시간 설정(초), default: 14일
			.alwaysRemember(false) // 사용자가 체크박스를 활성화하지 않아도 항상 실행, default: false
			.userDetailsService(service); // 기능을 사용할 때 사용자 정보가 필요함. 반드시 이 설정 필요함.

		// 로그인 설정
		http.formLogin()
			.loginPage("/user/login")
			.defaultSuccessUrl("/")
			.failureUrl("/user/login?success=100")
			.usernameParameter("uid")
			.passwordParameter("pass");
		
		// 로그아웃 설정
		http.logout()
			.invalidateHttpSession(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
			.logoutSuccessUrl("/user/login?success=200")
			.deleteCookies("remember-me", "JSESSIONID"); //자동 로그인 쿠키, Tomcat이 발급한 세션 유지 쿠키 삭제

		// 사용자 인증 처리 컴포넌트 서비스 등록
		http.userDetailsService(service);
		
		return http.build();
	}
	
	@Bean
    public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
    }
}