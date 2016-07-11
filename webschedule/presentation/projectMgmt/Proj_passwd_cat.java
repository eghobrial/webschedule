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
import java.util.Calendar;

/**
 * Proj_passwd.java handles the project password verify
 *
 */
public class Proj_passwd_cat extends BasePO
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


     public String handleSelectproject()
        throws HttpPresentationException
	    {
	      //***ChangeEvent***************************************
	
	     String password = this.getComms().request.getParameter(PASSWORD_NAME);
	     Project theProject = this.getProject();
	
	     try {
	
	     if(null == theProject || !theProject.getPassword().equals(password)) {
                return showPage("Invalid password, you can not schedule for this project");
                // Show error message that user not found (bad username/password)
             } else {
	
	    	Calendar rightnow = Calendar.getInstance();
		int currentmonth = rightnow.get(rightnow.MONTH);
		int currentyear = rightnow.get(rightnow.YEAR);
	
	
           	this.setYear(currentyear);
           	this.setMonth(currentmonth);
                //String projectname = project.getProj_name();
                /*this.getSessionData().setUserMessage("This project = " + proj_id+ "jkjk");
                  throw new ClientPageRedirectException(PROJECT_CATALOG_PAGE);*/

                throw new ClientPageRedirectException(SELECT_DATE_PAGE);
	    	
                }
                } catch(webscheduleBusinessException ex) {
               this.writeDebugMsg("System error getting project password: " + ex.getMessage());
            throw new webschedulePresentationException("System error getting project password", ex);
        }
       }

    /**
     *
     */
    public String showPage(String errorMsg)
    {
        Proj_passwd_catHTML page = new Proj_passwd_catHTML();

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


}




