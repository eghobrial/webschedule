
/**--------------------------------------------------------------
* Webschedule
*
* @class: ChangeEvent.java
* ChangeEvent on the 3T version
* Pilot hours rate 1 to 2 (Rick on June 2003) (Line 784)
* Pilot hours rate 1 to 1 (Rick on March 2004)
* Pilot hours credited at the end of each month
* @version
* @author: Eman Ghobrial
* @since: Sept 2002
* @update: last update is March 2005, donot credit pilot on the spot, instead at the 
*  end of the month
*	April 2006 introduced canellation plenty
*
*
*--------------------------------------------------------------*/


package webschedule.presentation.s_eventMgmt;
import webschedule.business.*;
import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.business.s_event.*;
import webschedule.business.c_event.*;
import webschedule.business.r_event.*;
import webschedule.business.operates.*;
import webschedule.business.operator.*;
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
 * Login.java handles the login functionality of the webschedule app.
 *
 */
public class ChangeEvent extends BasePO
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
    private static String EVENT_ID = "Event_id";
     private static String PROJ_ID = "projID";

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


    
    /**
     * 
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
    	String temptext = null;
    	int temphour;
    	/*String starth = this.getComms().request.getParameter(STARTH);
    	String startm = this.getComms().request.getParameter(STARTM);
    	String endh = this.getComms().request.getParameter(ENDH);
    	String endm = this.getComms().request.getParameter(ENDM);
    	String description = this.getComms().request.getParameter(DESCRIPTION);*/
        String event_id = this.getS_eventID();
	String startms, endms;

    	System.out.println("event_id from show page module =   "+event_id);
    	
        ChangeEventHTML page = new ChangeEventHTML();
        HTMLScriptElement script = new ChangeEventScriptHTML().getElementRealScript();
        XMLCUtil.replaceNode(script, page.getElementDummyScript());		


        S_event s_event = null;

        try {
	        s_event  = S_eventFactory.findS_eventByID(event_id);
	
	        	int  month = s_event.getEventmonth();
	        	int year = s_event.getEventyear();
			int day = s_event.getEventday();	
	                String eventdatestring = Integer.toString(month+1)+"/"+Integer.toString(day)+"/"+Integer.toString(year);
		        page.setTextDateID ("Event date to change: " + eventdatestring);
			
                   	int starthi = s_event.getStarth();
	        	int startmi = s_event.getStartm();
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
			     	
	        	
			
			//String starttime = Integer.toString(starthi)+":"+Integer.toString(startmi)+" "+ampm;
	        		   	
	        	int endhi = s_event.getEndh();
	        	int endmi = s_event.getEndm();
	        	
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
	        	
	        	String description = s_event.getDescription();
	        	page.setTextDescription(description);
	        	String ownername = s_event.getOwnerFirstname()+" "+s_event.getOwnerLastname();
	        	page.setTextOwner(ownername);
	        	
          		page.setTextProj_name(s_event.getProj_owner_name());
			page.setTextOwneremail(s_event.getOwnerEmail());

			String operatorname = s_event.getOperatorFirstname()+" "+s_event.getOperatorLastname();
	        	page.setTextOperator(operatorname);
	        	page.setTextOperatoremail(s_event.getOperatorEmail());  	
	        	
	        	
	        	} catch(Exception ex) {
	        this.writeDebugMsg("Error getting Event : " + ex);
            throw new webschedulePresentationException("Error getting Events for date: ", ex);
	    }
	        	
	//    HTMLOptionElement templateOption = page.getElementTemplateOption();

        //Node ProjSelect = templateOption.getParentNode();
	    		
	    // Remove ids to prevent duplicates
        //  (browsers don't care, but the DOM does)

        //templateOption.removeAttribute("id");


         // Remove the dummy storyboard text from the prototype HTML

        /*templateOption.removeChild(templateOption.getFirstChild());
	
	    try {
	        Project[] ProjList = ProjectFactory.findProjectsForPerson(this.getUser());
	
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	        for(int numProjs = 0; numProjs < ProjList.length; numProjs++) {	
                Project currentProj = ProjList[numProjs];
	        	

                HTMLOptionElement clonedOption = (HTMLOptionElement) templateOption.cloneNode(true);
                clonedOption.setValue(currentProj.getHandle());
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(currentProj.getProj_name() + ": " +
                                        currentProj.getDescription());
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                ProjSelect.appendChild(clonedOption);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjSelect.insertBefore(clonedOption, templateOption);
	        }
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Proj catalog: " + ex);
            throw new webschedulePresentationException("Error getting Projs for user: ", ex);
	    }

        // Finally remove the template row and template select option
        //  from the page
	
        templateOption.getParentNode().removeChild(templateOption);  	

	*/
	
        //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg || 
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {       
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }

