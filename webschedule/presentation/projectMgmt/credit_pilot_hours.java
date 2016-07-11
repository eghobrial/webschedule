/**--------------------------------------------------------------
* Webschedule
*
* @class: 
* @version
* @author: Eman Ghobrial
* @since: March 2005
*
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
import java.util.LinkedList;
import java.lang.String;

/**
 * proj_bill_list.java shows the project list for either outside or inside projects
 *
 */
public class credit_pilot_hours extends BasePO
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
	double totalhours = 0.0 ;
	double thours=0.0;
	double acutotalhours = 0.0;
	int paycode=0;
	int pay = 0;
	String lastname = null;
	String firstname = null;
	String descrip = null;
	String proj_name = null;
	String pilot_proj_name = null;
	Project[] ProjList ;
	String PIemail = null;
	String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};
        double pilothours=0.0;		       
	
	credit_pilot_hoursHTML page = new credit_pilot_hoursHTML();
	/*String billreportflag = this.getbillreportflag();*/
	
	int billmonth = this.getbillmonth();
	int billyear = this.getbillyear();
		

	
	//int sdate = this.getbillsday();
	//System.out.println("bill start date"+Integer.toString(sdate));

page.setTextMonth(monthnames[billmonth]);
page.setTextYear(Integer.toString(billyear));	

	
        
        String errorMsg = this.getSessionData().getAndClearUserMessage();
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	
	    // Generate the Proj list (either external or internal and create the HTML template references
	    HTMLTableRowElement templateRow = page.getElementTemplateRow();
        
        Node ProjTable = templateRow.getParentNode();
        
	    		
	    // Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
	    templateRow.removeAttribute("id");
        
        
        // Remove id attributes from the cells in the template row
        HTMLElement projnameCellTemplate = page.getElementProj_name();
 HTMLElement firstnameCellTemplate = page.getElementFirst_name();
  HTMLElement lastnameCellTemplate = page.getElementLast_name();
	    HTMLElement pilot_projCellTemplate = page.getElementPilot_proj();
	    HTMLElement thoursCellTemplate = page.getElementThours();
	    HTMLElement pilot_hoursCellTemplate = page.getElementPilot_hours();
        
	    projnameCellTemplate.removeAttribute("id");
	    firstnameCellTemplate.removeAttribute("id");
	    lastnameCellTemplate.removeAttribute("id");
	    pilot_projCellTemplate.removeAttribute("id");
	    thoursCellTemplate.removeAttribute("id");
	    pilot_hoursCellTemplate.removeAttribute("id");
        
	

	
	    try {
	       ProjList = ProjectFactory.getProjectsList();
	        } catch  (Exception ex) {
        throw new webschedulePresentationException("Error getting project information", ex);
	}
		
 LinkedList message_lines = new LinkedList();
	    message_lines.add("Report for Pilot hours for the month of:"+monthnames[billmonth]+": year"+Integer.toString(billyear));
	    message_lines.add("========================================================");
message_lines.add("Project name	 PI name	Total billed	Pilot Project	Total Pilot Hours Credit");
		
		
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
        for(int numProjs = 0; numProjs < ProjList.length; numProjs++) {	
                 Project currentProj = ProjList[numProjs];
	       	// set text of new cells to values from string array

	
	      try
	           { 	
		  proj_name = currentProj.getProj_name();
		  lastname = currentProj.getUserLastName();
		  firstname = currentProj.getUserFirstName();
		  paycode = currentProj.getCodeofpay();
	  	  descrip = currentProj.getDescription();
		   PIemail =  currentProj.getUserEmail();
	   } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Project information", ex);
        }   // assign payment cat.
	       
		   
		   pay = getPay(paycode,billmonth,billyear);
					
