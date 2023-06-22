package com.ReservationServer1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ReservationServer1.data.DTO.ReservationOrder.ReservationDTO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.service.StoreReservationOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController("StoreReservationOrderController")
@RequestMapping("/ro")
public class StoreReservationOrderController {

  private final StoreReservationOrderService storeReservationOrderService;
  
  public StoreReservationOrderController(StoreReservationOrderService storeReservationOrderService){
    this.storeReservationOrderService = storeReservationOrderService;
  }
  
  @PostMapping("/reservation")
  @Operation(summary = "가게 예약 등록 요청", description = "가게 예약 정보가 등록됩니다.",tags = {"Store Reservation Order Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = StoreRestDayDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> registerDayOff(@Valid @RequestBody ReservationDTO reservationDTO, Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK).body(storeReservationOrderService.registerReservation(reservationDTO, authentication.getName()));
  }
}
