package org.example.driverapplication.utils;

import org.example.driverapplication.dto.DriverProfileDto;
import org.example.driverapplication.exception.IllegalArgumentException;
import org.example.driverapplication.entity.Driver;
import org.springframework.http.HttpStatus;

public class DriverDetailsValidator {

    public static void validateDriver(DriverProfileDto driverProfileDto) throws IllegalArgumentException {
        if (driverProfileDto.getName() == null || driverProfileDto.getName().isEmpty()) {
            throw new IllegalArgumentException(HttpStatus.BAD_REQUEST.name(), "Driver name cannot be empty");
        }
        if (driverProfileDto.getEmail() == null || driverProfileDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException(HttpStatus.BAD_REQUEST.name(), "Driver email cannot be empty");
        }
        if (driverProfileDto.getPassword() == null || driverProfileDto.getPassword().isEmpty() || driverProfileDto.getPassword().length() < 7) {
            throw new IllegalArgumentException(HttpStatus.BAD_REQUEST.name() , "Invalid driverProfileDto password");
        }
        if (driverProfileDto.getPhoneNumber() == null || driverProfileDto.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException(HttpStatus.BAD_REQUEST.name(),"Driver phone number cannot be empty");
        }
        if (driverProfileDto.getLicenseNumber() == null || driverProfileDto.getLicenseNumber().isEmpty()) {
            throw new IllegalArgumentException(HttpStatus.BAD_REQUEST.name(), "Driver license number cannot be empty");
        }
    }
}
