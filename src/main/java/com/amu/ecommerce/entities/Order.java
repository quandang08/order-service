package com.amu.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "customer_order")
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String reference;

    BigDecimal totalAmount;

    @Enumerated(STRING)
    PaymentMethod paymentMethod;
    String customerId;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderLine> orderLine;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false, nullable = false)
    LocalDateTime lastModifiedDate;
}

