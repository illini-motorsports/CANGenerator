package com.illinimotorsports.model.canspec;

/**
 * Numeric CAN field, the most common field
 */
public class CANNumericField extends CANDataField {

  private String name;
  private String unit;
  private boolean signed;
  private double scale;
  private double offset;

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
  public CANNumericField(int pos, int len, String name,
                         String unit, boolean signed,
                         double scale, double offset) {
    super(pos, len);
    this.name = name;
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

  public String getName() {
    return name;
  }
}
