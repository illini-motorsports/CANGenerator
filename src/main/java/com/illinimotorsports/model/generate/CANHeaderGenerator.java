package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.*;
import com.x5.template.Chunk;
import com.x5.template.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CANHeaderGenerator {
  Map<String, List<CANMessage>> nodeMap;

  public CANHeaderGenerator(CANSpec canSpec) {
    generateNodeMap(canSpec);
  }

  private Map<String, List<CANMessage>> generateNodeMap(CANSpec spec) {
    nodeMap = new HashMap<>();
    for(CANMessage message: spec.getMessages()) {
      String node = message.getNode();
      List<CANMessage> nodeList = nodeMap.containsKey(node) ? nodeMap.get(node) : new ArrayList<>();
      nodeList.add(message);
      nodeMap.put(node, nodeList);
    }
    return nodeMap;
  }

  public String fillTemplate() {
    Theme theme = new Theme();

    Chunk header = theme.makeChunk("header", "h");

    header.set("ids", generateIDs());
    header.set("fieldDefs", generateFieldDefs());

    return header.toString();
  }

  public List<Map<String, String>> generateIDs() {
    List<Map<String, String>> canIDs = new ArrayList<>();
    for(Map.Entry<String, List<CANMessage>> entry: nodeMap.entrySet()) {
      List<CANMessage> messages = entry.getValue();
      for(int i = 0; i < messages.size(); i++) {
        String id = messages.get(i).getNode().toUpperCase() + "_" + i + "_ID";
        String hexID = "0x" + Integer.toHexString(messages.get(i).getId());
        Map<String, String> idMap = new HashMap<>();
        idMap.put("def", id);
        idMap.put("id", hexID);
        canIDs.add(idMap);
      }
    }
    return canIDs;
  }

  public List<Map<String, String>> generateFieldDefs() {
    List<Map<String, String>> fieldDefs = new ArrayList<>();
    for(Map.Entry<String, List<CANMessage>> entry: nodeMap.entrySet()) {
      List<CANMessage> messages = entry.getValue();
      for (CANMessage message : messages) {
        for (CANDataField field : message.getData()) {
          fieldDefs.addAll(generateDefsFromField(message.getNode(), field));
        }
      }
    }
    return fieldDefs;
  }

  public List<Map<String, String>> generateDefsFromField(String message, CANDataField field) {
    List<Map<String, String>> fieldDefs = new ArrayList<>();
    if (field instanceof CANNumericField) {
      CANNumericField numField = (CANNumericField) field;
      String genericDef = message.toUpperCase() + "_"
          + numField.getName().toUpperCase().replace(' ', '_');
      String byteNum = Integer.toString(numField.getPosition());
      String scl = Double.toString(numField.getScale());
      String off = Double.toString(numField.getOffset());

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

      List<String> bits = bitField.getBits();
      for(int i = 0; i < bits.size(); i++) {
        if(!bits.get(i).equals("Reserved")) {
          String bitDef = genericDef + "_"
              + bits.get(i).toUpperCase().replace(' ', '_') + "_BIT";
          String bitPos = Integer.toString(i);
          Map<String, String> bitMap = new HashMap<>();
          bitMap.put("def", bitDef);
          bitMap.put("value", bitPos);
          fieldDefs.add(bitMap);
        }
      }
    }
    //TODO: generate defs for nibbles
    return fieldDefs;
  }
}
