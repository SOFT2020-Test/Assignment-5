package unit.servicelayer.notification;


import datalayer.employee.EmployeeStorage;
import dto.SmsMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;
import servicelayer.notifications.SmsService;
import servicelayer.notifications.SmsServiceException;
import servicelayer.notifications.SmsServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class NotificationTest {

    // SUT (System Under Test)
    private SmsService smsService;
    private Object SmsServiceException;

    @BeforeAll
    public void beforeAll(){
        smsService = new SmsServiceImpl();
    }

    @Test
    public void sendMessage() throws SmsServiceException {
        // Arrange
        // Act
        var recipient = "123123";
        var message = "This is a message!";
        var sms = smsService.sendSms(new SmsMessage(recipient, message));
        assertEquals(true, sms); //We expect the sms to be TRUE
    }

    @Test
    public void sendMessageWithEmptyRecipientExpectFalse() {
        // Arrange
        // Act
        var recipient = "";
        var message = "This is a message!";
        var sms = smsService.sendSms(new SmsMessage(recipient, message));
        assertEquals(false, sms); //We expect the sms to be FALSE because it has no recipient
    }

    @Test
    public void sendMessageWithEmptyMessageExpectFalse() {
        // Arrange
        // Act
        var recipient = "12345678";
        var message = "";
        var sms = smsService.sendSms(new SmsMessage(recipient, message));
        assertEquals(false, sms); //We expect the sms to be FALSE because it has no recipient
    }

    @Test
    public void sendMessageWithEmptyMessageAndRecipientExpectFalse() {
        // Arrange
        // Act
        var recipient = "";
        var message = "";
        var sms = smsService.sendSms(new SmsMessage(recipient, message));
        assertEquals(false, sms); //We expect the sms to be FALSE because it has no recipient
    }
}
