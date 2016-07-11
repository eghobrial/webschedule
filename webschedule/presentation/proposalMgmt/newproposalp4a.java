/**--------------------------------------------------------------
* Webschedule
*
* @class:newproposalp4a
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

import java.sql.Date;
import java.util.Calendar;

//import java.util.regex.*;

/**
 * newproposalp4.java shows the Proposal part 4 (proposal Information)
 *
 */
public class newproposalp4a extends BasePO
{

/**
 * Constants
 *
 **/
    private static String ERROR_NAME = "ERROR_NAME";
    private static String THOURS = "thours";
    //private static String STARTD = "startd";
    //private static String ENDD = "endd";
    private static String WRITEUP = "writeup";
    private static String BIOHAZARD = "biohazard";
    private static String DATAANALYSIS = "dataanalysis";
    private static String AE1 = "ae1";
    private static String AE2 = "ae2";
    private static String AE3 = "ae3";
    private static String AE4 = "ae4";
    private static String AE5 = "ae5";
    private static String AE6 = "ae6";
    private static String AE7 = "ae7";
    private static String FAXTOLINA = "faxtolina";
  //  private static String IACUCDATE = "iacucdate";
  //  private static String ATRANSFER = "atransfer";
  
   private static String STARTDATE ="startdate";
	 private static String STARTMONTH ="startmonth";
    private static String STARTYEAR ="startyear"; 
    
     private static String ENDDATE ="enddate";
	 private static String ENDMONTH ="endmonth";
    private static String ENDYEAR ="endyear"; 
  
  private static String ICDATE ="icdate";
	 private static String ICMONTH ="icmonth";
    private static String ICYEAR ="icyear";  

  private static String ATDATE ="atdate";
  private static String ATMONTH ="atmonth";
  private static String ATYEAR ="atyear";  
  private static String NIGHTTIME ="nighttime";
  
  private static String ANIMALQ1 ="animalq1";  
 private static String ANIMALQ2 ="animalq2";  
  private static String ANIMALQ3 ="animalq3";  
   private static String ANIMALQ4 ="animalq4";  
   
  private static String PROPOSALQ1 ="proposalq1";  
  private static String PROPOSALQ2 ="proposalq2";  
  private static String PROPOSALQ3 ="proposalq3";  
  private static String PROPOSALQ4 ="proposalq4";  
  private static String PROPOSALQ5 ="proposalq5";  
private static String PROPOSALQ6 ="proposalq6";  
private static String PROPOSALQ7 ="proposalq7";  
private static String PROPOSALQ8 ="proposalq8";  



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
     String thours = this.getComms().request.getParameter(THOURS);
     
     //String startd = this.getComms().request.getParameter(STARTD);
     //String endd = this.getComms().request.getParameter(ENDD);
     
      String startdate = this.getComms().request.getParameter(STARTDATE);
     String startmonth = this.getComms().request.getParameter(STARTMONTH);
     String startyear = this.getComms().request.getParameter(STARTYEAR);
     
       String enddate = this.getComms().request.getParameter(ENDDATE);
     String endmonth = this.getComms().request.getParameter(ENDMONTH);
     String endyear = this.getComms().request.getParameter(ENDYEAR);
     
     String writeup = this.getComms().request.getParameter(WRITEUP);
     String biohazard = this.getComms().request.getParameter(BIOHAZARD);
     String dataanalysis = this.getComms().request.getParameter(DATAANALYSIS);
     String ae1 = this.getComms().request.getParameter(AE1);
     String ae2 = this.getComms().request.getParameter(AE2);
     String ae3 = this.getComms().request.getParameter(AE3);
     String ae4 = this.getComms().request.getParameter(AE4);
     String ae5 = this.getComms().request.getParameter(AE5);
     String ae6 = this.getComms().request.getParameter(AE6);
     String ae7 = this.getComms().request.getParameter(AE7); 
     String faxtolina = this.getComms().request.getParameter(FAXTOLINA);
     
       String icdate = this.getComms().request.getParameter(ICDATE);
     String icmonth = this.getComms().request.getParameter(ICMONTH);
     String icyear = this.getComms().request.getParameter(ICYEAR);
     
    // String iacucdate = this.getComms().request.getParameter(IACUCDATE);
  //   String atransfer = this.getComms().request.getParameter(ATRANSFER);
     
