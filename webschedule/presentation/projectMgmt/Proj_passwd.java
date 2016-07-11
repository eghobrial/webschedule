/**--------------------------------------------------------------
* Webschedule
*
* @class: Proj_passwd
* @version
* @author: Eman Ghobrial
* @since: Jan 2001
*
*--------------------------------------------------------------*/
package webschedule.presentation.projectMgmt;

import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.business.s_event.*;
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

/**
 * Proj_passwd.java handles the project password verify
 *
 */
public class Proj_passwd extends BasePO
{
    /**
     * Constants
     */
    private static String SUBMIT_NAME = "submit";
    private static String PASSWORD_NAME = "password";
    private static String ERROR_NAME = "ERROR_NAME";
    private static String PROJ_ID = "projID";
	
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
     *  Process Check project event
     */
 /*   public String handleCheck_proj()
	    throws HttpPresentationException
    {
	String proj_id = this.getComms().request.getParameter(PROJ_ID);
	String password = this.getComms().request.getParameter(PASSWORD_NAME);
        Project project = null;

        try {
            project = ProjectFactory.findProjectByID(proj_id);
                        	
            if(null == project || !project.getPassword().equals(password)) {
                return showPage("Invalid project or password");
                // Show error message that user not found (bad username/password)
            } else {
                this.setSwitchProject(project);
            	this.getSessionData().setUserMessage("The project has been switched for this event ");
                throw new ClientPageRedirectException(PROJ_PASSWD_PAGE);
            }
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding project: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding project", ex);
        }
    }
   */

     public String handleSwitchproject()
        throws HttpPresentationException
	    {
	      //***ChangeEvent***************************************
	     String cur_proj_id = null;
	     int starthi=0, startmi=0, endhi=0, endmi=0;
	     String switch_proj_id = null;
	     String s_eventID = this.getS_eventID();
	     String password = this.getComms().request.getParameter(PASSWORD_NAME);
	     Project theSwitchedProject = this.getSwitchProject();
	
	     try {
	
	     if(null == theSwitchedProject || !theSwitchedProject.getPassword().equals(password)) {
                return showPage("Invalid project or password");
                // Show error message that user not found (bad username/password)
             } else {
	        try {
	    	S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
                starthi = s_event.getStarth();
            	startmi = s_event.getStartm();
            	endhi = s_event.getEndh();
            	endmi = s_event.getEndm();
            	cur_proj_id = s_event.getProjectID();
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
	        }
	        subtract_projhours(cur_proj_id,starthi,endhi,startmi,endmi);
                try
    		{    	
    		  switch_proj_id = theSwitchedProject.getHandle();
                } catch  (Exception ex) {
                throw new webschedulePresentationException("Error getting project information", ex);
	        }
                update_projhours(switch_proj_id, starthi,endhi,startmi,endmi);
                updateS_event(s_eventID);
                throw new ClientPageRedirectException(CHANGE_EVENT_PAGE);
                }
                } catch(webscheduleBusinessException ex) {
               this.writeDebugMsg("System error getting project password: " + ex.getMessage());
            throw new webschedulePresentationException("System error getting project password", ex);
        }
       }




        /*Update project hours in the delete case*/

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

    /*Update project hours in the schedule case*/

   protected void update_projhours(String projID,int starthi,int endhi,int startmi,int endmi)
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
    	donehours = theProject.getDonehours() + eventhours;
    	totalhours = theProject.getTotalhours() - eventhours;
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
     *
     */
    public String showPage(String errorMsg)
    {
        Proj_passwdHTML page = new Proj_passwdHTML();

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
    	S_event s_event = null;	
    	
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
    		s_event.setProj_owner(this.getSwitchProject());
    		s_event.setDevelop(this.getUser().isDeveloper());
    		//System.out.println("about to save two ");
    		s_event.save();
    		System.out.println("****AN EVENT HAS BEEN SAVED with new project ****** ");
    		
    	 } catch(Exception ex) {
          throw new webschedulePresentationException("Error adding an event", ex);
     }
      }  	


}




