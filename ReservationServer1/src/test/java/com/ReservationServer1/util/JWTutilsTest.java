package com.ReservationServer1.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ReservationServer1.utils.JWTutil;

@ExtendWith(MockitoExtension.class)
public class JWTutilsTest {

  @Test
  @DisplayName("MemberEntity : toMemberDTO")
  void JWTutilConstructorTest() throws Exception {
    JWTutil jwt = new JWTutil();

    assertTrue(jwt != null);
  }

}
