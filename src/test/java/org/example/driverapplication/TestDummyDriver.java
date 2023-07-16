package org.example.driverapplication;

import org.example.driverapplication.dto.DriverProfileDto;

public class TestDummyDriver {


    public static DriverProfileDto dummyDriverProfile(){
        DriverProfileDto driverProfile = DriverProfileDto.builder()
                .email("test@gmail.com")
                .address("bellandur")
                .licenseNumber("1234")
                .phoneNumber("12345678")
                .password("abcd@123")
                .name("komal")
                .build();
        return driverProfile;

    }
}
