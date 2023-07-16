package org.example.driverapplication.async.backgroundtask;

import org.example.driverapplication.constants.ShipmentStatus;
import org.example.driverapplication.model.TrackingShipmentInfo;

public interface ITrackingShipment {
    ShipmentStatus ship(TrackingShipmentInfo trackingShipmentInfo);
}
