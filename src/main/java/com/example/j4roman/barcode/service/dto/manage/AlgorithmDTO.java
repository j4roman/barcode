package com.example.j4roman.barcode.service.dto.manage;

import com.example.j4roman.barcode.controller.utils.BCCheckList;
import com.example.j4roman.barcode.controller.utils.BCCheckNotEmptyAndNull;
import com.example.j4roman.barcode.controller.utils.BCCheckNotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = false)
public class AlgorithmDTO {

    public static final String REQUEST_METHOD_CREATE = "create";
    public static final String REQUEST_METHOD_UPDATE = "update";

    @BCCheckNotEmptyAndNull
    private String name;

    @BCCheckNotEmptyAndNull(requestMethods = REQUEST_METHOD_CREATE)
    private String inPattern;

    @BCCheckNotEmptyAndNull(requestMethods = REQUEST_METHOD_CREATE)
    private String outPattern;

    private String description;

    @BCCheckNotNull(requestMethods = REQUEST_METHOD_CREATE)
    @BCCheckList(fieldName = "actions")
    private List<ActionDTO> actions;

    public AlgorithmDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInPattern() {
        return inPattern;
    }

    public void setInPattern(String inPattern) {
        this.inPattern = inPattern;
    }

    public String getOutPattern() {
        return outPattern;
    }

    public void setOutPattern(String outPattern) {
        this.outPattern = outPattern;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ActionDTO> getActions() {
        return actions;
    }

    public void setActions(List<ActionDTO> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlgorithmDTO{");
        sb.append("name='").append(name).append('\'');
        sb.append(", inPattern='").append(inPattern).append('\'');
        sb.append(", outPattern='").append(outPattern).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", actions=").append(actions);
        sb.append('}');
        return sb.toString();
    }
}
