package com.illinimotorsports.model.canspec;

import com.illinimotorsports.model.Endianness;
import static org.junit.Assert.*;
import org.junit.*;

public class CANMessageTest {

  CANMessage message;

  @Before
  public void initMessage() {
    message = new CANMessage(123, "PDM", Endianness.LITTLE, 8);
  }

  @Test
  public void testAddField() throws Exception {
    assertEquals(message.getData().size(), 0);
    message.addField(new CANNumericField(0,2,"ayy", "V", true, 1, 0));
    assertEquals(message.getData().size(), 1);
    message.addField(new CANNumericField(2,2,"ayy", "V", true, 1, 0));
    assertEquals(message.getData().size(), 2);
  }

  @Test
  public void testGetFieldNames() throws Exception {
    assertEquals(message.getFieldNames().size(), 0);
    message.addField(new CANNumericField(0,2,"ayy", "V", true, 1, 0));
    assertEquals(message.getFieldNames().size(), 0);
    message.addField(new CANNumericField(2,2,"ayy", "V", true, 1, 0));
    assertEquals(message.getFieldNames().size(), 1);
    assertEquals(message.getFieldNames().get(0), "ayy");
  }

  @Test
  public void testGetId() throws Exception {
    assertEquals(message.getId(), 123);
  }

  @Test
  public void testGetNode() throws Exception {
    assertEquals(message.getNode(), "PDM");
  }

  @Test
  public void testGetEndianness() throws Exception {
    assertEquals(message.getEndianness(), Endianness.LITTLE);
  }

  @Test
  public void testGetDlc() throws Exception {
    assertEquals(message.getDlc(), 8);
  }

  @Test
  public void testGetData() throws Exception {
    assertEquals(message.getData().size(), 0);
    message.addField(new CANNumericField(0,2,"ayy", "V", true, 1, 0));
    assertEquals(message.getData().size(), 1);
    message.addField(new CANNumericField(2,2,"ayy", "V", true, 1, 0));
    assertEquals(message.getData().size(), 2);
    assertTrue(message.getData().get(0) instanceof CANNumericField);
    assertTrue(message.getData().get(1) instanceof CANNumericField);
  }

}