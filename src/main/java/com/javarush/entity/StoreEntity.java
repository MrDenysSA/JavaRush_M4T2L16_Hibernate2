package com.javarush.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "store", schema = "movie")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Byte id;

    @OneToOne
    @JoinColumn(name = "manager_staff_id")
    private StaffEntity staff;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

}
