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
public class FindPwdDTO {

  private static final FindPwdDTO sample =
      FindPwdDTO.builder().userId("testId").userEmail("testEmail").build();

  @NotNull
  private String userId;

  @NotNull
  private String userEmail;


  public static FindPwdDTO sample() {
    return sample;
  }
}
