package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.store.QStoreFoodsInfoEntity.storeFoodsInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTableInfoEntity.storeTableInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoEntity.storeTimeInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoMapEntity.storeTimeInfoMapEntity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;
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
import com.ReservationServer1.exception.store.NoInformationException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

@Repository("StoreInfoDAO")
@Transactional
public class StoreInfoDAOImpl implements StoreInfoDAO {

	private final JPAQueryFactory queryFactory;
	private final EntityManager entityManager;

	public StoreInfoDAOImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
		this.queryFactory = queryFactory;
		this.entityManager = entityManager;
	}

	@Override
	public String registerDayOff(StoreRestDayDTO restDayDTO) {

		// 부모 생성
		StoreRestDaysEntity parent = new StoreRestDaysEntity(restDayDTO.getStoreId());

		// 부모 저장
		entityManager.persist(parent);

		// 자식 생성
		Set<StoreRestDaysMapEntity> childs = new HashSet<>();
		for (String date : restDayDTO.getDate()) {
			// 자식 생성
			StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(date, parent);

			// 자식 저장
			entityManager.persist(child);
			childs.add(child);
		}

		// 부모-자식 연결
		parent.setChildSet(childs);
		return "success";
	}

	@Override
	public List<String> getDayOff(short storeId) {
		List<String> resultList = queryFactory.selectDistinct(storeRestDaysMapEntity.date).from(storeRestDaysMapEntity)
				.leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity)
				.where(storeRestDaysEntity.storeId.eq(storeId)).orderBy(storeRestDaysMapEntity.date.asc()).fetch();
		// 정보 없음
		if (resultList == null | resultList.size() == 0) {
			throw new NoInformationException();
		}
		return resultList;
	}

	// Cascade로 설정
	@Override
	public String deleteDayOff(short storeId) {
		List<StoreRestDaysEntity> entities = queryFactory.select(storeRestDaysEntity).from(storeRestDaysEntity)
				.leftJoin(storeRestDaysEntity.childSet, storeRestDaysMapEntity).fetchJoin()
				.where(storeRestDaysEntity.storeId.eq(storeId)).fetch();
		for (StoreRestDaysEntity entity : entities) {
			for (StoreRestDaysMapEntity map : entity.getChildSet()) {
				queryFactory.delete(storeRestDaysMapEntity).where(storeRestDaysMapEntity.dayId.eq(map.getDayId()))
						.execute();
			}
			queryFactory.delete(storeRestDaysEntity).where(storeRestDaysEntity.daysId.eq(entity.getDaysId())).execute();
		}
		return "success";
	}

	@Override
	public String registerTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO) {

		// 부모 객체 생성
		StoreTimeInfoEntity parent = StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
				.endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
				.storeId(storeTimeInfoDTO.getStoreId()).build();

		// 부모 테이블 저장
		entityManager.persist(parent);

		// 자식들 컬렉션 생성
		Set<StoreTimeInfoMapEntity> childs = new HashSet<>();

		for (String time : storeTimeInfoDTO.getBreakTime()) {
			// 자식 객체 생성
			StoreTimeInfoMapEntity child = StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent)
					.build();

			// 자식 테이블 저장
			entityManager.persist(child);
			childs.add(child);
		}
		// 부모테이블에 자식테이블 저장
		parent.setBreakTime(childs);
		return "success";
	}

	@Override
	public StoreTimeInfoEntity getTimeInfo(short storeId) {

		// 엔터티 호출
		StoreTimeInfoEntity result = queryFactory.select(storeTimeInfoEntity).from(storeTimeInfoEntity)
				.leftJoin(storeTimeInfoEntity.breakTime, storeTimeInfoMapEntity).fetchJoin()
				.where(storeTimeInfoMapEntity.storeTimeInfoEntity.storeId.eq(storeId))
				.orderBy(storeTimeInfoMapEntity.time.asc()).fetchFirst();

		if (result == null) {
			throw new NoInformationException();
		}

		return result;
	}

	@Override
	public String modTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO) {

		// 부모 엔터티 수정
		short storeId = storeTimeInfoDTO.getStoreId();
		StoreTimeInfoEntity p_entity = queryFactory.select(storeTimeInfoEntity).from(storeTimeInfoEntity)
				.where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();

		// 정보 없음
		if (p_entity == null) {
			throw new NoInformationException();
		}

		// 자식 엔터티 삭제 및 추가
		queryFactory.delete(storeTimeInfoMapEntity).where(storeTimeInfoMapEntity.storeTimeInfoEntity.eq(p_entity))
				.execute();

		Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
		for (String breakTime : storeTimeInfoDTO.getBreakTime()) {
			StoreTimeInfoMapEntity child = StoreTimeInfoMapEntity.builder().time(breakTime)
					.storeTimeInfoEntity(p_entity).build();
			entityManager.persist(child);
			childs.add(child);
		}

		p_entity.setStartTime(storeTimeInfoDTO.getStartTime());
		p_entity.setEndTime(storeTimeInfoDTO.getEndTime());
		p_entity.setIntervalTime(storeTimeInfoDTO.getIntervalTime());
		p_entity.setBreakTime(childs);

		return "success";
	}

	// Cascade로 설정
	@Override
	public String deleteTimeInfo(short storeId) {
		// 시간 Id 반환
		int times_id = queryFactory.select(storeTimeInfoEntity.timesId).from(storeTimeInfoEntity)
				.where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
		// 시간 정보 삭제
		queryFactory.delete(storeTimeInfoMapEntity)
				.where(storeTimeInfoMapEntity.storeTimeInfoEntity.timesId.eq(times_id)).execute();
		queryFactory.delete(storeTimeInfoEntity).where(storeTimeInfoEntity.timesId.eq(times_id)).execute();
		return "success";
	}

	@Override
	public String registerTableInfo(StoreTableInfoEntity storeTableInfoEntity) {
		entityManager.persist(storeTableInfoEntity);
		return "success";
	}

	// 서브 쿼리로 설정하자 - (update - select)
	@Override
	public String modTableInfo(StoreTableInfoEntity newData) {
		StoreTableInfoEntity originalTable = queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity)
				.where(storeTableInfoEntity.storeId.eq(newData.getStoreId())).fetchFirst();
		originalTable.setCount(newData.getCount());
		originalTable.setTableImage(newData.getTableImage());
		return "success";
	}

	@Override
	public String deleteTableInfo(short storeId) {
		queryFactory.delete(storeTableInfoEntity).where(storeTableInfoEntity.storeId.eq(storeId)).execute();
		return "success";
	}

	@Override
	public String registerFoodsInfo(StoreFoodsInfoEntity storeFoodsInfoEntity) {
		entityManager.persist(storeFoodsInfoEntity);
		return "success";
	}

	@Override
	public List<StoreFoodsInfoEntity> getFoodsInfo(short storeId) {
		List<StoreFoodsInfoEntity> result = queryFactory.select(storeFoodsInfoEntity).from(storeFoodsInfoEntity)
				.where(storeFoodsInfoEntity.storeId.eq(storeId)).fetch();
		// 음식 데이터 없음
		if (result == null | result.size() == 0) {
			throw new NoInformationException();
		}
		return result;
	}

	// 서브 쿼리로 설정하자 - (update - select)
	@Override
	public String modFoodsInfo(StoreFoodsInfoEntity new_entity) {
		StoreFoodsInfoEntity origin_entity = queryFactory
				.select(storeFoodsInfoEntity).from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId
						.eq(new_entity.getStoreId()).and(storeFoodsInfoEntity.foodName.eq(new_entity.getFoodName())))
				.fetchFirst();
		// 기존 데이터 없음
		if (origin_entity == null) {
			throw new NoInformationException();
		}
		// 데이터 수정
		origin_entity.setFoodName(new_entity.getFoodName());
		origin_entity.setFoodDescription(new_entity.getFoodDescription());
		origin_entity.setFoodPrice(new_entity.getFoodPrice());
		origin_entity.setFoodImage(new_entity.getFoodImage());
		return "success";
	}

	@Override
	public String deleteFoodsInfo(short storeId, String foodName) {
		queryFactory.delete(storeFoodsInfoEntity)
				.where(storeFoodsInfoEntity.storeId.eq(storeId).and(storeFoodsInfoEntity.foodName.eq(foodName)))
				.execute();
		return "success";
	}
}
