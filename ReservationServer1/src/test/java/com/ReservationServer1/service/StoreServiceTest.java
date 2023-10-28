package com.ReservationServer1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.DAO.Cache.StoreListCache;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.ReservationServer1.service.Impl.StoreServiceImpl;
import com.ReservationServer1.utils.JWTutil;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

	@InjectMocks
	private StoreServiceImpl storeServiceImpl;

	@Mock
	private StoreDAO storeDAO;

	@Mock
	private StoreListCache storeListCache;

	@Mock
	private JWTutil jwtUtil;

	// 1. Register Member
	@Test
	@DisplayName("가게 등록 성공")
	void registerStoreSuccess() throws Exception {
		// given
		StoreDTO sample = StoreDTO.sample();
		String response = "success";
		doReturn(response).when(storeDAO).registerStore(any(StoreEntity.class));

		// when
		String result = storeServiceImpl.registerStore(sample);

		// then
		assertThat(result.equals(response));

		// verify
		verify(storeDAO, times(1)).registerStore(any(StoreEntity.class));
		verify(storeDAO).registerStore(eq(new StoreEntity(sample)));
	}



	@Test
	@DisplayName("가게 로그인 실패")
	public void loginStoreTestFail() {
		// given
		String userId = "testId";
		String wrongId = "wrongId";
		doReturn(wrongId).when(storeDAO).loginStore(anyShort());

		// when
		String result = storeServiceImpl.loginStore((short) 0, userId);

		// then
		assertEquals(result, "user");

		// verify
		verify(storeDAO, times(1)).loginStore(anyShort());
		verify(storeDAO).loginStore((short) 0);
	}

}
