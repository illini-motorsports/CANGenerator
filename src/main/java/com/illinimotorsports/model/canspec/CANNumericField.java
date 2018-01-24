package com.illinimotorsports.model.canspec;

import com.illinimotorsports.model.Endianness;

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
  public CANNumericField(int pos, int len, String name, String node,
                         String unit, boolean signed, double scale, int offset) {
    super(pos, len, name, node);
    this.unit = unit;
    this.signed = signed;
    this.scale = scale;
    this.offset = offset;
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
  public List<Map<String, String>> generateCParseMap(Endianness endianness) {
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
  public List<Map<String, String>> generateDBCFieldDefs(String endianness) {
    Map<String, String> fieldMap = new HashMap<>();
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(20);
    String fieldName = getNode().toUpperCase() + "_" + getName().toUpperCase().replace(' ', '_');
    fieldMap.put("fieldName", fieldName);
    fieldMap.put("position", Integer.toString(getPosition() * 8));
    fieldMap.put("length", Integer.toString(getLength()*8));
    fieldMap.put("endianness", endianness);
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
    String[] row = new String[5];
    row[0] = getName();
    row[1] = getUnit();
    row[2] = df.format(getScale());
    row[3] = "0x" + Integer.toHexString(getOffset());
    row[4] = isSigned() ? "Signed" : "Unsigned";
    rows.add(row);
    return rows;
  }
}
