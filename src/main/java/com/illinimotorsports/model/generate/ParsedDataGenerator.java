package com.illinimotorsports.model.generate;

public class ParsedDataGenerator extends SelectedDataGenerator {
    @Override
    public String generate() {
        //TODO: Generate CSV and return as string
        return getData().toString();
    }
}