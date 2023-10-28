package com.ReservationServer1.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;
import com.ReservationServer1.service.Impl.StoreInfoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StoreInfoServiceTest {

	@InjectMocks
	private StoreInfoServiceImpl storeInfoServiceImpl;

	@Mock
	private StoreInfoDAO storeInfoDAO;

	// 1. Register Member
	@Test
	@DisplayName("가게 쉬는날 등록 성공")
	void registerDayOffSuccess() throws Exception {
		// given
		StoreRestDayDTO sample = StoreRestDayDTO.sample();
		String response = "success";
		doReturn(response).when(storeInfoDAO).registerDayOff(any(StoreRestDayDTO.class));

		// when
		String result = storeInfoServiceImpl.registerDayOff(sample);

		// then
		assertEquals(result, response);

		// verify
		verify(storeInfoDAO, times(1)).registerDayOff(any(StoreRestDayDTO.class));
		verify(storeInfoDAO).registerDayOff(sample);
	}

	// 2. Get Day Off
	@Test
	@DisplayName("가게 쉬는날 출력 성공")
	void getDayOffSuccess() throws Exception {
		// given
		short storeId = 1;
		List<String> response = new ArrayList<>();
		response.add("1월1일");
		response.add("1월2일");
		doReturn(response).when(storeInfoDAO).getDayOff(anyShort());

		// when
		List<String> result = storeInfoServiceImpl.getDayOff(storeId);

		// then
		assertEquals(result, response);

		// verify
		verify(storeInfoDAO, times(1)).getDayOff(anyShort());
		verify(storeInfoDAO).getDayOff(storeId);
	}

	// 3. Delete Day Off
	@Test
	@DisplayName("가게 쉬는날 삭제 성공")
	void deleteDayOffSuccess() throws Exception {
		// given
		short storeId = 1;
		String response = "success";
		doReturn(response).when(storeInfoDAO).deleteDayOff(anyShort());

		// when
		String result = storeInfoServiceImpl.deleteDayOff(storeId);

		// then
		assertEquals(result, response);

		// verify
		verify(storeInfoDAO, times(1)).deleteDayOff(anyShort());
		verify(storeInfoDAO).deleteDayOff(storeId);
	}

	// 4. Register Time Info
	@Test
	@DisplayName("가게 시간정보 등록 성공")
	void registerTimeInfoSuccess() throws Exception {
		// given
		StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
		String response = "success";
		doReturn(response).when(storeInfoDAO).registerTimeInfo(any(StoreTimeInfoDTO.class));

		// when
		String result = storeInfoServiceImpl.registerTimeInfo(sample);

		// then
		assertEquals(result, response);

		// verify
		verify(storeInfoDAO, times(1)).registerTimeInfo(any(StoreTimeInfoDTO.class));
		verify(storeInfoDAO).registerTimeInfo(sample);
	}

	// 5. Get Time Info
	@Test
	@DisplayName("가게 시간정보 출력 성공")
	void getTimeInfoSuccess() throws Exception {
		// given
		StoreTimeInfoEntity response = new StoreTimeInfoEntity();
		short storeId = response.getStoreId();
		doReturn(response).when(storeInfoDAO).getTimeInfo(anyShort());

		// when
		storeInfoServiceImpl.getTimeInfo(storeId);

		// verify
		verify(storeInfoDAO, times(1)).getTimeInfo(anyShort());
		verify(storeInfoDAO).getTimeInfo(storeId);
	}

	// 6. Modify Time Info
	@Test
	@DisplayName("가게 시간정보 등록 성공")
	void modTimeInfoSuccess() throws Exception {
		// given
		StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
		String response = "success";
		doReturn(response).when(storeInfoDAO).updateTimeInfo(any(StoreTimeInfoDTO.class));

		// when
		String result = storeInfoServiceImpl.updateTimeInfo(sample);

		// then
		assertEquals(result, response);

		// verify
		verify(storeInfoDAO, times(1)).updateTimeInfo(any(StoreTimeInfoDTO.class));
		verify(storeInfoDAO).updateTimeInfo(sample);
	}

	// 7. Delete Time Info
	@Test
	@DisplayName("가게 시간정보 삭제 성공")
	void deleteTimeInfoSuccess() throws Exception {
		// given
		short storeId = 1;
		String response = "success";
		doReturn(response).when(storeInfoDAO).deleteTimeInfo(anyShort());

		// when
		String result = storeInfoServiceImpl.deleteTimeInfo(storeId);

		// then
		assertEquals(result, response);

		// verify
		verify(storeInfoDAO, times(1)).deleteTimeInfo(anyShort());
		verify(storeInfoDAO).deleteTimeInfo(storeId);
	}

}
