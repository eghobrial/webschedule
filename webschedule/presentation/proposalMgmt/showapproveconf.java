/**--------------------------------------------------------------
* Webschedule
*
* @class:Showapproveconf
* @version
* @author: Eman Ghobrial
* @since: Feb 2009
* Added a page to confirm approval
*--------------------------------------------------------------*/

package webschedule.presentation.proposalMgmt;

import webschedule.business.proposal.*;
import webschedule.business.person.*;
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

import java.util.*;
import webschedule.SendMail;
import java.lang.String;



/**
 * manageproposal.java shows the Proposal Menu Options
 *
 */
public class showapproveconf extends BasePO
{

  private static String PROJ_ID = "proj_id"; 
 
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

 public String handleApprove()
        throws HttpPresentationException, webschedulePresentationException
    {
    	String proj_id = this.getComms().request.getParameter(PROJ_ID);
	System.out.println("Handle show approve conf visited  ");
	System.out.println("problem id  =   "+proj_id);
	
	try
	{
            if (null == proj_id) {
            this.getSessionData().setUserMessage(proj_id + "  Please choose a valid proposal ");
                 throw new ClientPageRedirectException(APROPOSALLOG_PAGE);
                 // Show error message that project not found
	} else{
		 
	  this.setProposalID(proj_id);
         return showPage(null);
	 //   throw new ClientPageRedirectException(APPROVEPROPOSAL_PAGE);
	     	}   
	    } catch (Exception ex) {
         	throw new webschedulePresentationException("Error getting user login name", ex);
          }	
      }

     

    /**
     *
     */
    public String showPage(String errorMsg)
     throws HttpPresentationException, webschedulePresentationException
    {
        showapproveconfHTML page = new showapproveconfHTML();


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


