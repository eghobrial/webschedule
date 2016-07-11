/**--------------------------------------------------------------
* Webschedule
*
* @class: Blocktime
* @version
* @author: Eman Ghobrial
* @since: May 2001
*
* needs check for the day entered??????????????
*--------------------------------------------------------------*/

package webschedule.presentation.s_eventMgmt;

import webschedule.business.blocktime.*;
import webschedule.presentation.BasePO;
import com.lutris.appserver.server.httpPresentation.*;
import com.lutris.appserver.server.session.*;
import webschedule.business.s_event.*;
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
import java.lang.String;

/**
 *Blocktime.java shows the blocktime form
 *
 */
public class Blocktime extends BasePO
{
   /*
   * Constants
   */
    private static String STARTH = "starth";
    private static String  STARTM = "startm";
    private static String ENDH = "endh";
    private static String  ENDM = "endm";
    private static String DAY ="day";
    private static String MONTH ="month";
    private static String YEAR ="year";
    private static String BTYPE = "btype";
    private static String  DESCRIPTION ="description";
    private static String REPEATMENU = "repeatmenu";
    private static String TIMES = "times";


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
    	String temptext = null;
    	int temphour;
       BlocktimeHTML page = new BlocktimeHTML();

    	
       String starth = this.getComms().request.getParameter(STARTH);
       String startm = this.getComms().request.getParameter(STARTM);
       String endh = this.getComms().request.getParameter(ENDH);
       String endm = this.getComms().request.getParameter(ENDM);
       String day =  this.getComms().request.getParameter(DAY);
       String month = this.getComms().request.getParameter(MONTH);
       String year = this.getComms().request.getParameter(YEAR);
       String btype = this.getComms().request.getParameter(BTYPE);
       
       
       /*	flags  1 development
       			2 clinical
			3 maintenance/setup
			*/
        	
       HTMLOptionElement starthtemplateOption = page.getElementStarthtemplateOption();
       HTMLOptionElement startmtemplateOption = page.getElementStartmtemplateOption();
       HTMLOptionElement endhtemplateOption = page.getElementEndhtemplateOption();
       HTMLOptionElement endmtemplateOption = page.getElementEndmtemplateOption();

      Node starthSelect = starthtemplateOption.getParentNode();
      Node startmSelect = startmtemplateOption.getParentNode();
      Node endhSelect = endhtemplateOption.getParentNode();
      Node endmSelect = endmtemplateOption.getParentNode();

      starthtemplateOption.removeAttribute("id");
      startmtemplateOption.removeAttribute("id");
      endhtemplateOption.removeAttribute("id");
      endmtemplateOption.removeAttribute("id");

      starthtemplateOption.removeChild(starthtemplateOption.getFirstChild());
      startmtemplateOption.removeChild(startmtemplateOption.getFirstChild());
      endhtemplateOption.removeChild(endhtemplateOption.getFirstChild());
      endmtemplateOption.removeChild(endmtemplateOption.getFirstChild());
	

