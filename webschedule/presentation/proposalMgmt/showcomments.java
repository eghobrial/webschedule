/**--------------------------------------------------------------
* Webschedule
*
* @class:showcomments
* @version
* @author: Eman Ghobrial
* @since: Dec 2007
*
*--------------------------------------------------------------*/

package webschedule.presentation.proposalMgmt;

import webschedule.business.proposal.*;
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

import java.util.*;
import webschedule.SendMail;
import java.lang.String;

/**
 * manageproposal.java shows the Proposal Menu Options
 *
 */
public class showcomments extends BasePO
{
  private static String PROJ_ID = "proj_id";
   private static String RESPONSE = "response";
 //  private static String COMMENTS = "comments";
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


 public String handleShowcomments()
        throws HttpPresentationException, webschedulePresentationException
    {
    	String proj_id = this.getComms().request.getParameter(PROJ_ID);
	System.out.println("Handle Show Comments visited  ");
	System.out.println("problem id  =   "+proj_id);
	try
	{
            if (null == proj_id) {
            this.getSessionData().setUserMessage(proj_id + "  Please choose a valid proposal ");
                 throw new ClientPageRedirectException(PROPOSALSTATUS_PAGE);
                 // Show error message that project not found
	} else{
		 
	  this.setProposalID(proj_id);
            throw new ClientPageRedirectException(SHOWCOMMENTS_PAGE);
	     	}   
	    } catch (Exception ex) {
         	throw new webschedulePresentationException("Error getting user login name", ex);
          }	
      }


    /**
     *
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
        showcommentsHTML page = new showcommentsHTML();
	int today ,month ,year;
	String	aproj_name,comments ;
	int	status ;
	String cdate;
	String responsedb = null;
String statuscode[] = {"Editable","Submitted","Internal Comments","Committe Comments","PI Responded","Approved","Checked","Accounts Added"};
  String response = this.getComms().request.getParameter(RESPONSE);
       //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
    /*   	 try {
	        page.setTextOwner(this.getUser().getFirstname() + " " +
	    		              this.getUser().getLastname() );
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error getting user info: " + ex);
	    }
	    */
        
      //  String errorMsg = this.getSessionData().getAndClearUserMessage();
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	
Proposal theProposal;	  
    String proposalID = this.getProposalID();
	
		
	  try {
            theProposal = ProposalFactory.findProposalByID(proposalID);
	    comments = theProposal.getComments();
	    responsedb = theProposal.getResponse();
	    
	      } catch(webscheduleBusinessException ex) {
           // this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
	
	if (comments == null)
	 page.setTextComments("No comments on file");
        else	
	 page.setTextComments(comments);    
    
    if (responsedb  == null)
  responsedb = "*";
  

if(null != response && response.length() != 0) {
	Node    responseText = page.getElementResponse().getOwnerDocument().createTextNode(response);  
                page.getElementResponse().appendChild(responseText);
            } else {
	  Node    responseText = page.getElementResponse().getOwnerDocument().createTextNode(responsedb);  
                page.getElementResponse().appendChild(responseText);  
            }
	    
    
	    // write out HTML
	    return page.toDocument();
    }
    
    
    
     /*
     * Updates the proposal appending operator information
     *
     */
    public String handleRespond() 
        throws HttpPresentationException, webschedulePresentationException
    {      
  
	Proposal theproposal = null;
	
	
	String proposalID = this.getProposalID();
	
		
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	   
            updateProposal(theproposal);
	        sendmail(theproposal);
		
               	throw new ClientPageRedirectException(PROPOSALSTATUS_PAGE);
               
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
     }
    
    

    
      protected void updateProposal(Proposal theProposal)
        throws HttpPresentationException, webschedulePresentationException
    {  
      String response = this.getComms().request.getParameter(RESPONSE);
	
	
	
	
	
  try {
     theProposal.setOwner(this.getUser());
     theProposal.setToday(theProposal.getToday());
     theProposal.setMonth(theProposal.getMonth());
     theProposal.setYear(theProposal.getYear());
     theProposal.setIsucsd(theProposal.Isucsd());
     theProposal.setIsanimal(theProposal.Isanimal());
     theProposal.setIssample(theProposal.Issample());
     theProposal.setProj_name(theProposal.getProj_name());
    theProposal.setAproj_name(theProposal.getBproj_name());
    theProposal.setCname(theProposal.getCname());
    theProposal.setCphone(theProposal.getCphone());
    theProposal.setCemail(theProposal.getCemail());
    theProposal.setCntrOp(theProposal.CntrOp());
    
    theProposal.setStatus(4);
    
    theProposal.setResponse(response);
   
            	   
    System.out.println(" trying to saving a proposal two ");
	        theProposal.save();	
    System.out.println(" trying to saving a proposal three ");
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding proposal", ex);
        }    
	
	}
    
    
    
   protected void sendmail (Proposal theProposal)
        throws HttpPresentationException, webschedulePresentationException
    {  
 String pifname, pilname, to, proposalid, proj_name, aproj_name,subject,from,comments,respond;
String [] message;
String pbookmark;
 int	status ;
	

String statuscode[] = {"Editable","Submitted","Internal Comments","Committe Comments","PI Responded","Approved","Checked","Accounts Added"};

 from = "appsadmin";
 
 
 
 to = "eghobrial@ucsd.edu";
  to = to +",committee_3t";
     try {
     pifname = theProposal.getUserFirstName();
     pilname = theProposal.getUserLastName();
   //  to = theProposal.getUserEmail();
     proposalid = theProposal.getHandle();
     proj_name = theProposal.getProj_name();
     aproj_name = theProposal.getBproj_name();
     comments = theProposal.getComments();
     status = theProposal.getStatus();
     respond = theProposal.getResponse();
      } catch(Exception ex) {
        throw new webschedulePresentationException("Cannot retrieve proposal info", ex);    
        }
	
subject = "Proposal "+aproj_name+" PI respond to Committee Comments - 3TW scanner";


//if (pbookmark.equals("humanns"))
 //{

String[] msgTextns = {"Dear Committee Memebers, ",
			"\n",
"This in regards to "+proj_name+"\" (ID number: "+proposalid+") :\n",
"  The committee had the following comments:\n",
		      "  "+ comments+"\n",
		      "\n",
	"The PI posted the following respond:\n",
 "  "+ respond+"\n",
		      "\n",
	    "Thank you,\n",
		    "\n",
		    ""};	
  message = msgTextns;

		    
		    
      try {
    	    SendMail sch_not;	

 sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }
}

      
    
    
}


