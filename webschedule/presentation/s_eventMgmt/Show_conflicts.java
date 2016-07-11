package webschedule.presentation.s_eventMgmt;

import webschedule.SendMail;
import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.business.s_event.*;
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
import java.lang.String;

/*import webschedule.collections.*;
import webschedule.collections.impl.LList;
import java.util.Enumeration;*/

import java.util.LinkedList;



public class Show_conflicts extends BasePO
 {

    private static String ERROR_NAME = "ERROR_NAME";
    private static String EVENTDAY = "Eventday";
    private static String EVENTMONTH = "Eventmonth";
    private static String EVENTYEAR = "Eventyear";


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
    	int starthi, startmi, endhi, endmi;
	
    	Show_conflictsHTML page = new Show_conflictsHTML();
        LinkedList myconflict_list = new LinkedList();
    	
    	temp_hold conflict_event = null;
    	int eventday, eventmonth, eventyear;
    	
    	myconflict_list = this.getConflictlist();
    	
    	HTMLTableRowElement templateRow = page.getElementTemplateRow();
        Node EventTable = templateRow.getParentNode();

        templateRow.removeAttribute("id");

        String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};

	String endms, startms;		


    	for (int c = 0; c <  myconflict_list.size(); c++)
    	     {
    	  Object list_evento = myconflict_list.get(c);
    	  conflict_event = (temp_hold) list_evento;
		  
	starthi = conflict_event.getcstarth();
	startmi = conflict_event.getcstartm();
	endhi = conflict_event.getcendh();
	endmi = conflict_event.getcendm();    	
		
	String ampm = null;
	        	
	if (starthi < 12) ampm = "am";
      	else if (starthi == 12) ampm = "Noon";
	     	else if (starthi == 24) {
      			starthi = starthi-12;
	      		ampm = "Mid.";
      				}
	     	else {
      			starthi = starthi -12;
	      		ampm = "pm";
		      	     }	
		if (startmi == 0) startms = "00";
			 else startms = "30";
	       		String starttime = Integer.toString(starthi)+":"+startms+" "+ampm;
		        	
	       	if (endhi < 12) ampm = "am";
      	        	else if (endhi == 12) ampm = "Noon";
		      	else if (endhi == 24) {
      				endhi = endhi-12;
		      		ampm = "Mid.";
      				}
		      	else {
      				endhi = endhi -12;
		      		ampm = "pm";
		      	     }		
	        	
		if (endmi == 0) endms = "00";
			 else endms = "30";
	        	String endtime = Integer.toString(endhi)+":"+endms+" "+ampm;
	        		
	  
	  page.setTextStarttime(starttime);
	  page.setTextEndtime(endtime);
    	  page.setTextEventday(Integer.toString(conflict_event.getceventday()));
    	  page.setTextEventmonth(monthnames[conflict_event.getceventmonth()]);
    	  page.setTextEventyear(Integer.toString(conflict_event.getceventyear()));
    	  Node clonedNode = templateRow.cloneNode(true);
          EventTable.appendChild(clonedNode);
                }
    	
           templateRow.getParentNode().removeChild(templateRow);

           //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }


           // write out HTML
	    return page.toDocument();
     }

      public String handleSaveRepeat()
        throws HttpPresentationException
    {
	 System.out.println("Visited *** save on showconflicts ***");
       saveMultiS_event();
	update_projhours();
       throw new ClientPageRedirectException(EDIT_EVENT_PAGE);
    }

/*      public String handleDonotSave()
        throws HttpPresentationException
    {
        System.out.println("Visited donot save ");
	removeConflictlistFromSession();
      removeGoodlistFromSession();
      throw new ClientPageRedirectException(EDIT_EVENT_PAGE);
     }
*/

