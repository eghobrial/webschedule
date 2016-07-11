/**--------------------------------------------------------------
* Webschedule
*
* @class: BasePO
* @version
* @author: Eman Ghobrial
* @since: Sept 2002
* modified Feb 2005
* modified April 2005 added operator as a session var
* Jan 09 added returnProposalType function
* new code added for center tech only charges if pilot hours used
*
*--------------------------------------------------------------*/


/* payments codes
*	0 development accounts no payment
* 	1 fMRI accounts (450)
*	2 1.5 scanners with tech ucsd (250)
*	3 1.5 scanners with out tech ucsd (210)
*	4 1.5 scanners with tech nonucsd (363)
*	5 1.5 scanners with out tech non-ucsd (305)
*	6 fMRI with a tech ucsd (500)
*	7 fMRI with out tech non-ucsd (653)
*	8 fMRI with a tech non-ucsd (725)
*	9 fMRI scanning animals without tech ucsd (250)
*	10 fMRI scanning animals with tech non-ucsd (435)
*	11 FMRI scanning animals with tech ucsd (300)
*       12 fMRI scanning animals without tech non-ucsd (363)
* 	13 Center Tech only (50)
*/



/**
 * This is the parent Presentaion object.  All presentation objects
 * should extend this class.
 * 
 * The run method looks for an event parameter and then calls 
 * handle<EventName>.  If the "event" Parameter is not defined then
 * the handleDefault() method is called in your child class.
 */

package webschedule.presentation;

import com.lutris.appserver.server.httpPresentation.*;
import com.lutris.appserver.server.Enhydra;
import com.lutris.xml.xmlc.*;
import com.lutris.xml.xmlc.html.*;
import com.lutris.logging.*;
import com.lutris.util.KeywordValueException;
import com.lutris.appserver.server.user.User;

import org.w3c.dom.*;
import org.w3c.dom.html.HTMLElement;

import webschedule.collections.*;
import webschedule.collections.impl.LList;
import java.util.Enumeration;

import java.lang.reflect.*;
import java.util.*;
import java.lang.Throwable;

import webschedule.*;
import webschedule.presentation.webschedulePresentationException;
import webschedule.business.*;
import webschedule.business.person.Person;
import webschedule.business.project.Project;
import webschedule.business.s_event.*;
import webschedule.business.operator.Operator;
import webschedule.business.operates.Operates;
import webschedule.business.proposal.*;

abstract public class BasePO implements HttpPresentation 
{
   protected static final String USER_KEY = "webschedulePerson";
   protected static String LOGIN_PAGE = "/personMgmt/Login.po";
   protected static String REGISTER_PAGE = "/personMgmt/Register.po";
    protected static String ADDOPERATOR_PAGE = "/personMgmt/Addoperator.po";
    protected static String ENROLLOPERATOR_PAGE = "/personMgmt/Enrolloperator.po";
   protected static String PROJECT_CATALOG_PAGE = "/projectMgmt/ProjCatalog.po";
   protected static String MAINMENU_PAGE = "/s_eventMgmt/mainmenu.po";
   protected static String SELECT_DATE_PAGE = "/s_eventMgmt/SelectDate.po";
   protected static String EDIT_EVENT_PAGE = "/s_eventMgmt/EditEvent.po";
   protected static String CHANGE_EVENT_PAGE =   "/s_eventMgmt/ChangeEvent.po";
    protected static String SHOW_EVENT_PAGE =   "/s_eventMgmt/ShowEvent.po";
   protected static String SHOW_CONFLICTS_PAGE =   "/s_eventMgmt/Show_conflicts.po";
   protected static String DOWNTIME_PAGE =   "/s_eventMgmt/Downtime.po";
   protected static String BLOCKTIME_PAGE =   "/s_eventMgmt/Blocktime.po";
   protected static String ADMIN_PAGE = "/personMgmt/Admin.po";
   protected static String PROJECT_ADMIN_PAGE = "/projectMgmt/Project_Admin.po";
   protected static String PROJ_PASSWD_PAGE = "/projectMgmt/Proj_passwd.po";
   protected static String REPORT_GEN_PAGE =   "/s_eventMgmt/Report_Gen.po";
protected static String BILL_REPORT_PAGE =   "/s_eventMgmt/Bill_report.po";
   protected static String CANCEL_REPORT_PAGE =   "/s_eventMgmt/Cancel_report.po";
   protected static String PROJ_PASSWD_CAT_PAGE =  "/projectMgmt/Proj_passwd_cat.po";
   protected static String PROJ_BILL_LIST_PAGE = "/projectMgmt/proj_bill_list.po";
  protected static String PROJ_CANCEL_LIST_PAGE = "/projectMgmt/proj_cancel_list.po";
 protected static String PROJ_CANCEL_ANNUAL_LIST_PAGE = "/projectMgmt/proj_cancel_annual_list.po";
   protected static String UCSDPROJLIST_PAGE = "/projectMgmt/ucsdprojlist.po";
  protected static String NONUCSDPROJLIST_PAGE = "/projectMgmt/nonucsdprojlist.po";
  protected static String UCSDPROJECTSEDIT_PAGE = "/projectMgmt/ucsdprojectsedit.po";
  protected static String NONUCSDPROJECTSEDIT_PAGE = "/projectMgmt/nonucsdprojectsedit.po";
 
