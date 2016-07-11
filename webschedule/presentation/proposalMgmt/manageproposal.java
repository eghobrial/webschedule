/**--------------------------------------------------------------
* Webschedule
*
* @class:manageproposal
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
public class manageproposal extends BasePO
{

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
    {
        manageproposalHTML page = new manageproposalHTML();

       //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }

	    return page.toDocument();
    }
    
    public String handleAddflag()
     throws webschedulePresentationException, HttpPresentationException
    {	
    System.out.println(" **** ADD Proposal has been visited from managment page ****");
    this.setPropFlag("Add");
         throw new ClientPageRedirectException(NEWPROPOSALP1_PAGE);
     }
     
     
     public String handleEditflag()
     throws webschedulePresentationException, HttpPresentationException
    {	
    this.setPropFlag("Edit");
         throw new ClientPageRedirectException(EDITPROPOSAL_PAGE);
     }
     
     public String handleStatusflag()
     throws webschedulePresentationException, HttpPresentationException
    {	
    this.setPropFlag("Status");
         throw new ClientPageRedirectException(PROPOSALSTATUS_PAGE);
     }
     

    /*
     * Updates the proposal appending STATUS information
     *
     */
    public String handlePsub() 
        throws HttpPresentationException, webschedulePresentationException
    {      
  
	Proposal theproposal ;
	String proposalID = this.getProposalID();
	int status = 1;
	System.out.println(" **** Proposal Submitted has been visited ****"+ proposalID);	
	  try {
	   
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    updateProposalStatus(theproposal, status);
	    // send an email to use that proposal has been submitted for review
	    sendemail (theproposal);
	      throw new ClientPageRedirectException(MANAGEPROPOSAL_PAGE);
	    
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
     }


 
      /*
     * Updates the proposal appending STATUS information
     *
     */
    public String handleSedit() 
        throws HttpPresentationException, webschedulePresentationException
    {      
  
	Proposal theproposal ;
	String proposalID = this.getProposalID();
	int status = 0;
	System.out.println(" **** Proposal Suave for Later has been visited ****"+ proposalID);
		
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    updateProposalStatus(theproposal, status);
	    
	      throw new ClientPageRedirectException(MANAGEPROPOSAL_PAGE);
	    
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
     }


   protected void updateProposalStatus(Proposal theProposal, int status)
        throws HttpPresentationException, webschedulePresentationException
    {  
    
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
   	theProposal.setStatus(status);
            	   
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
 String pifname, pilname, to, proposalid, proj_name, aproj_name,subject,from;
String [] message;
String pbookmark;  

 from = "appsadmin";
    
     try {
     pifname = theProposal.getUserFirstName();
     pilname = theProposal.getUserLastName();
     to = theProposal.getUserEmail();
     proposalid = theProposal.getHandle();
     proj_name = theProposal.getProj_name();
     aproj_name = theProposal.getBproj_name();
       pbookmark = theProposal.getBookmark();
      } catch(Exception ex) {
        throw new webschedulePresentationException("Cannot retrieve proposal info", ex);    
        }
	
	subject = "**Your proposal "+aproj_name+" on 3T West has been submitted for review**";
	
	//pbookmark = this.getPbookmark();
	System.out.println(" **** bookmark off manage proposal ****"+ pbookmark);
	
	//if (pbookmark.equals("humanns"))
	//{
String[] msgTextns = {"Dear Dr. "+pilname+",",
		     "\n",	
                    "Your proposal titled \""+proj_name+" \" (ID number: "+proposalid+") has been submitted for review. At this point, the proposal cannot be further edited.\n",
		    "Your proposal will be reviewed by the UCSD Center for Functional MRI  (CFMRI) Human Neuroscience Committee, reponse will be given within a month after submission.  \n",
		     "Thank you,\n",
		    "\n",
		    "The CFMRI Human Neuroscience Committee"};	
		    message = msgTextns;
		to = to +",committee_3t";    
		/*}    
else 
{
String[] msgTextbi = {"Dear Dr. "+pilname+",",
		     "\n",	
                    "Your proposal titled \""+proj_name+" \" (ID number: "+proposalid+") has been submitted for review. At this point, the proposal cannot be further edited.\n",
		    "Your proposal will be reviewed by the UCSD Center for Functional MRI  (CFMRI) Bioimaging Committee, which meets monthly. Please refer to http://cfmriweb.ucsd.edu/info/7TSubmission.html for a description of Committee meeting dates. \n",
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

}