      for (int hour = 0; hour < 25; hour ++)
      {
      	if (hour < 12) temptext = Integer.toString(hour)+"am";
      	else if (hour == 12) temptext = Integer.toString(hour)+"Noon";
      	else if (hour == 24) {
      		temphour = hour-12;
      		temptext = Integer.toString(temphour)+"Mid.";
      		}
      	else {
      		temphour = hour -12;
      		temptext = Integer.toString(temphour)+"pm";
      	     }		

      HTMLOptionElement clonedOption = (HTMLOptionElement) starthtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(hour));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(temptext);
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                starthSelect.appendChild(clonedOption);
         }

      for (int min = 0; min < 60; min+=30)
      {

      HTMLOptionElement clonedOption = (HTMLOptionElement) startmtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(min));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(Integer.toString(min));
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                startmSelect.appendChild(clonedOption);
         }


         for (int min = 0; min < 60; min+=30)
      {

      HTMLOptionElement clonedOption = (HTMLOptionElement) endmtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(min));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(Integer.toString(min));
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                endmSelect.appendChild(clonedOption);
         }

     for (int hour = 0; hour < 25; hour ++)
      {
      if (hour < 12) temptext = Integer.toString(hour)+"am";
      	else if (hour == 12) temptext = Integer.toString(hour)+"Noon";
      	else if (hour == 24) {
      		temphour = hour-12;
      		temptext = Integer.toString(temphour)+"Mid.";
      		}
      	else {
      		temphour = hour -12;
      		temptext = Integer.toString(temphour)+"pm";
      	     }		

      HTMLOptionElement clonedOption = (HTMLOptionElement) endhtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(hour));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(temptext);
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                endhSelect.appendChild(clonedOption);
         }


        starthtemplateOption.getParentNode().removeChild(starthtemplateOption);
        startmtemplateOption.getParentNode().removeChild(startmtemplateOption);
        endhtemplateOption.getParentNode().removeChild(endhtemplateOption);
        endmtemplateOption.getParentNode().removeChild(endmtemplateOption);


    	
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



   public String handleSaveblocktime()
        throws HttpPresentationException, webschedulePresentationException
    {
    	String test = null;
       String repeatinfo = this.getComms().request.getParameter(REPEATMENU);
        System.out.println("Repeat Information = "+repeatinfo);
        String times = this.getComms().request.getParameter(TIMES);
         System.out.println("Num of times = "+times);
         int numoftimes =  Integer.parseInt(times);
		
	  Blocktimec  blocktimec = null;
	
	 try {
  	 if (repeatinfo.equals("Never"))
    	 {
          try  {
	   blocktimec = new Blocktimec();
	  } catch(Exception ex) {
          return showPage("Cannot create a new block time class");
    		 }
    	// if (check_conflict()==1) return   showPage("You entered the date that conflict check calendar and try again");
        // else if (check_conflict_blocktime()==1) showPage ("You entered the date that conflict check calendar and try again");
        // else if  (check_time()==1) return showPage("You entered a wrong time end time must be after start time");
    	// else if (check_date()==1) return showPage("Yon can not schedule for a previous date or time");	
    	// else {	
    	
    	 saveBlocktime(blocktimec);	
    	 throw new ClientPageRedirectException(BLOCKTIME_PAGE);	
	 //  }
	 } else {   //begin else repeat event
	 if  (check_time()==1) return showPage("You entered a wrong time end time must be after start time");
    	 else if (check_date()==1) return showPage("Yon can not schedule for a previous date or time");
    	 else if (check_multi_conflict()==2) return   showPage("All the dates that you entered have conflicts check calendar and try again");
         else  if (check_multi_conflict()==1){   //begin if some conflicts
    	  //   if (check_multi_conflict_block()==2) return showPage("All the dates that you entered have conflicts check calendar and try again");
           //    else throw new ClientPageRedirectException(SHOW_CONFLICTS_PAGE)  ;
    		   }  //end if some conflicts
          else { // begin no conflicts
    	  saveMultiBlocktime ();
    	  throw new ClientPageRedirectException(BLOCKTIME_PAGE);
    		 }    // end no conflicts
         } //end else repeat event
    	 } catch(Exception ex) {
         return showPage("Cannot add a new block time");

        }
      return test;
    	
    }
	
/* Check conflicts routines */


