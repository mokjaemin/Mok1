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
public class MemberDTO {

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


  public static MemberDTO sample() {
    return sample;
  }


}
