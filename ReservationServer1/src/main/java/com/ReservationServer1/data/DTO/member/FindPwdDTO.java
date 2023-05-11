package com.ReservationServer1.data.DTO.member;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class FindPwdDTO {
  
  @NotNull
  private String userId;
  
  @NotNull
  private String userEmail;
  
  
  public static FindPwdDTO sample() {
    String userId = "testId";
    String userEmail = "testEmail";
    return new FindPwdDTO(userId, userEmail);
}
}
