/**--------------------------------------------------------------
* Webschedule
*
* @class: EditEvent for 3TW scanner used to be websch3t 
* Pilot hours 1 to 2 (Rick at June 2003)
* Pilot hours 1 to 1 (Rich March 2004)
* Restricted project added (May 2004)
* Moved the credit pilot hours function to the end of month (Rick April 2005)
* operator field added July 2005
* April 2006 new cancellation policy, is grab activity added
* add saving information about last time operator scanned (Sept 2006)
* new cancellation policy (May 2007)
* Conflict bug fix Nov 2013 added new function
* @version
* @author: Eman Ghobrial
* @since:
*
*--------------------------------------------------------------*/

package webschedule.presentation.s_eventMgmt;

import webschedule.SendMail;
import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.business.s_event.*;
import webschedule.business.blocktime.*;
import webschedule.business.operator.*;
import webschedule.business.operates.*;
import webschedule.presentation.BasePO;
import com.lutris.appserver.server.httpPresentation.*;
import com.lutris.appserver.server.session.*;
import com.lutris.util.*;
import com.lutris.xml.xmlc.*;
import com.lutris.xml.xmlc.html.*;
import org.w3c.dom.*;
import org.w3c.dom.html.*;
import webschedule.business.webscheduleBusinessException;
import webschedule.presentation.webschedulePresentationException;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.lang.String;


//import webschedule.collections.*;
//import webschedule.collections.impl.LList;
import java.util.Enumeration;
import java.sql.Timestamp;



/**
 * EditEvent.java handles the login functionality of the webschedule app.
 *
 */
public class EditEvent extends BasePO
{    
    /**
     * Constants
     */

    private static String ERROR_NAME = "ERROR_NAME";

     private static String DATE = "Date";
    private static String MONTH = "month";
    private static String YEAR = "year";
    private static String STARTH ="starth";
    private static String STARTM ="startm";
    private static String ENDH ="endh";
    private static String ENDM ="endm";
    private static String DESCRIPTION = "description";
    private static String REPEATMENU = "repeatmenu";
    private static String TIMES = "times";
    private static String PERSONID = "personID";
    private static String OP_ID = "op_id";

//    private static String INVALID_DAY = "invalidday";
//    private static String INVALID_MONTH = "invalidmonth";		
    /**
     * Superclass method override
     */
    public boolean loggedInUserRequired()
    {
        return true;
    }
    
    /** 
     *  Default event. Just show the page.
     */
    public String handleDefault() 
        throws HttpPresentationException 
    {
	    return showPage(null);
    }
        


    
    public String handleThrowException()
        throws Exception
    {
        throw new Exception("This is a test exception thrown from Login.java handleThrowException()");    
    }

 public String handleDonotSave()
        throws HttpPresentationException
    {
        System.out.println("Visited donot save ");
	removeConflictlistFromSession();
      removeGoodlistFromSession();
      throw new ClientPageRedirectException(EDIT_EVENT_PAGE);
     }

    /*
     * Adds an event
     *
     */
    public String handleAdd()
        throws HttpPresentationException, webschedulePresentationException
    {          //function
       String repeatinfo = this.getComms().request.getParameter(REPEATMENU);
       System.out.println("Repeat Information = "+repeatinfo);
       String times = this.getComms().request.getParameter(TIMES);
       //String test = null;
       System.out.println("Num of times = "+times);
       int numoftimes =  Integer.parseInt(times);
       S_event s_event = null;
       String personID = this.getComms().request.getParameter(PERSONID);
       System.out.println("Person ID = "+personID);    
       Operator operator = null;
	
       if (personID.equals("invalidID")) {
	this.getSessionData().setUserMessage("Please choose a valid operator, if non listed email eghobrial@ucsd.edu");
	throw new ClientPageRedirectException(EDIT_EVENT_PAGE);}
	else if (personID.equals("notlisted")) {
 	notify_pi();
	 this.getSessionData().setUserMessage("Please choose a valid operator, if non listed email eghobrial@ucsd.edu");
	throw new ClientPageRedirectException(EDIT_EVENT_PAGE);}
 
	else 
	{
      	try {
            operator = OperatorFactory.findOperatorByID(personID);
	    if ( (operator==null) ){
		this.getSessionData().setUserMessage("Please choose a valid operator, if non listed email eghobrial@ucsd.edu");
	throw new ClientPageRedirectException(EDIT_EVENT_PAGE);
	}
	else
	this.setOperator(operator);
       
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("You must choose an operator, if none listed please email eghobrial@ucsd.edu you project info.: " + ex.getMessage());
            throw new webschedulePresentationException("You must choose an operator, if none listed please email eghobrial@ucsd.edu you project info.", ex);
        }
	
}	
			   
        try {           //begin try --1
    		if (repeatinfo.equals("Never"))
    		{       //begin if never
    		 try {             //begin try 2
		 System.out.println("Never executed");    		
    		   s_event = new S_event();
    		    } //end try 2
    		    catch(Exception ex) {//begin catch
                    return showPage("Cannot create a new event");
                 }  //end catch
    		 if (check_conflict()==1) return   showPage("You entered the date that conflict check calendar and try again");
	//added Nov 2013
 	if (check_conflict1()==1) return   showPage("You entered the date that conflict 1 check calendar and try again");
    		else if (check_conflict_blocktime()==1) return showPage ("You entered the date that conflict check calendar and try again");
    		 else if  (check_time()==1) return showPage("You entered a wrong time end time must be after start time");
    		 //remark if you wnat to schedule for previous date
		else if (check_date()==1) return showPage("Yon can not schedule for a previous date or time");
	 	else if (check_proj_hours(numoftimes)==1) return showPage("Approved project hours are completed:please email Eman (eghobrial@ucsd.edu) ");
		 else if  (check_equal()==1) return showPage("You entered a start time that is equal to end time");
		 //else if (check_date()==1) return showPage("Yon cannot schedule for a previous date or time");
		else if (check_proj_expire() == 1) return showPage("Project Expire: please email Eman Ghobrial (eghobrial@ucsd.edu) with updated project information");
		else if (check_restricted(numoftimes)==1) return showPage ("Restricted project you can only schedule short notice & after midnight for 2 hours blocks only");
		else if (personID.equals("invalidID")) return showPage("Yon cannot schedule check email Eman the operator list");		
		 /*else if !(this.getUser().isDeveloper()) */
		 else {        //begin else
    		 saveS_event (s_event);
    		 send_notification(0);
    		 update_projhours(0);
		 updateoperatorlastscan();
//		 update_pilot_proj_hours(0);
    		 throw new ClientPageRedirectException(SELECT_DATE_PAGE);
    		      }          //end else
    		} //end if never
    		else {  //begin else repeat
    		
    		  if  (check_time()==1) return showPage("You entered a wrong time end time must be after start time");
    		  else if (check_date()==1) return showPage("Yon can not schedule for a previous date or time");
    	  else if (check_proj_hours(numoftimes)==1) return showPage("Approved project hours are completed:please email Eman (eghobrial@ucsd.edu) ");	
		  else if  (check_equal()==1) return showPage("You entered a start time that is equal to end time");
    		 else if (check_multi_conflict()==2) return   showPage("All the dates that you entered have conflicts check calendar and try again");
    		 else  if (check_multi_conflict()==1)  throw new ClientPageRedirectException(SHOW_CONFLICTS_PAGE)  ;
                else if (check_multi_expire() == 1) return showPage("Project Expire: please email Eman Ghobrial (eghobrial@ucsd.edu) with updated project information ");		
                else if (check_restricted(numoftimes)==1) return showPage ("Restricted project you can only schedule short notice & after midnight for 2 hours blocks only");
                else if (personID.equals("invalidID")) return showPage("Yon cannot schedule check email Eman the operator list");		
		 else {      //begin else
    		 saveMultiS_event ();
    		 send_notification(numoftimes);
    		 update_projhours(numoftimes);
//		 updateoperatorlastscan();
//		 update_pilot_proj_hours(numoftimes);
    		 throw new ClientPageRedirectException(SELECT_DATE_PAGE);
    		     }        //end else
    		}      //end else repeat
    		
	 } catch(Exception ex) {
                    return showPage("Cannot add a new event fill in all fields");

        }
   // return test;	
    }         //function


