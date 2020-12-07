package servicelayer.notifications;

import dto.SmsMessage;

public class SmsServiceImpl implements SmsService {
    @Override
    public boolean sendSms(SmsMessage message) {
        if(message == null || message.getRecipient().isEmpty() || message.getMessage().isEmpty()) {
                return false;
        }
        else {
            return true;
        }
    }
}
