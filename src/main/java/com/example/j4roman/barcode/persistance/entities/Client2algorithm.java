package com.example.j4roman.barcode.persistance.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "client2algorithm", uniqueConstraints = @UniqueConstraint(columnNames = {
        "client_id", "bc_algorithm_id"}))
public class Client2algorithm implements Serializable {
    private Long id;
    private Client client;
    private BCAlgorithm bcAlgorithm;
    private String specValue;

    public Client2algorithm() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bc_algorithm_id", nullable = false)
    public BCAlgorithm getBcAlgorithm() {
        return bcAlgorithm;
    }

    public void setBcAlgorithm(BCAlgorithm bcAlgorithm) {
        this.bcAlgorithm = bcAlgorithm;
    }

    @Column(name = "specValue", nullable = false, length = 15)
    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client2algorithm{");
        sb.append("id=").append(id);
        sb.append(", specValue='").append(specValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
