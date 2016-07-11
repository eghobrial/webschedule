/**--------------------------------------------------------------
* Webschedule
*
* @class: 
* @version
* @author: Eman Ghobrial
* @since: May 2006
* apply pilot hours plenty for unsed released slots.
* change debit/cedit policy May 2007
* per Tom Oct 08, no more pilot credit on pilot project
*---------------------------------------------------------------------------*/

package webschedule.presentation.projectMgmt;

import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.presentation.BasePO;
import webschedule.business.s_event.*;
import webschedule.business.r_event.*;
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
import java.util.*;
import java.lang.String;
//import webschedule.presentation.projectMgmt.Counter;


/**
 * proj_bill_list.java shows the project list for either outside or inside projects
 *
 */
public class debt_pilot_hours extends BasePO
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
	
	double thours=0.0;
	double acutotalhours = 0.0;
	
	int pay = 0;
	
	
	Project[] ProjList ;
	S_event[] s_eventList;
	R_event[] r_eventList;
	int lastday=0,day=0;
	String releasedA[];
String usedA[];
releasedA = new String[47];
usedA = new String[47];
Map unused = new HashMap();
//String personID  = null;
String PIemail = null;
//Project[] ProjList;
//double totalhours=0.0;


	
    
	
	String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};
        int daysPerMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		       
	debt_pilot_hoursHTML page = new debt_pilot_hoursHTML();
	/*String billreportflag = this.getbillreportflag();*/
	
	int billmonth = this.getbillmonth();
	int billyear = this.getbillyear();
		
	// get the last day of the month check for leap year
	if (billmonth == 1 && (billyear % 4 == 0 && billyear % 100 != 0))
		{
			lastday =  29;
		} else {
			lastday = daysPerMonth [billmonth];
		}	
