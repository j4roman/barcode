package com.example.j4roman.barcode.service.dto.manage;

import java.util.List;

public class AlgorithmsListResponseDTO {
    private Long count;
    private List<AlgorithmDTO> items;

    public AlgorithmsListResponseDTO() {
    }

    public AlgorithmsListResponseDTO(List<AlgorithmDTO> items) {
        this.count = (long)items.size();
        this.items = items;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<AlgorithmDTO> getItems() {
        return items;
    }

    public void setItems(List<AlgorithmDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlgorithmsListResponseDTO{");
        sb.append("count=").append(count);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}
