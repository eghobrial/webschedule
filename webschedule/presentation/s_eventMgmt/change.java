/**--------------------------------------------------------------
* Webschedule
*
* @class: change for 3TW (used to be websch3t)
* Pilot hours 1 to 2 (Rick at June 2003)
* Pilot hours 1 to 1 (Rick at March 2004)
* Pilot not edited by users changing time(April 2005)
* @version
* @author: Eman Ghobrial
* @since: Sept 2002
* March 2004, bug fix for scheduling 1/2 block time back to back
* April 2006 new cancellation policy implemented for event less than 48 hours
*
*--------------------------------------------------------------*/
package webschedule.presentation.s_eventMgmt;

import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.business.s_event.*;
import webschedule.business.c_event.*;
import webschedule.business.r_event.*;
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
import java.lang.String;
import webschedule.SendMail;
import java.sql.Timestamp;

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
    
    private static String PERSONID ="personID";		
    private static String DESCRIPTION ="description";			
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
       changeHTML page = new changeHTML();
    	String temptext = null;
    	int temphour;
    /*	String starth = this.getComms().request.getParameter(STARTH);
    	String startm = this.getComms().request.getParameter(STARTM);
	String endh = this.getComms().request.getParameter(ENDH);
    	String endm = this.getComms().request.getParameter(ENDM);*/
	
String description = this.getComms().request.getParameter(DESCRIPTION);
    	
HTMLSelectElement	starth_select,startm_select,endh_select,endm_select;
HTMLCollection	starth_selectCollection,startm_selectCollection,endh_selectCollection,endm_selectCollection;
HTMLOptionElement	starth_option,startm_option,endh_option,endm_option;
String	starth_optionName, startm_optionName,endh_optionName,endm_optionName;
		
     starth_select = (HTMLSelectElement)page.getElementStarth();
     starth_selectCollection = starth_select.getOptions();	

     startm_select = (HTMLSelectElement)page.getElementStartm();
     startm_selectCollection = startm_select.getOptions();	
	
     endh_select = (HTMLSelectElement)page.getElementEndh();
     endh_selectCollection = endh_select.getOptions();	

     endm_select = (HTMLSelectElement)page.getElementEndm();
     endm_selectCollection = endm_select.getOptions();	
	
	
	
    	S_event s_event=null;
    	
     

        int starthi = 0;
        int startmi = 0;
        int endhi = 0, endmi = 0;
        String projID = null;
        String s_eventID = this.getS_eventID();
	String eventdes = null;

        try
            {
               s_event = S_eventFactory.findS_eventByID(s_eventID);
                starthi = s_event.getStarth();
            	startmi = s_event.getStartm();
            	endhi = s_event.getEndh();
            	endmi = s_event.getEndm();
            	projID = s_event.getProjectID();
		eventdes = s_event.getDescription();
          } catch (webscheduleBusinessException ex) {
         throw new webschedulePresentationException("Error getting event info", ex);
         }

 if(null != description   && description.length() != 0) {
                page.getElementDescription().setValue(description);
            } else {
                page.getElementDescription().setValue(eventdes);
            }



String starths = Integer.toString(starthi);
String startms = Integer.toString(startmi);
String endhs = Integer.toString(endhi);
String endms = Integer.toString(endmi);

System.out.println("**** Starth *******"+starths);		
System.out.println("**** Startm *******"+startms);
System.out.println("**** Endh *******"+endhs);
System.out.println("**** Endm *******"+endms);

