package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.*;
import com.illinimotorsports.view.CheckBoxView;
import com.x5.template.Chunk;
import com.x5.template.Theme;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Main generator for parser function
 */
public class CParserGenerator extends SelectedDataGenerator {

  /**
   * Top Level function which fills the main template
   * @return
   */
  public String generate() {
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
    message.getData().forEach(x -> fieldList.addAll(x.generateCParseMap(message.getEndianness())));
    return fieldList;
  }

  /**
   * Produces the list of maps required for the top level template fill.
   * Executes a subtemplate fill for each message
   * @return
   */
  public List<Map<String, String>> generateMessageParseMap() {
    List<Map<String, String>> parseList = new ArrayList<>();
    for(CANMessage message: getData().stream().map(x -> (CANMessage) x.getData()).collect(Collectors.toList())) {
      Map<String, String> messageMap = new HashMap<>();
      messageMap.put("id", "0x" + Integer.toHexString(message.getId()));
      messageMap.put("comment", message.getNode());
      messageMap.put("fields", fillSubtemplate(message));
      parseList.add(messageMap);
    }
    return parseList;
  }
}
