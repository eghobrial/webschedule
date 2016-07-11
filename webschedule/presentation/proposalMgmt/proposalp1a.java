/**--------------------------------------------------------------
* Webschedule
*
* @class:proposalp1a
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
import java.util.Calendar;

/**
 * newproposalp1.java shows the Proposal Menu Options
 *
 */
public class proposalp1a extends BasePO
{

 /**
     * Constants
     */

    private static String ERROR_NAME = "ERROR_NAME";
    private static String FUNDUC = "funduc";
    private static String FUNDNONUC = "fundnonuc";
    private static String IMAGESA = "imagesa";
    private static String IMAGETS = "imagets";  
    private static String PROJ_NAME = "proj_name";
    private static String APROJ_NAME = "aproj_name";
     private static String COPIS = "copis";
    private static String CNAME = "cname";
    private static String CPHONE = "cphone";
    private static String CEMAIL = "cemail";
    private static String CTECH = "ctech";
    private static String ONEOP = "oneop";
     private static String PROPOSAL_ID = "proposalID";
      
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
    	String funduc = this.getComms().request.getParameter(FUNDUC);
     	String fundnonuc = this.getComms().request.getParameter(FUNDNONUC);
     	String imagesa = this.getComms().request.getParameter(IMAGESA);
     	String imagets = this.getComms().request.getParameter(IMAGETS);
        String proj_name = this.getComms().request.getParameter(PROJ_NAME);
	String aproj_name = this.getComms().request.getParameter(APROJ_NAME);
	String copis = this.getComms().request.getParameter(COPIS);
	String cname = this.getComms().request.getParameter(CNAME);
	String cphone = this.getComms().request.getParameter(CPHONE);
      	String  cemail = this.getComms().request.getParameter(CEMAIL);
	String  ctech = this.getComms().request.getParameter(CTECH);
	String  oneop = this.getComms().request.getParameter(ONEOP);
        String  user_email = null;
	String firstname=null;
	String lastname=null;
	String office = null;
	String phone = null;
	String piname=null;
	boolean extop, nonucsd, cntrop;
	
      
        proposalp1aHTML page = new proposalp1aHTML();
	Person thePerson = this.getUser();
	
	try
    	{    	
	firstname = thePerson.getFirstname();
	lastname = thePerson.getLastname();
    	user_email = thePerson.getEmail();
	office = thePerson.getOffice();
	phone = thePerson.getPhone();
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting user's email", ex);
        }
	
	piname = firstname +" "+lastname;
	page.setTextPiname(piname);
	page.setTextAtitle(office);
	page.setTextPhone(phone);
	page.setTextEmail(user_email);
	//First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if (null != errorMsg ||
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
            return showPage("Error retrieving proposal info");
        }
  
