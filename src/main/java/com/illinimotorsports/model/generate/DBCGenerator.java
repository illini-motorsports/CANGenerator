package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.Endianness;
import com.illinimotorsports.model.canspec.*;
import com.x5.template.Chunk;
import com.x5.template.Theme;

import java.text.DecimalFormat;
import java.util.*;


public class DBCGenerator {

  CANSpec spec;

  public DBCGenerator(CANSpec canSpec) {
    spec = canSpec;
  }

  public String fillTemplate() {
    Theme theme = new Theme();

    Chunk config = theme.makeChunk("config", "dbc");

    config.set("nodes", generateNodeDefs());
    config.set("messages", generateMessageDefs());

    return config.toString();
  }

  private List<Map<String, String>> generateNodeDefs() {
    List<Map<String, String>> nodeDefs = new ArrayList<>();
    Set<String> nodes = new HashSet<>();
    for(CANMessage message: spec.getMessages()) {
      nodes.add(message.getNode());
    }
    for(String node: nodes) {
      Map<String, String> nodeMap = new HashMap<>();
      nodeMap.put("node", node);
      nodeDefs.add(nodeMap);
    }
    return nodeDefs;
  }

  private List<Map<String, String>> generateMessageDefs() {
    List<Map<String, String>> messageDefs = new ArrayList<>();
    Map<CANMessage, String> ids = MessageIDUtils.generateIDNames(spec);
    for(Map.Entry<CANMessage, String> entry: ids.entrySet()) {
      Map<String, String> messageDef = new HashMap<>();
      messageDef.put("id", Integer.toString(entry.getKey().getId()));
      messageDef.put("name", entry.getValue());
      messageDef.put("dlc", Integer.toString(entry.getKey().getDlc()));
      messageDef.put("origin", entry.getKey().getNode());
      messageDef.put("fields", fillSubTemplate(entry.getKey()));
      messageDefs.add(messageDef);
    }
    return messageDefs;
  }

  private String fillSubTemplate(CANMessage message) {
    Theme theme = new Theme();

    Chunk subConfig = theme.makeChunk("subConfig", "dbc");

    subConfig.set("fields", generateFieldDefs(message));

    return subConfig.toString();
  }

  private List<Map<String, String>> generateFieldDefs(CANMessage message) {
    List<Map<String, String>> fields = new ArrayList<>();
    String endianness = message.getEndianness() == Endianness.BIG ? "1" : "0";
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(20);
    for(CANDataField field: message.getData()) {
      if(field instanceof CANNumericField) {
        CANNumericField numericField = (CANNumericField) field;
        Map<String, String> fieldMap = new HashMap<>();
        String fieldName = message.getNode().toUpperCase() + "_" + numericField.getName().toUpperCase().replace(' ', '_');
        fieldMap.put("fieldName", fieldName);
        fieldMap.put("position", Integer.toString(numericField.getPosition() * 8));
        fieldMap.put("length", Integer.toString(numericField.getLength()*8));
        fieldMap.put("endianness", endianness);
        fieldMap.put("signed", numericField.isSigned() ? "-" : "+");
        fieldMap.put("scl", df.format(numericField.getScale()));
        fieldMap.put("offset", Integer.toString(numericField.getOffset()));
        fieldMap.put("unit", numericField.getUnit());
        fields.add(fieldMap);
      }
      else if(field instanceof CANBitmapField) {
        CANBitmapField bitmapField = (CANBitmapField) field;
        List<String> bits = bitmapField.getBits();
        for(int i = 0; i < bits.size(); i++) {
          if(!bits.get(i).equals("reserved")) {
            Map<String, String> fieldMap = new HashMap<>();
            String fieldName = message.getNode().toUpperCase() + "_" + bits.get(i).toUpperCase().replace(' ', '_');
            fieldMap.put("fieldName", fieldName);
            fieldMap.put("position", Integer.toString((bitmapField.getPosition()*8) + i));
            fieldMap.put("length", "1");
            fieldMap.put("endianness", "1");
            fieldMap.put("signed", "+");
            fieldMap.put("scl", "1");
            fieldMap.put("offset", "0");
            fieldMap.put("unit", "");
          }
        }
      }
    }
    return fields;
  }
}
