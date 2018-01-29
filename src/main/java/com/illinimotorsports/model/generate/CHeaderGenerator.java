package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.*;
import com.x5.template.Chunk;
import com.x5.template.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generator class for header files
 */
public class CHeaderGenerator implements CodeGenerator {

  CANSpec spec;

  public CHeaderGenerator(CANSpec canSpec) {
    spec = canSpec;
  }

  /**
   * Main template fill function
   * @return
   */
  public String generate() {
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
    // Iterate through every field from each message, call helper function
    List<Map<String, String>> fieldDefs = new ArrayList<>();
    spec.getMessages().stream().flatMap(x -> x.getData().stream()).forEach(x -> fieldDefs.addAll(x.generateCHeaderDefs()));

    return fieldDefs;
  }
}
