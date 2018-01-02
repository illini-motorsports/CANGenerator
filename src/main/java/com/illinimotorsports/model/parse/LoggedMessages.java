package com.illinimotorsports.model.parse;

import com.illinimotorsports.model.Endianness;
import com.illinimotorsports.model.canspec.CANDataField;
import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANNumericField;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoggedMessages {
  private List<CANLoggedMessage> messages;

  public LoggedMessages(File[] fileList) {
    messages = parseFileList(fileList);
  }

  public Set<Integer> getMessageIDs() {
    Set<Integer> ids = new HashSet<>();
    for(CANLoggedMessage message: messages) {
      ids.add(message.getId());
    }
    return ids;
  }

  // TODO: Generalize to bitmaps
  public List<double[]> getNumericFieldList(CANMessage specMessage, CANNumericField specField) {
    List<double[]> fieldLst = new ArrayList<>();
    int id = specMessage.getId();
    int pos = specField.getPosition();
    int len = specField.getLength();
    int offset = specField.getOffset();
    double scl = specField.getScale();
    boolean isSigned = specField.isSigned();
    Endianness endianness = specMessage.getEndianness();

    for(CANLoggedMessage message: messages) {
      if(message.getId() == id) {
        double[] row = {message.getTimestamp(), message.getNumericField(pos, len, scl, offset, isSigned, endianness)};
        fieldLst.add(row);
      }
    }
    return fieldLst;
  }

  public static List<CANLoggedMessage> parseFileList(File[] fileList) {
    List<CANLoggedMessage> messageList = new ArrayList<>();
    for(File file: fileList) {
      try {
        InputStream inputFS = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
        messageList.addAll(br.lines().skip(1).map(stringToMessage).collect(Collectors.toList()));
        br.close();
      } catch (IOException e) {
        //TODO: Handle exception gracefully
      }
    }
    return messageList;
  }
  private static Function<String, CANLoggedMessage> stringToMessage = (line) -> {
    String[] p = line.split("\t");
    return new CANLoggedMessage(p);
  };
}