public int check_conflict()
     throws HttpPresentationException, webschedulePresentationException
    {
    	
     String starth = this.getComms().request.getParameter(STARTH);
     String startm = this.getComms().request.getParameter(STARTM);
     String endh = this.getComms().request.getParameter(ENDH);
     String endm = this.getComms().request.getParameter(ENDM);
     String day = this.getComms().request.getParameter(DAY);    	
     String month = this.getComms().request.getParameter(MONTH);
     String year = this.getComms().request.getParameter(YEAR);

     int event_starthi =  Integer.parseInt(starth);
     int event_startmi =  Integer.parseInt(startm);
     int event_endhi =     Integer.parseInt(endh);
     int event_endmi =   Integer.parseInt(endm);

     int dayi = Integer.parseInt(day);
     int monthi = Integer.parseInt(month);
     int yeari = Integer.parseInt(year);


        int conflict = 0;
        int conflict_has_been_set = 0;

          try {

          	S_event[] EventList = S_eventFactory.findS_eventsForDate(yeari,monthi,dayi);
          	
	         // Get collection of events and loop through collection
	        for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	
                	S_event currentS_event = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	int starthi = currentS_event.getStarth();
	        	int startmi = currentS_event.getStartm();
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	        	/*int conflict = check_for(starthi,startmi,endhi,endmi,event_starthi,event_startmi,event_endhi,event_endmi);*/
	        	
	        	if (event_starthi < starthi)
	        		{
	        		  if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		
	        		 }
	        		
	        	// 1. if it is equal check min and end time	
	        	else if (event_starthi == starthi)
	        		{
	        		 if (event_startmi >= startmi)
	        		   {  	//if
	        		   conflict = 1;
	        		   System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		    conflict_has_been_set = 1;
	        		   //return conflict;
	        		   break;
	        		   }        //if
	        		   else {//else 1
	        		
	        			if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		   }  //else 1
	        		 }
	        		
	        	
                       //1.    if it is greater than the booked on make sure it is not in the middle
                       else if (event_starthi > starthi)
                       		{
                       		if (event_starthi < endhi)
                       		 {
                       		   conflict = 1;
                       		   System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		   conflict_has_been_set = 1;
                       		   break;
                       		 }
                       		else if (event_starthi == endhi)
                       		    {
                       		      if (event_startmi < endmi)
                       		      {
                       		
                       		      	conflict = 1;
                       		      	 System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		      	  conflict_has_been_set = 1;
                       		      	 //return conflict;
                       		      	 break;
                       		      	  }
                       		    }
                       		
                       		}
	             	        	
                      } // for loop

                     } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);
            throw new webschedulePresentationException("Error getting Events for date: ", ex);
	    }
	
	System.out.println("System conflict flag  "+Integer.toString(conflict));
	if ( conflict_has_been_set == 1)
	        return 1;
	else return 0;
 	 	
  } //end procedure	              		


 public int check_conflict_blocktime()
     throws HttpPresentationException, webschedulePresentationException
    {	
     String starth = this.getComms().request.getParameter(STARTH);
     String startm = this.getComms().request.getParameter(STARTM);
     String endh = this.getComms().request.getParameter(ENDH);
     String endm = this.getComms().request.getParameter(ENDM);
     String day = this.getComms().request.getParameter(DAY);    	
     String month = this.getComms().request.getParameter(MONTH);
     String year = this.getComms().request.getParameter(YEAR);

     int event_starthi =  Integer.parseInt(starth);
     int event_startmi =  Integer.parseInt(startm);
     int event_endhi =     Integer.parseInt(endh);
     int event_endmi =   Integer.parseInt(endm);

     int dayi = Integer.parseInt(day);
     int monthi = Integer.parseInt(month);
     int yeari = Integer.parseInt(year);

       int conflict = 0;
        int conflict_has_been_set = 0;

       try {

          	Blocktimec[] EventList = BlocktimeFactory.findBlocktimeForDate(yeari,monthi,dayi);
          	
	         // Get collection of events and loop through collection
	        for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	
                	Blocktimec currentBlocktimec = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	int starthi = currentBlocktimec.getStarth();
	        	int startmi = currentBlocktimec.getStartm();
	        	int endhi = currentBlocktimec.getEndh();
	        	int endmi = currentBlocktimec.getEndm();
	        	/*int conflict = check_for(starthi,startmi,endhi,endmi,event_starthi,event_startmi,event_endhi,event_endmi);*/
	        	
	        	if (event_starthi < starthi)
	        		{
	        		  if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		
	        		 }
	        		
	        	// 1. if it is equal check min and end time	
	        	else if (event_starthi == starthi)
	        		{
	        		 if (event_startmi >= startmi)
	        		   {  	//if
	        		   conflict = 1;
	        		   System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		    conflict_has_been_set = 1;
	        		   //return conflict;
	        		   break;
	        		   }        //if
	        		   else {//else 1
	        		
	        			if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		   }  //else 1
	        		 }
	        		
	        	
                       //1.    if it is greater than the booked on make sure it is not in the middle
                       else if (event_starthi > starthi)
                       		{
                       		if (event_starthi < endhi)
                       		 {
                       		   conflict = 1;
                       		   System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		   conflict_has_been_set = 1;
                       		   break;
                       		 }
                       		else if (event_starthi == endhi)
                       		    {
                       		      if (event_startmi < endmi)
                       		      {
                       		
                       		      	conflict = 1;
                       		      	 System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		      	  conflict_has_been_set = 1;
                       		      	 //return conflict;
                       		      	 break;
                       		      	  }
                       		    }
                       		
                       		}
	             	        	
                      } // for loop

                     } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);
            throw new webschedulePresentationException("Error getting Events for date: ", ex);
	    }
	
	System.out.println("System conflict flag  "+Integer.toString(conflict));
	if ( conflict_has_been_set == 1)
	        return 1;
	else return 0;
 	 	
  } //end procedure	


  public int check_time()
    throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	

	int starthi =  Integer.parseInt(starth);
	int endhi = Integer.parseInt(endh);
	int startmi = Integer.parseInt(startm);
    	int endmi = Integer.parseInt(endm);		
	    			    			
	
