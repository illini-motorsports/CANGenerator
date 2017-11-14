package com.illinimotorsports.model.canspec;

import com.illinimotorsports.model.Endianness;

import java.util.ArrayList;
import java.util.List;

public class CANMessage {

  private int id;
  private String node;
  private Endianness endianness;
  private int dlc;
  private List<CANDataField> data;

  public CANMessage() {
    id = 0;
    node = "";
    endianness = Endianness.BIG;
    dlc = 0;
    data = new ArrayList<>();
  }

  public CANMessage(int id, String node, Endianness endianness, int dlc) {
    this.id = id;
    this.node = node;
    this.endianness = endianness;
    this.dlc = dlc;
    this.data = new ArrayList<>();
  }

  public void addField(CANDataField field) {
    data.add(field);
  }

  public int getId() {
    return id;
  }

  public String getNode() {
    return node;
  }

  public Endianness getEndianness() {
    return endianness;
  }

  public int getDlc() {
    return dlc;
  }

  public List<CANDataField> getData() {
    return data;
  }
}
