
/**--------------------------------------------------------------
* Webschedule
*
* @class:problemreport
* @version
* @author: Eman Ghobrial
* @since: June 2004
* added differnt format for message July 2007
* Nov 2007 fix timestamp format 
* added Link to the notify page when one of the auth users report the
* problem
*--------------------------------------------------------------*/


package webschedule.presentation.problemMgmt;


import webschedule.SendMail;
import webschedule.business.person.*;
import webschedule.business.problem.*;
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
import java.util.Calendar;
import java.util.Date;

import java.sql.Timestamp;

/**
 * problemreport.java handles the problem reporting functionality 
 *  of the webschedule app.
 *
 */
public class problemreport extends BasePO 
{    
    /**
     * Constants
     */
    private static String REPORTERNAME = "reportername";
    private static String REPORTEREMAIL = "reporteremail";
    private static String DESCRIB = "describ";
    private static String PROBLEMDETAIL = "problemdetail";
    private static String SEVERITYCODE = "severitycode";
    private static String CLASSCODE = "classcode";
    private static String GE_NOT = "ge_not";
    private static String PROBNUM = "probnum";
  
     String statuscode[] = {"closed","open","in progress"};
  String classcode[] = {"other","scanner","coils","physiological monitoring","stimulus presentation/response boxes","computer/networking"};
  String severitycode[] = {"noncritical","serious","critical"};
  

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
     *  process problem data, save fields to ProblemManager if correct, 
     *  otherwise output results
     *  save error results, 
     *  control PO flow
     */
    public String handleReport() 
	     throws HttpPresentationException 
    {
	    String reportername = this.getComms().request.getParameter(REPORTERNAME);
	    String reporteremail =   this.getComms().request.getParameter(REPORTEREMAIL);
	    String describ = this.getComms().request.getParameter(DESCRIB);
	    String problemdetail = this.getComms().request.getParameter(PROBLEMDETAIL);;
	    String severityc = this.getComms().request.getParameter(SEVERITYCODE);
	    String classc = this.getComms().request.getParameter(CLASSCODE);
 	String ge_not = this.getComms().request.getParameter(GE_NOT);
	     String probnum = this.getComms().request.getParameter(PROBNUM);
	    
System.out.println("reporter name"+reportername);  	    	    
	    
	    int scode =  Integer.parseInt(severityc);
	    int ccode =  Integer.parseInt(classc);

System.out.println("Severity code "+Integer.toString(scode));

	//calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
	int todayhour = cancelinfo.get(cancelinfo.HOUR);
	int todaymin = cancelinfo.get(cancelinfo.MINUTE);
	int todaypm = cancelinfo.get(cancelinfo.AM_PM);
//System.out.println("Today date"+Integer.toString(todaydate));
//System.out.println("Today month"+Integer.toString(todaymonth));
//System.out.println("Today hour"+Integer.toString(todayhour));
//System.out.println("Today ***AM_PM***"+Integer.toString(todaypm));
 String pms = null;
 
  if (todaypm == 0) 
     pms ="am";
  else
  	pms ="pm";   

todaymonth = todaymonth + 1;	

long now = System.currentTimeMillis();
Timestamp ts = new Timestamp(now);

String tss = ts.toString();

    
	    // if login or password field is empty, generate error and redirect to this PO
	    if (reportername.length() == 0 || reporteremail.length() ==0 || 
	        describ.length() == 0 || problemdetail.length() == 0 ) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }        
        
//problemdetail = problemdetail+" Reported By: "+reportername+", "+reporteremail+", "+ todaymonth+"-"+todaydate+"-"+todayyear+"-"+todayhour+":"+todaymin+" "+pms;       	
problemdetail = problemdetail+" Reported By: "+reportername+", "+reporteremail+", "+tss;

