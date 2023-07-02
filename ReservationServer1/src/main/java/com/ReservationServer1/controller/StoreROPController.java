package com.ReservationServer1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ReservationServer1.data.DTO.ROP.OrderDTO;
import com.ReservationServer1.data.DTO.ROP.PayDTO;
import com.ReservationServer1.data.DTO.ROP.ReservationDTO;
import com.ReservationServer1.data.Entity.ROP.StoreReservationEntity;
import com.ReservationServer1.service.StoreROPService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController("StoreReservationOrderController")
@RequestMapping("/rop")
public class StoreROPController {

  private final StoreROPService storeROPService;

  public StoreROPController(StoreROPService storeROPService) {
    this.storeROPService = storeROPService;
  }

  @PostMapping("/reservation")
  @Operation(summary = "가게 예약 등록 요청", description = "가게 예약 정보가 등록됩니다.",
      tags = {"Store Reservation Order Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = ReservationDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> registerReservation(
      @Valid @RequestBody ReservationDTO reservationDTO, Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK).body(
        storeROPService.registerReservation(reservationDTO, authentication.getName()));
  }

  @PutMapping("/reservation")
  @Operation(summary = "가게 예약 수정 요청", description = "가게 예약 정보가 수정됩니다.",
      tags = {"Store Reservation Order Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = ReservationDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> updateReservation(@Valid @RequestBody ReservationDTO reservationDTO,
      Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK).body(
        storeROPService.updateReservation(reservationDTO, authentication.getName()));
  }


  @GetMapping("/reservation")
  @Operation(summary = "가게 예약 정보 출력 요청", description = "가게 예약 정보가 출력됩니다.",
      tags = {"Store Reservation Order Controller"})
  @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<StoreReservationEntity> getReservation(
      @Valid @RequestParam String storeName, Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(storeROPService.getReservation(storeName, authentication.getName()));
  }


  @DeleteMapping("/reservation")
  @Operation(summary = "가게 예약 삭제 요청", description = "가게 예약 정보가 삭제됩니다.",
      tags = {"Store Reservation Order Controller"})
  @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> delelteReservation(@Valid @RequestParam String storeName,
      Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(storeROPService.deleteReservation(storeName, authentication.getName()));
  }

  @PostMapping("/order")
  @Operation(summary = "가게 주문 등록 요청", description = "가게 주문 정보가 수정됩니다.",
      tags = {"Store Reservation Order Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = OrderDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> registerOrder(@Valid @RequestBody OrderDTO orderDTO,
      Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(storeROPService.registerOrder(orderDTO, authentication.getName()));
  }


  @PutMapping("/order")
  @Operation(summary = "가게 주문 수정 요청", description = "가게 주문 정보가 수정됩니다.",
      tags = {"Store Reservation Order Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = OrderDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> updateOrder(@Valid @RequestBody OrderDTO orderDTO,
      Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(storeROPService.updateOrder(orderDTO, authentication.getName()));
  }


  @DeleteMapping("/order")
  @Operation(summary = "가게 주문 삭제 요청", description = "가게 주문 정보가 삭제됩니다.",
      tags = {"Store Reservation Order Controller"})
  @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> delelteReservation(@Valid @RequestParam String storeName,
      String foodName, Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK).body(
        storeROPService.deleteOrder(storeName, foodName, authentication.getName()));
  }


  @PostMapping("/pay")
  @Operation(summary = "가게 결제 등록 요청", description = "가게 결제 정보가 등록됩니다.",
      tags = {"Store Reservation Order Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = PayDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> registerPay(@Valid @RequestBody PayDTO payDTO,
      Authentication authentication) {
    if (!authentication.getName().equals(payDTO.getStoreName())) {
      return ResponseEntity.status(HttpStatus.OK).body("권한이 없습니다.");
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(storeROPService.registerPay(payDTO));
  }

  @DeleteMapping("/pay")
  @Operation(summary = "가게 결제 삭제 요청", description = "가게 결제 정보가 삭제됩니다.",
      tags = {"Store Reservation Order Controller"})
  @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> deleltePay(@Valid @RequestParam String storeName,
      Long reservationId, Authentication authentication) {
    if (!authentication.getName().equals(storeName)) {
      return ResponseEntity.status(HttpStatus.OK).body("권한이 없습니다.");
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(storeROPService.deletePay(reservationId));
  }
}