try {		
	 if(null != this.getComms().request.getParameter(FUNDUC)) {
            page.getElementFunduc().setChecked(true);
        } else {
            page.getElementFunduc().setChecked(proposal.Isucsd());
        }
	
	if (proposal.Isucsd())
	  nonucsd = false;
	else
	  nonucsd = true;  
	
	 if(null != this.getComms().request.getParameter(FUNDNONUC)) {
            page.getElementFundnonuc().setChecked(true);
        } else {
            page.getElementFundnonuc().setChecked(nonucsd);
        }
	
	 if(null != this.getComms().request.getParameter(IMAGESA)) {
            page.getElementImagesa().setChecked(true);
        } else {
            page.getElementImagesa().setChecked(proposal.Isanimal());
        }
	
	 if(null != this.getComms().request.getParameter(IMAGETS)) {
            page.getElementImagets().setChecked(true);
        } else {
            page.getElementImagets().setChecked(proposal.Issample());
        }
	
	if(null != proj_name && proj_name.length() != 0) {
                page.getElementProj_name().setValue(proj_name);
            } else {
                page.getElementProj_name().setValue(proposal.getProj_name());
            }
	
	
            if(null != aproj_name && aproj_name.length() != 0) {
                page.getElementAproj_name().setValue(aproj_name);
            } else {
                page.getElementAproj_name().setValue(proposal.getBproj_name());
            }
	
	
	if(null != copis && copis.length() != 0) {
                page.getElementCopis().setValue(copis);
            } else {
                page.getElementCopis().setValue(proposal.getCopis());
            }
	
	if(null != cname && cname.length() != 0) {
                page.getElementCname().setValue(cname);
            } else {
                page.getElementCname().setValue(proposal.getCname());
            }
	
	
	if(null != cphone && cphone.length() != 0) {
                page.getElementCphone().setValue(cphone);
            } else {
                page.getElementCphone().setValue(proposal.getCphone());
            }
	    
	    
	if(null != cemail && cemail.length() != 0) {
                page.getElementCemail().setValue(cemail);
            } else {
                page.getElementCemail().setValue(proposal.getCemail());
            }
	
	
	if (proposal.CntrOp())
	  {
	 cntrop = true;
	  extop = false;
	  }
	else
	{
	cntrop=false;
	  extop = true;  	
	}
	
	 if(null != this.getComms().request.getParameter(CTECH)) {
            page.getElementCtech().setChecked(true);
        } else {
            page.getElementCtech().setChecked(cntrop);
        }
	
	 if(null != this.getComms().request.getParameter(ONEOP)) {
            page.getElementOneop().setChecked(true);
        } else {
            page.getElementOneop().setChecked(extop);
        }
	 } catch(Exception ex) {
            return showPage("You must fill out all fields to add this proposal");
        }
	      
	    return page.toDocument();
    }
    
    
 /*   
    
      public String handlePropedit()
        throws HttpPresentationException, webscheduleBusinessException
    {

        String ProposalID = this.getComms().request.getParameter(PROPOSAL_ID);
	 Proposal proposal = null;
	  System.out.println(" **** I see the handle prop edit ****");
	  
	  if (ProposalID == "invalidID") {
	  this.getSessionData().setUserMessage("Please choose a valid proposal ");
                 throw new ClientPageRedirectException(EDITPROPOSAL_PAGE);
	  } else {
	  
	try {
            proposal = ProposalFactory.findProposalByID(ProposalID);
	     this.setProposalID(ProposalID);
       throw new ClientPageRedirectException(PROPOSALP1_PAGE);
	 } catch (Exception ex) {
 this.getSessionData().setUserMessage("Please choose a valid proposal to edit");
 throw new ClientPageRedirectException(EDITPROPOSAL_PAGE);
    }
    }
    }
  */
    
    public String handlePropedit()
     throws webschedulePresentationException, HttpPresentationException
    {	
    
     System.out.println(" **** I see the handle edit module ****");
    this.setPropFlag("Edit");
     String ProposalID = this.getComms().request.getParameter(PROPOSAL_ID);
     Proposal proposal = null;
     System.out.println(" **** Current proposal ID off handle edit ****"+ ProposalID);
          
    //    
    try {
            proposal = ProposalFactory.findProposalByID(ProposalID);
	 } catch (Exception ex) {
 this.getSessionData().setUserMessage("Please choose a valid proposal to edit");
 throw new ClientPageRedirectException(MANAGEPROPOSAL_PAGE);
     }
     
     if ((proposal == null)|| (ProposalID == "invalidID")) {
	       //System.out.println("Proj id off select date event"+proj_id);
            	this.getSessionData().setUserMessage("Please choose a valid project ");
                 throw new ClientPageRedirectException(EDITPROPOSAL_PAGE);
                 // Show error message that project not found
            } else {
      this.setProposalID(ProposalID);
       throw new ClientPageRedirectException(PROPOSALP1_PAGE);
       } 
     }
	  
    /*
     * Edits a proposal to the database
     *
     */
    public String handleEdit() 
        throws HttpPresentationException, webschedulePresentationException
    {    
    String  ctech = this.getComms().request.getParameter(CTECH);
    String funduc = this.getComms().request.getParameter(FUNDUC);
    String aproj_name = this.getComms().request.getParameter(APROJ_NAME);
    String ProposalID = this.getProposalID();
    Proposal proposal = null;
    
    //calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int month = cancelinfo.get(cancelinfo.MONTH);
    	int year = cancelinfo.get(cancelinfo.YEAR);
    
    
    try {
	        proposal = ProposalFactory.findProposalByID(ProposalID);
	   
            updateProposal(proposal);
	  
	    if(null != this.getComms().request.getParameter(CTECH)) {
	           if(null != this.getComms().request.getParameter(FUNDUC)) {
                	throw new ClientPageRedirectException(PROPOSALP3UC_PAGE);
			} else {
                	throw new ClientPageRedirectException(PROPOSALP3NONUC_PAGE);
            	        }
            	    } else {
                	throw new ClientPageRedirectException(PROPOSALP2_PAGE);
            	     }
            
	    } catch(Exception ex) {
            return showPage("You must fill out all fields to add this project");
        }
  
    
    }
    
    protected void updateProposal(Proposal theProposal)
        throws HttpPresentationException, webschedulePresentationException
    {  
     String funduc = this.getComms().request.getParameter(FUNDUC);
     String fundnonuc = this.getComms().request.getParameter(FUNDNONUC);
     String imagesa = this.getComms().request.getParameter(IMAGESA);
     String imagets = this.getComms().request.getParameter(IMAGETS);
     String proj_name = this.getComms().request.getParameter(PROJ_NAME);
     String aproj_name = this.getComms().request.getParameter(APROJ_NAME);
     String copis = this.getComms().request.getParameter(COPIS);
     String cname = this.getComms().request.getParameter(CNAME);
     String cphone = this.getComms().request.getParameter(CPHONE);
     String  cemail = this.getComms().request.getParameter(CEMAIL);
     String  ctech = this.getComms().request.getParameter(CTECH);
     String  oneop = this.getComms().request.getParameter(ONEOP);
     //calculation for the time right now
     Calendar cancelinfo = Calendar.getInstance();
     int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
     int todaymonth = cancelinfo.get(cancelinfo.MONTH);
     int todayyear = cancelinfo.get(cancelinfo.YEAR);
	
     try {
     theProposal.setOwner(this.getUser());
     theProposal.setToday(todaydate);
     theProposal.setMonth(todaymonth);
     theProposal.setYear(todayyear);
     if(null != this.getComms().request.getParameter(FUNDUC)) {
                	theProposal.setIsucsd(true);
            	    } else {
                	theProposal.setIsucsd(false);
            	     }
     if(null != this.getComms().request.getParameter(FUNDNONUC)) {
                	theProposal.setIsucsd(false);
            	    } else {
                	theProposal.setIsucsd(true);
            	     }
     if(null != this.getComms().request.getParameter(IMAGESA)) {
                	theProposal.setIsanimal(true);
            	    } else {
                	theProposal.setIsanimal(false);
            	     }
     if(null != this.getComms().request.getParameter(IMAGETS)) {
                	theProposal.setIssample(true);
            	    } else {
                	theProposal.setIssample(false);
            	     }
    theProposal.setProj_name(proj_name);
    theProposal.setAproj_name(aproj_name);
    theProposal.setCopis(copis);
    theProposal.setCname(cname);
    theProposal.setCphone(cphone);
    theProposal.setCemail(cemail);
    if(null != this.getComms().request.getParameter(CTECH)) {
                	theProposal.setCntrOp(true);
            	    } else {
                	theProposal.setCntrOp(false);
            	     }
    System.out.println(" trying to saving a proposal two "+ proj_name);
	        theProposal.save();	
    System.out.println(" trying to saving a proposal three "+ proj_name);
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding proposal", ex);
        }    
    }
  
    
    
}


