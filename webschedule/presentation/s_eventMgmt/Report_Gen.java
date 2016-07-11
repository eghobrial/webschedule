/**--------------------------------------------------------------
* Webschedule
*
* @class: Report_Gen
* @version
* @author: Eman Ghobrial
* @since: May 2001
*
*--------------------------------------------------------------*/

package webschedule.presentation.s_eventMgmt;

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

/**
 *Report_Gen.java shows the Report_Gen Menu Options
 *
 */
public class Report_Gen extends BasePO
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
    	Report_GenHTML page = new Report_GenHTML();
	// Swap in our real run-time JavaScript to repalce the storyboard JavaScript
	HTMLScriptElement script = new Report_GenScriptHTML().getElementRealScript();
        XMLCUtil.replaceNode(script, page.getElementDummyScript());		
    	

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



	public String handleRecharge()
	 throws HttpPresentationException
		{

		this.setbillreportflag("IFIS");
                  throw new ClientPageRedirectException(BILL_REPORT_PAGE);
              }

	/*public String handleInvoice()
	 throws HttpPresentationException
		{
		System.out.println("flag off Report_Gen is Invoice");
		this.setbillreportflag("INVOICE");
                  throw new ClientPageRedirectException(BILL_REPORT_PAGE);
              }*/

}
