package com.ReservationServer1.controller;

import java.io.IOException;
import java.util.HashMap;
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
import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.PayDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.ReservationServer1.exception.NoAuthorityException;
import com.ReservationServer1.service.StorePORService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController("StoreReservationOrderPaymentController")
@RequestMapping("/por")
public class StorePORController {

	private final StorePORService storePORService;

	public StorePORController(StorePORService storePORService) {
		this.storePORService = storePORService;
	}

	@PostMapping("/reservation")
	@Operation(summary = "가게 예약 등록 요청", description = "가게 예약 정보가 등록됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ReservationDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> registerReservation(@Valid @RequestBody ReservationDTO reservationDTO,
			Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.registerReservation(reservationDTO, authentication.getName()));
	}

	@PutMapping("/reservation")
	@Operation(summary = "가게 예약 수정 요청", description = "가게 예약 정보가 수정됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ReservationDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> updateReservation(@Valid @RequestBody ReservationDTO reservationDTO,
			Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.updateReservation(reservationDTO, authentication.getName()));
	}

	@GetMapping("/reservation")
	@Operation(summary = "가게 예약 정보 출력 요청", description = "가게 예약 정보가 출력됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<StoreReservationEntity> getReservation(@Valid @RequestParam short storeId,
			Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.getReservation(storeId, authentication.getName()));
	}

	@DeleteMapping("/reservation")
	@Operation(summary = "가게 예약 삭제 요청", description = "가게 예약 정보가 삭제됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> delelteReservation(@Valid @RequestParam short storeId,
			Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.deleteReservation(storeId, authentication.getName()));
	}

	@PostMapping("/order")
	@Operation(summary = "가게 주문 등록 요청", description = "가게 주문 정보가 수정됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> registerOrder(@Valid @RequestBody OrderDTO orderDTO, Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.registerOrder(orderDTO, authentication.getName()));
	}

	@PutMapping("/order")
	@Operation(summary = "가게 주문 수정 요청", description = "가게 주문 정보가 수정됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> updateOrder(@Valid @RequestBody OrderDTO orderDTO, Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.updateOrder(orderDTO, authentication.getName()));
	}

	@DeleteMapping("/order")
	@Operation(summary = "가게 주문 삭제 요청", description = "가게 주문 정보가 삭제됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> delelteReservation(@Valid @RequestParam short storeId, String foodName,
			Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.deleteOrder(storeId, foodName, authentication.getName()));
	}

	@PostMapping("/pay")
	@Operation(summary = "가게 결제 등록 요청", description = "가게 결제 정보가 등록됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PayDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> registerPay(@Valid @RequestBody PayDTO payDTO, Authentication authentication) {
		if (!authentication.getName().equals(String.valueOf(payDTO.getStoreId()))) {
			throw new NoAuthorityException();
		}
		return ResponseEntity.status(HttpStatus.OK).body(storePORService.registerPay(payDTO));
	}

	@DeleteMapping("/pay")
	@Operation(summary = "가게 결제 삭제 요청", description = "가게 결제 정보가 삭제됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> deleltePay(@Valid @RequestParam short storeId, int reservationId,
			Authentication authentication) {
		if (!authentication.getName().equals(String.valueOf(storeId))) {
			throw new NoAuthorityException();
		}
		return ResponseEntity.status(HttpStatus.OK).body(storePORService.deletePay(reservationId));
	}

	@PostMapping("/pay/comment")
	@Operation(summary = "가게 결제정보 고객댓글 작성 요청", description = "가게 결제정보 고객댓글 작성됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PayDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> registerComment(@Valid @RequestParam int reservationId, @RequestBody String comment,
			Authentication authentication) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(comment);
		String new_comment = jsonNode.get("comment").asText();
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.registerComment(reservationId, new_comment, authentication.getName()));
	}

	@DeleteMapping("/pay/comment")
	@Operation(summary = "가게 결제정보 고객댓글 삭제 요청", description = "가게 결제정보 고객댓글이 삭제됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> delelteComment(@Valid int reservationId, Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.deleteComment(reservationId, authentication.getName()));
	}

	@PostMapping("/pay/bigcomment")
	@Operation(summary = "가게 결제정보 대댓글 작성 요청", description = "가게 결제정보 대댓글 작성됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PayDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> registerBigComment(@Valid @RequestParam int reservationId,
			@RequestBody String bigcomment, Authentication authentication) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(bigcomment);
		String new_bigcomment = jsonNode.get("bigcomment").asText();
		return ResponseEntity.status(HttpStatus.OK).body(storePORService.registerBigComment(reservationId,
				new_bigcomment, Short.valueOf(authentication.getName())));
	}

	@DeleteMapping("/pay/bigcomment")
	@Operation(summary = "가게 결제정보 대댓글 삭제 요청", description = "가게 결제정보 대댓글이 삭제됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> delelteBigComment(@Valid int reservationId, Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.deleteBigComment(reservationId, Short.valueOf(authentication.getName())));
	}

	@GetMapping("/coupon/client")
	@Operation(summary = "가게 회원별 쿠폰정보 출력 요청", description = "가게 회원별 쿠폰 정보가 출력됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<Integer> getCouponOfClient(@Valid @RequestParam short storeId, Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.getCouponOfClient(storeId, authentication.getName()));
	}

	@GetMapping("/coupon/owner")
	@Operation(summary = "가게별 전체 회원 쿠폰정보 출력 요청", description = "가게별 전체 회원 쿠폰정보가 출력됩니다.", tags = {
			"Store Reservation Order Payment Coupon Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<HashMap<String, Integer>> getCouponOfStore(Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storePORService.getCouponOfStore(Short.valueOf(authentication.getName())));
	}
}
