package com.example.j4roman.barcode.controller.api;

import java.util.List;

public class AlgorithmsList {
    private Long count;
    private List<AlgorithmRequest> items;

    public AlgorithmsList() {
    }

    public AlgorithmsList(List<AlgorithmRequest> items) {
        this.count = (long)items.size();
        this.items = items;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<AlgorithmRequest> getItems() {
        return items;
    }

    public void setItems(List<AlgorithmRequest> items) {
        this.items = items;
    }
}
