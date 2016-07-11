package webschedule.presentation.proposalMgmt;

import webschedule.business.person.*;
import webschedule.business.proposal.*;
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
public class piprofile extends BasePO 
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
    //private static String ADMIN = "admin";
    //private static String DEVELOPER = "developer";
  //  private static String AUTH = "auth";
     private static String EMAIL = "email";

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
     *  process login data, save fields to PersonManager if correct, 
     *  otherwise output results
     *  save error results, 
     *  control PO flow
     */
    public String handleUpdate() 
	     throws HttpPresentationException 
    {
	  //  String login = this.getComms().request.getParameter(LOGINNAME);
	    String password = this.getComms().request.getParameter(PASSWORD);
	    String firstname = this.getComms().request.getParameter(FIRSTNAME);;
	    String lastname = this.getComms().request.getParameter(LASTNAME);
	    String repassword = this.getComms().request.getParameter(REPASSWORD);
	    String office = this.getComms().request.getParameter(OFFICE);
	    String phone =   this.getComms().request.getParameter(PHONE);
	    String email =   this.getComms().request.getParameter(EMAIL);
    
	    // if login or password field is empty, generate error and redirect to this PO
	    if ( password.length() ==0 || 
	        firstname.length() == 0 || lastname.length() == 0 ||
	        office.length() == 0 || phone.length() == 0 || email.length() == 0) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }        
        
        //Check that password was duplicated correctly
	    if(!repassword.equals(password)) {
            return showPage("Please make sure your password and password confirmation match exactly");    
	    }
	
		    Person user = this.getUser();
	
        try {
            
	            user.setLogin(user.getLogin());
	            user.setPassword(password);
	            user.setFirstname(firstname);
	            user.setLastname(lastname);
	            user.setOffice(office);
	            user.setPhone(phone);
	            user.setEmail(email);
	            user.setAdmin(user.isAdmin());
             	    user.setDeveloper(user.isDeveloper());
		     user.setAuth(user.isAuth());
		     user.setPadmin(user.isPadmin());
            	    //Add the user to the database.
	            user.save();
                this.getSessionData().setUserMessage(
                     "User, " + user.getFirstname() + ", has been updated");
	            throw new ClientPageRedirectException(MAINMENU_PAGE);
	       
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
    
      String password = this.getComms().request.getParameter(PASSWORD);
	    String firstname = this.getComms().request.getParameter(FIRSTNAME);;
	    String lastname = this.getComms().request.getParameter(LASTNAME);
	    String repassword = this.getComms().request.getParameter(REPASSWORD);
	    String office = this.getComms().request.getParameter(OFFICE);
	    String phone =   this.getComms().request.getParameter(PHONE);
	    String email =   this.getComms().request.getParameter(EMAIL);
    
    String  user_email = null;
	String firstnamedb=null;
	String lastnamedb=null;
	String officedb = null;
	String phonedb = null;
	String passworddb = null;
	    // instantiate XMLC object
	    piprofileHTML page = new piprofileHTML();
	    
	    Person thePerson = this.getUser();
	
	try
    	{    	
	firstnamedb = thePerson.getFirstname();
	lastnamedb = thePerson.getLastname();
    	user_email = thePerson.getEmail();
	officedb = thePerson.getOffice();
	phonedb = thePerson.getPhone();
	passworddb = thePerson.getPassword();
	
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting user's info", ex);
        }
	
	
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	    
        
	if(null != firstname && firstname.length() != 0) {
                page.getElementFirstname().setValue(firstname);
            } else {
                page.getElementFirstname().setValue(firstnamedb);
            }
	 
	if(null != lastname && lastname.length() != 0) {
                page.getElementLastname().setValue(lastname);
            } else {
                page.getElementLastname().setValue(lastnamedb);
            }
               
	if(null != office && office.length() != 0) {
                page.getElementOffice().setValue(office);
            } else {
                page.getElementOffice().setValue(officedb);
            }
                     
       if(null != phone && phone.length() != 0) {
                page.getElementPhone().setValue(phone);
            } else {
                page.getElementPhone().setValue(phonedb);
            }
       
         if(null != email && email.length() != 0) {
                page.getElementEmail().setValue(email);
            } else {
                page.getElementEmail().setValue(user_email);
            }

   if(null != password && password.length() != 0) {
                page.getElementPassword().setValue(password);
            } else {
                page.getElementPassword().setValue(passworddb);
            }
 
        return page.toDocument();
    }
}
