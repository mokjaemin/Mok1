package com.ReservationServer1.service;

import java.util.Map;

import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.DTO.board.BoardResultDTO;

public interface StoreBoardService {
	
	String registerBoard(BoardDTO boardDTO, String userId);

	String updateBoard(int boardId, BoardDTO boardDTO, String userId);

	String deleteBoard(int boardId, String userId);

	Map<String, Integer> getBoardListByStore(short storeId);

	Map<String, Integer> getBoardListByUser(String userId);
	
	BoardResultDTO getFullBoard(int boardId);

	String registerBoardComment(int boardId, String comment, String storeId);

	String updateBoardComment(int boardId, String comment, String storeId);

	String deleteBoardComment(int boardId, String storeId);
}
