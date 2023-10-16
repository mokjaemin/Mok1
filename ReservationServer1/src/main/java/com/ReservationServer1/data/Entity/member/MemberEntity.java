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
@EqualsAndHashCode(callSuper = false)
@Table(name = "Member")
// , indexes = {@Index(name = "idx_email", columnList = "userEmail")}
// , indexes = {@Index(name = "idx_city", columnList = "city"),
// @Index(name = "idx_dong", columnList = "dong")}
// @EntityListeners(CustomListener.class)
public class MemberEntity extends BaseEntity {

  private static final MemberEntity sample =
      MemberEntity.builder().userId("userId").userPwd("userPwd").userName("userName")
          .userNumber("userNumber").userAddress("userAddress").userEmail("userEmail").build();

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

  public static MemberEntity sample() {
    return sample;
  }
}