   public int check_date()
   throws HttpPresentationException, webschedulePresentationException
   {
      int eventyear = this.getYear();
        int eventmonth = this.getMonth();
        int eventday = this.getDay();

        String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	
    	
        int starthi =  Integer.parseInt(starth);
	int startmi = Integer.parseInt(startm);

        //calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
    	Date cancelinfodate =  cancelinfo.getTime();
    	long cancelinfoms = cancelinfodate.getTime();
    	   	
    	//calculation for the event info
    	Calendar eventinfo = Calendar.getInstance();
    	eventinfo.set(eventyear, eventmonth, eventday, starthi, startmi);
    	Date eventinfodate =   eventinfo.getTime();
    	long eventinfoms = eventinfodate.getTime();
	long difference = eventinfoms - cancelinfoms;
	long numofhours = difference/3600000;
    	
	if (numofhours < 0)
		return 1;
	else 	
        	return 0;
	}
	

public int check_conflict1()
     throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
       //System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	//System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	//System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	//System.out.println("endm selected = "+endm);
    	int event_starthi =  Integer.parseInt(starth);
	int event_endhi = Integer.parseInt(endh);
	int event_startmi = Integer.parseInt(startm);
    	int event_endmi = Integer.parseInt(endm);
    	int year = this.getYear();
        int month = this.getMonth();
        int day = this.getDay();
        int conflict = 0;
        int conflict_has_been_set = 0;
	Date c_eventinfodates, c_eventinfodatee;
	long c_eventinfomss,c_eventinfomse;
          try {
	Calendar s_eventdts = Calendar.getInstance();    //initialize
	Calendar s_eventdte = Calendar.getInstance();    //initialize
	Calendar c_eventdts = Calendar.getInstance();    //initialize
	Calendar c_eventdte = Calendar.getInstance();    //initialize
	s_eventdts.set(year,month,day,event_starthi,event_startmi);
	s_eventdte.set(year,month,day,event_endhi,event_endmi);
	Date s_eventinfodates =   s_eventdts.getTime();
    	long s_eventinfomss = s_eventinfodates.getTime();
	Date s_eventinfodatee =   s_eventdte.getTime();
    	long s_eventinfomse = s_eventinfodatee.getTime();
       	S_event[] EventList = S_eventFactory.findS_eventsForDate(year,month,day);
        // Get collection of events and loop through collection
	for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	
               	S_event currentS_event = EventList[numEvents];
	       	// set text of new cells to values from string array
	       	int starthi = currentS_event.getStarth();
	       	int startmi = currentS_event.getStartm();
	       	int endhi = currentS_event.getEndh();
	       	int endmi = currentS_event.getEndm();
	       	/*int conflict = check_for(starthi,startmi,endhi,endmi,event_starthi,event_startmi,event_endhi,event_endmi);*/
		c_eventdts.set(year,month,day,starthi,startmi);
		c_eventdte.set(year,month,day,endhi,endmi);
		c_eventinfodates =   c_eventdts.getTime();
		c_eventinfomss = c_eventinfodates.getTime();
		c_eventinfodatee =   c_eventdte.getTime();
		c_eventinfomse = c_eventinfodatee.getTime();
		if (s_eventinfomss < c_eventinfomss)
		{
			//if it starts before the current event make sure it ends before current starts
			if (s_eventinfomse > c_eventinfomss)
			{
	          	  conflict = 1;
	          	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	          	  conflict_has_been_set = 1;
	          	  //return conflict;
	          	   break;
	          	}
		}
		else 
		{	// it starts after current event starts make sure it starts after current event ends
			if (s_eventinfomss < c_eventinfomse)
			{
	        	  conflict = 1;
	        	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        	  conflict_has_been_set = 1;
	        	  //return conflict;
	        	   break;
	        	}
		}
	             	   	
                      } // for loop

                     } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);
            throw new webschedulePresentationException("Error getting Events for date: ", ex);
	    }
	
	System.out.println("System conflict flag  "+Integer.toString(conflict));
	if ( conflict_has_been_set == 1)
	        return 1;
	else return 0;
  } //end procedure	


    public int check_conflict()
     throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	
        int event_starthi =  Integer.parseInt(starth);
	int event_endhi = Integer.parseInt(endh);
	int event_startmi = Integer.parseInt(startm);
    	int event_endmi = Integer.parseInt(endm);
    	
    	
	    	
      	int year = this.getYear();
        int month = this.getMonth();
        int day = this.getDay();
        int conflict = 0;
        int conflict_has_been_set = 0;

          try {

          	S_event[] EventList = S_eventFactory.findS_eventsForDate(year,month,day);
          	
	         // Get collection of events and loop through collection
	        for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	
                	S_event currentS_event = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	int starthi = currentS_event.getStarth();
	        	int startmi = currentS_event.getStartm();
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	        	/*int conflict = check_for(starthi,startmi,endhi,endmi,event_starthi,event_startmi,event_endhi,event_endmi);*/
	        	
	        	if (event_starthi < starthi)
	        		{
	        		  if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		
	        		 }
	        		
	    /*    	// 1. if it is equal check min and end time	
	        	else if (event_starthi == starthi)
	        		{
	        		 if (event_startmi >= startmi)
	        		   {  	//if
	        		   conflict = 1;
	        		   System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		    conflict_has_been_set = 1;
	        		   //return conflict;
	        		   break;
	        		   }        //if
	        		   else {//else 1
	        		
	        			if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		   }  //else 1
	        		 }
	        		*/
			// 1. if it is equal check min and end time	
	        	else if (event_starthi == starthi)
	        		{
				// made == instead of >=
	        		 if (event_startmi == startmi)
	        		   {  	//if
				   //check to see if the prevoius event is only half an hour
				    
	        		   conflict = 1;
	        		   System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		    conflict_has_been_set = 1;
	        		     break;
	        		   
				   }        //if
	        		   else {//else 1
	        		// changed > to < 
	        			if (event_endhi < starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		     else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		   }  //else 1
	        		 }
	        			
				
	        	
                       //1.    if it is greater than the booked on make sure it is not in the middle
                       else if (event_starthi > starthi)
                       		{
                       		if (event_starthi < endhi)
                       		 {
                       		   conflict = 1;
                       		   System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		   conflict_has_been_set = 1;
                       		   break;
                       		 }
                       		else if (event_starthi == endhi)
                       		    {
                       		      if (event_startmi < endmi)
                       		      {
                       		
                       		      	conflict = 1;
                       		      	 System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		      	  conflict_has_been_set = 1;
                       		      	 //return conflict;
                       		      	 break;
                       		      	  }
                       		    }
                       		
                       		}
	             	        	
                      } // for loop

                     } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);
            throw new webschedulePresentationException("Error getting Events for date: ", ex);
	    }
	
	System.out.println("System conflict flag  "+Integer.toString(conflict));
	if ( conflict_has_been_set == 1)
	        return 1;
	else return 0;
 	 	
  } //end procedure	




  public int check_conflict_blocktime()
     throws HttpPresentationException, webschedulePresentationException
    {	
     String starth = this.getComms().request.getParameter(STARTH);
     String startm = this.getComms().request.getParameter(STARTM);
     String endh = this.getComms().request.getParameter(ENDH);
     String endm = this.getComms().request.getParameter(ENDM);

     int event_starthi =  Integer.parseInt(starth);
     int event_startmi =  Integer.parseInt(startm);
     int event_endhi =     Integer.parseInt(endh);
     int event_endmi =   Integer.parseInt(endm);

     int yeari = this.getYear();
     int monthi = this.getMonth();
     int dayi = this.getDay();
     int conflict = 0;
     int conflict_has_been_set = 0;


       try {

          	Blocktimec[] EventList = BlocktimeFactory.findBlocktimeForDate(yeari,monthi,dayi);
          	
	         // Get collection of events and loop through collection
	        for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	//begin for loop
                	Blocktimec currentBlocktimec = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	int starthi = currentBlocktimec.getStarth();
	        	int startmi = currentBlocktimec.getStartm();
	        	int endhi = currentBlocktimec.getEndh();
	        	int endmi = currentBlocktimec.getEndm();
	        	/*int conflict = check_for(starthi,startmi,endhi,endmi,event_starthi,event_startmi,event_endhi,event_endmi);*/
	        	
	        	if (event_starthi < starthi)
	        		{      //begin if --1
	        		  if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		
	        		 }       //end if --1
	        		
	        	// 1. if it is equal check min and end time	
	        	else if (event_starthi == starthi)
	        		{
	        		 if (event_startmi >= startmi)
	        		   {  	//if
	        		   conflict = 1;
	        		   System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		    conflict_has_been_set = 1;
	        		   //return conflict;
	        		   break;
	        		   }        //if
	        		   else {//else 1
	        		
	        			if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		   }  //else 1
	        		 }
	        		
	        	
                       //1.    if it is greater than the booked on make sure it is not in the middle
                       else if (event_starthi > starthi)
                       		{
                       		if (event_starthi < endhi)
                       		 {
                       		   conflict = 1;
                       		   System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		   conflict_has_been_set = 1;
                       		   break;
                       		 }
                       		else if (event_starthi == endhi)
                       		    {
                       		      if (event_startmi < endmi)
                       		      {
                       		
                       		      	conflict = 1;
                       		      	 System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		      	  conflict_has_been_set = 1;
                       		      	 //return conflict;
                       		      	 break;
                       		      	  }
                       		    }
                       		
                       		}
	             	        	
                      } // for loop

                     } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);
            throw new webschedulePresentationException("Error getting Events for date: ", ex);
	    }
	
	System.out.println("System conflict flag  "+Integer.toString(conflict));
	if ( conflict_has_been_set == 1)
	        return 1;
	else return 0;
 	 	
  } //end procedure	

      
 public int check_multi_expire()
        throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	String description = this.getComms().request.getParameter(DESCRIPTION);
    	
        int event_starthi =  Integer.parseInt(starth);
	int event_endhi = Integer.parseInt(endh);
	int event_startmi = Integer.parseInt(startm);
    	int event_endmi = Integer.parseInt(endm);
    	
    	String repeatinfo = this.getComms().request.getParameter(REPEATMENU);
        String times = this.getComms().request.getParameter(TIMES);
        int numoftimes =  Integer.parseInt(times);
	    	
      	int year = this.getYear();
        int month = this.getMonth();
        int day = this.getDay();
        int event_conflict = 0;
	int dow, nexteventday, nexteventmonth, nexteventyear;
        int incrementvalue = 0;
	 if  (repeatinfo.equals("week"))
          incrementvalue = 7;
        else incrementvalue = 14;

        Calendar eventinfo = Calendar.getInstance();    //initialize
        eventinfo.set(year, month, day); //first event
        dow =  eventinfo.get(eventinfo.DAY_OF_WEEK);
        //check the conflict for the first event
        event_conflict = checkeventexpire(year, month, day);
	
	 for (int t=2; t<=numoftimes; t++)
        { //for loop
	   eventinfo.add(eventinfo.DAY_OF_MONTH, incrementvalue);
           nexteventday = eventinfo.get(eventinfo.DAY_OF_MONTH);
           System.out.println("Next Event Day  "+Integer.toString(nexteventday));
           nexteventmonth = eventinfo.get(eventinfo.MONTH);
           System.out.println("Next Event Month  "+Integer.toString(nexteventmonth));
           nexteventyear = eventinfo.get(eventinfo.YEAR);
           System.out.println("Next Event Year  "+Integer.toString(nexteventyear));
           event_conflict = checkeventexpire(nexteventyear,nexteventmonth,nexteventday);
	   if (event_conflict == 1)
	    break;
	 }
	 
	    	
	if (event_conflict == 1)
        {
 	  return 1;
 	}     else return  0;
	
}	
	          		


   public int check_multi_conflict()
     throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	String description = this.getComms().request.getParameter(DESCRIPTION);
    	
        int event_starthi =  Integer.parseInt(starth);
	int event_endhi = Integer.parseInt(endh);
	int event_startmi = Integer.parseInt(startm);
    	int event_endmi = Integer.parseInt(endm);
    	
    	String repeatinfo = this.getComms().request.getParameter(REPEATMENU);
        String times = this.getComms().request.getParameter(TIMES);
        int numoftimes =  Integer.parseInt(times);
	    	
      	int year = this.getYear();
        int month = this.getMonth();
        int day = this.getDay();
        int event_conflict = 0;
	int expire_conflict = 0;
        int dow, nexteventday, nexteventmonth, nexteventyear;
        int incrementvalue = 0;

        LinkedList conflict_list = new LinkedList();
    	LinkedList good_list = new LinkedList();
    	
    	int conflictvalue = 0;
   	

        if  (repeatinfo.equals("week"))
          incrementvalue = 7;
        else incrementvalue = 14;

        Calendar eventinfo = Calendar.getInstance();    //initialize
        eventinfo.set(year, month, day); //first event
        dow =  eventinfo.get(eventinfo.DAY_OF_WEEK);
        //check the conflict for the first event
        event_conflict = checkeventconflict(year,month,day, event_starthi, event_endhi, event_startmi,event_endmi);
	
	//expire_conflict = checkeventexpire(year,month,day);
        
	if (event_conflict == 1)
          conflict_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, day, month,year,dow));
	else
       	  good_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, day, month,year,dow));

        /*if (expire_conflict == 1)
          conflict_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, day, month,year,dow));
	else
       	  good_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, day, month,year,dow));*/
	  
	  
        for (int t=2; t<=numoftimes; t++)
        { //for loop
	   eventinfo.add(eventinfo.DAY_OF_MONTH, incrementvalue);
           nexteventday = eventinfo.get(eventinfo.DAY_OF_MONTH);
           System.out.println("Next Event Day  "+Integer.toString(nexteventday));
           nexteventmonth = eventinfo.get(eventinfo.MONTH);
           System.out.println("Next Event Month  "+Integer.toString(nexteventmonth));
           nexteventyear = eventinfo.get(eventinfo.YEAR);
           System.out.println("Next Event Year  "+Integer.toString(nexteventyear));
           dow =  eventinfo.get(eventinfo.DAY_OF_WEEK);
         event_conflict = checkeventconflict(nexteventyear,nexteventmonth,nexteventday, event_starthi, event_endhi, event_startmi,event_endmi);
//       	expire_conflict = checkeventexpire(year,month,day);  

	 if (event_conflict == 1)
           conflict_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, nexteventday, nexteventmonth,nexteventyear,dow));
	 else
       	   good_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, nexteventday, nexteventmonth,nexteventyear,dow));
         }//for loop

 	/*if (expire_conflict == 1)
          conflict_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, day, month,year,dow));
	else
       	 good_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, day, month,year,dow));*/
	  

         System.out.println("Conflict list Size  "+Integer.toString(conflict_list.size()));
          System.out.println("Good list Size  "+Integer.toString(good_list.size()));
         if (conflict_list.size() == 0)
         //there are no  conflicts
         {
         this.setGoodlist(good_list);
         conflictvalue=0;
         }
         else if (conflict_list.size() > 0) //there is some conflicts
           {
           if (good_list.size() == 0)
            conflictvalue = 2; //there is no good events
           else
           {
           this.setConflictlist(conflict_list);
           this.setGoodlist(good_list);
           conflictvalue = 1; //there are some good and conflict events
           }
           }
         return conflictvalue;
    }


    public int checkeventexpire (int year, int month, int day)
      throws HttpPresentationException, webschedulePresentationException
    {

        int expday;
	int expmonth;
	int expyear;
    	
	Calendar eventinfo = Calendar.getInstance();
    	eventinfo.set(year,month,day);    			

	Date eventinfodate =   eventinfo.getTime();
    	long eventlong = eventinfodate.getTime();

	
	
    	Project theProject = this.getProject();
    	
    	try
    	{
    	expday = theProject.getExpday();
	expmonth = theProject.getExpmonth();
	expyear = theProject.getExpyear();
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
	
	Calendar expdateinfo = Calendar.getInstance();
	expdateinfo.set(expyear, expmonth, expday);
	
	Date expdate =   expdateinfo.getTime();
    	long expdatelong = expdate.getTime();
	
        if (eventlong <= expdatelong)
        {
 	  return 0;
 	}     else return  1;
	
  }
   





    public int checkeventconflict (int year, int month, int day, int event_starthi, int event_endhi, int event_startmi, int event_endmi)
      throws HttpPresentationException, webschedulePresentationException
    {

    int conflict = 0;
        int conflict_has_been_set = 0;

          try {

          	S_event[] EventList = S_eventFactory.findS_eventsForDate(year,month,day);
          	
	         // Get collection of events and loop through collection
	        for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	
                	S_event currentS_event = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	int starthi = currentS_event.getStarth();
	        	int startmi = currentS_event.getStartm();
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	        	/*int conflict = check_for(starthi,startmi,endhi,endmi,event_starthi,event_startmi,event_endhi,event_endmi);*/
	        	
	        	if (event_starthi < starthi)
	        		{
	        		  if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		
	        		 }
	        		
	        	// 1. if it is equal check min and end time	
	        	else if (event_starthi == starthi)
	        		{
	        		 if (event_startmi >= startmi)
	        		   {  	//if
	        		   conflict = 1;
	        		   System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		    conflict_has_been_set = 1;
	        		   //return conflict;
	        		   break;
	        		   }        //if
	        		   else {//else 1
	        		
	        			if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		   }  //else 1
	        		 }
	        		
	        	
                       //1.    if it is greater than the booked on make sure it is not in the middle
                       else if (event_starthi > starthi)
                       		{
                       		if (event_starthi < endhi)
                       		 {
                       		   conflict = 1;
                       		   System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		   conflict_has_been_set = 1;
                       		   break;
                       		 }
                       		else if (event_starthi == endhi)
                       		    {
                       		      if (event_startmi < endmi)
                       		      {
                       		
                       		      	conflict = 1;
                       		      	 System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		      	  conflict_has_been_set = 1;
                       		      	 //return conflict;
                       		      	 break;
                       		      	  }
                       		    }
                       		
                       		}
	             	        	
                      } // for loop

                     } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);
            throw new webschedulePresentationException("Error getting Events for date: ", ex);
	    }
	
	System.out.println("System conflict flag  "+Integer.toString(conflict));
	if ( conflict_has_been_set == 1)
	        return 1;
	else return 0;
 	 	
  } //end procedure	

                       		
    public int check_time()
    throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	

	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);		
	    			    			
	
