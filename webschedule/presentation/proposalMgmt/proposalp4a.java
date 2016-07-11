/**--------------------------------------------------------------
* Webschedule
*
* @class:proposalp4a
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

//import java.sql.Date;
import java.util.Calendar;
import java.util.*;
/**
 * newproposalp4.java shows the Proposal part 4 (proposal Information)
 *
 */
public class proposalp4a extends BasePO
{

/**
 * Constants
 *
 **/
    private static String ERROR_NAME = "ERROR_NAME";
    private static String THOURS = "thours";
    private static String STARTD = "startd";
    private static String ENDD = "endd";
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
     
   /*    String startdate = this.getComms().request.getParameter(STARTDATE);
     String startmonth = this.getComms().request.getParameter(STARTMONTH);
     String startyear = this.getComms().request.getParameter(STARTYEAR);
     
       String enddate = this.getComms().request.getParameter(ENDDATE);
     String endmonth = this.getComms().request.getParameter(ENDMONTH);
     String endyear = this.getComms().request.getParameter(ENDYEAR);
     */
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
     
/*     
       String icdate = this.getComms().request.getParameter(ICDATE);
     String icmonth = this.getComms().request.getParameter(ICMONTH);
     String icyear = this.getComms().request.getParameter(ICYEAR);
     
        
     String atdate = this.getComms().request.getParameter(ATDATE);
     String atmonth = this.getComms().request.getParameter(ATMONTH);
     String atyear = this.getComms().request.getParameter(ATYEAR);
  */   
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
    
        proposalp4aHTML page = new proposalp4aHTML();
	
	HTMLScriptElement script = new newproposalp4ScriptHTML().getElementRealScript();
       XMLCUtil.replaceNode(script, page.getElementDummyScript());	
	
	Date startdate= new Date();
  Date enddate = new  Date();
	
	Date atdate= new  Date();
  Date iacucdate = new  Date();
  
	
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
	

  	HTMLSelectElement	atdate_select, atmonth_select, atyear_select;
	HTMLCollection		atdate_selectCollection, atmonth_selectCollection, atyear_selectCollection;
	HTMLOptionElement	atdate_option, atmonth_option, atyear_option;
	String			atdate_optionName, atmonth_optionName, atyear_optionName;
		
		
	atdate_select = (HTMLSelectElement)page.getElementAtdate();
	atdate_selectCollection = atdate_select.getOptions();	
			
        atmonth_select = (HTMLSelectElement)page.getElementAtmonth();
	atmonth_selectCollection = atmonth_select.getOptions();	

	atyear_select = (HTMLSelectElement)page.getElementAtyear();
	atyear_selectCollection = atyear_select.getOptions();	
	

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

       System.out.println(" *** Proposal ID off p4*** "+ProposalID);
       
       int thoursi ;
       String writeups,datanas,bios,aq1,aq2,aq3,aq4,pq1,pq2,pq3,pq4,pq5,pq6,pq7,pq8;
    
    try {
	proposal = ProposalFactory.findProposalByID(ProposalID);
	
	thoursi = proposal.getThours();
	 startdate = proposal.getStdate();
	  enddate = proposal.getExpdate();
	  writeups = proposal.getWriteup();
	
	atdate = proposal.getAnimTransDate();
	  iacucdate = proposal.getIACUCDate();

//more fields of the database

 aq1=proposal.getAnimalq1();
 aq2=proposal.getAnimalq2();
 aq3= proposal.getAnimalq3();
   aq4=proposal.getAnimalq4();
   
    pq1=proposal.getProposalq1();
 pq2=proposal.getProposalq2();
  pq3=proposal.getProposalq3();
   pq4=proposal.getProposalq4();
    pq5=proposal.getProposalq5();
     pq6=proposal.getProposalq6();
      pq7=proposal.getProposalq7();
       pq8=proposal.getProposalq8();
datanas=proposal.getBioHazard();
bios=proposal.getDataanalysis();

	 
	    } catch(Exception ex) {
            return showPage("You must fill out all fields to add this project");
        }
	
	
	 System.out.println(" *** Total hours *** "+Integer.toString(thoursi));
	 System.out.println(" *** Writeup *** "+writeups); 
	 