     String atdate = this.getComms().request.getParameter(ATDATE);
     String atmonth = this.getComms().request.getParameter(ATMONTH);
     String atyear = this.getComms().request.getParameter(ATYEAR);
     String nighttime = this.getComms().request.getParameter(NIGHTTIME);
     
     String animalq1 = this.getComms().request.getParameter(ANIMALQ1);
     String animalq2 = this.getComms().request.getParameter(ANIMALQ2);
     String animalq3 = this.getComms().request.getParameter(ANIMALQ3);
     String animalq4 = this.getComms().request.getParameter(ANIMALQ4);
     
     String proposalq1 = this.getComms().request.getParameter(PROPOSALQ1);
     String proposalq2 = this.getComms().request.getParameter(PROPOSALQ2);
     String proposalq3 = this.getComms().request.getParameter(PROPOSALQ3);  
     String proposalq4 = this.getComms().request.getParameter(PROPOSALQ4);  
     String proposalq5 = this.getComms().request.getParameter(PROPOSALQ5);
     String proposalq6 = this.getComms().request.getParameter(PROPOSALQ6);
     String proposalq7 = this.getComms().request.getParameter(PROPOSALQ7);
     String proposalq8 = this.getComms().request.getParameter(PROPOSALQ8);
        
        newproposalp4aHTML page = new newproposalp4aHTML();
HTMLScriptElement script = new newproposalp4aScriptHTML().getElementRealScript();
       XMLCUtil.replaceNode(script, page.getElementDummyScript());		

//calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
	String todaydatei = Integer.toString(todaydate);
 	String todaymonthi = Integer.toString(todaymonth);
	String todayyeari = Integer.toString(todayyear); 

HTMLSelectElement	startdate_select, startmonth_select, startyear_select;
	HTMLCollection		startdate_selectCollection, startmonth_selectCollection, startyear_selectCollection;
	HTMLOptionElement	startdate_option, startmonth_option, startyear_option;
	String			startdate_optionName, startmonth_optionName, startyear_optionName;
		
		
	startdate_select = (HTMLSelectElement)page.getElementStartdate();
	startdate_selectCollection = startdate_select.getOptions();	
			
        startmonth_select = (HTMLSelectElement)page.getElementStartmonth();
	startmonth_selectCollection = startmonth_select.getOptions();	

	startyear_select = (HTMLSelectElement)page.getElementStartyear();
	startyear_selectCollection = startyear_select.getOptions();	
	
	
	HTMLSelectElement	enddate_select, endmonth_select, endyear_select;
	HTMLCollection		enddate_selectCollection, endmonth_selectCollection, endyear_selectCollection;
	HTMLOptionElement	enddate_option, endmonth_option, endyear_option;
	String			enddate_optionName, endmonth_optionName, endyear_optionName;
		
		
	enddate_select = (HTMLSelectElement)page.getElementEnddate();
	enddate_selectCollection = enddate_select.getOptions();	
			
        endmonth_select = (HTMLSelectElement)page.getElementEndmonth();
	endmonth_selectCollection = endmonth_select.getOptions();	

	endyear_select = (HTMLSelectElement)page.getElementEndyear();
	endyear_selectCollection = endyear_select.getOptions();	
	
