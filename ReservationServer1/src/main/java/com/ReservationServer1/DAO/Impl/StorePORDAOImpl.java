package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.POR.QStoreCouponEntity.storeCouponEntity;
import static com.ReservationServer1.data.Entity.POR.QStoreOrdersEntity.storeOrdersEntity;
import static com.ReservationServer1.data.Entity.POR.QStoreOrdersMapEntity.storeOrdersMapEntity;
import static com.ReservationServer1.data.Entity.POR.QStorePayEntity.storePayEntity;
import static com.ReservationServer1.data.Entity.POR.QStoreReservationEntity.storeReservationEntity;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ReservationServer1.DAO.StorePORDAO;
import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.PayDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreCouponEntity;
import com.ReservationServer1.data.Entity.POR.StoreOrdersEntity;
import com.ReservationServer1.data.Entity.POR.StoreOrdersMapEntity;
import com.ReservationServer1.data.Entity.POR.StorePayEntity;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.ReservationServer1.exception.NoAuthorityException;
import com.ReservationServer1.exception.NoInformationException;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository("ReservationAndOrderDAO")
public class StorePORDAOImpl implements StorePORDAO {
	private final JPAQueryFactory queryFactory;
	private final EntityManager entityManager;

	public StorePORDAOImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.queryFactory = queryFactory;
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String registerReservation(ReservationDTO reservationDTO, String userId) {

		StoreReservationEntity reservation = new StoreReservationEntity(reservationDTO);
		reservation.setUserId(userId);
		entityManager.persist(reservation);
		return "success";
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String updateReservation(ReservationDTO reservationDTO, String userId) {

		StoreReservationEntity reservation = queryFactory.select(storeReservationEntity).from(storeReservationEntity)
				.where(storeReservationEntity.storeId.eq(reservationDTO.getStoreId())
						.and(storeReservationEntity.userId.eq(userId)))
				.fetchFirst();
		reservation.setDate(reservationDTO.getDate());
		reservation.setTime(reservationDTO.getTime());
		reservation.setStoreTable(reservationDTO.getStoreTable());
		return "success";
	}

	@Override
	@Transactional(timeout = 10, readOnly = true, rollbackFor = Exception.class)
	public StoreReservationEntity getReservation(short storeId, String userId) {

		StoreReservationEntity reservation = queryFactory.select(storeReservationEntity).from(storeReservationEntity)
				.leftJoin(storeReservationEntity.child, storeOrdersEntity).fetchJoin()
				.leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
				.leftJoin(storeOrdersEntity.ordersMap, storeOrdersMapEntity).fetchJoin()
				.where(storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)))
				.fetchFirst();