	// System.out.println(" *** Start Date off p4*** "+startdate.toString());

if (aq1==null)
aq1="*";
if (aq2==null)
aq2="*";
if (aq3==null)
aq3="*";
if (aq4==null)
aq4="*";	
 
if (pq1==null)
pq1="*";
if (pq2==null)
pq2="*";
if (pq3==null)
pq3="*";
if (pq4==null)
pq4="*";	
if (pq5==null)
pq5="*";
if (pq6==null)
pq6="*";
if (pq7==null)
pq7="*";
if (pq8==null)
pq8="*";	

if (bios==null)
bios="*";
if (datanas==null)
datanas="*";
if (writeups==null) 
writeups="*";

//	if (writeups!=null) 

//{
try {	

	if(null != thours && thours.length() != 0) {
                page.getElementThours().setValue(thours);
            } else {
	    
                page.getElementThours().setValue(Integer.toString(proposal.getThours()));
            }
	
	
	
	 int startyeari = startdate.getYear();
     startyeari = startyeari + 1900;
     System.out.println("Project Start year  "+Integer.toString(startyeari));
     
     int startmonthi = startdate.getMonth();
     int startdatei = startdate.getDate();
     String startyears = Integer.toString(startyeari);
     String startmonths = Integer.toString(startmonthi);
     String startdates = Integer.toString(startdatei);

     int endyeari = enddate.getYear();
     endyeari = endyeari + 1900;
     System.out.println("Project end year  "+Integer.toString(endyeari));
     
     int endmonthi = enddate.getMonth();
     int enddatei = enddate.getDate();
     String endyears = Integer.toString(endyeari);
     String endmonths = Integer.toString(endmonthi);
     String enddates = Integer.toString(enddatei);
	
     int icyeari = iacucdate.getYear();
     icyeari = icyeari + 1900;
     System.out.println("End year  "+Integer.toString(endyeari));
     
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

     String atyears = Integer.toString(atyeari);
     String atmonths = Integer.toString(atmonthi);
     String atdates = Integer.toString(atdatei);
		
     if (startyeari > 2000)
      {
       int startdate_optionlen = startdate_selectCollection.getLength();
       for (int i=0; i< startdate_optionlen; i++) {
	  startdate_option = (HTMLOptionElement)startdate_selectCollection.item(i);
	  startdate_optionName = startdate_option.getValue();
	  if (startdate_optionName.equals(startdates))
	     startdate_option.setSelected(true);
	  else
	     startdate_option.setSelected(false);
	    }  

       int startmonth_optionlen = startmonth_selectCollection.getLength();
       for (int i=0; i< startmonth_optionlen; i++) {
	  startmonth_option = (HTMLOptionElement)startmonth_selectCollection.item(i);
	  startmonth_optionName = startmonth_option.getValue();
	  if (startmonth_optionName.equals(startmonths))
	     startmonth_option.setSelected(true);
	  else
	     startmonth_option.setSelected(false);
	    }  
	    
	      int startyear_optionlen = startyear_selectCollection.getLength();
	for (int i=0; i< startyear_optionlen; i++) {
	  startyear_option = (HTMLOptionElement)startyear_selectCollection.item(i);
	  startyear_optionName = startyear_option.getValue();
	  if (startyear_optionName.equals(startyears))
	     startyear_option.setSelected(true);
	  else
	     startyear_option.setSelected(false);
	    }  

        }

     if (endyeari > 2000)
     {
     int enddate_optionlen = enddate_selectCollection.getLength();
     for (int i=0; i< enddate_optionlen; i++) {
	  enddate_option = (HTMLOptionElement)enddate_selectCollection.item(i);
	  enddate_optionName = enddate_option.getValue();
	  if (enddate_optionName.equals(enddates))
	     enddate_option.setSelected(true);
	  else
	     enddate_option.setSelected(false);
	    }  

     int endmonth_optionlen = endmonth_selectCollection.getLength();
     for (int i=0; i< endmonth_optionlen; i++) {
	  endmonth_option = (HTMLOptionElement)endmonth_selectCollection.item(i);
	  endmonth_optionName = endmonth_option.getValue();
	  if (endmonth_optionName.equals(endmonths))
	     endmonth_option.setSelected(true);
	  else
	     endmonth_option.setSelected(false);
	    }  
	    
	      int endyear_optionlen = endyear_selectCollection.getLength();
	for (int i=0; i< endyear_optionlen; i++) {
	  endyear_option = (HTMLOptionElement)endyear_selectCollection.item(i);
	  endyear_optionName = endyear_option.getValue();
	  if (endyear_optionName.equals(endyears))
	     endyear_option.setSelected(true);
	  else
	     endyear_option.setSelected(false);
	    }  

        }
	
	
      if(null != writeup && writeup.length() != 0) {
	Node    writeupText = page.getElementWriteup().getOwnerDocument().createTextNode(writeup);  
                page.getElementWriteup().appendChild(writeupText);
            } else {
	  Node    writeupText = page.getElementWriteup().getOwnerDocument().createTextNode(proposal.getWriteup());  
                page.getElementWriteup().appendChild(writeupText);  
            }
	    
	    System.out.println(" *** Writeup of p4 *** ");
	 
	 
	 
	
	if(null != biohazard && biohazard.length() != 0) {
	Node    biohazardText = page.getElementBiohazard().getOwnerDocument().createTextNode(biohazard);  
                page.getElementBiohazard().appendChild(biohazardText);  
            } else {
	    Node    biohazardText = page.getElementBiohazard().getOwnerDocument().createTextNode(bios);  
                page.getElementBiohazard().appendChild(biohazardText);
            }
	
	if(null != dataanalysis && dataanalysis.length() != 0) {
        Node    dataanalysisText =page.getElementDataanalysis().getOwnerDocument().createTextNode(dataanalysis);	
	                page.getElementDataanalysis().appendChild(dataanalysisText);
            } else {
	    
	Node    dataanalysisText =page.getElementDataanalysis().getOwnerDocument().createTextNode(datanas);	
	           page.getElementDataanalysis().appendChild(dataanalysisText);    
               
            }
	
	if(null != animalq1 && animalq1.length() != 0) {
        Node    animalq1Text =page.getElementAnimalq1().getOwnerDocument().createTextNode(animalq1);	
	                page.getElementAnimalq1().appendChild(animalq1Text);
            } else {
	    
	Node    animalq1Text =page.getElementAnimalq1().getOwnerDocument().createTextNode(aq1);	
	           page.getElementAnimalq1().appendChild(animalq1Text);    
               
            }


	if(null != animalq2 && animalq2.length() != 0) {
        Node    animalq2Text =page.getElementAnimalq2().getOwnerDocument().createTextNode(animalq2);	
	                page.getElementAnimalq2().appendChild(animalq2Text);
            } else {
	    
	Node    animalq2Text =page.getElementAnimalq2().getOwnerDocument().createTextNode(aq2);	
	           page.getElementAnimalq2().appendChild(animalq2Text);    
               
            }

	if(null != animalq3 && animalq3.length() != 0) {
        Node    animalq3Text =page.getElementAnimalq3().getOwnerDocument().createTextNode(animalq3);	
	                page.getElementAnimalq3().appendChild(animalq3Text);
            } else {
	    
	Node    animalq3Text =page.getElementAnimalq3().getOwnerDocument().createTextNode(aq3);	
	           page.getElementAnimalq3().appendChild(animalq3Text);    
               
            }
	    
	    if(null != animalq4 && animalq4.length() != 0) {
        Node    animalq4Text =page.getElementAnimalq4().getOwnerDocument().createTextNode(animalq4);	
	                page.getElementAnimalq4().appendChild(animalq4Text);
            } else {
	    
	Node    animalq4Text =page.getElementAnimalq4().getOwnerDocument().createTextNode(aq4);	
	           page.getElementAnimalq4().appendChild(animalq4Text);    
               
            }


if(null != proposalq1 && proposalq1.length() != 0) {
        Node    proposalq1Text =page.getElementProposalq1().getOwnerDocument().createTextNode(proposalq1);	
	                page.getElementProposalq1().appendChild(proposalq1Text);
            } else {
	    
	Node    proposalq1Text =page.getElementProposalq1().getOwnerDocument().createTextNode(pq1);	
	           page.getElementProposalq1().appendChild(proposalq1Text);    
               
            }
	    

if(null != proposalq2 && proposalq2.length() != 0) {
        Node    proposalq2Text =page.getElementProposalq2().getOwnerDocument().createTextNode(proposalq2);	
	                page.getElementProposalq2().appendChild(proposalq2Text);
            } else {
	    
	Node    proposalq2Text =page.getElementProposalq2().getOwnerDocument().createTextNode(pq2);	
	           page.getElementProposalq2().appendChild(proposalq2Text);    
               
            }
	    
	if(null != proposalq3 && proposalq3.length() != 0) {
        Node    proposalq3Text =page.getElementProposalq3().getOwnerDocument().createTextNode(proposalq3);	
	                page.getElementProposalq3().appendChild(proposalq3Text);
            } else {
	    
	Node    proposalq3Text =page.getElementProposalq3().getOwnerDocument().createTextNode(pq3);	
	           page.getElementProposalq3().appendChild(proposalq3Text);    
               
            }
	    
	    
	    if(null != proposalq4 && proposalq4.length() != 0) {
        Node    proposalq4Text =page.getElementProposalq4().getOwnerDocument().createTextNode(proposalq4);	
	                page.getElementProposalq4().appendChild(proposalq4Text);
            } else {
	    
	Node    proposalq4Text =page.getElementProposalq4().getOwnerDocument().createTextNode(pq4);	
	           page.getElementProposalq4().appendChild(proposalq4Text);    
               
            }
    
	    if(null != proposalq5 && proposalq5.length() != 0) {
        Node    proposalq5Text =page.getElementProposalq5().getOwnerDocument().createTextNode(proposalq5);	
	                page.getElementProposalq5().appendChild(proposalq5Text);
            } else {
	    
	Node    proposalq5Text =page.getElementProposalq5().getOwnerDocument().createTextNode(pq5);	
	           page.getElementProposalq5().appendChild(proposalq5Text);    
               
            }
	    
	        
	    if(null != proposalq6 && proposalq6.length() != 0) {
        Node    proposalq6Text =page.getElementProposalq6().getOwnerDocument().createTextNode(proposalq6);	
	                page.getElementProposalq6().appendChild(proposalq6Text);
            } else {
	    
	Node    proposalq6Text =page.getElementProposalq6().getOwnerDocument().createTextNode(pq6);	
	           page.getElementProposalq6().appendChild(proposalq6Text);    
               
            }
	    
	        
	    if(null != proposalq7 && proposalq7.length() != 0) {
        Node    proposalq7Text =page.getElementProposalq7().getOwnerDocument().createTextNode(proposalq7);	
	                page.getElementProposalq7().appendChild(proposalq7Text);
            } else {
	    
	Node    proposalq7Text =page.getElementProposalq7().getOwnerDocument().createTextNode(pq7);	
	           page.getElementProposalq7().appendChild(proposalq7Text);    
               
            }
	    
	        
	    if(null != proposalq8 && proposalq8.length() != 0) {
        Node    proposalq8Text =page.getElementProposalq8().getOwnerDocument().createTextNode(proposalq8);	
	                page.getElementProposalq8().appendChild(proposalq8Text);
            } else {
	    
	Node    proposalq8Text =page.getElementProposalq8().getOwnerDocument().createTextNode(pq8);	
	           page.getElementProposalq8().appendChild(proposalq8Text);    
               
            }


       if(null != this.getComms().request.getParameter(AE1)) {
            page.getElementAe1().setChecked(true);
        } else {
            page.getElementAe1().setChecked(proposal.RFCoils());
        }
	
      if(null != this.getComms().request.getParameter(AE2)) {
            page.getElementAe2().setChecked(true);
        } else {
            page.getElementAe2().setChecked(proposal.Restraints());
        }
	
	 if(null != this.getComms().request.getParameter(AE3)) {
            page.getElementAe3().setChecked(true);
        } else {
            page.getElementAe3().setChecked(proposal.Physioeq());
        }
	
	 if(null != this.getComms().request.getParameter(AE4)) {
            page.getElementAe4().setChecked(true);
        } else {
            page.getElementAe4().setChecked(proposal.anesthetics());
        }
	
	 if(null != this.getComms().request.getParameter(AE5)) {
            page.getElementAe5().setChecked(true);
        } else {
            page.getElementAe5().setChecked(proposal.ancillary());
        }
	
	 if(null != this.getComms().request.getParameter(AE6)) {
            page.getElementAe6().setChecked(true);
        } else {
            page.getElementAe6().setChecked(proposal.Stimuli());
        }
	
	 if(null != this.getComms().request.getParameter(AE7)) {
            page.getElementAe7().setChecked(true);
        } else {
            page.getElementAe7().setChecked(proposal.Contrast());
        }
	
	 if(null != this.getComms().request.getParameter(FAXTOLINA)) {
            page.getElementFaxtolina().setChecked(true);
        } else {
            page.getElementFaxtolina().setChecked(proposal.IACUCFaxed());
        }
	
	 if(null != this.getComms().request.getParameter(NIGHTTIME)) {
            page.getElementNighttime().setChecked(true);
        } else {
            page.getElementNighttime().setChecked(proposal.Nighttime());
        }
		
	
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
	
	} catch(Exception ex) {
            return showPage("You must fill out all fields to add this proposal");
        }
     //}

