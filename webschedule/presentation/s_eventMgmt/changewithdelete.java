/**--------------------------------------------------------------
* Webschedule
*
* @class: change
* @version
* @author: Eman Ghobrial
* @since: Sept 2002
*
*--------------------------------------------------------------*/
package webschedule.presentation.s_eventMgmt;

import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.business.s_event.*;
import webschedule.business.c_event.*;
import webschedule.business.blocktime.*;
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
import java.lang.String;
import webschedule.SendMail;

/**
 * change.java handles changing the event start time
 *
 */
public class change extends BasePO
{
    /**
     * Constants
     */
    private static String SUBMIT_NAME = "submit";
    private static String PASSWORD_NAME = "password";
    private static String ERROR_NAME = "ERROR_NAME";
    private static String PROJ_ID = "projID";
    private static String STARTH ="starth";
    private static String STARTM ="startm";
    private static String ENDH ="endh";
    private static String ENDM ="endm";		
	
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



    /**
     *
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
    	String temptext = null;
    	int temphour;
    	String starth = this.getComms().request.getParameter(STARTH);
    	String startm = this.getComms().request.getParameter(STARTM);
	String endh = this.getComms().request.getParameter(ENDH);
    	String endm = this.getComms().request.getParameter(ENDM);
    	
    	
    	
        changeHTML page = new changeHTML();

        int starthi = 0;
        int startmi = 0;
        int endhi = 0, endmi = 0;
        String projID = null;
        
	temp_hold_change changeevent = this.getchangeevent();
        starthi = changeevent.getcstarth();
        startmi = changeevent.getcstartm();
        endhi = changeevent.getcendh();
        endmi = changeevent.getcendm();
        projID = changeevent.getProjID();
	year = changeevent.getceventyear();
	month = changeevent.getceventmonth();
	day = changeevent.getceventday();
         
       
        //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }

	    return page.toDocument();
    }

  /**
     *  Process Change start time or end time for the event
     */
     public String handleChange()
        throws HttpPresentationException
	    {
	      //***ChangeEvent***************************************
	     String proj_id = null;
	     int starthi=0, startmi=0, endhi=0, endmi=0;
	     double starttime = 0.0;
             double endtime = 0.0;
   	     double eventhours = 0.0;
	     double new_starttime = 0.0;
             double new_endtime = 0.0;
   	     double event_hours = 0.0;
	     double new_eventhours = 0.0;
	     int eventday=0, eventmonth=0, eventyear=0;
	     String description = null;
	     String starth = this.getComms().request.getParameter(STARTH);
             String startm = this.getComms().request.getParameter(STARTM);
             String endh = this.getComms().request.getParameter(ENDH);
    	     String endm = this.getComms().request.getParameter(ENDM);
	     int new_starthi =  Integer.parseInt(starth);
	     int new_startmi = Integer.parseInt(startm);	
	     int new_endhi =  Integer.parseInt(endh);
	     int new_endmi = Integer.parseInt(endm);	
	  
	temp_hold_change changeevent = this.getchangeevent();
        starthi = changeevent.getcstarth();
        startmi = changeevent.getcstartm();
        endhi = changeevent.getcendh();
        endmi = changeevent.getcendm();
        projID = changeevent.getProjID();
	eventyear = changeevent.getceventyear();
	eventmonth = changeevent.getceventmonth();
	eventday = changeevent.getceventday();
            
	
	//calculate old event hours
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
	
	

	//calculate new event hours
	if (new_startmi == 30)
    		{
    		double startt = (double) starthi;
    		new_starttime =    startt + 0.5;
    		}
    	else new_starttime = (double) starthi;
    	
    	if (new_endmi == 30)
    		new_endtime =     endhi + 0.5;
    	else new_endtime = (double) endhi;	
    	new_eventhours = new_endtime - new_starttime;

if (check_conflict()==1) return showPage("You entered the date that conflict check calendar and try again");
else if (check_conflict_blocktime()==1) return showPage ("You entered the date that conflict check calendar and try again");
else if  (check_time()==1) return showPage("You entered a wrong time end time must be after start time");
else if (check_proj_hours(1)==1) return showPage("You have acquired all project hours,check with the committee");
else if  (check_equal()==1) return showPage("You entered a start time that is equal to end time");

else {       //else pass conflicts
     if (new_eventhours >= event_hours) 
	// no cancelation and may be addition
	{
		change_projhours(proj_id,new_eventhours,eventhours);
		//updateS_event(s_eventID); 
		saveS_event();
		//this.removeS_eventIDFromSession();
		throw new ClientPageRedirectException(SELECT_DATE_PAGE);
	}
     else
	//cancelation save canceled portion
	{
		change_projhours(proj_id,new_eventhours,eventhours);
		updateS_event(s_eventID);
	  /* try {
  	    	C_event theC_event = new C_event();
      		saveC_event(s_eventID,theC_event);
		this.removeS_eventIDFromSession();
     		 }catch  (Exception ex) {
	    throw new webschedulePresentationException("Error Creating a blank Canceled event", ex);
		 }*/
		 
		 saveS_event();
    		throw new ClientPageRedirectException(SELECT_DATE_PAGE);
       }
    } //else pass conflicts
 } //end routine





