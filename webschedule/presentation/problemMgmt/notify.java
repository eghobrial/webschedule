/**--------------------------------------------------------------
* Webschedule
*
* @class:notify
* @version
* @author: Eman Ghobrial
* @since: June 2004
* addeded subtract done project hours March 2007
*
*--------------------------------------------------------------*/

package webschedule.presentation.problemMgmt;

import webschedule.SendMail;
import webschedule.business.problem.*;
import webschedule.business.person.*;
import webschedule.business.s_event.*;
import webschedule.business.project.*;
import webschedule.business.operator.*;
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
import java.util.Date;
import java.util.LinkedList;

/**
 * notify.java shows the notify page
 *
 */
public class notify extends BasePO
{

 private static String ERROR_NAME = "ERROR_NAME";
 
  private static String STARTH = "starth";
    private static String  STARTM = "startm";
    private static String ENDH = "endh";
    private static String  ENDM = "endm";
	private static String SDATE ="sdate";
    private static String EDATE ="edate";
        private static String MONTH = "month";
    private static String YEAR = "year";
     private static String REASON = "reason";
     private static String SYS_DOWN = "sys_down";
 private static String SYS_UPDATE = "sys_update";
  private static String SYS_UP = "sys_up";

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
    
      public String handleNotify()
        throws HttpPresentationException
	    {
	    
	    S_event[] s_event_list;
	    LinkedList		users_emails = new LinkedList();
	    
	String starth = this.getComms().request.getParameter(STARTH);
    	String startm = this.getComms().request.getParameter(STARTM);
    	String endh = this.getComms().request.getParameter(ENDH);
    	String endm = this.getComms().request.getParameter(ENDM);
	String sdate = this.getComms().request.getParameter(SDATE);
	String edate = this.getComms().request.getParameter(EDATE);
        String month = this.getComms().request.getParameter(MONTH);
    	String year = this.getComms().request.getParameter(YEAR);
    	String reason = this.getComms().request.getParameter(REASON);
	String sys_down = this.getComms().request.getParameter(SYS_DOWN);
	String sys_update = this.getComms().request.getParameter(SYS_UPDATE);
	String sys_up = this.getComms().request.getParameter(SYS_UP);
	
	String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};
	 int monthi = Integer.parseInt(month);
	//	  monthi = monthi + 1;
		    //  month = Integer.toString(monthi);
	String	  months = monthnames[monthi];	
	try 
	{
	 if (!(this.getUser().isAuth())){
	    this.getSessionData().setUserMessage("User not authorized to notify other PIs ");
            throw new ClientPageRedirectException(PROBLEMMENU_PAGE);
	            }
		    
	 } catch (webscheduleBusinessException ex) {
         	throw new webschedulePresentationException("Error getting user is auth attribute", ex);
          }
/*	
	  if (reason.length() == 0 ){
            return showPage("Missing information. Please make sure all fields are filled out exactly");    
        }   */
    	    

