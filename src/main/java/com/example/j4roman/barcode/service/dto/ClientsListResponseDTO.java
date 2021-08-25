package com.example.j4roman.barcode.service.dto;

import java.util.List;

public class ClientsListResponseDTO {

    private Long count;
    private List<ClientDTO> items;

    public ClientsListResponseDTO(List<ClientDTO> items) {
        this.count = (long)items.size();
        this.items = items;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<ClientDTO> getItems() {
        return items;
    }

    public void setItems(List<ClientDTO> items) {
        this.items = items;
    }
}