/*        if(null != this.getComms().request.getParameter(DESCRIPTION)) {
            page.getElementDescription().setValue(this.getComms().request.getParameter(DESCRIPTION));
        }*/

	    return page.toDocument();
    }


     public String handleChangeEvent()
        throws HttpPresentationException
    {
    	String event_id = this.getComms().request.getParameter(EVENT_ID);
           	
             System.out.println("event_id at first point =   "+event_id);
            if (null == event_id) {
            	this.getSessionData().setUserMessage(event_id + "  Please choose a valid event ");
                 throw new ClientPageRedirectException(SELECT_DATE_PAGE);
                 // Show error message that project not found
            } else {
            	this.setS_eventID(event_id);
            	  if  (check_user(event_id)==1) this.getSessionData().setUserMessage(event_id + "  You cannot modify or delete another user event ");;
                throw new ClientPageRedirectException(CHANGE_EVENT_PAGE);
           }
      }
      
       public String handleChangeEvent1()
        throws HttpPresentationException
    {
    	
	 System.out.println("Handle Change event 1 visited  ");
	     String s_eventID = this.getS_eventID();	 
		   if  (check_user(s_eventID)==1) return showPage("You can not delete part of another user event");
      //   else if  (check_date(s_eventID)==1) return showPage("You can not delete a past event");
       else if  (check_date_change(s_eventID)==1) return showPage("You can not modify part of a past event");
        else throw new ClientPageRedirectException(CHANGE_PAGE);
	
               
           }
  
        public String handleComplain()
        throws HttpPresentationException
    {
    String s_eventID = this.getS_eventID();
      if  (check_user(s_eventID)==1) return showPage("You can not submit a complaint for another user event");
    		// System.out.println("Handle Compaint has been visited  ");
	else throw new ClientPageRedirectException(COMPLAINTREPORT_PAGE);
		 
               
           }
  
      
      
