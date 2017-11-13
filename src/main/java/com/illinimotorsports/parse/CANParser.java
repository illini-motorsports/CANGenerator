package com.illinimotorsports.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Main CAN Spec parsing class
 * CANParser will take a JSON CAN spec, verify the schema,
 * and deliver individual
 */
public class CANParser {

  JSONObject canSpec;

  /**
   * Constructor for CANParser class
   * @param file
   */
  public CANParser(File file) {
    // Create Reader for file
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
    } catch (FileNotFoundException e) {
      canSpec = new JSONObject();
      return;
    }

    String jsonString = "";
    String line;

    try {
      // Read file text into string
      while ((line = reader.readLine()) != null) {
        jsonString += line;
      }
    } catch (IOException e) {
      // No need to do anything, since an empty canSpec will
      // still be created
    }

    // Parse string into json object
    try {
      canSpec = new JSONObject(jsonString);
    } catch (JSONException e) {
      canSpec = new JSONObject();
    }
  }

  /**
   * Verifies the CAN Spec schema
   * @return true if schema is valid, false otherwise
   * @throws JSONException
   */
  public boolean verifySchema() throws JSONException {
    JSONArray messages = canSpec.getJSONArray("messages");
    if(messages == null) {
      return false;
    }

    Iterator msgIter = messages.iterator();
    while(msgIter.hasNext()) {
      if(!verifyCANMessage((JSONObject) msgIter.next())) {
        return false;
      }
    }

    return true;
  }

  private boolean verifyCANMessage(JSONObject message) throws JSONException {
    String id = message.getString("id");
    if(id.length() != 5 || !id.substring(0,2).equals("0x")) {
      return false;
    }

    String node = message.getString("node");
    if(node.length() == 0) {
      return false;
    }

    if(message.get("bigEndian") == null) {
      return false;
    }
    int dlc = (Integer) message.get("dlc");
    JSONArray bytes = (JSONArray) message.get("bytes");
    return true;
  }

  public String getVersion() {
    return Double.toString(canSpec.getDouble("version"));
  }

  public List<String> getFields() {
    JSONArray messages = canSpec.getJSONArray("messages");
    Iterator msgIter = messages.iterator();
    List<String> fields = new ArrayList<String>();
    while(msgIter.hasNext()) {
      JSONObject curMsg = (JSONObject) msgIter.next();
      JSONArray bytes = curMsg.getJSONArray("bytes");
      Iterator bytesIter = bytes.iterator();
      while(bytesIter.hasNext()) {
        JSONObject field = (JSONObject)  bytesIter.next();
        String type = field.getString("type");
        if(!(type.equals("reserved") || type.equals("constant"))) {
          fields.add(field.getString("name"));
        }
      }
    }
    return fields;
  }
}
