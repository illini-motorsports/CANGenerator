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
 * CANParser will take a JSON CAN spec and
 * output a parsed spec with the proper application structure
 *
 * This is a utility class, and cannot be instantiated.
 * All methods are static
 */
public class CANParser {

  /**
   * Private constructor, not allowing instantiation
   */
  private CANParser() {}

  /**
   * Only public method in class, will call
   * other private methods to accomplish main
   * task of this utility class
   * @param file
   * @return Full CANSpec if JSON was formatted correctly, null otherwise
   */
  public static CANSpec parseCanSpec(File file) {
    // Try to parse the file as JSON
    JSONObject canJson = getJSONFromFile(file);
    if(canJson == null) {
      return null;
    }

    // Create top level spec object
    String version;
    JSONArray messages;
    try {
      version = canJson.getString("version");
      messages = canJson.getJSONArray("messages");
    } catch (JSONException e) {
      return null;
    }

    CANSpec spec = new CANSpec(version);

    // Add each message to spec
    Iterator messagesIter = messages.iterator();
    while(messagesIter.hasNext()) {
      JSONObject message = (JSONObject) messagesIter.next();
      CANMessage canMessage = parseCANMessage(message);
      if(canMessage == null) {
        return null;
      }
      spec.addMessage(canMessage);
    }

    return spec;
  }

  /**
   * Takes in file, outputs parsed JSON object
   * Returns null on parse failure
   * @param file
   * @return
   */
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

  /**
   * Takes in a JSONObject of a message and outputs a CANMessage object
   * Null if incorrect format
   * @param message
   * @return
   */
  //TODO: make custom exception with more verbose parse error messages
  private static CANMessage parseCANMessage(JSONObject message) {

    // Initialize primitive canMessage fields
    JSONArray bytes;
    CANMessage canMessage;
    try {
      int id = Integer.parseInt(message.getString("id").substring(2), 16);
      String node = message.getString("node");
      boolean bigEndian = message.getBoolean("bigEndian");
      Endianness endianness = bigEndian ? Endianness.BIG : Endianness.LITTLE;
      int dlc = message.getInt("dlc");
      bytes = message.getJSONArray("bytes");
      canMessage = new CANMessage(id, node, endianness, dlc);
    } catch (JSONException e) {
      return null;
    } catch (NumberFormatException e) {
      return null;
    }

    // Add each field to message
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

  /**
   * Parse message field
   * Needs to handle every possible field type
   * @param field
   * @return
   */
  private static CANDataField parseCANDataField(JSONObject field) {
    String type;
    int position;
    try {
      type = field.getString("type");
      position = field.getInt("byte");
    } catch (JSONException e) {
      return null;
    }


    CANDataField canField = null;

    try {
      switch (type) {
        case "number": {
          int length = field.getInt("length");
          String name = field.getString("name");
          String unit = field.getString("unit");
          boolean signed = field.getBoolean("signed");
          double scale = field.getDouble("scale");
          double offset = field.getDouble("offset");
          canField = new CANNumericField(position, length, name, unit, signed, scale, offset);
          break;
        }
        case "bitmap": {
          int length = field.getInt("length");
          String name = field.getString("name");
          JSONArray bits = field.getJSONArray("bits");
          if(bits.length() != length * 8) { break; }
          List<String> bitList = new ArrayList<>();
          for(int i = 0; i < bits.length(); i++) { bitList.add(bits.getString(i)); }
          canField = new CANBitmapField(position, length, name, bitList);
          break;
        }
        case "constant": {
          int length = field.getInt("length");
          int value = Integer.parseInt(field.getString("value").substring(2), 16);
          canField = new CANConstField(position, length, value);
          break;
        }
        case "reserved": {
          int length = field.getInt("length");
          canField = new CANReservedField(position, length);
          break;
        }
        case "nibble": {
          JSONObject msb = field.getJSONObject("msb");
          String msbName = msb.getString("name");
          String msbUnit = msb.getString("unit");
          boolean msbSigned = msb.getBoolean("signed");
          double msbScale = msb.getDouble("scale");
          double msbOffset = msb.getDouble("offset");
          JSONObject lsb = field.getJSONObject("lsb");
          String lsbName = lsb.getString("name");
          String lsbUnit = lsb.getString("unit");
          boolean lsbSigned = lsb.getBoolean("signed");
          double lsbScale = lsb.getDouble("scale");
          double lsbOffset = lsb.getDouble("offset");
          canField = new CANNibbleField(position, msbName, lsbName,
              msbUnit, lsbUnit, msbSigned, lsbSigned, msbScale, lsbScale, msbOffset, lsbOffset);
          break;
        }
      }
    } catch (JSONException e) {
      return null;
    } catch (NumberFormatException e) {
      return null;
    }
    return canField;
  }
}