/*   public String handleChangeEvent()
        throws HttpPresentationException
    {
    	String event_id = this.getComms().request.getParameter(EVENT_ID);
           	
             System.out.println("event_id at first point =   "+event_id);
            if (null == event_id) {
            	this.getSessionData().setUserMessage(event_id + "  Please choose a valid event ");
                 throw new ClientPageRedirectException(SELECT_DATE_PAGE);
                 // Show error message that project not found
            } else {
	    
	    String s_eventID = this.getS_eventID();
        int starthi=0, startmi=0, endhi=0, endmi=0, year=0, month=0, day=0, dow=0;
   	String projID=null, descrip=null;
   	 int check_user_flag = 0;
   	
   	 check_user_flag  = check_user(s_eventID);
   	
   	 System.out.println("Check user information   "+ check_user_flag);
        if  (check_user(s_eventID)==1) return showPage("You can not delete another user event");

        else if  (check_date(s_eventID)==1) return showPage("You can not delete a past event");
    	
    	 else {
             try
		 {
		//C_event theC_event = new C_event();
		//update the project hours
		try {
	    	S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
                starthi = s_event.getStarth();
            	startmi = s_event.getStartm();
            	endhi = s_event.getEndh();
            	endmi = s_event.getEndm();
            	projID = s_event.getProjectID();
		descrip = s_event.getDescription();
		year = s_event.getEventyear();
		month = s_event.getEventmonth();
		day = s_event.getEventday();
		dow = s_event.getEventdayofw();
		
//this.setchange_event(new temp_hold_change(projID, descrip,starthi,startmi, endhi,endmi,day, month, year, dow)); 
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
	        }
//temp_hold_change change_info = new temp_hold_change(projID, descrip,starthi,startmi, endhi,endmi,day,month, year, dow);
	//this.getSessionData().setchange_event(change_info);
	
	//this.setchange_event(change_info); 
	        System.out.println("Trying to save canceled event and subtract project ");
		//update_projhours(s_eventID);
		subtract_projhours(projID,starthi,endhi,startmi,endmi);
		//save this event as canceled
		//saveC_event(s_eventID,theC_event);
		//send an email that a slot has been deleted
		
		 }catch  (Exception ex) {
          	throw new webschedulePresentationException("Error Creating a blank Canceled event", ex);
		     }
            try {
                //delete the event from the table
	        S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
                String description = s_event.getDescription();
                s_event.delete();
            
            // Catch any possible database exception as well as the null pointer
            //  exception if the s_event is not found and is null after findS_eventByID
	    } catch(Exception ex) {
            this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event to change");
        	}
            
                throw new ClientPageRedirectException(CHANGE_PAGE);
          }
      }
}*/

     /*
     * Deletes a Event from the Events database
     */
    public String handleDelete()
        throws HttpPresentationException, webschedulePresentationException
    {
        String s_eventID = this.getS_eventID();
        int starthi=0, startmi=0, endhi=0, endmi=0;
   	String projID=null;
   	 int check_user_flag = 0;
   	
   	 check_user_flag  = check_user(s_eventID);
   	
   	 System.out.println("Check user information   "+ check_user_flag);
        if  (check_user(s_eventID)==1) return showPage("You can not delete another user event");

        else if  (check_date(s_eventID)==1) return showPage("You can not delete a past event");
    	
    	 else {
             try
		 {
		C_event theC_event = new C_event();
		//update the project hours
		try {
	    	S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
                starthi = s_event.getStarth();
            	startmi = s_event.getStartm();
            	endhi = s_event.getEndh();
            	endmi = s_event.getEndm();
            	projID = s_event.getProjectID();
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
	        }
	
	        System.out.println("Trying to save canceled event and subtract project ");
		//update_projhours(s_eventID);
		subtract_projhours(projID,starthi,endhi,startmi,endmi);
		saveR_event(projID,starthi,endhi,startmi,endmi,s_eventID);
	//	subtract_pilot_proj_hours(projID,starthi,endhi,startmi,endmi);
		//save this event as canceled
		saveC_event(s_eventID,theC_event);
		//send an email that a slot has been deleted
		
		 }catch  (Exception ex) {
          	throw new webschedulePresentationException("Error Creating a blank Canceled event", ex);
		     }
            try {
                //delete the event from the table
	        S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
                String description = s_event.getDescription();
                s_event.delete();
            this.getSessionData().setUserMessage("The event, " + description + ", was deleted");
            // Catch any possible database exception as well as the null pointer
            //  exception if the s_event is not found and is null after findS_eventByID
	    } catch(Exception ex) {
            this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event to delete");
        	}
        // Redirect to the catalog page which will display the error message,
        //  if there was one set by the above exception
        	throw new ClientPageRedirectException(SELECT_DATE_PAGE);
             }
    }


     /**
     *  Process Check project event
     */
   public String handleCheck_proj()
	    throws HttpPresentationException
    {
	String proj_id = this.getComms().request.getParameter(PROJ_ID);
//	String password = this.getComms().request.getParameter(PASSWORD_NAME);
        Project project = null;
        String s_eventID = this.getS_eventID();

        try {
            project = ProjectFactory.findProjectByID(proj_id);
            if  (check_user(s_eventID)==1) return showPage("You can not swtich projects for another user event");           	
            else
            {
              if(null == project) {
                  return showPage("Invalid project or password");
                // Show error message that user not found (bad username/password)
              } else {
                  this.setSwitchProject(project);
                  throw new ClientPageRedirectException(PROJ_PASSWD_PAGE);
              }
             }
              } catch(webscheduleBusinessException ex) {
                this.writeDebugMsg("System error finding project: " + ex.getMessage());
              throw new webschedulePresentationException("System error finding project", ex);
              }

    }



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


    public int check_date(String s_eventID)

     {
         int eventday=0;
           int eventmonth=0;
           int eventyear=0;
           int starth=0;
           int startm=0;
           int endh=0;
           int endm = 0;
           int cancelcode=0;
           String projID = null;
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
            projID = s_event.getProjectID();
            user_login = s_event.getOwnerLogin();
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
    	Date cancelinfodate =  cancelinfo.getTime();
    	long cancelinfoms = cancelinfodate.getTime();
    	   	
    	//calculation for the event info
    	Calendar eventinfo = Calendar.getInstance();
	
    	eventinfo.set(eventyear, eventmonth, eventday, starth, startm);
    	Date eventinfodate =   eventinfo.getTime();
    	long eventinfoms = eventinfodate.getTime();
	long difference = eventinfoms - cancelinfoms;
	//long numofhours = difference/3600000;
    	
	if (difference < 100)
	{
	System.out.println("Check date is OK num of hours = " + Long.toString(difference));
		return 1;
		}
	else 	
	 {
	 System.out.println("Check date is OK num of hours = " + Long.toString(difference));
        	return 0;
		}
	}


 public int check_date_change(String s_eventID)

     {
         int eventday=0;
           int eventmonth=0;
           int eventyear=0;
           int starth=0;
           int startm=0;
           int endh=0;
           int endm = 0;
           int cancelcode=0;
           String projID = null;
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
            projID = s_event.getProjectID();
            user_login = s_event.getOwnerLogin();
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
    	Date cancelinfodate =  cancelinfo.getTime();
    	long cancelinfoms = cancelinfodate.getTime();
    	   	
    	//calculation for the event info
    	Calendar eventinfo = Calendar.getInstance();
	//check the cancelation to 30 an hour before end time
	if (endm == 30)
	  {
	   endm=0;
	  } else {
	   endh = endh - 1;
	   endm = 30;
	  }  
    	eventinfo.set(eventyear, eventmonth, eventday, endh, endm);
    	Date eventinfodate =   eventinfo.getTime();
    	long eventinfoms = eventinfodate.getTime();
	long difference = eventinfoms - cancelinfoms;
	long numofhours = difference/3600000;
    	
	if (numofhours < 0)
	{
	System.out.println("Check date is OK ");
		return 1;
		}
	else 	
        	return 0;
	}






