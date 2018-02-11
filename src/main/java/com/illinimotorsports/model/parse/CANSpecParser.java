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
public class CANSpecParser {

  /**
   * Private constructor, not allowing instantiation
   */
  private CANSpecParser() {}

  /**
   * Only public method in class, will call
   * other private methods to accomplish main
   * task of this utility class
   * @param file
   * @return Full CANSpec if JSON was formatted correctly, null otherwise
   */
  public static CANSpec parseCanSpec(File file) throws CANParseException {
    // parse the file as JSON
    JSONObject canJson = getJSONFromFile(file);

    // Create top level spec object
    String version;
    JSONArray messages;
    try {
      version = canJson.getString("version");
      messages = canJson.getJSONArray("messages");
    } catch (JSONException e) {
      throw new CANParseException(e);
    }

    CANSpec spec = new CANSpec(version);

    // Add each message to spec
    Iterator messagesIter = messages.iterator();
    while(messagesIter.hasNext()) {
      JSONObject message = (JSONObject) messagesIter.next();
      CANMessage canMessage = parseCANMessage(message);
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
  private static JSONObject getJSONFromFile(File file) throws CANParseException {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
    } catch (FileNotFoundException e) {
      throw new CANParseException("File Not Found");
    }
    String jsonString = "";
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        jsonString += line;
      }
    } catch (IOException e) {
      throw new CANParseException("Error Reading File");
    }

    JSONObject canJson;
    try {
      canJson = new JSONObject(jsonString);
    } catch (JSONException e) {
      throw new CANParseException(e);
    }

    return canJson;
  }

  /**
   * Takes in a JSONObject of a message and outputs a CANMessage object
   * Null if incorrect format
   * @param message
   * @return
   */
  private static CANMessage parseCANMessage(JSONObject message) throws CANParseException {

    // Initialize primitive canMessage fields
    JSONArray bytes;
    CANMessage canMessage;
    String strID = "";
    String node = "";
    int id = 0;
    try {
      strID = message.getString("id");
      id = Integer.parseInt(message.getString("id").substring(2), 16);
      node = message.getString("node");
      boolean bigEndian = message.getBoolean("bigEndian");
      Endianness endianness = bigEndian ? Endianness.BIG : Endianness.LITTLE;
      int dlc = message.getInt("dlc");
      bytes = message.getJSONArray("bytes");
      canMessage = new CANMessage(id, node, endianness, dlc);
    } catch (JSONException e) {
      throw new CANParseException(e);
    } catch (NumberFormatException e) {
      throw new CANParseException("Could not parse ID: " + strID);
    }

    // Add each field to message
    Iterator bytesIter = bytes.iterator();
    while(bytesIter.hasNext()) {
      JSONObject field = (JSONObject) bytesIter.next();
      CANDataField canField = parseCANDataField(field, node, id);
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
  private static CANDataField parseCANDataField(JSONObject field, String node, int nodeId) throws CANParseException {
    String type;
    int position;
    try {
      type = field.getString("type");
      position = field.getInt("byte");
    } catch (JSONException e) {
      throw new CANParseException(e);
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
          int offset = Integer.parseInt(field.getString("offset").substring(2), 16);
          canField = new CANNumericField(position, length, name, node, nodeId, unit, signed, scale, offset);
          break;
        }
        case "bitmap": {
          int length = field.getInt("length");
          String name = field.getString("name");
          JSONArray bits = field.getJSONArray("bits");
          Iterator bitsIter = bits.iterator();
          List<CANBitField> bitList = new ArrayList<>();
          while(bitsIter.hasNext()) {
            JSONObject bit = (JSONObject) bitsIter.next();
            bitList.add(new CANBitField(bit.getString("name"),
                bit.getInt("position")));
          }
          canField = new CANBitmapField(position, length, name, node, nodeId, bitList);
          break;
        }
      }
    } catch (JSONException e) {
      throw new CANParseException(e);
    } catch (NumberFormatException e) {
      throw new CANParseException("Error Parsing Offset Value");
    }
    return canField;
  }
}
