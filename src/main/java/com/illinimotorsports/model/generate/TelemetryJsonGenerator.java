package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANDataField;
import org.json.JSONArray;
import org.json.JSONObject;

public class TelemetryJsonGenerator extends SelectedDataGenerator {
    @Override
    public String generate() {
        JSONObject dict = new JSONObject();

        //TODO: Figure out a better way of naming dict (version?)
        dict.put("name", "Skinny Pete");
        dict.put("key", "sc");

        JSONArray measurements = new JSONArray();
        getData().stream().map(x -> (CANDataField) x.getData()).forEach(x -> measurements.put(x.generateTelemetryJson()));

        dict.put("measurements", measurements);

        return dict.toString(2);
    }
}