//for (day = 1; day < lastday ; day++)
for (day = 1; day < lastday ; day++)
{

// 1. declare the array to hold the released and used activities
for (int t = 0; t < releasedA.length; t++){
   releasedA[t] = null;
   }

for (int t = 0; t < usedA.length; t++){
   usedA[t] = null;
   }

//loop through the whole month
 
        try {
	//2. papulate the used array
         	s_eventList = S_eventFactory.findS_eventsForDate(billyear,billmonth,day);
	        double starttime;
		double endtime;
		double eventhours;
		int startindex=0, endindex=0;
	        for(int numEvents = 0; numEvents < s_eventList.length; numEvents++) {	
		//for loop to papulate the used array
                	S_event currentS_event = s_eventList[numEvents];
	        	// set text of new cells to values from string array
	                int starthi = currentS_event.getStarth();
	        	int startmi = currentS_event.getStartm();
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	        	String projID = currentS_event.getProjectID();
	        	
	        	if (startmi == 30)
    			  {
    				double startt = (double) starthi;
    				starttime = startt + 0.5;
	         		}
    			else starttime = (double) starthi;
    	
    			if (endmi == 30)
    				endtime =     endhi + 0.5;
		    	else endtime = (double) endhi;	
		    	eventhours = endtime - starttime;
			startindex= (int) starttime*2;
			//eventhours=eventhours*2;
			endindex= startindex + (int) eventhours*2;
			for (int i = startindex ; i < endindex ; i++)
			  {
			    usedA[i]=projID;
		 System.out.println("Project ID for day "+day+" off s_event ["+i+"] "+projID);	
			    }
		}	
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);
             }	
	    
	     try {
	// 3. papulate the released array
         	r_eventList = R_eventFactory.findR_eventsForDate(billyear,billmonth,day);
	        double starttime;
		double endtime;
		double eventhours;
		
		int startindex=0, endindex=0;
	        for(int numEvents = 0; numEvents < r_eventList.length; numEvents++) {	
		//for loop to papulate the used array
                	R_event currentR_event = r_eventList[numEvents];
	        	// set text of new cells to values from string array
	                int starthi = currentR_event.getStarth();
	        	int startmi = currentR_event.getStartm();
	        	int endhi = currentR_event.getEndh();
	        	int endmi = currentR_event.getEndm();
	        	String projID = currentR_event.getProjectID();
	        	
	        	if (startmi == 30)
    			  {
    				double startt = (double) starthi;
    				starttime = startt + 0.5;
	         		}
    			else starttime = (double) starthi;
    	
    			if (endmi == 30)
    				endtime =     endhi + 0.5;
		    	else endtime = (double) endhi;	
		    	eventhours = endtime - starttime;
			startindex= (int) starttime*2;
			endindex=startindex+(int)eventhours*2;
			for (int i = startindex ; i<endindex ; i++)
			  {
			    releasedA[i]=projID;
	       System.out.println("Project ID "+day+" off r_event ["+i+"] "+projID);
			    }
		}	
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);
             }	
	     
	//compare released array with used array and papulate the unused array
	for (int j = 0 ; j < 47 ;j++)
	 {
	 
	 //System.out.println("releasedA "+releasedA[j]+" usedA "+usedA[j]+" ");
	  
//	if ((releasedA[j].equals("blank")) && (usedA[j].equals("blank")))
	if ((releasedA[j]!=null) && (usedA[j]==null))
	{
	 System.out.println("We have some unused slots ");
	   if (unused.containsKey(releasedA[j]))
	   //if (unused.get(releasedA[j]) == null)
	     {
	     String temphold = releasedA[j];
	      //useProj(releasedA[j]);
	    //  System.out.println("Project ID off add unused "+temphold);
	      Counter ctr=(Counter) unused.get(temphold);
	      ctr.bumpCount();
	      }
	     
	     else
	     {
	     String temphold = releasedA[j];
	     //System.out.println("Project ID off add unused "+temphold);
	      unused.put(temphold, new Counter());
	      Counter ctr=(Counter) unused.get(temphold);
	      ctr.bumpCount();
	      }
	   	     
	 }     
   }	  
 } //for loop		

    Iterator iter1 = unused.entrySet().iterator();
            while (iter1.hasNext()) {
                Map.Entry entry1 = 
                                (Map.Entry)iter1.next();
                System.out.println(entry1.getKey() + 
                               " " + entry1.getValue());
            }
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
    templateRow.removeAttribute("id");
    // Remove id attributes from the cells in the template row
    HTMLElement projnameCellTemplate = page.getElementProj_name();
    HTMLElement firstnameCellTemplate = page.getElementFirst_name();
    HTMLElement lastnameCellTemplate = page.getElementLast_name();
    HTMLElement pilot_projCellTemplate = page.getElementPilot_proj();
    HTMLElement thoursCellTemplate = page.getElementThours();
    HTMLElement pilot_hoursCellTemplate = page.getElementPilot_hours();
    HTMLElement creditCellTemplate = page.getElementCredit();
    HTMLElement debitCellTemplate = page.getElementDebit();
	
    projnameCellTemplate.removeAttribute("id");
    firstnameCellTemplate.removeAttribute("id");
    lastnameCellTemplate.removeAttribute("id");
    pilot_projCellTemplate.removeAttribute("id");
    thoursCellTemplate.removeAttribute("id");
    pilot_hoursCellTemplate.removeAttribute("id");
    creditCellTemplate.removeAttribute("id");
    debitCellTemplate.removeAttribute("id");
    LinkedList message_lines = new LinkedList();
    message_lines.add("Report for credited/debited Pilot hours for the month of:"+monthnames[billmonth]+": year"+Integer.toString(billyear));
    message_lines.add("========================================================");
    message_lines.add("Project name	  PI name	Total billed	  Pilot Project	    Unused Hours   Credited Hours   Debited Hours");

    try {
	       ProjList = ProjectFactory.getProjectsList();
	        } catch  (Exception ex) {
        throw new webschedulePresentationException("Error getting project information", ex);
	}
		
     //Get collection of Projs and loop through collection
     //what I am trying to do is to loop through the projects determine if they used hours or unused hours or both.
 System.out.println("num of Projects "+ProjList.length);
 
