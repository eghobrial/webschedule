/**--------------------------------------------------------------
* webschedule 
*
* @class: S_eventFactoryImpl
* @version 
* @author  
* @since   
* @updated April 2005
*
*
*
*--------------------------------------------------------------*/

package webschedule.business.s_event;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import webschedule.business.person.Person;
import webschedule.business.project.Project;
import com.lutris.dods.builder.generator.query.*;
import webschedule.business.operator.Operator;
/*import webschedule.data.PersonDO;*/
import com.lutris.appserver.server.sql.ObjectId;

public class S_eventFactory
{    
    /** 
     * The findS_eventsForPerson method performs a database query to
     * return an array of <CODE>S_event</CODE> objects
     * representing all the rows in the <CODE>disc</CODE> table
     * that have and owner matching <CODE>Person owner</CODE>. 
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static S_event[] findS_eventsForPerson(Person owner)
        throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;
        
	    try {
            S_eventQuery query = new S_eventQuery();
	        // To restrict the set of scheduled events returned
	        // to the that of the specific owner set in the query
	        query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));
            // Order by day
            //query.addOrderByEventday();
	        S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForPerson()", ex);
        }
        
        return theS_eventArray;
    }




public static S_event[] findS_eventsForOperator(Person operator)
        throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;
        
	    try {
            S_eventQuery query = new S_eventQuery();
	    
	        // To restrict the set of scheduled events returned
	        // to the that of the specific owner set in the query
	        query.setQueryOperator(OperatorDO.createExisting(operator.getHandle()));
            // Order by day
            query.addOrderByEventyear();
	    query.addOrderByEventmonth();
	    query.addOrderByEventday();
	        S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForPerson()", ex);
        }
        
        return theS_eventArray;
    }



  public static S_event[] findS_eventsForDate (int year, int month, int day)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);
            query.setQueryEventday(day);
            query.addOrderByStarth();
	    query.addOrderByStartm();
            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }


    public static S_event[] findS_eventsForDatePerson (int year, int month, int day,Person owner)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);
            query.setQueryEventday(day);
            query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));
            query.addOrderByStarth();
            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }



    /*new query for getting event for certain month for billing purposes*/
    public static S_event[] findS_eventsMonthlyForPerson (int year, int month,int lastday,Person owner)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);

            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(S_eventDO.Eventday,1,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Eventday,lastday,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }



    /*new query for getting event for certain period within the same month for billing purposes*/
  public static S_event[] findS_eventsPeriodForProject (int year, int month,int startday, int endday, Project project)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);

            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(S_eventDO.Eventday,startday,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Eventday,endday,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }
    
     public static S_event[] findS_eventsMonthForProject (int year, int month, Project project)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);

            QueryBuilder qb = query.getQueryBuilder();
            
            query.addOrderByEventday();
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }
    
        /*new query for getting event for certain period within the same year for reporting purposes*/
  public static S_event[] findS_eventsYearlyForProject (int year, int startmonth, int endmonth, Project project)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
            query.setQueryEventyear(year);
           

            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(S_eventDO.Eventmonth,startmonth,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Eventmonth,endmonth,QueryBuilder.LESS_THAN_OR_EQUAL);
query.addOrderByEventmonth();            
query.addOrderByEventday();
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }


 /*new query for getting event for certain period within the same first part of FY1 year for reporting purposes*/
  public static S_event[] findS_eventsFY1ForProject (int year, int startmonth, int endmonth, Project project)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
            query.setQueryEventyear(year);
            QueryBuilder qb = query.getQueryBuilder();
            /*qb.addWhere(S_eventDO.Eventmonth,startmonth,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Eventmonth,endmonth,QueryBuilder.LESS_THAN_OR_EQUAL);*/
	    qb.addWhere(S_eventDO.Eventmonth,6,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Eventmonth,11,QueryBuilder.LESS_THAN_OR_EQUAL);
query.addOrderByEventmonth();
            query.addOrderByEventday();
	    
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }

/*new query for getting event for certain period within the same first part of FY2 year for reporting purposes*/
  public static S_event[] findS_eventsFY2ForProject (int year, int startmonth, int endmonth, Project project)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
            query.setQueryEventyear(year);
            QueryBuilder qb = query.getQueryBuilder();
            /*qb.addWhere(S_eventDO.Eventmonth,startmonth,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Eventmonth,endmonth,QueryBuilder.LESS_THAN_OR_EQUAL);*/
	    qb.addWhere(S_eventDO.Eventmonth,0,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Eventmonth,5,QueryBuilder.LESS_THAN_OR_EQUAL);