/*	Calendar eventinfo = Calendar.getInstance();
    	int todayday = eventinfo.get(eventinfo.DAY_OF_MONTH);
    	int todaymonth = eventinfo.get(eventinfo.MONTH);
    	int todayyear = eventinfo.get(eventinfo.YEAR);
 	eventinfo.set(this.getYear(),this.getMonth(),this.getDay(),starthi-1,endhi-1);    			*/
 	
 	if ((endhi < starthi) || ((endhi == starthi) && (endmi < startmi)))
 	{
 	  return 1;
 	}     else return  0;
}


public int check_date()
   throws HttpPresentationException, webschedulePresentationException
   {


        String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	
    	String day = this.getComms().request.getParameter(DAY);    	
        String month = this.getComms().request.getParameter(MONTH);
    	String year = this.getComms().request.getParameter(YEAR);
    	
        int starthi =  Integer.parseInt(starth);
	int startmi = Integer.parseInt(startm);
	
	int eventyear = Integer.parseInt(year);
        int eventmonth = Integer.parseInt(month);
        int eventday = Integer.parseInt(day);

        //calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
    	Date cancelinfodate =  cancelinfo.getTime();
    	long cancelinfoms = cancelinfodate.getTime();
    	   	
    	//calculation for the event info
    	Calendar eventinfo = Calendar.getInstance();
    	eventinfo.set(eventyear, eventmonth, eventday, starthi, startmi);
    	Date eventinfodate =   eventinfo.getTime();
    	long eventinfoms = eventinfodate.getTime();
	long difference = eventinfoms - cancelinfoms;
	long numofhours = difference/3600000;
    	
	if (numofhours < 0)
		return 1;
	else 	
        	return 0;
	}
	
