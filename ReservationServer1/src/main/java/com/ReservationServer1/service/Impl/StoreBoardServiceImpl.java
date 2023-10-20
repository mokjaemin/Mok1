package com.ReservationServer1.service.Impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ReservationServer1.DAO.StoreBoardDAO;
import com.ReservationServer1.data.DTO.board.BoardCountResultDTO;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.DTO.board.BoardListResultDTO;
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
			StoreBoardEntity board = StoreBoardEntity.builder().storeId(boardDTO.getStoreId()).userId(userId)
					.title(boardDTO.getTitle()).content(boardDTO.getContent()).rating(boardDTO.getRating())
					.boardImage(boardDTO.getFoodImage().getBytes()).views(0).build();
			String boardId = storeBoardDAO.registerBoard(board);
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
	public List<BoardListResultDTO> getBoardListByStore(short storeId) {
		return storeBoardDAO.getBoardListByStore(storeId);

	}

	@Override
	public List<BoardListResultDTO> getBoardListByUser(String userId) {
		return storeBoardDAO.getBoardListByUser(userId);
	}

	@Override
	public BoardResultDTO getFullBoard(int boardId) {
		StoreBoardEntity boardEntity = storeBoardDAO.getFullBoard(boardId);
		BoardResultDTO boardResultDTO = BoardResultDTO.builder().boardId(boardId).storeId(boardEntity.getStoreId())
				.userId(boardEntity.getUserId()).title(boardEntity.getTitle()).content(boardEntity.getContent())
				.comment(boardEntity.getComment()).rating(boardEntity.getRating()).views(boardEntity.getViews())
				.encoded_boardImage(Base64.getEncoder().encodeToString(boardEntity.getBoardImage())).build();
		return boardResultDTO;
	}
	
	@Override
	public List<BoardCountResultDTO> getBoardCountByUserOfStore(short storeId) {
		return storeBoardDAO.getBoardCountByUserOfStore(storeId);
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
