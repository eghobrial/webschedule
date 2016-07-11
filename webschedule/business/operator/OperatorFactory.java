/**--------------------------------------------------------------
* webschedule
*
* @class: OperatorFactory
* @version
* @author Eman Ghobrial
* @since  August 2004
*
*--------------------------------------------------------------*/

package webschedule.business.operator;

import webschedule.data.*;
import webschedule.business.webscheduleBusinessException;
import com.lutris.dods.builder.generator.query.*;
import com.lutris.appserver.server.Enhydra;
import com.lutris.logging.*;
import com.lutris.appserver.server.sql.ObjectId;

public class OperatorFactory
{
     /**
     * Gets the Operator object.
     */
    public static Operator findOperator(String lastname)
        throws webscheduleBusinessException
    {
        try {
	        OperatorQuery query = new OperatorQuery();
	        // To restrict the set of objects returned
	        // to the that of a certain owner set the
	        // query owner.
	        query.setQueryLastName(lastname);
            // Throw an exception if more than one user by this name is found
	        query.requireUniqueInstance();

	        OperatorDO[] foundOperator = query.getDOArray();
	        if(foundOperator.length != 0) {
	    	    return new Operator(foundOperator[0]);
            } else {
                return null;
            }
	    } catch(NonUniqueQueryException ex) {
            Enhydra.getLogChannel().write(Logger.DEBUG,
                                          "Non-unique user found in database: " +
                                          ex.getMessage());
            throw new webscheduleBusinessException("More than one user found with username: " +
                                                    lastname);
        } catch(DataObjectException ex) {
              throw new webscheduleBusinessException("Database error retrieving user: ", ex);
        } catch(QueryException ex) {
              throw new webscheduleBusinessException("Query exception retrieving user: ", ex);
        }

    }


 /**
     * Gets the Operator object.
     */
    public static Operator findOperatorByemail(String lastname, String email)
        throws webscheduleBusinessException
    {
        try {
	        OperatorQuery query = new OperatorQuery();
	        // To restrict the set of objects returned
	        // to the that of a certain owner set the
	        // query owner.
	        query.setQueryLastName(lastname);
		query.setQueryEmail(email);
            // Throw an exception if more than one user by this name is found
	        query.requireUniqueInstance();

	        OperatorDO[] foundOperator = query.getDOArray();
	        if(foundOperator.length != 0) {
	    	    return new Operator(foundOperator[0]);
            } else {
                return null;
            }
	    } catch(NonUniqueQueryException ex) {
            Enhydra.getLogChannel().write(Logger.DEBUG,
                                          "Non-unique user found in database: " +
                                          ex.getMessage());
            throw new webscheduleBusinessException("More than one user found with username: " +
                                                    lastname);
        } catch(DataObjectException ex) {
              throw new webscheduleBusinessException("Database error retrieving user: ", ex);
        } catch(QueryException ex) {
              throw new webscheduleBusinessException("Query exception retrieving user: ", ex);
        }

    }



     public static Operator[] getOperatorsList()
 	throws webscheduleBusinessException
 	 {
  	    Operator[] theOperatorArray = null;

	    try {
            OperatorQuery query = new OperatorQuery();
	
            // Order projects alphabetically by project name
            query.addOrderByLastName();
	        OperatorDO[] DOarray = query.getDOArray();
	        theOperatorArray = new Operator[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theOperatorArray[i] = new Operator(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getOperatorsList()", ex);
        }

        return theOperatorArray;
    }

    public static Operator findOperatorByID(String OperatorID)
        throws webscheduleBusinessException
    {
    	Operator theOperator = null;
        try {
	        OperatorQuery query = new OperatorQuery();
	        // To restrict the set of objects returned
	        // to the that of a certain owner set the
	        // query owner.
	        query.requireUniqueInstance();
	        query.setQueryOId(new ObjectId(OperatorID));
            // Throw an exception if more than one user by this name is found
	
	        OperatorDO theOperatorDO = query.getNextDO();
	        theOperator = new Operator(theOperatorDO);
	        return theOperator;
        } catch(Exception ex) {
              throw new webscheduleBusinessException("Query exception retrieving user: ", ex);
        }

    }

}
