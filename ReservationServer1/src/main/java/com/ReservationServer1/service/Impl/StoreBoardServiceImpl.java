package com.ReservationServer1.service.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreBoardDAO;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.DTO.board.BoardResultDTO;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;
import com.ReservationServer1.service.StoreBoardService;

@Service
public class StoreBoardServiceImpl implements StoreBoardService {


  private final StoreBoardDAO storeBoardDAO;

  public StoreBoardServiceImpl(StoreBoardDAO storeBoardDAO) {
    this.storeBoardDAO = storeBoardDAO;
  }

  @Override
  public String registerBoard(BoardDTO boardDTO, String userId) {
    try {
      // Entity 생성
      StoreBoardEntity entity = StoreBoardEntity.builder().storeId(boardDTO.getStoreId())
          .userId(userId).title(boardDTO.getTitle()).content(boardDTO.getContent())
          .rating(boardDTO.getRating()).boardImage(boardDTO.getFoodImage().getBytes()).build();
      String boardId = storeBoardDAO.registerBoard(entity);
      return boardId;
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return "FILE UPLOAD FAIl";
    }
  }

  @Override
  public String updateBoard(int boardId, BoardDTO boardDTO, String userId) {
    return storeBoardDAO.updateBoard(boardId, boardDTO, userId);
  }

  @Override
  public String deleteBoard(int boardId, String userId) {
    return storeBoardDAO.deleteBoard(boardId, userId);
  }

  @Override
  public List<BoardResultDTO> getBoard(short storeId) {

    List<StoreBoardEntity> result = storeBoardDAO.getBoard(storeId);
    List<BoardResultDTO> answer = new ArrayList<>();
    for (StoreBoardEntity now : result) {
      BoardResultDTO dto = BoardResultDTO.builder().boardId(now.getBoardId()).storeId(storeId)
          .userId(now.getUserId()).title(now.getTitle()).content(now.getContent())
          .comment(now.getComment()).rating(now.getRating()).boardImage(now.getBoardImage())
          .build();
      answer.add(dto);
    }

    return answer;
  }

  @Override
  public List<BoardResultDTO> getBoardByUser(String userId) {

    List<StoreBoardEntity> result = storeBoardDAO.getBoardById(userId);
    List<BoardResultDTO> answer = new ArrayList<>();
    for (StoreBoardEntity now : result) {
      BoardResultDTO dto = BoardResultDTO.builder().boardId(now.getBoardId())
          .storeId(now.getStoreId()).userId(userId).title(now.getTitle()).content(now.getContent())
          .comment(now.getComment()).rating(now.getRating()).boardImage(now.getBoardImage())
          .build();
      answer.add(dto);
    }
    return answer;
  }

  @Override
  public String registerBoardComment(int boardId, String comment, String storeId) {
    return storeBoardDAO.registerBoardComment(boardId, comment, storeId);
  }

  @Override
  public String updateBoardComment(int boardId, String comment, String storeId) {
    return storeBoardDAO.updateBoardComment(boardId, comment, storeId);
  }

  @Override
  public String deleteBoardComment(int boardId, String storeId) {
    return storeBoardDAO.deleteBoardComment(boardId, storeId);
  }
}
