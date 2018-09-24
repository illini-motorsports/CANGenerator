package com.illinimotorsports.model.canspec;

import com.illinimotorsports.model.Endianness;
import org.junit.*;
import static org.junit.Assert.*;

public class CANNumericFieldTest {

  CANNumericField numeral;

  @Before
  public void init() {
    numeral = new CANNumericField(2, 2,
        "coolNumber", "PDM", 0x7c, "C", true, 1, 0, Endianness.BIG);
  }

  @Test
  public void testGetUnit() throws Exception {
    assertEquals(numeral.getUnit(), "C");
  }

  @Test
  public void testIsSigned() throws Exception {
    assertTrue(numeral.isSigned());
  }

  @Test
  public void testGetScale() throws Exception {
    assertEquals(numeral.getScale(), 1, 0);
  }

  @Test
  public void testGetOffset() throws Exception {
    assertEquals(numeral.getOffset(), 0, 0);
  }

  @Test
  public void testGetName() throws Exception {
    assertEquals(numeral.getName(), "coolNumber");
  }

}