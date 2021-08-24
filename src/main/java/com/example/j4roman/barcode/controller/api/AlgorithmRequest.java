package com.example.j4roman.barcode.controller.api;

import com.example.j4roman.barcode.controller.utils.BCCheckList;
import com.example.j4roman.barcode.controller.utils.BCCheckNotEmptyAndNull;
import com.example.j4roman.barcode.controller.utils.BCCheckNotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = false)
public class AlgorithmRequest {

    public static final String REQUEST_METHOD_CREATE = "create";
    public static final String REQUEST_METHOD_UPDATE = "update";

    @BCCheckNotEmptyAndNull(requestMethods = {REQUEST_METHOD_CREATE, REQUEST_METHOD_UPDATE})
    private String name;

    @BCCheckNotEmptyAndNull(requestMethods = REQUEST_METHOD_CREATE)
    private String pattern;

    private String description;

    @BCCheckNotNull(requestMethods = REQUEST_METHOD_CREATE)
    @BCCheckList(fieldName = "actions")
    private List<ActionRequest> actions;

    public AlgorithmRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ActionRequest> getActions() {
        return actions;
    }

    public void setActions(List<ActionRequest> actions) {
        this.actions = actions;
    }
}
