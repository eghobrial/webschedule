/**--------------------------------------------------------------
* Webschedule
*
* @class:information
* @version
* @author: Eman Ghobrial
* @since: Dec 2007
*
*--------------------------------------------------------------*/

package webschedule.presentation.proposalMgmt;

import webschedule.business.proposal.*;
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
 * manageproposal.java shows the Proposal Menu Options
 *
 */
public class information extends BasePO
{
 
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
        informationHTML page = new informationHTML();
	int today ,month ,year;
	String	aproj_name ;
	int	status ;
	String cdate;
String statuscode[] = {"Editable","Submitted","Internal Comments","Committe Comments","PI Responded","Approved","Checked","Accounts Added"};
       //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
    /*   	 try {
	        page.setTextOwner(this.getUser().getFirstname() + " " +
	    		              this.getUser().getLastname() );
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error getting user info: " + ex);
	    }
	    */
        
      //  String errorMsg = this.getSessionData().getAndClearUserMessage();
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	
	  
    
    
	    // write out HTML
	    return page.toDocument();
    }
    
    
}