        try {
                   Problem theproblem = new Problem();
	            theproblem.setReportername(reportername);
		    theproblem.setReporteremail(reporteremail);
	            theproblem.setDescrib(describ);
	            theproblem.setProblemdetail(problemdetail);
	            theproblem.setFixdetail(" ");
		     theproblem.setStatuscode(1);
		    theproblem.setSeveritycode(scode);
		    theproblem.setClasscode(ccode);
		    theproblem.setPrioritycode(0);
                    theproblem.setPostday(todaydate);  
		    theproblem.setPostmonth(todaymonth);
		    theproblem.setPostyear(todayyear);
		    theproblem.setPosth(todayhour);
		    theproblem.setPostm(todaymin);
		    theproblem.setPostpm(pms);
		    theproblem.setUpdateday(0);
	            theproblem.setUpdatemonth(0);
		    theproblem.setUpdateyear(0);
		    theproblem.setCloseday(0);
	            theproblem.setClosemonth(0);
		    theproblem.setCloseyear(0);
		 theproblem.setCloseyear(0);
	 if(null != this.getComms().request.getParameter(GE_NOT)) {
                	theproblem.setGE_called(true);
            	    } else {
                	theproblem.setGE_called(false);
            	     }
		     theproblem.setProblemnum(probnum);
			theproblem.setOwner(this.getUser());	
		send_notification();
	            //Add the problem to the database.
	            theproblem.save();
		   
		  try 
	{
	 if ((this.getUser().isAuth())){
this.getSessionData().setUserMessage("Notify PIs if needed please fill in period ");
            throw new ClientPageRedirectException(NOTIFY_PAGE);
	            }
		    
	 } catch (webscheduleBusinessException ex) {
         	throw new webschedulePresentationException("Error getting user is auth attribute", ex);
          }   
		   
		   
		    
                this.getSessionData().setUserMessage(
                     "Your problem titled, " + theproblem.getDescrib() + ", has been filed");
	            throw new ClientPageRedirectException(THANKYOU_PAGE);
	       
        } catch(webscheduleBusinessException ex) {
            throw new webschedulePresentationException("Exception logging in user: ", ex);
        }
    }
    
    
    protected void  send_notification()
   throws HttpPresentationException
   {
 String reportername = this.getComms().request.getParameter(REPORTERNAME);
	    String reporteremail =   this.getComms().request.getParameter(REPORTEREMAIL);
	    String describ = this.getComms().request.getParameter(DESCRIB);
	    String problemdetail = this.getComms().request.getParameter(PROBLEMDETAIL);;
	    String severityc = this.getComms().request.getParameter(SEVERITYCODE);
	    String classc = this.getComms().request.getParameter(CLASSCODE);
 	String ge_not = this.getComms().request.getParameter(GE_NOT);
	     String probnum = this.getComms().request.getParameter(PROBNUM);
	 int scode =  Integer.parseInt(severityc);
	    int ccode =  Integer.parseInt(classc);    



//calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
	int todayhour = cancelinfo.get(cancelinfo.HOUR);
	int todaymin = cancelinfo.get(cancelinfo.MINUTE);
	int todaypm = cancelinfo.get(cancelinfo.AM_PM);
System.out.println("Today date"+Integer.toString(todaydate));
System.out.println("Today month"+Integer.toString(todaymonth));
System.out.println("Today hour"+Integer.toString(todayhour));
System.out.println("Today ***AM_PM***"+Integer.toString(todaypm));
 String pms = null;
 
  if (todaypm == 0) 
     pms ="am";
  else
  	pms ="pm";  

todaymonth = todaymonth + 1;	

long now = System.currentTimeMillis();
Timestamp ts = new Timestamp(now);

String tss = ts.toString();
 

   try {
    	    SendMail sch_not;	
    	    String from = reporteremail;
    	    String to = reporteremail+",emang,prob_report";
//problemdetail = problemdetail+" Reported By: "+reportername+", "+reporteremail+", "+ todaymonth+"-"+todaydate+"-"+todayyear+"-"+todayhour+":"+todaymin+" "+pms;	    
problemdetail = problemdetail+" Reported By: "+reportername+", "+reporteremail+", "+tss;
   String subject="3TW Problem report **"+describ+"** which is "+classcode[ccode]+" related and is "+severitycode[scode];
    	   
 String[] message = {"The following problem has been reported by "+this.getUser().getFirstname()+" "+this.getUser().getLastname()+"\n",

   "System: 3T West  \n",
   "Description: "+describ + "\n",
   "Severity: "+severitycode[scode]+"\n",
   "Classification: "+classcode[ccode]+"\n",
   "Status: "+statuscode[1]+"\n",
   "\n",
    	    			"Details:             "+problemdetail,
    	    			" ",
				
    	    			" "}  ;
    	  		
           sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }

    }
   
    
    
    /** 
     *  produce HTML for this PO
     */
    public String showPage(String errorMsg)
        throws HttpPresentationException 
    {
	    // instantiate XMLC object
	   problemreportHTML page = new problemreportHTML();
	
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
        if(null != this.getComms().request.getParameter(DESCRIB)) {
            page.getElementDescrib().setValue(this.getComms().request.getParameter(DESCRIB));
        }

         if(null != this.getComms().request.getParameter(PROBLEMDETAIL)) {
            page.getElementProblemdetail().setValue(this.getComms().request.getParameter(PROBLEMDETAIL));
        }
         if(null != this.getComms().request.getParameter(SEVERITYCODE)) {
            page.getElementSeveritycode().setValue(this.getComms().request.getParameter(SEVERITYCODE));
        }

         if(null != this.getComms().request.getParameter(CLASSCODE)) {
            page.getElementClasscode().setValue(this.getComms().request.getParameter(CLASSCODE));
        }
	
	 if(null != this.getComms().request.getParameter(GE_NOT)) {
                	 page.getElementGe_not().setChecked(true);
            	    } else {
                	 page.getElementGe_not().setChecked(false);
            	     }
		     
	  if(null != this.getComms().request.getParameter(PROBNUM)) {
            page.getElementProbnum().setValue(this.getComms().request.getParameter(PROBNUM));
        }	     
         
        return page.toDocument();
    }
}
