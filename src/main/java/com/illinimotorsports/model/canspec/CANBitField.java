package com.illinimotorsports.model.canspec;

public class CANBitField {

  private String name;
  private int position;

  public CANBitField(String bitName, int bitPosition) {
    this.name = bitName;
    this.position = bitPosition;
  }

  public String getName() {
    return name;
  }

  public int getPosition() {
    return position;
  }
}
