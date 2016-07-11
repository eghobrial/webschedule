/**--------------------------------------------------------------
* Webschedule
*
* @class: invoice (the 3T version)
* @version
* @author: Eman Ghobrial
* @since: Sept 2002
* modified Feb 2005 added getPay()
*
*
*--------------------------------------------------------------*/

/* payments codes
*	0 development accounts no payment
* 	1 fMRI accounts without a tech ucsd (450)
*	2 1.5 scanners with tech ucsd (250)
*	3 1.5 scanners with out tech ucsd (210)
*	4 1.5 scanners with tech non-ucsd (355)
*	5 1.5 scanners with out tech non-ucsd (298)
*	6 fMRI with a tech ucsd (490)
*	7 fMRI with out tech non-ucsd (653)
*	8 fMRI with a tech non-ucsd  (696)
*	9 fMRI 7T 		(250)
*	10 fMRI animal with tech (435)
*/

package webschedule.presentation.s_eventMgmt;

import webschedule.business.person.*;
import webschedule.business.s_event.*;
import webschedule.business.project.*;
import webschedule.business.reporttrack.*;
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
import java.text.*;

/**
 *invoice.java generates an invoice for external billing
 *
 */
public class invoice extends BasePO
{
   /*
   * Constants
   */
    private static String MONTH ="month";
    private static String YEAR ="year";
    private static String  DESCRIPTION ="description";
    
    /*private static int PRIMERATE = 450;
    private static int NONPRIMERATE = 250;
    private static int HILLCRESTRATEWT = 250;
    private static int HILLCRESTRATEWOT = 210;
    private static int NONUCSDHILLCRESTRATEWT = 355;
    private static int NONUCSDHILLCRESTRATEWOT = 298;
    private static int NONUCSDPRIMERATEWOT = 653;
    private static int NONUCSDANIMFMRIWT = 435;*/
    
    private static String PROJ_ID = "projID";
 private static String mpatt = "$###,###.00";

    /**
     * Superclass method override
     */
    public boolean loggedInUserRequired()
    {
        return true;
    }
    static public String customFormat (String pattern, double value)
	{
	 DecimalFormat myFormatter = new DecimalFormat (pattern);
	 String output = myFormatter.format(value);
         return output;
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
    	invoiceHTML page = new invoiceHTML();
	String billreportflag = this.getbillreportflag();
	int billsday = this.getbillsday();
	int billeday = this.getbilleday();
	int billmonth = this.getbillmonth();
	int billyear = this.getbillyear();
	String billprojID = this.getbillprojID();
	String billdescription = this.getbilldescription();
	S_event[] s_event_list;
	Project project = null;
	Reporttrack report = null;
	int invoicenum = 0;

	
System.out.println("ProjectID off invoice"+billprojID);

try
		{    	
    		 report = ReporttrackFactory.findReporttrackForMonth(2010,0,3);
		 invoicenum = report.getInvoice_num();
		 //update invoice number
		 report.setDay(0);
	 	report.setMonth(0);
	 	report.setYear(2010);
		 report.setStartday(1);
		 report.setStartm(0);
	 	report.setStartyear(2010);
	 	report.setEndday(31);
	 	report.setEndmonth(0);
	 	report.setEndyear(2010);
		 report.setPreparedby("keeping a counter for invoice");
		 report.setInvoice_num(invoicenum+1);
	 	report.setReportinfo(3);
	 	report.save();
    		} catch  (Exception ex) {
            	throw new webschedulePresentationException("Error getting report information", ex);
        	}

         


             try
		{    	
    		 project = ProjectFactory.findProjectByID(billprojID);
    		} catch  (Exception ex) {
            	throw new webschedulePresentationException("Error getting project information", ex);
        	}
        	
      

	try {
	   s_event_list =S_eventFactory.findS_eventsPeriodForProject(billyear, billmonth, billsday, billeday, project);
       } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding EVENTs: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding EVENTs", ex);
        }
       
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
	     double unitcost = 0.0;
	     double extendedcost = 0.0;
   	     int pay = 0;
	     String accountid = null;
   	     String indexnum = null;
   	     String proj_name = null;
	     String contactphone = null;
	     String contactname = null;
	     String billaddr1 = null;
	     String billaddr2 = null;
	     String billaddr3 = null;
	     String city = null;
	     String state = null;
	     String zip = null;
	     String user_email = null;
	     String user_firstname = null;
	     String user_lastname = null;
	     
	     
   	     String[] message = null;
   	     String totalcosts = null;
   	     String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October","November","December"};
    		int daysPerMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	String starths = null;
		String startms = null;
		String endhs = null;
		String endms = null;
		Person owner = null;
		String userID = null;
	
String ponum = null;
	
