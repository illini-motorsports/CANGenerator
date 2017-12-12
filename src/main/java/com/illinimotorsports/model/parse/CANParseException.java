package com.illinimotorsports.model.parse;

import org.json.JSONException;

/**
 * Custom exception to make debugging spec parsing easier
 */
public class CANParseException extends Exception {
  public CANParseException(String s) {
    super(s);
  }

  public CANParseException(JSONException e) {
    super("JSON Parse Error: " + e.getLocalizedMessage());
  }
}
