package com.illinimotorsports.model.canspec;

import java.util.ArrayList;
import java.util.List;

/**
 * Bitmap message field in can spec.  Contains list of bit names
 */
public class CANBitmapField extends CANDataField {

  private List<CANBitField> bits;

  /**
   * Constructor for class, requires full list of bit names
   * @param pos
   * @param len
   * @param name
   * @param bits
   */
  public CANBitmapField(int pos, int len, String name, List<CANBitField> bits) {
    super(pos, len, name);
    this.bits = new ArrayList<>(bits);
  }

  /**
   * Returns full list of bit names
   * @return
   */
  public List<CANBitField> getBits() {
    return bits;
  }

  /**
   * Getter for all bit names in map
   * @return
   */
  public List<String> getBitNames() {
    List<String> bitNames = new ArrayList<>();
    for(CANBitField bit: bits) {
      bitNames.add(bit.getName());
    }
    return bitNames;
  }
}
