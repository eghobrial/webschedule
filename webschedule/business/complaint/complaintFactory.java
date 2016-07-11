/**--------------------------------------------------------------
* webschedule
*
* @class: complaintFactory
* @version
* @author Eman Ghobrial
* @since  Sept 2006
*
*--------------------------------------------------------------*/

package webschedule.business.complaint;

import webschedule.data.*;
import webschedule.business.webscheduleBusinessException;
import com.lutris.dods.builder.generator.query.*;
import com.lutris.appserver.server.Enhydra;
import com.lutris.logging.*;
import com.lutris.appserver.server.sql.ObjectId;

public class complaintFactory
{
     /**
     * Gets the complaint object.
     */
  
  
   public static complaint findcomplaintByID(String complaintID)
        throws webscheduleBusinessException
    {
    	complaint thecomplaint = null;
        try {
	        complaintQuery query = new complaintQuery();
	        // To restrict the set of objects returned
	        // to the that of a certain owner set the
	        // query owner.
	        query.requireUniqueInstance();
	        query.setQueryOId(new ObjectId(complaintID));
            // Throw an exception if more than one complaint by this name is found
	
	        complaintDO thecomplaintDO = query.getNextDO();
	        thecomplaint = new complaint(thecomplaintDO);
	        return thecomplaint;
        } catch(Exception ex) {
              throw new webscheduleBusinessException("Query exception retrieving complaint: ", ex);
        }

    }
  

     public static complaint[] getcomplaintsList()
 	throws webscheduleBusinessException
 	 {
  	    complaint[] thecomplaintArray = null;

	    try {
            complaintQuery query = new complaintQuery();
	
            // Order projects alphabetically by project name
           
	        complaintDO[] DOarray = query.getDOArray();
	        thecomplaintArray = new complaint[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	thecomplaintArray[i] = new complaint(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getcomplaintsList()", ex);
        }

        return thecomplaintArray;
    }

   
    
    
    
    /*  public static complaint findPerson(String username)
        throws webscheduleBusinessException
    {
        try {
	        PersonQuery query = new PersonQuery();
	        // To restrict the set of objects returned
	        // to the that of a certain owner set the
	        // query owner.
	        query.setQueryLogin(username);
            // Throw an exception if more than one user by this name is found
	        query.requireUniqueInstance();

	        PersonDO[] foundPerson = query.getDOArray();
	        if(foundPerson.length != 0) {
	    	    return new Person(foundPerson[0]);
            } else {
                return null;
            }
	    } catch(NonUniqueQueryException ex) {
            Enhydra.getLogChannel().write(Logger.DEBUG,
                                          "Non-unique user found in database: " +
                                          ex.getMessage());
            throw new webscheduleBusinessException("More than one user found with username: " +
                                                    username);
        } catch(DataObjectException ex) {
              throw new webscheduleBusinessException("Database error retrieving user: ", ex);
        } catch(QueryException ex) {
              throw new webscheduleBusinessException("Query exception retrieving user: ", ex);
        }

    }*/

}
