package com.illinimotorsports.model;

import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANParseException;
import com.illinimotorsports.model.parse.CANParser;

import java.io.File;

/**
 * Top level model class to hold can spec
 */
public class GeneratorModel {
  private CANSpec spec;

  public GeneratorModel() {
    spec = null;
  }

  /**
   * Generates model from file, returns 0 on success
   * @param file
   * @return
   */
  public void generateModel(File file) throws CANParseException {
    spec = CANParser.parseCanSpec(file);
  }

  public CANSpec getCanSpec() {
    return spec;
  }
}
