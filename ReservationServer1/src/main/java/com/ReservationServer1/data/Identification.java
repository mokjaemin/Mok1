package com.ReservationServer1.data;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class Identification {
  
  protected String id;

  protected Identification() {
      this.id = UUID.randomUUID().toString();
  }

}
