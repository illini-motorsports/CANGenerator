package com.illinimotorsports.model.canspec;

/**
 * Simple constant value field
 */
public class CANConstField extends CANDataField {
  private int value;

  public CANConstField(int pos, int len, int value) {
    super(pos, len);
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
