/**--------------------------------------------------------------
* Webschedule
*
* @class: proj_FY_list
* @version
* @author: Eman Ghobrial
* @since: Jan 2005
* @updated: July 2010 added Fiscal Year View
*--------------------------------------------------------------*/

package webschedule.presentation.projectMgmt;

import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.presentation.BasePO;
import webschedule.business.s_event.*;
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
import java.util.Date;

/**
 * proj_bill_list.java shows the project list for either outside or inside projects
 *
 */
public class proj_FY_list extends BasePO
{

private static String PROJ_ID = "projID";

    /**
     * Superclass method override
     */
    public boolean loggedInUserRequired()
    {
        return true;
    }

/**
     * Default event. Just show the page.
     */
    public String handleDefault()
        throws webschedulePresentationException
    {        
       boolean isoutside = false;
	String eflag = null;
	double monthtotals=0.0;
	double totalhours = 0.0;
	double acutotalhours = 0.0;
	int paycode=0;
	int pay = 0;
	String lastname = null;
	     String firstname = null;
	 String descrip = null;
	 String proj_name = null;
	 String institute = null;
	  String indexnum = null;
	Project[] ProjList ;
	
	String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};
        
	       
	    proj_FY_listHTML page = new proj_FY_listHTML();
	    String billreportflag = this.getbillreportflag();
	   // int billsmonth = this.getbillsmonth();
	//int billemonth = this.getbillemonth();
	// do not realy care about month info
	int billsmonth = 1;
	int billemonth = 1;
	//year is first part year
	int billyear = this.getbillyear();
		
	
	System.out.println("flag off proj_bill_list"+billreportflag);
	
	int smonth = this.getbillsmonth();
	System.out.println("bill start month"+Integer.toString(smonth));

	int emonth = this.getbillemonth();
	System.out.println("bill start month"+Integer.toString(emonth));

/*page.setTextSmonth(monthnames[billsmonth]);
page.setTextEmonth(monthnames[billemonth]);*/

page.setTextYear(Integer.toString(billyear));
	page.setTextYeare(Integer.toString(billyear+1));

	if (billreportflag == "IFIS") {
	    isoutside = false;
	} else {
	   isoutside = true;
	}       
        
        String errorMsg = this.getSessionData().getAndClearUserMessage();
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	
	    // Generate the Proj list (either external or internal and create the HTML template references
	    HTMLTableRowElement templateRow = page.getElementTemplateRow();
        HTMLOptionElement templateOption = page.getElementTemplateOption();
        Node ProjTable = templateRow.getParentNode();
        Node ProjSelect = templateOption.getParentNode();
	    		
	    // Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
	    templateRow.removeAttribute("id");
        templateOption.removeAttribute("id");
        
        // Remove id attributes from the cells in the template row
        HTMLElement projnameCellTemplate = page.getElementProj_name();
 HTMLElement firstnameCellTemplate = page.getElementFirst_name();
  HTMLElement lastnameCellTemplate = page.getElementLast_name();
	    HTMLElement discribCellTemplate = page.getElementDiscrib();
	      HTMLElement indexnumCellTemplate = page.getElementIndexnum();
	    HTMLElement instituteCellTemplate = page.getElementInstitute();
	    HTMLElement thoursCellTemplate = page.getElementThours();
	    HTMLElement tchargesCellTemplate = page.getElementTcharges();
        
	    projnameCellTemplate.removeAttribute("id");
	    firstnameCellTemplate.removeAttribute("id");
	    lastnameCellTemplate.removeAttribute("id");
	    discribCellTemplate.removeAttribute("id");
	    indexnumCellTemplate.removeAttribute("id");
	    instituteCellTemplate.removeAttribute("id");
	    thoursCellTemplate.removeAttribute("id");
	    tchargesCellTemplate.removeAttribute("id");
        
