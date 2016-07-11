/**--------------------------------------------------------------
* Webschedule
*
* @class: New Event Admin Functions
* @version
* @author: Eman Ghobrial
* @since: June 2009
*
*--------------------------------------------------------------*/

package webschedule.presentation.s_eventMgmt;

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
 * Admin.java shows the Admin Menu Options
 *
 */
public class Event_Admin extends BasePO
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
    {
        Event_AdminHTML page = new Event_AdminHTML();

        //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
   /*     if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }*/

	    return page.toDocument();
    }
}


