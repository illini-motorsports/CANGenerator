package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANParseException;
import com.illinimotorsports.model.parse.CANParser;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.*;

public class DBCGeneratorTest {

  CANSpec spec;
  DBCGenerator generator;

  @Before
  public void init() {
    try {
      spec = CANParser.parseCanSpec(new File("src/main/resources/can_spec_test.json"));
    } catch (CANParseException e) {
      fail();
    }
    generator = new DBCGenerator(spec);
  }

  @Test
  public void testFillTemplate() {
    String dbc = generator.fillTemplate();
    assertEquals(dbc.length(), 1171);
  }
}