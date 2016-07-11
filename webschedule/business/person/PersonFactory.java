/**--------------------------------------------------------------
* webschedule
*
* @class: PersonFactory
* @version
* @author Eman Ghobrial
* @since  August 2000
*
*--------------------------------------------------------------*/

package webschedule.business.person;

import webschedule.data.*;
import webschedule.business.webscheduleBusinessException;
import com.lutris.dods.builder.generator.query.*;
import com.lutris.appserver.server.Enhydra;
import com.lutris.logging.*;
import com.lutris.appserver.server.sql.ObjectId;

public class PersonFactory
{
     /**
     * Gets the Person object.
     */
    public static Person findPerson(String username)
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

    }

     public static Person[] getPersonsList()
 	throws webscheduleBusinessException
 	 {
  	    Person[] thePersonArray = null;

	    try {
            PersonQuery query = new PersonQuery();
	
            // Order projects alphabetically by project name
            query.addOrderByLastname();
	        PersonDO[] DOarray = query.getDOArray();
	        thePersonArray = new Person[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	thePersonArray[i] = new Person(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getPersonsList()", ex);
        }

        return thePersonArray;
    }

    public static Person findPersonByID(String personID)
        throws webscheduleBusinessException
    {
    	Person thePerson = null;
        try {
	        PersonQuery query = new PersonQuery();
	        // To restrict the set of objects returned
	        // to the that of a certain owner set the
	        // query owner.
	        query.requireUniqueInstance();
	        query.setQueryOId(new ObjectId(personID));
            // Throw an exception if more than one user by this name is found
	
	        PersonDO thePersonDO = query.getNextDO();
	        thePerson = new Person(thePersonDO);
	        return thePerson;
        } catch(Exception ex) {
              throw new webscheduleBusinessException("Query exception retrieving user: ", ex);
        }

    }

}