/*	Calendar eventinfo = Calendar.getInstance();
    	int todayday = eventinfo.get(eventinfo.DAY_OF_MONTH);
    	int todaymonth = eventinfo.get(eventinfo.MONTH);
    	int todayyear = eventinfo.get(eventinfo.YEAR);
 	eventinfo.set(this.getYear(),this.getMonth(),this.getDay(),starthi-1,endhi-1);   */
 	
 	if ((endhi < starthi) || ((endhi == starthi) && (endmi < startmi)))
 	{
 	  return 1;
 	}     else return  0;
} 	


    public int check_equal()
    throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	

	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);		
	    			    			
	

 	if ((endhi == starthi)  && (endmi == startmi))
 	{
 	  return 1;
 	}     else return  0;
} 	


 protected int check_proj_hours(int numoftimes)
   throws HttpPresentationException
   {
   	double starttime;
   	double endtime;
   	double donehours;
   	double totalhours;
   	double eventhours;
       	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	
    	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);
    	
    	if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    	else starttime = (double) starthi;
    	
    	if (endmi == 30)
    		endtime =     endhi + 0.5;
    	else endtime = (double) endhi;	
    	eventhours = endtime - starttime;
    	
    	Project theProject = this.getProject();
    	
    	try
    	{
    	//donehours = theProject.getDonehours();
	donehours = getActDonehours(theProject);
	System.out.println("done hours off adding an event = "+Double.toString(donehours));
	
    	totalhours = theProject.getTotalhours();
	System.out.println("total hours off adding an event"+Double.toString(totalhours));
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
        if (numoftimes == 0)
        {
        if ((totalhours-donehours) >= eventhours)
 	{
 	  return 0;
 	}     else return  1;
 	} else {
 	if ((totalhours-donehours) >= eventhours*numoftimes)
 	{
 	  return 0;
 	}     else return  1;
 	}
  }
  
   protected int check_restricted(int numoftimes)
   throws HttpPresentationException
   {
   	double starttime;
   	double endtime;
   	/*double donehours;
   	double totalhours;*/
   	double eventhours;
	int codeofpay = 0;
       	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	
    	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);
	
	
	int eventyear = this.getYear();
        int eventmonth = this.getMonth();
        int eventday = this.getDay();

        
        //calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
    	Date cancelinfodate =  cancelinfo.getTime();
    	long cancelinfoms = cancelinfodate.getTime();
    	   	
    	//calculation for the event info
    	Calendar eventinfo = Calendar.getInstance();
    	eventinfo.set(eventyear, eventmonth, eventday, starthi, startmi);
    	Date eventinfodate =   eventinfo.getTime();
    	long eventinfoms = eventinfodate.getTime();
	long difference = eventinfoms - cancelinfoms;
	long numofhours = difference/3600000;
   	System.out.println("number of hours difference = " + Long.toString(numofhours));
	
    	if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    	else starttime = (double) starthi;
    	
    	if (endmi == 30)
    		endtime =     endhi + 0.5;
    	else endtime = (double) endhi;	
    	eventhours = endtime - starttime;
    	
    	Project theProject = this.getProject();
    	
    	try
    	{
    	codeofpay = theProject.getCodeofpay();
	System.out.println("code of pay = "+Integer.toString(codeofpay));
		
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }

	if (codeofpay == 100)
	{
	
	System.out.println("Start time (code 100)= " + Double.toString(starttime));
	System.out.println("End time (code 100)= " + Double.toString(endtime));
	 if ((starttime >= 0) && (starttime <= 7) && (endtime >= 0) && (endtime <= 7))
	      return 0;
	     else
		{	     
        	 
	       if (numofhours > 2)
		        return 1;
	             else 
		   if (eventhours > 2)
		        return 1;
		  else	
        	       return 0;
	       	   }
	   } else {
	//return 0 if the project does not have code 100 (not restriced)
	return 0;
	}
  }
  
  
  
