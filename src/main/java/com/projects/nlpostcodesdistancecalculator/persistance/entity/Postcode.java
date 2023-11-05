package com.projects.nlpostcodesdistancecalculator.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "postcodes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Postcode {

    @Id
    @Column(name = "id")
    private Long postCodeID;

    @Column(name = "postcode")
    private String postCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = Instant.now();
    }

    @PrePersist
    protected void onCreate() {
        lastUpdated = Instant.now();
    }
}
