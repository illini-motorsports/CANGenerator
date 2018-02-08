package com.illinimotorsports.model;

import com.illinimotorsports.model.generate.CodeGenerator;

/**
 * Model for generated code window
 */
public class GeneratedCodeModel {
  private CodeGenerator generator;

  public GeneratedCodeModel(CodeGenerator codeGenerator) {
    generator = codeGenerator;
  }

  public String getCode() {
    return generator.generate(); // where tho
  }
}