for(int numProjs = 0; numProjs < ProjList.length; numProjs++) 
      {
      double totalhours = 0.0 ;
	double debit=0.0;
	double credit=0.0;
	double allowedh=0.0;
	String CProjID = null;
	 int unusednum=0;
	double unusedn = 0.0; 
	int hasused =  0;
	int hasunused = 0;
	String pilot_proj_name = " ";
	Project pilotProject = null;
	String personID = null;	
	String lastname = null;
	String firstname = null;
	String descrip = null;
	String proj_name = null;
	int paycode=0;
         Project currentProject = ProjList[numProjs];
	 
	  try
	           { 	
		  proj_name = currentProject.getProj_name();
		  System.out.println("name of project *** "+proj_name);
		  lastname = currentProject.getUserLastName();
		  firstname = currentProject.getUserFirstName();
		  paycode = currentProject.getCodeofpay();
		  CProjID = currentProject.getHandle();
	  	  descrip = currentProject.getDescription();
		  personID = currentProject.getUserID();
		  PIemail =  currentProject.getUserEmail();
	   } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding Project information: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Project information", ex);
        }   
	
	//totalhours = getUsedHours(currentProject, billmonth,billyear,1,lastday);
	totalhours = displayprojtable(currentProject,billyear,billmonth);
	 System.out.println("Project total hours "+totalhours);
	 
	if (totalhours>0)
	  hasused = 1;
	if (unused.containsKey(CProjID))
	 hasunused = 1;
	 
	 
	  if ((hasused == 1) && (hasunused == 1))
	 // this is a project that both credit and debit
	    {
	    allowedh = 0.25 * totalhours;   
	    Counter ctr = (Counter) unused.get(CProjID);
	    unusednum = ctr.getCount();
	    unusedn = unusednum/2;
	    if (unusedn > allowedh)
	       debit = unusedn - allowedh;
	     else if   (unusedn < allowedh)
  		credit = allowedh - unusedn; 
	     else if (unusedn == allowedh)
	       {
	       debit = 0.0;
	       credit = 0.0;
	       }	
	  }	 
	  else if ((hasused == 1) && (hasunused == 0))
	    {
	     //this is a credit only
	    debit = 0;
	    credit = 0.25 * totalhours;
	   } 
	  
	  else if ((hasused == 0) && (hasunused == 1))
	    {
	     //this is a debit only
	     Counter dctr = (Counter) unused.get(CProjID);
	    unusednum = dctr.getCount();
	    unusedn = unusednum/2;
	    debit = unusedn - (0.25 * totalhours);
	    credit = 0;
	   } 
	   
	System.out.println("Debit total hours "+debit);  
	System.out.println("Credit total hours "+credit);    
	System.out.println("hasused flag "+hasused);   
	System.out.println("hasunused flag "+hasunused);
	   
 if ((hasused == 1) || (hasunused==1))
 //there are some activities for that project
 {   
 
 	   //this is a free pilot or unfunded project tax it.
	if (paycode == 0)
	{
	
	pilot_proj_name = "nopilot";
	System.out.println("I am here at code = 0");
	   try {
            currentProject.setProj_name(currentProject.getProj_name());
            currentProject.setPassword(currentProject.getPassword());
	    currentProject.setDescription(currentProject.getDescription());
	    currentProject.setIndexnum(currentProject.getIndexnum());
	    currentProject.setTotalhours((currentProject.getTotalhours()-debit));
	    //no more pilot hours credit for pilot projects
	   // currentProject.setTotalhours((currentProject.getTotalhours()+credit));
	    currentProject.setDonehours(currentProject.getDonehours());
            currentProject.setCodeofpay(currentProject.getCodeofpay());
            currentProject.setOwner(PersonFactory.findPersonByID(personID));
	    currentProject.setContactname(currentProject.getContactname());
	    currentProject.setContactphone(currentProject.getContactphone());
	    currentProject.setBilladdr1(currentProject.getBilladdr1());
            currentProject.setBilladdr2(currentProject.getBilladdr2());
	    currentProject.setBilladdr3(currentProject.getBilladdr3());
	    currentProject.setCity(currentProject.getCity());
	    currentProject.setState(currentProject.getState());
	    currentProject.setZip(currentProject.getZip());
	    currentProject.setAccountid(currentProject.getAccountid());
	    currentProject.setOutside(currentProject.isOutside());
	    currentProject.setExpday(currentProject.getExpday());
	    currentProject.setExpmonth(currentProject.getExpmonth());
	    currentProject.setExpyear(currentProject.getExpyear());
	     currentProject.setExp(currentProject.isExp());
	    currentProject.save();	
	
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error updating project", ex);
        }
	
	} else {
	   
    	pilot_proj_name = "Pilot"+proj_name;
	double pdonehours,ptotalhours;
	
	
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
    	pdonehours = pilotProject.getDonehours();
//    	totalhours = pilotProject.getTotalhours()-(eventhours);
	ptotalhours = pilotProject.getTotalhours();
	ptotalhours=ptotalhours-debit+credit;
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }

	    try {
            pilotProject.setProj_name(pilotProject.getProj_name());
            pilotProject.setPassword(pilotProject.getPassword());
	    pilotProject.setDescription(pilotProject.getDescription());
	    pilotProject.setIndexnum(pilotProject.getIndexnum());
	    pilotProject.setTotalhours(ptotalhours);
	    pilotProject.setDonehours(pdonehours);
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
	    pilotProject.setExp(pilotProject.isExp());
	    pilotProject.save();	
	
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding project", ex);
        }
 }       //if not null
    }//else statment
	
	System.out.println("I am saving before the try stat at code = 0");	
	try {

	System.out.println("I am sending an email phase one");
	System.out.println("PI email "+PIemail);
 	sendpianemail(PIemail, proj_name,allowedh,totalhours,credit,debit,unusedn,monthnames[billmonth],billyear,paycode);	
     	}catch(Exception ex) {
            throw new webschedulePresentationException("could not send an email", ex);
        }
