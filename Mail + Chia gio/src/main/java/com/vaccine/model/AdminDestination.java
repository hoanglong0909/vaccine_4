package com.vaccine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(name="vaccine_destinations")
public class AdminDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nameDestination;


    @ManyToOne
    @JoinColumn(name = "id_warehouse_vaccine")
    private WarehouseVaccine warehouseVaccine;
}
