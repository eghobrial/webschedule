/**--------------------------------------------------------------
* Webschedule
*
* @class: Change_start
* @version
* @author: Eman Ghobrial
* @since: Feb 2001
*
*--------------------------------------------------------------*/
package webschedule.presentation.s_eventMgmt;

import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.business.s_event.*;
import webschedule.business.c_event.*;
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
 * Change_start.java handles changing the event start time
 *
 */
public class Change_start extends BasePO
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
    	
    	
    	
        Change_startHTML page = new Change_startHTML();

        int starthi = 0;
        int startmi = 0;
        int endhi = 0, endmi = 0;
        String projID = null;
        String s_eventID = this.getS_eventID();

        try
            {
               S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
                starthi = s_event.getStarth();
            	startmi = s_event.getStartm();
            	endhi = s_event.getEndh();
            	endmi = s_event.getEndm();
            	projID = s_event.getProjectID();
          } catch (webscheduleBusinessException ex) {
         throw new webschedulePresentationException("Error getting event info", ex);
         }



       HTMLOptionElement starthtemplateOption = page.getElementStarthtemplateOption();
       HTMLOptionElement startmtemplateOption = page.getElementStartmtemplateOption();

      Node starthSelect = starthtemplateOption.getParentNode();
      Node startmSelect = startmtemplateOption.getParentNode();


      starthtemplateOption.removeAttribute("id");
      startmtemplateOption.removeAttribute("id");


      starthtemplateOption.removeChild(starthtemplateOption.getFirstChild());
      startmtemplateOption.removeChild(startmtemplateOption.getFirstChild());

	

      for (int hour = starthi; hour < endhi; hour ++)
      {
      	if (hour < 12) temptext = Integer.toString(hour)+"am";
      	else if (hour == 12) temptext = Integer.toString(hour)+"Noon";
      	else if (hour == 24) {
      		temphour = hour-12;
      		temptext = Integer.toString(temphour)+"Mid.";
      		}
      	else {
      		temphour = hour -12;
      		temptext = Integer.toString(temphour)+"pm";
      	     }		

      HTMLOptionElement clonedOption = (HTMLOptionElement) starthtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(hour));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(temptext);
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                starthSelect.appendChild(clonedOption);
         }

      for (int min = 0; min < 60; min+=30)
      {

      HTMLOptionElement clonedOption = (HTMLOptionElement) startmtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(min));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(Integer.toString(min));
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                startmSelect.appendChild(clonedOption);
         }


        starthtemplateOption.getParentNode().removeChild(starthtemplateOption);
        startmtemplateOption.getParentNode().removeChild(startmtemplateOption);


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
     *  Process Change start time for the event
     */


     public String handleChange_start()
        throws HttpPresentationException
	    {
	      //***ChangeEvent***************************************
	     String proj_id = null;
	     int starthi=0, startmi=0, endhi=0, endmi=0;
	     String starth = this.getComms().request.getParameter(STARTH);
             String startm = this.getComms().request.getParameter(STARTM);
             int new_starthi =  Integer.parseInt(starth);
	     int new_startmi = Integer.parseInt(startm);	
	     String s_eventID = this.getS_eventID();
	     	
	        try {
	    	S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
                starthi = s_event.getStarth();
            	startmi = s_event.getStartm();
            	endhi = s_event.getEndh();
            	endmi = s_event.getEndm();
            	proj_id = s_event.getProjectID();
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
	        }
	        subtract_projhours(proj_id,starthi,new_starthi,startmi,new_startmi);
                updateS_event(s_eventID);
                try {
                C_event theC_event = new C_event();
                saveC_event(s_eventID,theC_event);
                 }catch  (Exception ex) {
          	throw new webschedulePresentationException("Error Creating a blank Canceled event", ex);
		     }

                throw new ClientPageRedirectException(CHANGE_EVENT_PAGE);

       }

      /*Update project hours in the partial delete case*/

   protected void subtract_projhours(String projID,int starthi,int endhi,int startmi,int endmi)
   throws HttpPresentationException
   {
   	double starttime;
   	double endtime;
   	double donehours;
   	double totalhours;
   	double eventhours;
   	
   	Project theProject=null;
       	
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
    	
	try
	{    	
    	theProject = ProjectFactory.findProjectByID(projID);
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
    	
    	/*Project theProject = this.getProject();*/
    	
    	try
    	{    	
    	donehours = theProject.getDonehours() - eventhours;
    	totalhours = theProject.getTotalhours() + eventhours;
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
	    theProject.save();	
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding project", ex);
        }

    }




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
        int new_starthi =  Integer.parseInt(starth);
	int new_startmi = Integer.parseInt(startm);
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
    		s_event.setEndh(endhi);
    		s_event.setEndm(endmi);
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
    		System.out.println("****AN EVENT HAS BEEN SAVED with new project ****** ");
    		
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
		

	
	 	System.out.println("** Start hour off cancel part of event ** "+starthi);
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
		System.out.println("User Login "+user_login);
		
		
    	try {
    		System.out.println("about to save one ");
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
    		System.out.println("about to save final ");
    		theC_event.save();
    		System.out.println("AN EVENT HAS BEEN SAVED ");
    		
    	 } catch(Exception ex) {
          throw new webschedulePresentationException("Error adding an event", ex);
     }
     } 	
}




