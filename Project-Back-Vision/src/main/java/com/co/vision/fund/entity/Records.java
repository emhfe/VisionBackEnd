package com.co.vision.fund.entity;

import jakarta.persistence.*;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Records {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String firstName;
    private String lastName;
    private String country;
    private String address;
    private String city;
    private String zip;
    private String email;
    private String phone;
    private String birth;
    private List<String> investmentType;
    private List<String> initialDeposit;
    private List<String> investorExperience;
    private List<String> riskTolerance;

    @OneToOne
    @JoinColumn(name = "kycId")
    private FileMetadata kycId;

    @OneToOne
    @JoinColumn(name = "proofAddress")
    private FileMetadata proofAddress;

    @ManyToOne
    @JoinColumn(name = "codRecords")
    private CodRecords codRecords;
}
