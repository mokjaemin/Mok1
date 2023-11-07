package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.store.QStoreFoodsInfoEntity.storeFoodsInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTableInfoEntity.storeTableInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoEntity.storeTimeInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoMapEntity.storeTimeInfoMapEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoMapEntity;
import com.ReservationServer1.exception.NoInformationException;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository("StoreInfoDAO")
public class StoreInfoDAOImpl implements StoreInfoDAO {

	private final JPAQueryFactory queryFactory;
	private final EntityManager entityManager;

	public StoreInfoDAOImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
		this.queryFactory = queryFactory;
		this.entityManager = entityManager;
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String registerDayOff(StoreRestDayDTO restDayDTO) {

		StoreRestDaysEntity restDays = new StoreRestDaysEntity(restDayDTO.getStoreId());

		entityManager.persist(restDays);

		Set<StoreRestDaysMapEntity> restDaysMapList = restDayDTO.getDate().stream().map(date -> {
			StoreRestDaysMapEntity restDaysMap = new StoreRestDaysMapEntity(date, restDays);
			entityManager.persist(restDaysMap);
			return restDaysMap;
		}).collect(Collectors.toSet());

		restDays.setRestDaysMap(restDaysMapList);
		return "success";
	}

	@Override
	@Transactional(timeout = 10, readOnly = true, rollbackFor = Exception.class)
	public List<String> getDayOff(short storeId) {

		List<String> dateList = queryFactory.selectDistinct(storeRestDaysMapEntity.date).from(storeRestDaysMapEntity)
				.leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity)
				.where(storeRestDaysEntity.storeId.eq(storeId)).fetch();

		if (dateList == null | dateList.size() == 0) {
			throw new NoInformationException();
		}

