package com.ReservationServer1.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.service.StoreInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController("StoreInfoController")
@RequestMapping("/info")
public class StoreInfoController {

  private final Logger logger = LoggerFactory.getLogger(StoreInfoController.class);
  private final StoreInfoService storeInfoService;

  public StoreInfoController(StoreInfoService storeInfoService) {
    this.storeInfoService = storeInfoService;
  }

  @PostMapping("/day")
  @Operation(summary = "가게 쉬는날 등록 요청", description = "가게 쉬는날 정보가 등록됩니다.",
      tags = {"Store Info Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = RestDayDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> postDayOff(@Valid @RequestBody RestDayDTO restDayDTO,
      Authentication authentication) {
    logger.info("[StoreRestDayController] post Day Off(쉬는날 등록) 호출");
    if (!authentication.getName().equals(restDayDTO.getStoreName())) {
      return ResponseEntity.status(HttpStatus.OK).body("권한이 없습니다.");
    }
    String result = storeInfoService.postDayOff(restDayDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/day")
  @Operation(summary = "가게 쉬는날 반환 요청", description = "가게 쉬는날 정보가 반환됩니다.",
      tags = {"Store Info Controller"})
  @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<List<String>> getDayOff(@RequestParam String storeName) {
    logger.info("[StoreRestDayController] get Day Off(쉬는날 반환) 호출");
    List<String> result = storeInfoService.getDayOff(storeName);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @DeleteMapping("/day")
  @Operation(summary = "가게 쉬는날 삭제 요청", description = "가게 쉬는날 정보가 삭제됩니다.",
      tags = {"Store Info Controller"})
  @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> deleteDayOff(@Valid @RequestBody RestDayDTO restDayDTO,
      Authentication authentication) {
    logger.info("[StoreRestDayController] delete Day Off(쉬는날 삭제) 호출");
    if(!restDayDTO.getStoreName().equals(authentication.getName())) {
      return ResponseEntity.status(HttpStatus.OK).body("권한이 없습니다.");
    }
    String result = storeInfoService.deleteDayOff(restDayDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }


}
