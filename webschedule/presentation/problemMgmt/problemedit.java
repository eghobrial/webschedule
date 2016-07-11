/**--------------------------------------------------------------
* Webschedule
*
* @class:problemdetails
* @version
* @author: Eman Ghobrial
* @since: June 2004
* added differnt format for message July 2007
* Nov 2007 fix timestamp format 
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
import webschedule.business.webscheduleBusinessException;
import webschedule.presentation.webschedulePresentationException;
import java.util.Calendar;
import java.util.Date;

import java.sql.Timestamp;

/**
 * status.java shows the status page
 *
 */
public class problemedit extends BasePO
{

 /**
  * Constants
  */
  String statuscode[] = {"closed","open","in progress"};
  String classcode[] = {"other","scanner","coils","physiological monitoring","stimulus presentation/response boxes","computer/networking"};
  String severitycode[] = {"non-critical","serious","critical"};
  
   private static String REPORTERNAME = "reportername";
    private static String REPORTEREMAIL = "reporteremail";
    private static String DESCRIB = "describ";
    private static String PROBLEMDETAIL = "problemdetail";
  
    private static String CLASSCODE = "classcode";
  
  
    private static String FIXDETAIL = "fixdetail";
    private static String STATUSCODE = "statuscode";
   private static String SEVERITYCODE = "severitycode";
  private static String GE_NOT = "ge_not";
   private static String PROB_NUM = "prob_num";
     private static String PROBLEM_ID = "Prob_id"; 

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
    
     public String handleShowedit()
        throws HttpPresentationException
    {
    	String prob_id = this.getComms().request.getParameter(PROBLEM_ID);
	System.out.println("problem id  =   "+prob_id);
	try
	{
            if (null == prob_id) {
            this.getSessionData().setUserMessage(prob_id + "  Please choose a valid report ");
                 throw new ClientPageRedirectException(UPDATESTATUS_PAGE);
                 // Show error message that project not found
	} else
	
	 if ((this.getUser().isAuth()) || (check_user(prob_id)!=1)){
		 
	  this.setProb_id(prob_id);
            throw new ClientPageRedirectException(PROBLEMEDIT_PAGE);
	    } else {
	    
	    this.getSessionData().setUserMessage("User not authorized to update status ");
                 throw new ClientPageRedirectException(UPDATESTATUS_PAGE);
	   
		
           }
	   
	    } catch (webscheduleBusinessException ex) {
         	throw new webschedulePresentationException("Error getting user login name", ex);
          }	
      }


