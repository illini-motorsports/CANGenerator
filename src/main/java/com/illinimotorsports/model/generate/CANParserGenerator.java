package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANDataField;
import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANNumericField;
import com.x5.template.Chunk;
import com.x5.template.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CANParserGenerator {
  List<CANMessage> messages;

  public CANParserGenerator(List<CANMessage> canMessages) {
    this.messages = canMessages;
  }

  public String fillTemplate() {
    Theme theme = new Theme();
    Chunk parse = theme.makeChunk("parse", "c");
    parse.set("messages", generateMessageParseMap());

    return parse.toString();
  }

  public String fillSubtemplate(CANMessage message) {
    Theme theme = new Theme();
    Chunk parse = theme.makeChunk("subParse", "c");
    parse.set("fields", generateFieldParseMap(message));

    return parse.toString();
  }

  public List<Map<String, String>> generateFieldParseMap(CANMessage message) {
    List<Map<String, String>> fieldList = new ArrayList<>();
    for(CANDataField field: message.getData()) {
      if(field instanceof CANNumericField) {
        Map<String, String> fieldMap = new HashMap<>();
        CANNumericField numField = (CANNumericField) field;
        fieldMap.put("pos", Integer.toString(numField.getPosition()));
        fieldMap.put("len", Integer.toString(numField.getLength()));
        fieldMap.put("endian", message.getEndianness().toString());
        fieldMap.put("sgn", numField.isSigned() ? "1": "0");
        fieldMap.put("scl", Double.toString(numField.getScale()));
        fieldMap.put("off", Double.toString(numField.getOffset()));
        String comment = numField.getName();
        if(numField.getUnit().length() > 0) {
          comment += " (" + numField.getUnit() + ")";
        }
        fieldMap.put("comment", comment);
        fieldList.add(fieldMap);
      }
    }
    return fieldList;
  }

  public List<Map<String, String>> generateMessageParseMap() {
    List<Map<String, String>> parseList = new ArrayList<>();
    for(CANMessage message: messages) {
      Map<String, String> messageMap = new HashMap<>();
      messageMap.put("id", "0x" + Integer.toHexString(message.getId()));
      messageMap.put("comment", message.getNode());
      messageMap.put("fields", fillSubtemplate(message));
      parseList.add(messageMap);
    }
    return parseList;
  }
}
