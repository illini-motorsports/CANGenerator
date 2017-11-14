package com.illinimotorsports.model.canspec;

import java.util.ArrayList;
import java.util.List;

public class CANBitmapField extends CANDataField {

  private List<String> bits;

  public CANBitmapField(String name, int pos, int len, List<String> bits) {
    super(name, pos, len);
    this.bits = new ArrayList<>(bits);
  }

  public List<String> getBits() {
    return bits;
  }
}
