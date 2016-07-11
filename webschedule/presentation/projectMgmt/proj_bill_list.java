/**--------------------------------------------------------------
* Webschedule
*
* @class: 
* @version
* @author: Eman Ghobrial
* @since: Sept 2002
* write to an upload file May 2007
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
import webschedule.SendMailLL;
import webschedule.SendMail;

import java.util.Calendar;
import java.util.Date;
import java.io.*;


/**
 * proj_bill_list.java shows the project list for either outside or inside projects
 *
 */
public class proj_bill_list extends BasePO
{

/*private static int PRIMERATE = 450;
    private static int NONPRIMERATE = 250;
    private static int HILLCRESTRATEWT = 350;
    private static int HILLCRESTRATEWOT = 250;
    private static int FMRI7TRATE = 250;*/

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
	String indexnum = null;
	Project[] ProjList ;
	int seq = 0;
	
	String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};
        	       
	proj_bill_listHTML page = new proj_bill_listHTML();
	String billreportflag = this.getbillreportflag();
	int billsday = this.getbillsday();
	int billeday = this.getbilleday();
	int billmonth = this.getbillmonth();
	int billyear = this.getbillyear();
	
	FileWriter file_writer;
	BufferedWriter buf_writer;
	PrintWriter print_writer;
	
	
		
/*File file = null;
		
	try {
	file = new File ("/home/emang/billing_files/rtest.txt");
	System.out.println ("file name= "+file);
	} catch (IOException ioe) {
	System.out.println("IO Exception");
	}*/
		
		
	System.out.println("flag off proj_bill_list"+billreportflag);
	
	int sdate = this.getbillsday();
	System.out.println("bill start date"+Integer.toString(sdate));

page.setTextMonth(monthnames[billmonth]);
page.setTextYear(Integer.toString(billyear));	

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
	    HTMLElement thoursCellTemplate = page.getElementThours();
	    HTMLElement tchargesCellTemplate = page.getElementTcharges();
        
	    projnameCellTemplate.removeAttribute("id");
	    firstnameCellTemplate.removeAttribute("id");
	    lastnameCellTemplate.removeAttribute("id");
	    discribCellTemplate.removeAttribute("id");
indexnumCellTemplate.removeAttribute("id");
	    thoursCellTemplate.removeAttribute("id");
	    tchargesCellTemplate.removeAttribute("id");
        
        // Remove the dummy storyboard text from the prototype HTML
        // (e.g., " One") from this option
        templateOption.removeChild(templateOption.getFirstChild());

String filename="/home/emang/billing_files/"+monthnames[billmonth]+billyear+"recharge7TW.txt";