/*
      public String handleChangestart()
       throws HttpPresentationException
      {
      String s_eventID = this.getS_eventID();
       if  (check_user(s_eventID)==1) return showPage("You can not delete part of another user event");
        else if  (check_date(s_eventID)==1) return showPage("You can not delete part of a past event");
        else throw new ClientPageRedirectException(CHANGE_START_PAGE);
      }

      public String handleChangeend()
      throws HttpPresentationException
      {
       String s_eventID = this.getS_eventID();
       if  (check_user(s_eventID)==1) return showPage("You can not delete part of another user event");
        else if  (check_date(s_eventID)==1) return showPage("You can not delete part of a past event");
        else throw new ClientPageRedirectException(CHANGE_END_PAGE);
      }

*/

      public String handleChange()
      throws HttpPresentationException
      {
       String s_eventID = this.getS_eventID();
       if  (check_user(s_eventID)==1) return showPage("You can not delete part of another user event");
        else if  (check_date_change(s_eventID)==1) return showPage("You can not modify part of a past event");
        else throw new ClientPageRedirectException(CHANGE_PAGE);
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
	System.out.println("done hours off deleting an event = "+Double.toString(donehours));
    	totalhours = theProject.getTotalhours();
	System.out.println("total hours off deleting an event"+Double.toString(totalhours));
	
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
            throw new webschedulePresentationException("Error subtracting project hours", ex);
        }

    }



/*protected void subtract_pilot_proj_hours(String projID,int starthi,int endhi,int startmi,int endmi)
   throws HttpPresentationException
   {
   	double starttime;
   	double endtime;
   	double donehours;
   	double totalhours;
   	double eventhours;
       	
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
			
    	eventhours = endtime - starttime;

	
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
    		
    	totalhours = pilotProject.getTotalhours()-(eventhours);
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
    	totalhours = theProject.getTotalhours();
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




  
    
    
    
  /*save released event if cancelation happend less than 48 hours in advance*/

  protected void saveR_event(String projID,int starthi,int endhi,int startmi,int endmi, String s_eventID)
   throws HttpPresentationException
   {
   	double starttime;
   	double endtime;
   	double donehours;
   	double totalhours;
   	double eventhours;
   	
   	Project theProject=null;
	R_event theR_event = null;
	 Project Project = null;
   Person Person = null;
   Operator Operator = null;
       	
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
   



// check to see if the cancellation is less than 48 hours charge the pilot hours one hour.

if (difference < 172800000)
	{
	
	
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
   
	
	
	
	System.out.println("Check date is OK num of hours = " + Long.toString(difference));
		
		}
	else 	
	 {
	 
	 //do nothing
	 System.out.println("Check date is OK num of hours = " + Long.toString(difference));
        	
		}
 	
	
}    
  





    /**
     * Method to save a new an event that has been canceled to the database
     */
