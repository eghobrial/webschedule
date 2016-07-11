/**--------------------------------------------------------------
* Webschedule
*
* @class:complaintreport
* @version
* @author: Eman Ghobrial
* @since: Sept 2006
*
*--------------------------------------------------------------*/

package webschedule.presentation.complaintMgmt;


import webschedule.SendMail;
import webschedule.business.person.*;
import webschedule.business.operator.*;
import webschedule.business.s_event.*;
import webschedule.business.complaint.*;
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
import java.sql.Timestamp;

/**
 * status.java shows the status page
 *
 */
public class complaintreport extends BasePO
{
   private static String REPORTERNAME = "reportername";
   private static String REPORTEREMAIL = "reporteremail";
   private static String TIME_PROB = "time_prob";
   private static String MESS_PROB = "mess_prob";
   private static String CABLES_PROB = "cables_prob";
   private static String OTHER_PROB = "other_prob";
   private static String SPECIFY = "specify";
   

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
    
    /* 
    * handle adding a new complaint
    */
    
    public String handleReport()
      throws HttpPresentationException, webschedulePresentationException
    {       
    
    String reportername = this.getComms().request.getParameter(REPORTERNAME);
	String reporteremail =this.getComms().request.getParameter(REPORTEREMAIL);
	String specify = this.getComms().request.getParameter(SPECIFY);
	String time_prob = this.getComms().request.getParameter(TIME_PROB);
	String mess_prob = this.getComms().request.getParameter(MESS_PROB);
	String cables_prob = this.getComms().request.getParameter(CABLES_PROB);
 	String other_prob = this.getComms().request.getParameter(OTHER_PROB);
	
	  if (reportername.length() == 0 || reporteremail.length() ==0 || 
	        specify.length() == 0  ) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }      
	    try {
	        complaint mycomplaint = new complaint();
            saveComplaint(mycomplaint);
            throw new ClientPageRedirectException(CHANGE_PAGE);
	    } catch(webscheduleBusinessException ex) {
            throw new webschedulePresentationException("Exception logging in user: ", ex);
        }
    }
   
  
 /**
 save a complaint function
 */
 
 public String saveComplaint(complaint mycomplaint)
    throws HttpPresentationException
    {
 
 	String reportername = this.getComms().request.getParameter(REPORTERNAME);
	String reporteremail =this.getComms().request.getParameter(REPORTEREMAIL);
	String specify = this.getComms().request.getParameter(SPECIFY);
	String time_prob = this.getComms().request.getParameter(TIME_PROB);
	String mess_prob = this.getComms().request.getParameter(MESS_PROB);
	String cables_prob = this.getComms().request.getParameter(CABLES_PROB);
 	String other_prob = this.getComms().request.getParameter(OTHER_PROB);
	   String s_eventID = this.getS_eventID();
	   String prev_eventID = null;
	   Person prev_person = null;
	   Operator prev_operator = null;
	   String prev_personID = null;
	   String prev_operatorID = null;
	   
//calculation for the time right now
    	Timestamp ts = new Timestamp(System.currentTimeMillis());
	int year = 0;
	int month = 0;
	int day = 0;
	S_event[] EventList = null;
	int counter = 0;
	String personLN = null;
	String personFN = null;
	String operatorLN = null;
	String operatorFN = null;
	try 
        {    	
    	S_event theEvent = S_eventFactory.findS_eventByID(s_eventID);
	year = theEvent.getEventyear();
	month = theEvent.getEventmonth();
	day = theEvent.getEventday();
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting event information", ex);
        }
	


	try {
            EventList = S_eventFactory.findS_eventsForDate(year,month,day);
	
       for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	// begin for loop for events
               S_event currentS_event = EventList[numEvents];
	       String currentID = currentS_event.getHandle();
	       if (currentID.equals(s_eventID))
	         {
	           int index = numEvents;
		   if (index == 0)
		   // this the first event for that day, look for last one on a prev day
		   {
		/*   if (day == 1)
		    {
		     if (month == 0)
		       {
		    EventList = S_eventFactory.findS_eventsForDate(year-1,11,day);
		      } else 
		      EventList = S_eventFactory.findS_eventsForDate(year,month-1,day);
		    } else {
		    }*/
		    prev_person = PersonFactory.findPerson("unknown");
		    prev_operator = OperatorFactory.findOperator("unknown");
		    personLN = "unknown";
	            personFN = "unknown";
		 operatorLN = "unknown";
		operatorFN = "unknown";
		   }
		   else {
		   S_event prevS_event = EventList[numEvents-1];
		   prev_eventID = prevS_event.getHandle();
		   prev_personID = prevS_event.getUserID();
		   prev_operatorID= prevS_event.getOperatorID();
		   prev_person = PersonFactory.findPersonByID(prev_personID);
		   personLN = prev_person.getLastname();
		   personFN = prev_person.getFirstname();
		   prev_operator = OperatorFactory.findOperatorByID(prev_operatorID);
		   operatorLN = prev_operator.getLastName();
		   operatorFN = prev_operator.getFirstName();
		   }	
		 }
	}	 
	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting prev event information", ex);
        }
	


	  try {
	           mycomplaint.setOwner(this.getUser());
                    mycomplaint.setReportername(reportername);
		    mycomplaint.setReporteremail(reporteremail);
	            mycomplaint.setSpecify(specify);
	            mycomplaint.setPostday(ts);
                   
		    if(null != this.getComms().request.getParameter(TIME_PROB)) {
                	mycomplaint.setTime_prob(true);
            	    } else {
                	mycomplaint.setTime_prob(false);
            	     }
		
		 if(null != this.getComms().request.getParameter(CABLES_PROB)) {
                	mycomplaint.setCables_prob(true);
            	    } else {
                	mycomplaint.setCables_prob(false);
            	     }
		
		 if(null != this.getComms().request.getParameter(MESS_PROB)) {
                	mycomplaint.setMess_prob(true);
            	    } else {
                	mycomplaint.setMess_prob(false);
            	     }
 if(null != this.getComms().request.getParameter(OTHER_PROB)) {
                	mycomplaint.setOther_prob(true);
            	    } else {
                	mycomplaint.setOther_prob(false);
            	     }
			
			mycomplaint.setFault_pi(prev_person);
			mycomplaint.setFault_op(prev_operator);	
		
	            //Add the problem to the database.
	            mycomplaint.save();
		    send_notification();
		     send_notification_staff(personLN,personFN,operatorLN,operatorFN);
                this.getSessionData().setUserMessage(
                     "The complaint has been updated");
	            throw new ClientPageRedirectException(CHANGE_EVENT_PAGE);
	       
        } catch(webscheduleBusinessException ex) {
            throw new webschedulePresentationException("Exception logging in user: ", ex);
        }
	
}	
	
	
protected void  send_notification()
   throws HttpPresentationException
   {
String reportername = this.getComms().request.getParameter(REPORTERNAME);
	String reporteremail =this.getComms().request.getParameter(REPORTEREMAIL);
	String specify = this.getComms().request.getParameter(SPECIFY);
	String time_prob = this.getComms().request.getParameter(TIME_PROB);
	String mess_prob = this.getComms().request.getParameter(MESS_PROB);
	String cables_prob = this.getComms().request.getParameter(CABLES_PROB);
 	String other_prob = this.getComms().request.getParameter(OTHER_PROB);
	
	

	
	try {
    	    SendMail sch_not;	
    	    String from = "websch_admin";
    	    String to = reporteremail;
    	    String subject = "3TW Complaint Form has been received ";
    	   
   String[] message = {"Your compaint has been received, thank you for your feedback"+"\n",
    	    			"Center internal personnel will look into it"+"\n",
    	    			" \n",
				 "\n"}  ;
    	  		
           sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }

    }