int starth_optionlen = starth_selectCollection.getLength();
	for (int i=0; i< starth_optionlen; i++) {
	  starth_option = (HTMLOptionElement)starth_selectCollection.item(i);
	  starth_optionName = starth_option.getValue();
	  if (starth_optionName.equals(starths))
	     starth_option.setSelected(true);
	  else
	     starth_option.setSelected(false);
	    }  
	    
	    
	      int startm_optionlen = startm_selectCollection.getLength();
	for (int i=0; i< startm_optionlen; i++) {
	  startm_option = (HTMLOptionElement)startm_selectCollection.item(i);
	  startm_optionName = startm_option.getValue();
	  if (startm_optionName.equals(startms))
	     startm_option.setSelected(true);
	  else
	     startm_option.setSelected(false);
	    }  
 
 
 int endh_optionlen = endh_selectCollection.getLength();
	for (int i=0; i< endh_optionlen; i++) {
	  endh_option = (HTMLOptionElement)endh_selectCollection.item(i);
	  endh_optionName = endh_option.getValue();
	  if (endh_optionName.equals(endhs))
	     endh_option.setSelected(true);
	  else
	     endh_option.setSelected(false);
	    }  
	    
	    
	      int endm_optionlen = endm_selectCollection.getLength();
	for (int i=0; i< endm_optionlen; i++) {
	  endm_option = (HTMLOptionElement)endm_selectCollection.item(i);
	  endm_optionName = endm_option.getValue();
	  if (endm_optionName.equals(endms))
	     endm_option.setSelected(true);
	  else
	     endm_option.setSelected(false);
	    }  
 
	 
            HTMLOptionElement templateOption = page.getElementTemplateOption();
        Node PersonSelect = templateOption.getParentNode();
        templateOption.removeAttribute("id");
        templateOption.removeChild(templateOption.getFirstChild());


             try {
        	Operates[] OperatesList = OperatesFactory.getOperatesList();
		String proj_id = this.getProject().getHandle();
		String op_id = s_event.getOperatorID();
		//System.out.println("Project ID off EditEvent = "+proj_id);
		for (int numOperates=0; numOperates < OperatesList.length; numOperates++) {
		  Operates currentOperates = OperatesList[numOperates];
		 //  System.out.println("Project ID off EditEvent Operates = "+currentOperates.getProjectID());
		   if (currentOperates.getProjectID().equals(proj_id))
		        {
				 
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
                Operator theOperator = OperatorFactory.findOperatorByID(op_id);
				Timestamp cts = new Timestamp(System.currentTimeMillis());
		System.out.println("Current Timestamp"+cts);
		Timestamp sts = theOperator.getSFTTS();
		System.out.println("Test Timestamp"+sts);
		
		long ctsl = cts.getTime();
		System.out.println("Current Timestamp"+ctsl);
		
		long stsl = sts.getTime();
		System.out.println("Current Timestamp"+stsl);
		
		long difference = ctsl - stsl ;
		System.out.println("differnce  "+difference);
		
		
		if (currentOperates.getOperatorID().equals(op_id))
		   clonedOption.setSelected(true); 
		else 
		   clonedOption.setSelected(false);
                
		clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                PersonSelect.appendChild(clonedOption);
		/*String op_id = s_event.getOperatorID();
		if (currentOperates.getOperatorID().equals(op_id))
		{templateOption.setSelected(true);}*/
		}
		    
			}
		
		}
        	
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Operates List: " + ex);
            throw new webschedulePresentationException("Error getting Operates List: ", ex);
	    }
	
  templateOption.getParentNode().removeChild(templateOption);



       
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
	     
	     String s_eventID = this.getS_eventID();
	     	
	        try {
	    	S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
                starthi = s_event.getStarth();
            	startmi = s_event.getStartm();
            	endhi = s_event.getEndh();
            	endmi = s_event.getEndm();
		description = s_event.getDescription();
            	proj_id = s_event.getProjectID();
		eventday =  s_event.getEventday();
            	eventmonth =  s_event.getEventmonth();
            	eventyear =  s_event.getEventyear();
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
	        }
	
	//calculate old event hours
	if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    	else starttime = (double) starthi;
    	
    	if (endmi == 30)
    		 {
	        double endt = (double) endhi;
    		endtime =     endt + 0.5;
	    }	
    	else endtime = (double) endhi;	
    	eventhours = endtime - starttime;
	
	
	//calculate new event hours
	if (new_startmi == 30)
    		{
    		double startt = (double) new_starthi;
    		new_starttime =    startt + 0.5;
    		}
    	else new_starttime = (double) new_starthi;
    	
    	if (new_endmi == 30)
		{
		double endt1 = (double) new_endhi;
    		new_endtime =     endt1 + 0.5;
		}
    	else new_endtime = (double) new_endhi;

	
	
	new_eventhours = new_endtime - new_starttime;

