package com.illinimotorsports.model.canspec;

import org.junit.*;
import static org.junit.Assert.*;

public class CANConstFieldTest {

  @Test
  public void testGetValue() throws Exception {
    CANConstField constField = new CANConstField(2, 2, 544);
    assertEquals(constField.getValue(), 544);
  }
}