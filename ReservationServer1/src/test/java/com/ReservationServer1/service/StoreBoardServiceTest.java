package com.ReservationServer1.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ReservationServer1.DAO.StoreBoardDAO;
import com.ReservationServer1.service.Impl.StoreBoardServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StoreBoardServiceTest {

	@InjectMocks
	private StoreBoardServiceImpl storeBoardServiceImpl;

	@Mock
	private StoreBoardDAO storeBoardDAO;

	// 6. Register Board Comment
	@Test
	@DisplayName("가게 게시판 댓글 등록 성공")
	void registeBoardCommentSuccess() throws Exception {

		// given
		int boardId = 0;
		String comment = "comment";
		String storeId = "storeId";
		String response = "success";
		doReturn(response).when(storeBoardDAO).registerBoardComment(anyInt(), anyString(), anyString());

		// when
		String result = storeBoardServiceImpl.registerBoardComment(boardId, comment, storeId);

		// then
		assertEquals(result, response);

		// verify
		verify(storeBoardDAO, times(1)).registerBoardComment(anyInt(), anyString(), anyString());
		verify(storeBoardDAO, times(1)).registerBoardComment(boardId, comment, storeId);
	}

	// 7. Update Board Comment
	@Test
	@DisplayName("가게 게시판 댓글 수정 성공")
	void updateBoardCommentSuccess() throws Exception {

		// given
		int boardId = 0;
		String comment = "comment";
		String storeId = "storeId";
		String response = "success";
		doReturn(response).when(storeBoardDAO).updateBoardComment(anyInt(), anyString(), anyString());

		// when
		String result = storeBoardServiceImpl.updateBoardComment(boardId, comment, storeId);

		// then
		assertEquals(result, response);

		// verify
		verify(storeBoardDAO, times(1)).updateBoardComment(anyInt(), anyString(), anyString());
		verify(storeBoardDAO, times(1)).updateBoardComment(boardId, comment, storeId);
	}

	// 8. Delete Board Comment
	@Test
	@DisplayName("가게 게시판 댓글 삭제 성공")
	void deleteBoardCommentSuccess() throws Exception {

		// given
		int boardId = 0;
		String storeId = "storeId";
		String response = "success";
		doReturn(response).when(storeBoardDAO).deleteBoardComment(anyInt(), anyString());

		// when
		String result = storeBoardServiceImpl.deleteBoardComment(boardId, storeId);

		// then
		assertEquals(result, response);

		// verify
		verify(storeBoardDAO, times(1)).deleteBoardComment(anyInt(), anyString());
		verify(storeBoardDAO, times(1)).deleteBoardComment(boardId, storeId);
	}

}
