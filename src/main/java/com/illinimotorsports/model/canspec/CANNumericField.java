package com.illinimotorsports.model.canspec;

import com.illinimotorsports.model.Endianness;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Numeric CAN field, the most common field
 */
public class CANNumericField extends CANDataField {

  private String unit;
  private boolean signed;
  private double scale;
  private int offset;
  private Endianness endianness;

  /**
   * Initialize all fields in constructor
   * @param pos
   * @param len
   * @param name
   * @param unit
   * @param signed
   * @param scale
   * @param offset
   */
  public CANNumericField(int pos, int len, String name, String node, int nodeId,
                         String unit, boolean signed, double scale, int offset, Endianness endianness) {
    super(pos, len, name, node, nodeId);
    this.unit = unit;
    this.signed = signed;
    this.scale = scale;
    this.offset = offset;
    this.endianness = endianness;
  }

  public String getUnit() {
    return unit;
  }

  public boolean isSigned() {
    return signed;
  }

  public double getScale() {
    return scale;
  }

  public int getOffset() {
    return offset;
  }

  public Endianness getEndianness() {
    return endianness;
  }

  @Override
  public List<Map<String, String>> generateCHeaderDefs() {
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(20);
    String genericDef = getNode().toUpperCase() + "_"
        + getName().toUpperCase().replace(' ', '_');
    String byteNum = Integer.toString(getPosition());
    String scl = df.format(getScale());
    String off = "0x" + Integer.toHexString(getOffset());

    Map<String, String> byteDef = new HashMap<>();
    byteDef.put("def", genericDef + "_BYTE");
    byteDef.put("value", byteNum);
    Map<String, String> sclDef = new HashMap<>();
    sclDef.put("def", genericDef + "_SCL");
    sclDef.put("value", scl);
    Map<String, String> offDef = new HashMap<>();
    offDef.put("def", genericDef + "_OFF");
    offDef.put("value", off);

    return Arrays.asList(byteDef, sclDef, offDef);
  }

  @Override
  public List<Map<String, String>> generateCParseMap() {
    Map<String, String> fieldMap = new HashMap<>();
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(20);
    fieldMap.put("type", "numeric");
    fieldMap.put("pos", Integer.toString(getPosition()));
    fieldMap.put("len", Integer.toString(getLength()));
    fieldMap.put("endian", endianness.toString());
    fieldMap.put("sgn", isSigned() ? "1": "0");
    fieldMap.put("scl", df.format(getScale()));
    fieldMap.put("off", "0x" + Integer.toHexString(getOffset()));
    String comment = getName();
    if(getUnit().length() > 0) {
      comment += " (" + getUnit() + ")";
    }
    fieldMap.put("comment", comment);
    return Arrays.asList(fieldMap);
  }

  @Override
  public List<Map<String, String>> generateDBCFieldDefs() {
    Map<String, String> fieldMap = new HashMap<>();
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(20);
    String fieldName = getNode().toUpperCase() + "_" + getName().toUpperCase().replace(' ', '_');
    fieldMap.put("fieldName", fieldName);
    fieldMap.put("position", Integer.toString(getPosition() * 8));
    fieldMap.put("length", Integer.toString(getLength()*8));
    fieldMap.put("endianness", endianness.toDBCString());
    fieldMap.put("signed", isSigned() ? "-" : "+");
    fieldMap.put("scl", df.format(getScale()));
    fieldMap.put("offset", Integer.toString(getOffset()));
    fieldMap.put("unit", getUnit());
    return Arrays.asList(fieldMap);
  }

  @Override
  public List<String[]> generateFieldTableRow() {
    List<String[]> rows = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(20);
    String[] row = new String[6];
    row[0] = getName();
    row[1] = getUnit();
    row[2] = df.format(getScale());
    row[3] = "0x" + Integer.toHexString(getOffset());
    row[4] = isSigned() ? "Signed" : "Unsigned";
    row[5] = endianness.toString();
    rows.add(row);
    return rows;
  }

  @Override
  public JSONObject generateTelemetryJson() {
    JSONObject fieldObj = new JSONObject();
    fieldObj.put("name", getName());

    String key = getNode() + "." + getName();
    fieldObj.put("key", key);

    JSONArray values = new JSONArray();

    JSONObject valueObj = new JSONObject();

    valueObj.put("key", "value");
    valueObj.put("key", "Value");
    valueObj.put("units", getUnit());
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