if (check_conflict()==1) return showPage("You entered the date that conflict check calendar and try again");
else if (check_conflict_blocktime()==1) return showPage ("You entered the date that conflict check calendar and try again");
else if (check_change_time()==1) return showPage ("You cannot change this time slot "); 
else if  (check_time()==1) return showPage("You entered a wrong time end time must be after start time");
else if (check_proj_hours(1)==1) return showPage("You have acquired all project hours,check with the committee");
else if  (check_equal()==1) return showPage("You entered a start time that is equal to end time");
else if (check_user(s_eventID)==1) return showPage("You can not change another user event");

else {       //else pass conflicts
    if (new_eventhours < eventhours) 

	// no cancelation and may be addition
	{
	
	System.out.println("****New Event Hours cancellation****"+Double.toString(new_eventhours));
System.out.println("**** Project ID *******"+proj_id);	
	change_projhours(proj_id,new_eventhours,eventhours);
		//change_pilothours(proj_id,new_eventhours,eventhours);
		updateS_event(s_eventID);
	   try {
  	    	C_event theC_event = new C_event();
      		saveC_event(s_eventID,theC_event);
	
saveR_event(proj_id,starthi,endhi,startmi,endmi,s_eventID,new_starthi,new_endhi,new_startmi,new_endmi);
		this.removeS_eventIDFromSession();
     		 }catch  (Exception ex) {
	    throw new webschedulePresentationException("Error Creating a blank Canceled event", ex);
		 }
    		throw new ClientPageRedirectException(SELECT_DATE_PAGE);
		
	}
     else
	//cancelation save canceled portion
	{
	
System.out.println("****New Event Hours addition****"+Double.toString(new_eventhours));
		change_projhours(proj_id,new_eventhours,eventhours);
		//change_pilothours(proj_id,new_eventhours,eventhours);
		updateS_event(s_eventID); 
		this.removeS_eventIDFromSession();
		throw new ClientPageRedirectException(SELECT_DATE_PAGE);
       }
    } //else pass conflicts
 } //end routine


   

 public int check_user(String s_eventID)
      throws HttpPresentationException, webschedulePresentationException
     {

           String projID = null;
           String user_event = null;
           String current_user = null;
           	
    try {
	    S_event s_event = S_eventFactory.findS_eventByID(s_eventID);

            projID = s_event.getProjectID();
            user_event = s_event.getOwnerLogin();
              System.out.println("login user-"+user_event+"-");
            // Catch any possible database exception as well as the null pointer
            //  exception if the s_event is not found and is null after findS_eventByID
	    } catch(Exception ex) {
            this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
        }

          try
          {
    		current_user = this.getUser().getLogin();
    		System.out.println("current user-"+current_user+"-");
    	  } catch (webscheduleBusinessException ex) {
         	throw new webschedulePresentationException("Error getting user login name", ex);
          }

	if (current_user.equals(user_event))
		{
		 System.out.println("current user matches");
		return 0;
		
		}
	else 	
		{
		 System.out.println("current user does not match");
        	return 1;
        	}
	}



 public int check_change_time()
     throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method **off check conflict = "+starth);
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
    	    	    	
      	int eventyear = 0 ;
        int eventmonth = 0;
        int eventday = 0;
	int eventstarth=0;
        int eventstartm=0;
        int eventendh=0;
        int eventendm = 0;
        int conflict = 0;
//       int conflict_wnext_has_been_set = 0;
	int conflict_has_been_set = 0;