           	try
    		{
    		 proj_name = project.getProj_name(); 	
//    		 indexnum = project.getIndexnum();
		 accountid = project.getAccountid();
		 contactphone = project.getContactphone();
		 contactname = project.getContactname();
		 billaddr1 = project.getBilladdr1();
		// billaddr2 = project.getBilladdr2();
		 billaddr3 = project.getBilladdr3();
		 city = project.getCity();
		 state = project.getState();
		 zip = project.getZip();
		  ponum = project.getPOnum();
		 paycode = project.getCodeofpay();
		 userID = project.getUserID();
			
		} catch  (Exception ex) {
            	throw new webschedulePresentationException("Error getting project information", ex);
	        }
		
		try
		{
		owner = PersonFactory.findPersonByID(userID);
		
		} catch  (Exception ex) {
            	throw new webschedulePresentationException("Error getting person ", ex);
	        }
		
		try
    		{
		user_email = owner.getEmail();
		user_firstname = owner.getFirstname();
		user_lastname = owner.getLastname();
		
		} catch  (Exception ex) {
            	throw new webschedulePresentationException("Error getting person information", ex);
	        }
		
		
		    LinkedList message_lines = new LinkedList();
	    message_lines.add("Report for Project "+proj_name+" :month"+monthnames[billmonth]+": year"+Integer.toString(billyear));
	    message_lines.add("========================================================");


	/*	  //calculation for the time the bill has been generated
    		Calendar cancelinfo = Calendar.getInstance();
    		int Ctodaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    		int Ctodaymonth = cancelinfo.get(cancelinfo.MONTH);
    		int Ctodayyear = cancelinfo.get(cancelinfo.YEAR);
		Ctodaymonth = Ctodaymonth + 1;
		page.setTextToday_month(Integer.toString(Ctodaymonth));
		page.setTextToday_day(Integer.toString(Ctodaydate));
		page.setTextToday_year(Integer.toString(Ctodayyear));*/

//Show the last day of the month as a billing date for the invoice
		page.setTextToday_month(monthnames[billmonth]);
		page.setTextToday_day(Integer.toString(daysPerMonth[billmonth]));
		page.setTextToday_year(Integer.toString(billyear));

		page.setTextInvoicenum(Integer.toString(invoicenum));
		
		page.setTextAccountID(accountid);
		page.setTextPonumber(ponum);
		//page.setTextAccountPhone(contactphone);
		page.setTextBilladdr1(billaddr1);
		//page.setTextBilladdr2(billaddr2);
		page.setTextBilladdr3(billaddr3);
		page.setTextCity(city);
		page.setTextState(state);
		page.setTextZip(zip);
		page.setTextContactname(contactname);
		page.setTextFirstName(user_firstname);
		page.setTextLastName(user_lastname);
		
  pay = getPay(paycode,billmonth,billyear);

    		   // assign payment cat.
/*	        if (paycode == 0)
	           {
	         	pay = 0;
	         	
	            }	
	        else if (paycode == 1)
	           {
	           pay = PRIMERATE;
	       	    }	
	       	else if (paycode == 2)
	       	         {
	       	    pay = HILLCRESTRATEWT;
	       	     }
	       	  else if (paycode == 3)
	       	    {
	       	      pay = HILLCRESTRATEWOT ;
	       	     
	       	   }
		   
		    else if (paycode == 7)
	       	    {
	       	      pay =  NONUCSDPRIMERATEWOT ;
	       	     
	       	   }
	      	
	      	else if (paycode == 10)
	       	    {
	       	      pay = NONUCSDANIMFMRIWT ;
	       	     
	       	   }*/
		   
	    // Generate the Events list 
	    HTMLTableRowElement templateRow = page.getElementTemplateRow();
       
        Node EventTable = templateRow.getParentNode();
        
	    		
	    // Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
	    templateRow.removeAttribute("id");
      
        
        // Remove id attributes from the cells in the template row
//        HTMLElement Start_timeCellTemplate = page.getElementStart_time();
//	HTMLElement End_timeCellTemplate = page.getElementEnd_time();
	HTMLElement DescripCellTemplate = page.getElementDescrip();
	HTMLElement QuantityCellTemplate = page.getElementQuantity();
	HTMLElement UnitcostCellTemplate = page.getElementUnitcost();
	HTMLElement Extendcost2CellTemplate = page.getElementExtendedcost2();
        