	    return page.toDocument();
    }
    
    
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
	    
	      throw new ClientPageRedirectException(PREVIEW_PAGE);
	    
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
     
      if (thours.length() == 0  ) {
            return showPage("Please fillin the number of hours, can be edited later");    
        }      
	
     // if some fields are empty, generate error and redirect to this PO
/*	    if (thours.length() == 0 || writeup.length() ==0 || 
	        biohazard.length() == 0 || dataanalysis.length() == 0 ||
	        animalq1.length() == 0 || animalq2.length() == 0 ||
		animalq3.length() == 0 || animalq4.length() == 0 ||
		proposalq1.length() == 0 || proposalq2.length() == 0 ||
		proposalq3.length() == 0 || proposalq4.length() == 0 ||
		proposalq5.length() == 0 || proposalq6.length() == 0 ||
		proposalq7.length() == 0 || proposalq8.length() == 0 ) {
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }      
	*/
  
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
     
     
     // if some fields are empty, generate error and redirect to this PO
	    if (thours.length() == 0 || writeup.length() ==0 || 
	        biohazard.length() == 0 || dataanalysis.length() == 0 ||
	        animalq1.length() == 0 || animalq2.length() == 0 ||
		animalq3.length() == 0 || animalq4.length() == 0 ||
		proposalq1.length() == 0 || proposalq2.length() == 0 ||
		proposalq3.length() == 0 || proposalq4.length() == 0 ||
		proposalq5.length() == 0 || proposalq6.length() == 0 ||
		proposalq7.length() == 0 || proposalq8.length() == 0 ) {
return showPage("Missing information. Please make sure all fields are filled out exactly, if not ready please the Save for Later Editing Button");    
        }      
  
	Proposal theproposal ;
	String proposalID = this.getProposalID();
	 System.out.println(" *** Proposal ID off p4 handle preview event *** "+proposalID);
		
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
    
    theProposal.setStatus(theProposal.getStatus());
    
     if (thours == null)
    theProposal.setThours(0);
    else
    theProposal.setThours(Integer.parseInt(thours));
   
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
   
   
//   String startdts = startd + " 00:00:00.00";

  // String startdts =  "2008-01-01 00:00:00.00";
    
   java.sql.Date jsqlStart;
  String tempStart = startyear+"-"+startmonth+"-"+startdate;   
    jsqlStart = java.sql.Date.valueOf(tempStart);
   theProposal.setStdate(jsqlStart);
  
    
   java.sql.Date jsqlEnd;
  String tempEnd = endyear+"-"+endmonth+"-"+enddate;   
    jsqlEnd = java.sql.Date.valueOf(tempEnd);
   theProposal.setExpdate(jsqlEnd);
  
  /*
   java.sql.Date jsqlSD = java.sql.Date.valueOf(startd);
 theProposal.setStdate(jsqlSD);
    java.sql.Date jsqlED = java.sql.Date.valueOf(endd);
      theProposal.setExpdate(jsqlED);
  */
  