//	int nexteventexists = 0;

	S_event  next_current_S_event = null;

	String s_eventID = null;
	
	try {
		s_eventID = this.getS_eventID();
	    	S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
                eventday = s_event.getEventday();
            	eventmonth = s_event.getEventmonth();
            	eventyear = s_event.getEventyear();
            	eventstarth = s_event.getStarth();
            	eventstartm = s_event.getStartm();
            	eventendh = s_event.getEndh();
            	eventendm = s_event.getEndm();
            	
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
	        }
     
     
       //calculation for the time the event has been canceled (current time)
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
    	Date cancelinfodate =  cancelinfo.getTime();
    	long cancelinfoms = cancelinfodate.getTime();
    	   	
    	//calculation for the event info (end time)
    	Calendar endeventinfo = Calendar.getInstance();
	//check the cancelation to 30 an hour before end time
	/*if (eventendm == 30)
	  {
	   eventendm=0;
	  } else {
	   eventendh = eventendh - 1;
	   eventendm = 30;
	  }  */
    	endeventinfo.set(eventyear, eventmonth, eventday, event_endhi, event_endmi);
    	Date endeventinfodate =   endeventinfo.getTime();
    	long endeventinfoms = endeventinfodate.getTime();
	long enddifference = endeventinfoms - cancelinfoms;
	//long endnumofhours = enddifference/3600000;
    	
	
	//calculation for the event info (start time)
    	Calendar starteventinfo = Calendar.getInstance();
	//check the cancelation to 30 an hour before start time
	/*if (eventstartm == 30)
	  {
	   eventstartm=0;
	  } else {
	   eventstarth = eventstarth - 1;
	   eventstartm = 30;
	  }  */
    	starteventinfo.set(eventyear, eventmonth, eventday, eventstarth, eventstartm);
    	Date starteventinfodate =   starteventinfo.getTime();
    	long starteventinfoms = starteventinfodate.getTime();
	long startdifference = starteventinfoms - cancelinfoms;
	//long startnumofhours = startdifference/3600000;
    	


	//calculation for the modified event info (end time)
    	Calendar modendeventinfo = Calendar.getInstance();
	//check the cancelation to 30 an hour before end time
	
	//I am commminting that it is confusing--E
/*	if (eventendm == 30)
	  {
	   eventendm=0;
	  } else {
	   eventendh = eventendh - 1;
	   eventendm = 30;
	  }  */
	modendeventinfo.set(eventyear, eventmonth, eventday, event_endhi, event_endmi);
    	Date modendeventinfodate =   modendeventinfo.getTime();
    	long modendeventinfoms = modendeventinfodate.getTime();
	long modenddifference = modendeventinfoms - cancelinfoms;
	//long endnumofhours = enddifference/3600000;
    	
	//calculation for the actal event info (start time)
    	Calendar modstarteventinfo = Calendar.getInstance();
	//check the cancelation to 30 an hour before start time
	