   protected static String INVOICE_PAGE =   "/s_eventMgmt/invoice.po";
protected static String CHANGE_PAGE =   "/s_eventMgmt/change.po";
protected static String RECHARGE_PAGE =   "/s_eventMgmt/recharge.po";
protected static String THANKYOU_PAGE =   "/problemMgmt/thankyou.po";
protected static String PROBLEMMENU_PAGE =   "/problemMgmt/problemmenu.po";
protected static String STATUS_PAGE =   "/problemMgmt/status.po";
protected static String PROBLEMDETAILS_PAGE =   "/problemMgmt/problemdetails.po";
protected static String UPDATESTATUS_PAGE =   "/problemMgmt/updatestatus.po";
protected static String PROBLEMEDIT_PAGE =   "/problemMgmt/problemedit.po";
protected static String NOTIFY_PAGE =   "/problemMgmt/notify.po";
protected static String ANNUAL_REPORT_PAGE =   "/s_eventMgmt/Annual_report.po";
protected static String FY_REPORT_PAGE =   "/s_eventMgmt/FY_report.po";
protected static String ANNUAL_CANCEL_REPORT_PAGE =   "/s_eventMgmt/Annual_cancel_report.po";
protected static String COMPLAINTREPORT_PAGE =   "/complaintMgmt/complaintreport.po";
protected static String PROJ_ANNUAL_LIST_PAGE = "/projectMgmt/proj_annual_list.po";
protected static String PROJ_FY_LIST_PAGE = "/projectMgmt/proj_FY_list.po";
protected static String CREDIT_PILOT_HOURS_PAGE = "/projectMgmt/credit_pilot_hours.po";
protected static String DEBT_PILOT_HOURS_PAGE = "/projectMgmt/debt_pilot_hours.po";
protected static String MANAGEPROPOSAL_PAGE =   "/proposalMgmt/manageproposal.po";
protected static String NEWPROPOSALP1_PAGE =   "/proposalMgmt/newproposalp1.po";
protected static String NEWPROPOSALP1A_PAGE =   "/proposalMgmt/newproposalp1a.po";
 protected static String NEWPROPOSALP2_PAGE =   "/proposalMgmt/newproposalp2.po";  
 protected static String NEWPROPOSALP3UC_PAGE =   "/proposalMgmt/newproposalp3uc.po"; 
 protected static String NEWPROPOSALP3NONUC_PAGE =   "/proposalMgmt/newproposalp3nonuc.po"; 
 protected static String NEWPROPOSALP4_PAGE =   "/proposalMgmt/newproposalp4.po"; 
  protected static String NEWPROPOSALP4A_PAGE =   "/proposalMgmt/newproposalp4a.po"; 
 protected static String PREVIEW_PAGE =   "/proposalMgmt/preview.po"; 
 protected static String PREVIEWA_PAGE =   "/proposalMgmt/previewa.po"; 
 protected static String EDITPROPOSAL_PAGE =   "/proposalMgmt/editproposal.po";
protected static String PROPOSALP1_PAGE =   "/proposalMgmt/proposalp1.po";
protected static String PROPOSALP1A_PAGE =   "/proposalMgmt/proposalp1a.po";
 protected static String PROPOSALP2_PAGE =   "/proposalMgmt/proposalp2.po";  
 protected static String PROPOSALP3UC_PAGE =   "/proposalMgmt/proposalp3uc.po"; 
 protected static String PROPOSALP3NONUC_PAGE =   "/proposalMgmt/proposalp3nonuc.po"; 
 protected static String PROPOSALP4_PAGE =   "/proposalMgmt/proposalp4.po"; 
 protected static String PROPOSALP4A_PAGE =   "/proposalMgmt/proposalp4a.po"; 
  protected static String PROPOSALSTATUS_PAGE =   "/proposalMgmt/proposalstatus.po"; 
   protected static String PROPOSALLOG_PAGE =   "/proposalMgmt/proposallog.po";
   protected static String APROPOSALLOG_PAGE =   "/proposalMgmt/aproposallog.po";
   protected static String CHPROPOSALLOG_PAGE =   "/proposalMgmt/chproposallog.po";
   protected static String CACCLOG_PAGE =   "/proposalMgmt/cacclog.po";
   protected static String PROPOSALADMINMENU_PAGE =   "/proposalMgmt/proposaladminmenu.po";
   protected static String APPROVEPROPOSAL_PAGE =   "/proposalMgmt/approveproposal.po";
   protected static String APPROVEPMESG_PAGE =   "/proposalMgmt/approvepmesg.po";
   protected static String CHECKROUTE_PAGE =   "/proposalMgmt/checkroute.po";
   protected static String QUICKVIEW_PAGE =   "/proposalMgmt/quickview.po"; 
   protected static String QUICKVIEWA_PAGE =   "/proposalMgmt/quickviewa.po"; 
   protected static String ADDACCOUNTS_PAGE =   "/proposalMgmt/addaccounts.po"; 
    protected static String SHOWCOMMENTS_PAGE =   "/proposalMgmt/showcomments.po"; 
    protected static String SHOWRESCOMMENTS_PAGE =   "/proposalMgmt/showrescomments.po";   
    protected static String UCSDPROJECTS_PAGE =   "/proposalMgmt/ucsdprojects.po";   
   protected static String UCPROJECTINFO_PAGE =   "/proposalMgmt/ucprojectinfo.po";  
    protected static String NONUCSDPROJECTS_PAGE =   "/proposalMgmt/nonucsdprojects.po";   
   protected static String NONUCPROJECTINFO_PAGE =   "/proposalMgmt/nonucprojectinfo.po";  
   protected static String PIPROJECTSLIST_PAGE =   "/proposalMgmt/piprojectslist.po";     
   protected static String PILIST_PAGE =   "/proposalMgmt/pilist.po";  
   protected static String NEWPROPOSALMENU_PAGE =   "/proposalMgmt/newproposalmenu.po"; 
   protected static String BIPROPOSALMENU_PAGE =   "/proposalMgmt/biproposalmenu.po";  
protected static String FY_DEV_PAGE =   "/s_eventMgmt/fy_dev.po";
protected static String ANNUAL_DEV_PAGE =   "/s_eventMgmt/annual_dev.po";  
 private static String STANDARD_METHOD_PREFIX = "handle";
     private static String EVENT = "event";
     
     
    private static int PRIMERATE = 450;
    private static int NONPRIMERATE = 250;
    private static int HILLCRESTRATEWT = 250;
    private static int HILLCRESTRATEWOT = 210;
    private static int NONUCSDHILLCRESTRATEWT = 363;
    private static int NONUCSDHILLCRESTRATEWOT = 305;
    private static int PRIMERATEWT = 500;
    private static int NONUCSDPRIMERATEWOT = 653;
    private static int NONUCSDPRIMERATEWT = 725;
      private static int FMRI7TRATE = 250;
    private static int NONUCSDANIMFMRIWT = 435;
    private static int ANIMFMRIWT = 300;
     private static int NONUCSDANIMFMRIWOT = 363;
     
