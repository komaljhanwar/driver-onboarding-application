package org.example.driverapplication.async.backgroundtask;

import org.example.driverapplication.constants.ShipmentStatus;
import org.example.driverapplication.model.TrackingShipmentInfo;
import org.springframework.stereotype.Service;

@Service
public class TrackingShipmentImpl implements ITrackingShipment{
    @Override
    public ShipmentStatus ship(TrackingShipmentInfo trackingShipmentInfo) {
        return trackingShipmentInfo.getTrackingId().length() % 2 == 0 ? ShipmentStatus.COMPLETED : ShipmentStatus.IN_PROGRESS;
    }
}
