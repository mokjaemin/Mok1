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
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.service.StoreRestDayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController("StoreRestDayController")
@RequestMapping("/day")
public class StoreRestDayController {
  
  private final Logger logger = LoggerFactory.getLogger(StoreRestDayController.class);
  private final StoreRestDayService ownerService;
  public StoreRestDayController(StoreRestDayService ownerService) {
    this.ownerService = ownerService;
  }
  
  @PostMapping()
  @Operation(summary = "가게 쉬는날 등록 요청", description = "가게 쉬는날 정보가 등록됩니다.", tags = {"Owner Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = RestDayDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> dayRegister(@Valid @RequestBody RestDayDTO restDayDTO, Authentication authentication) {
    logger.info("[StoreRestDayController] day register(쉬는날 등록) 호출");
    String result = ownerService.dayRegister(restDayDTO, authentication.getName());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
  
  
}