    //rate added for March 2007 increase
 private static int PRIMERATE2007 = 525;
 private static int PRIMERATEWT2007 = 575;
    private static int NONUCSDPRIMERATEWOT2007 = 761;
    private static int NONUCSDPRIMERATEWT2007 = 834;   
  private static int NONUCNAVY = 833;  
    private static int CENTERTECH = 50;   

//rate added for Sept 2011 increase
 private static int PRIMERATE2011 = 551;
 private static int PRIMERATEWT2011 = 614;
    private static int NONUCSDPRIMERATEWOT2011 = 799;
    private static int NONUCSDPRIMERATEWT2011 = 890;   
    
//rate added for July 2012 increase
 private static int PRIMERATE2012 = 579;
 private static int PRIMERATEWT2012 = 647;
    private static int NONUCSDPRIMERATEWOT2012 = 840;
    private static int NONUCSDPRIMERATEWT2012 = 938;   

//rate added for July 2013 increase
 private static int PRIMERATE2013 = 597;
 private static int PRIMERATEWT2013 = 668;
    private static int NONUCSDPRIMERATEWOT2013 = 866;
    private static int NONUCSDPRIMERATEWT2013 = 969;   
     
   /**
    * This is the procedure that is called if there is no "event"
    * HTTP parameter found. It must be overriden by the subclass to 
    * do default processing or error checking/handling.
    * 
    * @return String The String representation of the HTML or (other format)
    * of the document to be displayed. This method would need to be changed 
    * if you wanted to return binary data as well. It returns a String
    * for simplicity right now.
    */   
   abstract public String handleDefault() throws HttpPresentationException;
   
   /**
    * This method should be implemented in the subclass so that it returns
    * true if this particular request requires the user to be logged
    * in, otherwise false.
    */
   abstract protected boolean loggedInUserRequired();
   
   /* 
    * Saved HTML input and output context, and session data
    */
   protected HttpPresentationComms myComms = null;
   protected webscheduleSessionData mySessionData = null;
   
