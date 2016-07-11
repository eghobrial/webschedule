/**--------------------------------------------------------------
* Webschedule
*
* @class:checkroute
* @version
* @author: Eman Ghobrial
* @since: Jan 2008
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
 *checkroute.java shows the Proposal Menu Options
 *
 */
public class checkroute extends BasePO
{
 
  private static String PROJ_ID = "proj_id"; 
  
  private static String CHECKOP1 = "checkop1";
  private static String FAXONFILE = "faxonfile";
    private static String ATONFILE = "atonfile";
  
   private static String ICDATE ="icdate";
	 private static String ICMONTH ="icmonth";
    private static String ICYEAR ="icyear";  

//  private static String ATDATE ="atdate";
//	 private static String ATMONTH ="atmonth";
 //   private static String ATYEAR ="atyear";  
 
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
        checkrouteHTML page = new checkrouteHTML();

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
    


    /**
     *
     */
    public String showRoutePage(String errorMsg)
     throws HttpPresentationException, webschedulePresentationException
    {
        checkrouteHTML page = new checkrouteHTML();

       //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }

String proj_id = this.getComms().request.getParameter(PROJ_ID);
 
	  
  String oplastname =" ";
  String opfirstname=" ";
  //boolean AnimalTrans;
  boolean iacucfax;
  //Date atdate= new  Date();
  Date iacucdate = new  Date();
  
	
	
	HTMLSelectElement	icdate_select, icmonth_select, icyear_select;
	HTMLCollection		icdate_selectCollection, icmonth_selectCollection, icyear_selectCollection;
	HTMLOptionElement	icdate_option, icmonth_option, icyear_option;
	String			icdate_optionName, icmonth_optionName, icyear_optionName;
		
		
	icdate_select = (HTMLSelectElement)page.getElementIcdate();
	icdate_selectCollection = icdate_select.getOptions();	
			
        icmonth_select = (HTMLSelectElement)page.getElementIcmonth();
	icmonth_selectCollection = icmonth_select.getOptions();	

	icyear_select = (HTMLSelectElement)page.getElementIcyear();
	icyear_selectCollection = icyear_select.getOptions();	
	

  	/*HTMLSelectElement	atdate_select, atmonth_select, atyear_select;
	HTMLCollection		atdate_selectCollection, atmonth_selectCollection, atyear_selectCollection;
	HTMLOptionElement	atdate_option, atmonth_option, atyear_option;
	String			atdate_optionName, atmonth_optionName, atyear_optionName;
		
		
	atdate_select = (HTMLSelectElement)page.getElementAtdate();
	atdate_selectCollection = atdate_select.getOptions();	
			
        atmonth_select = (HTMLSelectElement)page.getElementAtmonth();
	atmonth_selectCollection = atmonth_select.getOptions();	

	atyear_select = (HTMLSelectElement)page.getElementAtyear();
	atyear_selectCollection = atyear_select.getOptions();	
  
  */
  Proposal theProposal ;
	
	 try {
	   
          theProposal = ProposalFactory.findProposalByID(proj_id);
	  oplastname = theProposal.getOp1lastname();
          opfirstname = theProposal.getOp1firstname();
	//  AnimalTrans = theProposal.AnimalTrans();
	  iacucfax = theProposal.IACUCFaxed();
	  //atdate = theProposal.getAnimTransDate();
	  iacucdate = theProposal.getIACUCDate();
	       } catch(Exception ex) {
            this.writeDebugMsg("Error getting Proposal info: " + ex);
        }    
     
     int icyeari = iacucdate.getYear();
     icyeari = icyeari + 1900;
     System.out.println("iacuc year  "+Integer.toString(icyeari));
     
     int icmonthi = iacucdate.getMonth();
     int icdatei = iacucdate.getDate();

/* int atyeari = atdate.getYear();
     atyeari = atyeari + 1900;
     System.out.println("animal transfer date year  "+Integer.toString(atyeari));
     
     int atmonthi = atdate.getMonth();
     int atdatei = atdate.getDate();
*/
String icyears = Integer.toString(icyeari);
String icmonths = Integer.toString(icmonthi);
String icdates = Integer.toString(icdatei);

/*String atyears = Integer.toString(atyeari);
String atmonths = Integer.toString(atmonthi);
String atdates = Integer.toString(atdatei);
*/

      
 page.setTextO1fname(opfirstname);
  page.setTextO1lname(oplastname);
    
//update_page(page);

if (icyeari > 2000)
{
int icdate_optionlen = icdate_selectCollection.getLength();
 for (int i=0; i< icdate_optionlen; i++) {
	  icdate_option = (HTMLOptionElement)icdate_selectCollection.item(i);
	  icdate_optionName = icdate_option.getValue();
	  if (icdate_optionName.equals(icdates))
	     icdate_option.setSelected(true);
	  else
	     icdate_option.setSelected(false);
	    }  

int icmonth_optionlen = icmonth_selectCollection.getLength();
 for (int i=0; i< icmonth_optionlen; i++) {
	  icmonth_option = (HTMLOptionElement)icmonth_selectCollection.item(i);
	  icmonth_optionName = icmonth_option.getValue();
	  if (icmonth_optionName.equals(icmonths))
	     icmonth_option.setSelected(true);
	  else
	     icmonth_option.setSelected(false);
	    }  
	    
	      int icyear_optionlen = icyear_selectCollection.getLength();
	for (int i=0; i< icyear_optionlen; i++) {
	  icyear_option = (HTMLOptionElement)icyear_selectCollection.item(i);
	  icyear_optionName = icyear_option.getValue();
	  if (icyear_optionName.equals(icyears))
	     icyear_option.setSelected(true);
	  else
	     icyear_option.setSelected(false);
	    }  

}

/*
if (atyeari > 2000)
{
int atdate_optionlen = atdate_selectCollection.getLength();
 for (int i=0; i< atdate_optionlen; i++) {
	  atdate_option = (HTMLOptionElement)atdate_selectCollection.item(i);
	  atdate_optionName = atdate_option.getValue();
	  if (atdate_optionName.equals(atdates))
	     atdate_option.setSelected(true);
	  else
	     atdate_option.setSelected(false);
	    }  

int atmonth_optionlen = atmonth_selectCollection.getLength();
 for (int i=0; i< atmonth_optionlen; i++) {
	  atmonth_option = (HTMLOptionElement)atmonth_selectCollection.item(i);
	  atmonth_optionName = atmonth_option.getValue();
	  if (atmonth_optionName.equals(atmonths))
	     atmonth_option.setSelected(true);
	  else
	     atmonth_option.setSelected(false);
	    }  
	    
	      int atyear_optionlen = atyear_selectCollection.getLength();
	for (int i=0; i< atyear_optionlen; i++) {
	  atyear_option = (HTMLOptionElement)atyear_selectCollection.item(i);
	  atyear_optionName = atyear_option.getValue();
	  if (atyear_optionName.equals(atyears))
	     atyear_option.setSelected(true);
	  else
	     atyear_option.setSelected(false);
	    }  
}

*/
	    return page.toDocument();
    }
    
   
   
