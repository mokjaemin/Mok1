package com.ReservationServer1.data.Entity.member;



import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.Entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Member")
@EqualsAndHashCode(callSuper=false)
// @EntityListeners(CustomListener.class)
public class MemberEntity extends BaseEntity {


  @Id
  private String userId;
  private String userPwd;
  private String userName;
  private String userNumber;
  private String userAddress;
  private String userEmail;


  public MemberEntity(MemberDTO member) {
    BeanUtils.copyProperties(member, this);
  }

  public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
    return MemberDTO.builder().userId(memberEntity.userId).userPwd(memberEntity.userPwd)
        .userName(memberEntity.userName).userNumber(memberEntity.userNumber)
        .userAddress(memberEntity.userAddress).userEmail(memberEntity.userEmail).build();
  }
}