 public String handleUpdate() 
	     throws HttpPresentationException 
    {
 String fixdetail = this.getComms().request.getParameter(FIXDETAIL);
  System.out.println("the fix detail off the update module"+fixdetail);
   String problemdetail = this.getComms().request.getParameter(PROBLEMDETAIL);
   System.out.println("the problem detail off the update module"+problemdetail);
  String statuscode = this.getComms().request.getParameter(STATUSCODE);
  System.out.println("statuscode"+statuscode);
String reportername = null;
	String reporteremail = null;
	String describ = null;

	/*String fixdetail = null;*/
	/*String problemdetail = null;*/
	int sevcode=0 ;
	int ccode=0;
	int postday=0;
	int postmonth=0;
	int postyear=0;
	int updateday=0;
	int updatemonth=0;
	int updateyear=0;
	int closeday=0;
	int closemonth=0;
	int closeyear=0;
	int scode = 0;
	Problem theProblem = null;

	
	scode =  Integer.parseInt(statuscode);
	System.out.println("statuscode off update"+Integer.toString(scode));
	
String prob_id = this.getProb_id();

//calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
	int todayhour = cancelinfo.get(cancelinfo.HOUR);
	int todaymin = cancelinfo.get(cancelinfo.MINUTE);
	int todaypm = cancelinfo.get(cancelinfo.AM_PM);
	String pms = null;
/* if problem is closed assign both last update and close date to today's date*/
if (scode==0)
 {
   updateday = todaydate;
   updatemonth = todaymonth;
   updateyear = todayyear;
  closeday = todaydate;
   closemonth = todaymonth;
   closeyear = todayyear;
   } else {
   closeday = 0;
   closemonth = 0;
   closeyear = 0;
   }
   
     if (todaypm == 0) 
     pms ="am";
  else
  	pms ="pm";  
	
long now = System.currentTimeMillis();
Timestamp ts = new Timestamp(now);

String tss = ts.toString();
   
String updatem = Integer.toString(todaymonth+1);
  String updated = Integer.toString(todaydate);
  String updatey = Integer.toString(todayyear); 
  String updateh = Integer.toString(todayhour);
  String updatemin = Integer.toString(todaymin);

	System.out.println("problem id  =   "+prob_id);
	 try {
	    	theProblem = ProblemFactory.findProblemByID(prob_id);
                		
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(prob_id + " Please choose a valid problem id");
	        } 
	
	
	
	  try {
                    theProblem.setReportername(theProblem.getReportername());
		    theProblem.setReporteremail(theProblem.getReporteremail());
	            theProblem.setDescrib(theProblem.getDescrib());
	            theProblem.setProblemdetail(theProblem.getProblemdetail());
//fixdetail=fixdetail+"\n"+" Updated "+this.getUser().getFirstname()+" "+getUser().getLastname()+" "+updatem+"-"+updated+"-"+updatey;
//fixdetail=fixdetail+" "+updateh+":"+updatemin+" "+pms;

fixdetail=fixdetail+"\n"+" Updated "+this.getUser().getFirstname()+" "+getUser().getLastname()+" "+tss;
	  
		
	            theProblem.setFixdetail(fixdetail);
		    theProblem.setStatuscode(scode);
		    theProblem.setSeveritycode(theProblem.getSeveritycode());
		    theProblem.setClasscode(theProblem.getClasscode());
		    theProblem.setPrioritycode(0);
                    theProblem.setPostday(theProblem.getPostday());  
		    theProblem.setPostmonth(theProblem.getPostmonth());
		    theProblem.setPostyear(theProblem.getPostyear()); 
		    theProblem.setUpdateday(updateday);
	            theProblem.setUpdatemonth(updatemonth);
		    theProblem.setUpdateyear(updateyear);
		    theProblem.setCloseday(closeday);
	            theProblem.setClosemonth(closemonth);
		    theProblem.setCloseyear(closeyear);
			theProblem.setOwner(this.getUser());	
		
	            //Add the problem to the database.
	            theProblem.save();
		    send_notification();
                this.getSessionData().setUserMessage(
                   "The problem titled, " + theProblem.getDescrib() + ", has been updated, please notify users with outcome");
	            throw new ClientPageRedirectException(NOTIFY_PAGE);
	       
	       
        } catch(webscheduleBusinessException ex) {
            throw new webschedulePresentationException("Exception logging in user: ", ex);
        }
	
}	
	
	
protected void  send_notification()
   throws HttpPresentationException
   {
String reportername = null;
	String reporteremail = null;
	String describ = null;
	String fixdetail = null;
	String problemdetail = null;
	int sevcode=0 ;
	int ccode=0;
	int scode=0;
	Problem theProblem = null;
	
String prob_id = this.getProb_id();

	
 try {
	    	theProblem = ProblemFactory.findProblemByID(prob_id);
		 reportername = theProblem.getReportername();
		reporteremail = theProblem.getReporteremail();
		describ = theProblem.getDescrib();
		fixdetail = theProblem.getFixdetail();
		problemdetail = theProblem.getProblemdetail();
		sevcode = theProblem.getSeveritycode();
		ccode = theProblem.getClasscode();
		scode = theProblem.getStatuscode();
                		
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(prob_id + " Please choose a valid problem id");
	        } 
		
	
	try {
    	    SendMail sch_not;	
    	    String from = "websch_admin";
    	    String to = reporteremail+",prob_report";
    	    String subject = "3TW Problem report updated **"+describ+"**";
    	   
   String[] message = {"The following problem report has been updated by "+this.getUser().getFirstname()+" "+this.getUser().getLastname()+"\n",
   
   "System: 3T West  \n",
   "Description: "+describ + "\n",
   "Severity: "+severitycode[sevcode]+"\n",
   "Classification: "+classcode[ccode]+"\n",
   "Status: "+statuscode[scode]+"\n",
   "\n",
   "Details:             \n"+problemdetail,
	 			"\n",
				"Progress/Fix details:		"+fixdetail,
    	    			" \n"}  ;
    	  		
           sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }

    }



