/**--------------------------------------------------------------
* webschedule demo
*
* @class: C_eventFactoryImpl
* @version 
* @author  
* @since   
*
*
*
*
*--------------------------------------------------------------*/

package webschedule.business.c_event;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import webschedule.business.person.Person;
import webschedule.business.project.Project;
import com.lutris.dods.builder.generator.query.*;
import webschedule.business.person.Person;
/*import webschedule.data.PersonDO;*/
import com.lutris.appserver.server.sql.ObjectId;

public class C_eventFactory
{    
    /** 
     * The findC_eventsForPerson method performs a database query to
     * return an array of <CODE>C_event</CODE> objects
     * representing all the rows in the <CODE>disc</CODE> table
     * that have and owner matching <CODE>Person owner</CODE>. 
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static C_event[] findC_eventsForPerson(Person owner)
        throws webscheduleBusinessException
    {
	    C_event[] theC_eventArray = null;
        
	    try {
            C_eventQuery query = new C_eventQuery();
	        // To restrict the set of scheduled events returned
	        // to the that of the specific owner set in the query
	        query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));
            // Order by day
            //query.addOrderByEventday();
	        C_eventDO[] DOarray = query.getDOArray();
	        theC_eventArray = new C_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theC_eventArray[i] = new C_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findC_eventsForPerson()", ex);
        }
        
        return theC_eventArray;
    }


  public static C_event[] findC_eventsForDate (int year, int month, int day)
  	throws webscheduleBusinessException
    {
	    C_event[] theC_eventArray = null;

	    try {
            C_eventQuery query = new C_eventQuery();	
            query.setQueryEventy(year);
            query.setQueryEventm(month);
            query.setQueryEventday(day);
            query.addOrderByStarth();
            C_eventDO[] DOarray = query.getDOArray();
	        theC_eventArray = new C_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theC_eventArray[i] = new C_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findC_eventsForDate()", ex);
        }

        return theC_eventArray;
    }



 public static C_event[] findC_eventsMonthForProject (int year, int month, Project project)
  	throws webscheduleBusinessException
    {
	    C_event[] theC_eventArray = null;

	    try {
            C_eventQuery query = new C_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
            query.setQueryEventy(year);
            query.setQueryEventm(month);

            QueryBuilder qb = query.getQueryBuilder();
            
            query.addOrderByEventday();
            qb.debug();

            C_eventDO[] DOarray = query.getDOArray();
	        theC_eventArray = new C_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theC_eventArray[i] = new C_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findC_eventsForDate()", ex);
        }

        return theC_eventArray;
    }
    
        /*new query for getting event for certain period within the same year for reporting purposes*/
  public static C_event[] findC_eventsYearlyForProject (int year, int startmonth, int endmonth, Project project)
  	throws webscheduleBusinessException
    {
	    C_event[] theC_eventArray = null;

	    try {
            C_eventQuery query = new C_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
            query.setQueryEventy(year);
           

            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(C_eventDO.Eventm,startmonth,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(C_eventDO.Eventm,endmonth,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            C_eventDO[] DOarray = query.getDOArray();
	        theC_eventArray = new C_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theC_eventArray[i] = new C_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findC_eventsForDate()", ex);
        }

        return theC_eventArray;
    }




    public static C_event findC_eventByID(String id)
        throws webscheduleBusinessException
    {
        C_event theC_event = null;

	    try {
            C_eventQuery query = new C_eventQuery();
	        // Require that we only find ONE disc
            query.requireUniqueInstance();
	        query.setQueryOId(new ObjectId(id));
	        C_eventDO theC_eventDO = query.getNextDO();
	    	theC_event = new C_event(theC_eventDO);
            return theC_event;
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findC_eventByID()", ex);
        }
    }


}