try {		
         file_writer = new FileWriter (filename);
	  buf_writer = new BufferedWriter (file_writer);
	   print_writer = new PrintWriter (buf_writer,true);
     
	
try {	
	
	    try {
	       ProjList = ProjectFactory.getProjectsList();
	        } catch  (Exception ex) {
        throw new webschedulePresentationException("Error getting project information", ex);
	}
	
	/*File file = null;
		
	file = new File ("/home/emang/billing_files/rtest.txt");	
	System.out.println ("file name= "+file);
	*/
	
	//try {
	/* FileWriter file_writer = new FileWriter (file);
	 BufferedWriter buf_writer = new BufferedWriter (file_writer);
	 PrintWriter print_writer = new PrintWriter (buf_writer,true);*/
	 
	 print_writer.println ("Seq"+",Type,Description,"+"Reference"+","+"Amount,D/C,Code,"+"Index,"+"Fund,Org"+",Account,Prog");
	
//} catch (IOException ioe) {
	//System.out.println("IO Exception");
	//}
	 	
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


if ((currentProj.isOutside())&&(billreportflag == "INVOICE")&&(currentProj.getCodeofpay()!= 0)&&(currentProj.getCodeofpay()!=100))
	    {	
	      try
	           { 	
		  proj_name = currentProj.getProj_name();
		  lastname = currentProj.getUserLastName();
		  firstname = currentProj.getUserFirstName();
		  paycode = currentProj.getCodeofpay();
	  	  descrip = currentProj.getDescription();
		  indexnum = currentProj.getPOnum();
	   } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Project information", ex);
        }   // assign payment cat.
	       
	pay = getPay(paycode,billmonth,billyear);
					
totalhours = displayprojtable(currentProj,billyear,billmonth,billsday,billeday);
monthtotals=monthtotals+pay*totalhours;


 acutotalhours=totalhours+acutotalhours;
   

	
//	String filename = 
	
	      
	 if (totalhours > 0)
	{    
	        page.setTextProj_name(proj_name);
		page.setTextFirst_name(firstname);
		page.setTextLast_name(lastname);
	 	page.setTextDiscrib(descrip);
if (indexnum == null)
{
indexnum = "N/A";
}
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
		
/* This is a recharge,  calculate and print to a file rtest.txt*/		
if (!(currentProj.isOutside())&&(billreportflag=="IFIS")&&(currentProj.getCodeofpay()!=0)&&(currentProj.getCodeofpay()!=100))
		    {	
		        try
		 	{ 	
			proj_name = currentProj.getProj_name();
		  	lastname = currentProj.getUserLastName();
		  	firstname = currentProj.getUserFirstName();
		  	paycode = currentProj.getCodeofpay();
			indexnum = currentProj.getIndexnum();
	  	  	descrip = currentProj.getDescription();
	   		} catch(webscheduleBusinessException ex) {
        		    	this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            			throw new webschedulePresentationException("System error finding Project information", ex);
        		}
			
			
			  pay = getPay(paycode,billmonth,billyear);
				
	
		   
	totalhours=displayprojtable(currentProj,billyear,billmonth,billsday,billeday);   
	monthtotals=monthtotals+pay*totalhours;

acutotalhours=totalhours+acutotalhours;
	


	if (totalhours > 0)
	{
seq = seq +1;
print_writer.println (seq+",F724,use of fMRI research facility,"+monthnames[billmonth]+" "+billyear+","+totalhours*pay+",D,A,"+indexnum+",,"+",637510,");

 if (print_writer.checkError()) {
    System.out.println("An error occurred !");
    }
	//file = new File ("rtest.txt");	
//formatter.format (proj_name+","+firstname+","+lastname+","+descrip+",%6.2e"+totalhours+",%6.2e"+totalhours*pay+"%n");

	      page.setTextProj_name(proj_name);
	      page.setTextFirst_name(firstname);
	      page.setTextLast_name(lastname);
	      page.setTextDiscrib(descrip);
if (indexnum == null)
{
indexnum = "N/A";
}
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
		
/* Free Time List for Dev and Pilot hours */		
		
if ((billreportflag == "Free")&&((currentProj.getCodeofpay()== 0)||(currentProj.getCodeofpay()==100)))
	    {	
	      try
	           { 	
		  proj_name = currentProj.getProj_name();
		  lastname = currentProj.getUserLastName();
		  firstname = currentProj.getUserFirstName();
		  paycode = currentProj.getCodeofpay();
	  	  descrip = currentProj.getDescription();
indexnum = currentProj.getIndexnum();
	   } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Project information", ex);
        }   // assign payment cat.

		   
		     pay = getPay(paycode,billmonth,billyear);
					
totalhours = displayprojtable(currentProj,billyear,billmonth,billsday,billeday);
monthtotals=monthtotals+pay*totalhours;

acutotalhours=totalhours+acutotalhours;
        
 System.out.println("Total hours  **1**"+acutotalhours);
	      
	      if (totalhours > 0)
	{	
	
                        page.setTextProj_name(proj_name);
			page.setTextFirst_name(firstname);
			page.setTextLast_name(lastname);
	      		page.setTextDiscrib(descrip);
			if (indexnum == null)
{
indexnum = "N/A";
}
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
	
	}	
	          	
   } catch(Exception ex) {
       this.writeDebugMsg("Error populating Proj catalog: " + ex);
       throw new webschedulePresentationException("Error getting Projs for user: ", ex);
    }
    
    //remarked by EG check the file exception
 print_writer.println (seq+",F724,use of fMRI research facility,"+""+","+monthtotals+",C,A,"+"RAD1104"+",,"+",693900,"); 
 if (print_writer.checkError()) {
    System.out.println("An error occurred !");
    }
	
	 } catch (IOException ioe) {
	System.out.println("IO Exception");
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
    
    protected double displayprojtable (Project project, int billyear,int billmonth,int billsday,int billeday)
	throws webschedulePresentationException
	{
	S_event[]	s_event_list;
	
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
	     double totalh = 0.0;
   	     double nonprimepart = 0.0, primepart = 0.0;
   	     double totalcost = 0.0;
	     double unitcost = 0.0;
	     double extendedcost = 0.0;
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
	   
	s_event_list=S_eventFactory.findS_eventsPeriodForProject(billyear,billmonth,billsday, billeday, project);
       } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding EVENTs: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding EVENTs", ex);
        }
	
	
	  //calculation for the time the bill has been generated
    		Calendar cancelinfo = Calendar.getInstance();
    		int Ctodaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    		int Ctodaymonth = cancelinfo.get(cancelinfo.MONTH);
    		int Ctodayyear = cancelinfo.get(cancelinfo.YEAR);
	  
	  
	    pay = getPay(paycode,billmonth,billyear);
	    
    		   // assign payment cat.
	    /*    if (paycode == 0)
	           {
	         	pay = 0;
	         	
	            }	
	        else if (paycode == 1)
	           {
	           pay = PRIMERATE;
	       	    }	
	       	else if (paycode == 2)
	       	         {
	       	    pay = HILLCRESTRATEWT;
	       	     }
	       	  else if (paycode == 3)
	       	    {
	       	      pay = HILLCRESTRATEWOT ;
	       	     
	       	   }
		    else if (paycode == 9)
	       	    {
	       	      pay = FMRI7TRATE ;
	       	     
	       	   }*/
		   
   for (int numEvents = 0; numEvents<s_event_list.length; numEvents++)
	 {
	     try {
	    	starthi = s_event_list[numEvents].getStarth();
	    	System.out.println("Start time "+Integer.toString(starthi));
            	startmi = s_event_list[numEvents].getStartm();
            	endhi = s_event_list[numEvents].getEndh();
            	endmi = s_event_list[numEvents].getEndm();
            	description = s_event_list[numEvents].getDescription();
            	eventday =  s_event_list[numEvents].getEventday();
            	eventmonth =  s_event_list[numEvents].getEventmonth();
            	eventyear =  s_event_list[numEvents].getEventyear();
            	dow =   s_event_list[numEvents].getEventdayofw();
            	todayday = s_event_list[numEvents].getTodayday() ;
            	todaymonth = s_event_list[numEvents].getTodaymonth() ;
            	todayyear = s_event_list[numEvents].getTodayyear() ;
            	proj_id = s_event_list[numEvents].getProjectID();
           	isRepeat = s_event_list[numEvents].isRepeat();
           	
	
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
		
		totalh = totalh+eventhours;
 		   
    		unitcost = pay;
                extendedcost = eventhours*pay;
		totalcost = totalcost + extendedcost;
	  }	
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
                 throw new ClientPageRedirectException(PROJ_BILL_LIST_PAGE);
                 // Show error message that project not found
            } else {
	    
	    System.out.println("ProjectID off proj_bill_list"+proj_id);
	System.out.println("Billreportflag off proj_bill_list"+billreportflag);
	
            	this.setbillprojID(proj_id);
		if ((billreportflag == "IFIS") || (billreportflag == "Free")){
            	 throw new ClientPageRedirectException(RECHARGE_PAGE);
		} else {
            	 throw new ClientPageRedirectException(INVOICE_PAGE);
		}
            }
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding PROJECT: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding PROJECT", ex);
        }
    }

}


