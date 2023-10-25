package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.store.QStoreEntity.storeEntity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.data.StoreType;
import com.ReservationServer1.data.DTO.store.StoreListResultDTO;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository("StoreDAO")
@Transactional
public class StoreDAOImpl implements StoreDAO {

	private final JPAQueryFactory queryFactory;
	private final EntityManager entityManager;

	public StoreDAOImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.queryFactory = queryFactory;
	}

	@Override
	public String registerStore(StoreEntity storeEntity) {
		entityManager.persist(storeEntity);
		return "success";
	}

	@Override
	public HashMap<String, Short> getStoreList(String country, String city, String dong, StoreType type, int page,
			int size) {

		List<StoreListResultDTO> storeList = queryFactory
				.select(Projections.fields(StoreListResultDTO.class, storeEntity.storeId, storeEntity.storeName))
				.from(storeEntity)
				.where(eqCountry(country).and(eqCity(city)).and(eqDong(dong)).and(eqType(type))
						.and(storeEntity.storeId.gt(page * size)))
				.orderBy(storeEntity.storeId.asc()).limit(size).offset(page * size).fetch();

		return storeList.stream().collect(Collectors.toMap(StoreListResultDTO::getStoreName,
				StoreListResultDTO::getStoreId, (existing, replacement) -> existing, HashMap::new));

	}

	@Override
	public String loginStore(short storeId) {
		String ownerId = queryFactory.select(storeEntity.ownerId).from(storeEntity)
				.where(storeEntity.storeId.eq(storeId)).fetchFirst();
		return ownerId;
	}

	private BooleanExpression eqCountry(String country) {
		if (StringUtils.isEmpty(country)) {
			return null;
		}
		return storeEntity.country.eq(country);
	}

	private BooleanExpression eqCity(String city) {
		if (StringUtils.isEmpty(city)) {
			return null;
		}
		return storeEntity.city.eq(city);
	}

	private BooleanExpression eqDong(String dong) {
		if (StringUtils.isEmpty(dong)) {
			return null;
		}
		return storeEntity.dong.eq(dong);
	}

	private BooleanExpression eqType(StoreType type) {
		if (type == null) {
			return null;
		}
		return storeEntity.type.eq(type);
	}

}