    public int check_conflict()
     throws HttpPresentationException, webschedulePresentationException
    {
    	
	int eventday=0, eventmonth=0, eventyear=0;
	String proj_id = null;
	  int starthi=0, startmi=0, endhi=0, endmi=0;
	  
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
    	
    	
	
	  
	temp_hold_change changeevent = this.getchangeevent();
        starthi = changeevent.getcstarth();
        startmi = changeevent.getcstartm();
        endhi = changeevent.getcendh();
        endmi = changeevent.getcendm();
        projID = changeevent.getProjID();
	eventyear = changeevent.getceventyear();
	eventmonth = changeevent.getceventmonth();
	eventday = changeevent.getceventday();
            
	    	
      	
        int conflict = 0;
        int conflict_has_been_set = 0;

          try {

          	S_event[] EventList = S_eventFactory.findS_eventsForDate(eventyear,eventmonth,eventday);
          	
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
     
     
     int eventday=0, eventmonth=0, eventyear=0;
	String proj_id = null;
	  int starthi=0, startmi=0, endhi=0, endmi=0;
	  
	temp_hold_change changeevent = this.getchangeevent();
        starthi = changeevent.getcstarth();
        startmi = changeevent.getcstartm();
        endhi = changeevent.getcendh();
        endmi = changeevent.getcendm();
        projID = changeevent.getProjID();
	eventyear = changeevent.getceventyear();
	eventmonth = changeevent.getceventmonth();
	eventday = changeevent.getceventday();	

     
     int conflict = 0;
     int conflict_has_been_set = 0;


       try {

          	Blocktimec[] EventList =
		BlocktimeFactory.findBlocktimeForDate(eventyear,eventmonth,eventday);
          	
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
	        		  	  //System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  //System.out.println("System conflict flag  "+Integer.toString(conflict));
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
	        		   //System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		    conflict_has_been_set = 1;
	        		   //return conflict;
	        		   break;
	        		   }        //if
	        		   else {//else 1
	        		
	        			if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  //System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  //System.out.println("System conflict flag  "+Integer.toString(conflict));
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
                       		   //System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		   conflict_has_been_set = 1;
                       		   break;
                       		 }
                       		else if (event_starthi == endhi)
                       		    {
                       		      if (event_startmi < endmi)
                       		      {
                       		
                       		      	conflict = 1;
                       		      	 //System.out.println("System conflict flag  "+Integer.toString(conflict));
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
	
	//System.out.println("System conflict flag  "+Integer.toString(conflict));
	if ( conflict_has_been_set == 1)
	        return 1;
	else return 0;
 	 	
  } //end procedure	

                		
 public int check_time()
    throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        //System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	//System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    //	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    //	System.out.println("endm selected = "+endm);
    	

	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);		
	    			    			
	
/*	Calendar eventinfo = Calendar.getInstance();
    	int todayday = eventinfo.get(eventinfo.DAY_OF_MONTH);
    	int todaymonth = eventinfo.get(eventinfo.MONTH);
    	int todayyear = eventinfo.get(eventinfo.YEAR);
 	eventinfo.set(this.getYear(),this.getMonth(),this.getDay(),starthi-1,endhi-1);    			*/
 	
 	if ((endhi < starthi) || ((endhi == starthi) && (endmi < startmi)))
 	{
 	  return 1;
 	}     else return  0;
} 	


    public int check_equal()
    throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        //System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	//System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    //	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    //	System.out.println("endm selected = "+endm);
    	

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
       // System.out.println("Starth selected = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	//System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	//System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    //	System.out.println("endm selected = "+endm);
    	
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
	
	
	int eventday=0, eventmonth=0, eventyear=0;
	String proj_id = null;
	  int starthi=0, startmi=0, endhi=0, endmi=0;
	  
	temp_hold_change changeevent = this.getchangeevent();
        starthi = changeevent.getcstarth();
        startmi = changeevent.getcstartm();
        endhi = changeevent.getcendh();
        endmi = changeevent.getcendm();
        projID = changeevent.getProjID();
	eventyear = changeevent.getceventyear();
	eventmonth = changeevent.getceventmonth();
	eventday = changeevent.getceventday();	
    	
    	Project theProject = null;

	try {
            project = ProjectFactory.findProjectByID(projID);
	 } catch (Exception ex) {
throw new webschedulePresentationException("Error getting project information", ex);
 }
	
	    	
    	try
    	{
    	donehours = theProject.getDonehours();
	//System.out.println("done hours off adding an event = "+Double.toString(donehours));
	
    	totalhours = theProject.getTotalhours();
	//System.out.println("total hours off adding an event"+Double.toString(totalhours));
	
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


      /*Update project hours in the partial delete case*/

   protected void change_projhours(String projID,double new_eventhours,double eventhours)
   throws HttpPresentationException
   {
   	
   	double donehours;
   	double totalhours;
   	double differencehours;
   	
   	Project theProject=null;
       	
    	differencehours = new_eventhours - eventhours;
    	
	try
	{    	
    	theProject = ProjectFactory.findProjectByID(projID);
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
    	
    	/*Project theProject = this.getProject();*/
    	
    	try
    	{    	
    	donehours = theProject.getDonehours() - differencehours;
    	totalhours = theProject.getTotalhours() ;
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




    /**
     * Method to update event to the database with new project
     */
/* protected void updateS_event(String s_eventID)
          throws HttpPresentationException, webschedulePresentationException
    {

        int starthi = 0, startmi = 0, endhi = 0, endmi = 0;
    	String description = null;
    	int todayday = 0;
    	int todaymonth = 0;
    	int todayyear = 0;
    	int eventday = 0, eventmonth = 0, eventyear = 0;
    	int dow = 0;
    	String proj_id = null;
    	int cancelcode = 0;
    	S_event s_event = null;	
    	String starth = this.getComms().request.getParameter(STARTH);
        String startm = this.getComms().request.getParameter(STARTM);
	String endh = this.getComms().request.getParameter(ENDH);
        String endm = this.getComms().request.getParameter(ENDM);
        int new_starthi =  Integer.parseInt(starth);
	int new_startmi = Integer.parseInt(startm);
	int new_endhi =  Integer.parseInt(endh);
	int new_endmi = Integer.parseInt(endm);
	String user_login = null;
	boolean isRepeat = false;
	C_event theC_event = null;
    	
        try {
	    	s_event = S_eventFactory.findS_eventByID(s_eventID);
                starthi = s_event.getStarth();
            	startmi = s_event.getStartm();
            	endhi = s_event.getEndh();
            	endmi = s_event.getEndm();
            	description = s_event.getDescription();
            	eventday =  s_event.getEventday();
            	eventmonth =  s_event.getEventmonth();
            	eventyear =  s_event.getEventyear();
            	dow =   s_event.getEventdayofw();
            	todayday = s_event.getTodayday() ;
            	todaymonth = s_event.getTodaymonth() ;
            	todayyear = s_event.getTodayyear() ;
            	proj_id = s_event.getProjectID();
            	user_login = s_event.getOwnerLogin();
            	 isRepeat = s_event.isRepeat();
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
            	}		
    		
   		
    	
    	try {
    		//System.out.println("about to save one ");
    		s_event.setDescription(description);
    		s_event.setStarth(new_starthi);
    		s_event.setStartm(new_startmi);
    		s_event.setEndh(new_endhi);
    		s_event.setEndm(new_endmi);
    		s_event.setEventday(eventday);
    		s_event.setEventmonth(eventmonth);
    		s_event.setEventyear(eventyear);
    		s_event.setEventdayofw(dow);
    		s_event.setTodayday(todayday);
    		s_event.setTodaymonth(todaymonth);
    		s_event.setTodayyear(todayyear);
    		s_event.setOwner(this.getUser());
    		s_event.setProj_owner(ProjectFactory.findProjectByID(proj_id));
    		s_event.setDevelop(this.getUser().isDeveloper());
    		s_event.setRepeat(isRepeat);
    		//System.out.println("about to save two ");
    		s_event.save();
    		//System.out.println("****AN EVENT HAS BEEN SAVED with new project ****** ");
    		
    	 } catch(Exception ex) {
          throw new webschedulePresentationException("Error adding an event", ex);
     }


  }


 protected void  saveC_event(String s_eventID,C_event theC_event) 	
   throws HttpPresentationException, webschedulePresentationException
   {

   int starthi = 0, startmi = 0, endhi = 0, endmi = 0;
    	String description = null;
    	int todayday = 0;
    	int todaymonth = 0;
    	int todayyear = 0;
    	int eventday = 0, eventmonth = 0, eventyear = 0;
    	int dow = 0;
    	String proj_id = null;
    	int cancelcode = 0;
    	S_event s_event = null;	
    	String starth = this.getComms().request.getParameter(STARTH);
        String startm = this.getComms().request.getParameter(STARTM);
        int new_starthi =  Integer.parseInt(starth);
	int new_startmi = Integer.parseInt(startm);
	String user_login = null;
	
	
    	
        try {
	    	s_event = S_eventFactory.findS_eventByID(s_eventID);
                starthi = s_event.getStarth();
            	startmi = s_event.getStartm();
            	endhi = s_event.getEndh();
            	endmi = s_event.getEndm();
            	description = s_event.getDescription();
            	eventday =  s_event.getEventday();
            	eventmonth =  s_event.getEventmonth();
            	eventyear =  s_event.getEventyear();
            	dow =   s_event.getEventdayofw();
            	todayday = s_event.getTodayday() ;
            	todaymonth = s_event.getTodaymonth() ;
            	todayyear = s_event.getTodayyear() ;
            	proj_id = s_event.getProjectID();
            	user_login = s_event.getOwnerLogin();
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
            	}		

            	
         //calculation for the time the event has been canceled
    	Calendar cancelinfo = Calendar.getInstance();
    	int Ctodaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int Ctodaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int Ctodayyear = cancelinfo.get(cancelinfo.YEAR);
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
		{
		this.getSessionData().setUserMessage(s_eventID + "You can not delete a past event");
		cancelcode = -1;	
		}
	else if (numofhours < 24)
		{
		this.getSessionData().setUserMessage(s_eventID + "Deleting part of event less than 24 hours check policy for charges");	
		cancelcode = 2;
		}
	else if ((numofhours < 168) && (numofhours >= 24))
    	        {
		this.getSessionData().setUserMessage(s_eventID + "Deleting part of event less than 7 days check policy for charges");	
		cancelcode = 1;
		}
	else
		{
		this.getSessionData().setUserMessage(s_eventID + "Deleting part ofevent more than 7 days ahead no charges");	
		cancelcode = 0;
		}
		

	
	 	/*System.out.println("** Start hour off cancel part of event ** "+starthi);
		System.out.println("Start min "+startmi);
		System.out.println("End hour off cancel module "+new_starthi);
		System.out.println("End min "+new_startmi);
		System.out.println("Event Day "+eventday);
		System.out.println("Event Month "+eventmonth);
		System.out.println("Event Year "+eventyear);
		System.out.println("today date "+Ctodaydate);
		System.out.println("today Month "+Ctodaymonth);
		System.out.println("Today Year "+Ctodayyear);
		System.out.println("Cancel Code "+cancelcode);
		System.out.println("Project ID "+proj_id);			
		System.out.println("User Login "+user_login);*/
		
		
    	try {
//    		System.out.println("about to save one ");
    		theC_event.setEventday(eventday);
    		theC_event.setEventm(eventmonth);
    		theC_event.setEventy(eventyear);
      		theC_event.setOwner(PersonFactory.findPerson(user_login));
      		try {
    		theC_event.setProj_owner(ProjectFactory.findProjectByID(proj_id));
    		} catch   (webscheduleBusinessException ex) {
         	throw new webschedulePresentationException("Error setting project owner", ex);
         	}
      		theC_event.setStarth(starthi);
    		theC_event.setStartm(startmi);
    		theC_event.setEndh(new_starthi);
    		theC_event.setEndm(new_startmi);
       		theC_event.setTodayd(Ctodaydate);
    		theC_event.setTodaym(Ctodaymonth);
    		theC_event.setTodayy(Ctodayyear);
     		theC_event.setCancelc(cancelcode);
  //  		System.out.println("about to save final ");
    		theC_event.save();
   // 		System.out.println("AN EVENT HAS BEEN SAVED ");
    		
    	 } catch(Exception ex) {
          throw new webschedulePresentationException("Error adding an event", ex);
     }
  } 	*/
  
  
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
    	eventinfo.set(this.getYear(),this.getMonth(),this.getDay());
    	int dow = eventinfo.get(eventinfo.DAY_OF_WEEK);
    	System.out.println("day of the week = "+Integer.toString(dow));
	
	int eventday=0, eventmonth=0, eventyear=0;
	String proj_id = null;
	  int starthi=0, startmi=0, endhi=0, endmi=0;
	  
	temp_hold_change changeevent = this.getchangeevent();
        starthi = changeevent.getcstarth();
        startmi = changeevent.getcstartm();
        endhi = changeevent.getcendh();
        endmi = changeevent.getcendm();
        projID = changeevent.getProjID();
	eventyear = changeevent.getceventyear();
	eventmonth = changeevent.getceventmonth();
	eventday = changeevent.getceventday();	
	
	try
	{    	
    	theProject = ProjectFactory.findProjectByID(projID);
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
    	
	
    	
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
    		theS_event.setOwner(this.getUser());
    		theS_event.setProj_owner(theProject);
    		theS_event.setDevelop(this.getUser().isDeveloper());
    		theS_event.setRepeat(false);
    		System.out.println("about to save two ");
    		theS_event.save();
    		System.out.println("AN EVENT HAS BEEN SAVED ");
    		
    	 } catch(Exception ex) {
    throw new webschedulePresentationException("Error adding an event, make sure all fields are filled", ex);
     }
      }  	



}