query.addOrderByEventmonth();
            query.addOrderByEventday();
 
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }
   /*new query for getting event for certain period within the same year for reporting purposes*/
  public static S_event[] findS_eventsForProject ( Project project)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryProj_owner(ProjectDO.createExisting(project.getHandle()));	
          
           

            QueryBuilder qb = query.getQueryBuilder();
           // qb.addWhere(S_eventDO.Eventmonth,startmonth,QueryBuilder.GREATER_THAN_OR_EQUAL);
            //qb.addWhere(S_eventDO.Eventmonth,endmonth,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForProject()", ex);
        }

        return theS_eventArray;
    }

    /*new query for getting event for certain period within the same month for notify purposes*/
  public static S_event[] findS_eventsPeriodForAll (int year, int month,int startday, int endday)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);

            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(S_eventDO.Eventday,startday,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Eventday,endday,QueryBuilder.LESS_THAN_OR_EQUAL);
	    
            query.addOrderByEventday();
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }


  /*new query for getting event for certain period within the same month for notify purposes*/
public static S_event[] findS_eventsTimePeriodForAll (int year,int month,int startday,int endday,int starth,int endh)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);

            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(S_eventDO.Eventday,startday,QueryBuilder.GREATER_THAN_OR_EQUAL);
	    qb.addWhere(S_eventDO.Starth,starth,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Eventday,endday,QueryBuilder.LESS_THAN_OR_EQUAL);
	    qb.addWhere(S_eventDO.Endh,endh,QueryBuilder.LESS_THAN_OR_EQUAL);
	    
            query.addOrderByEventday();
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }

  /*new query for getting event that have start time conf for maintenance account purposes*/
  public static S_event[] findS_eventsStartTimeConf (int year, int month,int day, int starthour, int
  startmin, int endhour, int endmin)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);
	    query.setQueryEventday(day);
            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(S_eventDO.Starth,starthour,QueryBuilder.GREATER_THAN_OR_EQUAL);
	    qb.addWhere(S_eventDO.Startm,startmin,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Starth,endhour,QueryBuilder.LESS_THAN_OR_EQUAL);
	    qb.addWhere(S_eventDO.Startm,endmin,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }



/*new query for getting events that has an end time conf for maintenance account purposes*/
  public static S_event[] findS_eventsEndTimeConf (int year, int month,int day, int starthour, int
  startmin, int endhour, int endmin)
  	throws webscheduleBusinessException
    {
	    S_event[] theS_eventArray = null;

	    try {
            S_eventQuery query = new S_eventQuery();
            query.setQueryEventyear(year);
            query.setQueryEventmonth(month);
	    query.setQueryEventday(day);
            QueryBuilder qb = query.getQueryBuilder();
            qb.addWhere(S_eventDO.Endh,starthour,QueryBuilder.GREATER_THAN_OR_EQUAL);
	    qb.addWhere(S_eventDO.Endm,startmin,QueryBuilder.GREATER_THAN_OR_EQUAL);
            qb.addWhere(S_eventDO.Endh,endhour,QueryBuilder.LESS_THAN_OR_EQUAL);
	    qb.addWhere(S_eventDO.Endm,endmin,QueryBuilder.LESS_THAN_OR_EQUAL);
            query.addOrderByEventday();
            qb.debug();

            S_eventDO[] DOarray = query.getDOArray();
	        theS_eventArray = new S_event[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theS_eventArray[i] = new S_event(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventsForDate()", ex);
        }

        return theS_eventArray;
    }



    public static S_event findS_eventByID(String id)
        throws webscheduleBusinessException
    {
        S_event theS_event = null;

	    try {
            S_eventQuery query = new S_eventQuery();
	        // Require that we only find ONE disc
            query.requireUniqueInstance();
	        query.setQueryOId(new ObjectId(id));
	        S_eventDO theS_eventDO = query.getNextDO();
	    	theS_event = new S_event(theS_eventDO);
            return theS_event;
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findS_eventByID()", ex);
        }
    }


}

