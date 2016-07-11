/**--------------------------------------------------------------
* Webschedule
*
* @class:notify
* @version
* @author: Eman Ghobrial
* @since: June 2004
*
*--------------------------------------------------------------*/

package webschedule.presentation.problemMgmt;

import webschedule.SendMail;
import webschedule.business.problem.*;
import webschedule.business.person.*;
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
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

/**
 * notify.java shows the notify page
 *
 */
public class notify extends BasePO
{

 private static String ERROR_NAME = "ERROR_NAME";
 
  private static String STARTH = "starth";
    private static String  STARTM = "startm";
    private static String ENDH = "endh";
    private static String  ENDM = "endm";
	private static String SDATE ="sdate";
    private static String EDATE ="edate";
        private static String MONTH = "month";
    private static String YEAR = "year";
     private static String REASON = "reason";
     private static String SYS_DOWN = "sys_down";
 private static String SYS_UPDATE = "sys_update";
  private static String SYS_UP = "sys_up";

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
    
      public String handleNotify()
        throws HttpPresentationException
	    {
	    
	    S_event[] s_event_list;
	    LinkedList		users_emails = new LinkedList();
	    
	String starth = this.getComms().request.getParameter(STARTH);
    	String startm = this.getComms().request.getParameter(STARTM);
    	String endh = this.getComms().request.getParameter(ENDH);
    	String endm = this.getComms().request.getParameter(ENDM);
	String sdate = this.getComms().request.getParameter(SDATE);
	String edate = this.getComms().request.getParameter(EDATE);
        String month = this.getComms().request.getParameter(MONTH);
    	String year = this.getComms().request.getParameter(YEAR);
    	String reason = this.getComms().request.getParameter(REASON);
	String sys_down = this.getComms().request.getParameter(SYS_DOWN);
	String sys_update = this.getComms().request.getParameter(SYS_UPDATE);
	String sys_up = this.getComms().request.getParameter(SYS_UP);
	
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
	   s_event_list =S_eventFactory.findS_eventsPeriodForAll(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(sdate),Integer.parseInt(edate));
       } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding EVENTs: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding EVENTs", ex);
        }
	
	
	    for (int numEvents = 0; numEvents<s_event_list.length; numEvents++)
	      {
	       try {
	    	int starthi = s_event_list[numEvents].getStarth();
	    	System.out.println("Start time "+Integer.toString(starthi));
            	int startmi = s_event_list[numEvents].getStartm();
            	int endhi = s_event_list[numEvents].getEndh();
            	int endmi = s_event_list[numEvents].getEndm();
		int eventday = s_event_list[numEvents].getEventday();
		String OwnerFirstName = s_event_list[numEvents].getOwnerFirstname();
		String OwnerLastName = s_event_list[numEvents].getOwnerLastname();
		String OwnerEmail = s_event_list[numEvents].getOwnerEmail();
		
		//if the date is equal the starting date check to see if the event is after the start time
		if (eventday == Integer.parseInt(sdate))
		  {
		  if (starthi >= Integer.parseInt(starth))
		    {
		    Object OwnerEmailo = (Object) OwnerEmail;
		    if (users_emails.indexOf(OwnerEmailo) == -1)
		    {
		      users_emails.add(OwnerEmailo);
		  send_user_notify(OwnerEmail,starth, startm, endh, endm, sdate, edate, month, year, reason);
		  System.out.println("OwnerEmail off the notify module"+OwnerEmail);  
			}
		  //return showPage("Thank you");    	
		 //throw new ClientPageRedirectException(PROBLEMMENU_PAGE);
		    }
		  }
		//if the date is equal the ending date check to see if the event is before the end time
		else if (eventday == Integer.parseInt(edate))
		  {
		 if (endhi < Integer.parseInt(endh))
		   {
		   Object OwnerEmailo = (Object) OwnerEmail;
		    if (users_emails.indexOf(OwnerEmailo) == -1)
		    {
		      users_emails.add(OwnerEmailo);
		  send_user_notify(OwnerEmail, starth, startm, endh, endm, sdate, edate, month, year, reason);
		  System.out.println("OwnerEmail off the notify module"+OwnerEmail); 
			}		  
		  //throw new ClientPageRedirectException(PROBLEMMENU_PAGE);
		  //return showPage("Thank you");   
		    }
		  }

		else 
		//if (((Integer.parseInt(sdate)) < eventday) && (eventday < (Integer.parseInt(edate)))
		 {
		 Object OwnerEmailo = (Object) OwnerEmail;
		    if (users_emails.indexOf(OwnerEmailo) == -1)
		    {
		      users_emails.add(OwnerEmailo);
		     send_user_notify(OwnerEmail , starth, startm, endh, endm, sdate, edate, month, year, reason);
		     System.out.println("OwnerEmail off the notify module"+OwnerEmail); 
		  }
		// throw new ClientPageRedirectException(PROBLEMMENU_PAGE);
		// return showPage("Thank you");   
		 }
		 // Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
	          throw new webschedulePresentationException("Error getting event info.", ex);
    		 }
	}
	
	
	//return showPage("Thank you");   	
	throw new ClientPageRedirectException(PROBLEMMENU_PAGE);
}	

 protected void send_user_notify(String OwnerEmail,String starth,String startm,String endh,String endm,String sdate,String edate,String month,String year, String reason )
    throws  HttpPresentationException
 {
 String sys_down = this.getComms().request.getParameter(SYS_DOWN);
 String subject=null;
 
 String[] message=null;
 
 
    try {
    	    SendMail sch_not;	
    	    String from = "websch_admin";
    	    String to = OwnerEmail;
	   
	    
	     if(null != this.getComms().request.getParameter(SYS_DOWN)) {
    	    subject = "3TE notification ** System Down **";
    	 String[]  message1 = {"This is an update on scanning problems that have been reported that may",
	   "affect your scheduled time. Please check the system status online for more information at:",
	    "		http://fmriwebapp.ucsd.edu:7000",
	    "This warning has been sent to all PI's with scheduled time between:",
    	    "Between:      "+starth+":"+startm+" on "+month+"-"+sdate+"-"+year,
    	    			"Ending:       "+endh+":"+endm+" on "+month+"-"+edate+"-"+year,
    	    			"Message:       "+reason,
    	    			" "}  ;
		message = message1;
		} else  if(null != this.getComms().request.getParameter(SYS_UP))  {
		
		subject = "3TE notification ** System Back Up **";
   String[] message2 = {"This is to notify you that the previously reported problem has been corrected.",
   "Please check the system status online for more information:",
	    "		http://fmriwebapp.ucsd.edu:7000",
	    "This message has been sent to all PI's with scheduled time between:",
    	    "Between:      "+starth+":"+startm+" on "+month+"-"+sdate+"-"+year,
    	    			"Ending:       "+endh+":"+endm+" on "+month+"-"+edate+"-"+year,
    	    			"Message:       "+reason,
	      			" "}  ;
		message = message2;		
		} else  if(null != this.getComms().request.getParameter(SYS_UPDATE))  {
		
		subject = "3TE notification ** System Update **";
   String[] message3 = {"This is an update or general message on system status.",
   "Please check the system status online for more information:",
	    "		http://fmriwebapp.ucsd.edu:7000",
	    "This message has been sent to all PI's with scheduled time between:",
    	    "Between:      "+starth+":"+startm+" on "+month+"-"+sdate+"-"+year,
    	    			"Ending:       "+endh+":"+endm+" on "+month+"-"+edate+"-"+year,
    	    			"Message:       "+reason,
	      			" "}  ;
		message = message3;		
		}		
    	 		
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
        notifyHTML page = new notifyHTML();
	
	//calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
	String updatem = Integer.toString(todaymonth);
 	String updated = Integer.toString(todaydate);
	String updatey = Integer.toString(todayyear); 
	
	HTMLSelectElement	sdate_select,edate_select,month_select,year_select;
HTMLCollection		sdate_selectCollection,edate_selectCollection,month_selectCollection,year_selectCollection;
HTMLOptionElement	sdate_option, edate_option, month_option,year_option;
	String		sdate_optionName,edate_optionName,month_optionName, year_optionName;
	
	
	sdate_select = (HTMLSelectElement)page.getElementSdate();
	sdate_selectCollection = sdate_select.getOptions();
	
	edate_select = (HTMLSelectElement)page.getElementEdate();
	edate_selectCollection = edate_select.getOptions();
	
	month_select = (HTMLSelectElement)page.getElementMonth();
	month_selectCollection = month_select.getOptions();
	
	year_select = (HTMLSelectElement)page.getElementYear();
	year_selectCollection = year_select.getOptions();	

       //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }
	
	
	
	int sdate_optionlen = sdate_selectCollection.getLength();
	for (int i=0; i< sdate_optionlen; i++) {
	  sdate_option = (HTMLOptionElement)sdate_selectCollection.item(i);
	  sdate_optionName = sdate_option.getValue();
	  if (sdate_optionName.equals(updated))
	     sdate_option.setSelected(true);
	  else
	     sdate_option.setSelected(false);
	    }    
	
			
int edate_optionlen = edate_selectCollection.getLength();
	for (int i=0; i< edate_optionlen; i++) {
	  edate_option = (HTMLOptionElement)edate_selectCollection.item(i);
	  edate_optionName = edate_option.getValue();
	  if (edate_optionName.equals(updated))
	     edate_option.setSelected(true);
	  else
	     edate_option.setSelected(false);
	    }  
	    
	        
	    
	int month_optionlen = month_selectCollection.getLength();
	for (int i=0; i< month_optionlen; i++) {
	  month_option = (HTMLOptionElement)month_selectCollection.item(i);
	  month_optionName = month_option.getValue();
	  if (month_optionName.equals(updatem))
	     month_option.setSelected(true);
	  else
	     month_option.setSelected(false);
	    }  
	    
	    
	      int year_optionlen = year_selectCollection.getLength();
	for (int i=0; i< year_optionlen; i++) {
	  year_option = (HTMLOptionElement)year_selectCollection.item(i);
	  year_optionName = year_option.getValue();
	  if (year_optionName.equals(updatey))
	     year_option.setSelected(true);
	  else
	     year_option.setSelected(false);
	    }  
	      
	    
	    return page.toDocument();
    }
}


