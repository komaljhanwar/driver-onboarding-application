package org.example.driverapplication.service;

import org.example.driverapplication.entity.Driver;
import org.example.driverapplication.exception.CustomException;
import org.example.driverapplication.exception.DriverNotOnboardedException;
import org.example.driverapplication.exception.IllegalArgumentException;
import org.example.driverapplication.exception.InternalServerErrorException;

import java.util.Optional;

public interface IDriverService {
    public Driver save(Driver driver) throws IllegalArgumentException, InternalServerErrorException;
    public Optional<Driver> getDriver(Long id);
    public void checkDriverExists(String email) throws CustomException;
    public void onboard(Driver driver);
    public Driver updateStatus(Long driverId, Boolean status) throws InternalServerErrorException, DriverNotOnboardedException;
}