   /*   
   Reference to the person logged in to the session
   */
   protected Person myPerson = null;
   protected Project myProject = null;
   protected Project mySwitchProject = null;
   protected MyDate myeDate = null;
      
   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public HttpPresentationComms getComms()
   {
       return this.myComms;    
   }
  


/**
    * @return The pay rate given the code of pay
    * 
    */
   public int getPay(int paycode, int billmonth, int billyear)
   {
    int pay=0;
   
    		   // assign payment cat.
	        if (paycode == 0)
	           {
	         	pay = 0;
	         	
	            }	
	        else if (paycode == 1)
	         {
//rate change begning Sept 1
		if (billyear < 2011)
			pay = PRIMERATE2007;
		else if (billyear == 2011 && billmonth <= 7)
	          	pay = PRIMERATE2007;
		else if (billyear == 2011 && billmonth >=8)
			pay =  PRIMERATE2011;
		else if (billyear == 2012 && billmonth <=5)
			pay =  PRIMERATE2011;
		 else if (billyear == 2012 && billmonth >=6)
			pay =  PRIMERATE2012;
		else if (billyear == 2013 && billmonth <=5)
			pay =  PRIMERATE2012;
		else if (billyear == 2013 && billmonth >=6)
			pay =  PRIMERATE2013;
		else if (billyear == 2014 && billmonth <=5)
			pay =  PRIMERATE2013;
	       	    }	

	       	else if (paycode == 2)
	       	         {
	       	    pay = HILLCRESTRATEWT;
	       	     }
	       	  else if (paycode == 3)
	       	    {
	       	      pay = HILLCRESTRATEWOT ;
	       	     
	       	   }
		  else if (paycode == 4)
	       	    {
	       	      pay = NONUCSDHILLCRESTRATEWT ;
	       	     
	       	   }
		  else if (paycode == 5)
	       	    {

	       	      pay = NONUCSDHILLCRESTRATEWOT ;
	       	     
	       	   }
		  else if (paycode == 6)
	       	    {
		if (billyear <= 2011 && billmonth <= 7)
	          	pay = PRIMERATEWT2007;
		else if (billyear == 2011 && billmonth >=8)
			pay =  PRIMERATEWT2011;
		else if (billyear == 2012 && billmonth <=5)
			pay =  PRIMERATEWT2011;
		 else if (billyear == 2012 && billmonth >=6)
			pay =  PRIMERATEWT2012;
		else if (billyear == 2013 && billmonth <=5)
			pay =  PRIMERATEWT2012;
		else if (billyear == 2013 && billmonth >=6)
			pay =  PRIMERATEWT2013;
		else if (billyear == 2014 && billmonth <=5)
			pay =  PRIMERATEWT2013;
	       	   }
		  else if (paycode == 7)
	       	    {
		if (billyear <= 2011 && billmonth <= 7)
	          	pay = NONUCSDPRIMERATEWOT2007;
		else if (billyear == 2011 && billmonth >=8)
			pay =  NONUCSDPRIMERATEWOT2011;
		else if (billyear == 2012 && billmonth <=5)
			pay =  NONUCSDPRIMERATEWOT2011;
		 else if (billyear == 2012 && billmonth >=6)
			pay =  NONUCSDPRIMERATEWOT2012;
		else if (billyear == 2013 && billmonth <=5)
			pay =  NONUCSDPRIMERATEWOT2012;
		else if (billyear == 2013 && billmonth >=6)
			pay =  NONUCSDPRIMERATEWOT2013;
		else if (billyear == 2014 && billmonth <=5)
			pay =  NONUCSDPRIMERATEWOT2013;

	       	   }
		  else if (paycode == 8)
	       	    {
		  
		if (billyear <= 2011 && billmonth <= 7)
	          	pay = NONUCSDPRIMERATEWT2007;
		else if (billyear == 2011 && billmonth >=8)
			pay =  NONUCSDPRIMERATEWT2011;
		else if (billyear == 2012 && billmonth <=5)
			pay =  NONUCSDPRIMERATEWT2011;
		 else if (billyear == 2012 && billmonth >=6)
			pay =  NONUCSDPRIMERATEWT2012;
		else if (billyear == 2013 && billmonth <=5)
			pay =  NONUCSDPRIMERATEWT2012;
		else if (billyear == 2013 && billmonth >=6)
			pay =  NONUCSDPRIMERATEWT2013;
		else if (billyear == 2014 && billmonth <=5)
			pay =  NONUCSDPRIMERATEWT2013;
     	     
	       	   }
		   
		    else if (paycode == 9)
	       	    {
	       	      pay = FMRI7TRATE ;
	       	     
	       	   }

		else if (paycode == 10)
	       	    {
	       	      pay = NONUCSDANIMFMRIWT ;
	       	     
	       	   }
		  else if (paycode == 11)
	       	    {
	       	      pay = ANIMFMRIWT ;
	       	     
	       	   }
		  else if (paycode == 12)
	       	    {
	       	      pay = NONUCSDANIMFMRIWOT ;
	       	     
	       	   }
		  else if (paycode == 13)
	       	    {
	       	      pay = CENTERTECH ;
	       	     
	       	   }
   
    else if (paycode == 16)
	       	    {
	       	      pay =  NONUCNAVY  ;
	       	     
	       	   }
       return pay;
   }
   



   
   /**
    * @return The pay rate given the code of pay
    * 
    */
   /*public int getPay(int paycode, int billmonth, int billyear)
   {
    int pay=0;
   
    		   // assign payment cat.
	        if (paycode == 0)
	           {
	         	pay = 0;
	         	
	            }	
	        else if (paycode == 1)
	           {
		   if (billyear >=2007)
	           pay = PRIMERATE2007;
		   else pay =  PRIMERATE;
	       	    }	
	       	else if (paycode == 2)
	       	         {
	       	    pay = HILLCRESTRATEWT;
	       	     }
	       	  else if (paycode == 3)
	       	    {
	       	      pay = HILLCRESTRATEWOT ;
	       	     
	       	   }
		  else if (paycode == 4)
	       	    {
	       	      pay = NONUCSDHILLCRESTRATEWT ;
	       	     
	       	   }
		  else if (paycode == 5)
	       	    {
	       	      pay = NONUCSDHILLCRESTRATEWOT ;
	       	     
	       	   }
		  else if (paycode == 6)
	       	    {
		     if (billyear >=2007)
	           pay = PRIMERATEWT2007;
		   else
	       	      pay = PRIMERATEWT ;
	       	     
	       	   }
		  else if (paycode == 7)
	       	    {
		     if (billyear >=2007)
	           pay = NONUCSDPRIMERATEWOT2007;
		   else
	       	      pay = NONUCSDPRIMERATEWOT ;
	       	     
	       	   }
		  else if (paycode == 8)
	       	    {
		     if (billyear >=2007)
	           pay = NONUCSDPRIMERATEWT2007;
		   else
	       	      pay =  NONUCSDPRIMERATEWT;
	       	     
	       	   }
		   
		   
		    else if (paycode == 9)
	       	    {
	       	      pay = FMRI7TRATE ;
	       	     
	       	   }

		else if (paycode == 10)
	       	    {
	       	      pay = NONUCSDANIMFMRIWT ;
	       	     
	       	   }
		  else if (paycode == 11)
	       	    {
	       	      pay = ANIMFMRIWT ;
	       	     
	       	   }
		  else if (paycode == 12)
	       	    {
	       	      pay = NONUCSDANIMFMRIWOT ;
	       	     
	       	   }
		  
   
   
       return pay;
   }*/
   
   
   /* @return the proposal type (humanns, humanbi, etc)*/
   
