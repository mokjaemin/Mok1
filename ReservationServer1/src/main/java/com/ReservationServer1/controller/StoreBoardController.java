package com.ReservationServer1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.service.StoreBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController("StoreBoardController")
@RequestMapping("/board")
public class StoreBoardController {


  private final StoreBoardService storeBoardService;

  public StoreBoardController(StoreBoardService storeBoardService) {
    this.storeBoardService = storeBoardService;
  }

  @PostMapping
  @Operation(summary = "가게 후기 등록 요청", description = "가게 후기가 등록됩니다.",
      tags = {"Store Board Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = BoardDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String> registerBoard(@Valid @ModelAttribute BoardDTO board, Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK).body(storeBoardService.registerBoard(board, authentication.getName()));
  }
  
  
  @PutMapping
  @Operation(summary = "가게 후기 등록 수정 요청", description = "가게 후기가 수정됩니다.",
      tags = {"Store Board Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = BoardDTO.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String>  updateBoard(@Valid @RequestParam Long boardId, @ModelAttribute BoardDTO board, Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK).body(storeBoardService.updateBoard(boardId, board, authentication.getName()));
  }
  
  @DeleteMapping
  @Operation(summary = "가게 후기 등록 요청", description = "가게 후기가 등록됩니다.",
      tags = {"Store Board Controller"})
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
  public ResponseEntity<String>  deleteBoard(@Valid @RequestParam Long boardId, Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK).body(storeBoardService.deleteBoard(boardId, authentication.getName()));
  }
}
