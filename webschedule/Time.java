// Time.java
// Time1 class definition
package webschedule;   // place Time1 in a package
import java.text.DecimalFormat;  // used for number formatting

public class Time {
   private int hour;     // 0 - 23
   private int minute;   // 0 - 59
   private int second;   // 0 - 59

   // Time1 constructor initializes each instance variable
   // to zero. Ensures that each Time1 object starts in a 
   // consistent state.
   public Time1() { setTime( 0, 0, 0 ); }

   // Set a new time value using military time. Perform 
   // validity checks on the data. Set invalid values 
   // to zero.
   public void setTime( int h, int m, int s )
   {
      hour = ( ( h >= 0 && h < 24 ) ? h : 0 );
      minute = ( ( m >= 0 && m < 60 ) ? m : 0 );
      second = ( ( s >= 0 && s < 60 ) ? s : 0 );
   }

   // Convert time to String in military-time format
   public String toMilitaryString()
   {
      DecimalFormat twoDigits = new DecimalFormat( "00" );

      return twoDigits.format( hour ) +
             twoDigits.format( minute );
   }

   // Convert time to String in standard-time format
   public String toString()
   {
      DecimalFormat twoDigits = new DecimalFormat( "00" );
      
      return ( ( hour == 12 || hour == 0 ) ? 12 : hour % 12 ) +
             ":" + twoDigits.format( minute ) +
             ":" + twoDigits.format( second ) +
             ( hour < 12 ? " AM" : " PM" );
   }
}
