package com.example.j4roman.barcode.persistance.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bc_algorithm", uniqueConstraints = @UniqueConstraint(columnNames = {
        "name"}))
public class BCAlgorithm implements Serializable {

    private Long id;
    private String name;
    private String pattern;
    private String description;
    private Set<Action> actions = new HashSet<>(0);
    private Set<Client2algorithm> client2algorithms = new HashSet<>(0);

    public BCAlgorithm() {
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

    @Column(name = "name", nullable = false, length = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "pattern", nullable = false, length = 30)
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Column(name = "description", nullable = true, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bcAlgorithm")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE, CascadeType.MERGE})
    public Set<Action> getActions() {
        return this.actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bcAlgorithm")
    public Set<Client2algorithm> getClient2algorithms() {
        return client2algorithms;
    }

    public void setClient2algorithms(Set<Client2algorithm> client2algorithms) {
        this.client2algorithms = client2algorithms;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BCAlgorithm{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", pattern='").append(pattern).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", actions=").append(actions);
        sb.append(", client2algorithms=").append(client2algorithms);
        sb.append('}');
        return sb.toString();
    }
}

