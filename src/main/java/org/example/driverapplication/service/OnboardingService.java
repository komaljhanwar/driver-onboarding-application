package org.example.driverapplication.service;

import org.example.driverapplication.entity.Driver;
import org.example.driverapplication.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnboardingService implements IOnboardingService{

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    IDriverService driverService;

    public void trigger(Driver driver) {
        driverService.onboard(driver);
    }
}
