/**--------------------------------------------------------------
* Webschedule
*
* @class:showrescomments
* @version
* @author: Eman Ghobrial
* @since: Dec 2007
*
*--------------------------------------------------------------*/

package webschedule.presentation.proposalMgmt;

import webschedule.business.proposal.*;
import webschedule.business.person.*;
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
public class showrescomments extends BasePO
{
 private static String NOTES = "notes";
  private static String PROJ_ID = "proj_id";
//   private static String RESPONSE = "response";
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


 public String handleShowcomresp()
        throws HttpPresentationException, webschedulePresentationException
    {
    	String proj_id = this.getComms().request.getParameter(PROJ_ID);
	System.out.println("Handle Show Comments visited  ");
	System.out.println("problem id  =   "+proj_id);
	try
	{
            if (null == proj_id) {
            this.getSessionData().setUserMessage(proj_id + "  Please choose a valid proposal ");
                 throw new ClientPageRedirectException(APROPOSALLOG_PAGE);
                 // Show error message that project not found
	} else{
		 
	  this.setProposalID(proj_id);
            throw new ClientPageRedirectException(SHOWRESCOMMENTS_PAGE);
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
        showrescommentsHTML page = new showrescommentsHTML();
	int today ,month ,year;
	String	aproj_name,comments ;
	int	status ;
	String cdate;
	String responsedb = null;
String statuscode[] = {"Editable","Submitted","Internal Comments","Committe Comments","PI Responded","Approved","Checked","Accounts Added"};
 String notes = this.getComms().request.getParameter(NOTES);
 
//  String rg = this.getComms().request.getParameter(RESPONSE);
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
	comments = "*";
  

if(null != notes && notes.length() != 0) {
	Node    notesText = page.getElementNotes().getOwnerDocument().createTextNode(notes);  
                page.getElementNotes().appendChild(notesText);
            } else {
	  Node    notesText = page.getElementNotes().getOwnerDocument().createTextNode(comments);  
                page.getElementNotes().appendChild(notesText);  
            }
	  

	
	
	/*if (comments == null)
	 page.setTextComments("No comments on file");
        else	
	 page.setTextComments(comments);    */
    
    if (responsedb  == null)
  page.setTextResponse("No comments on file");
        else	
	 page.setTextResponse(responsedb); 
  


	    
    
	    // write out HTML
	    return page.toDocument();
    }
    
     /*
     * Updates the proposal appending STATUS information
     *
     */
    public String handleScomments() 
        throws HttpPresentationException, webschedulePresentationException
    {      
   String notes = this.getComms().request.getParameter(NOTES);
  
  String proj_id = this.getProposalID();
 
   
    Proposal theproposal ;
    int status = 0;
    
	System.out.println("Handle send comments visited  ");
	System.out.println("problem id  =   "+proj_id);
	
	/*try {
	
	notes = notes +" Added by "+this.getUser().getFirstname()+" "+this.getUser().getLastname();
	
	 } catch(Exception ex) {
            throw new webschedulePresentationException("Error getting PI information", ex);
        }
	*/
	
	try
	{
            if (null == proj_id) {
            this.getSessionData().setUserMessage(proj_id + "  Please choose a valid proposal ");
                 throw new ClientPageRedirectException(APROPOSALLOG_PAGE);
                 // Show error message that project not found
	} else{
		 
//	  this.setProposalID(proj_id);
	 
	 
	  try {
	   
            theproposal = ProposalFactory.findProposalByID(proj_id);
	    status = theproposal.getStatus();
	     } catch(Exception ex) {
            throw new webschedulePresentationException("Error FINDing proposal", ex);
         
	} 
	   if (status == 5) {
	     // project already approved
	     this.getSessionData().setUserMessage(proj_id + "  Already approved ");
	      throw new ClientPageRedirectException(APROPOSALLOG_PAGE);
	      }
	     else {
	    updateProposalStatus(theproposal, notes);
	    // send an email to use that proposal has been submitted for review
	    sendemail (theproposal);
	    
            throw new ClientPageRedirectException(APROPOSALLOG_PAGE);
	    } 	
	    }
        } catch(Exception ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        
     }
}


 
   protected void updateProposalStatus(Proposal theProposal, String notes)
        throws HttpPresentationException, webschedulePresentationException
    {  
      

        String personID;
    
         try     { 	
		 
		  personID = theProposal.getUserID();
		
	   } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Project information", ex);
        }    
    
      
     try {
      theProposal.setOwner(PersonFactory.findPersonByID(personID));
      
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
    
      if (theProposal.CntrOp())
	 {
	  theProposal.setOp1lastname("N/A");
    theProposal.setOp1firstname("N/A");
    theProposal.setOp1phone("N/A");
    theProposal.setOp1email("N/A");
	   } else {
	    theProposal.setOp1lastname(theProposal.getOp1lastname());
    theProposal.setOp1firstname(theProposal.getOp1firstname());
    theProposal.setOp1phone(theProposal.getOp1phone());
    theProposal.setOp1email(theProposal.getOp1email());
	      } 
    
   if (theProposal.Isucsd())
   {
       theProposal.setIndexnum(theProposal.getIndexnum());
    } else {
       theProposal.setIndexnum("N/A");
       }
    theProposal.setBaline1(theProposal.getBaline1());
    theProposal.setBaline2(theProposal.getBaline2());
    theProposal.setBaline3(theProposal.getBaline3());
    theProposal.setBacity(theProposal.getBacity());
    theProposal.setBast(theProposal.getBast());
    theProposal.setBazip(theProposal.getBazip());
    theProposal.setFpname(theProposal.getFpname());
    theProposal.setFpphone(theProposal.getFpphone());
    theProposal.setFpemail(theProposal.getFpemail()); 
    theProposal.setThours(theProposal.getThours());
    theProposal.setStdate(theProposal.getStdate());
    theProposal.setExpdate(theProposal.getExpdate());
    theProposal.setWriteup(theProposal.getWriteup());
    theProposal.setBioHazard(theProposal.getBioHazard());
    theProposal.setDataanalysis(theProposal.getDataanalysis());
    theProposal.setRFCoils(theProposal.RFCoils());
    theProposal.setRestraints(theProposal.Restraints());
    theProposal.setPhysioeq(theProposal.Physioeq());
            	 theProposal.setAnesthetics(theProposal.anesthetics());
            	    theProposal.setAncillary(theProposal.ancillary());
            	   theProposal.setStimuli(theProposal.Stimuli());
            	theProposal.setContrast(theProposal.Contrast());
               	theProposal.setIACUCFaxed(theProposal.IACUCFaxed());
  // 	theProposal.setStatus(theProposal.getStatus());
  theProposal.setStatus(3);
  
	theProposal.setComments(notes);
            	   
	//String idts = iacucdate + " 00:00:00.00";	     
//    theProposal.setIACUCDate(Date.valueOf(idts));
 //   String adts = atransfer + " 00:00:00.00";
   // theProposal.setAnimTransDate(Date.valueOf(adts));
    
    System.out.println(" trying to saving a proposal two ");
	        theProposal.save();	
    System.out.println(" trying to saving a proposal three ");
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding proposal", ex);
        }    
    
    
    }
 
    
      protected void sendemail (Proposal theProposal)
        throws HttpPresentationException, webschedulePresentationException
    {  
 String pifname, pilname, to, proposalid, proj_name, aproj_name,subject,from,comments;
String [] message;
String pbookmark;
 int	status ;
	
String statuscode[] = {"Editable","Submitted","Internal Comments","Committe Comments","PI Responded","Approved","Checked","Accounts Added"};
 from = "appsadmin";
 
 // later add committee alias
 // to = "7tcommittee";   
     try {
     pifname = theProposal.getUserFirstName();
     pilname = theProposal.getUserLastName();
     to = theProposal.getUserEmail();
     proposalid = theProposal.getHandle();
     proj_name = theProposal.getProj_name();
     aproj_name = theProposal.getBproj_name();
     comments = theProposal.getComments();
     status = theProposal.getStatus();
     pbookmark = theProposal.getBookmark();
      } catch(Exception ex) {
        throw new webschedulePresentationException("Cannot retrieve proposal info", ex);    
        }
	
subject = "Proposal "+aproj_name+" Committee Comments ";

//if (pbookmark.equals("humanns"))
 //{

String[] msgTextns = {"Dear Dr. "+pilname+",",
			"\n",
"Your proposal titled \""+proj_name+"\" (ID number: "+proposalid+") has been reviewed by the UCSD Center for fMRI (CFMRI) Human Neuroscience Committee.\n",
"  The committee had the following comments regarding your proposal:\n",
		      "  "+ comments+"\n",
		      "\n",
	"Please review these comments and respond to them through the on-line proposal management system URL:\n",
"http://fmriwebapp.ucsd.edu:8100\n",
"   Please do not respond to this e-mail.  \n",	
		      "\n",
	    "Thank you,\n",
		    "\n",
		    "The CFMRI Human Neuroscience Committee"};	
  message = msgTextns;

	  to = to +",committee_3t";   
/*}	  
	else 
{
  String[] msgTextbi = {"Dear Dr. "+pilname+",",
" Your proposal titled "+proj_name+" (ID number: "+proposalid+") has been reviewed by the UCSD Center for fMRI (CFMRI) Bioimaging Committee.\n",
"  The committee had the following comments regarding your proposal:\n",
		      "  "+ comments+"\n",
		      "\n",
	"Please review these comments and respond to them through the on-line proposal management system URL:\n",
"http://fmriwebapp.ucsd.edu:8100\n",
"   Please do not respond to this e-mail.  \n",	
		    " \n",
		    "Thank you,\n",
		    "\n",
		    "The CFMRI Bioimaging Committee"};	
		  message = msgTextbi;
		to = to +",committee_7t";  
	}	    	  


*/
		    
		    
      try {
    	    SendMail sch_not;	

 sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }
}

    
     /*
     * Updates the proposal appending operator information
     *
     */
 /*   public String handleRespond() 
        throws HttpPresentationException, webschedulePresentationException
    {      
  
	Proposal theproposal = null;
	
	
	String proposalID = this.getProposalID();
	
		
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	   
            updateProposal(theproposal);
	   
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
    
    theProposal.setResponse(response);
   
            	   
    System.out.println(" trying to saving a proposal two ");
	        theProposal.save();	
    System.out.println(" trying to saving a proposal three ");
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding proposal", ex);
        }    
	
	}
    
   */ 
    
}


