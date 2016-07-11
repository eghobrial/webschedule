package webschedule.presentation.personMgmt;

import webschedule.business.operator.*;
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

import java.sql.Timestamp;

/**
 * Register.java handles the user registration functionality 
 *  of the webschedule app.
 *
 */
public class Addoperator extends BasePO 
{    
    /**
     * Constants
     */
   
    private static String FIRSTNAME = "firstname";
    private static String LASTNAME = "lastname";
    private static String EMAIL = "email";
    private static String CERTDAY ="certday";
    private static String CERTMONTH ="certmonth";
    private static String CERTYEAR ="certyear";
     private static String RECERTDAY ="recertday";
    private static String RECERTMONTH ="recertmonth";
    private static String RECERTYEAR ="recertyear";

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
    public String handleAddoperator() 
	     throws HttpPresentationException 
    {
	    String firstname = this.getComms().request.getParameter(FIRSTNAME);;
	    String lastname = this.getComms().request.getParameter(LASTNAME);
	    String certday =   this.getComms().request.getParameter(CERTDAY);
	     String certmonth =   this.getComms().request.getParameter(CERTMONTH);
	      String certyear =   this.getComms().request.getParameter(CERTYEAR);
	    String email =   this.getComms().request.getParameter(EMAIL);
    
	    // if login or password field is empty, generate error and redirect to this PO
	    if (firstname.length() == 0 || lastname.length() == 0 ||
	        certday.length() == 0 || certmonth.length() == 0 || certyear.length() == 0|| email.length() == 0) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }        
        
      	
        try {
            // Now check that the login name is not taken.
	        if(null == OperatorFactory.findOperator(lastname)) {
	            Operator user = new Operator();
	            user.setFirstname(firstname);
	            user.setLastname(lastname);
	            user.setEmail(email);
		    user.setCertday(Integer.parseInt(certday));
	            user.setCertmonth(Integer.parseInt(certmonth));
		     user.setCertyear(Integer.parseInt(certyear));
		     
		      user.setrecertday(Integer.parseInt(certday));
	            user.setrecertmonth(Integer.parseInt(certmonth));
		     user.setrecertyear(Integer.parseInt(certyear));
		     
		      user.setlastscanday(Integer.parseInt(certday));
	            user.setlastscanmonth(Integer.parseInt(certmonth));
		     user.setlastscanyear(Integer.parseInt(certyear));
		     user.setSFTTS(Timestamp.valueOf("2005-11-5 00:00:00.00"));
		     
	                      
	            //Add the user to the database.
	            user.save();
               // this.getSessionData().setUserMessage(
                 //    "You are registered, " + user.getFirstname() + ", please log in");
	           throw new ClientPageRedirectException(ADDOPERATOR_PAGE);
	        } else {
                // Login name already taken
                // Redirect to this same PO with informative error message
                return showPage("The operator last name " + lastname + " is already in record. Please try another.");    
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
	    AddoperatorHTML page = new AddoperatorHTML();
	
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	    
        
        if(null != this.getComms().request.getParameter(FIRSTNAME)) {
            page.getElementFirstname().setValue(this.getComms().request.getParameter(FIRSTNAME));
        }
        if(null != this.getComms().request.getParameter(LASTNAME)) {
            page.getElementLastname().setValue(this.getComms().request.getParameter(LASTNAME));
        }

              

         if(null != this.getComms().request.getParameter(EMAIL)) {
            page.getElementEmail().setValue(this.getComms().request.getParameter(EMAIL));
        }
        
	 if(null != this.getComms().request.getParameter(CERTDAY)) {
            page.getElementCertday().setValue(this.getComms().request.getParameter(CERTDAY));
        }
        
	 if(null != this.getComms().request.getParameter(CERTMONTH)) {
            page.getElementCertmonth().setValue(this.getComms().request.getParameter(CERTMONTH));
        }
	
	 if(null != this.getComms().request.getParameter(CERTYEAR)) {
            page.getElementCertyear().setValue(this.getComms().request.getParameter(CERTYEAR));
        }
        return page.toDocument();
    }
}