/*	if (eventstartm == 30)
	  {
	   eventstartm=0;
	  } else {
	   eventstarth = eventstarth - 1;
	   eventstartm = 30;
	  }  */
    	
	modstarteventinfo.set(eventyear, eventmonth, eventday, event_starthi, event_startmi);
    	Date modstarteventinfodate =   modstarteventinfo.getTime();
    	long modstarteventinfoms = modstarteventinfodate.getTime();
	long modstartdifference = modstarteventinfoms - cancelinfoms;
	
	

	// check if current time is greater than eventstart time
	//if (startdifference < 1800000)
	if (startdifference < 100)
	    { 
	     //set a conflict if the user tried to change starttime
	     if (modstarteventinfoms != starteventinfoms)
	       {
	       conflict = 0;
	       System.out.println ("***Conflict has been set to one phase one***");
	       conflict_has_been_set = 1;
	      
	       }
	       //set a conflict user try to change end time and the current time is less taahn an hour from the requested time
	    // else if (enddifference < 1800000)
	    else if (enddifference < 100)
	      {
	       conflict = 0;
	         System.out.println ("***Conflict has been set to one phase two***");
	       conflict_has_been_set = 1;
	     
	       }
	    }   
	       
	     System.out.println ("System Conflict flag of change module "+Integer.toString(conflict));
	     if (conflict_has_been_set == 1)
	         return 1;
	     else return 0;	 
	
          	
}





 public int check_conflict()
     throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method **off check conflict = "+starth);
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
    	    	    	
      	int year = 0 ;
        int month = 0;
        int day = 0;
        int conflict = 0;
       int conflict_wnext_has_been_set = 0;
	int conflict_has_been_set = 0;
	int nexteventexists = 0;

	S_event  next_current_S_event = null;

	String s_eventID = null;
	
	try {
		s_eventID = this.getS_eventID();
	    	S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
                year = s_event.getEventyear();
            	month = s_event.getEventmonth();
            	day = s_event.getEventday();
            	
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
	        }
          try {
  	S_event[] EventList = S_eventFactory.findS_eventsForDate(year,month,day);
	s_eventID = this.getS_eventID();
System.out.println("S_event ID off change  "+s_eventID);
          	
	         // Get collection of events and loop through collection
//	test: {
	for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	
			
			S_event currentS_event = EventList[numEvents];
			String currentS_eventID = currentS_event.getHandle();
 			System.out.println("current S_event ID off change  "+currentS_eventID);
        		int starthi = currentS_event.getStarth();
	        	int startmi = currentS_event.getStartm();
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	 System.out.println("number of events off change *** "+Integer.toString(numEvents));
	 System.out.println("number of total events off change *** "+Integer.toString(EventList.length));
	
	
	
	if (s_eventID.equals(currentS_eventID))
	 	 {
	 	   System.out.println("equality no events follow ");
	   	  continue;
		  }  
		
				 
	else //do not compare the same event
	{			 
	
	       	if (event_starthi < starthi)
	        		{
	        		  if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag ***1*** "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag ***2***"+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		
	        		 }
	        		
	        	// 1. if it is equal check min and end time	
	        	else if (event_starthi == starthi)
	        		{
	        		 if (event_startmi == startmi)
	        		   {  	//if
	        		   conflict = 1;
	        		   System.out.println("System conflict flag  ***3****"+Integer.toString(conflict));
	        		    conflict_has_been_set = 1;
	        		   //return conflict;
	        		   break;
	        		   }        //if
	        		  
				  
				   else if (event_starthi == endhi)
	        		  	{
	        		  	  if (event_startmi < endmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
    
				  

				  
				  /* else {//else 1
	        		
	        			if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag ****4**** "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  ***5****"+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		   }  //else 1*/
	        		 }
	        		
	        	
                       //1.    if it is greater than the booked on make sure it is not in the middle
                       else if (event_starthi > starthi)
                       		{
                       		if (event_starthi < endhi)
                       		 {
                       		   conflict = 1;
                       		   System.out.println("System conflict flag *****6*****  "+Integer.toString(conflict));
                       		   conflict_has_been_set = 1;
                       		   break;
                       		 }
                       		else if (event_starthi == endhi)
                       		    {
                       		      if (event_startmi < endmi)
                       		      {
                       		
                       		      	conflict = 1;
                       		      	 System.out.println("System conflict flag  ****7*****"+Integer.toString(conflict));
                       		      	  conflict_has_been_set = 1;
                       		      	 //return conflict;
                       		      	 break;
                       		      	  }
                       		    }
                       		
                       		}
	             	      	
} //else do not compare the same event
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
	System.out.println("today long var = "+Long.toString(todaylong) );
	
	
	Calendar lastscaninfo = Calendar.getInstance();
    	lastscaninfo.set(lsy,lsm,lsd);    
	Date lastscaninfodate =   lastscaninfo.getTime();
    	long lastscanlong = lastscaninfodate.getTime();
	System.out.println("last scan long var = "+Long.toString(lastscanlong) );
	
	
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
	
	//positive
	if (lastscanlong > 0)
		lastscandiff = (todaylong - lastscanlong) / 3600000;
	else
	      	lastscandiff = 0;
      if (recertlong > 0)
	   recertdiff = (todaylong - recertlong) / 3600000;
      else 	
           recertdiff = 0;
	

	// check have not scanned for over 4 month
	if (lastscandiff > 2880)
	{
	  // no recert on record for the last 4 month
	    if (recertdiff > 2880)
	        {
		conflict_has_set = 1;
		System.out.println("lapsed diff = "+Long.toString(lastscandiff) +"second diff "+Long.toString(recertdiff));
		//break;
		}
	}	
	
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
    	
    	Project theProject = this.getProject();
    	
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
		
    	//changed on April 2006
	
	if (differencehours >=0)	
    	donehours = theProject.getDonehours() - differencehours;
	else donehours = theProject.getDonehours() + differencehours;
    	totalhours = theProject.getTotalhours() ;
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }


System.out.println("Edit Project hours Visited off change module Total Done Hours="+Double.toString(differencehours));

    	

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

/*protected void change_pilothours(String projID,double new_eventhours,double eventhours)
   throws HttpPresentationException
   {
   	
   	double donehours;
   	double totalhours;
   	double differencehours;
   	
   	Project theProject=null;
       	
    	differencehours = (new_eventhours - eventhours);
    	
	try
	{    	
    	theProject = ProjectFactory.findProjectByID(projID);
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
    	
String proj_name, pilot_proj_name;
    	
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
	
	System.out.println("Edit **Pilot** Project hours Visited off change module ");
	
    	try
    	{    	
    	donehours = pilotProject.getDonehours();
	
    	totalhours = pilotProject.getTotalhours()+ differencehours;
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }

	    try {
            pilotProject.setProj_name(pilotProject.getProj_name());
            pilotProject.setPassword(pilotProject.getPassword());
	    pilotProject.setDescription(pilotProject.getDescription());
	    pilotProject.setIndexnum(pilotProject.getIndexnum());
	    pilotProject.setTotalhours(totalhours);
	    pilotProject.setDonehours(donehours);
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
	    
System.out.println("Pilot Project Saved");	    
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error updating pilot project hours", ex);
        }

    }//if statment

}    

*/


 
    /**
     * Method to update event to the database with new project
     */
 protected void updateS_event(String s_eventID)
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
	String user_login = null,reason=null;
	boolean isRepeat = false, isGrab=false;
	C_event theC_event = null;
    	 String personID = this.getComms().request.getParameter(PERSONID);
           System.out.println("Person ID = "+personID);    
	Operator operator = null;

  try {
            operator = OperatorFactory.findOperatorByID(personID);
	    if ( (operator==null) ){
this.getSessionData().setUserMessage("Please choose a valid operator, if non listed email eghobrial@ucsd.edu");
throw new ClientPageRedirectException(CHANGE_PAGE);
}
else
	this.setOperator(operator);
       
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("You must choose an operator, if none listed please email eghobrial@ucsd.edu you project info.: " + ex.getMessage());
            throw new webschedulePresentationException("You must choose an operator, if none listed please email eghobrial@ucsd.edu you project info.", ex);
        }
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
		 isGrab = s_event.isGrabbable();
		 reason = s_event.getReasondropped();
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
		s_event.setPrevowner(this.getUser());
    		s_event.setPrevproj_owner(ProjectFactory.findProjectByID(proj_id));
    		s_event.setDevelop(this.getUser().isDeveloper());
    		s_event.setRepeat(isRepeat);
		s_event.setGrab(isGrab);
		s_event.setReasondropped(reason);
		s_event.setOperator(this.getOperator());
    		//System.out.println("about to save two ");
    		s_event.save();
    		//System.out.println("****AN EVENT HAS BEEN SAVED with new project ****** ");
    		
    	 } catch(Exception ex) {
          throw new webschedulePresentationException("Error adding an event", ex);
     }


  }



  /*save released event if cancelation happend less than 48 hours in advance*/

  protected void saveR_event(String projID,int starthi,int endhi,int startmi,int endmi, String s_eventID,int new_starthi,int new_endhi,int new_startmi,int new_endmi)
   throws HttpPresentationException
   {
   	double starttime=0.0;
   	double endtime=0.0;
	double new_starttime=0.0;
   	double new_endtime=0.0;
   	double donehours;
   	double totalhours;
   	double eventhours;
   	
   	Project theProject=null;
	

	//calculate old event hours
	if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    	else starttime = (double) starthi;
    	
    	if (endmi == 30)
	   {
	        double endt = (double) endhi;
    		endtime =     endt + 0.5;
	    }	
    	else endtime = (double) endhi;	

    	eventhours = endtime - starttime;
	
	

	//calculate new event hours
	if (new_startmi == 30)
    		{
    		double startt1 = (double) new_starthi;
    		new_starttime =    startt1 + 0.5;
    		}
    	else new_starttime = (double) new_starthi;
    	
    	if (new_endmi == 30)
		{ 
		double endt1 = (double) new_endhi;
		new_endtime =     endt1 + 0.5;
		}
    	else new_endtime = (double) new_endhi;
		

	   int eventday=0;
           int eventmonth=0;
           int eventyear=0;
           int starth=0;
           int startm=0;
           int endh=0;
           int endm = 0;
           int cancelcode=0;
	   int eventdayofw=0;
      //   String projID = null;
	String personID = null;
	String operatorID = null;
           String user_login = null;

    try {
	    S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
            eventday = s_event.getEventday();
            eventmonth = s_event.getEventmonth();
            eventyear = s_event.getEventyear();
            starth = s_event.getStarth();
            startm = s_event.getStartm();
            endh = s_event.getEndh();
            endm = s_event.getEndm();
	    eventdayofw = s_event.getEventdayofw();
            user_login = s_event.getOwnerLogin();
	    personID = s_event.getUserID();
            projID = s_event.getProjectID();
	    operatorID=s_event.getOperatorID();
            // Catch any possible database exception as well as the null pointer
            //  exception if the s_event is not found and is null after findS_eventByID
	    } catch(Exception ex) {
            this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
        }
        //calculation for the time the event has been canceled
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
	int todayhour = cancelinfo.get(cancelinfo.HOUR);
	int todaymin = cancelinfo.get(cancelinfo.MINUTE);
	
    	Date cancelinfodate =  cancelinfo.getTime();
    	long cancelinfoms = cancelinfodate.getTime();
    	   	
    	//calculation for the event info
    	Calendar eventinfo = Calendar.getInstance();
	
    	eventinfo.set(eventyear, eventmonth, eventday, starth, startm);
    	Date eventinfodate =   eventinfo.getTime();
    	long eventinfoms = eventinfodate.getTime();
	long difference = eventinfoms - cancelinfoms;
	//double numofhours = Double(difference)/3600000;
	
	System.out.println("*******Check date is OK num of hours = " + Long.toString(difference));
    	




// check to see if the cancellation is less than 48 hours charge the pilot hours one hour.

if (difference < 172800000)
	{
	
	
if ((starttime != new_starttime) && (endtime != new_endtime))
    {
    //save head and tail
     saveR_eventfinal(s_eventID,starthi,startmi, new_starthi,new_startmi);
      saveR_eventfinal(s_eventID, new_endhi,new_endmi,endhi,endmi);
    }	
else if (starttime != new_starttime)
//save only head
	saveR_eventfinal(s_eventID,starthi,startmi,new_starthi,new_startmi);
else if (endtime != new_endtime)
     	saveR_eventfinal(s_eventID,new_endhi,new_endmi,endhi,endmi);
	
	System.out.println("Check date is OK num of hours = " + Long.toString(difference));
		
		}
	else 	
	 {
	 
	 //do nothing
	 System.out.println("Check date is OK num of hours = " + Long.toString(difference));
        	
		}
 	
	

}    

       
  protected void  saveR_eventfinal(String s_eventID,int starth, int startm, int endh, int endm) 	
   throws HttpPresentationException, webschedulePresentationException
   {   
    


R_event theR_event = null;
	 Project Project = null;
   Person Person = null;
   Operator Operator = null;
       	
	
	
	
	   int eventday=0;
           int eventmonth=0;
           int eventyear=0;
           int cancelcode=0;
	   int eventdayofw=0;
         String projID = null;
	String personID = null;
	String operatorID = null;
           String user_login = null;

    
    //calculation for the time the event has been canceled
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
	int todayhour = cancelinfo.get(cancelinfo.HOUR);
	int todaymin = cancelinfo.get(cancelinfo.MINUTE);
	
    
    
    try {
	    S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
            eventday = s_event.getEventday();
            eventmonth = s_event.getEventmonth();
            eventyear = s_event.getEventyear();
            eventdayofw = s_event.getEventdayofw();
            user_login = s_event.getOwnerLogin();
	    personID = s_event.getUserID();
            projID = s_event.getProjectID();
	    operatorID=s_event.getOperatorID();
            // Catch any possible database exception as well as the null pointer
            //  exception if the s_event is not found and is null after findS_eventByID
	    } catch(Exception ex) {
            this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
        }
        
	

 try
	{    	
    	Person = PersonFactory.findPersonByID(personID);
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting person information", ex);
        }
	
	try
	{    	
    	Project = ProjectFactory.findProjectByID(projID);
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }     
   
   
   try
	{    	
    	Operator = OperatorFactory.findOperatorByID(operatorID);
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting operator information", ex);
        }    
   



try
		 {
		 theR_event = new R_event();
		 }catch  (Exception ex) {
          	throw new webschedulePresentationException("Error Creating a blank Canceled event", ex);
		     }
	
	
	
	try {
    		System.out.println("about to save one ");
		theR_event.setStarth(starth);
    		theR_event.setStartm(startm);
    		theR_event.setEndh(endh);
    		theR_event.setEndm(endm);
    		theR_event.setEventday(eventday);
    		theR_event.setEventmonth(eventmonth);
    		theR_event.setEventyear(eventyear);
    		theR_event.setEventdayofw(eventdayofw);
    		theR_event.setCancelday(todaydate);
    		theR_event.setCancelmonth(todaymonth);
		theR_event.setCancelyear(todayyear);
		theR_event.setCancelh(todayhour);
		theR_event.setCancelm(todaymin);
		System.out.println("****today hour****"+Integer.toString(todayhour));
		System.out.println("****today min****"+Integer.toString(todaymin));
    		theR_event.setOwner(Person);
    		theR_event.setProj_owner(Project);
		theR_event.setOperator(Operator);
    		theR_event.setUsed(0);
		System.out.println("about to save two ");
    		theR_event.save();
    		System.out.println("AN EVENT HAS BEEN SAVED ");
    		
    	 } catch(Exception ex) {
    throw new webschedulePresentationException("Error adding an event, make sure all fields are filled", ex);
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
	else if (numofhours < 48)
		{
this.getSessionData().setUserMessage(s_eventID + "Deleting part of event less than 48 hours subject to plenty if not used");	
		cancelcode = 2;
		}
	else if ((numofhours < 168) && (numofhours >= 48))
    	        {
		this.getSessionData().setUserMessage(s_eventID + "Deleting part of event less than 7 days ");	
		cancelcode = 1;
		}
	else
		{
		this.getSessionData().setUserMessage(s_eventID + "Deleting part ofevent more than 7 days ahead ");	
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
  } 	


}