    public String handleShowroute()
        throws HttpPresentationException, webschedulePresentationException
    {
    	String proj_id = this.getComms().request.getParameter(PROJ_ID);
	System.out.println("Handle show route visited  ");
	System.out.println("problem id  =   "+proj_id);
	try
	{
            if (null == proj_id) {
            this.getSessionData().setUserMessage(proj_id + "  Please choose a valid proposal ");
                 throw new ClientPageRedirectException(CHPROPOSALLOG_PAGE);
                 // Show error message that project not found
	} else{
		 
	  this.setProposalID(proj_id);
	  return showRoutePage(null);
        //    throw new ClientPageRedirectException(APPROVEPROPOSAL_PAGE);
	     	}   
	    } catch (Exception ex) {
         	throw new webschedulePresentationException("Error getting user login name", ex);
          }	
      }

   
   

    /*
     * Updates the proposal appending STATUS information
     *
     */
    public String handleRoute() 
        throws HttpPresentationException, webschedulePresentationException
    {      
  
  
  String proj_id = this.getProposalID();
 
   
	System.out.println("Handle Route visited  ");
	System.out.println("problem id  =   "+proj_id);
	
	  Proposal theproposal ;
	  
	  try
	{
            if (null == proj_id) {
            this.getSessionData().setUserMessage(proj_id + "  Please choose a valid proposal ");
                 throw new ClientPageRedirectException(CHECKROUTE_PAGE);
                 // Show error message that project not found
	} else{
		 
//	  this.setProposalID(proj_id);
	 
	 
	  try {
	   
            theproposal = ProposalFactory.findProposalByID(proj_id);
	     } catch(Exception ex) {
            throw new webschedulePresentationException("Error FINDing proposal", ex);
         
	} 
	    updateProposalStatus(theproposal);
	    // send an email to Eman to add accounts
	  sendemail (theproposal);
            throw new ClientPageRedirectException(CHPROPOSALLOG_PAGE);
	     	
	    }
        } catch(Exception ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        
     }
}

	  
	
 
 
 
 
