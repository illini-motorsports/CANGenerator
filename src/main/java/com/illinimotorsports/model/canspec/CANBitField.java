package com.illinimotorsports.model.canspec;

/**
 * Class for a single bit in a bitmap of a can message
 */
public class CANBitField {

  private String name;
  private int position;

  /**
   * Constructor for bitfield
   * @param bitName
   * @param bitPosition
   */
  public CANBitField(String bitName, int bitPosition) {
    this.name = bitName;
    this.position = bitPosition;
  }

  /**
   * Getter for name
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for position
   * @return
   */
  public int getPosition() {
    return position;
  }
}
