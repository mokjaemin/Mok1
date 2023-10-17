//package com.ReservationServer1.Entity;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import com.ReservationServer1.data.Entity.POR.StoreOrdersEntity;
//import com.ReservationServer1.data.Entity.POR.StoreOrdersMapEntity;
//import com.ReservationServer1.data.Entity.POR.StorePayEntity;
//import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
//
//@ExtendWith(MockitoExtension.class)
//public class EqualsTest {
//
//
//  @Test
//  @DisplayName("Reservation 관련 모든 Entity equals 테스트")
//  void ReservationSystem() throws Exception {
//
//    // Data 1
//    StorePayEntity payment =
//        StorePayEntity.builder().paymentId(0L).comment("comment").bigComment("bigComment").build();
//
//    StoreOrdersMapEntity ordersMap =
//        StoreOrdersMapEntity.builder().orderId(0L).foodName("foodName").foodCount(0).build();
//
//    StoreOrdersEntity orders = StoreOrdersEntity.builder().ordersId(0L).build();
//
//    StoreReservationEntity reservation =
//        StoreReservationEntity.builder().reservationId(0L).storeId(0).userId("userId").storeId(0)
//            .date("date").time("time").storeTable("storeTable").build();
//
//
//    payment.setStoreOrdersEntity(orders);
//    orders.setPayment(payment);
//
//    ordersMap.setStoreOrdersEntity(orders);
//    List<StoreOrdersMapEntity> ordersMaps = new ArrayList<>();
//    ordersMaps.add(ordersMap);
//    orders.setChildSet(ordersMaps);
//
//    orders.setStoreReservationEntity(reservation);
//    reservation.setChild(orders);
//
//
//
//    // Data 2
//    StorePayEntity payment2 =
//        StorePayEntity.builder().paymentId(0L).comment("comment").bigComment("bigComment").build();
//
//    StoreOrdersMapEntity ordersMap2 =
//        StoreOrdersMapEntity.builder().orderId(0L).foodName("foodName").foodCount(0).build();
//
//    StoreOrdersEntity orders2 = StoreOrdersEntity.builder().ordersId(0L).build();
//
//    StoreReservationEntity reservation2 =
//        StoreReservationEntity.builder().reservationId(0L).storeId(0).userId("userId").storeId(0)
//            .date("date").time("time").storeTable("storeTable").build();
//
//
//    payment2.setStoreOrdersEntity(orders2);
//    orders2.setPayment(payment2);
//
//    ordersMap2.setStoreOrdersEntity(orders2);
//    List<StoreOrdersMapEntity> ordersMaps2 = new ArrayList<>();
//    ordersMaps2.add(ordersMap2);
//    orders2.setChildSet(ordersMaps2);
//
//    orders2.setStoreReservationEntity(reservation2);
//    reservation2.setChild(orders2);
//
//
//
//    // Equals Test
//    assertTrue(reservation.equals(reservation2));
//    assertTrue(orders.equals(orders2));
//    assertTrue(ordersMap.equals(ordersMap2));
//    assertTrue(payment.equals(payment2));
//
//  }
//
//}
