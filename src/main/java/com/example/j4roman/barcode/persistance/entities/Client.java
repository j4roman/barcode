package com.example.j4roman.barcode.persistance.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client", uniqueConstraints = @UniqueConstraint(columnNames = {
        "code"}))
public class Client implements Serializable {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Set<Client2algorithm> client2algorithms = new HashSet<>(0);

    public Client() {
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

    @Column(name = "code", nullable = false, length = 10)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = true, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE, CascadeType.MERGE})
    public Set<Client2algorithm> getClient2algorithms() {
        return client2algorithms;
    }

    public void setClient2algorithms(Set<Client2algorithm> client2algorithms) {
        this.client2algorithms = client2algorithms;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("id=").append(id);
        sb.append(", code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", client2algorithms=").append(client2algorithms);
        sb.append('}');
        return sb.toString();
    }
}
