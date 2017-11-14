package com.illinimotorsports.model.canspec;

public class CANNibbleField extends CANDataField {

  private String msbName;
  private String lsbName;
  private String msbUnit;
  private String lsbUnit;
  private boolean msbSigned;
  private boolean lsbSigned;
  private double msbScale;
  private double lsbScale;
  private double msbOffset;
  private double lsbOffset;

  public CANNibbleField(int pos, String msbName, String lsbName,
                        String msbUnit, String lsbUnit, boolean msbSigned,
                        boolean lsbSigned, double msbScale, double lsbScale,
                        double msbOffset, double lsbOffset) {
    super(pos, 1);
    this.msbName = msbName;
    this.lsbName = lsbName;
    this.msbUnit = msbUnit;
    this.lsbUnit = lsbUnit;
    this.msbSigned = msbSigned;
    this.lsbSigned = lsbSigned;
    this.msbScale = msbScale;
    this.lsbScale = lsbScale;
    this.msbOffset = msbOffset;
    this.lsbOffset = lsbOffset;
  }

  public String getMsbName() {
    return msbName;
  }

  public String getLsbName() {
    return lsbName;
  }

  public String getMsbUnit() {
    return msbUnit;
  }

  public String getLsbUnit() {
    return lsbUnit;
  }

  public boolean isMsbSigned() {
    return msbSigned;
  }

  public boolean isLsbSigned() {
    return lsbSigned;
  }

  public double getMsbScale() {
    return msbScale;
  }

  public double getLsbScale() {
    return lsbScale;
  }

  public double getMsbOffset() {
    return msbOffset;
  }

  public double getLsbOffset() {
    return lsbOffset;
  }
}
