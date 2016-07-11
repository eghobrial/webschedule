/**--------------------------------------------------------------
* Webschedule
*
* @class: padminlogin
* @version
* @author: Eman Ghobrial
* @since: March 2008
*
*--------------------------------------------------------------*/

package webschedule.presentation.personMgmt;

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
 * Login.java handles the login functionality of the webschedule app.
 *
 */
public class padminlogin extends BasePO
{
    /**
     * Constants
     */
    private static String SUBMIT_NAME = "submit";
    private static String LOGIN_NAME = "login";
    private static String PASSWORD_NAME = "password";
    private static String ERROR_NAME = "ERROR_NAME";
	
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

    /**
     *  Process login data
     */
    public String handleLogin()
	    throws HttpPresentationException
    {
	    String login = this.getComms().request.getParameter(LOGIN_NAME);
	    String password = this.getComms().request.getParameter(PASSWORD_NAME);
        Person user = null;
	System.out.println(" **** Handle proposal admin page has been visited off padminlogin ****");

        try {
         user = PersonFactory.findPerson(login);
         if (user.isPadmin())
         {
            if(null == user || !user.getPassword().equals(password)) {
                return showPage("Invalid username or password");
                // Show error message that user not found (bad username/password)
            //} else if !user.isAdmin() {
             //    return showPage("Must be admin to access this page");
            } else {
                this.setUser(user);
                throw new ClientPageRedirectException(PROPOSALADMINMENU_PAGE);
            }
         } else {
            return showPage("Must be admin to access this page");
         }

        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding user: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding user", ex);
        }
    }

    /**
     *
     */
    public String handleLogout()
        throws HttpPresentationException
    {
        this.removeUserFromSession();

        return new ExitHTML().toDocument();
    }



    /**
     *
     */
    public String showPage(String errorMsg)
    {
        padminloginHTML page = new padminloginHTML();

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


