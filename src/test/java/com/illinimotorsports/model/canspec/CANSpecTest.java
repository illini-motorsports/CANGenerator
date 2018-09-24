package com.illinimotorsports.model.canspec;

import com.illinimotorsports.model.Endianness;
import org.junit.*;
import static org.junit.Assert.*;

public class CANSpecTest {

  CANSpec spec;

  @Before
  public void init() {
    spec = new CANSpec("0.1");
  }

  @Test
  public void testAddMessage() throws Exception {
    assertEquals(spec.getMessages().size(), 0);
    spec.addMessage(new CANMessage(123, "PDM", 8));
    assertEquals(spec.getMessages().size(), 1);
  }

  @Test
  public void testGetMessages() throws Exception {
    spec.addMessage(new CANMessage(123, "PDM", 8));
    assertEquals(spec.getMessages().get(0).getNode(), "PDM");
  }

  @Test
  public void testGetMessagesWithFields() throws Exception {
    CANMessage message = new CANMessage(123, "PDM", 8);
    message.addField(new CANNumericField(0, 2, "Current","PDM", 0x7b, "A", true, 1, 0, Endianness.LITTLE));
    spec.addMessage(message);
    assertEquals(spec.getMessagesWithFields().get(0).size(), 2);
    assertEquals(spec.getMessagesWithFields().get(0).get(0), "PDM: 0x7b");
    assertEquals(spec.getMessagesWithFields().get(0).get(1), "Current");
  }

}