  public int check_user(String prob_id)
      throws HttpPresentationException, webschedulePresentationException
     {

           
           String user_event = null;
           String current_user = null;
           	
    try {
	    Problem problem = ProblemFactory.findProblemByID(prob_id);

            
            user_event = problem.getOwnerLogin();
              System.out.println("login user-"+user_event+"-");
            // Catch any possible database exception as well as the null pointer
            //  exception if the s_event is not found and is null after findS_eventByID
	    } catch(Exception ex) {
            this.getSessionData().setUserMessage(prob_id + " Please choose a valid event");
        }

          try
          {
    		current_user = this.getUser().getLogin();
    		System.out.println("current user-"+current_user+"-");
    	  } catch (webscheduleBusinessException ex) {
         	throw new webschedulePresentationException("Error getting user login name", ex);
          }

	if (current_user.equals(user_event))
		{
		 System.out.println("current user matches");
		return 0;
		
		}
	else 	
		{
		 System.out.println("current user does not match");
        	return 1;
        	}
	}

    
     /**
     show problem edit page
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
    
        problemeditHTML page = new problemeditHTML();
	String reportername = this.getComms().request.getParameter(REPORTERNAME);
	String reporteremail =this.getComms().request.getParameter(REPORTEREMAIL);
	String describ = this.getComms().request.getParameter(DESCRIB);
	String problemdetail = this.getComms().request.getParameter(PROBLEMDETAIL);
	String severityc = this.getComms().request.getParameter(SEVERITYCODE);
	String classc = this.getComms().request.getParameter(CLASSCODE);
	String ge_not = this.getComms().request.getParameter(GE_NOT);
	String prob_num = this.getComms().request.getParameter(PROB_NUM);
	int ccode=0;
	String fixdetail = this.getComms().request.getParameter(FIXDETAIL);
 	String statuscode = this.getComms().request.getParameter(STATUSCODE);
 
/* Node fixdetailText ;
 Node problemdetailText ;*/
 
 	HTMLSelectElement	sc_select,st_select,cc_select;
HTMLCollection		sc_selectCollection,st_selectCollection,cc_selectCollection;
HTMLOptionElement	sc_option, st_option, cc_option;
	String		sc_optionName,st_optionName,cc_optionName;
	
	
	sc_select = (HTMLSelectElement)page.getElementSeveritycode();
	sc_selectCollection = sc_select.getOptions();
	
	st_select = (HTMLSelectElement)page.getElementStatuscode();
	st_selectCollection = st_select.getOptions();
	
	cc_select = (HTMLSelectElement)page.getElementClasscode();
	cc_selectCollection = cc_select.getOptions();
	


//or use replaceChild(commentsText)
 
  System.out.println("the fix detail off the show page module"+fixdetail);
  System.out.println("the problem detail off the show page module"+problemdetail);
  System.out.println("statuscode of show page"+statuscode);
  
		
	String prob_id = this.getProb_id();
	System.out.println("problem id  =   "+prob_id);
	
	

    //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }
	
