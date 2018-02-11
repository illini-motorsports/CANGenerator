package com.illinimotorsports.model.canspec;

import com.illinimotorsports.model.Endianness;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bitmap message field in can spec.  Contains list of bit names
 */
public class CANBitmapField extends CANDataField {

  private List<CANBitField> bits;

  /**
   * Constructor for class, requires full list of bit names
   * @param pos
   * @param len
   * @param name
   * @param bits
   */
  public CANBitmapField(int pos, int len, String name, String node, int nodeId, List<CANBitField> bits) {
    super(pos, len, name, node, nodeId);
    this.bits = new ArrayList<>(bits);
  }

  /**
   * Returns full list of bit names
   * @return
   */
  public List<CANBitField> getBits() {
    return bits;
  }

  /**
   * Getter for all bit names in map
   * @return
   */
  public List<String> getBitNames() {
    List<String> bitNames = new ArrayList<>();
    bits.forEach(x -> bitNames.add(x.getName()));
    return bitNames;
  }

  @Override
  public List<Map<String, String>> generateCHeaderDefs() {
    List<Map<String, String>> defs = new ArrayList<>();
    String genericDef = getNode().toUpperCase() + "_"
        + getName().toUpperCase().replace(' ', '_');
    String byteNum = Integer.toString(getPosition());

    Map<String, String> byteDef = new HashMap<>();
    byteDef.put("def", genericDef + "_BYTE");
    byteDef.put("value", byteNum);
    defs.add(byteDef);

    List<CANBitField> bits = getBits();
    for(CANBitField bit: bits) {
      String bitDef = genericDef + "_"
          + bit.getName().toUpperCase().replace(' ', '_')
          + "_BIT";
      String bitPos = Integer.toString(bit.getPosition());
      Map<String, String> bitMap = new HashMap<>();
      bitMap.put("def", bitDef);
      bitMap.put("value", bitPos);
      defs.add(bitMap);
    }
    return defs;
  }

  @Override
  public List<Map<String, String>> generateCParseMap(Endianness endianness) {
    List<Map<String, String>> bitList = new ArrayList<>();
    for(CANBitField bit: getBits()) {
      Map<String, String> fieldMap = new HashMap<>();
      fieldMap.put("type", "bit");
      fieldMap.put("pos", Integer.toString(getPosition()));
      fieldMap.put("len", Integer.toString(getLength()));
      fieldMap.put("endian", endianness.toString());
      fieldMap.put("bitPos", Integer.toString(bit.getPosition()));
      fieldMap.put("comment", getName() + " " + bit.getName());
      bitList.add(fieldMap);
    }
    return bitList;
  }

  @Override
  public List<Map<String, String>> generateDBCFieldDefs(String endianness) {
    List<Map<String, String>> fields = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(20);
    List<CANBitField> bits = getBits();
    for(CANBitField bit: bits) {
      Map<String, String> fieldMap = new HashMap<>();
      String fieldName = getNode().toUpperCase() +
          "_" + bit.getName().toUpperCase().replace(' ', '_');
      fieldMap.put("fieldName", fieldName);
      int bitPos = (getPosition()*8) + bit.getPosition();
      fieldMap.put("position", Integer.toString(bitPos));
      fieldMap.put("length", "1");
      fieldMap.put("endianness", "1");
      fieldMap.put("signed", "+");
      fieldMap.put("scl", "1");
      fieldMap.put("offset", "0");
      fieldMap.put("unit", "");
      fields.add(fieldMap);
    }
    return fields;
  }

  @Override
  public List<String[]> generateFieldTableRow() {
    List<String[]> rows = new ArrayList<>();
    for(CANBitField bit: getBits()) {
      String[] row = new String[5];
      row[0] = bit.getName();
      row[1] = "bit";
      row[2] = "";
      row[3] = "";
      row[4] = "";
      rows.add(row);
    }
    return rows;
  }

  @Override
  public JSONObject generateTelemetryJson() {
    // TODO: Generate individual bitmap entries in array
    JSONObject fieldObj = new JSONObject();
    fieldObj.put("name", getName());

    String key = getNode() + "." + getName();
    fieldObj.put("key", key);

    JSONArray values = new JSONArray();

    JSONObject valueObj = new JSONObject();

    valueObj.put("key", "value");
    valueObj.put("key", "Value");
    // Better unit?
    valueObj.put("units", "");
    valueObj.put("format", "float");

    //TODO: add more sensible values
    valueObj.put("min", -1000);
    valueObj.put("max", 1000);

    JSONObject hints = new JSONObject();
    hints.put("range", 1);
    valueObj.put("hints", hints);

    values.put(valueObj);

    JSONObject utcObj = new JSONObject();

    utcObj.put("key", "utc");
    utcObj.put("source", "timestamp");
    utcObj.put("name", "Timestamp");
    utcObj.put("format", "utc");

    hints = new JSONObject();
    hints.put("domain", 1);
    utcObj.put("hints", hints);

    values.put(utcObj);

    fieldObj.put("values", values);
    return fieldObj;
  }
}
