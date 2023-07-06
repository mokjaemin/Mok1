package com.ReservationServer1.service;

import com.ReservationServer1.data.DTO.board.BoardDTO;

public interface StoreBoardService {
  String registerBoard(BoardDTO boardDTO, String userId);
  String updateBoard(Long boardId, BoardDTO boardDTO, String userId);
  String deleteBoard(Long boardId, String userId);
}
