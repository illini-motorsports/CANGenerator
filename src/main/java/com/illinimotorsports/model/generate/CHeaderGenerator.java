package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.*;
import com.x5.template.Chunk;
import com.x5.template.Theme;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generator class for header files
 */
public class CHeaderGenerator implements TemplatedGenerator {

  CANSpec spec;

  public CHeaderGenerator(CANSpec canSpec) {
    spec = canSpec;
  }

  /**
   * Main template fill function
   * @return
   */
  public String fillTemplate() {
    Theme theme = new Theme();

    Chunk header = theme.makeChunk("header", "h");

    // Populate template with the lists of maps
    // The template library will automatically iterate through this datatype
    header.set("ids", generateIDs());
    header.set("fieldDefs", generateFieldDefs());

    return header.toString();
  }

  /**
   * Generator function for message ID's
   * @return
   */
  public List<Map<String, String>> generateIDs() {
    // Return datatype is a list of maps
    List<Map<String, String>> canIDList = new ArrayList<>();

    // Compute message ID names
    Map<CANMessage, String> canIDs = MessageIDUtils.generateIDNames(spec);
    for(Map.Entry<CANMessage, String> entry: canIDs.entrySet()) {
      Map<String, String> idMap = new HashMap<>();
      // Add define name and hex ID to inner map
      String hexID = "0x" + Integer.toHexString(entry.getKey().getId());
      idMap.put("def", entry.getValue() + "_ID");
      idMap.put("id", hexID);

      // Add to outer list
      canIDList.add(idMap);
    }
    return canIDList;
  }

  /**
   * Generates main list of maps for all field definitions
   * @return
   */
  public List<Map<String, String>> generateFieldDefs() {
    List<Map<String, String>> fieldDefs = new ArrayList<>();

    // Iterate through every field from each message, call helper function
    for(CANMessage message: spec.getMessages()) {
      for (CANDataField field : message.getData()) {
        fieldDefs.addAll(generateDefsFromField(message.getNode(), field));
      }
    }
    return fieldDefs;
  }

  /**
   * Generates a list for individual fields
   * @param message
   * @param field
   * @return
   */
  //TODO: move the meat of this function to CANDataField
  public List<Map<String, String>> generateDefsFromField(String message, CANDataField field) {
    List<Map<String, String>> fieldDefs = new ArrayList<>();
    if (field instanceof CANNumericField) {
      DecimalFormat df = new DecimalFormat("#");
      df.setMaximumFractionDigits(20);
      CANNumericField numField = (CANNumericField) field;
      String genericDef = message.toUpperCase() + "_"
          + numField.getName().toUpperCase().replace(' ', '_');
      String byteNum = Integer.toString(numField.getPosition());
      String scl = df.format(numField.getScale());
      String off = "0x" + Integer.toHexString(numField.getOffset());

      Map<String, String> byteDef = new HashMap<>();
      byteDef.put("def", genericDef + "_BYTE");
      byteDef.put("value", byteNum);
      Map<String, String> sclDef = new HashMap<>();
      sclDef.put("def", genericDef + "_SCL");
      sclDef.put("value", scl);
      Map<String, String> offDef = new HashMap<>();
      offDef.put("def", genericDef + "_OFF");
      offDef.put("value", off);

      fieldDefs.add(byteDef);
      fieldDefs.add(sclDef);
      fieldDefs.add(offDef);
    }
    else if(field instanceof CANBitmapField) {
      CANBitmapField bitField = (CANBitmapField) field;
      String genericDef = message.toUpperCase() + "_"
          + bitField.getName().toUpperCase().replace(' ', '_');
      String byteNum = Integer.toString(bitField.getPosition());

      Map<String, String> byteDef = new HashMap<>();
      byteDef.put("def", genericDef + "_BYTE");
      byteDef.put("value", byteNum);
      fieldDefs.add(byteDef);

      List<CANBitField> bits = bitField.getBits();
      for(CANBitField bit: bits) {
        String bitDef = genericDef + "_"
            + bit.getName().toUpperCase().replace(' ', '_')
            + "_BIT";
        String bitPos = Integer.toString(bit.getPosition());
        Map<String, String> bitMap = new HashMap<>();
        bitMap.put("def", bitDef);
        bitMap.put("value", bitPos);
        fieldDefs.add(bitMap);
      }
    }
    return fieldDefs;
  }
}