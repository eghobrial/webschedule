/**--------------------------------------------------------------
* Webschedule
*
* @class:problemdetails
* @version
* @author: Eman Ghobrial
* @since: June 2004
*
*--------------------------------------------------------------*/

package webschedule.presentation.problemMgmt;

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

/**
 * status.java shows the status page
 *
 */
public class problemdetails extends BasePO
{

 /**
  * Constants
  */
  String statuscode[] = {"closed","open","in progress"};
  String classcode[] = {"other","scanner","coils","physiological monitoring","stimulus presentation/response boxes","computer/networking"};
  String severitycode[] = {"non-critical","serious","critical"};
   
  
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
    
     public String handleShowdetails()
        throws HttpPresentationException
    {
    	String prob_id = this.getComms().request.getParameter(PROBLEM_ID);
	System.out.println("problem id  =   "+prob_id);
            if (null == prob_id) {
            this.getSessionData().setUserMessage(prob_id + "  Please choose a valid report ");
                 throw new ClientPageRedirectException(STATUS_PAGE);
                 // Show error message that project not found
            } else {
	    this.setProb_id(prob_id);
            	
	  throw new ClientPageRedirectException(PROBLEMDETAILS_PAGE);
		
           }
      }
    
     /**
     stat
     */
    public String showPage(String errorMsg)
    {
        problemdetailsHTML page = new problemdetailsHTML();
	String reportername = null;
	String reporteremail = null;
	String describ = null;
	String fixdetail = null;
	String problemdetail = null;
	int sevcode=0 ;
	int ccode=0;
	int scode=0;
	
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
			
		page.setTextReportername(reportername);
		page.setTextReporteremail(reporteremail);
		page.setTextDescrib(describ);
		page.setTextSeveritycode(severitycode[sevcode]);
		page.setTextClasscode(classcode[ccode]);
		page.setTextStatuscode(statuscode[scode]);
		page.setTextProblemdetail(problemdetail);
		page.setTextFixdetail(fixdetail);
	    return page.toDocument();
    }
}

	
