package com.ReservationServer1.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ReservationServer1.data.DTO.board.BoardCountResultDTO;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.DTO.board.BoardListResultDTO;
import com.ReservationServer1.data.DTO.board.BoardResultDTO;
import com.ReservationServer1.service.StoreBoardService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	@Operation(summary = "가게 후기 등록 요청", description = "가게 후기가 등록됩니다.", tags = { "Store Board Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BoardDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> registerBoard(@Valid @ModelAttribute BoardDTO board, Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storeBoardService.registerBoard(board, authentication.getName()));
	}

	@PutMapping
	@Operation(summary = "가게 후기 등록 수정 요청", description = "가게 후기가 수정됩니다.", tags = { "Store Board Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BoardDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> updateBoard(@Valid @RequestParam int boardId, @ModelAttribute BoardDTO board,
			Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storeBoardService.updateBoard(boardId, board, authentication.getName()));
	}

	@DeleteMapping
	@Operation(summary = "가게 후기 삭제 요청", description = "가게 후기가 삭제됩니다.", tags = { "Store Board Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> deleteBoard(@Valid @RequestParam int boardId, Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storeBoardService.deleteBoard(boardId, authentication.getName()));
	}

	@GetMapping
	@Operation(summary = "가게별 후기 리스트 출력 요청", description = "가게 후기 리스트가 출력됩니다.", tags = { "Store Board Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<List<BoardListResultDTO>> getBoardListByStore(@Valid @RequestParam short storeId) {
		return ResponseEntity.status(HttpStatus.OK).body(storeBoardService.getBoardListByStore(storeId));
	}

	@GetMapping("/user")
	@Operation(summary = "개인별 후기 리스트 출력 요청", description = "개인별 가게 후기 리스트가 출력됩니다.", tags = { "Store Board Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<List<BoardListResultDTO>> getBoardListByUser(Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK).body(storeBoardService.getBoardListByUser(authentication.getName()));
	}
	
	@GetMapping("/article")
	@Operation(summary = "게시글 상세 출력 요청", description = "게시글 상세 출력됩니다.", tags = { "Store Board Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<BoardResultDTO> getFullBoard(int boardId) {
		return ResponseEntity.status(HttpStatus.OK).body(storeBoardService.getFullBoard(boardId));
	}
	
	@GetMapping("/article/count")
	@Operation(summary = "게시글 상세 출력 요청", description = "게시글 상세 출력됩니다.", tags = { "Store Board Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<List<BoardCountResultDTO>> getBoardCountByUserOfStore(short storeId) {
		return ResponseEntity.status(HttpStatus.OK).body(storeBoardService.getBoardCountByUserOfStore(storeId));
	}

	@PostMapping("/comment")
	@Operation(summary = "가게 후기 댓글 등록 요청", description = "가게 후기 댓글이 등록됩니다.", tags = { "Store Board Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> registerBoardComment(@Valid @RequestParam int boardId, @RequestBody String comment,
			Authentication authentication) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(comment);
		String Comment = jsonNode.get("comment").asText();
		return ResponseEntity.status(HttpStatus.OK)
				.body(storeBoardService.registerBoardComment(boardId, Comment, authentication.getName()));
	}

	@PutMapping("/comment")
	@Operation(summary = "가게 후기 댓글 수정 요청", description = "가게 후기 댓글이 수정됩니다.", tags = { "Store Board Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> updateBoardComment(@Valid @RequestParam int boardId, @RequestBody String comment,
			Authentication authentication) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(comment);
		String Comment = jsonNode.get("comment").asText();
		return ResponseEntity.status(HttpStatus.OK)
				.body(storeBoardService.updateBoardComment(boardId, Comment, authentication.getName()));
	}

	@DeleteMapping("/comment")
	@Operation(summary = "가게 후기 댓글 삭제 요청", description = "가게 후기 댓글이 삭제됩니다.", tags = { "Store Board Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> deleteBoardComment(@Valid @RequestParam int boardId, Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(storeBoardService.deleteBoardComment(boardId, authentication.getName()));
	}
}
