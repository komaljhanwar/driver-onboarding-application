package org.example.driverapplication.response;

import lombok.Builder;
import lombok.Data;

import java.sql.DriverPropertyInfo;

@Builder
@Data
public class LoginResponse {

    private String token;

    private DriverPropertyInfo driverPropertyInfo;
}
