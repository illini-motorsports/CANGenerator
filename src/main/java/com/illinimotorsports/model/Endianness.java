package com.illinimotorsports.model;

/**
 * Endianess enum
 */
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

  //TODO: Check if this is right
  public String toDBCString() {
    return this == BIG ? "1" : "0";
  }
}