/*   protected void update_pilot_proj_hours(int numoftimes)
   throws HttpPresentationException
   {
   	double starttime;
   	double endtime;
   	double donehours;
   	double totalhours;
   	double eventhours;
       	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	
    	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);
    	
    	if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    	else starttime = (double) starthi;
    	
    	if (endmi == 30)
    		endtime =     endhi + 0.5;
    	else endtime = (double) endhi;	
    	eventhours = endtime - starttime;
			
    	if (numoftimes == 0)
    	eventhours = endtime - starttime;
    	else eventhours =  (endtime - starttime) * numoftimes;
	
	String proj_name, pilot_proj_name;
	
    	Project theProject = this.getProject();
    	
	Project pilotProject = null;
	
	try 
	{
	proj_name = theProject.getProj_name();
	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
	
    	pilot_proj_name = "Pilot"+proj_name;
	
	
	
    	try
    	{
    	pilotProject = ProjectFactory.findProjectByProj_name(pilot_proj_name);
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
        
	if (pilotProject != null)
	{
	try
    	{    	
    		
    	totalhours = pilotProject.getTotalhours()+(eventhours);
	System.out.println("total hours = "+totalhours);
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }

    try {
            pilotProject.setProj_name(pilotProject.getProj_name());
            pilotProject.setPassword(pilotProject.getPassword());
	    pilotProject.setDescription(pilotProject.getDescription());
	    pilotProject.setIndexnum(pilotProject.getIndexnum());
	    pilotProject.setTotalhours(totalhours);
	    pilotProject.setDonehours(pilotProject.getDonehours());
            pilotProject.setCodeofpay(pilotProject.getCodeofpay());
            pilotProject.setOwner(this.getUser());
	    pilotProject.setContactname(pilotProject.getContactname());
	    pilotProject.setContactphone(pilotProject.getContactphone());
	    pilotProject.setBilladdr1(pilotProject.getBilladdr1());
            pilotProject.setBilladdr2(pilotProject.getBilladdr2());
	    pilotProject.setBilladdr3(pilotProject.getBilladdr3());
	    pilotProject.setCity(pilotProject.getCity());
	    pilotProject.setState(pilotProject.getState());
	    pilotProject.setZip(pilotProject.getZip());
	    pilotProject.setAccountid(pilotProject.getAccountid());
	    pilotProject.setOutside(pilotProject.isOutside());
	    pilotProject.setExpday(pilotProject.getExpday());
	    pilotProject.setExpmonth(pilotProject.getExpmonth());
	    pilotProject.setExpyear(pilotProject.getExpyear());
	   
	        pilotProject.save();	
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding pilot project", ex);
        }
	
	}
	
  }
  
  */
   protected int check_proj_expire()
   throws HttpPresentationException
   {
        int expday;
	int expmonth;
	int expyear;
    	
	Calendar eventinfo = Calendar.getInstance();
    	int todayday = eventinfo.get(eventinfo.DAY_OF_MONTH);
    	int todaymonth = eventinfo.get(eventinfo.MONTH);
    	int todayyear = eventinfo.get(eventinfo.YEAR);
 	eventinfo.set(this.getYear(),this.getMonth(),this.getDay());    
	
	Date eventinfodate =   eventinfo.getTime();
    	long eventlong = eventinfodate.getTime();
	
    	Project theProject = this.getProject();
    	
    	try
    	{
    	expday = theProject.getExpday();
	expmonth = theProject.getExpmonth();
	expyear = theProject.getExpyear();
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
	
	Calendar expdateinfo = Calendar.getInstance();
	expdateinfo.set(expyear, expmonth, expday);
	
	Date expdate = expdateinfo.getTime();
	long expdatelong = expdate.getTime();
	
        if (eventlong <= expdatelong)
        {
 	  return 0;
 	}     else return  1;
	
  }
   
   
    
    /**
     * 
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
    	String temptext = null;
	String mytext = null;
	int flagid = 0;
	String Proj_text = null;
    	int temphour;
    	String starth = this.getComms().request.getParameter(STARTH);
    	String startm = this.getComms().request.getParameter(STARTM);
    	String endh = this.getComms().request.getParameter(ENDH);
    	String endm = this.getComms().request.getParameter(ENDM);
    	String description = this.getComms().request.getParameter(DESCRIPTION);
    	String repeatinfo = this.getComms().request.getParameter(REPEATMENU);
        System.out.println("Repeat Information = "+repeatinfo);
        /*String times = this.getComms().request.getParameter(TIMES);
         System.out.println("Num of times = "+times);*/
        EditEventHTML page = new EditEventHTML();
	Blocktimec[] BlockList = null;
	int nofbe;
	String project_name = null;
	String endmbs = null;
	String startmbs = null;
	String startms = null;
	String endms = null;
	String personID = this.getComms().request.getParameter(PERSONID);


         int year = this.getYear();
        int month = this.getMonth();
        int day = this.getDay();
         String projectname = null;

 
	 
            HTMLOptionElement templateOption = page.getElementTemplateOption();
        Node PersonSelect = templateOption.getParentNode();
        templateOption.removeAttribute("id");
        templateOption.removeChild(templateOption.getFirstChild());

 try {
        	Operates[] OperatesList = OperatesFactory.getOperatorsListforProj(this.getProject());
		//String proj_id = this.getProject().getHandle();
		//System.out.println("Project ID off EditEvent = "+proj_id);
		for (int numOperates=0; numOperates < OperatesList.length; numOperates++) {
		  Operates currentOperates = OperatesList[numOperates];
		 //  System.out.println("Project ID off EditEvent Operates = "+currentOperates.getProjectID());
		 //  if (currentOperates.getProjectID().equals(proj_id))
		    //    {
		int lsd = currentOperates.getOperatorLSD();
			int lsm = currentOperates.getOperatorLSM();
			int lsy = currentOperates.getOperatorLSY();
			int recd = currentOperates.getOperatorReCD();
			int recm = currentOperates.getOperatorReCM();
			int recy = currentOperates.getOperatorReCY();
			Timestamp ts = currentOperates.getOperatorSFTTS();
			boolean isExp = currentOperates.getOperatorIsExp();
	int cstatus  =	check_opstatus(lsd, lsm, lsy,recd,recm,recy,ts,isExp);
	if (cstatus == 0)
		{		
		     HTMLOptionElement clonedOption = (HTMLOptionElement) templateOption.cloneNode(true);
                    clonedOption.setValue(currentOperates.getOperatorID());
                    Node optionTextNode = clonedOption.getOwnerDocument().
                          createTextNode(currentOperates.getOperatorFN() + " " +
                                        currentOperates.getOperatorLN());
                clonedOption.appendChild(optionTextNode);Calendar eventinfo = Calendar.getInstance();    //initialize
        eventinfo.set(year, month, day); //first event
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                PersonSelect.appendChild(clonedOption);
		  }  
		//	}
		
		}
        	
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Operates List: " + ex);
            throw new webschedulePresentationException("Error getting Operates List: ", ex);
	    }
	
	
  templateOption.getParentNode().removeChild(templateOption);



       String eventdatestring = Integer.toString(month+1)+"/"+Integer.toString(day)+"/"+Integer.toString(year);
            try
            {
       		 projectname = this.getProject().getProj_name();
          } catch (webscheduleBusinessException ex) {
         throw new webschedulePresentationException("Error getting project name", ex);
         }

        page.setTextDateID ("Scheduling for date : " + eventdatestring);
        page.setTextProject_name(projectname);

        HTMLTableRowElement templateRow = page.getElementTemplateRow();
        Node EventTable = templateRow.getParentNode();

         templateRow.removeAttribute("id");

         try {
	        S_event[] EventList = S_eventFactory.findS_eventsForDate(year,month,day);
	
	        BlockList = BlocktimeFactory.findBlocktimeForDate (year,month,day);
                nofbe = BlockList.length;
            //    System.out.println(" ***Number of Block events ***" + Integer.toString(nofbe));
	

               if (EventList.length==0)
         {      //begin else if no events
          for (int numBlockE = 0; numBlockE < nofbe; numBlockE ++)   {// begin Block event for loop
	        Blocktimec BlockE = BlockList[numBlockE];
	        int starthb = BlockE.getStarth();
	        int startmb = BlockE.getStartm();
		int endhb = BlockE.getEndh();
	        int endmb = BlockE.getEndm();		        	
	                String ampm = null;
	               	if (starthb < 12) ampm = "am";
      			else if (starthb == 12) ampm = "Noon";
			else if (starthb == 24) { //begin else if
      				starthb = starthb-12;
				ampm = "Mid.";
      				}          //end else if
			else {     //begin else
      				starthb = starthb -12;
				ampm = "pm";
		        	}		       //end else
				
				if (startmb == 0) {
				 startmbs = "00" ;
				 }else {
				 startmbs = "30";}
	        		String starttime = Integer.toString(starthb)+":"+startmbs+" "+ampm;
				
	        	
	        	 if (endhb < 12) ampm = "am";
      			else if (endhb == 12) ampm = "Noon";
			else if (endhb == 24) {       //begin else if
      			endhb = endhb-12;
			ampm = "Mid.";
      				}         //end else if
		      		else {                 //begin else
      					endhb = endhb -12;
		      			ampm = "pm";
		      	     	}		//end else
				
				if (endmb == 0) endmbs = "00";
				 else endmbs = "30";
				String endtime =   Integer.toString(endhb)+":"+endmbs+" "+ampm;
				
	        	        
	        	        mytext ="Blocktime";
	        	
	        	    	page.setTextStart_time(starttime);
	        		page.setTextEnd_time (endtime);
	        		
	        		page.setTextOwner(mytext);
				flagid = BlockE.getFlag();
				if (flagid == 1)
					Proj_text = "Development";
				else if (flagid == 2)
					Proj_text = "Clinical";
				else Proj_text = "Maintenance/Setup";		
				
          		page.setTextProj_name(Proj_text);
	        	page.setTextDescrip(BlockE.getDescription());
	        	

	        // Add a deep clone of the row to the DOM
                Node clonedNode = templateRow.cloneNode(true);
                EventTable.appendChild(clonedNode);
	        	
		} //end for loop
         }    // end else if no events



         else if  ((nofbe > 0)   && ( EventList.length > 0))
         {                       //begin num of block event if
         	int Blockindex = 0;
         	

         	
         	for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	// begin for loop for events
                	S_event currentS_event = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	int starthi = currentS_event.getStarth();
	        	System.out.println(" ***Start time for event ***" + Integer.toString(starthi));
	        	int startmi = currentS_event.getStartm();
	        	int event_dow = currentS_event.getEventdayofw();
	        	//determine link color blue for prime time, purple for development and green otherwise.
	        	
	        	for (int numBlockE = Blockindex; numBlockE < nofbe; numBlockE ++)   {// begin Block event for loop
	        	     Blocktimec BlockE = BlockList[numBlockE];
	        	     int starthb = BlockE.getStarth();
	        	     System.out.println(" ***Start time for Block event ***" + Integer.toString(starthb));
	        	     int startmb = BlockE.getStartm();
			     int endhb = BlockE.getEndh();
	        	     int endmb = BlockE.getEndm();		        	
	        	     if (starthb < starthi)
	        	     {                //begin if 1
	        	        String ampm = null;
	        	       	if (starthb < 12) ampm = "am";
      				else if (starthb == 12) ampm = "Noon";
		      		else if (starthb == 24) { //begin else if
      					starthb = starthb-12;
		      			ampm = "Mid.";
      					}          //end else if
		      		else {     //begin else
      					starthb = starthb -12;
		      			ampm = "pm";
		      	     	}		       //end else
				if (startmb == 0) startmbs = "00";
				 else startmbs = "30";
	        		String starttime = Integer.toString(starthb)+":"+startmbs+" "+ampm;
	        		 if (endhb < 12) ampm = "am";
      				else if (endhb == 12) ampm = "Noon";
		      		else if (endhb == 24) {       //begin else if
      					endhb = endhb-12;
		      			ampm = "Mid.";
      				}         //end else if
		      		else {                 //begin else
      					endhb = endhb -12;
		      			ampm = "pm";
		      	     	}		//end else
				if (endmb == 0) endmbs = "00";
				 else endmbs = "30";
				String endtime =   Integer.toString(endhb)+":"+endmbs+" "+ampm;
	        	        Blockindex = Blockindex + 1;
	        	
			mytext ="Blocktime";
	        	
	        	    	page.setTextStart_time(starttime);
	        		page.setTextEnd_time (endtime);
	        		
	        		page.setTextOwner(mytext);
				flagid = BlockE.getFlag();
				if (flagid == 1)
					Proj_text = "Development";
				else if (flagid == 2)
					Proj_text = "Clinical";
				else Proj_text = "Maintenance/Setup";		
				
          		page.setTextProj_name(Proj_text);
	        	page.setTextDescrip(BlockE.getDescription());
			
			// Add a deep clone of the row to the DOM
                	Node clonedNode = templateRow.cloneNode(true);
                	EventTable.appendChild(clonedNode);
				
			     } //end if 1
			     else if (starthb == starthi) { //begin elseif
			       if (startmb < startmi)
			     	 {          //begin if
			     	  String ampm = null;
	        	  	  if (starthb < 12) ampm = "am";
      				  else if (starthb == 12) ampm = "Noon";
		      		  else if (starthb == 24) {  //begin else if
      				  starthb = starthb-12;
		      		   ampm = "Mid.";
      				     }     //end else if
		      		   else {               //begin else
      				     starthb = starthb -12;
		      		     ampm = "pm";
		      	     	      }		//end else
				      
	        		     
				    if (startmb == 0) startmbs = "00";
				     else startmbs = "30";
	        		String starttime = Integer.toString(starthb)+":"+startmbs+" "+ampm;
	        		     				     
				     if (endhb < 12) ampm = "am";
      				     else if (endhb == 12) ampm = "Noon";
		      		     else if (endhb == 24) { //begin else if
      				     endhb = endhb-12;
		      		     ampm = "Mid.";
      				      }          		//begin else if
		      		     else { //begin else
      				     endhb = endhb -12;
		      		     ampm = "pm";
		      		     }             //end else
				     
				     
		      		        	        	        	        
				if (endmb == 0) endmbs = "00";
				 else endmbs = "30";
				String endtime =   Integer.toString(endhb)+":"+endmbs+" "+ampm;
				
			     	 	}		      //end if
			     	} //end else if
			     	else {          //begin else
			     	 String ampm = null;
	        	  	  if (starthb < 12) ampm = "am";
      				  else if (starthb == 12) ampm = "Noon";
		      		  else if (starthb == 24) {        //begin else if
      				  starthb = starthb-12;
		      		   ampm = "Mid.";
      				     }   //end else if
		      		   else {             //begin else
      				     starthb = starthb -12;
		      		     ampm = "pm";
		      	     	      }		      //end else

			        if (startmb == 0) startmbs = "00";
				 else startmbs = "30";
	        		String starttime = Integer.toString(starthb)+":"+startmbs+" "+ampm;
				
	        		
	        		     if (endhb < 12) ampm = "am";
      				     else if (endhb == 12) ampm = "Noon";
		      		     else if (endhb == 24) {    //begin else if
      				     endhb = endhb-12;
		      		     ampm = "Mid.";
      				      }  			//end else if
		      		     else {                     // begin else
      				     endhb = endhb -12;
		      		     ampm = "pm";
		      	     	}		                //end else
	        	        
				
				if (endmb == 0) endmbs = "00";
				 else endmbs = "30";
				String endtime =   Integer.toString(endhb)+":"+endmbs+" "+ampm;
				
	        	        
				mytext ="Blocktime";
	        	
	        	    	page.setTextStart_time(starttime);
	        		page.setTextEnd_time (endtime);
	        		
	        		page.setTextOwner(mytext);
				flagid = BlockE.getFlag();
				if (flagid == 1)
					Proj_text = "Development";
				else if (flagid == 2)
					Proj_text = "Clinical";
				else Proj_text = "Maintenance/Setup";		
				
          		page.setTextProj_name(Proj_text);
	        	page.setTextDescrip(BlockE.getDescription());
			Blockindex = Blockindex + 1;
			
			// Add a deep clone of the row to the DOM
                Node clonedNode = templateRow.cloneNode(true);
                EventTable.appendChild(clonedNode);
	
				
			   }		//end else  		
			     		        	      	
	        }//end Block event for loop
	        	
	        	
	        	
	        	String ampm = null;
	        	
	        	if (starthi < 12) ampm = "am";
      			else if (starthi == 12) ampm = "Noon";
		      	else if (starthi == 24) {
      				starthi = starthi-12;
		      		ampm = "Mid.";
      				}
		      	else {
      				starthi = starthi -12;
		      		ampm = "pm";
		      	     }	
			     
			 if (startmi == 0) startms = "00";
				 else startms = "30";
	        		String starttime = Integer.toString(starthi)+":"+startms+" "+ampm;
				     
			     	
	        		   	
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	        	
	        	if (endhi < 12) ampm = "am";
      			else if (endhi == 12) ampm = "Noon";
		      	else if (endhi == 24) {
      				endhi = endhi-12;
		      		ampm = "Mid.";
      				}
		      	else {
      				endhi = endhi -12;
		      		ampm = "pm";
		      	     }	
			     
			     if (endmi == 0) endms = "00";
				 else endms = "30";
				String endtime =   Integer.toString(endhi)+":"+endms+" "+ampm;	
	        	
	        	
	        	String ownername = currentS_event.getOwnerLastname();	        	
	        	String projowner =  currentS_event.getProj_owner_name() ;
	        	if  (currentS_event.isDevelop())
	        	  {
	        	        if (this.getUser().isDeveloper())
	        		mytext = ownername;
	        		else
	        		mytext = "Development time";
	        	  }	
	        	else
	        		mytext = "Development time";
	        	
			page.setTextOwner(mytext);	
			
			
			project_name = currentS_event.getProj_owner_name();
			
			if  (currentS_event.isDevelop())
	        	  {
	        	        if (this.getUser().isDeveloper())
	        		mytext = project_name;
	        		else
	        		mytext = "Development time";
	        	  }	
	        	else
	        		mytext = project_name;
			
			
          		page.setTextProj_name(mytext);
	        	page.setTextDescrip(currentS_event.getDescription());
	        	
			
			
	        		}   // end for loop for events
 	
         	// Add a deep clone of the row to the DOM
                Node clonedNode = templateRow.cloneNode(true);
                EventTable.appendChild(clonedNode);

                      	

         }	//end num of block events if stat

         else
         {	// no block time available for this date
                 // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	        for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	
                S_event currentS_event = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	int starthi = currentS_event.getStarth();
	        	int startmi = currentS_event.getStartm();
	        	String ampm = null;
	        	
	        	if (starthi < 12) ampm = "am";
      			else if (starthi == 12) ampm = "Noon";
		      	else if (starthi == 24) {
      				starthi = starthi-12;
		      		ampm = "Mid.";
      				}
		      	else {
      				starthi = starthi -12;
		      		ampm = "pm";
		      	     }	
			     
			     
				if (startmi == 0) startms = "00";
				 else startms = "30";
	        		String starttime = Integer.toString(starthi)+":"+startms+" "+ampm;
			     	
	        	
	        		   	
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	        	
	        	if (endhi < 12) ampm = "am";
      			else if (endhi == 12) ampm = "Noon";
		      	else if (endhi == 24) {
      				endhi = endhi-12;
		      		ampm = "Mid.";
      				}
		      	else {
      				endhi = endhi -12;
		      		ampm = "pm";
		      	     }		
	        	
				if (endmi == 0) endms = "00";
				 else endms = "30";
	        		String endtime = Integer.toString(endhi)+":"+endms+" "+ampm;
	        	
	        	
	        	page.setTextStart_time(starttime);
	        	page.setTextEnd_time (endtime);
	        	      	
	        	String ownername = currentS_event.getOwnerFirstname()+" "+currentS_event.getOwnerLastname();
	        	
	        	
	        	if  (currentS_event.isDevelop())
	        	  {
	        	        if (this.getUser().isDeveloper())
	        		mytext = ownername;
	        		else
	        		mytext = "Development time";
	        	  }	
	        	else
	        		mytext = ownername;
				
	        	page.setTextOwner(mytext);	
			
			
			project_name = currentS_event.getProj_owner_name();
			
			if  (currentS_event.isDevelop())
	        	  {
	        	        if (this.getUser().isDeveloper())
	        		mytext = project_name;
	        		else
	        		mytext = "Development time";
	        	  }	
	        	else
	        		mytext = project_name;
			
			
          		page.setTextProj_name(mytext);
	        	page.setTextDescrip(currentS_event.getDescription());
	        	

	        // Add a deep clone of the row to the DOM
                Node clonedNode = templateRow.cloneNode(true);
                EventTable.appendChild(clonedNode);

                 }

            }
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);
            throw new webschedulePresentationException("Error getting Events for date: ", ex);
	    }

	 templateRow.getParentNode().removeChild(templateRow);
	


        //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg || 
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {       
            page.setTextErrorText(errorMsg);
	   
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }


        if(null != this.getComms().request.getParameter(DESCRIPTION)) {
            page.getElementDescription().setValue(this.getComms().request.getParameter(DESCRIPTION));
        }

    /*    if(null != this.getComms().request.getParameter(REPEATMENU)) {
            page.getElementRepeatmenu().setValue(this.getComms().request.getParameter(REPEATMENU));
        }*/

      /*  if(null != this.getComms().request.getParameter(TIMES)) {
            page.getElementTimes().setValue(this.getComms().request.getParameter(TIMES));
        }*/

	    return page.toDocument();
    }


     public String handleEditEvent()
        throws HttpPresentationException
    {

       String day = this.getComms().request.getParameter(DATE);
       System.out.println("date selected = "+day);
        String month = this.getComms().request.getParameter(MONTH);
	System.out.println("month selected = "+month);
        String year = this.getComms().request.getParameter(YEAR);
	System.out.println("year selected = "+year);
	
            if ((null == day)||(null == month)||(null == year)) {
            	this.getSessionData().setUserMessage("Please choose a valid date ");
                 throw new ClientPageRedirectException(SELECT_DATE_PAGE);
                 // Show error message that project not found
            } else {
            	this.setYear(Integer.parseInt(year));
            	this.setMonth(Integer.parseInt(month));
            	this.setDay(Integer.parseInt(day));
                throw new ClientPageRedirectException(EDIT_EVENT_PAGE);
           }
      }

  protected void update_projhours(int numoftimes)
   throws HttpPresentationException
   {
   	double starttime;
   	double endtime;
   	double donehours;
   	double totalhours;
   	double eventhours;
       	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	
    	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);
    	
    	if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    	else starttime = (double) starthi;
    	
    	if (endmi == 30)
    		endtime =     endhi + 0.5;
    	else endtime = (double) endhi;	
    	
    	if (numoftimes == 0)
    	eventhours = endtime - starttime;
    	else eventhours =  (endtime - starttime) * numoftimes;
    	
	System.out.println("Event hours = "+eventhours);
	
    	Project theProject = this.getProject();
    	
    	try
    	{    	
    	donehours = theProject.getDonehours() + eventhours;
	System.out.println("done hours = "+donehours);
	
    	totalhours = theProject.getTotalhours();
	System.out.println("total hours = "+totalhours);
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }

    try {
            theProject.setProj_name(theProject.getProj_name());
            theProject.setPassword(theProject.getPassword());
	    theProject.setDescription(theProject.getDescription());
	    theProject.setIndexnum(theProject.getIndexnum());
	    theProject.setTotalhours(totalhours);
	    theProject.setDonehours(donehours);
            theProject.setCodeofpay(theProject.getCodeofpay());
            theProject.setOwner(this.getUser());
	    theProject.setContactname(theProject.getContactname());
	    theProject.setContactphone(theProject.getContactphone());
	    theProject.setBilladdr1(theProject.getBilladdr1());
            theProject.setBilladdr2(theProject.getBilladdr2());
	    theProject.setBilladdr3(theProject.getBilladdr3());
	    theProject.setCity(theProject.getCity());
	    theProject.setState(theProject.getState());
	    theProject.setZip(theProject.getZip());
	    theProject.setAccountid(theProject.getAccountid());
	    theProject.setOutside(theProject.isOutside());
	    theProject.setExpday(theProject.getExpday());
	    theProject.setExpmonth(theProject.getExpmonth());
	    theProject.setExpyear(theProject.getExpyear());
	   
	        theProject.save();	
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding project", ex);
        }

    }


