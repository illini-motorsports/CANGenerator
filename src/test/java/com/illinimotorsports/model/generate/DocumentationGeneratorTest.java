package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANParseException;
import com.illinimotorsports.model.parse.CANParser;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.*;

public class DocumentationGeneratorTest {

  CANSpec spec;
  DocumentationGenerator generator;

  @Before
  public void init() {
    try {
      spec = CANParser.parseCanSpec(new File("src/main/resources/can_spec_test.json"));
    } catch (CANParseException e) {
      fail();
    }
    generator = new DocumentationGenerator(spec);
  }

  @Test
  public void testGenerateMessageTable() {
    String[][] data = generator.generateMessageTable();
    assertEquals(data.length, spec.getMessages().size());
    assertEquals(data[0].length, 11);
  }
}