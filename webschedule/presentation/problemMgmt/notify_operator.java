/**--------------------------------------------------------------
* Webschedule
*
* @class:notify_operator
* @version
* @author: Eman Ghobrial
* @since: June 2006
*
*--------------------------------------------------------------*/

package webschedule.presentation.problemMgmt;

import webschedule.SendMail;
import webschedule.business.problem.*;
import webschedule.business.person.*;
import webschedule.business.s_event.*;
import webschedule.business.project.*;
import webschedule.business.operator.*;
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
import java.util.Date;
import java.util.LinkedList;

/**
 * notify.java shows the notify page
 *
 */
public class notify_operator extends BasePO
{

 private static String ERROR_NAME = "ERROR_NAME";
 
 
     private static String REASON = "reason";
    
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
    
      public String handleNotify_operator()
        throws HttpPresentationException
	    {
	    
	    Operator[] operator_list;
	    LinkedList		users_emails = new LinkedList();
	    
	
    	String reason = this.getComms().request.getParameter(REASON);
	
	try 
	{
	 if (!(this.getUser().isAuth())){
	    this.getSessionData().setUserMessage("User not authorized to notify other PIs ");
            throw new ClientPageRedirectException(PROBLEMMENU_PAGE);
	            }
		    
	 } catch (webscheduleBusinessException ex) {
         	throw new webschedulePresentationException("Error getting user is auth attribute", ex);
          }
/*	
	  if (reason.length() == 0 ){
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }   */
    	    

	try {
	   operator_list = OperatorFactory.getOperatorsList();
       } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding EVENTs: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding EVENTs", ex);
        }
	
	
	    for (int num = 0; num<operator_list.length; num++)
	      {
	       try {
	    	
		String OperatorEmail = operator_list[num].getEmail();
		
		if (!(operator_list[num].isExp()))
			{				
		Object OwnerEmailo = (Object) OperatorEmail;
		 users_emails.add(OwnerEmailo);
		  }
	    	} catch(Exception ex) {
	          throw new webschedulePresentationException("Error getting operator info.", ex);
    		 }
	}
	
	send_operator(users_emails);
	//return showPage("Thank you");   	
	throw new ClientPageRedirectException(PROBLEMMENU_PAGE);
}	

 protected void send_operator(LinkedList users_emails)
    throws  HttpPresentationException
 {
 String reason = this.getComms().request.getParameter(REASON);
 String subject=null;
 
 String[] message=null;
 String to = " ";
 
 
  for (int j = 0; j < users_emails.size(); j++)
    	      {
    	      Object line = users_emails.get(j);
    	     System.out.println("Line String "+((String) line));
    	      to = to+","+(String) line;
    	      //System.out.println("Message array "+ message);
    	    }
 
 
    try {
    	    SendMail sch_not;	
	    subject = "Message to all scanner operator @ cfMRI 3TW";
    	    String from = "websch_admin";
    	    String[] message1 = {"*",
	  	    "Message:       "+reason,
    	    			" "}  ;
				
	message = message1;			
	       	 		
           sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }
 
 }
 

    /**
     *
     */
    public String showPage(String errorMsg)
    {
        notify_operatorHTML page = new notify_operatorHTML();
	
	    
	    return page.toDocument();
    }
}


