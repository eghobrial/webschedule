/**--------------------------------------------------------------
* Webschedule
*
* @class:previewa
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

import java.sql.Date;

/**
 *preview.java shows the Proposal Menu Options
 *
 */
public class previewa extends BasePO
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
    throws HttpPresentationException, webschedulePresentationException
    {
    	
//	String  oneop = this.getComms().request.getParameter(ONEOP);
        // Person Parameters
	String  user_email ;
	String firstname;
	String lastname;
	String office ;
	String phone ;
	String piname;
	
	//Proposal parameters
	boolean funduc;
     	boolean imagesa ;
     	boolean imagets ;
        String proj_name ;
	String aproj_name;
	String cname ;
	String cphone ;
      	String  cemail;
	boolean  ctech ;
	String op1lname ;
	String op1fname ;
	String op1phone ;
	String op1email ;
	String indexnum ;
	String Baline1 ;
	String Baline2 ;
	String Baline3 ;
	String Bacity ;
	String Bast ;
	String Bazip;
	String fpname ;
	String fpphone;
      	String  fpemail ;
	
	int thours ;
	String writeup ;
	String dataanalysis ;
	String biohazard ;
	
	boolean iacucfax;
	boolean RFCoils;
	boolean Restrains;
	boolean Physioeq;
	boolean anesthetics;
	boolean ancillary;
	boolean stimuli;
	boolean contrast;
	
	
	Date stdate ;
	Date expdate ;
	Date atdate ;
	Date iacucdate;
                  
     String animalq1 ;
     String animalq2 ;
     String animalq3 ;
     String animalq4 ;
     
     String proposalq1;
     String proposalq2 ;
     String proposalq3 ;  
     String proposalq4 ;  
     String proposalq5 ;
     String proposalq6 ;
     String proposalq7 ;
     String proposalq8 ;
      
        previewaHTML page = new previewaHTML();
	
	HTMLScriptElement script = new previewaScriptHTML().getElementRealScript();
       XMLCUtil.replaceNode(script, page.getElementDummyScript());		

	
	Person thePerson = this.getUser();
	
	Proposal  theProposal ;
	
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
	
	String proposalID = this.getProposalID();
	
		
	  try {
            theProposal = ProposalFactory.findProposalByID(proposalID);
	    funduc = theProposal.Isucsd();
	    imagesa = theProposal.Isanimal();
	    imagets = theProposal.Issample();
	    proj_name = theProposal.getProj_name();
	    aproj_name = theProposal.getBproj_name();
	    cname = theProposal.getCname();
	    cphone = theProposal.getCphone();
	    cemail = theProposal.getCemail();
	    
	    ctech = theProposal.CntrOp();
	    
	    if (ctech)
	 {
	 op1lname = "N/A" ;
	    op1fname = "N/A";
	    op1phone = "N/A";
	    op1email = "N/A";
	
	    } else {
	   op1lname = theProposal.getOp1lastname();
	   if (op1lname == null) 
	       op1lname = "not on file";
	    op1fname = theProposal.getOp1firstname();
	     if (op1fname == null) 
	       op1fname = "not on file";
	    op1phone = theProposal.getOp1phone();
	     if (op1phone == null) 
	       op1phone = "not on file";
	    op1email = theProposal.getOp1email();
	     if (op1email == null) 
	       op1email = "not on file";
	      } 
	    
	    if  (theProposal.Isucsd())
	   {
	    indexnum = theProposal.getIndexnum();
	
	    } else {
	    
	   indexnum = "N/A" ;
	    }
	   
	   
	    Baline1 = theProposal.getBaline1();
	    Baline2 = theProposal.getBaline2();
	    Baline3 = theProposal.getBaline3();
	    Bacity = theProposal.getBacity();
	    Bast = theProposal.getBast();
	    Bazip = theProposal.getBazip();
	    fpname = theProposal.getFpname();
	    fpphone = theProposal.getFpphone();
	    fpemail = theProposal.getFpemail();    
	    
	    thours = theProposal.getThours();
	writeup = theProposal.getWriteup();
	 dataanalysis = theProposal.getDataanalysis();
	biohazard = theProposal.getBioHazard();
	
	 iacucfax = theProposal.IACUCFaxed() ;
	 RFCoils = theProposal.RFCoils();
	 Restrains = theProposal.Restraints();
	 Physioeq = theProposal.Physioeq();
	 anesthetics= theProposal.anesthetics();
	 ancillary= theProposal.ancillary();
	 stimuli= theProposal.Stimuli();
	 contrast= theProposal.Contrast();
	
	
	 stdate = theProposal.getStdate();
	 expdate = theProposal.getExpdate();
	 
	 
	 atdate = theProposal.getAnimTransDate();
	iacucdate = theProposal.getIACUCDate(); 
	     
	  animalq1 = theProposal.getAnimalq1() ;
         animalq2 = theProposal.getAnimalq2() ;
         animalq3 = theProposal.getAnimalq3() ;
         animalq4 = theProposal.getAnimalq4();
     
     proposalq1 = theProposal.getProposalq1();
     proposalq2 = theProposal.getProposalq2();
    proposalq3 = theProposal.getProposalq3() ;  
     proposalq4 = theProposal.getProposalq4();  
    proposalq5 = theProposal.getProposalq5();
    proposalq6 = theProposal.getProposalq6();
    proposalq7 = theProposal.getProposalq7();
    proposalq8 = theProposal.getProposalq8();           
		    
        } catch(webscheduleBusinessException ex) {
           // this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
	
      //calculation for the time right now
     Calendar cancelinfo = Calendar.getInstance();
     int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
     int todaymonth = cancelinfo.get(cancelinfo.MONTH);
     int todayyear = cancelinfo.get(cancelinfo.YEAR);
     String today=Integer.toString(todayyear)+"-"+Integer.toString(todaymonth+1)+"-"+Integer.toString(todaydate);
	
 System.out.println(" Today off Preview "+ today);
	page.setTextToday(today);
	piname = firstname +" "+lastname;
	
System.out.println(" PI NAME off Preview "+ piname);
	page.setTextPiname(piname);
System.out.println(" PI OFFICE off Preview "+ office);	
	page.setTextAtitle(office);
	page.setTextPhone(phone);
	page.setTextEmail(user_email);
	
	
System.out.println(" Contact info off Preview "+ cname+cphone+cemail);
	page.setTextCname(cname);
	page.setTextCphone(cphone);
	page.setTextCemail(cemail);
	
	if (ctech)
	 {
	  page.setTextCtech("Yes");
	  page.setTextO1lname("N/A");
	  page.setTextO1fname("N/A");
	  page.setTextO1phone("N/A");
	  page.setTextO1email("N/A");
	    } else {
	   page.setTextCtech("No");
	  page.setTextO1lname(op1lname);
	  page.setTextO1fname(op1fname);
	  page.setTextO1phone(op1phone);
	  page.setTextO1email(op1email); 
	      } 
	     
	  if  (funduc)
	   {
	    page.setTextFunding("UCSD");
	    page.setTextIndexnum(indexnum);
	    } else {
	    page.setTextFunding("NON-UCSD");
	    page.setTextIndexnum("N/A");
	    }
	  
	    page.setTextBaline1(Baline1);
	    page.setTextBaline2(Baline2);
	    page.setTextBaline3(Baline3);
	    page.setTextBacity(Bacity);
	    page.setTextBast(Bast);   
	    page.setTextBazip(Bazip);
	    
	   page.setTextFpname(fpname);
           page.setTextFpphone(fpphone);
	   page.setTextFpemail(fpemail); 
	   
	 
	  
	  if (imagesa)
	     page.setTextImage("Small Animals");
	   else     page.setTextImage("Samples"); 
	   
	 
	   
	   System.out.println(" **** PROJECT Name off Preview ****"+ proj_name);
	 
	   page.setTextProj_name(proj_name);
	    page.setTextAproj_name(aproj_name);
	 page.setTextThours(Integer.toString(thours)); 
	 
//	 System.out.println(" Project Start Date off Preview "+ stdate.toString()+" End Date "+expdate.toString());
	 
//	 System.out.println(" Project IACUC "+ atdate.toString()+" Antrasfer "+iacucdate.toString());
	
	if (stdate==null) {
	   page.setTextStartd("Not on file");
	 } else {
	 page.setTextStartd(stdate.toString());
	 }
	 
	 if (expdate==null) {
	   page.setTextEndd("Not on file");
	 } else {
	 page.setTextEndd(expdate.toString());
	 }
	 
	 
	 if (iacucdate == null)
	 {
	 page.setTextIacucdate("Not on file");
	 }else {
	  int icyeari = iacucdate.getYear();
          icyeari = icyeari + 1900;
	 if (icyeari < 2000) {
	   page.setTextIacucdate("Not on file");
	   } else {
	   page.setTextIacucdate(iacucdate.toString());
	   }
	}
	
	  if (atdate == null)
	 { 
	 page.setTextAtransfer("Not on file");
	   } else {
	  int atyeari = atdate.getYear();
          atyeari = atyeari + 1900;
	    if (atyeari < 2000)
	   page.setTextAtransfer("Not on file");
	   else
	  page.setTextAtransfer(atdate.toString());
	 }
	 
	 
	 //page.setTextStartd(Date.toString(stdate));
	 //page.setTextEndd(Date.toString(expdate));
	 
	 //page.setText();
	page.setTextWriteup(writeup);
	page.setTextDataanalysis(dataanalysis);
	page.setTextBiohazard(biohazard);

  System.out.println(" **** Writeup off Preview ****"+ writeup);
System.out.println(" **** dataanalysis off Preview ****"+ dataanalysis);	
System.out.println(" **** biohazard off Preview ****"+ biohazard);	
		
	if (RFCoils)
	{
	  page.setTextAe1("Yes");
	 System.out.println(" **** rfcoils off Preview visited ****"); 
	  }
	 else 
	 {
	   page.setTextAe1("No");
	    System.out.println(" **** rfcoils off Preview visited ****"); 
	}
	if (Restrains)
	  page.setTextAe2("Yes");
	 else 
	   page.setTextAe2("No");
	
	if (Physioeq)
	  page.setTextAe3("Yes");
	 else 
	   page.setTextAe3("No");
	   
	   if (anesthetics)
	  page.setTextAe4("Yes");
	 else 
	   page.setTextAe4("No");
	   
	   if (ancillary)
	  page.setTextAe5("Yes");
	 else 
	   page.setTextAe5("No");
	   
	   if (stimuli)
	  page.setTextAe6("Yes");
	 else 
	   page.setTextAe6("No");
	   
	   if (contrast)
	  page.setTextAe7("Yes");
	 else 
	   page.setTextAe7("No");
	
	
	page.setTextAnimalq1(animalq1);
	page.setTextAnimalq2(animalq2);
	page.setTextAnimalq3(animalq3);
	page.setTextAnimalq4(animalq4);
	
	page.setTextProposalq1(proposalq1);
	page.setTextProposalq2(proposalq2);
	page.setTextProposalq3(proposalq3);
	page.setTextProposalq4(proposalq4);
	page.setTextProposalq5(proposalq5);
	page.setTextProposalq6(proposalq6);
	page.setTextProposalq7(proposalq7);
	page.setTextProposalq8(proposalq8);
	
	
	//First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if (null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }
	
    return page.toDocument();
    }
    
       /*
     * Updates the proposal appending STATUS information
     *
     */
    public String handlePsubmit() 
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
    public String handlePedit() 
        throws HttpPresentationException, webschedulePresentationException
    {      
  
	Proposal theproposal ;
	String proposalID = this.getProposalID();
	int status = 0;
	System.out.println(" **** Proposal Edit has been visited ****"+ proposalID);
		
	  try {
            theproposal = ProposalFactory.findProposalByID(proposalID);
	    updateProposalStatus(theproposal, status);
	    
	      throw new ClientPageRedirectException(PROPOSALP1_PAGE);
	    
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
	    
	      throw new ClientPageRedirectException(PREVIEW_PAGE);
	    
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
 

 from = "appsadmin";
    
     try {
     pifname = theProposal.getUserFirstName();
     pilname = theProposal.getUserLastName();
     to = theProposal.getUserEmail();
    
     proposalid = theProposal.getHandle();
     proj_name = theProposal.getProj_name();
     aproj_name = theProposal.getBproj_name();
      } catch(Exception ex) {
        throw new webschedulePresentationException("Cannot retrieve proposal info", ex);    
        }
	
subject = "**Your proposal "+aproj_name+" has been submitted for review**";
String[] msgText1 = {"Dear Dr. "+pilname+",",
                    " Your proposal titled "+proj_name+" has been submitted for review, no more editing at this point.\n",
		    "Your proposal ID is "+proposalid+"\n",
		    " ",
		    "Thank you."};	
		    
	 to = to +",committee_3t";	    
      try {
    	    SendMail sch_not;	
message = msgText1;
 sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }
}
}


