/**--------------------------------------------------------------
* Webschedule
*
* @class: SelectDate for 3T scanner
* Restricted project added (May 2004)
* @version
* @author: Eman Ghobrial
* @since:
*
*--------------------------------------------------------------*/


package webschedule.presentation.s_eventMgmt;

import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.business.s_event.*;
import webschedule.business.blocktime.*;
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
import webschedule.collections.*;
import webschedule.collections.impl.LList;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.lang.Object;

/**
 * SelectDate.java handles the main view functionality of the webschedule app.
 *
 */

public class SelectDate extends BasePO
{    
    /**
     * Constants
     */

    private static String ERROR_NAME = "ERROR_NAME";

     private static String PROJ_NAME = "proj_name";
    private static String DISCRIB = "discrib";
    private static String THOURS = "thours";
    private static String DHOURS = "dhours";
    private static String INDEXNUM = "indexnum";
    private static String PROJ_ID = "projID";
    private static String INVALID_ID = "invalidID";
	
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
        


    
    public String handleThrowException()
        throws Exception
    {
        throw new Exception("This is a test exception thrown from Login.java handleThrowException()");    
    }

    public String buildhref (int date, int month, int year)
    {
       String temps = "EditEvent.po?event=EditEvent&Date="+Integer.toString(date)+"&month="+Integer.toString(month)+"&year="+Integer.toString(year);
       return temps;
     }

     public void replacehref (SelectDateHTML page, String id, int date, int month, int year)
     {

      Element link = page.getElementById(id);
        HTMLAnchorElement mylink = (HTMLAnchorElement) link;
        if (date == 0)
        mylink.setHref("");
        else
        mylink.setHref(buildhref(date,month,year));

        Text t = XMLCUtil.getFirstText(link);
        if (date == 0)
	t.setData ("");
        else t.setData (Integer.toString(date));
     	}



      public String buildhref1 (String event_id)
    {
       String temps = "ChangeEvent.po?event=ChangeEvent&Event_id="+event_id;
       return temps;
     }

     public void replacehref1 (SelectDateHTML page, String id,  int date, int month, int year, String event_id, String mytext, String linkcolor)
     {

      Element link = page.getElementById(id);

        HTMLAnchorElement mylink = (HTMLAnchorElement) link;
        if (date == 0)
        mylink.setHref("");
        else if (linkcolor.equals("available"))
        {
        mylink.setHref(buildhref(date,month,year));
         mylink.setAttribute("class",linkcolor);
         }
        else
        {
        mylink.setHref(buildhref1(event_id));
        mylink.setAttribute("class",linkcolor);
        }

        Text t = XMLCUtil.getFirstText(link);
        if (date == 0)
	t.setData ("");
        else t.setData (mytext);
     	}

