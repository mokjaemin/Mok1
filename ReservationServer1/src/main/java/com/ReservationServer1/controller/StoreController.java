package com.ReservationServer1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ReservationServer1.data.DTO.MemberDTO;
import com.ReservationServer1.data.DTO.StoreDTO;
import com.ReservationServer1.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController("StoreController")
@RequestMapping("/store")
public class StoreController {

  private final Logger logger = LoggerFactory.getLogger(StoreController.class);
  private final StoreService storeService;
  public StoreController(StoreService storeService) {
    this.storeService = storeService;
  }
  
  
  @PostMapping
  @Operation(summary = "가게 등록 요청", description = "가게 정보가 등록됩니다.", tags = {"Store Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = MemberDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> register(@Valid @RequestBody StoreDTO storeDTO, Authentication authentication) {
    logger.info("[StoreController] register(가게 등록) 호출");
    storeDTO.setOwnerId(authentication.getName());
    String result = storeService.registerStore(storeDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
