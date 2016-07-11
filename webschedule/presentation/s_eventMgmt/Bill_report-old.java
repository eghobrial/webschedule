/**--------------------------------------------------------------
* Webschedule
*
* @class: Bill_report
* @version
* @author: Eman Ghobrial
* @since: May 2001
*
*--------------------------------------------------------------*/

package webschedule.presentation.s_eventMgmt;

import webschedule.business.person.*;
import webschedule.business.s_event.*;
import webschedule.business.project.*;
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
import webschedule.SendMailLL;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.lang.String;

/**
 *Report_Gen.java shows the Report_Gen Menu Options
 *
 */
public class Bill_report extends BasePO
{
   /*
   * Constants
   */
    private static String MONTH ="month";
    private static String YEAR ="year";
    private static String  DESCRIPTION ="description";
    private static int PRIMERATE = 450;
    private static int NONPRIMERATE = 250;


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
    throws HttpPresentationException, webschedulePresentationException
    {
    	Bill_reportHTML page = new Bill_reportHTML();
	String sdate = this.getComms().request.getParameter(SDATE);
	String edate = this.getComms().request.getParameter(EDATE);
    	String month = this.getComms().request.getParameter(MONTH);
    	String year = this.getComms().request.getParameter(YEAR);
        	

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

   public String handleGenerateBill()
        throws HttpPresentationException
	    {
	String sdate = this.getComms().request.getParameter(SDATE);
	String edate = this.getComms().request.getParameter(EDATE);
    	String month = this.getComms().request.getParameter(MONTH);
    	String year = this.getComms().request.getParameter(YEAR);
    	String description = this.getComms().request.getParameter(DESCRIPTION);
    	
    	   System.out.println("Month off generate Bill module "+month);   	
        int lastday;
	int daysPerMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};
	int billmonth =  Integer.parseInt(month);
	int billyear =    Integer.parseInt(year);
	String username = null, firstname=null, lastname=null;
	
	// get the last day of the month check for leap year
	if (billmonth == 1 && (billyear % 400 == 0 && billyear % 100 != 0))
		{
			lastday =  29;
		} else {
			lastday = daysPerMonth [billmonth];
		}
    	
	try  {
	   Person[] personList = PersonFactory.getPersonsList();
	   for (int numPersons = 0; numPersons < personList.length; numPersons++)
	    {
	    try {
	     firstname = personList[numPersons].getFirstname();
	     lastname = personList[numPersons].getLastname();
	     username = firstname+" "+lastname;
	     System.out.println("User name "+username);
	     S_event[] s_event_list = S_eventFactory.findS_eventsMonthlyForPerson(billyear, billmonth, lastday, personList[numPersons]);
	     generate_bill(s_event_list, billmonth, billyear, username);
	     } catch(Exception ex) {
          	throw new webschedulePresentationException("Error getting events list", ex);
    		 }
	    }
	  } catch(Exception ex) {
          	throw new webschedulePresentationException("Error getting persons list", ex);
    		 }
    	throw new ClientPageRedirectException(REPORT_GEN_PAGE);	
	}
	
   public void generate_bill (S_event[] s_event_list, int month, int year, String username)
       throws HttpPresentationException
	    {
	     int starthi = 0, startmi = 0, endhi = 0, endmi = 0;
    	     String description = null;
             int todayday = 0;
    	     int todaymonth = 0;
    	     int todayyear = 0;
    	     int dow = 0;
    	     int eventday = 0, eventmonth = 0, eventyear = 0;
    	     String proj_id = null;
    	     boolean isRepeat = false;
    	     Project  theProject = null;
    	     int paycode = 0;
    	     double starttime = 0.0;
             double endtime = 0.0;
   	     double eventhours = 0.0;
   	     double nonprimepart = 0.0, primepart = 0.0;
   	     double totalcost = 0.0;
   	     int pay = 0;
   	     String indexnum = null;
   	     String proj_name = null;
   	     String[] message = null;
   	     String totalcosts = null;
   	     String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};
    	
	    LinkedList message_lines = new LinkedList();
	    message_lines.add("Bill Report for user "+username+" :month"+month+": year"+year);
	    message_lines.add("========================================================");
	
