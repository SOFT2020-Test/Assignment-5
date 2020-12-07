package dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingCreation {

    private final Date date;
    private final String start;
    private final String end;
    private final int customerId, employeeId;


    public BookingCreation(int customerId, int employeeId, Date date, String start, String end) {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.date = date;
        this.start = start;
        this.end = end;

    }

    public Date getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public boolean validateWithinWorkingHours() {



        if(date.getDay() != 0 )
        {
            if(date != null && start != null && end != null)
            {
                try {
                    Date time1 = new SimpleDateFormat("HH:mm").parse(start);
                    Calendar bookingStartTime = Calendar.getInstance();
                    bookingStartTime.setTime(time1);
                    bookingStartTime.add(Calendar.DATE, 1);

                    Date time2 = new SimpleDateFormat("HH:mm").parse(end);
                    Calendar bookingEndTime = Calendar.getInstance();
                    bookingEndTime.setTime(time2);
                    bookingEndTime.add(Calendar.DATE, 1);

                    Date refStart = new SimpleDateFormat("HH:mm").parse("08:00");
                    Calendar workStartTime = Calendar.getInstance();
                    workStartTime.setTime(refStart);
                    workStartTime.add(Calendar.DATE, 1);

                    Date refEnd = new SimpleDateFormat("HH:mm").parse("16:00");
                    Calendar workEndTime = Calendar.getInstance();
                    workEndTime.setTime(refEnd);
                    workEndTime.add(Calendar.DATE, 1);
                    if(bookingStartTime.getTime().after(workStartTime.getTime()) || bookingStartTime.getTime().equals(workStartTime.getTime()))
                    {
                        if(bookingEndTime.getTime().before(workEndTime.getTime()) || bookingEndTime.getTime().equals(workEndTime.getTime()))
                        {
                            return true;
                        }

                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }


        return false;
    }
}
