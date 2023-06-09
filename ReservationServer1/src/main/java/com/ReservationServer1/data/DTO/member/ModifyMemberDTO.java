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
public class ModifyMemberDTO {

  private static final ModifyMemberDTO sample = ModifyMemberDTO.builder().userPwd("testPwd")
      .userName("testName").userAddress("testAddress").userEmail("testEmail").build();

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


  public static ModifyMemberDTO sample() {
    return sample;
  }
}
