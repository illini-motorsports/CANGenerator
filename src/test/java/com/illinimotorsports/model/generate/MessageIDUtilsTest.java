package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANDataField;
import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANParseException;
import com.illinimotorsports.model.parse.CANParser;
import org.junit.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MessageIDUtilsTest {

  CANSpec spec;

  @Before
  public void init() {
    try {
      spec = CANParser.parseCanSpec(new File("src/main/resources/can_spec_test.json"));
    } catch (CANParseException e) {
      fail();
    }
  }

  @Test
  public void testGenerateNodeMap() {
    Map<String, List<CANMessage>> nodeMap = MessageIDUtils.generateNodeMap(spec);
    assertNotNull(nodeMap);
    assertEquals(nodeMap.size(), 1);
    assertTrue(nodeMap.containsKey("PDM"));
    assertEquals(nodeMap.get("PDM").size(), 3);
  }

  @Test
  public void testGenerateIDNames() {
    Map<CANMessage, String> nodeMap = MessageIDUtils.generateIDNames(spec);
    assertNotNull(nodeMap);
    assertEquals(nodeMap.size(), 3);
    assertTrue(nodeMap.values().contains("PDM_0"));
  }
}