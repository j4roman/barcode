package com.example.j4roman.barcode.service.dto.manage;

import com.example.j4roman.barcode.controller.utils.BCCheckList;
import com.example.j4roman.barcode.controller.utils.BCCheckNotEmptyAndNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = false)
public class ClientDTO {

    public static final String REQUEST_METHOD_CREATE = "create";
    public static final String REQUEST_METHOD_UPDATE = "update";

    @BCCheckNotEmptyAndNull
    private String code;

    @BCCheckNotEmptyAndNull(requestMethods = REQUEST_METHOD_CREATE)
    private String name;
    private String description;

    @BCCheckList(fieldName = "algorithms")
    private List<Client2algorithmDTO> algorithms;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Client2algorithmDTO> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<Client2algorithmDTO> algorithms) {
        this.algorithms = algorithms;
    }
}
