package com.illinimotorsports.model;

import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANParser;

import java.io.File;

public class GeneratorModel {
  private CANSpec spec;

  public GeneratorModel() {
    spec = null;
  }

  public int generateModel(File file) {
    spec = CANParser.parseCanSpec(file);
    if(spec == null) {
      return 1; // failure;
    }
    return 0;
  }

  public CANSpec getCanSpec() {
    return spec;
  }
}
