package kr.co.ch08.security;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyUserDetails implements UserDetails{

	/**
	 *  버전 ID
	 */
	private static final long serialVersionUID = 1L;

	private String uid;
	private String pass;
	private String name;
	private String hp;
	private int age;
	private LocalDateTime rdate;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 사용자 권한 목록(계정이 갖는 권한 목록)
		return null;
	}

	@Override
	public String getPassword() {
		// 계정이 갖는 비밀번호
		return pass;
	}

	@Override
	public String getUsername() {
		// 계정이 갖는 ID
		return uid;
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정 만료 여부(true: 만료x, false: 만료 -- false일 경우 로그인 x)
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠김 여부(true: 잠김x, false: 잠김 -- false일 경우 로그인 x)
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호 만료 여부(true: 만료x, false: 만료)
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성화(true: 활성화, false: 비활성화)
		return true;
	}
}
