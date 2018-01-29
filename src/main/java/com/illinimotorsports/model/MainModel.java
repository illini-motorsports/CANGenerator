package com.illinimotorsports.model;

import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANParseException;
import com.illinimotorsports.model.parse.CANSpecParser;

import java.io.File;

/**
 * Top level model class to hold can spec
 */
public class MainModel {
  private CANSpec spec;

  public MainModel() {
    spec = null;
  }

  /**
   * Generates model from file, returns 0 on success
   * @param file
   * @return
   */
  public void generateModel(File file) throws CANParseException {
    spec = CANSpecParser.parseCanSpec(file);
  }

  public CANSpec getCanSpec() {
    return spec;
  }
}
