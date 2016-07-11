/**--------------------------------------------------------------
* Webschedule
*
* @class: Annual_report
* @version
* @author: Eman Ghobrial
* @since: Jan 2005
*
*
*--------------------------------------------------------------*/

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

/**
 *Bill_report.java shows the Bill_report Menu Options
 *
 */
public class Annual_report extends BasePO
{
   /*
   * Constants
   */
    private static String SMONTH ="smonth";
    private static String EMONTH ="emonth";
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
    	Annual_reportHTML page = new Annual_reportHTML();
        String smonth = this.getComms().request.getParameter(SMONTH);
	String emonth = this.getComms().request.getParameter(EMONTH);
    	/*String month = this.getComms().request.getParameter(MONTH);
    	String year = this.getComms().request.getParameter(YEAR);*/
	
	//calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
	String updatem = Integer.toString(todaymonth);
 	String updated = Integer.toString(todaydate);
	String updatey = Integer.toString(todayyear); 
			
	HTMLSelectElement	year_select;
	HTMLCollection		year_selectCollection;
	HTMLOptionElement	year_option;
	String			year_optionName;
		
     	
	year_select = (HTMLSelectElement)page.getElementYear();
	year_selectCollection = year_select.getOptions();	
	
        //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the 7T error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
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

   public String handleGenerate()
        throws HttpPresentationException
	    {
	String smonth = this.getComms().request.getParameter(SMONTH);
	String emonth = this.getComms().request.getParameter(EMONTH);
        String year = this.getComms().request.getParameter(YEAR);
    	String description = this.getComms().request.getParameter(DESCRIPTION);
    	
	String billingflag = this.getbillreportflag();
	System.out.println("flag off bill_report"+billingflag);
	
	this.setbillreportflag(billingflag);

    	//   System.out.println("Month off generate Bill module "+month);   	
        int lastday;
	int daysPerMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};
	int billsmonth =  Integer.parseInt(smonth);
	int billemonth =  Integer.parseInt(emonth);
	int billyear =    Integer.parseInt(year);
	String username = null, firstname=null, lastname=null;
	
	this.setbillsmonth(billsmonth);
	this.setbillemonth(billemonth);
	this.setbillyear(billyear);
	this.setbilldescription(description);
	
    	throw new ClientPageRedirectException(PROJ_ANNUAL_LIST_PAGE);	
	}

	public String handleRecharge()
	 throws HttpPresentationException
		{

		this.setbillreportflag("IFIS");
                  throw new ClientPageRedirectException(ANNUAL_REPORT_PAGE);
              }

	public String handleInvoice()
	 throws HttpPresentationException
		{
		System.out.println("flag off Report_Gen is Invoice");
		this.setbillreportflag("INVOICE");
                  throw new ClientPageRedirectException(ANNUAL_REPORT_PAGE);
              }
	
	
	
	public String handleFree()
	 throws HttpPresentationException
		{
		System.out.println("flag off Report_Gen is Free");
		this.setbillreportflag("Free");
                  throw new ClientPageRedirectException(ANNUAL_REPORT_PAGE);
              }
	
	
}
