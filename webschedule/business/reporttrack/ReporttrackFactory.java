/**--------------------------------------------------------------
* webschedule demo
*
* @class: ReportinfoFactoryImpl
* @version
* @author Eman
* @since
*
*
*
*
*--------------------------------------------------------------*/

package webschedule.business.reporttrack;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import com.lutris.appserver.server.Enhydra;
import com.lutris.logging.*;
import com.lutris.dods.builder.generator.query.*;
import com.lutris.appserver.server.sql.ObjectId;

public class ReporttrackFactory
{

  public static Reporttrack[] getReporttrackList()
 	throws webscheduleBusinessException
 	 {
  	    Reporttrack[] theReporttrackArray = null;

	    try {
            ReporttrackQuery query = new ReporttrackQuery();
	

	        ReporttrackDO[] DOarray = query.getDOArray();
	        theReporttrackArray = new Reporttrack[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theReporttrackArray[i] = new Reporttrack(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getReporttrackList()", ex);
        }

        return theReporttrackArray;
    }
    

 public static Reporttrack findReporttrackForMonth (int year, int month, int reportinfo)
  	throws webscheduleBusinessException
    {
	   

	    try {
            ReporttrackQuery query = new ReporttrackQuery();	
            query.setQueryStartyear(year);
            query.setQueryStartmonth(month);
            query.setQueryReportinfo(reportinfo);
            query.requireUniqueInstance();
	   
            ReporttrackDO[] foundReporttrack = query.getDOArray();
	     if    (foundReporttrack.length != 0) 
	     {	        
	    	return new Reporttrack(foundReporttrack[0]);
	      } else {
	       return null;
	      }	
	   
	   
	    } catch(NonUniqueQueryException ex) {
   Enhydra.getLogChannel().write(Logger.DEBUG,"Non-unique user found in database: " +ex.getMessage());
          throw new webscheduleBusinessException("More than one report found  " );
        } catch(DataObjectException ex) {
              throw new webscheduleBusinessException("Database error retrieving user: ", ex);
        } catch(QueryException ex) {
              throw new webscheduleBusinessException("Query exception retrieving user: ", ex);
        }
    }


   public static Reporttrack[] findReporttrackForDate (int year, int month, int day)
  	throws webscheduleBusinessException
    {
	    Reporttrack[] theReporttrackArray = null;

	    try {
            ReporttrackQuery query = new ReporttrackQuery();	
            query.setQueryYear(year);
            query.setQueryMonth(month);
            query.setQueryDay(day);
            query.addOrderByStartday();
            ReporttrackDO[] DOarray = query.getDOArray();
	        theReporttrackArray = new Reporttrack[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theReporttrackArray[i] = new Reporttrack(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findReporttrackForDate()", ex);
        }

        return theReporttrackArray;
    }


 }