message_lines.add("-"+proj_name+"  "+lastname+", "+firstname+" "+totalhours+" "+pilot_proj_name+"  "+Double.toString(unusedn)+" "+Double.toString(credit)+" "+Double.toString(debit));
	      page.setTextProj_name(proj_name);
		page.setTextFirst_name(firstname);
		page.setTextLast_name(lastname);
		page.setTextPilot_proj(pilot_proj_name);
		page.setTextThours(Double.toString(totalhours));
	 page.setTextPilot_hours(Double.toString(unusedn));
         	page.setTextCredit(Double.toString(credit)); 
		page.setTextDebit(Double.toString(debit));       
	        // Add a deep clone of the row to the DOM
                Node clonedNode = templateRow.cloneNode(true);
                ProjTable.appendChild(clonedNode);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjTable.insertBefore(clonedNode, templateRow);
                
	//	} //if totalhours > 0
	         
//	}	
    }//if there are some activities for that project

}//for loop to go through projects

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
	    String subject=("Pilot hours credit/debit for the month of:"+monthnames[billmonth]+": year"+Integer.toString(billyear));
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
    
    
protected void sendpianemail(String PIemail,String proj_name,double allowedh,double totalhours, double credit, double debit,double unusedn,String billmonth,int billyear, int paycode)   
  throws HttpPresentationException
  {
     try {
    	    SendMail sch_not;	
    	    String from = "appsadmin";
    	    String to = PIemail;
	    String[] message;
String subject="Project's pilot hours credit/debit report for month  "+billmonth+"  year"+Integer.toString(billyear);
if (paycode == 0)
{
       String[] message1={"Your Project "+proj_name+" has the following hours information ",
	" ",
	" Num of total billed hours for that project "+Double.toString(totalhours)+" hours",
	" Num of lost hours (cancelled and unused) "+Double.toString(unusedn)+" hours",
	" Num of pilot hours debited (if any)"+Double.toString(debit)+" hours",
	  "  "};
	  message= message1;
	  } else {
	String[]  message2={"Your Project "+proj_name+" has the following hours information ",
	" ",
	" Num of total billed hours for that project "+Double.toString(totalhours)+" hours",
	" Num of lost hours (cancelled and unused) "+Double.toString(unusedn)+" hours",
	" Num of pilot hours credited (if any)"+Double.toString(credit)+" hours",
	" Num of pilot hours debited (if any)"+Double.toString(debit)+" hours",
	  "  "};
	  message= message2;
	  }
	
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


