package com.illinimotorsports.model.parse;

import com.illinimotorsports.model.Endianness;

public class CANLoggedMessage implements Comparable {
  private double timestamp;
  private int id;
  private int[] bytes;

  public CANLoggedMessage(String[] csvRow) {
    timestamp = Double.parseDouble(csvRow[0]);
    id = Integer.parseInt(csvRow[2].substring(2), 16);
    int dlc = Integer.parseInt(csvRow[4]);
    bytes = new int[dlc];
    for(int i = 5; i < dlc + 5; i++) {
      bytes[i-5] = Integer.parseInt(csvRow[i], 16);
    }
  }

  public double getTimestamp() {
    return timestamp;
  }

  public int getId() {
    return id;
  }

  public double getField(int pos, int len, double scl, int offset, boolean signed, Endianness endianness) {
    if(pos + len >= bytes.length) {
      // TODO: Make custom exception for this error
      return 0;
    }
    // TODO: figure out how to deal with unsigned numbers
    return (getHexField(pos, len, endianness) - offset) * scl;
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
