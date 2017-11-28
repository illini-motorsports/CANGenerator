package com.illinimotorsports.model;

/**
 * Endianess enum
 */
//TODO: add more useful functions
public enum Endianness {
  BIG ("BIG"),
  LITTLE ("LITTLE");

  private String strVal;

  Endianness(String val) {
    this.strVal = val;
  }

  @Override
  public String toString() {
    return strVal;
  }
}
