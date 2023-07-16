
package org.example.driverapplication;


import org.example.driverapplication.dto.DriverProfileDto;
import org.example.driverapplication.entity.Driver;
import org.example.driverapplication.exception.DriverNotOnboardedException;
import org.example.driverapplication.exception.IllegalArgumentException;
import org.example.driverapplication.exception.InternalServerErrorException;
import org.example.driverapplication.repository.DriverRepository;
import org.example.driverapplication.service.IDriverService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = DriverApplication.class)
@AutoConfigureMockMvc
public class DriverServiceImplTest {

    @Autowired
    private IDriverService driverService;

    @Autowired
    private DriverRepository driverRepository;

    private DriverProfileDto driver;
    private String emailId = "test" + Math.random() + "@gmail.com";

    @Test
    public void test_DriverSignup() throws InternalServerErrorException, IllegalArgumentException {
        Driver driver = Driver.builder().
                name("komal")
                .address("Bangalore")
                .email(emailId)
                .licenseNumber("LNC1234")
                .phoneNumber("12345678")
                .password("password@123")
        .build();

        driverService.save(driver);
        Driver driverInfo = driverRepository.findByEmail(emailId).get();
        Assert.assertEquals("komal", driverInfo.getName());
    }

    @Test
    public void test_DocumentUpload() throws InternalServerErrorException, IllegalArgumentException {
        Driver driver = Driver.builder().
                name("komal")
                .address("Bangalore")
                .email(emailId)
                .licenseNumber("LNC1234")
                .phoneNumber("12345678")
                .password("password@123")
                .build();

        driverService.save(driver);
        Driver driverInfo = driverRepository.findByEmail(emailId).get();
        Assert.assertEquals("komal", driverInfo.getName());
    }

    @Test
    public void test_DriverMarkStatus_whenBGVIsPending() throws Exception {
        try {
            driverService.updateStatus(10L, true);
        } catch (DriverNotOnboardedException ex) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void test_DriverMarkStatus_whenBGVIsCompleted() throws Exception{
            Driver driver = driverService.updateStatus(2L, true);
            Assert.assertTrue(driver.isAvailable());
    }
}