	    for (int numEvents = 0; numEvents<s_event_list.length; numEvents++)
	      {
	       try {
	    	starthi = s_event_list[numEvents].getStarth();
	    	System.out.println("Start time "+Integer.toString(starthi));
            	startmi = s_event_list[numEvents].getStartm();
            	endhi = s_event_list[numEvents].getEndh();
            	endmi = s_event_list[numEvents].getEndm();
            	description = s_event_list[numEvents].getDescription();
            	eventday =  s_event_list[numEvents].getEventday();
            	eventmonth =  s_event_list[numEvents].getEventmonth();
            	eventyear =  s_event_list[numEvents].getEventyear();
            	dow =   s_event_list[numEvents].getEventdayofw();
            	todayday = s_event_list[numEvents].getTodayday() ;
            	todaymonth = s_event_list[numEvents].getTodaymonth() ;
            	todayyear = s_event_list[numEvents].getTodayyear() ;
            	proj_id = s_event_list[numEvents].getProjectID();
           	isRepeat = s_event_list[numEvents].isRepeat();
           	try
		{    	
    		 theProject = ProjectFactory.findProjectByID(proj_id);
    		} catch  (Exception ex) {
            	throw new webschedulePresentationException("Error getting project information", ex);
        	}
        	
           	try
    		{
    		 proj_name = theProject.getProj_name(); 	
    		 indexnum = theProject.getIndexnum();
    		 paycode = theProject.getCodeofpay();
		} catch  (Exception ex) {
            	throw new webschedulePresentationException("Error getting project information", ex);
	        }
	
	       	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
	          throw new webschedulePresentationException("Error getting event info.", ex);
    		 }
    		
    		if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    		else starttime = (double) starthi;
    	
    		if (endmi == 30)
    		endtime =     endhi + 0.5;
    		else endtime = (double) endhi;
    		eventhours = endtime - starttime;
    		
    		   // assign payment cat.
	        if (paycode == 0)
	           {
	         	pay = 0;
	         	totalcost = totalcost + eventhours*pay;
	            }	
	        else  if (paycode == 1)
	           {
	           if ((starttime >= 8) &&  (dow!=1) && (dow!=7))
	            {
	               if (endtime <= 20)
	                 {
	       		   pay = PRIMERATE;
	       		   totalcost = totalcost + eventhours*pay;
	       	          }	
	       	       else
	       	         {
	       	           nonprimepart = endtime - 20;
	       	           primepart = eventhours - nonprimepart;
	       	           totalcost = totalcost + (nonprimepart * PRIMERATE) + (primepart * NONPRIMERATE);
	       	         }
	       	     }
	       	  else
	       	    {
	       	      pay = NONPRIMERATE;
	       	      totalcost = totalcost + eventhours*pay;
	       	     }
	       	   }
	       	

    			
    		
    		
    		message_lines.add("Event on "+monthnames[month]+" "+Integer.toString(eventday)+", "+Integer.toString(year)+"for project "+proj_name+" costs = "+Double.toString((eventhours*pay)));
           System.out.println("Event on "+monthnames[month]+" "+Integer.toString(eventday)+", "+Integer.toString(year)+"for project "+proj_name+"costs"+Double.toString((eventhours*pay)));
      		
    	    } //for end	
    	    totalcosts = Double.toString(totalcost);
    	      message_lines.add("Total cost = "+totalcosts);
    	    System.out.println("Total cost = "+totalcosts);
	
	        try {
    	    SendMailLL sch_not;	
    	    String from = "emang";
    	    String to = "eghobrial@ucsd.edu";
    	    String subject = "Billing for user"+username+": for "+monthnames[month]+", "+year;
    	    System.out.println("Linked List size "+Integer.toString(message_lines.size()));
    	   /* for (int j = 0; j < message_lines.size(); j++)
    	      {
    	      Object line = message_lines.get(j);
    	     System.out.println("Line String "+((String) line));
    	       message[j] = (String) line;
    	      System.out.println("Message array "+ message);
    	    }*/	
    	   		
           sch_not = new SendMailLL (from,to,subject,message_lines);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }

}

}
