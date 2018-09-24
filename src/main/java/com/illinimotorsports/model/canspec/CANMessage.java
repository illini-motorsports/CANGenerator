package com.illinimotorsports.model.canspec;

import com.illinimotorsports.model.Endianness;

import java.util.ArrayList;
import java.util.List;

/**
 * CAN Message class, contains all descriptive fields for a message,
 * and a list of CANDataFields
 */
public class CANMessage {

  private int id;
  private String node;
  private int dlc;
  private List<CANDataField> data;

  /**
   * Constructor for all primitive fields
   * @param id
   * @param node
   * @param dlc
   */
  public CANMessage(int id, String node, int dlc) {
    this.id = id;
    this.node = node;
    this.dlc = dlc;
    this.data = new ArrayList<>();
  }

  /**
   * Add field to list, so the message can be built up while being parsed
   * @param field
   */
  public void addField(CANDataField field) {
    data.add(field);
  }

  /**
   * Iterate through all fields in message, put field names in list.
   * Not all fields have the same format for names,
   * so instanceof calls need to be made
   * @return
   */
  public List<String> getFieldNames() {
    List<String> names = new ArrayList<>();
    for(CANDataField field: data) {
      if(field instanceof CANNumericField) {
        names.add(field.getName());
      }
      else if(field instanceof CANBitmapField) {
        names.addAll(((CANBitmapField) field).getBitNames());
      }
    }
    return names;
  }

  public int getId() {
    return id;
  }

  public String getNode() {
    return node;
  }

  public int getDlc() {
    return dlc;
  }

  public List<CANDataField> getData() {
    return data;
  }

  @Override
  public String toString() {
    return getNode();
  }

}
