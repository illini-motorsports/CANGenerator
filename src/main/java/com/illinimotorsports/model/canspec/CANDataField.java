package com.illinimotorsports.model.canspec;

public abstract class CANDataField {

  private String name;
  private int position;
  private int length;

  public CANDataField(String name, int pos, int len) {
    this.name = name;
    this.position = pos;
    this.length = len;
  }

  public String getName() {
    return name;
  }

  public int getPosition() {
    return position;
  }

  public int getLength() {
    return length;
  }
}
