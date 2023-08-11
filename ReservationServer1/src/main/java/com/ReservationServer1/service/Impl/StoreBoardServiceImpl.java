package com.ReservationServer1.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreBoardDAO;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.DTO.board.BoardResultDTO;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;
import com.ReservationServer1.service.StoreBoardService;

@Service
public class StoreBoardServiceImpl implements StoreBoardService {


  private String DIR_BOARD = "/Users/mokjaemin/Desktop/Mok1/storeBoard/";
  private final StoreBoardDAO storeBoardDAO;

  public StoreBoardServiceImpl(StoreBoardDAO storeBoardDAO) {
    this.storeBoardDAO = storeBoardDAO;
  }
  
  public void setDirBoard(String dir) {
    this.DIR_BOARD = dir;
  }

  @Override
  public String registerBoard(BoardDTO boardDTO, String userId) {
    try {
      File uploadDir = new File(DIR_BOARD);
      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }
      String boardId = storeBoardDAO.registerBoard(boardDTO, userId, DIR_BOARD);
      Path filePath = Path.of(DIR_BOARD, String.valueOf(boardId) + ".png");
      Files.copy(boardDTO.getFoodImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      return boardId;
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return "File upload failed";
    }
  }

  @Override
  public String updateBoard(Long boardId, BoardDTO boardDTO, String userId) {
    try {
      storeBoardDAO.updateBoard(boardId, boardDTO, userId);
      File uploadDir = new File(DIR_BOARD);
      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }
      Path filePath = Path.of(DIR_BOARD, String.valueOf(boardId) + ".png");
      Files.copy(boardDTO.getFoodImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      return "success";
    } catch (IOException e) {
      return "File upload failed";
    }
  }

  @Override
  public String deleteBoard(Long boardId, String userId) {
    try {
      Long imageName = storeBoardDAO.deleteBoard(boardId, userId);
      Path filePath = Path.of(DIR_BOARD, String.valueOf(imageName) + ".png");
      Files.delete(filePath);
      return "success";
    } catch (IOException e) {
      return "File Delete Failed";
    }
  }

  @Override
  public List<BoardResultDTO> getBoard(int storeId) {
    try {
      List<StoreBoardEntity> result = storeBoardDAO.getBoard(storeId);
      List<BoardResultDTO> answer = new ArrayList<>();
      for (StoreBoardEntity now : result) {
        BoardResultDTO dto = BoardResultDTO.builder().boardId(now.getBoardId()).storeId(storeId)
            .userId(now.getUserId()).title(now.getTitle()).content(now.getContent())
            .comment(now.getComment()).rating(now.getRating()).build();
        Path filePath = Path.of(now.getImageURL());
        byte[] imageBytes = Files.readAllBytes(filePath);
        String encoded_image = Base64.getEncoder().encodeToString(imageBytes);
        dto.setFoodImage(encoded_image);
        answer.add(dto);
      }
      return answer;
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public List<BoardResultDTO> getBoardByUser(String userId) {
    try {
      List<StoreBoardEntity> result = storeBoardDAO.getBoardById(userId);
      List<BoardResultDTO> answer = new ArrayList<>();
      for (StoreBoardEntity now : result) {
        BoardResultDTO dto = BoardResultDTO.builder().boardId(now.getBoardId())
            .storeId(now.getStoreId()).userId(userId).title(now.getTitle())
            .content(now.getContent()).comment(now.getComment()).rating(now.getRating()).build();
        Path filePath = Path.of(now.getImageURL());
        byte[] imageBytes = Files.readAllBytes(filePath);
        String encoded_image = Base64.getEncoder().encodeToString(imageBytes);
        dto.setFoodImage(encoded_image);
        answer.add(dto);
      }
      return answer;
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public String registerBoardComment(Long boardId, String comment, String storeId) {
    return storeBoardDAO.registerBoardComment(boardId, comment, storeId);
  }

  @Override
  public String updateBoardComment(Long boardId, String comment, String storeId) {
    return storeBoardDAO.updateBoardComment(boardId, comment, storeId);
  }

  @Override
  public String deleteBoardComment(Long boardId, String storeId) {
    return storeBoardDAO.deleteBoardComment(boardId, storeId);
  }
}
