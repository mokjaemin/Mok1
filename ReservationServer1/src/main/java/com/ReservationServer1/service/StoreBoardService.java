package com.ReservationServer1.service;

import java.util.List;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.DTO.board.BoardResultDTO;

public interface StoreBoardService {
  String registerBoard(BoardDTO boardDTO, String userId);
  String updateBoard(Long boardId, BoardDTO boardDTO, String userId);
  String deleteBoard(Long boardId, String userId);
  
  List<BoardResultDTO> getBoard(int storeId);
  List<BoardResultDTO> getBoardByUser(String userId);
  
  String registerBoardComment(Long boardId, String comment, String storeId);
  String updateBoardComment(Long boardId, String comment, String storeId);
  String deleteBoardComment(Long boardId, String storeId);
}
