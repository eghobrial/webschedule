/**--------------------------------------------------------------
* Webschedule
*
* @class: Register.java
* @version
* @author: Eman Ghobrial
* @since: Nov
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
import webschedule.presentation.webschedulePresentationException;
import webschedule.business.webscheduleBusinessException;

/**
 * Register.java handles the user registration functionality 
 *  of the webschedule app.
 *
 */
public class Register extends BasePO 
{    
    /**
     * Constants
     */
    private static String LOGINNAME = "login";
    private static String PASSWORD = "password";
    private static String REPASSWORD = "repassword";
    private static String FIRSTNAME = "firstname";
    private static String LASTNAME = "lastname";
    private static String OFFICE = "office";
    private static String PHONE = "phone";
    private static String ADMIN = "admin";
    private static String DEVELOPER = "developer";
    private static String AUTH = "auth";
    private static String PADMIN = "padmin";
    private static String EMAIL = "email";

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
     *  process login data, save fields to PersonManager if correct, 
     *  otherwise output results
     *  save error results, 
     *  control PO flow
     */
    public String handleRegister() 
	     throws HttpPresentationException 
    {
	    String login = this.getComms().request.getParameter(LOGINNAME);
	    String password = this.getComms().request.getParameter(PASSWORD);
	    String firstname = this.getComms().request.getParameter(FIRSTNAME);;
	    String lastname = this.getComms().request.getParameter(LASTNAME);
	    String repassword = this.getComms().request.getParameter(REPASSWORD);
	    String office = this.getComms().request.getParameter(OFFICE);
	    String phone =   this.getComms().request.getParameter(PHONE);
	    String email =   this.getComms().request.getParameter(EMAIL);
    
	    // if login or password field is empty, generate error and redirect to this PO
	    if (login.length() == 0 || password.length() ==0 || 
	        firstname.length() == 0 || lastname.length() == 0 ||
	        office.length() == 0 || phone.length() == 0 || email.length() == 0) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }        
        
        //Check that password was duplicated correctly
	    if(!repassword.equals(password)) {
            return showPage("Please make sure your password and password confirmation match exactly");    
	    }
	
        try {
            // Now check that the login name is not taken.
	        if(null == PersonFactory.findPerson(login)) {
	            Person user = new Person();
	            user.setLogin(login);
	            user.setPassword(password);
	            user.setFirstname(firstname);
	            user.setLastname(lastname);
	            user.setOffice(office);
	            user.setPhone(phone);
	            user.setEmail(email);
	
	            if(null != this.getComms().request.getParameter(ADMIN)) {
                	user.setAdmin(true);
            	    } else {
                	user.setAdmin(false);
            	     }
            	      if(null != this.getComms().request.getParameter(DEVELOPER)) {
                	user.setDeveloper(true);
            	    } else {
                	user.setDeveloper(false);
            	     }
	            if(null != this.getComms().request.getParameter(AUTH)) {
                	user.setAuth(true);
            	    } else {
                	user.setAuth(false);
            	     }
		    
		     if(null != this.getComms().request.getParameter(PADMIN)) {
                	user.setPadmin(true);
            	    } else {
                	user.setPadmin(false);
            	     }
		     
	            //Add the user to the database.
	            user.save();
                this.getSessionData().setUserMessage(
                     "User, " + user.getFirstname() + ", has been added");
	            throw new ClientPageRedirectException(REGISTER_PAGE);
	        } else {
                // Login name already taken
                // Redirect to this same PO with informative error message
                return showPage("The login name " + login + " is already taken. Please try another.");    
	        }
        } catch(webscheduleBusinessException ex) {
            throw new webschedulePresentationException("Exception logging in user: ", ex);
        }
    }
    /** 
     *  produce HTML for this PO
     */
    public String showPage(String errorMsg)
        throws HttpPresentationException 
    {
	    // instantiate XMLC object
	    RegisterHTML page = new RegisterHTML();
	
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	    
        if(null != this.getComms().request.getParameter(LOGINNAME)) {
            page.getElementLogin().setValue(this.getComms().request.getParameter(LOGINNAME));
        }
        if(null != this.getComms().request.getParameter(FIRSTNAME)) {
            page.getElementFirstname().setValue(this.getComms().request.getParameter(FIRSTNAME));
        }
        if(null != this.getComms().request.getParameter(LASTNAME)) {
            page.getElementLastname().setValue(this.getComms().request.getParameter(LASTNAME));
        }

         if(null != this.getComms().request.getParameter(OFFICE)) {
            page.getElementOffice().setValue(this.getComms().request.getParameter(OFFICE));
        }
         if(null != this.getComms().request.getParameter(PHONE)) {
            page.getElementPhone().setValue(this.getComms().request.getParameter(PHONE));
        }

         if(null != this.getComms().request.getParameter(EMAIL)) {
            page.getElementPhone().setValue(this.getComms().request.getParameter(EMAIL));
        }
	
         if(null != this.getComms().request.getParameter(ADMIN)) {
            page.getElementAdminBox().setChecked(true);
        } else {
            page.getElementAdminBox().setChecked(false);
        }
        
	  if(null != this.getComms().request.getParameter(DEVELOPER)) {
            page.getElementDeveloperBox().setChecked(true);
        } else {
            page.getElementDeveloperBox().setChecked(false);
        }
	if(null != this.getComms().request.getParameter(AUTH)) {
            page.getElementDeveloperBox().setChecked(true);
        } else {
            page.getElementDeveloperBox().setChecked(false);
        }
        
	 if(null != this.getComms().request.getParameter(PADMIN)) {
            page.getElementPadminBox().setChecked(true);
        } else {
            page.getElementPadminBox().setChecked(false);
        }
	return page.toDocument();
    }
}
