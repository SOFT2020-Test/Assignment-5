Feature: Can i create a booking?
  Every Employee must not be able to create booking outside of work hours, or on sundays

 Scenario Outline: Employee can or can not create booking
   Given the booking is within "<workday>", "<startTime>", "<endTime>", and "<employeeId>"
   When  i want to create booking
   Then i should be told "<answer>"

   Examples:
     | workday            | startTime  | endTime | employeeId |answer |
     | 07/12/2020             |      08:00      |    16:00     | 1 | true   |
     | 07/12/2020              |     15:00       |    16:30     | 1 | false   |
     |       07/12/2020         |     07:30       |    12:00     | 1 | false   |
     |       07/12/2020         |     09:15       |    15:15     | 1 | true   |
     |       06/12/2020         |     09:15       |    15:15     | 1 | false   |