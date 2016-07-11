/**--------------------------------------------------------------
* webschedule 
*
* @class: R_eventFactoryImpl
* @version 
* @author  
* @since   
* @updated April 2005
* @updated April 2006
*
*
*--------------------------------------------------------------*/

package webschedule.business.r_event;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import webschedule.business.person.Person;
import webschedule.business.project.Project;
import com.lutris.dods.builder.generator.query.*;
import webschedule.business.person.Person;
/*import webschedule.data.PersonDO;*/
import com.lutris.appserver.server.sql.ObjectId;

public class R_eventFactory
{    
    /** 
     * The findR_eventsForPerson method performs a database query to
     * return an array of <CODE>R_event</CODE> objects
     * representing all the rows in the <CODE>disc</CODE> table
     * that have and owner matching <CODE>Person owner</CODE>. 
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static R_event[] findR_eventsForPerson(Person owner)
        throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;
        
	    try {
            R_eventQuery query = new R_eventQuery();
	        // To restrict the set of scheduled events returned
	        // to the that of the specific owner set in the query
	        query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));
            // Order by day
            //query.addOrderByEventday();
	        R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForPerson()", ex);
        }
        
        return theR_eventArray;
    }




public static R_event[] findR_eventsForOperator(Person operator)
        throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;
        
	    try {
            R_eventQuery query = new R_eventQuery();
	        // To restrict the set of scheduled events returned
	        // to the that of the specific owner set in the query
	        query.setQueryOwner(PersonDO.createExisting(operator.getHandle()));
            // Order by day
            //query.addOrderByEventday();
	        R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForPerson()", ex);
        }
        
        return theR_eventArray;
    }



  public static R_event[] findR_eventsForDate (int year, int month, int day)
  	throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;

	    try {
            R_eventQuery query = new R_eventQuery();	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);
            query.setQueryEventday(day);
            query.addOrderByStarth();
	    query.addOrderByStartm();
            R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForDate()", ex);
        }

        return theR_eventArray;
    }
    
public static R_event[] findR_eventsForDate (int year, int month, int day,int starth,int startm,int endh,int endm)
  	throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;

	    try {
            R_eventQuery query = new R_eventQuery();	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);
            query.setQueryEventday(day);
	    query.setQueryStarth(starth);
	     query.setQueryStartm(startm);
	      query.setQueryEndh(endh);
	          query.setQueryEndm(endm);
            query.addOrderByStarth();
	    query.addOrderByStartm();
            R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForDate()", ex);
        }

        return theR_eventArray;
    }

    public static R_event[] findR_eventsForDatePerson (int year, int month, int day,Person owner)
  	throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;

	    try {
            R_eventQuery query = new R_eventQuery();	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);
            query.setQueryEventday(day);
            query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));
            query.addOrderByStarth();
            R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForDate()", ex);
        }

        return theR_eventArray;
    }



    /*new query for getting event for certain month for billing purposes*/
    public static R_event[] findR_eventsMonthlyForPerson (int year, int month,int lastday,Person owner)
  	throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;

	    try {
            R_eventQuery query = new R_eventQuery();
            query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);

            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(R_eventDO.Eventday,1,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(R_eventDO.Eventday,lastday,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForDate()", ex);
        }

        return theR_eventArray;
    }



    /*new query for getting event for certain period within the same month for billing purposes*/
  public static R_event[] findR_eventsPeriodForProject (int year, int month,int startday, int endday, Project project)
  	throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;

	    try {
            R_eventQuery query = new R_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);

            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(R_eventDO.Eventday,startday,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(R_eventDO.Eventday,endday,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForDate()", ex);
        }

        return theR_eventArray;
    }
    
     public static R_event[] findR_eventsMonthForProject (int year, int month, Project project)
  	throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;

	    try {
            R_eventQuery query = new R_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);

            QueryBuilder qb = query.getQueryBuilder();
            
            query.addOrderByEventday();
            qb.debug();

            R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForDate()", ex);
        }

        return theR_eventArray;
    }
    
        /*new query for getting event for certain period within the same year for reporting purposes*/
  public static R_event[] findR_eventsYearlyForProject (int year, int startmonth, int endmonth, Project project)
  	throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;

	    try {
            R_eventQuery query = new R_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
            query.setQueryEventyear(year);
           

            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(R_eventDO.Eventmonth,startmonth,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(R_eventDO.Eventmonth,endmonth,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForDate()", ex);
        }

        return theR_eventArray;
    }



    /*new query for getting event for certain period within the same month for notify purposes*/
  public static R_event[] findR_eventsPeriodForAll (int year, int month,int startday, int endday)
  	throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;

	    try {
            R_eventQuery query = new R_eventQuery();
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);

            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(R_eventDO.Eventday,startday,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(R_eventDO.Eventday,endday,QueryBuilder.LESS_THAN_OR_EQUAL);
	    
            query.addOrderByEventday();
            qb.debug();

            R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForDate()", ex);
        }

        return theR_eventArray;
    }


  /*new query for getting event that have start time conf for maintenance account purposes*/
  public static R_event[] findR_eventsStartTimeConf (int year, int month,int day, int starthour, int
  startmin, int endhour, int endmin)
  	throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;

	    try {
            R_eventQuery query = new R_eventQuery();
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);
	    query.setQueryEventday(day);
            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(R_eventDO.Starth,starthour,QueryBuilder.GREATER_THAN_OR_EQUAL);
	    qb.addWhere(R_eventDO.Startm,startmin,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(R_eventDO.Starth,endhour,QueryBuilder.LESS_THAN_OR_EQUAL);
	    qb.addWhere(R_eventDO.Startm,endmin,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForDate()", ex);
        }

        return theR_eventArray;
    }



/*new query for getting events that has an end time conf for maintenance account purposes*/
  public static R_event[] findR_eventsEndTimeConf (int year, int month,int day, int starthour, int
  startmin, int endhour, int endmin)
  	throws webscheduleBusinessException
    {
	    R_event[] theR_eventArray = null;

	    try {
            R_eventQuery query = new R_eventQuery();
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);
	    query.setQueryEventday(day);
            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(R_eventDO.Endh,starthour,QueryBuilder.GREATER_THAN_OR_EQUAL);
	    qb.addWhere(R_eventDO.Endm,startmin,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(R_eventDO.Endh,endhour,QueryBuilder.LESS_THAN_OR_EQUAL);
	    qb.addWhere(R_eventDO.Endm,endmin,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            R_eventDO[] DOarray = query.getDOArray();
	        theR_eventArray = new R_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theR_eventArray[i] = new R_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventsForDate()", ex);
        }

        return theR_eventArray;
    }



    public static R_event findR_eventByID(String id)
        throws webscheduleBusinessException
    {
        R_event theR_event = null;

	    try {
            R_eventQuery query = new R_eventQuery();
	        // Require that we only find ONE disc
            query.requireUniqueInstance();
	        query.setQueryOId(new ObjectId(id));
	        R_eventDO theR_eventDO = query.getNextDO();
	    	theR_event = new R_event(theR_eventDO);
            return theR_event;
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findR_eventByID()", ex);
        }
    }


}

