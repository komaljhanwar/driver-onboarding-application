package org.example.driverapplication.entity;


import lombok.*;
import org.example.driverapplication.constants.BackgroundCheckStatus;
import org.example.driverapplication.constants.OnboardingStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private String licenseNumber;
    // other fields

    private String documentUrl;

    private boolean available;

    private String onboardingStatus;

    private String backgroundCheckStatus;

    private String shipmentStatus;

    private String trackingId;

    // getters and setters
}