		if (reservation == null) {
			throw new NoInformationException();
		}
		return reservation;
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String deleteReservation(short storeId, String userId) {

		queryFactory.delete(storeReservationEntity)
				.where(storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)))
				.execute();
		return "success";
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String registerOrder(OrderDTO orderDTO, String userId) {

		Integer reservationId = queryFactory.select(storeReservationEntity.reservationId).from(storeReservationEntity)
				.where(storeReservationEntity.userId.eq(userId)
						.and(storeReservationEntity.storeId.eq(orderDTO.getStoreId())))
				.fetchFirst();

		if (reservationId == null) {
			throw new NoInformationException();
		}

		StoreReservationEntity reservation = StoreReservationEntity.builder().reservationId(reservationId).build();
		StoreOrdersEntity orders = new StoreOrdersEntity(reservation);
		entityManager.persist(orders);

		List<StoreOrdersMapEntity> ordersMapList = orderDTO.getOrderInfo().keySet().stream().map(foodName -> {
			StoreOrdersMapEntity ordersMap = new StoreOrdersMapEntity(foodName, orderDTO.getOrderInfo().get(foodName),
					orders);
			entityManager.persist(ordersMap);
			return ordersMap;
		}).toList();

		orders.setOrdersMap(ordersMapList);

		return "success";
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String updateOrder(OrderDTO orderDTO, String userId) {

		StoreReservationEntity reservation = queryFactory.select(storeReservationEntity).from(storeReservationEntity)
				.leftJoin(storeReservationEntity.child, storeOrdersEntity).fetchJoin()
				.leftJoin(storeOrdersEntity.ordersMap, storeOrdersMapEntity).fetchJoin()
				.where(storeReservationEntity.storeId.eq(orderDTO.getStoreId())
						.and(storeReservationEntity.userId.eq(userId)))
				.fetchFirst();

		if (reservation == null) {
			throw new NoInformationException();
		}

		reservation.getChild().getOrdersMap().forEach(ordersMap -> {
			orderDTO.getOrderInfo().keySet().stream().filter(foodName -> ordersMap.getFoodName().equals(foodName))
					.forEach(foodName -> {
						ordersMap.setFoodCount(orderDTO.getOrderInfo().get(foodName));
					});
		});

		return "success";
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String deleteOrder(short storeId, String foodName, String userId) {

		StoreReservationEntity reservation = queryFactory.select(storeReservationEntity).from(storeReservationEntity)
				.leftJoin(storeReservationEntity.child, storeOrdersEntity).fetchJoin()
				.leftJoin(storeOrdersEntity.ordersMap, storeOrdersMapEntity).fetchJoin()
				.where(storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)))
				.fetchFirst();

		if (reservation == null) {
			throw new NoInformationException();
		}

		queryFactory
				.delete(storeOrdersMapEntity).where(storeOrdersMapEntity.storeOrdersEntity.ordersId
						.eq(reservation.getChild().getOrdersId()).and(storeOrdersMapEntity.foodName.eq(foodName)))
				.execute();
		return "success";
	}

	// -- DTO로 받기 --
	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String registerPay(PayDTO payDTO) {

		short storeId = payDTO.getStoreId();

		StoreReservationEntity reservation = queryFactory.select(storeReservationEntity).from(storeReservationEntity)
				.leftJoin(storeReservationEntity.child, storeOrdersEntity)
				.where(storeReservationEntity.reservationId.eq(payDTO.getReservationId())).fetchFirst();

		if (reservation == null) {
			throw new NoInformationException();
		}

		StorePayEntity pay = StorePayEntity.builder().amount(payDTO.getAmount()).build();
		pay.setStoreOrdersEntity(StoreOrdersEntity.builder().ordersId(reservation.getChild().getOrdersId()).build());
		entityManager.persist(pay);

		StoreCouponEntity coupon = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
				.where(storeCouponEntity.userId.eq(reservation.getUserId()).and(storeCouponEntity.storeId.eq(storeId)))
				.fetchFirst();

		if (coupon == null) {
			StoreCouponEntity newCoupon = StoreCouponEntity.builder().storeId(storeId).userId(reservation.getUserId())
					.amount(1).build();
			entityManager.persist(newCoupon);
		} else {
			coupon.setAmount(coupon.getAmount() + 1);
		}

		return "success";
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String deletePay(int reservationId) {

		StoreReservationEntity reservation = queryFactory.select(storeReservationEntity).from(storeReservationEntity)
				.leftJoin(storeReservationEntity.child, storeOrdersEntity).fetchJoin()
				.leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
				.where(storeReservationEntity.reservationId.eq(reservationId)).fetchFirst();

		queryFactory.delete(storePayEntity)
				.where(storePayEntity.paymentId.eq(reservation.getChild().getPayment().getPaymentId())).execute();

		StoreCouponEntity coupon = queryFactory
				.select(storeCouponEntity).from(storeCouponEntity).where(storeCouponEntity.userId
						.eq(reservation.getUserId()).and(storeCouponEntity.storeId.eq(reservation.getStoreId())))
				.fetchFirst();
		coupon.setAmount(coupon.getAmount() - 1);
		return "success";
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String registerComment(int reservationId, String comment, String userId) {

		StoreReservationEntity reservation = queryFactory.select(storeReservationEntity).from(storeReservationEntity)
				.leftJoin(storeReservationEntity.child, storeOrdersEntity).fetchJoin()
				.leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
				.where(storeReservationEntity.reservationId.eq(reservationId)).fetchFirst();

		if (!userId.equals(reservation.getUserId())) {
			throw new NoAuthorityException();
		}

		StorePayEntity pay = reservation.getChild().getPayment();
		pay.setComment(comment);

		return "success";
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String deleteComment(int reservationId, String userId) {

		StoreReservationEntity reservation = queryFactory.select(storeReservationEntity).from(storeReservationEntity)
				.leftJoin(storeReservationEntity.child, storeOrdersEntity).fetchJoin()
				.leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
				.where(storeReservationEntity.reservationId.eq(reservationId)).fetchFirst();

		if (!userId.equals(reservation.getUserId())) {
			throw new NoAuthorityException();
		}

		StorePayEntity pay = reservation.getChild().getPayment();
		pay.setComment(null);

		return "success";
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String registerBigComment(int reservationId, String bigcomment, short storeId) {

		StoreReservationEntity reservation = queryFactory.select(storeReservationEntity).from(storeReservationEntity)
				.leftJoin(storeReservationEntity.child, storeOrdersEntity).fetchJoin()
				.leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
				.where(storeReservationEntity.reservationId.eq(reservationId)).fetchFirst();

		if (storeId != reservation.getStoreId()) {
			throw new NoAuthorityException();
		}

		StorePayEntity pay = reservation.getChild().getPayment();
		pay.setBigComment(bigcomment);

		return "success";
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String deleteBigComment(int reservationId, short storeId) {

		StoreReservationEntity reservation = queryFactory.select(storeReservationEntity).from(storeReservationEntity)
				.leftJoin(storeReservationEntity.child, storeOrdersEntity).fetchJoin()
				.leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
				.where(storeReservationEntity.reservationId.eq(reservationId)).fetchFirst();

		if (storeId != reservation.getStoreId()) {
			throw new NoAuthorityException();
		}

		StorePayEntity pay = reservation.getChild().getPayment();
		pay.setBigComment(null);

		return "success";
	}

	@Override
	@Transactional(timeout = 10, readOnly = true, rollbackFor = Exception.class)
	public int getCouponOfClient(short storeId, String userId) {

		int couponAmount = queryFactory.select(storeCouponEntity.amount).from(storeCouponEntity)
				.where(storeCouponEntity.storeId.eq(storeId).and(storeCouponEntity.userId.eq(userId))).fetchFirst();

		return couponAmount;
	}

	@Override
	@Transactional(timeout = 10, readOnly = true, rollbackFor = Exception.class)
	public HashMap<String, Integer> getCouponOfStore(short storeId) {

		List<StoreCouponEntity> couponList = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
				.where(storeCouponEntity.storeId.eq(storeId)).fetch();

		if (couponList.size() == 0 | couponList == null) {
			throw new NoInformationException();
		}

		HashMap<String, Integer> result = couponList.stream().collect(Collectors.toMap(StoreCouponEntity::getUserId,
				StoreCouponEntity::getAmount, (existing, replacement) -> existing, HashMap::new));
		return result;
	}
}
