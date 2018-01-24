package com.illinimotorsports.model.canspec;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CANBitmapFieldTest {

  List<CANBitField> bits;
  CANBitmapField bmap;

  @Before
  public void init() {
    bits = new ArrayList<>(Arrays.asList(
        new CANBitField("ayy", 0),
        new CANBitField("ososo", 1),
        new CANBitField("aymo", 4),
        new CANBitField("kjfdkjf", 5)));
    bmap = new CANBitmapField(2, 2, "TestName", "PDM", bits);
  }

  @Test
  public void testGetBits() throws Exception {
    assertEquals(bmap.getBits().size(), 4);
    assertEquals(bmap.getBits().get(1).getName(), "ososo");
  }

  @Test
  public void testGetName() throws Exception {
    assertEquals(bmap.getName(), "TestName");
  }

}