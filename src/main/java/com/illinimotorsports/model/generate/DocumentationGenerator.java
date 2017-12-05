package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

public class DocumentationGenerator {
  private CANSpec spec;
  public static final String[] messageTableColumns = {"Name", "ID", "Endianness",
      "Byte 0", "Byte 1", "Byte 2", "Byte 3", "Byte 4", "Byte 5", "Byte 6", "Byte 7"};

  public DocumentationGenerator(CANSpec canSpec) {
    spec = canSpec;
  }

  public String[][] generateMessageTable() {
    String[][] messages = new String[spec.getMessages().size()][11];
    Map<CANMessage, String> messageMap = MessageIDUtils.generateIDNames(spec);
    Iterator iter = messageMap.entrySet().iterator();
    for(int i = 0; i < messageMap.size(); i++) {
      Map.Entry<CANMessage, String> message = (Map.Entry<CANMessage, String>) iter.next();
      messages[i][0] = message.getValue();
      messages[i][1] = "0x" + Integer.toHexString(message.getKey().getId());
      messages[i][2] = message.getKey().getEndianness().toString();
      for(CANDataField field: message.getKey().getData()) {
        String name = "Not Assigned";
        if(field instanceof CANNumericField) {
          CANNumericField numericField = (CANNumericField) field;
          name = numericField.getName();
        } else if(field instanceof CANBitmapField) {
          CANBitmapField bitmapField = (CANBitmapField) field;
          name = bitmapField.getName();
        }
        for(int j = field.getPosition() + 3; j < field.getPosition() + field.getLength() + 3; j++) {
          messages[i][j] = name;
        }
      }
    }
    for(int i = 0; i < messages.length; i++) {
      for(int j = 0; j < messages[0].length; j++) {
        System.out.print(messages[i][j] + ", ");
      }
      System.out.println("");
    }
    return messages;
  }
}
