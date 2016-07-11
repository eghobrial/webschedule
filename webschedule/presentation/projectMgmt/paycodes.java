/**--------------------------------------------------------------
* Webschedule
*
* @class: paycodes
* @version
* @author: Eman Ghobrial
* @since: May 2009
*
*--------------------------------------------------------------*/
package webschedule.presentation.projectMgmt;


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
 * selectdate_help.java handles the help page display
 *
 */
public class paycodes extends BasePO
{

	
    /**
     * Superclass method override
     */
    public boolean loggedInUserRequired()
    {
        return false;
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
    {
        paycodesHTML page = new paycodesHTML();



	    return page.toDocument();
    }
}


