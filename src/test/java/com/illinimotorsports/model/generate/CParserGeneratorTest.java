package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANParseException;
import com.illinimotorsports.model.parse.CANSpecParser;
import com.illinimotorsports.view.MessageCheckBoxView;
import org.junit.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CParserGeneratorTest {

  CANSpec spec;
  CParserGenerator generator;

  @Before
  public void init() {
    try {
      spec = CANSpecParser.parseCanSpec(new File("src/main/resources/can_spec_test.json"));
    } catch (CANParseException e) {
      fail();
    }
    generator = new CParserGenerator();
    generator.setData(MessageCheckBoxView.generateCheckBoxViews(spec));
  }

  @Test
  public void testGenerateFieldParseMap() {
    List<Map<String,String>> fieldParseMap = generator.generateFieldParseMap(spec.getMessages().get(0));
    assertNotNull(fieldParseMap);
    assertEquals(fieldParseMap.size(), 3);
    assertEquals(fieldParseMap.get(0).get("comment"), "Uptime (s)");
    assertEquals(fieldParseMap.get(0).get("pos"), "0");
  }

  @Test
  public void testGenerateMessageParseMap() {
    List<Map<String,String>> messageParseMap = generator.generateMessageParseMap();
    assertNotNull(messageParseMap);
    assertEquals(messageParseMap.size(), 3);
    assertEquals(messageParseMap.get(0).get("comment"), "PDM");
  }
}