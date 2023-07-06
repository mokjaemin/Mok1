package com.ReservationServer1.DAO;

import com.ReservationServer1.data.DTO.board.BoardDTO;

public interface StoreBoardDAO {

  String registerBoard(BoardDTO boardDTO, String userId, String imageURL);
  String updateBoard(Long boardId, BoardDTO boardDTO, String userId);
  Long deleteBoard(Long boardId, String userId);
}
