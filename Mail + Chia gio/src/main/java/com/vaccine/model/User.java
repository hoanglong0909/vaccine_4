package com.vaccine.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String userName;

    String CMND;

    String password;

    String phoneNumber;

    String email;

    //    get year only!
    int age;

    String gender;

    //    Huyen
    String district;

    //    Xa~
    String commune;

    String healthyStatus;

    //    Current day;
    String createDay;

    String DateVaccine;

    String TimeVaccine;

    int status;

    @Column(columnDefinition = "integer default '0' ")
    int checkStatus;



    @ManyToOne
    @JoinColumn(name = "id_destination")
    private AdminDestination adminDestination;

}