        // Remove the dummy storyboard text from the prototype HTML
        // (e.g., " One") from this option
        templateOption.removeChild(templateOption.getFirstChild());
	
try {	
	
	    try {
	       ProjList = ProjectFactory.getProjectsList();
	        } catch  (Exception ex) {
        throw new webschedulePresentationException("Error getting project information", ex);
	}
		
		
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
        for(int numProjs = 0; numProjs < ProjList.length; numProjs++) {	
                 Project currentProj = ProjList[numProjs];
	       	// set text of new cells to values from string array

/*if  (currentProj.isOutside())
 eflag = "invoice";
else 
 eflag = "ifis";		

System.out.println("project status off proj_bill_list"+eflag);
System.out.println("flag off proj_bill_list"+billreportflag);*/


if((currentProj.isOutside())&&(billreportflag == "INVOICE")&&(currentProj.getCodeofpay()!=0)&&(currentProj.getCodeofpay()!=100)&&(currentProj.getCodeofpay()!=-1))
	    {	
	      try
	           { 	
		  proj_name = currentProj.getProj_name();
		  lastname = currentProj.getUserLastName();
		  firstname = currentProj.getUserFirstName();
		  paycode = currentProj.getCodeofpay();
	  	  descrip = currentProj.getDescription();
		  indexnum = "N/A";
		  institute = currentProj.getInstitute();
	   } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Project information", ex);
        }   

 pay = getPay(paycode,billsmonth,billyear);
					
totalhours = displayprojtable(currentProj,billyear,billsmonth,billemonth);
monthtotals=monthtotals+pay*totalhours;


 acutotalhours=totalhours+acutotalhours;
   
              
	      if (totalhours > 0)
	{	
	
                        page.setTextProj_name(proj_name);
			page.setTextFirst_name(firstname);
			page.setTextLast_name(lastname);
	      		page.setTextDiscrib(descrip);
			page.setTextInstitute(institute);
			page.setTextIndexnum(indexnum);
	       		page.setTextThours(Double.toString(totalhours));
        		page.setTextTcharges(Double.toString(totalhours*pay));
                
	        // Add a deep clone of the row to the DOM
                Node clonedNode = templateRow.cloneNode(true);
                ProjTable.appendChild(clonedNode);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjTable.insertBefore(clonedNode, templateRow);
                
                // Now populate the select list
                // This algorithm is obscure because options are not normal 
                //  HTML elements
                // First populate the option value (the Proj database ID).
                //  Then append a text child as the option
                // text, which is what is displayed as the text
                // in each row of the select box
                HTMLOptionElement clonedOption = (HTMLOptionElement) templateOption.cloneNode(true);
                clonedOption.setValue(currentProj.getHandle());
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(proj_name + ": " +
                                        descrip);
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                ProjSelect.appendChild(clonedOption);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjSelect.insertBefore(clonedOption, templateOption);
		
	          }       
		  
		}  
if (!(currentProj.isOutside())&&(billreportflag=="IFIS")&&(currentProj.getCodeofpay()!=0)&&(currentProj.getCodeofpay()!=100)&&(currentProj.getCodeofpay()!=-1))
		    {	
		        try
		 	{ 	
			proj_name = currentProj.getProj_name();
		  	lastname = currentProj.getUserLastName();
		  	firstname = currentProj.getUserFirstName();
		  	paycode = currentProj.getCodeofpay();
	  	  	descrip = currentProj.getDescription();
			 indexnum = currentProj.getIndexnum();
			 institute = currentProj.getInstitute();
	   		} catch(webscheduleBusinessException ex) {
        		    	this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            			throw new webschedulePresentationException("System error finding Project information", ex);
        		}	
	    // assign payment cat.
	    
	     pay = getPay(paycode,billsmonth,billyear);
	     
	    
					
totalhours = displayprojtable(currentProj,billyear,billsmonth,billemonth);
	   
		   

	monthtotals=monthtotals+pay*totalhours;

acutotalhours=totalhours+acutotalhours;

	if (totalhours > 0)
	{	
	      page.setTextProj_name(proj_name);
	      page.setTextFirst_name(firstname);
	      page.setTextLast_name(lastname);
	      page.setTextInstitute(institute);
	      page.setTextIndexnum(indexnum);
	      page.setTextDiscrib(descrip);
	      page.setTextThours(Double.toString(totalhours));
              page.setTextTcharges(Double.toString(totalhours*pay));
              // Add a deep clone of the row to the DOM
              Node clonedNode = templateRow.cloneNode(true);
              ProjTable.appendChild(clonedNode);
              // Alternative way to insert nodes below
              // insertBefore(newNode, oldNode);
              // ProjTable.insertBefore(clonedNode, templateRow);
              // Now populate the select list
              // This algorithm is obscure because options are not normal 
              //  HTML elements
              // First populate the option value (the Proj database ID).
              //  Then append a text child as the option
              // text, which is what is displayed as the text
              // in each row of the select box
              HTMLOptionElement clonedOption = (HTMLOptionElement) templateOption.cloneNode(true);
              clonedOption.setValue(currentProj.getHandle());
              Node optionTextNode = clonedOption.getOwnerDocument().
                      createTextNode(proj_name + ": " +
                                        descrip);
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                ProjSelect.appendChild(clonedOption);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjSelect.insertBefore(clonedOption, templateOption);
	}	
		}  
		
/* Free Time List for Dev and Pilot hours */		
		
if ((billreportflag == "Free")&&((currentProj.getCodeofpay()==0)||(currentProj.getCodeofpay()==100)||(currentProj.getCodeofpay()==-1)))
	    {	
	      try
	           { 	
		  proj_name = currentProj.getProj_name();
		  lastname = currentProj.getUserLastName();
		  firstname = currentProj.getUserFirstName();
		  paycode = currentProj.getCodeofpay();
		  indexnum="N/A";
	  	  descrip = currentProj.getDescription();
		   institute = currentProj.getInstitute();
	   } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Project information", ex);
        }   
	
	// assign payment cat.
	 pay = getPay(paycode,billsmonth,billyear);
	 
	
					
totalhours = displayprojtable(currentProj,billyear,billsmonth,billemonth);

monthtotals=monthtotals+pay*totalhours;

acutotalhours=totalhours+acutotalhours;
        
 System.out.println("Total hours  **1**"+acutotalhours);
	      
	      if (totalhours > 0)
	{	
	
                        page.setTextProj_name(proj_name);
			page.setTextFirst_name(firstname);
			page.setTextLast_name(lastname);
	      		page.setTextDiscrib(descrip);
			page.setTextIndexnum(indexnum);
			page.setTextInstitute(institute);
	       		page.setTextThours(Double.toString(totalhours));
        		page.setTextTcharges(Double.toString(totalhours*pay));
                
	        // Add a deep clone of the row to the DOM
                Node clonedNode = templateRow.cloneNode(true);
                ProjTable.appendChild(clonedNode);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjTable.insertBefore(clonedNode, templateRow);
                
                // Now populate the select list
                // This algorithm is obscure because options are not normal 
                //  HTML elements
                // First populate the option value (the Proj database ID).
                //  Then append a text child as the option
                // text, which is what is displayed as the text
                // in each row of the select box
                HTMLOptionElement clonedOption = (HTMLOptionElement) templateOption.cloneNode(true);
                clonedOption.setValue(currentProj.getHandle());
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(proj_name + ": " +
                                        descrip);
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                ProjSelect.appendChild(clonedOption);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjSelect.insertBefore(clonedOption, templateOption);
		
	          }       
		  
		}  		
		
		
		
	}	
	          	
   } catch(Exception ex) {
       this.writeDebugMsg("Error populating Proj catalog: " + ex);
       throw new webschedulePresentationException("Error getting Projs for user: ", ex);
    }
    
    System.out.println("Total hours  "+acutotalhours);
	    
    page.setTextTotalhours(Double.toString(acutotalhours));	    
    page.setTextSumcharges(Double.toString(monthtotals));
    // Finally remove the template row and template select option 
    //  from the page
    templateRow.getParentNode().removeChild(templateRow);
    templateOption.getParentNode().removeChild(templateOption);
	    // write out HTML
	    return page.toDocument();
    }
    
    protected double displayprojtable (Project project, int billyear,int billsmonth,int billemonth)
	throws webschedulePresentationException
	{
	S_event[]	s_event_list1;
	S_event[]	s_event_list2;
	 int starthi = 0, startmi = 0, endhi = 0, endmi = 0;
    	     String description = null;
             int todayday = 0;
    	     int todaymonth = 0;
    	     int todayyear = 0;
    	     int dow = 0;
    	     int eventday = 0, eventmonth = 0, eventyear = 0;
    	     String proj_id = null;
    	     boolean isRepeat = false;
    	     Project  theProject = null;
    	     int paycode = 0;
    	     double starttime = 0.0;
             double endtime = 0.0;
   	     double eventhours = 0.0;
	     double totalh1 = 0.0;
	     double totalh2 = 0.0;
	     double totalh = 0.0;
   	     double nonprimepart = 0.0, primepart = 0.0;
   	     double totalcost1 = 0.0;
	     double totalcost2 = 0.0;
	     double totalcost = 0.0;
	     double unitcost = 0.0;
	     double extendedcost1 = 0.0;
	     double extendedcost2 = 0.0;
   	     int pay = 0;
	     
	     
	     String proj_name = null;
	    
	
	
	try
	  {
	   paycode = project.getCodeofpay();
	   } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Project information", ex);
        }
	 
		  
	  try 
	  {
	   
	s_event_list1=S_eventFactory.findS_eventsFY1ForProject(billyear,billsmonth, billemonth, project);
	for (int numEvents = 0; numEvents<s_event_list1.length; numEvents++)
	 {
	     
	    	starthi = s_event_list1[numEvents].getStarth();
	    	System.out.println("Start time "+Integer.toString(starthi));
            	startmi = s_event_list1[numEvents].getStartm();
            	endhi = s_event_list1[numEvents].getEndh();
            	endmi = s_event_list1[numEvents].getEndm();
            	description = s_event_list1[numEvents].getDescription();
            	eventday =  s_event_list1[numEvents].getEventday();
            	eventmonth =  s_event_list1[numEvents].getEventmonth();
            	eventyear =  s_event_list1[numEvents].getEventyear();
            	dow =   s_event_list1[numEvents].getEventdayofw();
            	todayday = s_event_list1[numEvents].getTodayday() ;
            	todaymonth = s_event_list1[numEvents].getTodaymonth() ;
            	todayyear = s_event_list1[numEvents].getTodayyear() ;
            	proj_id = s_event_list1[numEvents].getProjectID();
           	isRepeat = s_event_list1[numEvents].isRepeat();
           	
			
    		if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    		else starttime = (double) starthi;
    	
    		if (endmi == 30)
    		endtime =     endhi + 0.5;
    		else endtime = (double) endhi;
    		eventhours = endtime - starttime;
		
		totalh1 = totalh1+eventhours;
 		 System.out.println("Total hours FY1  "+totalh1);  
    		/*unitcost = pay;
                extendedcost1 = eventhours*pay;
		totalcost1 = totalcost1 + extendedcost1;*/
	  }	
	  
	billyear=billyear+1;
	s_event_list2=S_eventFactory.findS_eventsFY2ForProject(billyear,billsmonth, billemonth, project);
	  for (int numEvents = 0; numEvents<s_event_list2.length; numEvents++)
	 {
	      	starthi = s_event_list2[numEvents].getStarth();
	    	System.out.println("Start time "+Integer.toString(starthi));
            	startmi = s_event_list2[numEvents].getStartm();
            	endhi = s_event_list2[numEvents].getEndh();
            	endmi = s_event_list2[numEvents].getEndm();
            	description = s_event_list2[numEvents].getDescription();
            	eventday =  s_event_list2[numEvents].getEventday();
            	eventmonth =  s_event_list2[numEvents].getEventmonth();
            	eventyear =  s_event_list2[numEvents].getEventyear();
            	dow =   s_event_list2[numEvents].getEventdayofw();
            	todayday = s_event_list2[numEvents].getTodayday() ;
            	todaymonth = s_event_list2[numEvents].getTodaymonth() ;
            	todayyear = s_event_list2[numEvents].getTodayyear() ;
            	proj_id = s_event_list2[numEvents].getProjectID();
           	isRepeat = s_event_list2[numEvents].isRepeat();
            		
    		if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    		else starttime = (double) starthi;
    	
    		if (endmi == 30)
    		endtime =     endhi + 0.5;
    		else endtime = (double) endhi;
    		eventhours = endtime - starttime;
		
		totalh2 = totalh2+eventhours;
		System.out.println("Total hours FY2  "+totalh2); 
 		   
    		/*unitcost = pay;
                extendedcost2 = eventhours*pay;
		totalcost2 = totalcost2 + extendedcost2;*/
	  }	
	
       } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding EVENTs: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding EVENTs", ex);
        }
	
	
	  //calculation for the time the bill has been generated
    		Calendar cancelinfo = Calendar.getInstance();
    		int Ctodaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    		int Ctodaymonth = cancelinfo.get(cancelinfo.MONTH);
    		int Ctodayyear = cancelinfo.get(cancelinfo.YEAR);
	  
    		   // assign payment cat.
		    pay = getPay(paycode,billsmonth,billyear);
		    
		   