	    //Start_timeCellTemplate.removeAttribute("id");
	    //End_timeCellTemplate.removeAttribute("id");
	    DescripCellTemplate.removeAttribute("id");
	    QuantityCellTemplate.removeAttribute("id");
	    UnitcostCellTemplate.removeAttribute("id");
	    Extendcost2CellTemplate.removeAttribute("id"); 	

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
 		   
    		unitcost = pay;
                extendedcost = eventhours*pay;
		totalcost = totalcost + extendedcost;
	           			
	       	String ampm = null;
	        	
	        	if (starthi < 12) ampm = "am";
      			else if (starthi == 12) ampm = "Noon";
		      	else if (starthi == 24) {
      				starthi = starthi-12;
		      		ampm = "Mid.";
      				}
		      	else {
      				starthi = starthi -12;
		      		ampm = "pm";
		      	     }	
			     
			 if (startmi == 0) startms = "00";
				 else startms = "30";
	        String	starttimes = Integer.toString(starthi)+":"+startms+" "+ampm;
				     
			    	
	        		   	
	        		        	
	        	if (endhi < 12) ampm = "am";
      			else if (endhi == 12) ampm = "Noon";
		      	else if (endhi == 24) {
      				endhi = endhi-12;
		      		ampm = "Mid.";
      				}
		      	else {
      				endhi = endhi -12;
		      		ampm = "pm";
		      	     }	
			     
			     if (endmi == 0) endms = "00";
				 else endms = "30";
		String	endtimes =   Integer.toString(endhi)+":"+endms+" "+ampm;	
	
message_lines.add("Event on "+monthnames[eventmonth]+" "+Integer.toString(eventday)+","+Integer.toString(eventyear)+" costs = "+Double.toString((eventhours*pay)));	
	
	
	/*page.setTextStart_time(starttimes);
	page.setTextEnd_time(endtimes);*/
	eventmonth = eventmonth+1;
	page.setTextEvent_month(Integer.toString(eventmonth));
	page.setTextEvent_day(Integer.toString(eventday));
	page.setTextEvent_year(Integer.toString(eventyear));
	page.setTextProj_name(proj_name);
	page.setTextDescrip(description);
	page.setTextQuantity(Double.toString(eventhours));
	/*page.setTextUnitcost(Double.toString(unitcost));
	page.setTextExtendedcost2(Double.toString(extendedcost));*/
page.setTextUnitcost(customFormat(mpatt,unitcost));
	page.setTextExtendedcost2(customFormat(mpatt,extendedcost));	  		
	// Add a deep clone of the row to the DOM
        Node clonedNode = templateRow.cloneNode(true);
        EventTable.appendChild(clonedNode);	

	} //for end
// Finally remove the template row and template select option 
        //  from the page
	    templateRow.getParentNode().removeChild(templateRow);	

/*page.setTextExtendedcost1(Double.toString(totalcost));
page.setTextExtendedcost3(Double.toString(totalcost));*/
page.setTextExtendedcost1(customFormat(mpatt,totalcost));
page.setTextExtendedcost3(customFormat(mpatt,totalcost));

message_lines.add("========================================================");
message_lines.add("Total cost = "+totalcost);

message_lines.add("========================================================");
message_lines.add("This automatic email notification reflects your scheduled activities at the UCSD's center for fMRI  ");    
message_lines.add("in the past month. Your department business office will get the actual bill. Please do not reply to this message.  ");  
message_lines.add("If you have questions or think there may be an error, please contact our billing specialist, Mary O'Malley ");
message_lines.add("by email at momalley@ucsd.edu. Thank you.  ");	
message_lines.add("========================================================");




	        try {
    	    SendMailLL sch_not;	
    	    String from = "appsadmin";
	    System.out.println("User email "+user_email);
    	   String to = user_email;
	   /*String to = "eghobrial@ucsd.edu";*/
    	    String subject = "Billing for Project"+proj_name;
    	    System.out.println("Linked List size "+Integer.toString(message_lines.size()));
    	    for (int j = 0; j < message_lines.size(); j++)
    	      {
    	      Object line = message_lines.get(j);
    	     System.out.println("Line String "+((String) line));
    	      // message[j] = (String) line;
    	      //System.out.println("Message array "+ message);
    	    }
    	   		
           sch_not = new SendMailLL (from,to,subject,message_lines);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }

// write out HTML
	    return page.toDocument();
    }
   
		




}