		List<String> result = dateList.stream().sorted().toList();
		return result;
	}

	// Cascade로 설정
	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String deleteDayOff(short storeId) {

		List<StoreRestDaysEntity> restDaysList = queryFactory.select(storeRestDaysEntity).from(storeRestDaysEntity)
				.leftJoin(storeRestDaysEntity.restDaysMap, storeRestDaysMapEntity).fetchJoin()
				.where(storeRestDaysEntity.storeId.eq(storeId)).fetch();

		restDaysList.forEach(restDays -> {
			restDays.getRestDaysMap().forEach(restDaysMap -> {
				queryFactory.delete(storeRestDaysMapEntity)
						.where(storeRestDaysMapEntity.dayId.eq(restDaysMap.getDayId())).execute();
			});

			queryFactory.delete(storeRestDaysEntity).where(storeRestDaysEntity.daysId.eq(restDays.getDaysId()))
					.execute();
		});

		return "success";
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String registerTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO) {

		StoreTimeInfoEntity timeInfo = StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
				.endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
				.storeId(storeTimeInfoDTO.getStoreId()).build();

		entityManager.persist(timeInfo);

		Set<StoreTimeInfoMapEntity> timeInfoMapList = storeTimeInfoDTO.getBreakTime().stream().map(breakTime -> {
			StoreTimeInfoMapEntity timeInfoMap = StoreTimeInfoMapEntity.builder().time(breakTime)
					.storeTimeInfoEntity(timeInfo).build();
			entityManager.persist(timeInfoMap);
			return timeInfoMap;
		}).collect(Collectors.toSet());

		timeInfo.setBreakTime(timeInfoMapList);

		return "success";
	}

	@Override
	@Transactional(timeout = 10, readOnly = true, rollbackFor = Exception.class)
	public StoreTimeInfoEntity getTimeInfo(short storeId) {

		StoreTimeInfoEntity timeInfo = queryFactory.select(storeTimeInfoEntity).from(storeTimeInfoEntity)
				.leftJoin(storeTimeInfoEntity.breakTime, storeTimeInfoMapEntity).fetchJoin()
				.where(storeTimeInfoMapEntity.storeTimeInfoEntity.storeId.eq(storeId))
				.orderBy(storeTimeInfoMapEntity.time.asc()).fetchFirst();

		if (timeInfo == null) {
			throw new NoInformationException();
		}

		return timeInfo;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String updateTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO) {

		short storeId = storeTimeInfoDTO.getStoreId();
		StoreTimeInfoEntity timeInfo = queryFactory.select(storeTimeInfoEntity).from(storeTimeInfoEntity)
				.where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();

		if (timeInfo == null) {
			throw new NoInformationException();
		}

		queryFactory.delete(storeTimeInfoMapEntity).where(storeTimeInfoMapEntity.storeTimeInfoEntity.eq(timeInfo))
				.execute();

		Set<StoreTimeInfoMapEntity> timeInfoMapList = storeTimeInfoDTO.getBreakTime().stream().map(breakTime -> {
			StoreTimeInfoMapEntity timeInfoMap = StoreTimeInfoMapEntity.builder().time(breakTime)
					.storeTimeInfoEntity(timeInfo).build();
			entityManager.persist(timeInfoMap);
			return timeInfoMap;
		}).collect(Collectors.toSet());

		timeInfo.setStartTime(storeTimeInfoDTO.getStartTime());
		timeInfo.setEndTime(storeTimeInfoDTO.getEndTime());
		timeInfo.setIntervalTime(storeTimeInfoDTO.getIntervalTime());
		timeInfo.setBreakTime(timeInfoMapList);

		return "success";
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String deleteTimeInfo(short storeId) {

		int timesId = queryFactory.select(storeTimeInfoEntity.timesId).from(storeTimeInfoEntity)
				.where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();

		queryFactory.delete(storeTimeInfoMapEntity)
				.where(storeTimeInfoMapEntity.storeTimeInfoEntity.timesId.eq(timesId)).execute();

		queryFactory.delete(storeTimeInfoEntity).where(storeTimeInfoEntity.timesId.eq(timesId)).execute();
		return "success";
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String registerTableInfo(StoreTableInfoEntity tableInfo) {
		entityManager.persist(tableInfo);
		return "success";
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String updateTableInfo(StoreTableInfoEntity tableInfo) {
		StoreTableInfoEntity getTableInfo = queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity)
				.where(storeTableInfoEntity.storeId.eq(tableInfo.getStoreId())).fetchFirst();
		getTableInfo.setCount(tableInfo.getCount());
		getTableInfo.setTableImage(tableInfo.getTableImage());
		return "success";
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String deleteTableInfo(short storeId) {
		queryFactory.delete(storeTableInfoEntity).where(storeTableInfoEntity.storeId.eq(storeId)).execute();
		return "success";
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String registerFoodsInfo(StoreFoodsInfoEntity foodsInfo) {
		entityManager.persist(foodsInfo);
		return "success";
	}

	@Override
	@Transactional(timeout = 10, readOnly = true, rollbackFor = Exception.class)
	public List<StoreFoodsInfoEntity> getFoodsInfo(short storeId) {

		List<StoreFoodsInfoEntity> foodsInfo = queryFactory.select(storeFoodsInfoEntity).from(storeFoodsInfoEntity)
				.where(storeFoodsInfoEntity.storeId.eq(storeId)).fetch();
		if (foodsInfo == null | foodsInfo.size() == 0) {
			throw new NoInformationException();
		}
		return foodsInfo;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String updateFoodsInfo(StoreFoodsInfoEntity foodsInfo) {
		StoreFoodsInfoEntity getFoodsInfo = queryFactory
				.select(storeFoodsInfoEntity).from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId
						.eq(foodsInfo.getStoreId()).and(storeFoodsInfoEntity.foodName.eq(foodsInfo.getFoodName())))
				.fetchFirst();
		if (getFoodsInfo == null) {
			throw new NoInformationException();
		}
		getFoodsInfo.setFoodName(foodsInfo.getFoodName());
		getFoodsInfo.setFoodDescription(foodsInfo.getFoodDescription());
		getFoodsInfo.setFoodPrice(foodsInfo.getFoodPrice());
		getFoodsInfo.setFoodImage(foodsInfo.getFoodImage());
		return "success";
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String deleteFoodsInfo(short storeId, String foodName) {
		queryFactory.delete(storeFoodsInfoEntity)
				.where(storeFoodsInfoEntity.storeId.eq(storeId).and(storeFoodsInfoEntity.foodName.eq(foodName)))
				.execute();
		return "success";
	}
}
