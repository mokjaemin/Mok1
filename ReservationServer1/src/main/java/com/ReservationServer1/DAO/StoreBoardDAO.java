package com.ReservationServer1.DAO;

import java.util.Map;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;

public interface StoreBoardDAO {

	String registerBoard(StoreBoardEntity board);

	String updateBoard(int boardId, BoardDTO boardDTO, String userId);

	String deleteBoard(int boardId, String userId);

	Map<String, Integer> getBoardListByStore(short storeId);

	Map<String, Integer> getBoardListByUser(String userId);
	
	StoreBoardEntity getFullBoard(int boardId);

	String registerBoardComment(int boardId, String comment, String storeId);

	String updateBoardComment(int boardId, String comment, String storeId);

	String deleteBoardComment(int boardId, String storeId);
}