	try {
//s_event_list =S_eventFactory.findS_eventsPeriodForAll(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(sdate),Integer.parseInt(edate));
s_event_list=S_eventFactory.findS_eventsTimePeriodForAll(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(sdate),Integer.parseInt(edate),Integer.parseInt(starth),Integer.parseInt(endh));
       } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding EVENTs: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding EVENTs", ex);
        }
	
	
	    for (int numEvents = 0; numEvents<s_event_list.length; numEvents++)
	      {
	       try {
	    	int starthi = s_event_list[numEvents].getStarth();
	    	System.out.println("Start time "+Integer.toString(starthi));
            	int startmi = s_event_list[numEvents].getStartm();
            	int endhi = s_event_list[numEvents].getEndh();
            	int endmi = s_event_list[numEvents].getEndm();
		int eventday = s_event_list[numEvents].getEventday();
		String OwnerFirstName = s_event_list[numEvents].getOwnerFirstname();
		String OwnerLastName = s_event_list[numEvents].getOwnerLastname();
		String OwnerEmail = s_event_list[numEvents].getOwnerEmail();
		String OperatorEmail = s_event_list[numEvents].getOperatorEmail();
		String eventID = s_event_list[numEvents].getHandle();
		

//if (eventday == Integer.parseInt(sdate))
	//	  {
		//  if (starthi >= Integer.parseInt(starth))
		  //  {
		    
		      if(null != this.getComms().request.getParameter(SYS_DOWN)) {
	                 System.out.println("****Event ID off notify****"+eventID); 
	                 update_sch(eventID,reason);
			} 
		    Object OwnerEmailo = (Object) OwnerEmail;
		    if (users_emails.indexOf(OwnerEmailo) == -1)
		    {
		      users_emails.add(OwnerEmailo);
		    
send_user_notify(OperatorEmail,OwnerEmail,starth, startm, endh, endm, sdate, edate, months, year, reason,eventID);
		  System.out.println("OwnerEmail off the notify module"+OwnerEmail);  
			}
		  
	    	} catch(Exception ex) {
	          throw new webschedulePresentationException("Error getting event info.", ex);
    		 }
	}
	
	
	//return showPage("Thank you");   	
	throw new ClientPageRedirectException(PROBLEMMENU_PAGE);
}	


		


 protected void send_user_notify(String OperatorEmail,String OwnerEmail,String starth,String startm,String endh,String endm,String sdate,String edate,String month,String year,String reason,String eventID)
    throws  HttpPresentationException
 {
 String sys_down = this.getComms().request.getParameter(SYS_DOWN);
 String subject=null;
 
 String[] message=null;
 
 
    try {
    	    SendMail sch_not;	
    	    String from = "websch_admin";
    	    String to = OwnerEmail+","+OperatorEmail+",eghobrial@ucsd.edu"+",kunlu@ucsd.edu"+",ddshin@ucsd.edu"+",ttliu@ucsd.edu";
	   
	    
	     if(null != this.getComms().request.getParameter(SYS_DOWN)) {
	     
	   ///stopped  here???
	  //     System.out.println("****Event ID off notify****"+eventID); 
	   //  update_sch(eventID,reason);
    	   
	       subject = "3TW notification ** Technical Difficulty **";
    	 String[]  message1 = {"Certain scanner related problems may have prevented or will prevent you from scanning.",
	   "Your scheduled time has been moved to technical difficulty category.",
	   "Your account will not be billed for that time slot",
	    "Please check the system status online for more information at:",
	    "http://cfmri.ucsd.edu/webschedule/webschedule.htm",
	    " ",
	    "This notification is sent to groups with scheduled time between:",
    	    "Start:      "+starth+":"+startm+" on "+month+"-"+sdate+"-"+year,
    	    "End:       "+endh+":"+endm+" on "+month+"-"+edate+"-"+year,
    	    "Message:       "+reason,
    	    " "}  ;
	    
		message = message1;
		} else  if(null != this.getComms().request.getParameter(SYS_UP))  {
		
		subject = "3TW notification ** System Back Up **";
   String[] message2 = {"This is to notify you that the previously reported problem has been corrected.",
   "Please check the system status online for more information:",
	    "		http://cfmri.ucsd.edu/webschedule/webschedule.htm",
	    "This message has been sent to all PI's with scheduled time between:",
    	    "Between:      "+starth+":"+startm+" on "+month+"-"+sdate+"-"+year,
    	    			"Ending:       "+endh+":"+endm+" on "+month+"-"+edate+"-"+year,
    	    			"Message:       "+reason,
	      			" "}  ;
		message = message2;		
		} else  if(null != this.getComms().request.getParameter(SYS_UPDATE))  {
		
		subject = "3TW notification ** System Update **";
   String[] message3 = {"This is an update or general message on system status.",
   "Please check the system status online for more information:",
	    "		http://cfmri.ucsd.edu/webschedule/webschedule.htm",
	    "This message has been sent to all PI's with scheduled time between:",
    	    "Between:      "+starth+":"+startm+" on "+month+"-"+sdate+"-"+year,
    	    			"Ending:       "+endh+":"+endm+" on "+month+"-"+edate+"-"+year,
    	    			"Message:       "+reason,
	      			" "}  ;
		message = message3;		
		}		
    	 		
           sch_not = new SendMail (from,to,subject,message);

	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error sending an email", ex);
        }
 
 }
 
 
   protected void update_sch(String eventID,String reason)
   throws HttpPresentationException
   {
   
   Project tech_diffProject = null;
   Person tech_diffPerson = null;
    Project Project = null;
   Person Person = null;
   Operator Operator = null;
   S_event s_event = null;
   String personID=null, projID=null, operatorID=null;
   double starttime=0.0;
   	double endtime=0.0;
   int starthi=0;
   int endhi=0;
   int startmi=0;
   int endmi=0;
   
   double eventhours,donehours,totalhours;
      
   
   
   try {
	    	s_event = S_eventFactory.findS_eventByID(eventID);
               starthi = s_event.getStarth();
            	startmi = s_event.getStartm();
            	endhi = s_event.getEndh();
            	endmi = s_event.getEndm();
		personID = s_event.getUserID();
            	projID = s_event.getProjectID();
		operatorID=s_event.getOperatorID();
             	// Catch any possible database exception as well as the null pointer
            	//  exception if the s_event is not found and is null after findS_eventByID
	    	} catch(Exception ex) {
            	this.getSessionData().setUserMessage(eventID + " Please choose a valid event");
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
	      
	      
	 try
	{    	
    	Person = PersonFactory.findPersonByID(personID);
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting person information", ex);
        }
	
	try
	{    	
    	Project = ProjectFactory.findProjectByID(projID);
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }     
   
   
   	try
    	{    	
    	donehours = Project.getDonehours() - eventhours;
	System.out.println("done hours off deleting an event = "+Double.toString(donehours));
    	totalhours = Project.getTotalhours();
	System.out.println("total hours off deleting an event"+Double.toString(totalhours));
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }

	    try {
            Project.setProj_name(Project.getProj_name());
            Project.setPassword(Project.getPassword());
	    Project.setDescription(Project.getDescription());
	    Project.setIndexnum(Project.getIndexnum());
	    Project.setTotalhours(totalhours);
	    Project.setDonehours(donehours);
            Project.setCodeofpay(Project.getCodeofpay());
            Project.setOwner(Person);

	    Project.setContactname(Project.getContactname());
	    Project.setContactphone(Project.getContactphone());
	    Project.setBilladdr1(Project.getBilladdr1());
            Project.setBilladdr2(Project.getBilladdr2());
	    Project.setBilladdr3(Project.getBilladdr3());
	    Project.setCity(Project.getCity());
	    Project.setState(Project.getState());
	    Project.setZip(Project.getZip());
	    Project.setAccountid(Project.getAccountid());
	    Project.setOutside(Project.isOutside());
	    Project.setExpday(Project.getExpday());
	    Project.setExpmonth(Project.getExpmonth());
	    Project.setExpyear(Project.getExpyear());

	    Project.save();	
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error subtracting project hours", ex);
        }
   
   
   try
	{    	
    	Operator = OperatorFactory.findOperatorByID(operatorID);
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting operator information", ex);
        }    
   
   	try
    	{
    	tech_diffPerson = PersonFactory.findPerson("tech_prob");
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting person information", ex);
        }
        
   
   
   	try
    	{
    	tech_diffProject = ProjectFactory.findProjectByProj_name("tech_diff");
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }
        
    Calendar eventinfo = Calendar.getInstance();
    int todayday = eventinfo.get(eventinfo.DAY_OF_MONTH);
    int todaymonth = eventinfo.get(eventinfo.MONTH);
    int todayyear = eventinfo.get(eventinfo.YEAR);
   
   	try {
    		System.out.println("about to save one ");
		
    		s_event.setDescription(s_event.getDescription());
    		s_event.setStarth(s_event.getStarth());
    		s_event.setStartm(s_event.getStartm());
    		s_event.setEndh(s_event.getEndh());
    		s_event.setEndm(s_event.getEndm());
    		s_event.setEventday(s_event.getEventday());
    		s_event.setEventmonth(s_event.getEventmonth());
    		s_event.setEventyear(s_event.getEventyear());
    		s_event.setEventdayofw(s_event.getEventdayofw());
    		s_event.setTodayday(todayday);
    		s_event.setTodaymonth(todaymonth);
		System.out.println("****today year****"+Integer.toString(todayyear));
    		s_event.setTodayyear(todayyear);
    		s_event.setOwner(tech_diffPerson);
    		s_event.setProj_owner(tech_diffProject);
		s_event.setPrevowner(Person);
    		s_event.setPrevproj_owner(Project);
		s_event.setOperator(Operator);
    		s_event.setDevelop(s_event.isDevelop());
    		s_event.setRepeat(s_event.isRepeat());
		s_event.setGrab(false);
		System.out.println("****Reason****"+reason);
		s_event.setReasondropped(reason);
    		System.out.println("about to save two ");
    		s_event.save();
    		System.out.println("AN EVENT HAS BEEN SAVED ");
    		
    	 } catch(Exception ex) {
    throw new webschedulePresentationException("Error adding an event, make sure all fields are filled", ex);
     }
   
   
   }

    /**
     *
     */
    public String showPage(String errorMsg)
    {
        notifyHTML page = new notifyHTML();
	
	//calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
	String updatem = Integer.toString(todaymonth);
 	String updated = Integer.toString(todaydate);
	String updatey = Integer.toString(todayyear); 
	
	HTMLSelectElement	sdate_select,edate_select,month_select,year_select;
HTMLCollection		sdate_selectCollection,edate_selectCollection,month_selectCollection,year_selectCollection;
HTMLOptionElement	sdate_option, edate_option, month_option,year_option;
	String		sdate_optionName,edate_optionName,month_optionName, year_optionName;
	
	
	sdate_select = (HTMLSelectElement)page.getElementSdate();
	sdate_selectCollection = sdate_select.getOptions();
	
	edate_select = (HTMLSelectElement)page.getElementEdate();
	edate_selectCollection = edate_select.getOptions();
	
	month_select = (HTMLSelectElement)page.getElementMonth();
	month_selectCollection = month_select.getOptions();
	
	year_select = (HTMLSelectElement)page.getElementYear();
	year_selectCollection = year_select.getOptions();	

       //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }
	
	
	
	int sdate_optionlen = sdate_selectCollection.getLength();
	for (int i=0; i< sdate_optionlen; i++) {
	  sdate_option = (HTMLOptionElement)sdate_selectCollection.item(i);
	  sdate_optionName = sdate_option.getValue();
	  if (sdate_optionName.equals(updated))
	     sdate_option.setSelected(true);
	  else
	     sdate_option.setSelected(false);
	    }    
	
			
int edate_optionlen = edate_selectCollection.getLength();
	for (int i=0; i< edate_optionlen; i++) {
	  edate_option = (HTMLOptionElement)edate_selectCollection.item(i);
	  edate_optionName = edate_option.getValue();
	  if (edate_optionName.equals(updated))
	     edate_option.setSelected(true);
	  else
	     edate_option.setSelected(false);
	    }  
	    
	        
	    
	int month_optionlen = month_selectCollection.getLength();
	for (int i=0; i< month_optionlen; i++) {
	  month_option = (HTMLOptionElement)month_selectCollection.item(i);
	  month_optionName = month_option.getValue();
	  if (month_optionName.equals(updatem))
	     month_option.setSelected(true);
	  else
	     month_option.setSelected(false);
	    }  
	    
	    
	      int year_optionlen = year_selectCollection.getLength();
	for (int i=0; i< year_optionlen; i++) {
	  year_option = (HTMLOptionElement)year_selectCollection.item(i);
	  year_optionName = year_option.getValue();
	  if (year_optionName.equals(updatey))
	     year_option.setSelected(true);
	  else
	     year_option.setSelected(false);
	    }  
	      
	    
	    return page.toDocument();
    }
}


