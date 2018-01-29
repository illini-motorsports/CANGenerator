package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Generator class for documentation
 */
public class DocumentationGenerator {
  private CANSpec spec;

  /**
   * Column names for each table
   */
  public static final String[] messageTableColumns = {"Name", "ID", "DLC", "Endianness",
      "Byte 0", "Byte 1", "Byte 2", "Byte 3", "Byte 4", "Byte 5", "Byte 6", "Byte 7"};
  public static final String[] fieldTableColumns = {"Name", "Unit", "Scalar", "Offset", "Signed"};

  /**
   * Constructor for generator class, requires spec
   * @param canSpec
   */
  public DocumentationGenerator(CANSpec canSpec) {
    spec = canSpec;
  }

  /**
   * Generates a table with a row for each message,
   * showing information about the message and positions
   * and lengths for each field
   * @return
   */
  public List<String[]> generateMessageTable() {
    List<String[]> messages = new ArrayList<>();
    Map<CANMessage, String> messageMap = MessageIDUtils.generateIDNames(spec);
    Iterator iter = messageMap.entrySet().iterator();
    for(int i = 0; i < messageMap.size(); i++) {
      Map.Entry<CANMessage, String> message = (Map.Entry<CANMessage, String>) iter.next();
      String[] row = new String[messageTableColumns.length];
      row[0] = message.getValue();
      row[1] = "0x" + Integer.toHexString(message.getKey().getId());
      row[2] = Integer.toString(message.getKey().getDlc());
      row[3] = message.getKey().getEndianness().toString();
      for(CANDataField field: message.getKey().getData()) {
        row[field.getPosition() + 4] = field.getName();
        for(int j = field.getPosition() + 5; j < field.getPosition() + 4 + field.getLength(); j++) {
          row[j] = "%";
        }
      }
      messages.add(row);
    }
    return messages;
  }

  /**
   * Generates a table with a row for each field,
   * showing all relevant information
   * @return
   */
  public List<String[]> generateFieldTable() {
    List<String[]> fields = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(20);
    spec.getMessages().stream().flatMap(x -> x.getData().stream()).forEach(x -> fields.addAll(x.generateFieldTableRow()));
    return fields;
  }
}
