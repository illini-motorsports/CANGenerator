package com.illinimotorsports.model.canspec;

public class CANConstField extends CANDataField {
  private int value;

  public CANConstField(String name, int pos, int len, int value) {
    super(name, pos, len);
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