/*   for (int numEvents = 0; numEvents<s_event_list1.length; numEvents++)
	 {
	     try {
	    	starthi = s_event_list1[numEvents].getStarth();
	    	System.out.println("Start time "+Integer.toString(starthi));
            	startmi = s_event_list1[numEvents].getStartm();
            	endhi = s_event_list1[numEvents].getEndh();
            	endmi = s_event_list1[numEvents].getEndm();
            	description = s_event_list1[numEvents].getDescription();
            	eventday =  s_event_list1[numEvents].getEventday();
            	eventmonth =  s_event_list1[numEvents].getEventmonth();
            	eventyear =  s_event_list1[numEvents].getEventyear();
            	dow =   s_event_list1[numEvents].getEventdayofw();
            	todayday = s_event_list1[numEvents].getTodayday() ;
            	todaymonth = s_event_list1[numEvents].getTodaymonth() ;
            	todayyear = s_event_list1[numEvents].getTodayyear() ;
            	proj_id = s_event_list1[numEvents].getProjectID();
           	isRepeat = s_event_list1[numEvents].isRepeat();
           	
	
	       	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
	          throw new webschedulePresentationException("Error getting event info.", ex);
    		 }
    		
    		if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    		else starttime = (double) starthi;
    	
    		if (endmi == 30)
    		endtime =     endhi + 0.5;
    		else endtime = (double) endhi;
    		eventhours = endtime - starttime;
		
		totalh1 = totalh1+eventhours;
 		 System.out.println("Total hours FY1  "+totalh1);  
    		/*unitcost = pay;
                extendedcost1 = eventhours*pay;
		totalcost1 = totalcost1 + extendedcost1;
	  }	
	  
	  
	  for (int numEvents = 0; numEvents<s_event_list2.length; numEvents++)
	 {
	     try {
	    	starthi = s_event_list2[numEvents].getStarth();
	    	System.out.println("Start time "+Integer.toString(starthi));
            	startmi = s_event_list2[numEvents].getStartm();
            	endhi = s_event_list2[numEvents].getEndh();
            	endmi = s_event_list2[numEvents].getEndm();
            	description = s_event_list2[numEvents].getDescription();
            	eventday =  s_event_list2[numEvents].getEventday();
            	eventmonth =  s_event_list2[numEvents].getEventmonth();
            	eventyear =  s_event_list2[numEvents].getEventyear();
            	dow =   s_event_list2[numEvents].getEventdayofw();
            	todayday = s_event_list2[numEvents].getTodayday() ;
            	todaymonth = s_event_list2[numEvents].getTodaymonth() ;
            	todayyear = s_event_list2[numEvents].getTodayyear() ;
            	proj_id = s_event_list2[numEvents].getProjectID();
           	isRepeat = s_event_list2[numEvents].isRepeat();
           	
	
	       	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
	          throw new webschedulePresentationException("Error getting event info.", ex);
    		 }
    		
    		if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    		else starttime = (double) starthi;
    	
    		if (endmi == 30)
    		endtime =     endhi + 0.5;
    		else endtime = (double) endhi;
    		eventhours = endtime - starttime;
		
		totalh2 = totalh2+eventhours;
		System.out.println("Total hours FY2  "+totalh2); 
 		   
    		unitcost = pay;
                extendedcost2 = eventhours*pay;
		totalcost2 = totalcost2 + extendedcost2;
	  }	*/
	  
	  
	totalh = totalh1+ totalh2;  
	  
	  System.out.println("****Total hours FY*****  "+totalh); 
	  
   return totalh;
    }
    

  public String handleSelectProj()
        throws HttpPresentationException
    {

        String proj_id = this.getComms().request.getParameter(PROJ_ID);
	Project project = null;
	String billreportflag = this.getbillreportflag();



      try {
            project = ProjectFactory.findProjectByID(proj_id);
            if((null == project) || (proj_id == "invalidID")){
            	this.getSessionData().setUserMessage("Please choose a valid project ");
                 throw new ClientPageRedirectException(PROJ_FY_LIST_PAGE);
                 // Show error message that project not found
            } else {
	    
	    System.out.println("ProjectID off proj_bill_list"+proj_id);
	System.out.println("Billreportflag off proj_bill_list"+billreportflag);
	
            	this.setbillprojID(proj_id);
	//	if ((billreportflag == "IFIS") || (billreportflag == "Free")){
if (billreportflag == "Free"){
            	 throw new ClientPageRedirectException(FY_DEV_PAGE);
		} else {
        //    	 throw new ClientPageRedirectException(FY_PAID_PAGE);
    	 throw new ClientPageRedirectException(FY_DEV_PAGE);
		}
            }
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding PROJECT: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding PROJECT", ex);
        }
    }

}


