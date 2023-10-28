package com.ReservationServer1.dao;

import static com.ReservationServer1.data.Entity.store.QStoreEntity.storeEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ReservationServer1.DAO.Impl.StoreDAOImpl;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class StoreDAOTest {

	@Mock
	private JPAQueryFactory queryFactory;

	@InjectMocks
	private StoreDAOImpl storeDAOImpl;

	@Mock
	private EntityManager entityManager;

	@Mock
	private JPAQuery<MemberEntity> jpaQuery;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	// 1. Register Store
	@Test
	@DisplayName("가게 등록 성공")
	public void registerStoreSuccess() {
		// given
		StoreEntity sample = new StoreEntity(StoreDTO.sample());
		doNothing().when(entityManager).persist(any(StoreEntity.class));

		// when
		String result = storeDAOImpl.registerStore(sample);

		// then
		assertEquals("success", result);
		verify(entityManager, times(1)).persist(any(StoreEntity.class));
		verify(entityManager).persist(eq(sample));
	}

	@Test
	@DisplayName("가게 로그인 성공")
	public void loginStoreSuccess() {
		// given
		short storeId = 1;
		String ownerId = "ownerId";
		doReturn(jpaQuery).when(queryFactory).select(storeEntity.ownerId);
		doReturn(jpaQuery).when(jpaQuery).from(storeEntity);
		doReturn(jpaQuery).when(jpaQuery).where(storeEntity.storeId.eq(storeId));
		doReturn(jpaQuery).when(jpaQuery).limit(1);
		doReturn(ownerId).when(jpaQuery).fetchOne();

		// when
		String result = storeDAOImpl.loginStore(storeId);

		// then
		assertEquals(ownerId, result);
	}
}
