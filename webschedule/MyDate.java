//  Date.java
// Declaration of the Date class.
package webschedule;

public class MyDate {
   private int month;  // 1-12
   private int day;    // 1-31 based on month
   private int year;   // any year

   // Constructor: Confirm proper value for month;
   // call method function checkDay to confirm proper
   // value for day.
   public MyDate( int mn, int dy, int yr )
   {
      if ( mn > 0 && mn <= 12 )       // validate the month
         month = mn;
      else {
         month = 1;
         System.out.println( "Month " + mn + 
                             " invalid. Set to month 1." );
      }

      year = yr;                      // could also check
      day = checkDay( dy );           // validate the day

      System.out.println(
         "Date object constructor for date " + toString() );
   }

   // Utility method to confirm proper day value
   // based on month and year.
   private int checkDay( int testDay )
   {
      int daysPerMonth[] = { 0, 31, 28, 31, 30,
                             31, 30, 31, 31, 30,
                             31, 30, 31 };
   
      if ( testDay > 0 && testDay <= daysPerMonth[ month ] )
         return testDay;
   
      if ( month == 2 &&   // February: Check for leap year
           testDay == 29 &&
           ( year % 400 == 0 ||
             ( year % 4 == 0 && year % 100 != 0 ) ) )
         return testDay;
   
      System.out.println( "Day " + testDay + 
                          " invalid. Set to day 1." );
   
      return 1;  // leave object in consistent state
   }
   
   // Create a String of the form month/day/year
   public String toString()
      { return month + "/" + day + "/" + year; }
}      