protected void saveC_event(String s_eventID, C_event theC_event)
          throws HttpPresentationException, webschedulePresentationException
    {
           int eventday=0;
           int eventmonth=0;
           int eventyear=0;
           int starth=0;
           int startm=0;
           int endh=0;
           int endm = 0;
           int cancelcode=0;
           String projID = null;
           String user_login = null;
	     String operator_email = null;
	   String startms = null;
	   String endms = null;
	   
    try {
	    S_event s_event = S_eventFactory.findS_eventByID(s_eventID);
            eventday = s_event.getEventday();
            eventmonth = s_event.getEventmonth();
            eventyear = s_event.getEventyear();
            starth = s_event.getStarth();
            startm = s_event.getStartm();
            endh = s_event.getEndh();
            endm = s_event.getEndm();
            projID = s_event.getProjectID();
            user_login = s_event.getOwnerLogin();
	     operator_email = s_event.getOperatorEmail();
	     
	    if (startm == 30)
	    	startms = "30";
	    else 
	    	startms = "00";
			
         if (endm == 30)
	    	endms = "30";
	    else 
	    	endms = "00";

             // Catch any possible database exception as well as the null pointer
            //  exception if the s_event is not found and is null after findS_eventByID
	    } catch(Exception ex) {
            this.getSessionData().setUserMessage(s_eventID + " Please choose a valid event");
        }
        String eventdatestring = Integer.toString(eventmonth+1)+"/"+Integer.toString(eventday)+"/"+Integer.toString(eventyear);

        Person theUser = this.getUser();
        String user_email = null;
    	
    	try
    	{    	
    	user_email = theUser.getEmail();
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting user's email", ex);
        }

    try {
    	    //send email to confrim delete
    	    SendMail sch_not;	
    	    String from = "emang";
    	    String to = user_email+","+operator_email+",t3tw_notify";
    	    String subject = "A slot has been deleted for the 3TW magnet";
    	    String[] message = {"This message is to confirm that you deleted the following event "+
    	    			"Date:             "+eventdatestring,
    	    			"Starting time is: "+Integer.toString(starth)+":"+Integer.toString(startm),
    	    			"End time is: "+Integer.toString(endh)+":"+Integer.toString(endm),
    	    			" "}  ;
           sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }


        //calculation for the time the event has been canceled
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
    	Date cancelinfodate =  cancelinfo.getTime();
    	long cancelinfoms = cancelinfodate.getTime();
    	
    	
    	//calculation for the event info
    	Calendar eventinfo = Calendar.getInstance();
    	eventinfo.set(eventyear, eventmonth, eventday, starth, startm);
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
		this.getSessionData().setUserMessage(s_eventID + "Deleting event less than 24 hours check policy for charges");	
		cancelcode = 2;
		}
	else if ((numofhours < 168) && (numofhours >= 24))
    	        {
		this.getSessionData().setUserMessage(s_eventID + "Deleting event less than 7 days check policy for charges");	
		cancelcode = 1;
		}
	else
		{
		this.getSessionData().setUserMessage(s_eventID + "Deleting event more than 7 days ahead no charges");	
		cancelcode = 0;
		}
			
		/* try
		 {
		C_event theC_event = new C_event();
		 }catch  (Exception ex) {
          	throw new webschedulePresentationException("Error Creating a blank Canceled event", ex);
		     }*/
	 	
	 	System.out.println("Start hour off cancel module "+starth);
		System.out.println("Start min "+startm);
		System.out.println("End hour off cancel module "+endh);
		System.out.println("End min "+endm);
		System.out.println("Event Day "+eventday);
		System.out.println("Event Month "+eventmonth);
		System.out.println("Event Year "+eventyear);
		System.out.println("today date "+todaydate);
		System.out.println("today Month "+todaymonth);
		System.out.println("Today Year "+todayyear);
		System.out.println("Cancel Code "+cancelcode);
		System.out.println("Project ID "+projID);
		System.out.println("User Login "+user_login);
		
    	try {
    		System.out.println("about to save one ");
    		
    		/*C_event theC_event = new C_event();*/
    		
    		theC_event.setEventday(eventday);
    		theC_event.setEventm(eventmonth);
    		theC_event.setEventy(eventyear);
    		
    		theC_event.setOwner(PersonFactory.findPerson(user_login));
    		theC_event.setProj_owner(ProjectFactory.findProjectByID(projID));
    		
    		theC_event.setStarth(starth);
    		theC_event.setStartm(startm);
    		theC_event.setEndh(endh);
    		theC_event.setEndm(endm);
    		
    		theC_event.setTodayd(todaydate);
    		theC_event.setTodaym(todaymonth);
    		theC_event.setTodayy(todayyear);
    		
    		theC_event.setCancelc(cancelcode);
    		
    		
    		
    		System.out.println("about to save final ");
    		theC_event.save();
    		System.out.println("AN EVENT HAS BEEN SAVED ");
    		
    	 } catch(Exception ex) {
          throw new webschedulePresentationException("Error adding an event", ex);
     }
      }  	

}


