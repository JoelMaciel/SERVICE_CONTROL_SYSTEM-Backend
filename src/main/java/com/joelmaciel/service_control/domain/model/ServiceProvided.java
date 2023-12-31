package com.joelmaciel.service_control.domain.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Entity
public class ServiceProvided {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime creationDate;

    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
}
