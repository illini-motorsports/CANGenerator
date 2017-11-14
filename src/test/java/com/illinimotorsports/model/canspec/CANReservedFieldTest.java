package com.illinimotorsports.model.canspec;

import org.junit.*;
import static org.junit.Assert.*;

public class CANReservedFieldTest {

  CANReservedField reserved;

  @Before
  public void init() {
    reserved = new CANReservedField(2, 1);
  }

  @Test
  public void testGetPosition() {
    assertEquals(reserved.getPosition(), 2);
  }

  @Test
  public void testGetLength() {
    assertEquals(reserved.getLength(), 1);
  }
}