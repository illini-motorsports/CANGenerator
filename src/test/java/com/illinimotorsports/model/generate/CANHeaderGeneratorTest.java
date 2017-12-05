package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANParseException;
import com.illinimotorsports.model.parse.CANParser;
import org.junit.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CANHeaderGeneratorTest {

  CANSpec spec;
  CANHeaderGenerator generator;

  @Before
  public void init() {
    try {
      spec = CANParser.parseCanSpec(new File("src/main/resources/can_spec_test.json"));
    } catch (CANParseException e) {
      fail();
    }
    generator = new CANHeaderGenerator(spec);
  }

  @Test
  public void testGenerateIDs() throws Exception {
    List<Map<String, String>> ids = generator.generateIDs();
    assertNotNull(ids);
    assertEquals(ids.size(), 3);
    assertEquals(ids.get(0).get("def"), "PDM_0_ID");
    assertEquals(ids.get(0).get("id"), "0x600");
  }

  @Test
  public void testGenerateFieldDefs() throws Exception {
    List<Map<String, String>> fields = generator.generateFieldDefs();
    assertNotNull(fields);
    assertEquals(fields.size(), 33);
    assertEquals(fields.get(0).get("def"), "PDM_UPTIME_BYTE");
    assertEquals(fields.get(0).get("value"), "0");
  }

  @Test
  public void testGenerateDefsFromField() throws Exception {
    CANMessage message = spec.getMessages().get(0);
    List<Map<String, String>> field = generator.generateDefsFromField(message.getNode(), message.getData().get(1));
    assertNotNull(field);
    assertEquals(field.size(), 3);
    assertEquals(field.get(0).get("def"), "PDM_PCB_TEMPERATURE_BYTE");
    assertEquals(field.get(0).get("value"), "2");
  }

}