public int check_multi_conflict()
     throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	String description = this.getComms().request.getParameter(DESCRIPTION);
    	
        int event_starthi =  Integer.parseInt(starth);
	int event_endhi = Integer.parseInt(endh);
	int event_startmi = Integer.parseInt(startm);
    	int event_endmi = Integer.parseInt(endm);
    	
    	String day = this.getComms().request.getParameter(DAY);    	
        String month = this.getComms().request.getParameter(MONTH);
    	String year = this.getComms().request.getParameter(YEAR);
    	
	int eventyear = Integer.parseInt(year);
        int eventmonth = Integer.parseInt(month);
        int eventday = Integer.parseInt(day);
    	
    	String repeatinfo = this.getComms().request.getParameter(REPEATMENU);
        String times = this.getComms().request.getParameter(TIMES);
        int numoftimes =  Integer.parseInt(times);
	  	
      	
        int event_conflict = 0;
        int dow, nexteventday, nexteventmonth, nexteventyear;
        int incrementvalue = 0;

        LinkedList conflict_list = new LinkedList();
    	LinkedList good_list = new LinkedList();
    	
    	int conflictvalue = 0;
   	

        if  (repeatinfo.equals("week"))
          incrementvalue = 7;
        else incrementvalue = 14;

        Calendar eventinfo = Calendar.getInstance();    //initialize
        eventinfo.set(eventyear, eventmonth, eventday); //first event
        dow =  eventinfo.get(eventinfo.DAY_OF_WEEK);
        //check the conflict for the first event
        event_conflict = checkeventconflict(eventyear,eventmonth,eventday, event_starthi, event_endhi, event_startmi,event_endmi);
        if (event_conflict == 1)
          conflict_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, eventday, eventmonth,eventyear,dow));
	else
       	  good_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, eventday, eventmonth,eventyear,dow));
        for (int t=2; t<=numoftimes; t++)
        { //for loop
         eventinfo.add(eventinfo.DAY_OF_MONTH, incrementvalue);
         nexteventday = eventinfo.get(eventinfo.DAY_OF_MONTH);
         System.out.println("Next Event Day  "+Integer.toString(nexteventday));
         nexteventmonth = eventinfo.get(eventinfo.MONTH);
         System.out.println("Next Event Month  "+Integer.toString(nexteventmonth));
         nexteventyear = eventinfo.get(eventinfo.YEAR);
         System.out.println("Next Event Year  "+Integer.toString(nexteventyear));
         dow =  eventinfo.get(eventinfo.DAY_OF_WEEK);
         event_conflict = checkeventconflict(nexteventyear,nexteventmonth,nexteventday, event_starthi, event_endhi, event_startmi,event_endmi);
         if (event_conflict == 1)
           conflict_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, nexteventday, nexteventmonth,nexteventyear,dow));
	 else
       	   good_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, nexteventday, nexteventmonth,nexteventyear,dow));
         }//for loop

         System.out.println("Conflict list Size  "+Integer.toString(conflict_list.size()));
          System.out.println("Good list Size  "+Integer.toString(good_list.size()));
         if (conflict_list.size() == 0)
         //there are  conflicts
         {
         this.setGoodlist(good_list);
         conflictvalue=0;
         }
         else if (conflict_list.size() > 0) //there is some conflicts
           {
           if (good_list.size() == 0)
            conflictvalue = 2; //there is no good events
           else
           {
           this.setConflictlist(conflict_list);
           this.setGoodlist(good_list);
           conflictvalue = 1; //there are some good and conflict events
           }
           }
         return conflictvalue;
    }


    public int check_multi_conflict_block()
     throws HttpPresentationException, webschedulePresentationException
    {
    	String starth = this.getComms().request.getParameter(STARTH);
        System.out.println("Starth selected on check time method = "+starth);
    	String startm = this.getComms().request.getParameter(STARTM);
    	System.out.println("Startm selected = "+startm);
    	String endh = this.getComms().request.getParameter(ENDH);
    	System.out.println("endh selected = "+endh);
    	String endm = this.getComms().request.getParameter(ENDM);
    	System.out.println("endm selected = "+endm);
    	String description = this.getComms().request.getParameter(DESCRIPTION);
    	
        int event_starthi =  Integer.parseInt(starth);
	int event_endhi = Integer.parseInt(endh);
	int event_startmi = Integer.parseInt(startm);
    	int event_endmi = Integer.parseInt(endm);
    	
    	String day = this.getComms().request.getParameter(DAY);    	
        String month = this.getComms().request.getParameter(MONTH);
    	String year = this.getComms().request.getParameter(YEAR);
    	
	int eventyear = Integer.parseInt(year);
        int eventmonth = Integer.parseInt(month);
        int eventday = Integer.parseInt(day);
    	
    	String repeatinfo = this.getComms().request.getParameter(REPEATMENU);
        String times = this.getComms().request.getParameter(TIMES);
        int numoftimes =  Integer.parseInt(times);
	  	
      	
        int event_conflict = 0;
        int dow, nexteventday, nexteventmonth, nexteventyear;
        int incrementvalue = 0;

        LinkedList conflict_list = this.getConflictlist();
    	LinkedList good_list = this.getGoodlist();
    	
    	int conflictvalue = 0;
   	

        if  (repeatinfo.equals("week"))
          incrementvalue = 7;
        else incrementvalue = 14;

        Calendar eventinfo = Calendar.getInstance();    //initialize
        eventinfo.set(eventyear, eventmonth, eventday); //first event
        dow =  eventinfo.get(eventinfo.DAY_OF_WEEK);
        //check the conflict for the first event
        event_conflict = checkeventconflict(eventyear,eventmonth,eventday, event_starthi, event_endhi, event_startmi,event_endmi);
        if (event_conflict == 1)
          conflict_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, eventday, eventmonth,eventyear,dow));
	else
       	  good_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, eventday, eventmonth, eventyear,dow));
        for (int t=2; t<=numoftimes; t++)
        { //for loop
         eventinfo.add(eventinfo.DAY_OF_MONTH, incrementvalue);
         nexteventday = eventinfo.get(eventinfo.DAY_OF_MONTH);
         System.out.println("Next Event Day  "+Integer.toString(nexteventday));
         nexteventmonth = eventinfo.get(eventinfo.MONTH);
         System.out.println("Next Event Month  "+Integer.toString(nexteventmonth));
         nexteventyear = eventinfo.get(eventinfo.YEAR);
         System.out.println("Next Event Year  "+Integer.toString(nexteventyear));
         dow =  eventinfo.get(eventinfo.DAY_OF_WEEK);
         event_conflict = checkeventconflict(nexteventyear,nexteventmonth,nexteventday, event_starthi, event_endhi, event_startmi,event_endmi);
         if (event_conflict == 1)
           conflict_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, nexteventday, nexteventmonth,nexteventyear,dow));
	 else
       	   good_list.add(new temp_hold(description, event_starthi, event_startmi, event_endhi, event_endmi, nexteventday, nexteventmonth,nexteventyear,dow));
         }//for loop

         System.out.println("Conflict list Size  "+Integer.toString(conflict_list.size()));
          System.out.println("Good list Size  "+Integer.toString(good_list.size()));
         if (conflict_list.size() == 0)
         //there are  conflicts
         {
         this.setGoodlist(good_list);
         conflictvalue=0;
         }
         else if (conflict_list.size() > 0) //there is some conflicts
           {
           if (good_list.size() == 0)
            conflictvalue = 2; //there is no good events
           else
           {
           this.setConflictlist(conflict_list);
           this.setGoodlist(good_list);
           conflictvalue = 1; //there are some good and conflict events
           }
           }
         return conflictvalue;
    }



    public int checkeventconflict (int year, int month, int day, int event_starthi, int event_endhi, int event_startmi, int event_endmi)
      throws HttpPresentationException, webschedulePresentationException
    {

    int conflict = 0;
        int conflict_has_been_set = 0;

          try {

          	S_event[] EventList = S_eventFactory.findS_eventsForDate(year,month,day);
          	
	         // Get collection of events and loop through collection
	        for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	
                	S_event currentS_event = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	int starthi = currentS_event.getStarth();
	        	int startmi = currentS_event.getStartm();
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	        	/*int conflict = check_for(starthi,startmi,endhi,endmi,event_starthi,event_startmi,event_endhi,event_endmi);*/
	        	
	        	if (event_starthi < starthi)
	        		{
	        		  if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		
	        		 }
	        		
	        	// 1. if it is equal check min and end time	
	        	else if (event_starthi == starthi)
	        		{
	        		 if (event_startmi >= startmi)
	        		   {  	//if
	        		   conflict = 1;
	        		   System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		    conflict_has_been_set = 1;
	        		   //return conflict;
	        		   break;
	        		   }        //if
	        		   else {//else 1
	        		
	        			if (event_endhi > starthi)
	        		  	{
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	  //return conflict;
	        		  	   break;
	        		  	}
	        		  else if (event_endhi == starthi)
	        		  	{
	        		  	  if (event_endmi > startmi)
	        		  	  {
	        		  	  conflict = 1;
	        		  	  System.out.println("System conflict flag  "+Integer.toString(conflict));
	        		  	  conflict_has_been_set = 1;
	        		  	   break;
	        		  	   }
	        		  	  }
	        		   }  //else 1
	        		 }
	        		
	        	
                       //1.    if it is greater than the booked on make sure it is not in the middle
                       else if (event_starthi > starthi)
                       		{
                       		if (event_starthi < endhi)
                       		 {
                       		   conflict = 1;
                       		   System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		   conflict_has_been_set = 1;
                       		   break;
                       		 }
                       		else if (event_starthi == endhi)
                       		    {
                       		      if (event_startmi < endmi)
                       		      {
                       		
                       		      	conflict = 1;
                       		      	 System.out.println("System conflict flag  "+Integer.toString(conflict));
                       		      	  conflict_has_been_set = 1;
                       		      	 //return conflict;
                       		      	 break;
                       		      	  }
                       		    }
                       		
                       		}
	             	        	
                      } // for loop

                     } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);
            throw new webschedulePresentationException("Error getting Events for date: ", ex);
	    }
	
	System.out.println("System conflict flag  "+Integer.toString(conflict));
	if ( conflict_has_been_set == 1)
	        return 1;
	else return 0;
 	 	
  } //end procedure		
		

	
