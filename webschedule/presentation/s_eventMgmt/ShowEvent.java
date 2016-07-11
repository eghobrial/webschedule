/**--------------------------------------------------------------
* Webschedule
*
* @class: ShowEvent.java
* ShowEvent Information on the General test version
* @version
* @author: Eman Ghobrial
* @since: August 2007
* @update: 
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
public class ShowEvent extends BasePO
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


  public String handleShowEvent()
        throws HttpPresentationException
    {
    	String event_id = this.getComms().request.getParameter(EVENT_ID);
           	
             System.out.println("event_id at first point =   "+event_id);
            if (null == event_id) {
            	this.getSessionData().setUserMessage(event_id + "  Please choose a valid event ");
                 throw new ClientPageRedirectException(MAINMENU_PAGE);
                 // Show error message that project not found
            } else {
            	this.setS_eventID(event_id);
           
                throw new ClientPageRedirectException(SHOW_EVENT_PAGE);
           }
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
    	
        ShowEventHTML page = new ShowEventHTML();
       // HTMLScriptElement script = new ChangeEventScriptHTML().getElementRealScript();
       // XMLCUtil.replaceNode(script, page.getElementDummyScript());		


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
	        		
				
	        	        	
	        	//String endtime =   Integer.toString(endhi)+":"+Integer.toString(endmi)+" "+ampm;
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
	
}


