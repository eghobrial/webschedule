/**--------------------------------------------------------------
* Webschedule
*
* @class:proposalp2
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

/**
 * proposalp2.java gets operator parameters.
 *
 */
public class proposalp2 extends BasePO
{


    /**
     * Constants
     */
     private static String ERROR_NAME = "ERROR_NAME"; 
    private static String O1LNAME = "o1lname";
    private static String O1FNAME = "o1fname";
    private static String O1PHONE = "o1phone";
    private static String O1EMAIL = "o1email";


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
        String o1lname = this.getComms().request.getParameter(O1LNAME);
	String o1fname = this.getComms().request.getParameter(O1FNAME);
	String o1phone = this.getComms().request.getParameter(O1PHONE);
	String o1email = this.getComms().request.getParameter(O1EMAIL);
	
	
	proposalp2HTML page = new proposalp2HTML();
	HTMLScriptElement script = new newproposalp2ScriptHTML().getElementRealScript();
       XMLCUtil.replaceNode(script, page.getElementDummyScript());


       //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }
	String ProposalID = this.getProposalID();
    Proposal proposal = null;
    
 
    
    
    try {
	proposal = ProposalFactory.findProposalByID(ProposalID);
	    } catch(Exception ex) {
            return showPage("You must fill out all fields to add this project");
        }
	
try {


	if(null != o1lname && o1lname.length() != 0) {
                page.getElementO1lname().setValue(o1lname);
            } else {
                page.getElementO1lname().setValue(proposal.getOp1lastname());
            }
	
	if(null != o1fname && o1fname.length() != 0) {
                page.getElementO1fname().setValue(o1fname);
            } else {
                page.getElementO1fname().setValue(proposal.getOp1firstname());
            }
	
	if(null != o1phone && o1phone.length() != 0) {
                page.getElementO1phone().setValue(o1phone);
            } else {
                page.getElementO1phone().setValue(proposal.getOp1phone());
            }
	
	if(null != o1email && o1email.length() != 0) {
                page.getElementO1email().setValue(o1email);
            } else {
                page.getElementO1email().setValue(proposal.getOp1email());
            }
 
	} catch(Exception ex) {
            return showPage("You must fill out all fields to add this proposal");
        }
	
	    return page.toDocument();
    }
    
         
    /*
     * Updates the proposal appending operator information
     *
     */
    public String handleAdd() 
        throws HttpPresentationException, webschedulePresentationException
    {      
  
   String o1lname = this.getComms().request.getParameter(O1LNAME);
	String o1fname = this.getComms().request.getParameter(O1FNAME);
	String o1phone = this.getComms().request.getParameter(O1PHONE);
	String o1email = this.getComms().request.getParameter(O1EMAIL);
  
    if (o1lname.length() == 0 || o1fname.length() ==0 || 
	        o1phone.length() == 0 || o1email.length() == 0 ) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }    
	
	
	Proposal theproposal = null;
	boolean isucsd = true;
	
	String proposalID = this.getProposalID();
	
		
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    isucsd = theproposal.Isucsd();
            updateProposal(theproposal);
	    
	    if (isucsd) {
	      throw new ClientPageRedirectException(PROPOSALP3UC_PAGE);
	    } else {
               	throw new ClientPageRedirectException(PROPOSALP3NONUC_PAGE);
               }
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
     }
     
     
       public String handleAddforlater() 
        throws HttpPresentationException, webschedulePresentationException
    {      
  
	Proposal theproposal = null;
	boolean isucsd = true;
	
	String proposalID = this.getProposalID();
	
		
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    isucsd = theproposal.Isucsd();
            updateProposal(theproposal);
	    
	    throw new ClientPageRedirectException(MANAGEPROPOSAL_PAGE);
	    
	    /*if (isucsd) {
	      throw new ClientPageRedirectException(NEWPROPOSALP3UC_PAGE);
	    } else {
               	throw new ClientPageRedirectException(NEWPROPOSALP3NONUC_PAGE);
               }*/
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
     }
     
      protected void updateProposal(Proposal theProposal)
        throws HttpPresentationException, webschedulePresentationException
    {  
      String o1lname = this.getComms().request.getParameter(O1LNAME);
	String o1fname = this.getComms().request.getParameter(O1FNAME);
	String o1phone = this.getComms().request.getParameter(O1PHONE);
	String o1email = this.getComms().request.getParameter(O1EMAIL);
	
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
    theProposal.setOp1lastname(o1lname);
    theProposal.setOp1firstname(o1fname);
    theProposal.setOp1phone(o1phone);
    theProposal.setOp1email(o1email);
            	   
    System.out.println(" trying to saving a proposal two ");
	        theProposal.save();	
    System.out.println(" trying to saving a proposal three ");
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding proposal", ex);
        }    
	
	}
    
}