totalhours = displayprojtable(currentProj,billyear,billmonth);
monthtotals=monthtotals+pay*totalhours;


 acutotalhours=totalhours+acutotalhours;


   Project pilotProject = null;
   String personID  = null;
   
              
      if (totalhours > 0)
	{	
	
		pilot_proj_name = "Pilot"+proj_name;
	
	
	
    	try
    	{
    	pilotProject = ProjectFactory.findProjectByProj_name(pilot_proj_name);
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
        
	if (pilotProject != null)
	{
	
	     try
    	{    	
    		
		
    	 pilothours=totalhours/4;	
    	thours = pilotProject.getTotalhours()+totalhours/4;
	System.out.println("total hours = "+thours);
	personID = pilotProject.getUserID();
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
	
	 try {
            pilotProject.setProj_name(pilotProject.getProj_name());
            pilotProject.setPassword(pilotProject.getPassword());
	    pilotProject.setDescription(pilotProject.getDescription());
	    pilotProject.setIndexnum(pilotProject.getIndexnum());
	    pilotProject.setTotalhours(thours);
	    pilotProject.setDonehours(pilotProject.getDonehours());
            pilotProject.setCodeofpay(pilotProject.getCodeofpay());
	      
            pilotProject.setOwner(PersonFactory.findPersonByID(personID));
	    pilotProject.setContactname(pilotProject.getContactname());
	    pilotProject.setContactphone(pilotProject.getContactphone());
	    pilotProject.setBilladdr1(pilotProject.getBilladdr1());
            pilotProject.setBilladdr2(pilotProject.getBilladdr2());
	    pilotProject.setBilladdr3(pilotProject.getBilladdr3());
	    pilotProject.setCity(pilotProject.getCity());
	    pilotProject.setState(pilotProject.getState());
	    pilotProject.setZip(pilotProject.getZip());
	    pilotProject.setAccountid(pilotProject.getAccountid());
	    pilotProject.setOutside(pilotProject.isOutside());
	    pilotProject.setExpday(pilotProject.getExpday());
	    pilotProject.setExpmonth(pilotProject.getExpmonth());
	    pilotProject.setExpyear(pilotProject.getExpyear());
	   
	        pilotProject.save();	
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding pilot project", ex);
        }
		try
	{
 sendpianemail(PIemail, proj_name,pilothours,monthnames[billmonth],billyear);
 }catch(Exception ex) {
            throw new webschedulePresentationException("could not send an email", ex);
        }
message_lines.add("-"+proj_name+"     "+lastname+", "+firstname+"	"+totalhours+"	 "+pilot_proj_name +"   "+totalhours/4);
	       
	        page.setTextProj_name(proj_name);
		page.setTextFirst_name(firstname);
		page.setTextLast_name(lastname);
		page.setTextPilot_proj(pilot_proj_name);
		page.setTextThours(Double.toString(totalhours));
        	page.setTextPilot_hours(Double.toString(totalhours/4));
                
	        // Add a deep clone of the row to the DOM
                Node clonedNode = templateRow.cloneNode(true);
                ProjTable.appendChild(clonedNode);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjTable.insertBefore(clonedNode, templateRow);
                
		} //if totalhours > 0
	          }       //if not null
	}	  
    
  /*  System.out.println("Total hours  "+acutotalhours);
	    
    page.setTextTotalhours(Double.toString(acutotalhours));	    
    page.setTextSumcharges(Double.toString(monthtotals));*/
    // Finally remove the template row and template select option 
    //  from the page
    templateRow.getParentNode().removeChild(templateRow);


      try {
    	    SendMailLL sch_not;	
    	    String from = "appsadmin";
	 //   System.out.println("User email "+user_email);
    	  // String to = user_email;
	   String to = "eghobrial@ucsd.edu";
String subject=("Report for Pilot hours for the month of:"+monthnames[billmonth]+": year"+Integer.toString(billyear));
    	    System.out.println("Linked List size "+Integer.toString(message_lines.size()));
    	    for (int j = 0; j < message_lines.size(); j++)
    	      {
    	      Object line = message_lines.get(j);
    	     System.out.println("Line String "+((String) line));
    	      // message[j] = (String) line;
    	      //System.out.println("Message array "+ message);
    	    }
    	   		
           sch_not = new SendMailLL (from,to,subject,message_lines);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }

	    // write out HTML
	    return page.toDocument();
    }

 protected void sendpianemail(String PIemail,String proj_name,double pilothours,String billmonth,int billyear)   
  throws HttpPresentationException
  {
     try {
    	    SendMail sch_not;	
    	    String from = "appsadmin";
    	    String to = PIemail;
String subject="Project credit report for month  "+billmonth+"  year"+Integer.toString(billyear);
String[] message={"Your Project "+proj_name+" has been credited "+Double.toString(pilothours)+" pilot hours",
		  "proportional to your project billing",
		  "  "};
	sch_not = new SendMail(from, to , subject,message);
	
 	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }

    }
       
    
    
protected double displayprojtable (Project project, int billyear,int billmonth)
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
	   
	s_event_list=S_eventFactory.findS_eventsMonthForProject(billyear,billmonth, project);
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
  

}


