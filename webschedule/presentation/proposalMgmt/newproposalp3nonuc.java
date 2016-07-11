/**--------------------------------------------------------------
* Webschedule
*
* @class:newproposalp3nonuc
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
 * newproposalp1.java shows the Proposal Menu Options
 *
 */
public class newproposalp3nonuc extends BasePO
{
/**
 * Constants
 *
 **/
    private static String ERROR_NAME = "ERROR_NAME";
    private static String BALINE1 = "baline1";
    private static String BALINE2 = "baline2";
    private static String BALINE3 = "baline3";
    private static String BACITY = "bacity";
    private static String BAST = "bast"; 
    private static String BAZIP = "bazip"; 
    private static String FPNAME = "fpname"; 
    private static String FPPHONE = "fpphone"; 
    private static String FPEMAIL = "fpemail"; 
    
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
    
    String baline1 = this.getComms().request.getParameter(BALINE1);
    String baline2 = this.getComms().request.getParameter(BALINE2);
    String baline3 = this.getComms().request.getParameter(BALINE3);
    String bacity = this.getComms().request.getParameter(BACITY);
    String bast = this.getComms().request.getParameter(BAST);
    String bazip = this.getComms().request.getParameter(BAZIP);
    String fpname = this.getComms().request.getParameter(FPNAME);
    String fpphone = this.getComms().request.getParameter(FPPHONE);
    String fpemail = this.getComms().request.getParameter(FPEMAIL);
    
        newproposalp3nonucHTML page = new newproposalp3nonucHTML();

HTMLScriptElement script = new newproposalp3ScriptHTML().getElementRealScript();
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

 if(null != baline1 && baline1.length() != 0) {
                page.getElementBaline1().setValue(baline1);
            } else {
                page.getElementBaline1().setValue(proposal.getBaline1());
            }
	    
	     if(null != baline2 && baline2.length() != 0) {
                page.getElementBaline2().setValue(baline2);
            } else {
                page.getElementBaline2().setValue(proposal.getBaline2());
            }
	    
	     if(null != baline3 && baline3.length() != 0) {
                page.getElementBaline3().setValue(baline3);
            } else {
                page.getElementBaline3().setValue(proposal.getBaline3());
            }
	    
	 if(null != bacity && bacity.length() != 0) {
                page.getElementBacity().setValue(bacity);
            } else {
                page.getElementBacity().setValue(proposal.getBacity());
            }
	
	
	 if(null != bast && bast.length() != 0) {
                page.getElementBast().setValue(bast);
            } else {
                page.getElementBast().setValue(proposal.getBast());
            }
	    
	     if(null != bazip && bazip.length() != 0) {
                page.getElementBazip().setValue(bazip);
            } else {
                page.getElementBazip().setValue(proposal.getBazip());
            }
	
	 if(null != fpname && fpname.length() != 0) {
                page.getElementFpname().setValue(fpname);
            } else {
                page.getElementFpname().setValue(proposal.getFpname());
            }
	    
	     if(null != fpphone && fpphone.length() != 0) {
                page.getElementFpphone().setValue(fpphone);
            } else {
                page.getElementFpphone().setValue(proposal.getFpphone());
            }
	    
	     if(null != fpemail && fpemail.length() != 0) {
                page.getElementFpemail().setValue(fpemail);
            } else {
                page.getElementFpemail().setValue(proposal.getFpemail());
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
  
   // String indexnum = this.getComms().request.getParameter(INDEXNUM);
    String baline1 = this.getComms().request.getParameter(BALINE1);
    String baline2 = this.getComms().request.getParameter(BALINE2);
    String baline3 = this.getComms().request.getParameter(BALINE3);
    String bacity = this.getComms().request.getParameter(BACITY);
    String bast = this.getComms().request.getParameter(BAST);
    String bazip = this.getComms().request.getParameter(BAZIP);
    String fpname = this.getComms().request.getParameter(FPNAME);
    String fpphone = this.getComms().request.getParameter(FPPHONE);
    String fpemail = this.getComms().request.getParameter(FPEMAIL);
    
     // if some fields are empty, generate error and redirect to this PO
	    if (baline1.length() == 0 || bacity.length() ==0 || 
	        bast.length() == 0 || bazip.length() == 0 ||
		 fpname.length() == 0 || fpphone.length() == 0 ||
	        fpemail.length() == 0 ) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }    
  
