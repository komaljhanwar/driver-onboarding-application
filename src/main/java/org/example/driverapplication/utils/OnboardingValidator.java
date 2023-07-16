package org.example.driverapplication.utils;

import org.example.driverapplication.constants.ResponseCodeMapping;
import org.example.driverapplication.exception.DriverNotOnboardedException;
import org.example.driverapplication.constants.BackgroundCheckStatus;
import org.example.driverapplication.entity.Driver;

public class OnboardingValidator {

    public static void validate(Driver driver) throws DriverNotOnboardedException {
        if(driver.getBackgroundCheckStatus() == null || !driver.getBackgroundCheckStatus().equals(BackgroundCheckStatus.VERIFIED.name())) {
            throw new DriverNotOnboardedException(ResponseCodeMapping.ON_BOARDING_INCOMPLETE.getCode(),
                    ResponseCodeMapping.ON_BOARDING_INCOMPLETE.getMessage());
        }
    }
}
