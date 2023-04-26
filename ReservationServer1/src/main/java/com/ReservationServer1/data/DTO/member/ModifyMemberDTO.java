package com.ReservationServer1.data.DTO.member;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ModifyMemberDTO {
  
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
  
  
  public ModifyMemberDTO(String userPwd, String userName, String userNumber, String userAddress, String userEmail) {
      this.userPwd = userPwd;
      this.userName = userName;
      this.userNumber = userNumber;
      this.userAddress = userAddress;
      this.userEmail = userEmail;
  } 
  
  public static ModifyMemberDTO sample() {
      
      String userPwd = "testPwd";
      String userName = "testName";
      String userNumber = "testNumber";
      String userAddress = "testAddress";
      String userEmail = "testEmail";
      return new ModifyMemberDTO(userPwd, userName, userNumber, userAddress, userEmail);
  }
}