protected void update_projhours()
   throws HttpPresentationException
   {
   	double starttime;
   	double endtime;
   	double donehours;
   	double totalhours;
   	double eventhours;
	int    numoftimes;
	int starthi, startmi, endhi, endmi;

      /* 	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	
    	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);*/
	
	LinkedList mygood_list = new LinkedList();
	temp_hold good_event = null;
  	mygood_list = this.getGoodlist();
	
	numoftimes = mygood_list.size();
	
	Object list_evento = mygood_list.get(0);
    	good_event = (temp_hold) list_evento;

	starthi = good_event.getcstarth();
    	startmi = good_event.getcstartm();
    	endhi = good_event.getcendh();
    	endmi = good_event.getcendm();
	
	if (startmi == 30)
    		{
    		double startt = (double) starthi;
    		starttime =    startt + 0.5;
    		}
    	else starttime = (double) starthi;
    	
    	if (endmi == 30)
    		endtime =     endhi + 0.5;
    	else endtime = (double) endhi;	
    	
    	if (numoftimes == 0)
    	eventhours = endtime - starttime;
    	else eventhours =  (endtime - starttime) * numoftimes;
    	
	System.out.println("Event hours = "+eventhours);
	
    	Project theProject = this.getProject();
    	
    	try
    	{    	
    	donehours = theProject.getDonehours() + eventhours;
	System.out.println("done hours = "+donehours);
	
    	totalhours = theProject.getTotalhours();
	System.out.println("total hours = "+totalhours);
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting project information", ex);
        }

    try {
            theProject.setProj_name(theProject.getProj_name());
            theProject.setPassword(theProject.getPassword());
	    theProject.setDescription(theProject.getDescription());
	    theProject.setIndexnum(theProject.getIndexnum());
	    theProject.setTotalhours(totalhours);
	    theProject.setDonehours(donehours);
            theProject.setCodeofpay(theProject.getCodeofpay());
            theProject.setOwner(this.getUser());
	    theProject.setContactname(theProject.getContactname());
	    theProject.setContactphone(theProject.getContactphone());
	    theProject.setBilladdr1(theProject.getBilladdr1());
            theProject.setBilladdr2(theProject.getBilladdr2());
	    theProject.setBilladdr3(theProject.getBilladdr3());
	    theProject.setCity(theProject.getCity());
	    theProject.setState(theProject.getState());
	    theProject.setZip(theProject.getZip());
	    theProject.setAccountid(theProject.getAccountid());
	    theProject.setOutside(theProject.isOutside());
	    theProject.setExpday(theProject.getExpday());
	    theProject.setExpmonth(theProject.getExpmonth());
	    theProject.setExpyear(theProject.getExpyear());
	   
	        theProject.save();	
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding project", ex);
        }

    }



           /**
     * Method to save a multiple event to the database
     */
 protected void saveMultiS_event()
          throws HttpPresentationException, webschedulePresentationException
    {

    LinkedList mygood_list = new LinkedList();


    temp_hold good_event = null;
    mygood_list = this.getGoodlist();


    Calendar eventinfo = Calendar.getInstance();
    int todayday = eventinfo.get(eventinfo.DAY_OF_MONTH);
    int todaymonth = eventinfo.get(eventinfo.MONTH);
    int todayyear = eventinfo.get(eventinfo.YEAR);

    for (int c = 0; c <  mygood_list.size(); c++)
    	     {
    	  Object list_evento = mygood_list.get(c);
    	  good_event = (temp_hold) list_evento;
    	
    	
    	  try {
    	  	 S_event theS_event = new S_event();
    		System.out.println("about to save one ");
    		theS_event.setDescription(good_event.getcdescription());
    		theS_event.setStarth(good_event.getcstarth());
    		theS_event.setStartm(good_event.getcstartm());
    		theS_event.setEndh(good_event.getcendh());
    		theS_event.setEndm(good_event.getcendm());
    		theS_event.setEventday(good_event.getceventday());
    		theS_event.setEventmonth(good_event.getceventmonth());
    		theS_event.setEventyear(good_event.getceventyear());
    		theS_event.setEventdayofw(good_event.getceventdayofw());
    		theS_event.setTodayday(todayday);
    		theS_event.setTodaymonth(todaymonth);
    		theS_event.setTodayyear(todayyear);
    		theS_event.setOwner(this.getUser());
    		theS_event.setProj_owner(this.getProject());
    		theS_event.setDevelop(this.getUser().isDeveloper());
    		theS_event.setRepeat(true);
    		System.out.println("about to save two ");
    		theS_event.save();
    		System.out.println("AN EVENT HAS BEEN SAVED ");
    		
    	 	} catch(Exception ex) {
          	throw new webschedulePresentationException("Error adding an event", ex);
    		 }
      	     }  	
    	
      }

  }
