package com.ReservationServer1.data.DTO.member;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
  @Size(min=3, max=20)
  private String userId;

  @NotNull
  @Email
  private String userEmail;


  public static FindPwdDTO sample() {
    return sample;
  }
}
