package com.ReservationServer1.data.DTO.store;

import org.springframework.web.multipart.MultipartFile;
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
public class StoreFoodsInfoDTO {

  @NotNull
  private String storeName;
  
  @NotNull
  private String foodName;
  
  @NotNull
  private String foodDescription;
  
  private MultipartFile foodImage;
}
