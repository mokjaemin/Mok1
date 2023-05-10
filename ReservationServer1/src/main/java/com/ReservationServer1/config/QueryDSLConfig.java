package com.ReservationServer1.config;


import org.springframework.context.annotation.Configuration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



@Configuration
public class QueryDSLConfig {

  @PersistenceContext
  private EntityManager entityManager;

//  @Bean
//  public JPAQueryFactory jpaQueryFactory() {
//      return new JPAQueryFactory(entityManager);
//  }

}