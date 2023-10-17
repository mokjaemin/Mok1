package com.ReservationServer1.data;

public enum StoreType {

  KOREAN("KOREAN"),
  JPANESE("JAPNESE"),
  CHINSESE("CHINESE"),
  VIETNAMESE("VIETNAMESE"),
  THAI("THAI"),
  WESTERN("WESTERN");
  
  private final String value;
  
  private StoreType(String value){
    this.value = value;
  }
  
  public String getValue() {
    return this.value;
  }
  
}
