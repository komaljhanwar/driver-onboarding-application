package org.example.driverapplication.async.backgroundtask;

import org.example.driverapplication.constants.BackgroundCheckStatus;
import org.example.driverapplication.model.Document;

public interface IBackgroundVerification {
    public BackgroundCheckStatus verify(Document document);
}