   public String returnProposalType(String proposalID)
   
   { 
   String bookmark=null;
    
    try {
	   
          Proposal theProposal = ProposalFactory.findProposalByID(proposalID);
	  bookmark = theProposal.getBookmark();
	    } catch(webscheduleBusinessException ex) {
         
	  }   
   
   return bookmark;
   }
   
/* @return the number of hours used for a certain project*/

public double getActDonehours (Project theproject)

{
S_event[]	s_event_list=null;
int starthi = 0, startmi = 0, endhi = 0, endmi = 0;
double starttime = 0.0;
double endtime = 0.0;
double eventhours = 0.0;
double totalh = 0.0;

System.out.println("I am at the act done function");

try {
s_event_list=S_eventFactory.findS_eventsForProject(theproject);
System.out.println("Num of events for the project "+Integer.toString(s_event_list.length));
       } catch(webscheduleBusinessException ex) {
            
        }

  for (int numEvents = 0; numEvents<s_event_list.length; numEvents++)
	 {
	     try {
	    	starthi = s_event_list[numEvents].getStarth();
	    	System.out.println("Start time "+Integer.toString(starthi));
            	startmi = s_event_list[numEvents].getStartm();
            	endhi = s_event_list[numEvents].getEndh();
            	endmi = s_event_list[numEvents].getEndm();
	       	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
	        
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
			totalh = totalh+eventhours;
 		  }	
   return totalh;

}


public double getUsedHours (Project project, int billyear,int billmonth,int billsday,int billeday)
	throws webschedulePresentationException
	{
	S_event[]	s_event_list;
	
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
	     double totalh = 0.0;
   	     double nonprimepart = 0.0, primepart = 0.0;
   	     double totalcost = 0.0;
	     double unitcost = 0.0;
	     double extendedcost = 0.0;
   	     int pay = 0;
	       
	     String proj_name = null;
	     
	    System.out.println("I am called **Total hours** ");   
	    	
	try
	  {
	   paycode = project.getCodeofpay();
	   } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Project information", ex);
        }
	 
		  
	  try 
	  {
	   
	s_event_list=S_eventFactory.findS_eventsPeriodForProject(billyear,billmonth,billsday, billeday, project);
       } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding EVENTs: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding EVENTs", ex);
        }
	
	
	  //calculation for the time the bill has been generated
    		Calendar cancelinfo = Calendar.getInstance();
    		int Ctodaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    		int Ctodaymonth = cancelinfo.get(cancelinfo.MONTH);
    		int Ctodayyear = cancelinfo.get(cancelinfo.YEAR);
	  
	  
	    pay = getPay(paycode,billmonth,billyear);
	    
		   
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
		