	 try {
	    	Problem theProblem = ProblemFactory.findProblemByID(prob_id);
             
	     
	      if(null != reportername && reportername.length() != 0) {
                page.getElementReportername().setValue(reportername);
            } else {
                page.getElementReportername().setValue(theProblem.getReportername());
            }
	    
	  if(null != reporteremail && reporteremail.length() != 0) {
                page.getElementReporteremail().setValue(reporteremail);
            } else {
               
		page.getElementReporteremail().setValue(theProblem.getReporteremail());
            }
  	if(null != describ && describ.length() != 0) {
                page.getElementDescrib().setValue(describ);
            } else {
               
	page.getElementDescrib().setValue(theProblem.getDescrib());
            }
	    
 if(null != prob_num && prob_num.length() != 0) {
                page.getElementProbnum().setValue(prob_num);
            } else {
               	page.getElementProbnum().setValue(theProblem.getProblemnum());
            }
	    
	    
	    if(null != ge_not && ge_not.length() != 0) {
                page.getElementGe_not().setChecked(true);
            } else {
               	page.getElementGe_not().setChecked(theProblem.GE_called());
            } 
	    
	    
if(null != problemdetail && problemdetail.length() != 0) {
   Node    problemdetailText = page.getElementProblemdetail().getOwnerDocument().createTextNode(problemdetail);   
      page.getElementProblemdetail().appendChild(problemdetailText);
            } else {
      Node  problemdetailText = page.getElementProblemdetail().getOwnerDocument().createTextNode(theProblem.getProblemdetail());
	page.getElementProblemdetail().appendChild(problemdetailText);
	    }
 

	  if(null != fixdetail && fixdetail.length() != 0) {
         Node fixdetailText = page.getElementFixdetail().getOwnerDocument().createTextNode(fixdetail);   
	  page.getElementFixdetail().appendChild(fixdetailText); 
            } else {
            Node fixdetailText = page.getElementFixdetail().getOwnerDocument().createTextNode(theProblem.getFixdetail());
	    page.getElementFixdetail().appendChild(fixdetailText); 
	    }
  



/*int cc = theProblem.getClasscode();
String ccs = Integer.toString(cc);
page.getElementClasscode().setValue(ccs);*/

  /*
	if(null != statuscode && statuscode.length() != 0) {
                page.getElementStatuscode().setValue(statuscode);
            } else {
               

page.getElementStatuscode().setValue(Integer.toString(theProblem.getStatuscode()));
            }

	if(null != classc && classc.length() != 0) {
                page.getElementClasscode().setValue(classc);
            } else {
               

page.getElementClasscode().setValue(Integer.toString(theProblem.getClasscode()));
            }

	*/
	
	
	int sc_optionlen = sc_selectCollection.getLength();
	for (int i=0; i< sc_optionlen; i++) {
	  sc_option = (HTMLOptionElement)sc_selectCollection.item(i);
	  sc_optionName = sc_option.getValue();
	  if (sc_optionName.equals(Integer.toString(theProblem.getSeveritycode())))
	     sc_option.setSelected(true);
	  else
	     sc_option.setSelected(false);
	    } 
		
	int cc_optionlen = cc_selectCollection.getLength();
	for (int i=0; i< cc_optionlen; i++) {
	  cc_option = (HTMLOptionElement)cc_selectCollection.item(i);
	  cc_optionName = cc_option.getValue();
	  if (cc_optionName.equals(Integer.toString(theProblem.getClasscode())))
	     cc_option.setSelected(true);
	  else
	     cc_option.setSelected(false);
	    } 
	
	
	int st_optionlen = st_selectCollection.getLength();
	for (int i=0; i< st_optionlen; i++) {
	  st_option = (HTMLOptionElement)st_selectCollection.item(i);
	  st_optionName = st_option.getValue();
	  if (st_optionName.equals(Integer.toString(theProblem.getStatuscode())))
	     st_option.setSelected(true);
	  else
	     st_option.setSelected(false);
	    } 
	    
	    
	
	  } catch(webscheduleBusinessException ex) {
     throw new webschedulePresentationException("Error populating page for problem editing", ex);
        }
	
    
	   return page.toDocument();
    }
}

	
