package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.Endianness;
import com.illinimotorsports.model.canspec.*;
import com.x5.template.Chunk;
import com.x5.template.Theme;

import java.text.DecimalFormat;
import java.util.*;


/**
 * Generator Class for DBC files
 */
public class DBCGenerator implements CodeGenerator {

  CANSpec spec;

  /**
   * Constructor for DBCGenerator
   * @param canSpec
   */
  public DBCGenerator(CANSpec canSpec) {
    spec = canSpec;
  }

  /**
   * Main fill function.  Will return a fully filled DBC file
   * generated from the given spec
   * @return
   */
  public String generate() {
    Theme theme = new Theme();

    Chunk config = theme.makeChunk("config", "dbc");

    config.set("nodes", generateNodeDefs());
    config.set("messages", generateMessageDefs());

    return config.toString();
  }

  /**
   * Generator for node definitions
   * @return
   */
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

  /**
   * Generator for top level message definitions
   * Will use a subTemplate to get all fields
   * @return
   */
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

  /**
   * Fill function for subTemplate.  Returns string with all
   * field definitions from given CANMessage
   * @param message
   * @return
   */
  private String fillSubTemplate(CANMessage message) {
    Theme theme = new Theme();

    Chunk subConfig = theme.makeChunk("subConfig", "dbc");

    subConfig.set("fields", generateFieldDefs(message));

    return subConfig.toString();
  }

  /**
   * Generator function for fields.  Returns list of field information to be used
   * in the fillSubTemplate function
   * @param message
   * @return
   */
  private List<Map<String, String>> generateFieldDefs(CANMessage message) {
    List<Map<String, String>> fields = new ArrayList<>();
    String endianness = message.getEndianness() == Endianness.BIG ? "1" : "0";
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(20);
    for(CANDataField field: message.getData()) {
      fields.addAll(field.generateDBCFieldDefs(endianness));
    }
    return fields;
  }
}
