package com.illinimotorsports.model.parse;

import com.illinimotorsports.model.Endianness;
import com.illinimotorsports.model.canspec.CANDataField;
import com.illinimotorsports.model.canspec.CANNumericField;

public class CANLoggedMessage implements Comparable {
  private double timestamp;
  private int id;
  private int[] bytes;

  public CANLoggedMessage(String[] csvRow) {
    try {
      timestamp = Double.parseDouble(csvRow[0]);
      id = Integer.parseInt(csvRow[2], 16);
      int dlc = Integer.parseInt(csvRow[4]);
      bytes = new int[dlc];
      for (int i = 5; i < dlc + 5; i++) {
        bytes[i - 5] = Integer.parseInt(csvRow[i], 16);
      }
    } catch (NumberFormatException e) {
      //TODO: do something here
    }
  }

  public double getTimestamp() {
    return timestamp;
  }

  public int getId() {
    return id;
  }

  // false for 0, true for 1
  public boolean getBit(int pos) {
    if(pos >= bytes.length*8) {
      // TODO: Make custom exception for this error
      return false;
    }
    int bytePos = pos/8;
    int bitPos = pos%8;

    return ((bytes[bytePos] >> bitPos) & 0x01) == 1;
  }

  public double getField(CANDataField field) {
    if(field instanceof CANNumericField) {
      return getNumericField((CANNumericField) field);
    }
    //TODO: handle bitmaps
    return 0;
  }

  public double getNumericField(CANNumericField field) {
    if(field.getPosition() + field.getLength() >= bytes.length) {
      // TODO: Make custom exception for this error
      return 0;
    }
    // TODO: figure out how to deal with unsigned numbers
    return (getHexField(field.getPosition(), field.getPosition(), field.getEndianness()) - field.getOffset()) * field.getScale();
  }

  private long getHexField(int pos, int len, Endianness endianness) {
    long hexOut = 0;
    // TODO: Make sure this is right
    if(endianness == Endianness.BIG) {
      for(int i = 0; i < len; i++) {
        hexOut = hexOut | (bytes[pos + i] << (8*i));
      }
    } else {
      for(int i = 0; i < len; i++) {
        hexOut = hexOut | (bytes[pos + len - i - 1] << (8*i));
      }
    }
    return hexOut;
  }

  @Override
  public int compareTo(Object o) {
    if(o instanceof CANLoggedMessage) {
      CANLoggedMessage other = (CANLoggedMessage) o;
      return (int) ((getTimestamp() - other.getTimestamp())*1000000);
    }
    return -1;
  }
}