	Proposal theproposal = null;
	boolean isucsd = true;
	
	String proposalID = this.getProposalID();
	String pbookmark;
		
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    isucsd = theproposal.Isucsd();
	    pbookmark = theproposal.getBookmark();
	     System.out.println(" Bookmark off P3 NoNUC "+pbookmark);	
            updateProposal(theproposal);
	    
	    if ((pbookmark.equals("humanns"))||(pbookmark.equals("humanbi")))
	      throw new ClientPageRedirectException(NEWPROPOSALP4_PAGE);
	    else 
	      throw new ClientPageRedirectException(NEWPROPOSALP4A_PAGE);
	    
	    
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
     }
    
    
   
    
     /*
     * Updates the proposal appending operator information
     *
     */
    public String handleAddforlater() 
        throws HttpPresentationException, webschedulePresentationException
    {      
  
   // String indexnum = this.getComms().request.getParameter(INDEXNUM);
    String baline1 = this.getComms().request.getParameter(BALINE1);
    String baline2 = this.getComms().request.getParameter(BALINE2);
    String baline3 = this.getComms().request.getParameter(BALINE3);
    String bacity = this.getComms().request.getParameter(BACITY);
    String bast = this.getComms().request.getParameter(BAST);
    String bazip = this.getComms().request.getParameter(BAZIP);
    String fpname = this.getComms().request.getParameter(FPNAME);
    String fpphone = this.getComms().request.getParameter(FPPHONE);
    String fpemail = this.getComms().request.getParameter(FPEMAIL);
    
     // if some fields are empty, generate error and redirect to this PO
/*	    if (baline1.length() == 0 || bacity.length() ==0 || 
	        bast.length() == 0 || bazip.length() == 0 ||
		 fpname.length() == 0 || fpphone.length() == 0 ||
	        fpemail.length() == 0 ) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }    
  */
	Proposal theproposal = null;
	boolean isucsd = true;
	
	String proposalID = this.getProposalID();
	
		
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    isucsd = theproposal.Isucsd();
            updateProposal(theproposal);
	    
	      throw new ClientPageRedirectException(MANAGEPROPOSAL_PAGE);
	    
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
     }
    
    
    
    
    
      protected void updateProposal(Proposal theProposal)
        throws HttpPresentationException, webschedulePresentationException
    {  
    
    // String indexnum = this.getComms().request.getParameter(INDEXNUM);
    String baline1 = this.getComms().request.getParameter(BALINE1);
    String baline2 = this.getComms().request.getParameter(BALINE2);
    String baline3 = this.getComms().request.getParameter(BALINE3);
    String bacity = this.getComms().request.getParameter(BACITY);
    String bast = this.getComms().request.getParameter(BAST);
    String bazip = this.getComms().request.getParameter(BAZIP);
    String fpname = this.getComms().request.getParameter(FPNAME);
    String fpphone = this.getComms().request.getParameter(FPPHONE);
    String fpemail = this.getComms().request.getParameter(FPEMAIL);
    
     // if some fields are empty, generate error and redirect to this PO

    
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
    theProposal.setOp1lastname(theProposal.getOp1lastname());
    theProposal.setOp1firstname(theProposal.getOp1firstname());
    theProposal.setOp1phone(theProposal.getOp1phone());
    theProposal.setOp1email(theProposal.getOp1email());
    
   // theProposal.setIndexnum(indexnum);
    theProposal.setBaline1(baline1);
    theProposal.setBaline2(baline2);
    theProposal.setBaline3(baline3);
    theProposal.setBacity(bacity);
    theProposal.setBast(bast);
    theProposal.setBazip(bazip);
    theProposal.setFpname(fpname);
    theProposal.setFpphone(fpphone);
    theProposal.setFpemail(fpemail);  
            	   
    System.out.println(" trying to saving a proposal two ");
	        theProposal.save();	
    System.out.println(" trying to saving a proposal three ");
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding proposal", ex);
        }    
    
    
    }  
    
}


