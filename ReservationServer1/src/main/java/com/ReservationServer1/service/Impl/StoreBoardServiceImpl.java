package com.ReservationServer1.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreBoardDAO;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.service.StoreBoardService;

@Service
public class StoreBoardServiceImpl implements StoreBoardService {


  private static final String DIR_BOARD = "/Users/mokjaemin/Desktop/Mok1/storeBoard";
  private final StoreBoardDAO storeBoardDAO;

  public StoreBoardServiceImpl(StoreBoardDAO storeBoardDAO) {
    this.storeBoardDAO = storeBoardDAO;
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
      return "success";
    } catch (IOException e) {
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

}
