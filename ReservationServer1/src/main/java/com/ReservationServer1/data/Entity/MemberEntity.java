package com.ReservationServer1.data.Entity;

import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.Listener.CustomListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Member")
//@EntityListeners(CustomListener.class)
public class MemberEntity extends BaseEntity{
	
	
	@Id // 식별자, key 정의
	private String userId;
	private String userPwd;
	private String userName;
	private String userNumber;
	private String userAddress;
	private String userEmail;
	
	
	// DTO를 Entity로 변환
	public MemberEntity(MemberDTO member) {
		// DTO의 프로퍼티들을 Entity의 프로퍼티들로 매핑
		BeanUtils.copyProperties(member, this);
	}
	
	// Entity를 다시 DTO로 변환 
	public MemberDTO toDomain() {
		MemberDTO member = new MemberDTO(this.userId, this.userPwd, this.userName, this.userNumber, this.userAddress, this.userEmail);
		return member;
	}
}
