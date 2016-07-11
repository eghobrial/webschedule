/**--------------------------------------------------------------
* webschedule demo
*
* @class: DowntimeFactoryImpl
* @version
* @author
* @since
*
*
*
*
*--------------------------------------------------------------*/

package webschedule.business.downtime;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import com.lutris.dods.builder.generator.query.*;
import com.lutris.appserver.server.sql.ObjectId;

public class DowntimeFactory
{

  public static Downtimec[] getDowntimeList()
 	throws webscheduleBusinessException
 	 {
  	    Downtimec[] theDowntimeArray = null;

	    try {
            DowntimeQuery query = new DowntimeQuery();
	

	        DowntimeDO[] DOarray = query.getDOArray();
	        theDowntimeArray = new Downtimec[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theDowntimeArray[i] = new Downtimec(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getDowntimeList()", ex);
        }

        return theDowntimeArray;
    }
 }