     /*replace the link text on the fillin_summary case*/
      public void replacehrefS (SelectDateHTML page, String id,  int date, int month, int year, String mytext, String linkcolor)
     {

      Element link = page.getElementById(id);

        HTMLAnchorElement mylink = (HTMLAnchorElement) link;
        mylink.setHref("");
        mylink.setAttribute("class",linkcolor);

        Text t = XMLCUtil.getFirstText(link);
        if (date == 0)
	t.setData ("");
        else t.setData (mytext);
     	}	
     	
     	
     public void fillin_table (SelectDateHTML page, String id,  int year, int month, int day)
       //throws HttpPresentationException, webschedulePresentationException
     {
     String cid = id+"templateRow";
     S_event[] EventList = null;
     Blocktimec[] BlockList = null;	
     String linkcolor = null;
    // System.out.println("templateRow " + cid);
     Element templateRowElement = page.getElementById(cid);
     HTMLTableRowElement templateRow = (HTMLTableRowElement) templateRowElement;
     Node EventTable = templateRow.getParentNode();
     templateRow.removeAttribute("cid");
     LList l = new LList();
     List list = l;
     String event_id;
     int nofbe;
     String mytext;
     String endmbs = null;
     String startmbs = null;
     String startms = null;
     String endms = null;
     String prefixstr = "Pilot";
     String suffixstr = "Dev";
     int unpaid = 0;


     /*if (this.getBlockflag() == 1)

     {      // get just the block information

     try {
           BlockList = BlocktimeFactory.findBlocktimeForDate (year,month,day);
            nofbe = BlockList.length;

             for (int numBlockE = 0; numBlockE < nofbe; numBlockE ++)   {// begin Block event for loop
	        	     Blocktimec BlockE = BlockList[numBlockE];
	        	     int starthb = BlockE.getStarth();
	        	     int startmb = BlockE.getStartm();
			     int endhb = BlockE.getEndh();
	        	     int endmb = BlockE.getEndm();		        	
	        	
	        	        String ampm = null;
	        	       	if (starthb < 12) ampm = "am";
      				else if (starthb == 12) ampm = "Noon";
		      		else if (starthb == 24) {
      					starthb = starthb-12;
		      			ampm = "Mid.";
      					}
		      		else {
      					starthb = starthb -12;
		      			ampm = "pm";
		      	     	}
				
				if (startmb == 0) startmbs = "00";
				 else startmbs = "30";		
	        		String starttime =
Integer.toString(starthb)+":"+startmbs+" "+ampm;
	        		 if (endhb < 12) ampm = "am";
      				else if (endhb == 12) ampm = "Noon";
		      		else if (endhb == 24) {
      					endhb = endhb-12;
		      			ampm = "Mid.";
      				}
		      		else {
      					endhb = endhb -12;
		      			ampm = "pm";
		      	     	}
				if (endmb == 0) endmbs = "00";
				 else endmbs = "30";
						
	        	        String endtime =   Integer.toString(endhb)+":"+endmbs+" "+ampm;
	        	        mytext = starttime+"-"+endtime+":Block time";
	        	      	event_id = BlockE.getHandle();
	        	      	linkcolor = "block";
	        	
				list.add(new temp_event (event_id,mytext, linkcolor));
			
			   	
			     		        	      	
	        }//end Block event for loop
	        	

        } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table with Block time: " + ex);

	    }
	
*/

if (this.getBillableflag() == 1)

     {      // get just the billable slots information

     try {
            EventList = S_eventFactory.findS_eventsForDate(year,month,day);


       for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	// begin for loop for events
               S_event currentS_event = EventList[numEvents];
	       // set text of new cells to values from string array
	       int starthi = currentS_event.getStarth();
	       //	System.out.println(" ***Start time for event ***" + Integer.toString(starthi));
	       int startmi = currentS_event.getStartm();
	       int event_dow = currentS_event.getEventdayofw();
	       //determine link color blue for prime time, purple for development and green otherwise.

	       if ((starthi >= 8) && (starthi <20) && (event_dow!=1) && (event_dow!=7))
	        	   if (currentS_event.isGrabbable())
		           linkcolor = "grab";
			 else 
			   linkcolor = "prime";
	        	else
			   if (currentS_event.isGrabbable())
		           linkcolor = "grab";
			 else  	
			
	        	   linkcolor = "nonprime";

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

	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();

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


	        	//String ownername = currentS_event.getOwnerFirstname()+" "+currentS_event.getOwnerLastname();
			String ownername = currentS_event.getOwnerLastname();
	        	String projowner =  currentS_event.getProj_owner_name() ;
			int codeofpay = currentS_event.getProj_Codeofpay();
			

/*
                    char devSeparator = 'D';
			int dot = projowner.lastIndexOf(devSeparator);
if (dot >=0) {unpaid=1;}
else
   {char pilotSeparator ='P';
    int dotp = projowner.lastIndexOf(pilotSeparator);
    if (dotp >=0) {unpaid=1;} else {unpaid=0;}
   }*/
   
 if ((codeofpay == 0) || (codeofpay == 100) || (codeofpay ==-1))
    unpaid = 1;
 else unpaid = 0;   

System.out.println("unpaid value " + Integer.toString(unpaid));

		if (unpaid == 0)
			mytext = starttime+"-"+endtime+":"+ownername;
		else
			//mytext = starttime+"-"+endtime+":"+"DevOrPilot";
			mytext = " ";

	        	//String mytext = starttime+"-"+endtime+":"+ownername+":"+projowner;
	        /*	if  (currentS_event.isDevelop())
	        	  {
	        	        if (this.getUser().isDeveloper())
	        		mytext = starttime+"-"+endtime+":"+ownername;
	        		else
	        		mytext = starttime+"-"+endtime+":Development time";
	        	  }
	        	else
	        		mytext = starttime+"-"+endtime+":"+ownername;*/

	        	System.out.println("link text " + mytext);

	  		event_id = currentS_event.getHandle();
	        	list.add(new temp_event (event_id,mytext, linkcolor));

	        	if (numEvents < EventList.length-1)
	        	 {
	        	 //check for gaps and display the gaps
		           S_event  nextS_event = EventList [numEvents+1];
	        	    int nstarthi = nextS_event.getStarth();
	        	    int nstartmi = nextS_event.getStartm();
	        	//if (nstarthi != endhi)
	        	//{
	        	   if (nstarthi < 12) ampm = "am";
      		      	   else if (nstarthi == 12) ampm = "Noon";
		      	   else if (nstarthi == 24) {
      				nstarthi = nstarthi-12;
		      		ampm = "Mid.";
      				}
		      	   else {
      				nstarthi = nstarthi -12;
		      		ampm = "pm";
		      	     }
		/*	  if (endhi != nstarthi)
			        {
				 //System.out.println(" *** Start and end not equal ***" );
			         String nstarttime = Integer.toString(nstarthi)+":"+Integer.toString(nstartmi)+" "+ampm;
	        	         linkcolor = "available";
	        	         String linktext = endtime+"-"+nstarttime+": OPEN";
	        	         String event_idd = "null";
	        	         list.add( new temp_event (event_idd,linktext, linkcolor));
				}*/
	        	//}
	          }
	       	}   // end for loop for events


        } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table with Block time: " + ex);

	    }

     } else {

         try {
         	if (this.getMyeventflag() == 1)
		   EventList = S_eventFactory.findS_eventsForDatePerson(year,month,day,this.getUser());
		else
                   EventList = S_eventFactory.findS_eventsForDate(year,month,day);

                 BlockList = BlocktimeFactory.findBlocktimeForDate (year,month,day);
                 nofbe = BlockList.length;

           // System.out.println(" ***Number of Block events ***" + Integer.toString(nofbe));


       if (EventList.length==0)
         {      //begin else if no events
          for (int numBlockE = 0; numBlockE < nofbe; numBlockE ++)   {// begin Block event for loop
	        Blocktimec BlockE = BlockList[numBlockE];
	        int starthb = BlockE.getStarth();
	        int startmb = BlockE.getStartm();
		int endhb = BlockE.getEndh();
	        int endmb = BlockE.getEndm();		        	
	                String ampm = null;
	               	if (starthb < 12) ampm = "am";
      			else if (starthb == 12) ampm = "Noon";
			else if (starthb == 24) { //begin else if
      				starthb = starthb-12;
				ampm = "Mid.";
      				}          //end else if
			else {     //begin else
      				starthb = starthb -12;
				ampm = "pm";
		        	}		       //end else
				
	        	 if (startmb == 0) startmbs = "00";
				 else startmbs = "30";
	        		String starttime = Integer.toString(starthb)+":"+startmbs+" "+ampm;
				
	        	 if (endhb < 12) ampm = "am";
      			else if (endhb == 12) ampm = "Noon";
			else if (endhb == 24) {       //begin else if
      			endhb = endhb-12;
			ampm = "Mid.";
      				}         //end else if
		      		else {                 //begin else
      					endhb = endhb -12;
		      			ampm = "pm";
		      	     	}		//end else
				
				
	        	        if (endmb == 0) endmbs = "00";
				 else endmbs = "30";
				String endtime =   Integer.toString(endhb)+":"+endmbs+" "+ampm;
				
	        	        mytext = starttime+"-"+endtime+":Block time";
	        	      	event_id = BlockE.getHandle();
	        	      	linkcolor = "block";
	        	      	list.add(new temp_event (event_id,mytext, linkcolor));
		} //end for loop
         }    // end else if no events



         else if  ((nofbe > 0)   && ( EventList.length > 0))
         {                       //begin num of block event if
         	int Blockindex = 0;
         	
	        for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	// begin for loop for events
                	S_event currentS_event = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	int starthi = currentS_event.getStarth();
	        //	System.out.println(" ***Start time for event ***" + Integer.toString(starthi));
	        	int startmi = currentS_event.getStartm();
	        	int event_dow = currentS_event.getEventdayofw();
	        	//determine link color blue for prime time, purple for development and green otherwise.
	        	
	        	for (int numBlockE = Blockindex; numBlockE < nofbe; numBlockE ++)   {// begin Block event for loop
	        	     Blocktimec BlockE = BlockList[numBlockE];
	        	     int starthb = BlockE.getStarth();
	        	 //    System.out.println(" ***Start time for Block event ***" + Integer.toString(starthb));
	        	     int startmb = BlockE.getStartm();
			     int endhb = BlockE.getEndh();
	        	     int endmb = BlockE.getEndm();		        	
	        	     if (starthb < starthi)
	        	     {                //begin if 1
	        	        String ampm = null;
	        	       	if (starthb < 12) ampm = "am";
      				else if (starthb == 12) ampm = "Noon";
		      		else if (starthb == 24) { //begin else if
      					starthb = starthb-12;
		      			ampm = "Mid.";
      					}          //end else if
		      		else {     //begin else
      					starthb = starthb -12;
		      			ampm = "pm";
		      	     	}		       //end else
				
				if (startmb == 0) startmbs = "00";
				 else startmbs = "30";
	        		String starttime = Integer.toString(starthb)+":"+startmbs+" "+ampm;
				
	        		
	        		 if (endhb < 12) ampm = "am";
      				else if (endhb == 12) ampm = "Noon";
		      		else if (endhb == 24) {       //begin else if
      					endhb = endhb-12;
		      			ampm = "Mid.";
      				}         //end else if
		      		else {                 //begin else
      					endhb = endhb -12;
		      			ampm = "pm";
		      	     	}		//end else
	        	        
				if (endmb == 0) endmbs = "00";
				 else endmbs = "30";
				String endtime =   Integer.toString(endhb)+":"+endmbs+" "+ampm;
				
	        	        mytext = starttime+"-"+endtime+":Block time";
	        	      	event_id = BlockE.getHandle();
	        	      	linkcolor = "block";
	        	      	Blockindex = Blockindex + 1;
				list.add(new temp_event (event_id,mytext, linkcolor));
			     } //end if 1
			     else if (starthb == starthi) { //begin elseif
			       if (startmb < startmi)
			     	 {          //begin if
			     	  String ampm = null;
	        	  	  if (starthb < 12) ampm = "am";
      				  else if (starthb == 12) ampm = "Noon";
		      		  else if (starthb == 24) {  //begin else if
      				  starthb = starthb-12;
		      		   ampm = "Mid.";
      				     }     //end else if
		      		   else {               //begin else
      				     starthb = starthb -12;
		      		     ampm = "pm";
		      	     	      }		//end else
				      
				     if (startmb == 0) startmbs = "00";
				 else startmbs = "30";
	        		String starttime = Integer.toString(starthb)+":"+startmbs+" "+ampm;
				 
				      
	        		    
	        		     if (endhb < 12) ampm = "am";
      				     else if (endhb == 12) ampm = "Noon";
		      		     else if (endhb == 24) { //begin else if
      				     endhb = endhb-12;
		      		     ampm = "Mid.";
      				      }          		//begin else if
		      		     else { //begin else
      				     endhb = endhb -12;
		      		     ampm = "pm";
		      		     }             //end else
				     
				     if (endmb == 0) endmbs = "00";
				 else endmbs = "30";
				String endtime =   Integer.toString(endhb)+":"+endmbs+" "+ampm;
				
		      	        	
	        	        mytext = starttime+"-"+endtime+":Block time";
	        	      	event_id = BlockE.getHandle();
	        	      	linkcolor = "block";
	        	      	Blockindex = Blockindex + 1;
				list.add(new temp_event (event_id,mytext, linkcolor));
			     	 	}		      //end if
			     	} //end else if
			     	else {          //begin else
			     	 String ampm = null;
	        	  	  if (starthb < 12) ampm = "am";
      				  else if (starthb == 12) ampm = "Noon";
		      		  else if (starthb == 24) {        //begin else if
      				  starthb = starthb-12;
		      		   ampm = "Mid.";
      				     }   //end else if
		      		   else {             //begin else
      				     starthb = starthb -12;
		      		     ampm = "pm";
		      	     	      }		      //end else
				      
					if (startmb == 0) startmbs = "00";
				 else startmbs = "30";
	        		String starttime = Integer.toString(starthb)+":"+startmbs+" "+ampm;
				 
				     	        		     
	        		     if (endhb < 12) ampm = "am";
      				     else if (endhb == 12) ampm = "Noon";
		      		     else if (endhb == 24) {    //begin else if
      				     endhb = endhb-12;
		      		     ampm = "Mid.";
      				      }  			//end else if
		      		     else {                     // begin else
      				     endhb = endhb -12;
		      		     ampm = "pm";
		      	     	}		                //end else
				
				 if (endmb == 0) endmbs = "00";
				 else endmbs = "30";
				String endtime =   Integer.toString(endhb)+":"+endmbs+" "+ampm;
				
	        		        	        
	        	        mytext = starttime+"-"+endtime+":Block time";
	        	      	event_id = BlockE.getHandle();
	        	      	linkcolor = "block";
	        	      	Blockindex = Blockindex + 1;
				list.add(new temp_event (event_id,mytext, linkcolor));
				
			   }		//end else  		
			     		        	      	
	        }//end Block event for loop
	        	
	        	if  (currentS_event.isDevelop())
	        	   linkcolor = "develop";
	        	else if ((starthi >= 8) && (starthi <20) && (event_dow!=1) && (event_dow!=7))
	        	   if (currentS_event.isGrabbable())
		            linkcolor = "grab";
			else
			   linkcolor = "prime";
	        	else
			 if (currentS_event.isGrabbable())
		            linkcolor = "grab";
			  else 
	        	   linkcolor = "nonprime";
	        	
	        	
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
			     	  	
	        	
	        		   	
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	        	
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
	        		
				        	
	        	//String ownername = currentS_event.getOwnerFirstname()+" "+currentS_event.getOwnerLastname();
			String ownername = currentS_event.getOwnerLastname();	        	
	        	String projowner =  currentS_event.getProj_owner_name() ;
	        	//String mytext = starttime+"-"+endtime+":"+ownername+":"+projowner;
	        	if  (currentS_event.isDevelop())
	        	  {
	        	        if (this.getUser().isDeveloper())
	        		mytext = starttime+"-"+endtime+":"+ownername;
	        		else
	        		mytext = starttime+"-"+endtime+":Development time";
	        	  }	
	        	else
	        		mytext = starttime+"-"+endtime+":"+ownername;
	        		
	        	//System.out.println("link text " + mytext);
	  		event_id = currentS_event.getHandle();
	        	list.add(new temp_event (event_id,mytext, linkcolor));
	        	
	        	if (numEvents < EventList.length-1)
	        	 {
	        	 //check for gaps and display the gaps	
		           S_event  nextS_event = EventList [numEvents+1];
	        	    int nstarthi = nextS_event.getStarth();
	        	    int nstartmi = nextS_event.getStartm();
	        	//if (nstarthi != endhi)
	        	//{ 	        	
	        	   if (nstarthi < 12) ampm = "am";
      		      	   else if (nstarthi == 12) ampm = "Noon";
		      	   else if (nstarthi == 24) {
      				nstarthi = nstarthi-12;
		      		ampm = "Mid.";
      				}
		      	   else {
      				nstarthi = nstarthi -12;
		      		ampm = "pm";
		      	     }	
			  if (endhi != nstarthi) 
			        {
				 //System.out.println(" *** Start and end not equal ***" );
			         String nstarttime = Integer.toString(nstarthi)+":"+Integer.toString(nstartmi)+" "+ampm;
	        	         linkcolor = "available";
	        	         String linktext = endtime+"-"+nstarttime+": OPEN";
	        	         String event_idd = "null";
	        	         list.add( new temp_event (event_idd,linktext, linkcolor));
				} 
	        	//}
	          }
	       	}   // end for loop for events

         }	//end num of block events if stat




         else
         {
	
	        for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	
                	S_event currentS_event = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	int starthi = currentS_event.getStarth();
	        	int event_dow = currentS_event.getEventdayofw();
	        	//determine link color blue for prime time, purple for development and green otherwise.
	        	
	        	if  (currentS_event.isDevelop())
	        	   linkcolor = "develop";
	        	else if ((starthi >= 8) && (starthi <20) && (event_dow!=1) && (event_dow!=7))
			if (currentS_event.isGrabbable())
		           linkcolor = "grab";
			 else  
	        	   linkcolor = "prime";
	        	else
			if (currentS_event.isGrabbable())
		           linkcolor = "grab";
			 else  
	        	   linkcolor = "nonprime";
	        	
	        	int startmi = currentS_event.getStartm();
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
			     	
	        	
	        		   	
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	        	
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
	        		
			

	        	//String ownername = currentS_event.getOwnerFirstname()+" "+currentS_event.getOwnerLastname();
			String ownername = currentS_event.getOwnerLastname();	        	
	        	String projowner =  currentS_event.getProj_owner_name() ;
	        	//String mytext = starttime+"-"+endtime+":"+ownername+":"+projowner;
	        	if  (currentS_event.isDevelop())
	        	  {
	        	        if (this.getUser().isDeveloper())
	        		mytext = starttime+"-"+endtime+":"+ownername;
	        		else
	        		mytext = starttime+"-"+endtime+":Development time";
	        	  }	
	        	else
	        		mytext = starttime+"-"+endtime+":"+ownername;
	        		
	        	//System.out.println("link text " + mytext);
	        	event_id = currentS_event.getHandle();
	        	list.add(new temp_event (event_id,mytext, linkcolor));
	        	
	        	if (numEvents < EventList.length-1)
	        	 {
	        	 //check for gaps and display the gaps	
		           S_event  nextS_event = EventList [numEvents+1];
	        	    int nstarthi = nextS_event.getStarth();
	        	    int nstartmi = nextS_event.getStartm();
	        	//if (nstarthi != endhi)
	        	//{ 	        	
	        	   if (nstarthi < 12) ampm = "am";
      		      	   else if (nstarthi == 12) ampm = "Noon";
		      	   else if (nstarthi == 24) {
      				nstarthi = nstarthi-12;
		      		ampm = "Mid.";
      				}
		      	   else {
      				nstarthi = nstarthi -12;
		      		ampm = "pm";
		      	     }	
			     
			  if (endhi != nstarthi) 
			        {	
				//System.out.println(" *** Start and end not equal ***" );
	        	  String nstarttime = Integer.toString(nstarthi)+":"+Integer.toString(nstartmi)+" "+ampm;
	        	  linkcolor = "available";
	        	  String linktext = endtime+"-"+nstarttime+": OPEN";
	        	  String event_idd = "null";
	        	  list.add( new temp_event (event_idd,linktext, linkcolor));
			  }
	        	//}
	          }
	       	}	
	   }//end else if no block time listed    	
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);

	    }
	
} //end else block flag	
	
	   int numofele = list.length();
    	   //System.out.println(" number of elements in the temp array " + Integer.toString(numofele));
    	   temp_event list_event = null;
    	   for ( int k = 0; k < list.length(); k++)
	          {
	               	Object list_evento = list.elementAt(k);
	        	list_event = (temp_event) list_evento;
	        	event_id = list_event.getcevent_id();
	        	//System.out.println("event id off new procedure " + event_id);
	        	String mytext2 = list_event.getcmytext();
	        	//System.out.println("text off new procedure " + mytext2);
	        	String linkcolor2 =  list_event.getclinkcolor();
			//System.out.println("link color off new procedure " + linkcolor2);	        	
	        	replacehref1 (page,  id, day,  month, year,event_id,mytext2,linkcolor2); 	
	        	// Add a deep clone of the row to the DOM
              		Node clonedNode = templateRow.cloneNode(true);
                	EventTable.appendChild(clonedNode);
                    }
	           templateRow.getParentNode().removeChild(templateRow);	
	
	
  }




   public void fillin_summary_table (SelectDateHTML page, String id,  int year, int month, int day)
       //throws HttpPresentationException, webschedulePresentationException
     {
     String cid = id+"templateRow";
     S_event[] EventList = null;	
     String linkcolor = null;
     //System.out.println("templateRow " + cid);
     Element templateRowElement = page.getElementById(cid);
     HTMLTableRowElement templateRow = (HTMLTableRowElement) templateRowElement;
     Node EventTable = templateRow.getParentNode();
     templateRow.removeAttribute("cid");
     String mytext;
     Node clonedNode;

     double develop_count = 0;
     double prime_count = 0;
     double nonprime_count = 0;
     double shortnotice_count = 0;
     double primepercent = 0.0, nonprimepercent = 0.0, develpercent =0.0;
     int  primepercenti = 0, nonprimepercenti = 0, develpercenti = 0, prime_bar, nonprime_bar, develope_bar;
     int t, numofchar=0,nnumofchar=0,dnumofchar=0;
     String mybar = "",nmybar = "", dmybar ="";
     String myspacebar = "",nmyspacebar = "", dmyspacebar = "";

         try {
         	EventList = S_eventFactory.findS_eventsForDate(year,month,day);
	        double starttime;
		double endtime;
		double eventhours;
	        for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	
                	S_event currentS_event = EventList[numEvents];
	        	// set text of new cells to values from string array
	        	
	        	
	        	int starthi = currentS_event.getStarth();
	        	int startmi = currentS_event.getStartm();
	        	int endhi = currentS_event.getEndh();
	        	int endmi = currentS_event.getEndm();
	        	int event_dow = currentS_event.getEventdayofw();
	        	
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
	        	
	        	//determine link color blue for prime time, purple for development and green otherwise.
	        	if ((starthi >= 8) && (starthi <20) && (event_dow!=1) && (event_dow!=7))
	        	  {
	        	   if  (currentS_event.isDevelop()) develop_count = develop_count + eventhours;
	        	   prime_count = prime_count +  eventhours;
//	        	   linkcolor = "develop";
//	        	   linkcolor = "prime";
	        	  }
	        	else
//	        	   linkcolor = "nonprime";
			   {	        	
			   if  (currentS_event.isDevelop()) develop_count = develop_count +  eventhours;
	        	   nonprime_count = nonprime_count +  eventhours;
	        	   }
	       	}	
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);

	    }
	
	    //System.out.println("Prime Count hours " + Double.toString(prime_count));
	     if (prime_count > 0)
	     {
	     primepercent = (prime_count / 12)*100;
	     primepercenti = (int) primepercent;
	     if (primepercenti > 10)
	     {
	     prime_bar = primepercenti / 10;
	     for (t=1; t <= prime_bar; t++)
	     	{
	     	     numofchar = numofchar + 1;
	     	     mybar = mybar + "=";
	     	 }
	     for (int t1=numofchar; t1<=10; t1++)
	     	     myspacebar = myspacebar +"..";	
	     //System.out.println("***********Prime Count percent ******* " + Double.toString(primepercent));
	     mytext =  mybar+myspacebar+Integer.toString(primepercenti)+" %Prime";
	     } else
	      	mytext = "=.................."+ Integer.toString(primepercenti)+" %Prime";
	     linkcolor = "prime";
	     replacehrefS (page,  id, day,  month, year,mytext,linkcolor);
	     clonedNode = templateRow.cloneNode(true);
             EventTable.appendChild(clonedNode);
             }


             if (nonprime_count > 0)
             {
	     nonprimepercent = (nonprime_count/12)*100;
	     nonprimepercenti = (int) nonprimepercent;
	     if (nonprimepercenti > 10)
	     {
	        nonprime_bar = nonprimepercenti / 10;
	        for (t=1; t <= nonprime_bar; t++)
	            {
	            nnumofchar = nnumofchar + 1;
	            nmybar = nmybar + "=";
	            }
	        for (int t1=nnumofchar; t1<=10; t1++)
	     	     nmyspacebar = nmyspacebar +"..";
	         mytext =  nmybar+nmyspacebar+Integer.toString(nonprimepercenti)+" %NonPrime";
	      } else
	      	mytext = "=.................."+ Integer.toString(nonprimepercenti)+" %NonPrime";
	     linkcolor = "nonprime";
	     replacehrefS (page,  id, day,  month, year,mytext,linkcolor);
	     clonedNode = templateRow.cloneNode(true);
             EventTable.appendChild(clonedNode);
             }


             if  (develop_count > 0)
             {
             develpercent = (develop_count/24)*100;
             develpercenti = (int) develpercent;
             if (develpercenti > 10)
	     {
	     develope_bar = develpercenti / 10;
	     for (t=1; t <= develope_bar; t++)
	     	{
	     	     dnumofchar = dnumofchar + 1;	
	     	     dmybar = dmybar + "=";
	     	 }
	     for (int t1=dnumofchar; t1<=10; t1++)
	     	     dmyspacebar = dmyspacebar +"..";
	     mytext =  dmybar+dmyspacebar+Integer.toString(develpercenti)+" %Develop";
	     } else
	      	mytext = "=.................."+ Integer.toString(develpercenti)+" %Develop";
	     linkcolor = "develop";
	     replacehrefS (page,  id, day,  month, year,mytext,linkcolor);
	     clonedNode = templateRow.cloneNode(true);
             EventTable.appendChild(clonedNode);
              }
           templateRow.getParentNode().removeChild(templateRow);	
  }

   	
	




	
	public void build_month_view (SelectDateHTML page, int currentyear, int currentmonth)
	//throws  webschedulePresentationException
	
	{
	String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};
        int lastday;
	String DaysofWeek[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	int daysPerMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	Calendar eventinfo = Calendar.getInstance();
	
	eventinfo.set(currentyear,currentmonth,1);
	int dow = eventinfo.get(eventinfo.DAY_OF_WEEK);
	//System.out.println("first day day of the week= " + dow +"   "+ DaysofWeek[dow-1]);

	// get the last day of the month check for leap year
	if (currentmonth == 1 && (currentyear % 4 == 0 && currentyear % 100 != 0))
		{
			lastday =  29;
		} else {
			lastday = daysPerMonth [currentmonth];
		}
	
	eventinfo.set(currentyear,currentmonth,lastday);
	int ldow = eventinfo.get(eventinfo.DAY_OF_WEEK);
	//System.out.println("month last day day of the week= " + ldow +"  "+DaysofWeek[ldow-1] );
	 //if the first day is not sunday, blank the first cloumns
	if (dow>1) {
	 	for (int i=1; i<dow;i++)
	 	{
	 		String daynumtext = "C"+i;
	 		String daynumtabletext1 = "S"+i;
	 		replacehref(page,daynumtext,0,0,0);
	 		if (this.getSummaryflag()==1) fillin_summary_table(page,daynumtabletext1,0,0,0);
	 		else
			  fillin_table(page,daynumtabletext1,0,0,0);
	        }
	        }
	//fill-in main table
	for (int daynum = 1; daynum < lastday+1; daynum++)
		{
			String daynumlinktext = "C"+(dow+daynum-1);
			String daynumtabletext = "S"+(dow+daynum-1);
			replacehref(page,daynumlinktext,daynum,currentmonth,currentyear);
			if (this.getSummaryflag()==1) fillin_summary_table(page,daynumtabletext,currentyear,currentmonth,daynum);
	 		else
			fillin_table(page,daynumtabletext,currentyear,currentmonth,daynum);
					
		}	
	int lastcell = dow+lastday;		
	//System.out.println("lastcel " + Integer.toString(lastcell)); 	
		
	if (lastcell < 38)
	{	
	//blank the rest of the table
	for (int j = 0; j < (39-lastcell); j++)
		{
			int lastcellc = lastcell+j;
		        String daynumlinktext2 = "C"+lastcellc;
		         String daynumtabletext2 = "S"+lastcellc;
		         replacehref(page,daynumlinktext2,0,0,0);
		         if (this.getSummaryflag()==1) fillin_summary_table(page,daynumtabletext2,0,0,0);
	 		else
			fillin_table(page,daynumtabletext2,0,0,0);	
		}	
	 }
	
		
  }	
	
    
    /**
     * 
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
        SelectDateHTML page = new SelectDateHTML();
        String projectname ;
        Project project = null;
        String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};

        //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg || 
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {       
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }
        try
        {
        project = this.getProject();
        projectname = project.getProj_name();
	page.setTextProjectID ("Select Date for " + projectname);

        } catch (webscheduleBusinessException ex) {
         throw new webschedulePresentationException("Error getting project name", ex);
         }

          int viewyear = this.getYear();
          int viewmonth = this.getMonth();
          page.setTextMonth(monthnames[viewmonth]);
              //System.out.println("Current Month= " + viewmonth);
	  page.setTextYear(Integer.toString(viewyear));
              //System.out.println("Current Year= " + viewyear);

          build_month_view(page,viewyear,viewmonth);
     		
	    return page.toDocument();
    }


    public String handleNextMonth()
     throws HttpPresentationException
    {
         int viewyear = this.getYear();
         int viewmonth = this.getMonth();
         if (viewmonth == 11)
          {
            viewmonth = 0;
            viewyear = viewyear + 1;
            this.setYear(viewyear);
            this.setMonth(viewmonth);
           } else {
           viewmonth = viewmonth + 1;
            this.setMonth(viewmonth);
            }
              throw new ClientPageRedirectException(SELECT_DATE_PAGE);
        }



    public String handlePrevMonth()
     throws HttpPresentationException
    {
         int viewyear = this.getYear();
         int viewmonth = this.getMonth();
         if (viewmonth == 0)
          {
            viewmonth = 11;
            viewyear = viewyear - 1;
            this.setYear(viewyear);
            this.setMonth(viewmonth);
           } else {
           viewmonth = viewmonth - 1;
            this.setMonth(viewmonth);
            }
              throw new ClientPageRedirectException(SELECT_DATE_PAGE);
        }

     public String handleSelectdate()
        throws HttpPresentationException, webscheduleBusinessException
    {

        String proj_id = this.getComms().request.getParameter(PROJ_ID);
	Project project = null;
	
	try {
            project = ProjectFactory.findProjectByID(proj_id);
	 } catch (Exception ex) {
 this.getSessionData().setUserMessage("Please choose a valid project to schedule for");
 throw new ClientPageRedirectException(PROJECT_CATALOG_PAGE);
    }
	
	
	/*try {
      		
           project = ProjectFactory.findProjectByID(proj_id);*/

	    
	    if ((project == null) || (proj_id == "invalidID")) {
	       //System.out.println("Proj id off select date event"+proj_id);
            	this.getSessionData().setUserMessage("Please choose a valid project ");
                 throw new ClientPageRedirectException(PROJECT_CATALOG_PAGE);
                 // Show error message that project not found
            } else {
            	this.setProject(project);
            	String DaysofWeek[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		int daysPerMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		Calendar rightnow = Calendar.getInstance();
		int currentmonth = rightnow.get(rightnow.MONTH);
		int currentyear = rightnow.get(rightnow.YEAR);
	
	
           	this.setYear(currentyear);
           	this.setMonth(currentmonth);
                //String projectname = project.getProj_name();


                throw new ClientPageRedirectException(SELECT_DATE_PAGE);
           }
       /* } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding PROJECT: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding PROJECT", ex);
        }*/
    }
	
   /* try {
            project = ProjectFactory.findProjectByID(proj_id);
	 } catch (Exception ex) {
 this.getSessionData().setUserMessage("Please choose a valid project to schedule for");
 throw new ClientPageRedirectException(PROJECT_CATALOG_PAGE);
    }
    
   try {
      		
               if ((project == null) || (proj_id == "invalidID")) {
	       //System.out.println("Proj id off select date event"+proj_id);
            	this.getSessionData().setUserMessage("Please choose a valid project ");
                 throw new ClientPageRedirectException(PROJECT_CATALOG_PAGE);
                 // Show error message that project not found
            } else {
            	this.setProject(project);
            	String DaysofWeek[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		int daysPerMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		Calendar rightnow = Calendar.getInstance();
		int currentmonth = rightnow.get(rightnow.MONTH);
		int currentyear = rightnow.get(rightnow.YEAR);
	
	
           	this.setYear(currentyear);
           	this.setMonth(currentmonth);
                //String projectname = project.getProj_name();


                throw new ClientPageRedirectException(SELECT_DATE_PAGE);
            }
        } catch(Exception ex) {
            this.writeDebugMsg("System error finding PROJECT: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding PROJECT", ex);
        }
    }*/

	

      /*try {
      		
            project = ProjectFactory.findProjectByID(proj_id);
	    
	    if ((project == null) || (proj_id == "invalidID")) {
	       //System.out.println("Proj id off select date event"+proj_id);
            	this.getSessionData().setUserMessage("Please choose a valid project ");
                 throw new ClientPageRedirectException(PROJECT_CATALOG_PAGE);
                 // Show error message that project not found
            } else {
            	this.setProject(project);
            	String DaysofWeek[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		int daysPerMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		Calendar rightnow = Calendar.getInstance();
		int currentmonth = rightnow.get(rightnow.MONTH);
		int currentyear = rightnow.get(rightnow.YEAR);
	
	
           	this.setYear(currentyear);
           	this.setMonth(currentmonth);
                //String projectname = project.getProj_name();


                throw new ClientPageRedirectException(SELECT_DATE_PAGE);
            }
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding PROJECT: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding PROJECT", ex);
        }
    }*/


   public String handleDisplayMyevents()
        throws HttpPresentationException
    {

      this.setMyeventflag(1);
      this.setSummaryflag(0);
       this.setBlockflag(0);
        this.setBillableflag(0);
      throw new ClientPageRedirectException(SELECT_DATE_PAGE);

    }


    public String handleBacktoall()
        throws HttpPresentationException
    {
      this.setMyeventflag(0);
      this.setSummaryflag(0);
      this.setBlockflag(0);
this.setBillableflag(0);
      throw new ClientPageRedirectException(SELECT_DATE_PAGE);
     }

     public String handleDisplaySummary()
        throws HttpPresentationException
    {
      this.setSummaryflag(1);
      throw new ClientPageRedirectException(SELECT_DATE_PAGE);
      }

      public String handleDisplayBlock()
        throws HttpPresentationException
    {

      this.setBlockflag(1);
      throw new ClientPageRedirectException(SELECT_DATE_PAGE);

    }


   public String handleDisplayBillable()
        throws HttpPresentationException
    {

      this.setBillableflag(1);
      throw new ClientPageRedirectException(SELECT_DATE_PAGE);

    }

}


