package com.illinimotorsports.model.canspec;

/**
 * Main abstract class for CAN Data
 * Many possible field types, such as numeric, bitmap, const, etc
 * Important for defining abstract code generator function definitions
 */
public abstract class CANDataField {

  private int position;
  private int length;
  private String name;

  /**
   * Only contains position and length,
   * since those are the only two fields common between
   * all of the data fields
   * @param pos
   * @param len
   */
  public CANDataField(int pos, int len, String fieldName) {
    this.position = pos;
    this.length = len;
    this.name = fieldName;
  }

  public int getPosition() {
    return position;
  }

  public int getLength() {
    return length;
  }

  public String getName() {
    return name;
  }
}
