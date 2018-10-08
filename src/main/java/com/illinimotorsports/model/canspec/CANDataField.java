package com.illinimotorsports.model.canspec;

import com.illinimotorsports.model.Endianness;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Main abstract class for CAN Data
 * Many possible field types, such as numeric, bitmap, const, etc
 * Important for defining abstract code generator function definitions
 */
public abstract class CANDataField {

  private int position;
  private int length;
  private String name;
  private String node;
  private int nodeId;

  /**
   * Only contains position and length,
   * since those are the only two fields common between
   * all of the data fields
   * @param pos
   * @param len
   */
  public CANDataField(int pos, int len, String fieldName, String parentNode, int parentId) {
    this.position = pos;
    this.length = len;
    this.name = fieldName;
    this.node = parentNode;
    this.nodeId = parentId;
  }

  public abstract List<Map<String, String>> generateCHeaderDefs();

  public abstract List<Map<String, String>> generateCParseMap();

  public abstract List<Map<String, String>> generateDBCFieldDefs();

  public abstract List<String[]> generateFieldTableRow();

  public abstract JSONObject generateTelemetryJson();

  public int getPosition() {
    return position;
  }

  public int getLength() {
    return length;
  }

  public String getName() {
    return name;
  }

  public String getNode() {
    return node;
  }

  public int getNodeId() {
    return nodeId;
  }

  @Override
  public String toString() {
    return "CANDataField{" +
            "position=" + position +
            ", length=" + length +
            ", name='" + name + '\'' +
            ", node='" + node + '\'' +
            ", nodeId=" + nodeId +
            '}';
  }
}
