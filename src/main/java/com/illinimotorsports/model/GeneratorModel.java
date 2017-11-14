package com.illinimotorsports.model;

import java.io.File;

public class GeneratorModel {
  private CANParser parser;

  public GeneratorModel() {
    parser = new CANParser();
  }

  public CANParser getParser() {
    return parser;
  }
}
