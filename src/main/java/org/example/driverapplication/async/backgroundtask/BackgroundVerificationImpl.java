package org.example.driverapplication.async.backgroundtask;

import lombok.extern.log4j.Log4j2;
import org.example.driverapplication.constants.BackgroundCheckStatus;
import org.example.driverapplication.model.Document;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class BackgroundVerificationImpl implements IBackgroundVerification {
    public BackgroundCheckStatus verify(Document document)  {
        return document.getDriverId() % 2 == 0 ? BackgroundCheckStatus.VERIFIED : BackgroundCheckStatus.REJECTED;
    }
}