		totalh = totalh+eventhours;
 		   
    		unitcost = pay;
                extendedcost = eventhours*pay;
		totalcost = totalcost + extendedcost;
	  }	
	  System.out.println("Total hours "+ totalh);
   return totalh;
    }
    


   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public webscheduleSessionData getSessionData()
   {
       return this.mySessionData;
   }

   /**
    * Method to log the conflict list in to a session
    */
   public void setConflictlist(LinkedList theConflictlist)
       throws webschedulePresentationException
   {
       this.getSessionData().setConflictlist(theConflictlist);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public LinkedList getConflictlist()
   {
       return this.getSessionData().getConflictlist();
   }

   /**
    * Method to remove the current conflict list from the session
    */
   public void removeConflictlistFromSession()
   {
       this.getSessionData().removeConflictlist();
   }


   /**
    * Method to log the good list in to a session
    */
   public void setGoodlist(LinkedList theGoodlist)
       throws webschedulePresentationException
   {
       this.getSessionData().setGoodlist(theGoodlist);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public LinkedList getGoodlist()
   {
       return this.getSessionData().getGoodlist();
   }

   /**
    * Method to remove the current good list from the session
    */
   public void removeGoodlistFromSession()
   {
       this.getSessionData().removeGoodlist();
   }
   
   /**
    * Method to log a user in to a session
    */
   public void setUser(Person thePerson)
       throws webschedulePresentationException
   {
       this.getSessionData().setUser(thePerson);
   }
   
   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public Person getUser()
   {
       return this.getSessionData().getUser();
   }
      
   /**
    * Method to remove the current user from the session
    */
   public void removeUserFromSession()
   {
       this.getSessionData().removeUser();
   }


   
   /**/

   /**
    * Method to log a project in to a session
    */
   public void setProject(Project theProject)
       throws webschedulePresentationException
   {
       this.getSessionData().setProject(theProject);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public Project getProject()
   {
       return this.getSessionData().getProject();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeProjectFromSession()
   {
       this.getSessionData().removeProject();
   }



/**/

   /**
    * Method to log a operator in to a session
    */
   public void setOperator(Operator theOperator)
       throws webschedulePresentationException
   {
       this.getSessionData().setOperator(theOperator);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public Operator getOperator()
   {
       return this.getSessionData().getOperator();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeOperatorFromSession()
   {
       this.getSessionData().removeOperator();
   }




   /**
    * Method to log a project in to a session
    */
   public void setSwitchProject(Project theSwitchProject)
       throws webschedulePresentationException
   {
       this.getSessionData().setSwitchProject(theSwitchProject);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public Project getSwitchProject()
   {
       return this.getSessionData().getSwitchProject();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeSwitchProjectFromSession()
   {
       this.getSessionData().removeSwitchProject();
   }



   public void setYear(int theYear)
       throws webschedulePresentationException
   {
       this.getSessionData().setYear(theYear);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getYear()
   {
       return this.getSessionData().getYear();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeYearFromSession()
   {
       this.getSessionData().removeYear();
   }


   public void setMonth(int theMonth)
       throws webschedulePresentationException
   {
       this.getSessionData().setMonth(theMonth);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getMonth()
   {
       return this.getSessionData().getMonth();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeMonthFromSession()
   {
       this.getSessionData().removeMonth();
   }


   public void setDay(int theDay)
       throws webschedulePresentationException
   {
       this.getSessionData().setDay(theDay);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getDay()
   {
       return this.getSessionData().getDay();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeDayFromSession()
   {
       this.getSessionData().removeDay();
   }


 public void setbillsday(int thebillsday)
       throws webschedulePresentationException
   {
       this.getSessionData().setbillsday(thebillsday);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getbillsday()
   {
       return this.getSessionData().getbillsday();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removebillsdayFromSession()
   {
       this.getSessionData().removebillsday();
   }


public void setbilleday(int thebilleday)
       throws webschedulePresentationException
   {
       this.getSessionData().setbilleday(thebilleday);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getbilleday()
   {
       return this.getSessionData().getbilleday();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removebilledayFromSession()
   {
       this.getSessionData().removebilleday();
   }



/* session var for the annual report*/


 public void setbillsmonth(int thebillsmonth)
       throws webschedulePresentationException
   {
       this.getSessionData().setbillsmonth(thebillsmonth);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getbillsmonth()
   {
       return this.getSessionData().getbillsmonth();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removebillsmonthFromSession()
   {
       this.getSessionData().removebillsmonth();
   }


public void setbillemonth(int thebillemonth)
       throws webschedulePresentationException
   {
       this.getSessionData().setbillemonth(thebillemonth);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getbillemonth()
   {
       return this.getSessionData().getbillemonth();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removebillemonthFromSession()
   {
       this.getSessionData().removebillemonth();
   }




public void setbillmonth(int thebillmonth)
       throws webschedulePresentationException
   {
       this.getSessionData().setbillmonth(thebillmonth);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getbillmonth()
   {
       return this.getSessionData().getbillmonth();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removebillmonthFromSession()
   {
       this.getSessionData().removebillmonth();
   }


public void setbillyear(int thebillyear)
       throws webschedulePresentationException
   {
       this.getSessionData().setbillyear(thebillyear);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getbillyear()
   {
       return this.getSessionData().getbillyear();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removebillyearFromSession()
   {
       this.getSessionData().removebillyear();
   }

    public void setS_eventID(String theS_eventID)

   {
       this.getSessionData().setS_eventID(theS_eventID);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public String getProb_id()
   {
       return this.getSessionData().getProb_id();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeProb_idFromSession()
   {
       this.getSessionData().removeProb_id();
   }



 public void setProb_id(String theProb_id)

   {
       this.getSessionData().setProb_id(theProb_id);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public String getS_eventID()
   {
       return this.getSessionData().getS_eventID();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeS_eventIDFromSession()
   {
       this.getSessionData().removeS_eventID();
   }
   
    public void setbillprojID(String thebillprojID)

   {
       this.getSessionData().setbillprojID(thebillprojID);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public String getbillprojID()
   {
       return this.getSessionData().getbillprojID();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removebillprojIDFromSession()
   {
       this.getSessionData().removebillprojID();
   }

   
 public void setbilldescription(String thebilldescription)

   {
       this.getSessionData().setbilldescription(thebilldescription);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public String getbilldescription()
   {
       return this.getSessionData().getbilldescription();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removebilldescriptionFromSession()
   {
       this.getSessionData().removebilldescription();
   }

   
   
   public void setbillreportflag(String thebillreportflag)

   {
       this.getSessionData().setbillreportflag(thebillreportflag);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public String getbillreportflag()
   {
       return this.getSessionData().getbillreportflag();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removebillreportflagFromSession()
   {
       this.getSessionData().removebillreportflag();
   }

   
   
    public void setBlockflag (int theBlockflag)
       throws webschedulePresentationException
   {
       this.getSessionData().setBlockflag(theBlockflag);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getBlockflag()
   {
       return this.getSessionData().getBlockflag();
   }




 public void setBillableflag (int theBillableflag)
       throws webschedulePresentationException
   {
       this.getSessionData().setBillableflag(theBillableflag);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getBillableflag()
   {
       return this.getSessionData().getBillableflag();
   }


    public void setMyeventflag(int theMyeventflag)
       throws webschedulePresentationException
   {
       this.getSessionData().setMyeventflag(theMyeventflag);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getMyeventflag()
   {
       return this.getSessionData().getMyeventflag();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeMyeventflagFromSession()
   {
       this.getSessionData().removeMyeventflag();
   }


   public void setSummaryflag(int theSummaryflag)
       throws webschedulePresentationException
   {
       this.getSessionData().setSummaryflag(theSummaryflag);
   }

   /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public int getSummaryflag()
   {
       return this.getSessionData().getSummaryflag();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeSummaryflagFromSession()
   {
       this.getSessionData().removeSummaryflag();
   }



    
    /* 
    * added session var for proposal Mgmt
    */
    
    public void setPropFlag(String thepropflag)
    {
    this.getSessionData().setPropFlag(thepropflag);
    }
    
 /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public String getPropFlag()
   {
       return this.getSessionData().getPropFlag();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removePropFlagFromSession()
   {
       this.getSessionData().removePropFlagFromSession();
   }
   


 public void setProposalID(String theproposalID)
    {
    this.getSessionData().setProposalID(theproposalID);
    }
    
 /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public String getProposalID()
   {
       return this.getSessionData().getProposalID();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeProposalIDFromSession()
   {
       this.getSessionData().removeProposalIDFromSession();
   }



 public void setProjectID(String theprojectID)
    {
    this.getSessionData().setProjectID(theprojectID);
    }
    
 /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public String getProjectID()
   {
       return this.getSessionData().getProjectID();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removeProjectIDFromSession()
   {
       this.getSessionData().removeProjectIDFromSession();
   }



 public void setPiID(String thepiID)
    {
    this.getSessionData().setPiID(thepiID);
    }
    
 /**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public String getPiID()
   {
       return this.getSessionData().getPiID();
   }

   /**
    * Method to remove the current pi from the session
    */
   public void removePiIDFromSession()
   {
       this.getSessionData().removePiIDFromSession();
   }

/**
    * @return The saved comms objects
    * to whichever subclass needs it
    */
   public String getPbookmark()
   {
       return this.getSessionData().getPbookmark();
   }

   /**
    * Method to remove the current project from the session
    */
   public void removePbookmarkFromSession()
   {
       this.getSessionData().removePbookmark();
   }



 public void setPbookmark(String thePbookmark)

   {
       this.getSessionData().setPbookmark(thePbookmark);
   }



   /**
    * This implements the run method in HttpPresentation.
    */
   public void run(HttpPresentationComms comms)
       throws Exception
   {          
      // Initialize new or get the existing session data
      initSessionData(comms);
      // Check if the user needs to be logged in for this request.
      if(this.loggedInUserRequired()) {
        checkForUserLogin();
      }
      // Handle the incoming event request
      handleEvent(comms);
   }

   /**
    * Method to get or create the AgSessionData object from the user session
    * This object is saved in the EbrokerPresentation object
    */
   protected void initSessionData(HttpPresentationComms comms) 
       throws webschedulePresentationException
   {
        this.myComms = comms;
       
        try {
            Object obj = getComms().sessionData.get(webscheduleSessionData.SESSION_KEY);
            // If we found the session data, save it in a private data member
            if(null != obj) {
              this.mySessionData = (webscheduleSessionData)obj;
            } else {
              // If no session data was found, create a new session data instance
              this.mySessionData = new webscheduleSessionData();
              getComms().sessionData.set(webscheduleSessionData.SESSION_KEY, this.mySessionData);
            }
        } catch(KeywordValueException ex) {  
            writeDebugMsg("Problem getting session data from session: " +
                            ex.getMessage());
        }
   }         
   
   /**
    * Checks the session data for a User, if not there then redirects to the login page
    */
   protected void checkForUserLogin() 
       throws ClientPageRedirectException, webschedulePresentationException {
      try {
         Person user = getUser();
      
         // User wasn't in session data if the user is null.
         // This means that the user isn't logged in but we need the autologin
         // utility to check the persistent cookie for the user to see if we can auto-
         // log him in from the cookie.
         if (null == user) {
            writeDebugMsg("USER NOT FOUND IN SESSION");
            //send to LoginPage if a logged in user is required.   
            String requestedPO = getComms().request.getRequestURI();
            this.writeDebugMsg("PO: "+ requestedPO);
            // Call the subclass's implemented method
            writeDebugMsg("REDIRECTING TO LOGIN PAGE");
            throw new ClientPageRedirectException(LOGIN_PAGE);         
         } else {
            writeDebugMsg("USER ALREADY LOGGED IN WITH A SESSION");
         }
      } catch (Exception ex) {
         throw new webschedulePresentationException("Trouble checking for user login status", ex);
      }
   }

   /**
    * Method to call the proper method for the incoming event
    */
   public void handleEvent(HttpPresentationComms comms)
       throws Exception
   {
      String event = getComms().request.getParameter(EVENT);
      String returnHTML = null;
      
      if (event == null || event.length() == 0) {
         returnHTML = handleDefault();
      } 
      else {
         returnHTML = getPageContentForEvent(event);
      }
      
      getComms().response.writeHTML(returnHTML);    
   }
   
   /**
    * If an event parameter is defined then this invokes the method that
    * handles that event.
    */   
   public String getPageContentForEvent(String event) 
       throws Exception 
   {            
      try {
         Method method = this.getClass().getMethod(toMethodName(event), null);      
         String thePage = (String)method.invoke(this, null);
         return thePage;
      } catch(InvocationTargetException ex) {
          // Rethrow the originating exception if as it should be propagated as is
         // It could be a page redirect exception, etc.
         if (ex.getTargetException() instanceof Exception) {
               throw (Exception)ex.getTargetException();
            } else if (ex.getTargetException() instanceof Error) {
               throw (Error)ex.getTargetException();
            } else {
               throw ex;
            } 
      } catch(NoSuchMethodException ex) {
         //The method to handle the event does not exist.
         throw new webschedulePresentationException("NO EVENT HANDLER FOUND FOR EVENT: " +
                                      event, ex);           
      } catch(IllegalAccessException ex) {
         //The method to handle the event does not exist.
         throw new webschedulePresentationException("ILLEGAL ACCESS TO EVENT HANDLER (is it public?): " +
                                      event, ex);           
      } 
   }

   /**
    * This sets the first letter of the event parameter value in order
    * to adhere to Java method naming conventions.
    * 
    * @param String event the incoming name of the event
    * @return String the properly capitalized name
    */   
   private String toMethodName(String event) 
   {
      StringBuffer methodName = new StringBuffer(STANDARD_METHOD_PREFIX);
      methodName.append(Character.toUpperCase(event.charAt(0)));
      
      if (event.length() > 1) {
         methodName.append(event.substring(1));
      }
      
      return methodName.toString();   
   }         
          
   /**
    * Returns the application object associated with the
    * current request.
    *
    * @return the ebroker application object.
    */
   public Webschedule getApplication() {
      return (Webschedule)Enhydra.getApplication();
   }   
   
   /**
    * Method to write a debugging message to the debug log
    * channel when the DEBUG flag is turned on
    * 
    * @param msg The message to write to the DEBUG log channel
    */
   public static void writeDebugMsg(String msg) {
      Enhydra.getLogChannel().write(Logger.DEBUG,msg);
   }
}
