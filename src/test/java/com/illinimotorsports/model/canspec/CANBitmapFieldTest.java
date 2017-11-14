package com.illinimotorsports.model.canspec;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CANBitmapFieldTest {

  List<String> strings;
  CANBitmapField bmap;

  @Before
  public void init() {
    strings = new ArrayList<>(Arrays.asList("ayo", "yo", "o"));
    bmap = new CANBitmapField(2, 2, "TestName", strings);
  }

  @Test
  public void testGetBits() throws Exception {
    assertEquals(bmap.getBits().size(), 3);
    assertEquals(bmap.getBits().get(1), "yo");
  }

  @Test
  public void testGetName() throws Exception {
    assertEquals(bmap.getName(), "TestName");
  }

}