package com.challenge.tenpe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "startdate")
    private Date startDate;
    @Column(name = "enddate")
    private Date endDate;
    @Column(name = "endpoint")
    private String endpoint;
    @Column(name = "request")
    private String request;
    @Column(name = "response")
    private String response;
    @Column(name = "descriptionerror")
    private String descriptionError;
}