	 System.out.println(" todays date of newproposal p4 = "+todaydatei);
	
	
	int startdate_optionlen = startdate_selectCollection.getLength();
	for (int i=0; i< startdate_optionlen; i++) {
	  startdate_option = (HTMLOptionElement)startdate_selectCollection.item(i);
	  startdate_optionName = startdate_option.getValue();
	  if (startdate_optionName.equals(todaydatei)){
	   System.out.println(" todays date of newproposal p4 inside loop= "+todaydatei);
	     startdate_option.setSelected(true);}
	  else
	     startdate_option.setSelected(false);
	    }  

	
	int startmonth_optionlen = startmonth_selectCollection.getLength();
	for (int i=0; i< startmonth_optionlen; i++) {
	  startmonth_option = (HTMLOptionElement)startmonth_selectCollection.item(i);
	  startmonth_optionName = startmonth_option.getValue();
	  if (startmonth_optionName.equals(todaymonthi))
	     startmonth_option.setSelected(true);
	  else
	     startmonth_option.setSelected(false);
	    }  
	    
	    
	      int startyear_optionlen = startyear_selectCollection.getLength();
	for (int i=0; i< startyear_optionlen; i++) {
	  startyear_option = (HTMLOptionElement)startyear_selectCollection.item(i);
	  startyear_optionName = startyear_option.getValue();
	  if (startyear_optionName.equals(todayyeari))
	     startyear_option.setSelected(true);
	  else
	     startyear_option.setSelected(false);
	    }  

int enddate_optionlen = enddate_selectCollection.getLength();
	for (int i=0; i< enddate_optionlen; i++) {
	  enddate_option = (HTMLOptionElement)enddate_selectCollection.item(i);
	  enddate_optionName = enddate_option.getValue();
	  if (enddate_optionName.equals(todaydatei))
	     enddate_option.setSelected(true);
	  else
	     enddate_option.setSelected(false);
	    }  

	
	int endmonth_optionlen = endmonth_selectCollection.getLength();
	for (int i=0; i< endmonth_optionlen; i++) {
	  endmonth_option = (HTMLOptionElement)endmonth_selectCollection.item(i);
	  endmonth_optionName = endmonth_option.getValue();
	  if (endmonth_optionName.equals(todaymonthi))
	     endmonth_option.setSelected(true);
	  else
	     endmonth_option.setSelected(false);
	    }  
	    
	    
	      int endyear_optionlen = endyear_selectCollection.getLength();
	for (int i=0; i< endyear_optionlen; i++) {
	  endyear_option = (HTMLOptionElement)endyear_selectCollection.item(i);
	  endyear_optionName = endyear_option.getValue();
	  if (endyear_optionName.equals(todayyeari))
	     endyear_option.setSelected(true);
	  else
	     endyear_option.setSelected(false);
	    }  


       //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }
	
	 if(null != this.getComms().request.getParameter(THOURS)) {
            page.getElementThours().setValue(this.getComms().request.getParameter(THOURS));
        }
	
/*	 if(null != this.getComms().request.getParameter(STARTD)) {
            page.getElementStartd().setValue(this.getComms().request.getParameter(STARTD));
        }
	
	 if(null != this.getComms().request.getParameter(ENDD)) {
            page.getElementEndd().setValue(this.getComms().request.getParameter(ENDD));
        }
	*/
	
	 if(null != this.getComms().request.getParameter(WRITEUP)) {
            page.getElementWriteup().setValue(this.getComms().request.getParameter(WRITEUP));
        }
	
	 if(null != this.getComms().request.getParameter(BIOHAZARD)) {
            page.getElementBiohazard().setValue(this.getComms().request.getParameter(BIOHAZARD));
        }
	
	 if(null != this.getComms().request.getParameter(DATAANALYSIS)) {
            page.getElementDataanalysis().setValue(this.getComms().request.getParameter(DATAANALYSIS));
        }

 if(null != this.getComms().request.getParameter(AE1)) {
            page.getElementAe1().setChecked(true);
        } else {
            page.getElementAe1().setChecked(false);
        }
	
 if(null != this.getComms().request.getParameter(AE2)) {
            page.getElementAe2().setChecked(true);
        } else {
            page.getElementAe2().setChecked(false);
        }
	
	 if(null != this.getComms().request.getParameter(AE3)) {
            page.getElementAe3().setChecked(true);
        } else {
            page.getElementAe3().setChecked(false);
        }
	
	 if(null != this.getComms().request.getParameter(AE4)) {
            page.getElementAe4().setChecked(true);
        } else {
            page.getElementAe4().setChecked(false);
        }
	
	 if(null != this.getComms().request.getParameter(AE5)) {
            page.getElementAe5().setChecked(true);
        } else {
            page.getElementAe5().setChecked(false);
        }
	
	 if(null != this.getComms().request.getParameter(AE6)) {
            page.getElementAe6().setChecked(true);
        } else {
            page.getElementAe6().setChecked(false);
        }
	
	 if(null != this.getComms().request.getParameter(AE7)) {
            page.getElementAe7().setChecked(true);
        } else {
            page.getElementAe7().setChecked(false);
        }
	
