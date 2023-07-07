package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;

public interface StoreBoardDAO {

  String registerBoard(BoardDTO boardDTO, String userId, String imageURL);
  String updateBoard(Long boardId, BoardDTO boardDTO, String userId);
  Long deleteBoard(Long boardId, String userId);
  
  List<StoreBoardEntity> getBoard(int storeId);
  List<StoreBoardEntity> getBoardById(String userId);
  
  String registerBoardComment(Long boardId, String comment, String storeId);
  String updateBoardComment(Long boardId, String comment, String storeId);
  String deleteBoardComment(Long boardId, String storeId);
}
