package com.example.j4roman.barcode.service.dto.manage;

import com.example.j4roman.barcode.controller.utils.BCCheckNotEmptyAndNull;
import com.example.j4roman.barcode.controller.utils.BCCheckNotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = false)
public class ActionDTO {
    @BCCheckNotEmptyAndNull
    private String task;
    @BCCheckNotNull(requestMethods = AlgorithmDTO.REQUEST_METHOD_CREATE)
    private Long orderNum;
    private Long ind1;
    private Long ind2;
    private Long count;
    private String description;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Long getInd1() {
        return ind1;
    }

    public void setInd1(Long ind1) {
        this.ind1 = ind1;
    }

    public Long getInd2() {
        return ind2;
    }

    public void setInd2(Long ind2) {
        this.ind2 = ind2;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ActionDTO{");
        sb.append("task='").append(task).append('\'');
        sb.append(", orderNum=").append(orderNum);
        sb.append(", ind1=").append(ind1);
        sb.append(", ind2=").append(ind2);
        sb.append(", count=").append(count);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