protected void  send_notification_staff(String ppersonLN,String ppersonFN,String poperatorLN,String poperatorFN)
   throws HttpPresentationException
   {
String reportername = this.getComms().request.getParameter(REPORTERNAME);
	String reporteremail =this.getComms().request.getParameter(REPORTEREMAIL);
	String specify = this.getComms().request.getParameter(SPECIFY);
	String time_prob = this.getComms().request.getParameter(TIME_PROB);
	String mess_prob = this.getComms().request.getParameter(MESS_PROB);
	String cables_prob = this.getComms().request.getParameter(CABLES_PROB);
 	String other_prob = this.getComms().request.getParameter(OTHER_PROB);
	String personFN = null;
	String personLN = null;
	String operatorFN = null;
	String operatorLN = null;
	Timestamp ts = new Timestamp(System.currentTimeMillis());
	
	String timep = null;
	String messp = null;
	String cablesp = null;
	String otherp = null;
	
	   if(null != this.getComms().request.getParameter(TIME_PROB)) {
                	timep="Yes";
            	    } else {
                	timep="No";
            	     }
		
		 if(null != this.getComms().request.getParameter(CABLES_PROB)) {
                	cablesp="Yes";
            	    } else {
                	cablesp="No";
            	     }
		
		 if(null != this.getComms().request.getParameter(MESS_PROB)) {
                	messp="Yes";
            	    } else {
                	messp="No";
            	     }
 if(null != this.getComms().request.getParameter(OTHER_PROB)) {
                	otherp="Yes";
            	    } else {
                	otherp="No";
            	     }
	
String s_eventID = this.getS_eventID();
try 
        {    	
    	S_event theEvent = S_eventFactory.findS_eventByID(s_eventID);
	personFN = theEvent.getOwnerFirstname();
	personLN = theEvent.getOwnerLastname();
	operatorFN = theEvent.getOperatorFirstname();
	operatorLN = theEvent.getOperatorLastname();
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting event information", ex);
        }
	

	
	try {
    	    SendMail sch_not;	
    	    String from = "websch_admin";
    	    String to = "complaint_report";
    	    String subject = "3TW Complaint Form has been received ";
    	   
   String[] message = {"Date"+Long.toString(ts.getTime())+"\n",
    	    			"The followin PI/Operator have sent a complaint"+"\n",
    	    			"PI:  "+personFN+" "+personLN+" \n",
				 "Operator:  "+operatorFN+" "+operatorLN+"\n",
				 "\n",
				"The problem is time related "+timep+ "\n",
				"The problem is cables related "+cablesp+ "\n",
				"The problem is mess related "+messp+ "\n",
				"The problem is other related "+otherp+ "\n",
				" \n",
				"Describ: "+specify+"\n",
				" \n",
				"Previous PI and Operator info if available: \n",
				"Prev PI:  "+ppersonFN+" "+ppersonLN+" \n",
				 "Prev Operator:  "+poperatorFN+" "+poperatorLN+"\n",
				 "\n",
				 " \n"}  ;
    	  		
           sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }

    }



     /**
     show problem edit page
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException
    {
    
    
    
        complaintreportHTML page = new complaintreportHTML();
	
	
	 if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	  
	  
	   if(null != this.getComms().request.getParameter(REPORTERNAME)) {
            page.getElementReportername().setValue(this.getComms().request.getParameter(REPORTERNAME));
        }
	
	
	 if(null != this.getComms().request.getParameter(REPORTEREMAIL)) {
            page.getElementReporteremail().setValue(this.getComms().request.getParameter(REPORTEREMAIL));
        }
	
	
	
	 if(null != this.getComms().request.getParameter(TIME_PROB)) {
                	 page.getElementTime_prob().setChecked(true);
            	    } else {
                	 page.getElementTime_prob().setChecked(false);
            	     }
	
	
        if(null != this.getComms().request.getParameter(SPECIFY)) {
            page.getElementSpecify().setValue(this.getComms().request.getParameter(SPECIFY));
        }
	
	  
	   return page.toDocument();
   }
   

}
	