	 if(null != this.getComms().request.getParameter(FAXTOLINA)) {
            page.getElementFaxtolina().setChecked(true);
        } else {
            page.getElementFaxtolina().setChecked(false);
        }
	
	
	if(null != this.getComms().request.getParameter(NIGHTTIME)) {
            page.getElementNighttime().setChecked(true);
        } else {
            page.getElementNighttime().setChecked(false);
        }
	
	
/*	 if(null != this.getComms().request.getParameter(IACUCDATE)) {
            page.getElementIacucdate().setValue(this.getComms().request.getParameter(IACUCDATE));
        }
	
	 if(null != this.getComms().request.getParameter(ATRANSFER)) {
            page.getElementAtransfer().setValue(this.getComms().request.getParameter(ATRANSFER));
        }*/

	    return page.toDocument();
    }
    
    
     /*
     * Updates the proposal appending operator information
     *
     */
    public String handleAdd() 
        throws HttpPresentationException, webschedulePresentationException
    {      
    
     String thours = this.getComms().request.getParameter(THOURS);
      String writeup = this.getComms().request.getParameter(WRITEUP);
     String biohazard = this.getComms().request.getParameter(BIOHAZARD);
     String dataanalysis = this.getComms().request.getParameter(DATAANALYSIS);
     
      String animalq1 = this.getComms().request.getParameter(ANIMALQ1);
     String animalq2 = this.getComms().request.getParameter(ANIMALQ2);
     String animalq3 = this.getComms().request.getParameter(ANIMALQ3);
     String animalq4 = this.getComms().request.getParameter(ANIMALQ4);
     
     String proposalq1 = this.getComms().request.getParameter(PROPOSALQ1);
     String proposalq2 = this.getComms().request.getParameter(PROPOSALQ2);
     String proposalq3 = this.getComms().request.getParameter(PROPOSALQ3);  
     String proposalq4 = this.getComms().request.getParameter(PROPOSALQ4);  
     String proposalq5 = this.getComms().request.getParameter(PROPOSALQ5);
     String proposalq6 = this.getComms().request.getParameter(PROPOSALQ6);
     String proposalq7 = this.getComms().request.getParameter(PROPOSALQ7);
     String proposalq8 = this.getComms().request.getParameter(PROPOSALQ8);
     
   //  String pattern = "[0-9]";
     
     
     Proposal theproposal = null;
	String proposalID = this.getProposalID();
     
     
   //  if !(thours.matches(pattern))
   //     return showPage("Total number of hours must be a number");   
     
     // if some fields are empty, generate error and redirect to this PO
	    if (thours.length() == 0 || writeup.length() ==0 || 
	        biohazard.length() == 0 || dataanalysis.length() == 0 ||
	        animalq1.length() == 0 || animalq2.length() == 0 ||
		animalq3.length() == 0 || animalq4.length() == 0 ||
		proposalq1.length() == 0 || proposalq2.length() == 0 ||
		proposalq3.length() == 0 || proposalq4.length() == 0 ||
		proposalq5.length() == 0 || proposalq6.length() == 0 ||
		proposalq7.length() == 0 || proposalq8.length() == 0 ) {
		
		 try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    updateProposal(theproposal);
	    
	      throw new ClientPageRedirectException(NEWPROPOSALP4A_PAGE);
	    
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
          //  return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }      
	else {
  	
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    updateProposal(theproposal);
	    
	      throw new ClientPageRedirectException(PREVIEWA_PAGE);
	    
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
  }
  
     }
     
     
     
     /*
     * Updates the proposal appending operator information
     *
     */
    public String handleAddforlater() 
        throws HttpPresentationException, webschedulePresentationException
    {      
    
     String thours = this.getComms().request.getParameter(THOURS);
      String writeup = this.getComms().request.getParameter(WRITEUP);
     String biohazard = this.getComms().request.getParameter(BIOHAZARD);
     String dataanalysis = this.getComms().request.getParameter(DATAANALYSIS);
     
      String animalq1 = this.getComms().request.getParameter(ANIMALQ1);
     String animalq2 = this.getComms().request.getParameter(ANIMALQ2);
     String animalq3 = this.getComms().request.getParameter(ANIMALQ3);
     String animalq4 = this.getComms().request.getParameter(ANIMALQ4);
     
     String proposalq1 = this.getComms().request.getParameter(PROPOSALQ1);
     String proposalq2 = this.getComms().request.getParameter(PROPOSALQ2);
     String proposalq3 = this.getComms().request.getParameter(PROPOSALQ3);  
     String proposalq4 = this.getComms().request.getParameter(PROPOSALQ4);  
     String proposalq5 = this.getComms().request.getParameter(PROPOSALQ5);
     String proposalq6 = this.getComms().request.getParameter(PROPOSALQ6);
     String proposalq7 = this.getComms().request.getParameter(PROPOSALQ7);
     String proposalq8 = this.getComms().request.getParameter(PROPOSALQ8);
     
     
     // if some fields are empty, generate error and redirect to this PO
	    if (thours.length() == 0  ) {
            return showPage("Please fillin the number of hours, can be edited later");    
        }      
	
  
	Proposal theproposal = null;
	String proposalID = this.getProposalID();
	
		
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    updateProposal(theproposal);
	    
	      throw new ClientPageRedirectException(MANAGEPROPOSAL_PAGE);
	    
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
     }
     
    
    
    
     /*
     * Updates the proposal appending operator information
     *
     */
    public String handlePreview() 
        throws HttpPresentationException, webschedulePresentationException
    {      
    
     String thours = this.getComms().request.getParameter(THOURS);
      String writeup = this.getComms().request.getParameter(WRITEUP);
     String biohazard = this.getComms().request.getParameter(BIOHAZARD);
     String dataanalysis = this.getComms().request.getParameter(DATAANALYSIS);
     
      String animalq1 = this.getComms().request.getParameter(ANIMALQ1);
     String animalq2 = this.getComms().request.getParameter(ANIMALQ2);
     String animalq3 = this.getComms().request.getParameter(ANIMALQ3);
     String animalq4 = this.getComms().request.getParameter(ANIMALQ4);
     
     String proposalq1 = this.getComms().request.getParameter(PROPOSALQ1);
     String proposalq2 = this.getComms().request.getParameter(PROPOSALQ2);
     String proposalq3 = this.getComms().request.getParameter(PROPOSALQ3);  
     String proposalq4 = this.getComms().request.getParameter(PROPOSALQ4);  
     String proposalq5 = this.getComms().request.getParameter(PROPOSALQ5);
     String proposalq6 = this.getComms().request.getParameter(PROPOSALQ6);
     String proposalq7 = this.getComms().request.getParameter(PROPOSALQ7);
     String proposalq8 = this.getComms().request.getParameter(PROPOSALQ8);
     
     	try {
		 int totalh = Integer.parseInt(thours);
		 } catch (Exception e) {
		  return showPage(" Please make sure that the total number of hours is an integer ex (100)");
		  }
     
     // if some fields are empty, generate error and redirect to this PO
	    if (thours.length() == 0 || writeup.length() ==0 || 
	        biohazard.length() == 0 || dataanalysis.length() == 0 ||
	        animalq1.length() == 0 || animalq2.length() == 0 ||
		animalq3.length() == 0 || animalq4.length() == 0 ||
		proposalq1.length() == 0 || proposalq2.length() == 0 ||
		proposalq3.length() == 0 || proposalq4.length() == 0 ||
		proposalq5.length() == 0 || proposalq6.length() == 0 ||
		proposalq7.length() == 0 || proposalq8.length() == 0 ) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }      
	
  
	Proposal theproposal = null;
	String proposalID = this.getProposalID();
	
		
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    updateProposal(theproposal);
	    
	      throw new ClientPageRedirectException(PREVIEWA_PAGE);
	    
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
     }
     
     
     
    
      protected void updateProposal(Proposal theProposal)
        throws HttpPresentationException, webschedulePresentationException
    {  
    
     String thours = this.getComms().request.getParameter(THOURS);
     
     
   //  String startd = this.getComms().request.getParameter(STARTD);
   //  String endd = this.getComms().request.getParameter(ENDD);
      String startdate = this.getComms().request.getParameter(STARTDATE);
     String startmonth = this.getComms().request.getParameter(STARTMONTH);
     String startyear = this.getComms().request.getParameter(STARTYEAR);
     
       String enddate = this.getComms().request.getParameter(ENDDATE);
     String endmonth = this.getComms().request.getParameter(ENDMONTH);
     String endyear = this.getComms().request.getParameter(ENDYEAR);
     
     String writeup = this.getComms().request.getParameter(WRITEUP);
     String biohazard = this.getComms().request.getParameter(BIOHAZARD);
     String dataanalysis = this.getComms().request.getParameter(DATAANALYSIS);
     String ae1 = this.getComms().request.getParameter(AE1);
     String ae2 = this.getComms().request.getParameter(AE2);
     String ae3 = this.getComms().request.getParameter(AE3);
     String ae4 = this.getComms().request.getParameter(AE4);
     String ae5 = this.getComms().request.getParameter(AE5);
     String ae6 = this.getComms().request.getParameter(AE6);
     String ae7 = this.getComms().request.getParameter(AE7); 
     String faxtolina = this.getComms().request.getParameter(FAXTOLINA);
   //  String iacucdate = this.getComms().request.getParameter(IACUCDATE);
   //  String atransfer = this.getComms().request.getParameter(ATRANSFER);
   
     String icdate = this.getComms().request.getParameter(ICDATE);
     String icmonth = this.getComms().request.getParameter(ICMONTH);
     String icyear = this.getComms().request.getParameter(ICYEAR);
     
      String atdate = this.getComms().request.getParameter(ATDATE);
     String atmonth = this.getComms().request.getParameter(ATMONTH);
     String atyear = this.getComms().request.getParameter(ATYEAR);
      String nighttime = this.getComms().request.getParameter(NIGHTTIME);

      String animalq1 = this.getComms().request.getParameter(ANIMALQ1);
     String animalq2 = this.getComms().request.getParameter(ANIMALQ2);
     String animalq3 = this.getComms().request.getParameter(ANIMALQ3);
     String animalq4 = this.getComms().request.getParameter(ANIMALQ4);
     
     String proposalq1 = this.getComms().request.getParameter(PROPOSALQ1);
     String proposalq2 = this.getComms().request.getParameter(PROPOSALQ2);
     String proposalq3 = this.getComms().request.getParameter(PROPOSALQ3);  
     String proposalq4 = this.getComms().request.getParameter(PROPOSALQ4);  
     String proposalq5 = this.getComms().request.getParameter(PROPOSALQ5);
     String proposalq6 = this.getComms().request.getParameter(PROPOSALQ6);
     String proposalq7 = this.getComms().request.getParameter(PROPOSALQ7);
     String proposalq8 = this.getComms().request.getParameter(PROPOSALQ8);


     
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
    theProposal.setIndexnum(theProposal.getIndexnum());
    theProposal.setBaline1(theProposal.getBaline1());
    theProposal.setBaline2(theProposal.getBaline2());
    theProposal.setBaline3(theProposal.getBaline3());
    theProposal.setBacity(theProposal.getBacity());
    theProposal.setBast(theProposal.getBast());
    theProposal.setBazip(theProposal.getBazip());
    theProposal.setFpname(theProposal.getFpname());
    theProposal.setFpphone(theProposal.getFpphone());
    theProposal.setFpemail(theProposal.getFpemail()); 
    if (thours == null)
    theProposal.setThours(0);
    else
    theProposal.setThours(Integer.parseInt(thours));
  //  System.out.println(" startd "+startd);
//   String startdts = startd + " 00:00:00.00";
    theProposal.setAnimalq1(animalq1);
 theProposal.setAnimalq2(animalq2);
  theProposal.setAnimalq3(animalq3);
   theProposal.setAnimalq4(animalq4);
   
    theProposal.setProposalq1(proposalq1);
 theProposal.setProposalq2(proposalq2);
  theProposal.setProposalq3(proposalq3);
   theProposal.setProposalq4(proposalq4);
    theProposal.setProposalq5(proposalq5);
     theProposal.setProposalq6(proposalq6);
      theProposal.setProposalq7(proposalq7);
       theProposal.setProposalq8(proposalq8);

  // String startdts =  "2008-01-01 00:00:00.00";
//  java.sql.Date jsqlSD = java.sql.Date.valueOf(startd);
 //Calendar cal = Calendar.getInstance();
 //java.sql.Date jsqlSD = new java.sql.Date (cal.getTime().getTime());
 
    java.sql.Date jsqlSD;
    String tempSD = startyear+"-"+startmonth+"-"+startdate;   
    jsqlSD = java.sql.Date.valueOf(tempSD);
     theProposal.setStdate(jsqlSD);
     
   // theProposal.setStdate(startd);
 //   System.out.println(" startdts "+startdts);
 //  String enddts = endd + " 00:00:00.00";
   // String enddts = "2009-01-01 00:00:00.00";
  // java.sql.Date jsqlED = java.sql.Date.valueOf(endd);
   
   
   java.sql.Date jsqlED;
    String tempED = endyear+"-"+endmonth+"-"+enddate;   
    jsqlED = java.sql.Date.valueOf(tempED);
   
      theProposal.setExpdate(jsqlED);
    theProposal.setWriteup(writeup);
    theProposal.setBioHazard(biohazard);
    theProposal.setDataanalysis(dataanalysis);
	
     if(null != this.getComms().request.getParameter(AE1)) {
                	theProposal.setRFCoils(true);
            	    } else {
                	theProposal.setRFCoils(false);
            	     }
    	   
     if(null != this.getComms().request.getParameter(AE2)) {
                	theProposal.setRestraints(true);
            	    } else {
                	theProposal.setRestraints(false);
            	     }
     if(null != this.getComms().request.getParameter(AE3)) {
                	theProposal.setPhysioeq(true);
            	    } else {
                	theProposal.setPhysioeq(false);
            	     }
     if(null != this.getComms().request.getParameter(AE4)) {
                	theProposal.setAnesthetics(true);
            	    } else {
                	theProposal.setAnesthetics(false);
            	     }
     if(null != this.getComms().request.getParameter(AE5)) {
                	theProposal.setAncillary(true);
            	    } else {
                	theProposal.setAncillary(false);
            	     }
     if(null != this.getComms().request.getParameter(AE6)) {
                	theProposal.setStimuli(true);
            	    } else {
                	theProposal.setStimuli(false);
            	     }
     if(null != this.getComms().request.getParameter(AE7)) {
                	theProposal.setContrast(true);
            	    } else {
                	theProposal.setContrast(false);
            	     }
    
     if(null != this.getComms().request.getParameter(FAXTOLINA)) {
                	theProposal.setIACUCFaxed(true);
            	    } else {
                	theProposal.setIACUCFaxed(false);
           	     }
	//String idts = iacucdate + " 00:00:00.00";	     
 //  theProposal.setIACUCDate(java.sql.Date.valueOf(iacucdate));
 //   String adts = atransfer + " 00:00:00.00";
  // theProposal.setAnimTransDate(java.sql.Date.valueOf(atransfer));
  
  java.sql.Date jsqlIC;
  String tempIC = icyear+"-"+icmonth+"-"+icdate;   
    jsqlIC = java.sql.Date.valueOf(tempIC);
   theProposal.setIACUCDate(jsqlIC);
  
  /*if (null != iacucdate)
   {
    jsqlIC = java.sql.Date.valueOf(iacucdate);
    theProposal.setIACUCDate(jsqlIC);
    } else {
    String tempIC = "2000-01-31";
    
    jsqlIC = java.sql.Date.valueOf(tempIC);
    theProposal.setIACUCDate(jsqlIC);
    }
  */
  
  
     java.sql.Date jsqlAT;
   String tempAT = atyear+"-"+atmonth+"-"+atdate;   
    jsqlAT = java.sql.Date.valueOf(tempAT);
    theProposal.setAnimTransDate(jsqlAT);
     
 /* if (null != atransfer)
   {
    jsqlAT = java.sql.Date.valueOf(atransfer);
    theProposal.setAnimTransDate(jsqlAT);
    } else {
    
     String tempAT = "2000-01-31";
    jsqlAT = java.sql.Date.valueOf(tempAT);
    theProposal.setAnimTransDate(jsqlAT);
    }
   */
   
   if(null != this.getComms().request.getParameter(NIGHTTIME)) {
                	theProposal.setNighttime(true);
            	    } else {
                	theProposal.setNighttime(false);
            	     }
     
    System.out.println(" trying to saving a proposal two ");
	        theProposal.save();	
    System.out.println(" trying to saving a proposal three ");
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding proposal", ex);
        }    
    
    
    }
     
    
}