//  check the operator status module
    
   public int check_opstatus(int lsd, int lsm,int lsy, int recd,int recm,int recy,Timestamp ts, boolean isExp)
   	throws HttpPresentationException
      {
      int conflict = 0;
      int conflict_has_set = 0;
      Timestamp	cts = new Timestamp(System.currentTimeMillis());
      long lastscandiff=0;
      long recertdiff = 0;
      
      long ctsl = cts.getTime();
      long tsl = ts.getTime();
      
      long tsnh = (ctsl - tsl)/3600000;
      
      
      
        Calendar todayinfo = Calendar.getInstance();
        Date todayinfodate =   todayinfo.getTime();
    	long todaylong = todayinfodate.getTime();
	//System.out.println("today long var = "+Long.toString(todaylong) );
	
	
	Calendar lastscaninfo = Calendar.getInstance();
    	lastscaninfo.set(lsy,lsm,lsd);    
	Date lastscaninfodate =   lastscaninfo.getTime();
    	long lastscanlong = lastscaninfodate.getTime();
	//System.out.println("last scan long var = "+Long.toString(lastscanlong) );
	
	
	Calendar recertinfo = Calendar.getInstance();
    	recertinfo.set(recy,recm,recd);    
	Date recertinfodate =   recertinfo.getTime();
    	long recertlong = recertinfodate.getTime();
      
      if (isExp)
        {
	conflict_has_set = 1;
	System.out.println("Account experired ");
	//break;
	}
	
	//remarked by Eman temp
	//positive
	if (lastscanlong > 0)
		lastscandiff = (todaylong - lastscanlong) / 3600000;
	else
	      	lastscandiff = 0;
      if (recertlong > 0)
	   recertdiff = (todaylong - recertlong) / 3600000;
      else 	
           recertdiff = 0;
	

//rmarked by Eman April 2007
	// check have not scanned for over 4 month
/*	if (lastscandiff > 2880)
	{
	  // no recert on record for the last 4 month
	    if (recertdiff > 2880)
	        {
		conflict_has_set = 1;
		System.out.println("lapsed diff = "+Long.toString(lastscandiff) +"second diff "+Long.toString(recertdiff));
		//break;
		}
	}	
	*/
	
	//test initial date has been set to Nov 3, 2005, which will lock them out if has not taken the test by Nov, 2006
	if (tsnh > 8760)
	  {
		conflict_has_set = 1;
		System.out.println("No safety on test ");
	//break ;
	}

		
		if (conflict_has_set == 1)
		   return 1;
		 else return 0;  
		
      
      }

 public void updateoperatorlastscan()
   throws HttpPresentationException
   {
      int eventyear = this.getYear();
        int eventmonth = this.getMonth();
        int eventday = this.getDay();
 int lastscanday,lastscanmonth,lastscanyear;
        //get last scan on record
	Operator theOperator = this.getOperator();
    	
    	try
    	{    	
    	lastscanday = theOperator.getlastscanday();
	lastscanmonth = theOperator.getlastscanmonth();
	lastscanyear = theOperator.getlastscanyear();
	
	//System.out.println("total hours = "+totalhours);
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting operator information", ex);
        }
	
	
        //calculation for the time right now
    	Calendar lastscaninfo = Calendar.getInstance();
	lastscaninfo.set(lastscanyear, lastscanmonth, lastscanday);
    	Date lastscandate =  lastscaninfo.getTime();
    	long lastscaninfoms = lastscandate.getTime();
    	   	
    	//calculation for the event info
    	Calendar eventinfo = Calendar.getInstance();
    	eventinfo.set(eventyear, eventmonth, eventday);
    	Date eventinfodate =   eventinfo.getTime();
    	long eventinfoms = eventinfodate.getTime();
	
	long difference =  lastscaninfoms - eventinfoms;
	//long numofhours = difference/3600000;
    	
	System.out.println("lastscan = "+lastscandate);
	
	if (eventinfoms > lastscaninfoms)
	{
	     try 
	     {
	     System.out.println("lastscan = "+lastscandate);
	     theOperator.setFirstname(theOperator.getFirstName());
	     theOperator.setLastname(theOperator.getLastName());
	     theOperator.setEmail(theOperator.getEmail());
	     theOperator.setCertday(theOperator.getCertday());
	     theOperator.setCertmonth(theOperator.getCertmonth());
	     theOperator.setCertyear(theOperator.getCertyear());
	     theOperator.setlastscanday(eventday);
	     theOperator.setlastscanmonth(eventmonth);
	     theOperator.setlastscanyear(eventyear);
	     theOperator.setrecertday(theOperator.getrecertday());
	     theOperator.setrecertmonth(theOperator.getrecertmonth());
	     theOperator.setrecertyear(theOperator.getrecertyear());
	     theOperator.setSFTTS(theOperator.getSFTTS());
	     theOperator.setExp(theOperator.isExp());
	     theOperator.save();
	     } catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting operator information", ex);
        }
	}
}




  protected void  send_notification(int numoftimes)
   throws HttpPresentationException
   {
   	double starttime;
   	double endtime;
   	double donehours;
   	double totalhours;
   	double eventhours;
   	String user_email = null;
String operator_email = null;
   	String [] message = null;
	
	String startms = null;
	String endms = null;
	
       	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	
    	int year = this.getYear();
        int month = this.getMonth();
        int day = this.getDay();

       String eventdatestring = Integer.toString(month+1)+"/"+Integer.toString(day)+"/"+Integer.toString(year);
    	
    	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);
    	
    	if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
		startms = "30";
    		}
    	else {
	starttime = (double) starthi;
    	startms = "00";
	}
	
    	if (endmi == 30)
	{
		endms = "30";
    		endtime =     endhi + 0.5;
		}
    	else {
	endms = "00";
	endtime = (double) endhi;	
	}
    	eventhours = endtime - starttime;
    	
    	Project theProject = this.getProject();
    	
    	try
    	{    	
    	donehours = theProject.getDonehours() + eventhours;
    	totalhours = theProject.getTotalhours();
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }

        Person theUser = this.getUser();
    	
    	try
    	{    	
    	user_email = theUser.getEmail();
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting user's email", ex);
        }

