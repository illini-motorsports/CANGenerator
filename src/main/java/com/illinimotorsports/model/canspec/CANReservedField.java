package com.illinimotorsports.model.canspec;

/**
 * Not often used CAN field, more to fill in empty spaces in a message
 */
public class CANReservedField extends CANDataField {
  public CANReservedField(int pos, int len) {
    super(pos, len);
  }
}