//    System.out.println(" startdts "+startdts);
  // String enddts = endd + " 00:00:00.00";
   // String enddts = "2009-01-01 00:00:00.00";
  
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
		     
		     
		     
		     
		     
	 java.sql.Date jsqlIC;
  String tempIC = icyear+"-"+icmonth+"-"+icdate;   
    jsqlIC = java.sql.Date.valueOf(tempIC);
   theProposal.setIACUCDate(jsqlIC);
  
    
     java.sql.Date jsqlAT;
   String tempAT = atyear+"-"+atmonth+"-"+atdate;   
    jsqlAT = java.sql.Date.valueOf(tempAT);
    theProposal.setAnimTransDate(jsqlAT);
		     
		     
	
    
    if(null != this.getComms().request.getParameter(NIGHTTIME)) {
                	theProposal.setNighttime(true);
            	    } else {
                	theProposal.setNighttime(false);
            	     }
        
		     
		     //do a check if the date is blank insert a dummy date 0000-00-00
	//String idts = iacucdate + " 00:00:00.00";	     
 // theProposal.setIACUCDate(Date.valueOf(iacucdate));
 //   String adts = atransfer + " 00:00:00.00";
 // theProposal.setAnimTransDate(Date.valueOf(atransfer));
    
    System.out.println(" trying to saving a proposal two ");
	        theProposal.save();	
    System.out.println(" trying to saving a proposal three off p4  ");
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding proposal", ex);
        }    
    
    
    }
     
    
}


