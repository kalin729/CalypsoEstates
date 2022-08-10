package com.kalinkrumov.calypso_estates.model.entity;

import com.kalinkrumov.calypso_estates.model.enums.StatusEnum;

import javax.persistence.*;

@Entity
@Table(name = "statuses")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public Long getId() {
        return id;
    }

    public Status setId(Long id) {
        this.id = id;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Status setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }
}
