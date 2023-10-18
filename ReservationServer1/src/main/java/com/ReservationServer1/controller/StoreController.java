package com.ReservationServer1.controller;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ReservationServer1.data.StoreType;
import com.ReservationServer1.data.DTO.store.StoreDTO;
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

	private final StoreService storeService;

	public StoreController(StoreService storeService) {
		this.storeService = storeService;
	}

	@PostMapping
	@Operation(summary = "가게 등록 요청", description = "가게 정보가 등록됩니다.", tags = { "Store Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StoreDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> registerStore(@Valid @RequestBody StoreDTO storeDTO, Authentication authentication) {
		// JWT에서 Id추출해 ownerId 설정
		storeDTO.setOwnerId(authentication.getName());
		return ResponseEntity.status(HttpStatus.OK).body(storeService.registerStore(storeDTO));
	}

	@GetMapping("/list")
	@Operation(summary = "가게 리스트 출력 요청", description = "가게 리스트가 분류되어 반환됩니다.", tags = { "Store Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<HashMap<String, Short>> getStoreList(@RequestParam String country, String city, String dong,
			StoreType type, int page, int size) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storeService.getStoreList(country, city, dong, type, page, size));
	}

	@GetMapping
	@Operation(summary = "가게 권한 인증 요청", description = "가게 사장/손님 등 권한이 반환됩니다.", tags = { "Store Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> loginStore(@RequestParam short storeId, Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK).body(storeService.loginStore(storeId, authentication.getName()));
	}
}
