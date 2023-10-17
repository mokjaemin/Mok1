//package com.ReservationServer1.Entity;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import com.ReservationServer1.data.Entity.POR.StoreOrdersEntity;
//import com.ReservationServer1.data.Entity.POR.StoreOrdersMapEntity;
//import com.ReservationServer1.data.Entity.POR.StorePayEntity;
//import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
//
//
//public class toStringTest {
//
//  @Test
//  @DisplayName("Reservation 관련 toStringTest")
//  void MemberDTOSample() throws Exception {
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
//    System.out.println(reservation);
//    System.out.println(orders);
//    System.out.println(ordersMap);
//    System.out.println(payment);
//    
//  }
//
//}
