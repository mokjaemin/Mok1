package com.ReservationServer1.data.DTO.member;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MemberDTO implements Comparable<MemberDTO> {

  private static final MemberDTO sample = MemberDTO.builder().userId("id").userPwd("pwd")
      .userName("name").userNumber("number").userAddress("address").userEmail("email").build();

  @NotNull
  private String userId;
  @NotNull
  private String userPwd;
  @NotNull
  private String userName;
  @NotNull
  private String userNumber;
  @NotNull
  private String userAddress;
  @NotNull
  private String userEmail;

  public MemberDTO(MemberDTO other) {
    this.userId = other.userId;
    this.userPwd = other.userPwd;
    this.userName = other.userName;
    this.userNumber = other.userNumber;
    this.userAddress = other.userAddress;
    this.userEmail = other.userEmail;
  }

  public static MemberDTO sample() {
    return sample;
  }

  @Override
  public int compareTo(MemberDTO other) {
    int check1 = this.userId.compareTo(other.userId);
    if (check1 < 0) {
      return -1;
    } 
    else if (check1 > 0) {
      return 1;
    } 
    else {
      int check2 = this.userPwd.compareTo(other.userPwd);
      if(check2 < 0) {
        return -1;
      }
      else {
        return 1;
      }
    }
  }


}
