package com.ReservationServer1.data.Entity.member;



import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.Entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
public class MemberEntity extends BaseEntity {

  private static final MemberEntity sample =
      MemberEntity.builder().userId("userId").userPwd("userPwd").userName("userName")
          .userNumber("userNumber").userAddress("userAddress").userEmail("userEmail").build();

  @Id
  @Column(length = 20)
  @NotNull
  private String userId;
  
  @NotNull
  private String userPwd;
  
  @Column(length = 7)
  @NotNull
  private String userName;
  
  @Column(columnDefinition = "CHAR(12)")
  @NotNull
  private String userNumber;
  
  @NotNull
  private String userAddress;
  
  @NotNull
  private String userEmail;


  // MemberDTO -> MemberEntity
  public MemberEntity(MemberDTO member) {
    BeanUtils.copyProperties(member, this);
  }

  // MemberEntity -> MemberDTO
  public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
    return MemberDTO.builder().userId(memberEntity.userId).userPwd(memberEntity.userPwd)
        .userName(memberEntity.userName).userNumber(memberEntity.userNumber)
        .userAddress(memberEntity.userAddress).userEmail(memberEntity.userEmail).build();
  }

  // Get Sample
  public static MemberEntity sample() {
    return sample;
  }
  
  
}
