package com.illinimotorsports.model;

import com.illinimotorsports.model.generate.TemplatedGenerator;

/**
 * Model for generated code window
 */
public class GeneratedCodeModel {
  private TemplatedGenerator generator;

  public GeneratedCodeModel(TemplatedGenerator templatedGenerator) {
    generator = templatedGenerator;
  }

  public String getCode() {
    return generator.fillTemplate();
  }
}
