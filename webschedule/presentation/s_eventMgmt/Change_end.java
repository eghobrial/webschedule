/**--------------------------------------------------------------
* Webschedule
*
* @class: Change_end
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
 * Change_end.java handles changing the event end time
 *
 */
public class Change_end extends BasePO
{
    /**
     * Constants
     */
    private static String SUBMIT_NAME = "submit";
    private static String PASSWORD_NAME = "password";
    private static String ERROR_NAME = "ERROR_NAME";
    private static String PROJ_ID = "projID";
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
    	String endh = this.getComms().request.getParameter(ENDH);
    	String endm = this.getComms().request.getParameter(ENDM);
    	
    	
    	
        Change_endHTML page = new Change_endHTML();

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



       HTMLOptionElement endhtemplateOption = page.getElementEndhtemplateOption();
       HTMLOptionElement endmtemplateOption = page.getElementEndmtemplateOption();

      Node endhSelect = endhtemplateOption.getParentNode();
      Node endmSelect = endmtemplateOption.getParentNode();


      endhtemplateOption.removeAttribute("id");
      endmtemplateOption.removeAttribute("id");


      endhtemplateOption.removeChild(endhtemplateOption.getFirstChild());
      endmtemplateOption.removeChild(endmtemplateOption.getFirstChild());

	

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

      HTMLOptionElement clonedOption = (HTMLOptionElement) endhtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(hour));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(temptext);
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                endhSelect.appendChild(clonedOption);
         }

      for (int min = 0; min < 60; min+=30)
      {

      HTMLOptionElement clonedOption = (HTMLOptionElement) endmtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(min));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(Integer.toString(min));
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                endmSelect.appendChild(clonedOption);
         }


        endhtemplateOption.getParentNode().removeChild(endhtemplateOption);
        endmtemplateOption.getParentNode().removeChild(endmtemplateOption);


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


     public String handleChange_end()
        throws HttpPresentationException
	    {
	      //***ChangeEvent***************************************
	     String proj_id = null;
	     int starthi=0, startmi=0;
	     int endhi=0, endmi=0;
	     String endh = this.getComms().request.getParameter(ENDH);
             String endm = this.getComms().request.getParameter(ENDM);
             int new_endhi =  Integer.parseInt(endh);
	     int new_endmi = Integer.parseInt(endm);	
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
	        subtract_projhours(proj_id,new_endhi,endhi,new_endmi,endmi);

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
    	String endh = this.getComms().request.getParameter(ENDH);
        String endm = this.getComms().request.getParameter(ENDM);
        int new_endhi =  Integer.parseInt(endh);
	int new_endmi = Integer.parseInt(endm);
	boolean isRepeat = false;
	
	
    	
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
            	isRepeat = s_event.isRepeat();
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
            	}		

    	try {
    		//System.out.println("about to save one ");
    		s_event.setDescription(description);
    		s_event.setStarth(starthi);
    		s_event.setStartm(startmi);
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
    	String endh = this.getComms().request.getParameter(ENDH);
        String endm = this.getComms().request.getParameter(ENDM);
        int new_endhi =  Integer.parseInt(endh);
	int new_endmi = Integer.parseInt(endm);
	
	
    	
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
		
    	try {
    		System.out.println("about to save one ");
    		theC_event.setEventday(eventday);
    		theC_event.setEventm(eventmonth);
    		theC_event.setEventy(eventyear);
      		theC_event.setOwner(this.getUser());
    		theC_event.setProj_owner(ProjectFactory.findProjectByID(proj_id));
      		theC_event.setStarth(new_endhi);
    		theC_event.setStartm(new_endmi);
    		theC_event.setEndh(endhi);
    		theC_event.setEndm(endmi);
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




