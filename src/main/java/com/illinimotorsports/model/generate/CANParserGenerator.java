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
 * Main generator for parser function
 */
public class CANParserGenerator extends SelectedMessagesGenerator {

  /**
   * Top Level function which fills the main template
   * @return
   */
  public String fillTemplate() {
    Theme theme = new Theme();
    Chunk parse = theme.makeChunk("parse", "c");
    parse.set("messages", generateMessageParseMap());

    return parse.toString();
  }

  /**
   * Templates cannot have nested loops, so this is a template filler
   * for the inner loop of the parsing function (fields)
   * @param message
   * @return
   */
  public String fillSubtemplate(CANMessage message) {
    Theme theme = new Theme();
    Chunk parse = theme.makeChunk("subParse", "c");
    parse.set("fields", generateFieldParseMap(message));

    return parse.toString();
  }

  /**
   * Produces the list of maps required for the field template fill.
   * This will be sent to the fillSubTemplate function, then to the
   * upper level generate function
   * @param message
   * @return
   */
  public List<Map<String, String>> generateFieldParseMap(CANMessage message) {
    List<Map<String, String>> fieldList = new ArrayList<>();
    for(CANDataField field: message.getData()) {
      DecimalFormat df = new DecimalFormat("#");
      df.setMaximumFractionDigits(20);
      if(field instanceof CANNumericField) {
        Map<String, String> fieldMap = new HashMap<>();
        CANNumericField numField = (CANNumericField) field;
        fieldMap.put("type", "numeric");
        fieldMap.put("pos", Integer.toString(numField.getPosition()));
        fieldMap.put("len", Integer.toString(numField.getLength()));
        fieldMap.put("endian", message.getEndianness().toString());
        fieldMap.put("sgn", numField.isSigned() ? "1": "0");
        fieldMap.put("scl", df.format(numField.getScale()));
        fieldMap.put("off", "0x" + Integer.toHexString(numField.getOffset()));
        String comment = numField.getName();
        if(numField.getUnit().length() > 0) {
          comment += " (" + numField.getUnit() + ")";
        }
        fieldMap.put("comment", comment);
        fieldList.add(fieldMap);
      }
      else if(field instanceof CANBitmapField) {
        CANBitmapField bitmapField = (CANBitmapField) field;
        for(CANBitField bit: bitmapField.getBits()) {
          Map<String, String> fieldMap = new HashMap<>();
          fieldMap.put("type", "bit");
          fieldMap.put("pos", Integer.toString(bitmapField.getPosition()));
          fieldMap.put("len", Integer.toString(bitmapField.getLength()));
          fieldMap.put("endian", message.getEndianness().toString());
          fieldMap.put("bitPos", Integer.toString(bit.getPosition()));
          fieldMap.put("comment", field.getName() + " " + bit.getName());
          fieldList.add(fieldMap);
        }
      }
    }
    return fieldList;
  }

  /**
   * Produces the list of maps required for the top level template fill.
   * Executes a subtemplate fill for each message
   * @return
   */
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
