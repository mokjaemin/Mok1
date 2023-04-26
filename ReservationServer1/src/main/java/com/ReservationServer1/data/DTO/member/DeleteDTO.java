package com.ReservationServer1.data.DTO.member;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class DeleteDTO {

  @NotNull
  private String userPwd;

  public DeleteDTO(String userPwd) {
    this.userPwd = userPwd;
  }

  public static DeleteDTO sample() {
    String userPwd = "testPwd";
    return new DeleteDTO(userPwd);
  }

}
