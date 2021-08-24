package com.example.j4roman.barcode.persistance.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "action")
public class Action implements Serializable {

    private Long id;
    private Long orderNum;
    private String task;
    private Long ind1;
    private Long ind2;
    private Long count;
    private String description;
    private BCAlgorithm bcAlgorithm;

    public Action() {
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

    @Column(name = "order_num", nullable = false)
    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    @Column(name = "task", nullable = false, length = 10)
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Column(name = "ind1", nullable = true)
    public Long getInd1() {
        return ind1;
    }

    public void setInd1(Long ind1) {
        this.ind1 = ind1;
    }

    @Column(name = "ind2", nullable = true)
    public Long getInd2() {
        return ind2;
    }

    public void setInd2(Long ind2) {
        this.ind2 = ind2;
    }

    @Column(name = "count", nullable = true)
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Column(name = "description", nullable = true, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bc_algorithm_id")
    public BCAlgorithm getBcAlgorithm() {
        return bcAlgorithm;
    }

    public void setBcAlgorithm(BCAlgorithm bcAlgorithm) {
        this.bcAlgorithm = bcAlgorithm;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Action{");
        sb.append("id=").append(id);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", task='").append(task).append('\'');
        sb.append(", ind1=").append(ind1);
        sb.append(", ind2=").append(ind2);
        sb.append(", count=").append(count);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
