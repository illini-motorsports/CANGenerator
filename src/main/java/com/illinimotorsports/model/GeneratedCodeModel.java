package com.illinimotorsports.model;

import com.illinimotorsports.model.generate.SelectedMessagesGenerator;

/**
 * Model for generated code window
 */
public class GeneratedCodeModel {
  private String code;

  public GeneratedCodeModel(SelectedMessagesGenerator generator) {
    code = generator.fillTemplate();
  }

  public GeneratedCodeModel(String codeString) {
    code = codeString;
  }

  public String getCode() {
    return code;
  }
}
