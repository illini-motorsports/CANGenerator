package com.illinimotorsports.model.parse;

import org.json.JSONException;

public class CANParseException extends Exception {
  public CANParseException(String s) {
    super(s);
  }

  public CANParseException(JSONException e) {
    super("JSON Parse Error: " + e.getLocalizedMessage());
  }
}
