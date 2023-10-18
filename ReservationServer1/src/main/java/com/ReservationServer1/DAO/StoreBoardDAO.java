package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;

public interface StoreBoardDAO {

	String registerBoard(StoreBoardEntity boardEntity);

	String updateBoard(int boardId, BoardDTO boardDTO, String userId);

	String deleteBoard(int boardId, String userId);

	List<StoreBoardEntity> getBoard(short storeId);

	List<StoreBoardEntity> getBoardById(String userId);

	String registerBoardComment(int boardId, String comment, String storeId);

	String updateBoardComment(int boardId, String comment, String storeId);

	String deleteBoardComment(int boardId, String storeId);
}
