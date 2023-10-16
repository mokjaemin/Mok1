package com.ReservationServer1.data.DTO.member;

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
public class SearchMemberDTO {
  
  
  private String userId;
  
  private String userName;
  
  private String userNumber;
  
  private String userAddress;
  
  private String userEmail;
  
  

}
