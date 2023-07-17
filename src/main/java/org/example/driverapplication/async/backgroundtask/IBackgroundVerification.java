package org.example.driverapplication.async.backgroundtask;

import org.example.driverapplication.constants.BackgroundCheckStatus;
import org.example.driverapplication.model.DocumentInfo;

public interface IBackgroundVerification {
    public BackgroundCheckStatus verify(DocumentInfo document);
}
