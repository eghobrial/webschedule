/**--------------------------------------------------------------
* Webschedule
*
* @class:newproposalp1a
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
public class newproposalp1a extends BasePO
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
	
      
        newproposalp1aHTML page = new newproposalp1aHTML();
	HTMLScriptElement script = new newproposalaScriptHTML().getElementRealScript();
       XMLCUtil.replaceNode(script, page.getElementDummyScript());		

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
	
	 if(null != this.getComms().request.getParameter(FUNDUC)) {
            page.getElementFunduc().setChecked(true);
        } else {
            page.getElementFunduc().setChecked(false);
        }
	
	
	 if(null != this.getComms().request.getParameter(FUNDNONUC)) {
            page.getElementFundnonuc().setChecked(true);
        } else {
            page.getElementFundnonuc().setChecked(false);
        }
	
	 if(null != this.getComms().request.getParameter(IMAGESA)) {
            page.getElementImagesa().setChecked(true);
        } else {
            page.getElementImagesa().setChecked(false);
        }
	
	 if(null != this.getComms().request.getParameter(IMAGETS)) {
            page.getElementImagets().setChecked(true);
        } else {
            page.getElementImagets().setChecked(false);
        }
	
	  if(null != this.getComms().request.getParameter(PROJ_NAME)) {
            page.getElementProj_name().setValue(this.getComms().request.getParameter(PROJ_NAME));
        }

         if(null != this.getComms().request.getParameter(APROJ_NAME)) {
            page.getElementAproj_name().setValue(this.getComms().request.getParameter(APROJ_NAME));
        }
	
	 if(null != this.getComms().request.getParameter(COPIS)) {
            page.getElementCopis().setValue(this.getComms().request.getParameter(COPIS));
        }
	
	  if(null != this.getComms().request.getParameter(CNAME)) {
            page.getElementCname().setValue(this.getComms().request.getParameter(CNAME));
        }

	if(null != this.getComms().request.getParameter(CPHONE)) {
            page.getElementCphone().setValue(this.getComms().request.getParameter(CPHONE));
        }
	
	if(null != this.getComms().request.getParameter(CEMAIL)) {
            page.getElementCemail().setValue(this.getComms().request.getParameter(CEMAIL));
        }
	if(null != this.getComms().request.getParameter(CTECH)) {
            page.getElementCtech().setChecked(true);
        } else {
            page.getElementCtech().setChecked(false);
        }
	
	 if(null != this.getComms().request.getParameter(ONEOP)) {
            page.getElementOneop().setChecked(true);
        } else {
            page.getElementOneop().setChecked(false);
        }
	
	      
	    return page.toDocument();
    }
    
          
    /*
     * Adds a proposal to the database
     *
     */
    public String handleAdd() 
        throws HttpPresentationException, webschedulePresentationException
    {    
    
    String proj_name = this.getComms().request.getParameter(PROJ_NAME);
    String  ctech = this.getComms().request.getParameter(CTECH);
    String funduc = this.getComms().request.getParameter(FUNDUC);
    String aproj_name = this.getComms().request.getParameter(APROJ_NAME);
    String copis = this.getComms().request.getParameter(COPIS);
    String cname = this.getComms().request.getParameter(CNAME);
     String cphone = this.getComms().request.getParameter(CPHONE);
     String  cemail = this.getComms().request.getParameter(CEMAIL);
    String ProposalID = null;
    String pnflag="no";
    String bproj_name;
    
    //calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int month = cancelinfo.get(cancelinfo.MONTH);
    	int year = cancelinfo.get(cancelinfo.YEAR);
     
     
     // if some fields are empty, generate error and redirect to this PO
	    if (proj_name.length() == 0 || aproj_name.length() ==0 || 
	        cname.length() == 0 || cphone.length() == 0 ||
	        cemail.length() == 0 || copis.length() == 0) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }      
    
        if ( aproj_name.length() > 8 ) {
        return showPage("Please select an abbreviated project title between no more than 8 chars");    
        }      
    
    
      /* try {
	        Proposal[] ProposalList = ProposalFactory.findProposalsForPerson(this.getUser());
	        
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	        for(int numProjs = 0; numProjs < ProposalList.length; numProjs++) {	
                Proposal theProposal = ProposalList[numProjs];
			
		
		bproj_name = theProposal.getBproj_name();
		if (bproj_name == aproj_name)
		pnflag = "yes";
		}
    
      } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Proposal catalog: " + ex);
            throw new webschedulePresentationException("Error getting Proposals for user: ", ex);
	    }
	    
	    
	    
	    if (pnflag == "yes")
	    {
        return showPage("The abbreviated project name has been used before, please choose a different one.");    
        }      else {
    
    try {
	        Proposal proposal = new Proposal();
            saveProposal(proposal);
	    ProposalID = getProposalID(year,month,this.getUser(),aproj_name);
	    System.out.println("proposal id  =   "+ProposalID);
	    this.setProposalID(ProposalID);
	    if(null != this.getComms().request.getParameter(CTECH)) {
	           if(null != this.getComms().request.getParameter(FUNDUC)) {
                	throw new ClientPageRedirectException(NEWPROPOSALP3UC_PAGE);
			} else {
                	throw new ClientPageRedirectException(NEWPROPOSALP3NONUC_PAGE);
            	        }
            	    } else {
                	throw new ClientPageRedirectException(NEWPROPOSALP2_PAGE);
            	     }
            
	    } catch(Exception ex) {
            return showPage("You must fill out all fields to add this project");
        }
  
    }*/
    
      try {
            // Now check that the login name is not taken.
	        if(null == ProposalFactory.findProposalByOwnerBproj_name(this.getUser(),aproj_name)) {
	             Proposal proposal = new Proposal();
            saveProposal(proposal);
	    ProposalID = getProposalID(year,month,this.getUser(),aproj_name);
	    System.out.println("proposal id  =   "+ProposalID);
	    this.setProposalID(ProposalID);
	    if(null != this.getComms().request.getParameter(CTECH)) {
	           if(null != this.getComms().request.getParameter(FUNDUC)) {
                	throw new ClientPageRedirectException(NEWPROPOSALP3UC_PAGE);
			} else {
                	throw new ClientPageRedirectException(NEWPROPOSALP3NONUC_PAGE);
            	        }
            	    } else {
                	throw new ClientPageRedirectException(NEWPROPOSALP2_PAGE);
            	     }
	        } else {
                // Login name already taken
                // Redirect to this same PO with informative error message
                return showPage("The abbreviated project name " + aproj_name + " is already taken. Please try another.");    
	        }
        } catch(webscheduleBusinessException ex) {
            throw new webschedulePresentationException("Exception logging in user: ", ex);
        }
    
    }
    
    
     /*
     * Adds a proposal to the database
     *
     */
    public String handleAddforlater() 
        throws HttpPresentationException, webschedulePresentationException
    {    
    
    String proj_name = this.getComms().request.getParameter(PROJ_NAME);
    String  ctech = this.getComms().request.getParameter(CTECH);
    String funduc = this.getComms().request.getParameter(FUNDUC);
    String aproj_name = this.getComms().request.getParameter(APROJ_NAME);
    String copis = this.getComms().request.getParameter(COPIS);
    String cname = this.getComms().request.getParameter(CNAME);
     String cphone = this.getComms().request.getParameter(CPHONE);
     String  cemail = this.getComms().request.getParameter(CEMAIL);
    String ProposalID = null;
    
    //calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int month = cancelinfo.get(cancelinfo.MONTH);
    	int year = cancelinfo.get(cancelinfo.YEAR);
     
     
     // if some fields are empty, generate error and redirect to this PO
	    if (proj_name.length() == 0 || aproj_name.length() ==0 || 
	        cname.length() == 0 || cphone.length() == 0 ||
	        cemail.length() == 0 || copis.length() == 0) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }      
    
        if ( aproj_name.length() > 8 ) {
        return showPage("Please select an abbreviated project title between no more than 8 chars");    
        }      
    
    
    try {
	        Proposal proposal = new Proposal();
            saveProposal(proposal);
	    ProposalID = getProposalID(year,month,this.getUser(),aproj_name);
	    System.out.println("proposal id  =   "+ProposalID);
	    this.setProposalID(ProposalID);
	      throw new ClientPageRedirectException(MANAGEPROPOSAL_PAGE);
	    
	   
	   /* if(null != this.getComms().request.getParameter(CTECH)) {
	           if(null != this.getComms().request.getParameter(FUNDUC)) {
                	throw new ClientPageRedirectException(NEWPROPOSALP3UC_PAGE);
			} else {
                	throw new ClientPageRedirectException(NEWPROPOSALP3NONUC_PAGE);
            	        }
            	    } else {
                	throw new ClientPageRedirectException(NEWPROPOSALP2_PAGE);
            	     }
            */
	    } catch(Exception ex) {
            return showPage("You must fill out all fields to add this project");
        }
  
    
    }
    
  
    
  
    protected void saveProposal(Proposal theProposal)
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
		//	theProposal.setBookmark("animal");
            	    } else {
                	theProposal.setIsanimal(false);
            	     }
     if(null != this.getComms().request.getParameter(IMAGETS)) {
                	theProposal.setIssample(true);
	//		theProposal.setBookmark("sample");
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
// temp set totalhours to 0 to avoid null errors in quickview
theProposal.setThours(0);

theProposal.setBookmark("animal");

    System.out.println(" trying to saving a proposal two, and book mark is  animal"+ proj_name);
	        theProposal.save();	
    System.out.println(" trying to saving a proposal three "+ proj_name);
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding proposal", ex);
        }    
    }
    
    protected String getProposalID(int year,int month,Person thePerson,String aproj_name)
      throws HttpPresentationException, webschedulePresentationException
     {
     Proposal theProposal = null;
     String proposalID  = null;
     try
	{    	
    	theProposal = ProposalFactory.findProposalByYearMonthOwnerBproj_name(year,month,thePerson,aproj_name);
	proposalID = theProposal.getHandle();
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting proposal information", ex);
        }
	return proposalID;
     }
    
    
}


