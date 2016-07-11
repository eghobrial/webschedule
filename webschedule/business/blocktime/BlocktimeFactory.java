/**--------------------------------------------------------------
* webschedule demo
*
* @class: BlocktimeFactoryImpl
* @version
* @author
* @since
*
*
*
*
*--------------------------------------------------------------*/

package webschedule.business.blocktime;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import com.lutris.dods.builder.generator.query.*;
import com.lutris.appserver.server.sql.ObjectId;

public class BlocktimeFactory
{

  public static Blocktimec[] getBlocktimeList()
 	throws webscheduleBusinessException
 	 {
  	    Blocktimec[] theBlocktimeArray = null;

	    try {
            BlocktimeQuery query = new BlocktimeQuery();
	

	        BlocktimeDO[] DOarray = query.getDOArray();
	        theBlocktimeArray = new Blocktimec[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theBlocktimeArray[i] = new Blocktimec(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getBlocktimeList()", ex);
        }

        return theBlocktimeArray;
    }

   public static Blocktimec[] findBlocktimeForDate (int year, int month, int day)
  	throws webscheduleBusinessException
    {
	    Blocktimec[] theBlocktimeArray = null;

	    try {
            BlocktimeQuery query = new BlocktimeQuery();	
            query.setQueryYear(year);
            query.setQueryMonth(month);
            query.setQueryDay(day);
            query.addOrderByStartth();
            BlocktimeDO[] DOarray = query.getDOArray();
	        theBlocktimeArray = new Blocktimec[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theBlocktimeArray[i] = new Blocktimec(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findBlocktimeForDate()", ex);
        }

        return theBlocktimeArray;
    }


 }