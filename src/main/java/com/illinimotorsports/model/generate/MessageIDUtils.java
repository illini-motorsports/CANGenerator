package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;

import java.util.*;

public class MessageIDUtils {
  /**
   * Generate map from node to message, for better organization
   * @param spec
   * @return
   */
  public static Map<String, List<CANMessage>> generateNodeMap(CANSpec spec) {
    Map<String, List<CANMessage>> nodeMap = new HashMap<>();
    for(CANMessage message: spec.getMessages()) {
      String node = message.getNode();
      List<CANMessage> nodeList = nodeMap.containsKey(node) ? nodeMap.get(node) : new ArrayList<>();
      nodeList.add(message);
      nodeMap.put(node, nodeList);
    }
    return nodeMap;
  }

  public static Map<CANMessage, String> generateIDNames(CANSpec spec) {
    return generateIDNames(generateNodeMap(spec));
  }

  /**
   * Generate ID names from a map of strings to messages
   * @param nodeMap
   * @return
   */
  public static Map<CANMessage, String> generateIDNames(Map<String, List<CANMessage>> nodeMap) {
    Map<CANMessage, String> canIDs = new TreeMap<>(
        Comparator.comparing((CANMessage message) -> message.getId()));
    for(List<CANMessage> messages: nodeMap.values()) {
      for(int i = 0; i < messages.size(); i++) {
        String id = messages.get(i).getNode().toUpperCase() + "_" + i;
        canIDs.put(messages.get(i), id);
      }
    }
    return canIDs;
  }
}
