package com.illinimotorsports.model.canspec;

import org.junit.*;
import static org.junit.Assert.*;

public class CANNibbleFieldTest {

  CANNibbleField nibble;

  @Before
  public void init() {
    nibble = new CANNibbleField(2, "msbName",
        "lsbName", "V", "A",
        true, false, 1,
        2, 0, 1);
  }

  @Test
  public void testGetMsbName() throws Exception {
    assertEquals(nibble.getMsbName(), "msbName");
  }

  @Test
  public void testGetLsbName() throws Exception {
    assertEquals(nibble.getLsbName(), "lsbName");
  }

  @Test
  public void testGetMsbUnit() throws Exception {
    assertEquals(nibble.getMsbUnit(), "V");
  }

  @Test
  public void testGetLsbUnit() throws Exception {
    assertEquals(nibble.getLsbUnit(), "A");
  }

  @Test
  public void testIsMsbSigned() throws Exception {
    assertTrue(nibble.isMsbSigned());
  }

  @Test
  public void testIsLsbSigned() throws Exception {
    assertFalse(nibble.isLsbSigned());
  }

  @Test
  public void testGetMsbScale() throws Exception {
    assertEquals(nibble.getMsbScale(), 1, 0);
  }

  @Test
  public void testGetLsbScale() throws Exception {
    assertEquals(nibble.getLsbScale(), 2, 0);
  }

  @Test
  public void testGetMsbOffset() throws Exception {
    assertEquals(nibble.getMsbOffset(), 0, 0);
  }

  @Test
  public void testGetLsbOffset() throws Exception {
    assertEquals(nibble.getLsbOffset(), 1, 0);
  }

}