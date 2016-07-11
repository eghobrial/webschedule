/**--------------------------------------------------------------
* webschedule
*
* @class: ProblemFactory
* @version
* @author Eman Ghobrial
* @since  May 2004
*
*--------------------------------------------------------------*/

package webschedule.business.problem;

import webschedule.data.*;
import webschedule.business.webscheduleBusinessException;
import com.lutris.dods.builder.generator.query.*;
import com.lutris.appserver.server.Enhydra;
import com.lutris.logging.*;
import com.lutris.appserver.server.sql.ObjectId;

public class ProblemFactory
{
     /**
     * Gets the Problem object.
     */
  
  
   public static Problem findProblemByID(String problemID)
        throws webscheduleBusinessException
    {
    	Problem theProblem = null;
        try {
	        ProblemQuery query = new ProblemQuery();
	        // To restrict the set of objects returned
	        // to the that of a certain owner set the
	        // query owner.
	        query.requireUniqueInstance();
	        query.setQueryOId(new ObjectId(problemID));
            // Throw an exception if more than one problem by this name is found
	
	        ProblemDO theProblemDO = query.getNextDO();
	        theProblem = new Problem(theProblemDO);
	        return theProblem;
        } catch(Exception ex) {
              throw new webscheduleBusinessException("Query exception retrieving problem: ", ex);
        }

    }
  

     public static Problem[] getProblemsList()
 	throws webscheduleBusinessException
 	 {
  	    Problem[] theProblemArray = null;

	    try {
            ProblemQuery query = new ProblemQuery();
	
            // Order projects alphabetically by project name
            query.addOrderByPostyear();
	    query.addOrderByPostmonth();
	    query.addOrderByPostday();
	        ProblemDO[] DOarray = query.getDOArray();
	        theProblemArray = new Problem[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theProblemArray[i] = new Problem(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getProblemsList()", ex);
        }

        return theProblemArray;
    }

   
    
    
    
    /*  public static Problem findPerson(String username)
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
