package com.ex.ticket.user.domain.entity;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ex.ticket.common.domain.BaseTimeEntity;
import com.ex.ticket.common.vo.UserInfoVo;
import com.ex.ticket.common.vo.UserProfileVo;
import com.ex.ticket.user.domain.dto.request.SignUpRequest;
import com.ex.ticket.user.exception.ForbiddenUserException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_user")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	private String name;

	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private AccountRole accountRole = AccountRole.USER;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private AccountState accountState = AccountState.NORMAL;

	@Builder.Default
	private LocalDateTime lastLoginAt = LocalDateTime.now();

	public User(SignUpRequest request, PasswordEncoder passwordEncoder) {
		this.email = request.getEmail();
		this.password = passwordEncoder.encode(request.getPassword());
		this.name = request.getName();
		this.accountRole = AccountRole.USER;
		this.accountState = AccountState.NORMAL;
	}

	public static User from(String email, String name, String password) {
		return User.builder()
			.email(email)
			.password(password)
			.name(name)
			.accountRole(AccountRole.USER)
			.accountState(AccountState.NORMAL)
			.build();
	}

	public void login() {
		if (!AccountState.NORMAL.equals(this.accountState)) {
			throw ForbiddenUserException.EXCEPTION;
		}
		lastLoginAt = LocalDateTime.now();
	}

	// public void update(UserUpdateRequest newMember, PasswordEncoder passwordEncoder) {
	// 	this.password = newMember.newPassword() == null || newMember.newPassword().isBlank()
	// 		? this.password : passwordEncoder.encode(newMember.password());
	// 	this.name = newMember.name();
	// 	this.email = newMember.email();
	// 	this.phoneNumber = newMember.phoneNumber();
	// }

	public UserProfileVo toUserProfileVo() {
		return UserProfileVo.from(this);
	}


	public UserInfoVo toUserInfoVo() {
		return UserInfoVo.from(this);
	}

	// public EmailUserInfo toEmailUserInfo() {
	// 	return new EmailUserInfo(this.name, this.email);
	// }

	public Boolean isDeletedUser() {
		return accountState == AccountState.DELETED;
	}


	public void updateAccountRole(AccountRole type){
		this.accountRole = type;
	}

	// @PostPersist
	// public void registerEvent() {
	// 	UserRegisterEvent userRegisterEvent = UserRegisterEvent.builder().userId(id).build();
	// }

}
