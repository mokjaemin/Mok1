package com.ReservationServer1.DAO.JPAImpl.Entity;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import com.ReservationServer1.data.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Member") // 테이블 생성시 이 이름으로 설정
public class MemberEntity {
	
	
	@Id // 식별자, key 정의
	private String userId;
	private String userPwd;
	private String userName;
	private String userNumber;
	private String userAddress;
	private String userEmail;
	private LocalDate foundationTime;
	
	
	// DTO를 Entity로 변환
	public MemberEntity(Member member) {
		// DTO의 프로퍼티들을 Entity의 프로퍼티들로 매핑
		BeanUtils.copyProperties(member, this);
	}
	
	// Entity를 다시 DTO로 변환 
	public Member toDomain() {
		Member member = new Member(this.userId, this.userPwd, this.userName, this.userNumber, this.userAddress, this.userEmail);
		return member;
	}
}
