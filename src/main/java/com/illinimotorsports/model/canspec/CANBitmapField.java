package com.illinimotorsports.model.canspec;

import java.util.ArrayList;
import java.util.List;

public class CANBitmapField extends CANDataField {

  private String name;
  private List<String> bits;

  public CANBitmapField(int pos, int len, String name, List<String> bits) {
    super(pos, len);
    this.name = name;
    this.bits = new ArrayList<>(bits);
  }

  public List<String> getBits() {
    return bits;
  }

  public String getName() {
    return name;
  }
}
