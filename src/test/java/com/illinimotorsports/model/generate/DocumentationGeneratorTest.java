package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANParseException;
import com.illinimotorsports.model.parse.CANParser;
import org.junit.*;

import java.io.File;
import java.util.List;

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
    List<String[]> data = generator.generateMessageTable();
    assertEquals(data.size(), spec.getMessages().size());
    assertEquals(data.get(0).length, generator.messageTableColumns.length);
  }

  @Test
  public void testGenerateFieldTable() {
    List<String[]> data = generator.generateFieldTable();
    assertEquals(data.get(0).length, generator.fieldTableColumns.length);
  }
}