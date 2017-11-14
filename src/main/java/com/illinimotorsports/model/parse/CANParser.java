package com.illinimotorsports.model.parse;

import com.illinimotorsports.model.Endianness;
import com.illinimotorsports.model.canspec.*;
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

  /**
   * Constructor for CANParser class
   */
  private CANParser() {}

  public static CANSpec parseCanSpec(File file) {
    JSONObject canJson = getJSONFromFile(file);
    if(canJson == null) {
      return null;
    }

    String version;
    JSONArray messages;
    try {
      version = canJson.getString("version");
      messages = canJson.getJSONArray("messages");
    } catch (JSONException e) {
      return null;
    }

    CANSpec spec = new CANSpec(version);

    Iterator messagesIter = messages.iterator();
    while(messagesIter.hasNext()) {
      JSONObject message = (JSONObject) messagesIter.next();
      CANMessage canMessage = parseCANMessage(message);
      if(canMessage == null) {
        return null;
      }
      spec.addMessage(canMessage);
    }

    return null;
  }

  private static JSONObject getJSONFromFile(File file) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
    } catch (FileNotFoundException e) {
      return null;
    }
    String jsonString = "";
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        jsonString += line;
      }
    } catch (IOException e) {
      return null;
    }

    JSONObject canJson = null;
    try {
      canJson = new JSONObject(jsonString);
    } catch (JSONException e) {}

    return canJson;
  }

  //TODO: make custom exception with more verbose parse error messages
  private static CANMessage parseCANMessage(JSONObject message) {
    String strID;
    String node;
    boolean bigEndian;
    int dlc;
    JSONArray bytes;

    try {
      strID = message.getString("id");
      node = message.getString("node");
      bigEndian = message.getBoolean("bigEndian");
      dlc = message.getInt("dlc");
      bytes = message.getJSONArray("bytes");
    } catch (JSONException e) {
      return null;
    }

    System.out.println(strID);

    int id = 0;
    try {
      id = Integer.parseInt(strID.substring(2), 16);
    } catch (NumberFormatException e) {
      return null;
    }

    Endianness endianness = bigEndian ? Endianness.BIG : Endianness.LITTLE;

    CANMessage canMessage = new CANMessage(id, node, endianness, dlc);

    Iterator bytesIter = bytes.iterator();
    while(bytesIter.hasNext()) {
      JSONObject field = (JSONObject) bytesIter.next();
      CANDataField canField = parseCANDataField(field);
      if(canField == null) {
        return null;
      }
      canMessage.addField(canField);
    }

    return canMessage;
  }

  private static CANDataField parseCANDataField(JSONObject field) {
    String type;
    int position;
    int length;
    try {
      type = field.getString("type");
      position = field.getInt("byte");
      length = field.getInt("length");
    } catch (JSONException e) {
      return null;
    }

    System.out.println(position);

    CANDataField canField = null;

    try {
      switch (type) {
        case "number": {
          String name = field.getString("name");
          String unit = field.getString("unit");
          boolean signed = field.getBoolean("signed");
          double scale = field.getDouble("scale");
          double offset = field.getDouble("offset");
          canField = new CANNumericField(position, length, name, unit, signed, scale, offset);
          break;
        }
        case "bitmap": {
          String name = field.getString("name");
          JSONArray bits = field.getJSONArray("bits");
          if(bits.length() != length * 8) { break; }
          List<String> bitList = new ArrayList<>();
          for(int i = 0; i < bits.length(); i++) { bitList.add(bits.getString(i)); }
          canField = new CANBitmapField(position, length, name, bitList);
          break;
        }
        case "constant": {
          int value = Integer.parseInt(field.getString("value").substring(2), 16);
          canField = new CANConstField(position, length, value);
          break;
        }
        case "reserved": {
          canField = new CANReservedField(position, length);
          break;
        }
        //TODO: Handle nibbles
      }
    } catch (JSONException e) {
      return null;
    } catch (NumberFormatException e) {
      return null;
    }
    return canField;
  }
}
