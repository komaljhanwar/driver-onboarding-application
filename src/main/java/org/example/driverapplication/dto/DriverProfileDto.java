package org.example.driverapplication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.driverapplication.constants.BackgroundCheckStatus;
import org.example.driverapplication.constants.OnboardingStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
public class DriverProfileDto {

    private String name;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private String licenseNumber;
}
