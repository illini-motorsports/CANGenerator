package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.*;

import java.text.DecimalFormat;
import java.util.*;

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
  public String[][] generateMessageTable() {
    String[][] messages = new String[spec.getMessages().size()][12];
    Map<CANMessage, String> messageMap = MessageIDUtils.generateIDNames(spec);
    Iterator iter = messageMap.entrySet().iterator();
    for(int i = 0; i < messageMap.size(); i++) {
      Map.Entry<CANMessage, String> message = (Map.Entry<CANMessage, String>) iter.next();
      messages[i][0] = message.getValue();
      messages[i][1] = "0x" + Integer.toHexString(message.getKey().getId());
      messages[i][2] = Integer.toString(message.getKey().getDlc());
      messages[i][3] = message.getKey().getEndianness().toString();
      for(CANDataField field: message.getKey().getData()) {
        messages[i][field.getPosition() + 4] = field.getName();
      }
    }
    return messages;
  }

  /**
   * Generates a table with a row for each field,
   * showing all relevant information
   * @return
   */
  public String[][] generateFieldTable() {
    List<String[]> fields = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(20);
    for(CANMessage message: spec.getMessages()) {
      for(CANDataField field: message.getData()) {
        if(field instanceof CANNumericField) {
          CANNumericField numericField = (CANNumericField) field;
          String[] row = new String[5];
          row[0] = field.getName();
          row[1] = numericField.getUnit();
          row[2] = df.format(numericField.getScale());
          row[3] = "0x" + Integer.toHexString(numericField.getOffset());
          row[4] = numericField.isSigned() ? "Signed" : "Unsigned";
          fields.add(row);
        } else if(field instanceof CANBitmapField) {
          CANBitmapField bitmapField = (CANBitmapField) field;
          for(CANBitField bit: bitmapField.getBits()) {
            String[] row = new String[5];
            row[0] = bit.getName();
            row[1] = "bit";
            row[2] = "";
            row[3] = "";
            row[4] = "";
          }
        }
      }
    }
    return (String[][]) fields.toArray();
  }
}