protected void saveBlocktime (Blocktimec theBlocktime)
   throws HttpPresentationException, webschedulePresentationException
      {	
        String starth = this.getComms().request.getParameter(STARTH);
   	String startm = this.getComms().request.getParameter(STARTM);
    	String endh = this.getComms().request.getParameter(ENDH);
    	String endm = this.getComms().request.getParameter(ENDM);
	String day = this.getComms().request.getParameter(DAY);    	
        String month = this.getComms().request.getParameter(MONTH);
    	String year = this.getComms().request.getParameter(YEAR);
    	String btype = this.getComms().request.getParameter(BTYPE);
    	String description = this.getComms().request.getParameter(DESCRIPTION);
	System.out.println("Start time off Block time module "+starth);
	System.out.println("Start time off Block time module "+startm);
	System.out.println("Start time off Block time module "+endh);
	System.out.println("Start time off Block time module "+endm);	
	System.out.println("Day off Block time module "+day);
	System.out.println("Month off Block time module "+month);    	
    	System.out.println("Year off Block time module "+year);
    	System.out.println("Flag time off Block time module "+btype);
    	System.out.println("Description time off Block time module "+description);
    	
   try
     { 	
     	System.out.println("About to save 1 ");
	theBlocktime.setStarth(Integer.parseInt(starth));
        theBlocktime.setStartm(Integer.parseInt(startm));
    	theBlocktime.setEndh(Integer.parseInt(endh));
    	theBlocktime.setEndm(Integer.parseInt(endm));
    	theBlocktime.setDay(Integer.parseInt(day));
    	theBlocktime.setMonth(Integer.parseInt(month));
    	theBlocktime.setYear(Integer.parseInt(year));
    	theBlocktime.setFlag(Integer.parseInt(btype));    	
    	theBlocktime.setDescription(description);
    	 System.out.println("About to save 2 ");
    	theBlocktime.save();
    	System.out.println("saved ....... ");    		
    	 } catch(Exception ex) {
          throw new webschedulePresentationException("Error adding a block time information", ex);
     }
   }
 /**
     * Method to save a block time to the database
     */
 protected void saveMultiBlocktime()
          throws HttpPresentationException, webschedulePresentationException
    {

    String btype = this.getComms().request.getParameter(BTYPE);

    LinkedList mygood_list = new LinkedList();

    temp_hold good_event = null;
    mygood_list = this.getGoodlist();


    for (int c = 0; c <  mygood_list.size(); c++)
    	     {
    	  Object list_evento = mygood_list.get(c);
    	  good_event = (temp_hold) list_evento;
    	     System.out.println("Good Event Description "+good_event.getcdescription());
    	
    	  try {
    	 Blocktimec   theBlocktime = new Blocktimec();
    	theBlocktime.setStarth(good_event.getcstarth());
        theBlocktime.setStartm(good_event.getcstartm());
    	theBlocktime.setEndh(good_event.getcendh());
    	theBlocktime.setEndm(good_event.getcendm());
    	theBlocktime.setDay(good_event.getceventday());
    	theBlocktime.setMonth(good_event.getceventmonth());
    	theBlocktime.setYear(good_event.getceventyear());
    	theBlocktime.setFlag(Integer.parseInt(btype));    	
    	theBlocktime.setDescription(good_event.getcdescription());
    	theBlocktime.save();
    	    		
    	 } catch(Exception ex) {
          throw new webschedulePresentationException("Error adding a block time information", ex);
     	  }
     	}
}
}
