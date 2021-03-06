/**--------------------------------------------------------------
* Webschedule
*
* @class:addaccounts
* @version
* @author: Eman Ghobrial
* @since: Feb 2008
*
*--------------------------------------------------------------*/

package webschedule.presentation.proposalMgmt;

import webschedule.business.proposal.*;
import webschedule.business.person.*;
import webschedule.business.operator.*;
import webschedule.business.operates.*;
import webschedule.business.project.*;
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

import java.util.Calendar;

import java.sql.Timestamp;

/**
 *checkroute.java shows the Proposal Menu Options
 *
 */
public class addaccounts extends BasePO
{
 
  private static String PROJ_ID = "proj_id"; 
  
  private static String CHECKOP1 = "checkop1";
  private static String FAXONFILE = "faxonfile";
    private static String ATONFILE = "atonfile";
  
   private static String ICDATE ="icdate";
	 private static String ICMONTH ="icmonth";
    private static String ICYEAR ="icyear";  

 // private static String ATDATE ="atdate";
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
        addaccountsHTML page = new addaccountsHTML();

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
    
    
    
  public String handleShowaddpage()
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
	  return showAddPage(null);
        //    throw new ClientPageRedirectException(APPROVEPROPOSAL_PAGE);
	     	}   
	    } catch (Exception ex) {
         	throw new webschedulePresentationException("Error getting user login name", ex);
          }	
      }


    /**
     *
     */
    public String showAddPage(String errorMsg)
     throws HttpPresentationException, webschedulePresentationException
    {
        addaccountsHTML page = new addaccountsHTML();

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
  boolean AnimalTrans = false;
  boolean iacuc = false;
  boolean op1status = false;
  Date atdate= new  Date();
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
	
/*
  	HTMLSelectElement	atdate_select, atmonth_select, atyear_select;
	HTMLCollection		atdate_selectCollection, atmonth_selectCollection, atyear_selectCollection;
	HTMLOptionElement	atdate_option, atmonth_option, atyear_option;
	String			atdate_optionName, atmonth_optionName, atyear_optionName;
		
		
	atdate_select = (HTMLSelectElement)page.getElementAtdate();
	atdate_selectCollection = atdate_select.getOptions();	
			
        atmonth_select = (HTMLSelectElement)page.getElementAtmonth();
	atmonth_selectCollection = atmonth_select.getOptions();	

	atyear_select = (HTMLSelectElement)page.getElementAtyear();
	atyear_selectCollection = atyear_select.getOptions();	*/
  
  
  Proposal theProposal ;
	
	 try {
	   
          theProposal = ProposalFactory.findProposalByID(proj_id);
	  oplastname = theProposal.getOp1lastname();
          opfirstname = theProposal.getOp1firstname();
	  op1status = theProposal.op1status();
	  AnimalTrans = theProposal.AnimalTrans();
	  iacuc = theProposal.IACUC();
//	  atdate = theProposal.getAnimTransDate();
	  iacucdate = theProposal.getIACUCDate();
	       } catch(Exception ex) {
            this.writeDebugMsg("Error getting Proposal info: " + ex);
        }    
     
     if(null != this.getComms().request.getParameter(CHECKOP1)) {
            page.getElementCheckop1().setChecked(true);
        } else {
            page.getElementCheckop1().setChecked(op1status);
        }
   
      if(null != this.getComms().request.getParameter(FAXONFILE)) {
            page.getElementFaxonfile().setChecked(true);
        } else {
            page.getElementFaxonfile().setChecked(iacuc);
        }
	
/*	if(null != this.getComms().request.getParameter(ATONFILE)) {
            page.getElementAtonfile().setChecked(true);
        } else {
            page.getElementAtonfile().setChecked(AnimalTrans);
        }
	*/
     
     int icyeari = iacucdate.getYear();
     icyeari = icyeari + 1900;
     System.out.println("iacuc year  "+Integer.toString(icyeari));
     
     int icmonthi = iacucdate.getMonth();
     int icdatei = iacucdate.getDate();

 int atyeari = atdate.getYear();
     atyeari = atyeari + 1900;
     System.out.println("animal transfer date year  "+Integer.toString(atyeari));
     
     int atmonthi = atdate.getMonth();
     int atdatei = atdate.getDate();

String icyears = Integer.toString(icyeari);
String icmonths = Integer.toString(icmonthi);
String icdates = Integer.toString(icdatei);

/*String atyears = Integer.toString(atyeari);
String atmonths = Integer.toString(atmonthi);
String atdates = Integer.toString(atdatei);*/


      
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

/*if (atyeari > 2000)
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
    
   
  
   
   

    /*
     * Updates the proposal appending STATUS information
     *
     */
    public String handleAddaccounts() 
        throws HttpPresentationException, webschedulePresentationException
    {      
  
  
  String proj_id = this.getProposalID();
 
   
	System.out.println("Handle Add accounts visited  ");
	System.out.println("problem id  =   "+proj_id);
	
	  Proposal theproposal ;
	  
	  try
	{
            if (null == proj_id) {
            this.getSessionData().setUserMessage(proj_id + "  Please choose a valid proposal ");
                 throw new ClientPageRedirectException(ADDACCOUNTS_PAGE);
                 // Show error message that project not found
	} else{
		 
//	  this.setProposalID(proj_id);
	 
	 
	  try {
	   
            theproposal = ProposalFactory.findProposalByID(proj_id);
	     } catch(Exception ex) {
            throw new webschedulePresentationException("Error FINDing proposal", ex);
         
	} 
	addacc(theproposal);
	    updateProposalStatus(theproposal);
	    // send an email to use that proposal has been submitted for review
	   sendemail (theproposal);
            throw new ClientPageRedirectException(CACCLOG_PAGE);
	     	
	    }
        } catch(Exception ex) {
            this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        
     }
}

	 
  protected void addacc(Proposal theProposal)
        throws HttpPresentationException, webschedulePresentationException
    {  
    Person owner ;
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
	String billaddr1 ;
	String billaddr2 ;
	String billaddr3 ;
	String city ;
	String state ;
	String zip;
	String fpname ;
	String fpphone;
      	String  fpemail ;
	int thours ;
	String personID;
	int codeofpay;
	Date endate;
	String lastname;
	String proposalID;
        boolean nighttime;
	 java.sql.Date iacucdate = null;
      
      //calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int certday = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int certmonth = cancelinfo.get(cancelinfo.MONTH);
    	int certyear = cancelinfo.get(cancelinfo.YEAR);
      
      
     try {
       personID = theProposal.getUserID();
       proposalID = theProposal.getHandle();
       lastname = theProposal.getUserLastName();
       //owner = theProposal.getOwner();
       funduc = theProposal.Isucsd();
       imagesa = theProposal.Isanimal();
       imagets = theProposal.Issample();
       proj_name = theProposal.getProj_name();
       aproj_name = theProposal.getBproj_name();
       cname = theProposal.getCname();
       cphone = theProposal.getCphone();
       cemail = theProposal.getCemail();
       ctech = theProposal.CntrOp();
       nighttime = theProposal.Nighttime();	  
	    if (ctech)
	 {
	 op1lname = "Kemper" ;
	    op1fname = "Cecelia";
	    op1phone = "822-0584";
	    op1email = "ckemper@ucsd.edu";
	
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
	    billaddr1 = theProposal.getBaline1();
	    billaddr2 = theProposal.getBaline2();
	    billaddr3 = theProposal.getBaline3();
	    city = theProposal.getBacity();
	    state = theProposal.getBast();
	    zip = theProposal.getBazip();
	    fpname = theProposal.getFpname();
	    fpphone = theProposal.getFpphone();
	    fpemail = theProposal.getFpemail();    
	    thours = theProposal.getThours();
	    endate = theProposal.getExpdate();
	      iacucdate = theProposal.getIACUCDate();
	 } catch(Exception ex) {
        throw new webschedulePresentationException("Cannot retrieve proposal info", ex);    
        }
 
  int endyeari = endate.getYear();
     endyeari = endyeari + 1900;
     System.out.println("exp year  "+Integer.toString(endyeari));
     
     int endmonthi = endate.getMonth();
     int enddatei = endate.getDate();
 
// 1. Let us save project Information

            aproj_name=lastname+aproj_name;                                                                                                                      
         try {
	 Project theProject = new Project();
	System.out.println(" trying to saving a project one "+ aproj_name);
            theProject.setProj_name(aproj_name);

            theProject.setPassword("test");
	    theProject.setDescription(proj_name);
	    theProject.setIndexnum(indexnum);
	    double thoursd = (double) thours;
	    theProject.setTotalhours(thoursd);
	 //   theProject.setDonehours(Double.parseDouble(dhours));
	    
	    //decied the code of pay 
	      if  (theProposal.Isucsd())
	   {
	    if (ctech)
	     codeofpay = 6;
	     else codeofpay = 1;
	
	    } else {
	    if (ctech)
	     codeofpay = 8;
	     else codeofpay = 7;
	
	   
	    } 
	   
	 System.out.println(" **code of pay out of saving project** "+ codeofpay);   
            theProject.setCodeofpay(codeofpay);
	
	    theProject.setContactname(fpname);
//	    String contactphone = fpphone+"/"+fpemail;
//	     System.out.println(" **contact phone/email** "+ contactphone);
	    theProject.setContactphone(fpphone);
	    theProject.setBilladdr1(billaddr1);
            theProject.setBilladdr2(billaddr2);

theProject.setBilladdr3(billaddr3);
theProject.setCity(city);
theProject.setState(state);
theProject.setZip(zip);
theProject.setAccountid("AA");
theProject.setNotifycontact(cemail);


theProject.setFpemail(fpemail);
theProject.setPOnum("0");
theProject.setPOexpdate(java.sql.Date.valueOf("2010-09-31"));
theProject.setPOhours(0);
theProject.setIACUCDate(java.sql.Date.valueOf("2010-01-01"));
theProject.setModifiedby("Ghobrial");
theProject.setModDate(java.sql.Date.valueOf("2010-04-30"));
theProject.setNotes("*");
theProject.setIRBnum("0");

String institute ;

	             if  (theProposal.Isucsd()) {
		      institute = "UCSD";
                	theProject.setOutside(false);
            	    } else {
		      institute = "Non-UCSD";
                	theProject.setOutside(true);
            	     }
 
                	theProject.setExp(false);
            	     
		     theProject.setInstitute(institute);
theProject.setExpday(enddatei);
theProject.setExpmonth(endmonthi);
theProject.setExpyear(endyeari);

	        theProject.setOwner(PersonFactory.findPersonByID(personID));
theProject.setProposalID(proposalID);
theProject.setCancredit(0);
theProject.setIACUCDate(iacucdate);
System.out.println(" Person ID "+ personID);

System.out.println(" trying to saving a project two "+ proj_name);
	        theProject.save();	
System.out.println(" trying to saving a project three "+ proj_name);
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding project", ex);
        }                                                                                                                                
                                                                                                                          
   //1 a. Check if the project is a UCSD add pilot hours project
  String paproj_name = null; 
 //  if (funduc) {
   
     paproj_name="Pilot"+aproj_name;                                                                                                                      
         try {
	 Project theProject = new Project();
	System.out.println(" trying to saving a project one "+ paproj_name);
            theProject.setProj_name(paproj_name);

            theProject.setPassword("test");
	    theProject.setDescription("Pilot hours account");
	    theProject.setIndexnum("N/A");
	    
	    theProject.setTotalhours(0.0);
	 //   theProject.setDonehours(Double.parseDouble(dhours));
	    
	    theProject.setCodeofpay(0);
	
	    theProject.setContactname("N/A");
	    
	    theProject.setContactphone("N/A");
	    theProject.setBilladdr1("N/A");
            theProject.setBilladdr2("N/A");

theProject.setBilladdr3("N/A");
theProject.setCity("N/A");
theProject.setState("N/A");
theProject.setZip("N/A");
theProject.setAccountid("N/A");
theProject.setNotifycontact(cemail);
String institute ;

	             if  (theProposal.Isucsd()) {
		      institute = "UCSD";
                	theProject.setOutside(false);
            	    } else {
		      institute = "Non-UCSD";
                	theProject.setOutside(true);
            	     }
 
                	theProject.setExp(false);
            	     
		     theProject.setInstitute(institute);
theProject.setExpday(enddatei);
theProject.setExpmonth(endmonthi);
theProject.setExpyear(endyeari+1);

	        theProject.setOwner(PersonFactory.findPersonByID(personID));
theProject.setProposalID(proposalID);
theProject.setCancredit(1);


theProject.setFpemail(" ");
theProject.setPOnum("0");
theProject.setPOexpdate(java.sql.Date.valueOf("2010-09-31"));
theProject.setPOhours(0);
theProject.setIACUCDate(java.sql.Date.valueOf("2010-01-01"));
theProject.setModifiedby("Ghobrial");
theProject.setModDate(java.sql.Date.valueOf("2010-04-30"));
theProject.setNotes("*");
theProject.setIRBnum("0");

System.out.println(" Person ID "+ personID);

System.out.println(" trying to saving a project two "+ proj_name);
	        theProject.save();	
System.out.println(" trying to saving a project three "+ proj_name);
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding project", ex);
        }                                         


//}
 
                                                                                                
                                                                                                                                  
// 2. Also if the proposal requires night time add a night time pilot project

  
 
 



// find operator if null add

Operator theOperator = null;

try {
theOperator = OperatorFactory.findOperatorByemail( op1lname,op1email);
//if (theOperator != null)
//opID = theOperator.getHandle();
   } catch(Exception ex) {
            throw new webschedulePresentationException("Error getting operator", ex);
        }    


if (theOperator == null)
//add operator
  {
  try {
            // Now check that the login name is not taken.
	            Operator user = new Operator();
	            user.setFirstname(op1fname);
	            user.setLastname(op1lname);
	            user.setEmail(op1email);
		    
		    user.setCertday(certday);
	            user.setCertmonth(certmonth);
		     user.setCertyear(certyear);
		     
		      user.setrecertday(certday);
	            user.setrecertmonth(certmonth);
		     user.setrecertyear(certyear);
		     
		      user.setlastscanday(certday);
	            user.setlastscanmonth(certmonth);
		     user.setlastscanyear(certyear);
		     user.setSFTTS(Timestamp.valueOf("2006-11-5 00:00:00.00"));
	                      
	            //Add the operator to the database.
	            user.save();
		   } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding operator", ex);
        }    
  }
//3. Let us link operator and project	
	
try {
theOperator = OperatorFactory.findOperatorByemail( op1lname,op1email);	 
Person person = PersonFactory.findPersonByID(personID);
// after adding the Project let us get it handle
  Project cproject = ProjectFactory.findProjectByProj_nameOwner(aproj_name, person);
 Operates theOperates = new Operates();
		theOperates.setOperator(theOperator);
		theOperates.setProject(cproject);
		theOperates.setIsExp(false);
		theOperates.save();
		
	  } catch(Exception ex) {
            throw new webschedulePresentationException("Error getting operator", ex);
        }   
	
 if (funduc) {
	try {
theOperator = OperatorFactory.findOperatorByemail( op1lname,op1email);	 
Person person = PersonFactory.findPersonByID(personID);
// after adding the Project let us get it handle
  Project cproject = ProjectFactory.findProjectByProj_nameOwner(paproj_name, person);
 Operates theOperates = new Operates();
		theOperates.setOperator(theOperator);
		theOperates.setProject(cproject);
		theOperates.setIsExp(false);
		theOperates.save();
		
	  } catch(Exception ex) {
            throw new webschedulePresentationException("Error getting operator", ex);
        }   	
 }
 }
 
 
   protected void updateProposalStatus(Proposal theProposal)
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
  
    
    
    
    
   /*   if (theProposal.Isucsd())
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
             	theProposal.setIACUCFaxed(theProposal.IACUCFaxed());*/
   	theProposal.setStatus(7);
/*	theProposal.setComments(theProposal.getComments());
	
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
		     
	if(null != this.getComms().request.getParameter(ATONFILE)) {
                	theProposal.setAnimalTrans(true);
            	    } else {
                	theProposal.setAnimalTrans(false);
           	     }	     
	
	 java.sql.Date jsqlIC;
  String tempIC = icyear+"-"+icmonth+"-"+icdate;   
    jsqlIC = java.sql.Date.valueOf(tempIC);
   theProposal.setIACUCDate(jsqlIC);
  
    
     java.sql.Date jsqlAT;
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
String pbookmark;

 from = "appsadmin";
    
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
	
subject = "Proposal "+aproj_name+" Research Time Scheduling @ 3TW ";

if (pbookmark.equals("humanns"))
 {

String[] msgTextns = {"Dear Dr. "+pilname+",",
      "\n",
"Your proposal titled \""+proj_name+"\" (ID number: "+proposalid+") has been added to the webschedule program.\n",

		      "\n",
	"Please use the URL below to schedule your research time:\n",
"http://fmri.ucsd.edu/webschedule.html\n",
"   Please do not respond to this e-mail.  \n",	
		    " \n",
		    "Thank you,\n",
		    "\n",
		    "The CFMRI Human Neuroscience Committee "};	
		    
 message = msgTextns;

	 to = to+",eghobrial@ucsd.edu,momalley@ucsd.edu";	   
	  }
	else 
{
  String[] msgTextbi = {"Dear Dr. "+pilname+",",
     "\n",
"Your proposal titled \""+proj_name+"\" (ID number: "+proposalid+") has been added to the webschedule program.\n",

		      "\n",
	"Please use the URL below to schedule your research time:\n",
"http://fmri.ucsd.edu/webschedule.html\n",
"   Please do not respond to this e-mail.  \n",	
		    " \n",
		    "Thank you,\n",
		    "\n",
		    "The CFMRI Bioimaging Committee"};	
		    
  message = msgTextbi;

	 to = to+",eghobrial@ucsd.edu,momalley@ucsd.edu";	   
	  }
  	    
      try {
    	    SendMail sch_not;	

 sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }
}

}


