//package org.example.driverapplication.controller;
//
//import org.example.driverapplication.constants.ResponseCodeMapping;
//import org.example.driverapplication.exception.MessageResponse;
//import org.example.driverapplication.exception.ResourceNotFoundException;
//import org.example.driverapplication.entity.Driver;
//import org.example.driverapplication.service.DriverServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//public class OnboardingController {
//
//    @Autowired
//    DriverServiceImpl driverServiceImpl;
//
//    /*@PostMapping("/api/drivers/documents")
//    public ResponseEntity<?> uploadDocuments(@RequestParam("file") MultipartFile file, @RequestParam("driverId") Long driverId) {
//        try {
//            Driver driver = driverServiceImpl.getDriver(driverId).orElseThrow(() ->
//                    new ResourceNotFoundException(ResponseCodeMapping.NOT_FOUND.getCode(),
//                            ResponseCodeMapping.DRIVER_NOT_FOUND.getMessage()  + driverId));
//            //String docUrl = storageService.uploadFile(file);
//            driver.setDocumentUrl("docUrl/1234");
//            driverServiceImpl.save(driver);
//            return ResponseEntity.ok(new MessageResponse("Uploaded the file successfully: " + file.getOriginalFilename()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("Could not upload the file: " + file.getOriginalFilename() + "!"));
//        }
//    }
//*/
//    @GetMapping("/api/drivers/verification/{driverId}")
//    public ResponseEntity<?> getVerificationStatus(@PathVariable Long driverId) {
//        Driver driver = driverServiceImpl.getDriver(driverId).orElseThrow(() ->
//                new ResourceNotFoundException(ResponseCodeMapping.NOT_FOUND.getCode(),
//                        ResponseCodeMapping.DRIVER_NOT_FOUND.getMessage()  + driverId));
//        String status = driver.getVerificationStatus();
//        return ResponseEntity.ok(new MessageResponse(status));
//    }
//
//    @PostMapping("/api/drivers/tracking-device")
//    public ResponseEntity<?> requestTrackingDevice(@RequestParam("driverId") Long driverId) {
//        Driver driver = driverServiceImpl.getDriver(driverId).orElseThrow(() ->
//                new ResourceNotFoundException(ResponseCodeMapping.NOT_FOUND.getCode(),
//                        ResponseCodeMapping.DRIVER_NOT_FOUND.getMessage()  + driverId));
//        // logic to request tracking device
//        return ResponseEntity.ok(new MessageResponse("Tracking device requested successfully for driver id: " + driverId));
//    }
//
//
//
//    @PutMapping("/api/drivers/availability/{driverId}")
//    public ResponseEntity<?> markAvailability(@PathVariable Long driverId, @RequestParam("status") boolean status) throws Exception {
//        Driver driver = driverServiceImpl.getDriver(driverId).orElseThrow(() ->
//                new ResourceNotFoundException(ResponseCodeMapping.NOT_FOUND.getCode(),
//                ResponseCodeMapping.DRIVER_NOT_FOUND.getMessage()  + driverId));
//        driver.setAvailable(status);
//        driverServiceImpl.save(driver);
//        return ResponseEntity.ok(new MessageResponse("Availability status updated successfully for driver id: " + driverId));
//    }
//
//
//}