try
    	{    	
    	operator_email = this.getOperator().getEmail();
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting user's email", ex);
        }

    try {
    	    SendMail sch_not;	
    	    String from = "appsadmin";
    	    String to = user_email+","+operator_email;
    	    String subject = "A slot has been scheduled for the 3TW magnet";
    	    if (numoftimes == 0)
    	    {
    	    String[] message1 = {"This message is to confirm that you scheduled "+eventhours+" hours for "+ theProject.getProj_name(),
    	    			"Date:             "+eventdatestring,
    	    			"Starting time is: "+starth+":"+startms,
    	    			"End time is: "+endh+":"+endms,
    	    			" "}  ;
    	   message = message1;
    	   } 			
    	   else
    	   {
    	    String[] messagem = {"This message is to confirm that you scheduled "+eventhours+" hours for "+ theProject.getProj_name(),
    	    			"Date:             "+eventdatestring,
    	    			"Starting time is: "+starth+":"+startms,
    	    			"End time is: "+endh+":"+endms,
    	    			"This event is a repeat event for "+numoftimes+" times",
    	    			" "}  ; 			
    	    message = messagem;
    	    }			
           sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }

    }



    
     protected void  notify_pi()
   throws HttpPresentationException
   {
   	double starttime;
   	double endtime;
   	double donehours;
   	double totalhours;
   	double eventhours;
   	String user_email = null;
	String operator_email = null;
   	String [] message = null;
	
	String startms = null;
	String endms = null;
	
       	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);

    	
    	int year = this.getYear();
        int month = this.getMonth();
        int day = this.getDay();

       String eventdatestring = Integer.toString(month+1)+"/"+Integer.toString(day)+"/"+Integer.toString(year);
    	
    	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);
    	
    	if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
		startms = "30";
    		}
    	else {
	starttime = (double) starthi;
    	startms = "00";
	}
	
    	if (endmi == 30)
	{
		endms = "30";
    		endtime =     endhi + 0.5;
		}
    	else {
	endms = "00";
	endtime = (double) endhi;	
	}
    	eventhours = endtime - starttime;
    	
    	Project theProject = this.getProject();
    	
    	try
    	{    	
    	donehours = theProject.getDonehours() + eventhours;
    	totalhours = theProject.getTotalhours();
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }

        Person theUser = this.getUser();
    	
    	try
    	{    	
    	user_email = theUser.getEmail();
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting user's email", ex);
        }
 	
     

    try {
    	    SendMail sch_not;	
    	    String from = "emang";
    	    String to = user_email+",emang";
	   // String cc = operator_email;
    	    String subject = "An unlisted operator for one of your studies at the CFMRI";
    	    
    	     String[] message1 = {"This message is to inform you that your project  "+ theProject.getProj_name(),
    	    		"does not have the scanner operators listed, currently you cannot schedule",
			"please email eghobrial@ucsd.edu and cc: eghobrial@ucsd.edu the  list of certified operators for that project " ,
    	    		" ",
    	    		"Thank you ",
    	    		" "}  ;
    	  
	    message = message1;	
           sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }

    }









    /**
     * Method to save a new or existing event to the database
     */
 protected void saveS_event(S_event theS_event)
          throws HttpPresentationException, webschedulePresentationException
    {
        String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	String description = this.getComms().request.getParameter(DESCRIPTION);
    	System.out.println("decription = "+description);
    	Calendar eventinfo = Calendar.getInstance();
    	int todayday = eventinfo.get(eventinfo.DAY_OF_MONTH);
    	int todaymonth = eventinfo.get(eventinfo.MONTH);
    	int todayyear = eventinfo.get(eventinfo.YEAR);
	int todayh = eventinfo.get(eventinfo.HOUR);
	int todaym = eventinfo.get(eventinfo.MINUTE);
    	eventinfo.set(this.getYear(),this.getMonth(),this.getDay());
    	int dow = eventinfo.get(eventinfo.DAY_OF_WEEK);
    	System.out.println("day of the week = "+Integer.toString(dow));
    	
    	try {
    		System.out.println("about to save one ");
    		theS_event.setDescription(description);
    		theS_event.setStarth(Integer.parseInt(starth));
    		theS_event.setStartm(Integer.parseInt(startm));
    		theS_event.setEndh(Integer.parseInt(endh));
    		theS_event.setEndm(Integer.parseInt(endm));
    		theS_event.setEventday(this.getDay());
    		theS_event.setEventmonth(this.getMonth());
    		theS_event.setEventyear(this.getYear());
    		theS_event.setEventdayofw(dow);
    		theS_event.setTodayday(todayday);
    		theS_event.setTodaymonth(todaymonth);
    		theS_event.setTodayyear(todayyear);
		theS_event.setTodayh(todayh);
		theS_event.setTodaym(todaym);
    		theS_event.setOwner(this.getUser());
    		theS_event.setProj_owner(this.getProject());
		theS_event.setPrevowner(this.getUser());
    		theS_event.setPrevproj_owner(this.getProject());
		theS_event.setOperator(this.getOperator());
    		theS_event.setDevelop(this.getUser().isDeveloper());
    		theS_event.setRepeat(false);
		theS_event.setGrab(false);
		theS_event.setReasondropped(" ");
    		System.out.println("about to save two ");
    		theS_event.save();
    		System.out.println("AN EVENT HAS BEEN SAVED ");
    		
    	 } catch(Exception ex) {
    throw new webschedulePresentationException("Error adding an event, make sure all fields are filled", ex);
     }
      }  	

       /**
     * Method to save a multiple event to the database
     */
 protected void saveMultiS_event()
          throws HttpPresentationException, webschedulePresentationException
    {

    LinkedList mygood_list = new LinkedList();

    temp_hold good_event = null;
    mygood_list = this.getGoodlist();



    Calendar eventinfo = Calendar.getInstance();
    int todayday = eventinfo.get(eventinfo.DAY_OF_MONTH);
    int todaymonth = eventinfo.get(eventinfo.MONTH);
    int todayyear = eventinfo.get(eventinfo.YEAR);
     int todayh = eventinfo.get(eventinfo.HOUR);
	int todaym = eventinfo.get(eventinfo.MINUTE);

    for (int c = 0; c <  mygood_list.size(); c++)
    	     {
    	  Object list_evento = mygood_list.get(c);
    	  good_event = (temp_hold) list_evento;
    	     System.out.println("Good Event Description "+good_event.getcdescription());
    	
    	  try {
    	  	 S_event theS_event = new S_event();
    		System.out.println("about to save one Multi Events");
    		theS_event.setDescription(good_event.getcdescription());
    		String tempdes = theS_event.getDescription();
    		System.out.println("Temp Description = "+tempdes);
    		theS_event.setStarth(good_event.getcstarth());
    		theS_event.setStartm(good_event.getcstartm());
    		theS_event.setEndh(good_event.getcendh());
    		theS_event.setEndm(good_event.getcendm());
    		theS_event.setEventday(good_event.getceventday());
    		theS_event.setEventmonth(good_event.getceventmonth());
    		theS_event.setEventyear(good_event.getceventyear());
    		theS_event.setEventdayofw(good_event.getceventdayofw());
    		theS_event.setTodayday(todayday);
    		theS_event.setTodaymonth(todaymonth);
    		theS_event.setTodayyear(todayyear);
		theS_event.setTodayh(todayh);
		theS_event.setTodaym(todaym);
    		theS_event.setOwner(this.getUser());
    		theS_event.setProj_owner(this.getProject());
		theS_event.setOperator(this.getOperator());
    		theS_event.setDevelop(this.getUser().isDeveloper());
    		theS_event.setRepeat(true);
		theS_event.setPrevowner(this.getUser());
    		theS_event.setPrevproj_owner(this.getProject());
		theS_event.setGrab(false);
		theS_event.setReasondropped(" ");
    		System.out.println("about to save two ");
    		theS_event.save();
    		System.out.println("AN EVENT HAS BEEN SAVED ");
    		
    	 	} catch(Exception ex) {
          	throw new webschedulePresentationException("Error adding an event", ex);
    		 }
      	     }  	
    	
      }


}