   protected void updateProposalStatus(Proposal theProposal)
        throws HttpPresentationException, webschedulePresentationException
    {  
    
     
     String checkop1 = this.getComms().request.getParameter(CHECKOP1);
     String faxonfile = this.getComms().request.getParameter(FAXONFILE);
   //  String atonfile = this.getComms().request.getParameter(ATONFILE);
        
     String icdate = this.getComms().request.getParameter(ICDATE);
     String icmonth = this.getComms().request.getParameter(ICMONTH);
     String icyear = this.getComms().request.getParameter(ICYEAR);
        
   //  String atdate = this.getComms().request.getParameter(ATDATE);
   //  String atmonth = this.getComms().request.getParameter(ATMONTH);
  //   String atyear = this.getComms().request.getParameter(ATYEAR);
     String oplastname, opfirstname;
     
     System.out.println("icdate  =   "+icdate);
      System.out.println("icmonth =   "+icmonth);  
       
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
   	theProposal.setStatus(6);
	theProposal.setComments(theProposal.getComments());
	
 if(null != this.getComms().request.getParameter(CHECKOP1)) {
                	theProposal.setOp1status(true);
            	    } else {
                	theProposal.setOp1status(false);
           	     }	
	
	
	
	   if(null != this.getComms().request.getParameter(FAXONFILE)) {
                	theProposal.setIACUC(true);
            	    } else {
                	theProposal.setIACUC(false);
           	     }
		     
	/*if(null != this.getComms().request.getParameter(ATONFILE)) {
                	theProposal.setAnimalTrans(true);
            	    } else {
                	theProposal.setAnimalTrans(false);
           	     }	     
	*/
	 java.sql.Date jsqlIC;
  String tempIC = icyear+"-"+icmonth+"-"+icdate;   
    jsqlIC = java.sql.Date.valueOf(tempIC);
   theProposal.setIACUCDate(jsqlIC);
  
    
  /*   java.sql.Date jsqlAT;
   String tempAT = atyear+"-"+atmonth+"-"+atdate;   
    jsqlAT = java.sql.Date.valueOf(tempAT);
    theProposal.setAnimTransDate(jsqlAT);
	
    */  
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
 int	status ;
	
String statuscode[] = {"Editable","Submitted","Internal Comments","Committe Comments","PI Responded","Approved","Checked","Accounts Added"};
 from = "appsadmin";
    
     try {
     pifname = theProposal.getUserFirstName();
     pilname = theProposal.getUserLastName();
     to = "eghobrial@ucsd.edu";
     proposalid = theProposal.getHandle();
     proj_name = theProposal.getProj_name();
     aproj_name = theProposal.getBproj_name();
     comments = theProposal.getComments();
     status = theProposal.getStatus();
      } catch(Exception ex) {
        throw new webschedulePresentationException("Cannot retrieve proposal info", ex);    
        }
	
subject = "Proposal "+aproj_name+" Status is: "+statuscode[status];
String[] msgText1 = {"PI Name "+pilname+",",
                      "Your proposal ID number: "+proposalid+" ",
		      "Has fulfilled the requirements and ready for webschedule accounts\n",
		      "  \n",
		    " "};	
		    
		    
      try {
    	    SendMail sch_not;	
message = msgText1;
 sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }
}

}


