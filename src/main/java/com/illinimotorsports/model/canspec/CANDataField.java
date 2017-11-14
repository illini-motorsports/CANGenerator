package com.illinimotorsports.model.canspec;

public abstract class CANDataField {

  private int position;
  private int length;

  public CANDataField(int pos, int len) {
    this.position = pos;
    this.length = len;
  }

  public int getPosition() {
    return position;
  }

  public int getLength() {
    return length;
  }
}
