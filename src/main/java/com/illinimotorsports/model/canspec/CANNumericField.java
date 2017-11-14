package com.illinimotorsports.model.canspec;

public class CANNumericField extends CANDataField {

  private String unit;
  private boolean signed;
  private double scale;
  private double offset;

  public CANNumericField(String name, int pos, int len,
                         String unit, boolean signed,
                         double scale, double offset) {
    super(name, pos, len);
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

  public double getOffset() {
    return offset;
  }
}
