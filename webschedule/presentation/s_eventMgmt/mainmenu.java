/**--------------------------------------------------------------
* Webschedule
*
* @class:mainmenu
* @version
* @author: Eman Ghobrial
* @since: June 2004
* @update August 2007 add a bi-weekly view on main page
*--------------------------------------------------------------*/

package webschedule.presentation.s_eventMgmt;

import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.business.s_event.*;
import webschedule.business.problem.*;
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

/**
 * mainmenu.java shows the Main Menu Options
 *
 */
public class mainmenu extends BasePO
{

 /**
  * Constants
  */
  
     private static String PROBLEM_ID = "Prob_id"; 

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


    public String buildhref (String prob_id)
    {
       String temps = "../problemMgmt/problemdetails.po?event=showdetails&Prob_id="+prob_id;
       return temps;
     }

    


   public String handleshowedit()
        throws HttpPresentationException
    {
    	String prob_id = this.getComms().request.getParameter(PROBLEM_ID);
	//System.out.println("problem id  =   "+prob_id);
            if (null == prob_id) {
            this.getSessionData().setUserMessage(prob_id + "  Please choose a valid report ");
                 throw new ClientPageRedirectException(MAINMENU_PAGE);
                 // Show error message that project not found
            } else {
	    this.setProb_id(prob_id);
            	
	  throw new ClientPageRedirectException(PROBLEMDETAILS_PAGE);
		
           }
      }
      
      
        public String buildhref2 (int date, int month, int year)
    {
       String temps = "../projectMgmt/ProjCatalog.po";
       return temps;
     }

     public void replacehref2 (mainmenuHTML page, String id, int date, int month, int year)
     {
String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};
String months = monthnames[month];	
      Element link = page.getElementById(id);
        HTMLAnchorElement mylink = (HTMLAnchorElement) link;
        if (date == 0)
        mylink.setHref("");
        else
	{
	//System.out.println("Link replaced= " + (buildhref2(date,month,year)));
        mylink.setHref(buildhref2(date,month,year));
	}

        Text t = XMLCUtil.getFirstText(link);
        if (date == 0)
	{
	t.setData ("");
	////System.out.println("Date of replacing link= " + date);
	}
        else if (date==1) {
	String months1 = months + " "  +Integer.toString(date);
	t.setData (months1);
	}
	else{
	t.setData (Integer.toString(date));
	////System.out.println("Date of replacing link= " + date);
	}
     	}
      
      
      
         public String buildhref1 (String event_id)
    {
       String temps = "ShowEvent.po?event=ShowEvent&Event_id="+event_id;
       return temps;
     }
      
          public void replacehref1 (mainmenuHTML page, String id,  int date, int month, int year, String event_id, String mytext, String linkcolor)
     {

      Element link = page.getElementById(id);

        HTMLAnchorElement mylink = (HTMLAnchorElement) link;
        if (date == 0)
        mylink.setHref("");
        else if (linkcolor.equals("available"))
        {
        mylink.setHref(buildhref2(date,month,year));
     mylink.setHref(buildhref2(date,month,year));
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
      
        public void fillin_table (mainmenuHTML page, String id,  int year, int month, int day)
       //throws HttpPresentationException, webschedulePresentationException
     {
     String cid = id+"templateRow";
     S_event[] EventList = null;
   
     String linkcolor = null;
    // //System.out.println("templateRow " + cid);
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
     
      
       try {
            EventList = S_eventFactory.findS_eventsForDate(year,month,day);

		
       for(int numEvents = 0; numEvents < EventList.length; numEvents++) {	// begin for loop for events
               S_event currentS_event = EventList[numEvents];
	       // set text of new cells to values from string array
	       int starthi = currentS_event.getStarth();
	       //	//System.out.println(" ***Start time for event ***" + Integer.toString(starthi));
	       int startmi = currentS_event.getStartm();
	       int event_dow = currentS_event.getEventdayofw();
	       
	       linkcolor = "prime";
      
    //  else if ((starthi >= 8) && (starthi <20) && (event_dow!=1) && (event_dow!=7))
		/*	if (currentS_event.isGrabbable())
		           linkcolor = "grab";
			 else  
	        	   linkcolor = "prime";
	        	else
			if (currentS_event.isGrabbable())
		           linkcolor = "grab";
			 else  
	        	   linkcolor = "nonprime";
	        	*/
			
	        //	int startmi = currentS_event.getStartm();
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
	        	/*if  (currentS_event.isDevelop())
	        	  {
	        	        if (this.getUser().isDeveloper())
	        		mytext = starttime+"-"+endtime+":"+ownername;
	        		else
	        		mytext = starttime+"-"+endtime+":Development time";
	        	  }	
	        	else*/
	        		mytext = starttime+"-"+endtime+":"+ownername;
	        		
	        	////System.out.println("link text " + mytext);
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
				////System.out.println(" *** Start and end not equal ***" );
	        	  String nstarttime = Integer.toString(nstarthi)+":"+Integer.toString(nstartmi)+" "+ampm;
	        	  linkcolor = "available";
	        	  String linktext = endtime+"-"+nstarttime+": OPEN";
	        	  String event_idd = "null";
	        	  list.add( new temp_event (event_idd,linktext, linkcolor));
			  }
	        	//}
	          }
	       	}	
		
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Event table: " + ex);

	    }
      
      
	   int numofele = list.length();
    	   ////System.out.println(" number of elements in the temp array " + Integer.toString(numofele));
    	   temp_event list_event = null;
    	   for (int k = 0; k < list.length(); k++)
	          {
	               	Object list_evento = list.elementAt(k);
	        	list_event = (temp_event) list_evento;
	        	event_id = list_event.getcevent_id();
	        	////System.out.println("event id off new procedure " + event_id);
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



    /**
     *
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
        mainmenuHTML page = new mainmenuHTML();

      //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }

String statuscode[] = {"closed","open","in progress"};
String classcode[] = {"other","scanner","coils","physiological monitoring","stimulus presentation/response boxes","computer/networking"};
String severitycode[] = {"noncritial","serious","critical"};
  
	  Problem[] ProbList;
	
String monthnames[] = {"Jan","Feb","March","April","May","June","July","August","September","October ","November","December"};	
String DaysofWeek[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
int daysPerMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
Calendar rightnow = Calendar.getInstance();
int currentmonth = rightnow.get(rightnow.MONTH);
int currentyear = rightnow.get(rightnow.YEAR);
int currentdow = rightnow.get(rightnow.DAY_OF_WEEK);
int todaydate = rightnow.get(rightnow.DAY_OF_MONTH);
int actualdaynum = todaydate;
	
//System.out.println("Current DOW= " + currentdow);	
//System.out.println("Current today= " + todaydate);	

          // 	this.setYear(currentyear);
           //	this.setMonth(currentmonth);

    
    //  page.setTextMonth(monthnames[currentmonth]);
              //System.out.println("Current Month= " + viewmonth);
	//  page.setTextYear(Integer.toString(currentyear));
              //System.out.println("Current Year= " + viewyear);

       //   build_month_view(page,viewyear,viewmonth);
    
    
  /*  
   * build the bi-weekly view
   */
   
    int totalnumofdays = 21-currentdow;
    
    
  int lastday = 0;
    // get the last day of the month check for leap year
	if (currentmonth == 1 && (currentyear % 4 == 0 && currentyear % 100 != 0))
		{
			lastday =  29;
		} else {
			lastday = daysPerMonth [currentmonth];
		}
    String daynumtext, daynumtabletext;
     String daynumtext1, daynumtabletext1;
     
     
    int lastcday = todaydate + totalnumofdays;
  //System.out.println("Last calculated day= " + lastcday);  
  
  
 
 
    if  (lastcday <= lastday)
         { //if first day of the month is not sun
	  int rowcounter = 0;
	   int  numofextradays1 = 21 - currentdow;
	 	if (currentdow>1) {
		  for (int i=1;i<currentdow;i++)
		     {
		      
		     daynumtext = "C"+i;
		      //System.out.println("Row ID= " + daynumtext);
		     daynumtabletext = "S"+i;
		     //System.out.println("Row table ID= " + daynumtabletext);
		     replacehref2(page,daynumtext,0,0,0);
		     fillin_table (page,daynumtabletext,0,0,0);
		     rowcounter = rowcounter + 1;
		     }
	            }
		    //System.out.println("Start counter= " + rowcounter);
		  // fillin the rest 
		int startc = rowcounter;
		  for (int daynum1=startc+1;daynum1<22;daynum1++)
		    {
		   /*  daynumtext1 = "C"+(currentdow+daynum1-1);
		     System.out.println("Row ID= " + daynumtext1);
		     daynumtabletext1 = "S"+(currentdow+daynum1-1);
		     System.out.println("Row table ID= " + daynumtabletext1);
		 replacehref2(page,daynumtabletext1,actualdaynum,currentmonth,currentyear);
		      rowcounter = rowcounter + 1;
		     fillin_table (page,daynumtabletext1,currentyear,currentmonth,actualdaynum);
		 actualdaynum = actualdaynum + 1;*/
		  daynumtext1 = "C"+daynum1;
		     //System.out.println("Row ID= " + daynumtext1);
		     daynumtabletext1 = "S"+daynum1;
		     //System.out.println("Row table ID= " + daynumtabletext1);
		     //System.out.println("Actual Date= " + actualdaynum);
		 replacehref2(page,daynumtext1,actualdaynum,currentmonth,currentyear);
		      rowcounter = rowcounter + 1;
		     fillin_table (page,daynumtabletext1,currentyear,currentmonth,actualdaynum);
		 actualdaynum = actualdaynum + 1;
		    }
		    }
		    
 else // lastcday > lastday
	  {
	  //this case is when we expand on another month
	  int rowcounter1 = 0;
	    int  numofextradays = 14 - currentdow;
	     //System.out.println("***today ***" + todaydate);
	    int daysleft = lastday - todaydate;
	    //System.out.println("***Days Left ***" + daysleft);
	     //fill in the first cells if dow > 1
	     if  (currentdow > 1) {
	       for (int j=1; j<currentdow;j++)
	         {
		 
		  daynumtext = "C"+j;
		    //System.out.println("Row ID= " + daynumtext);
		  daynumtabletext = "S"+j;
		   //System.out.println("Row table ID= " + daynumtabletext);
		    replacehref2(page,daynumtext,0,0,0);
		    fillin_table (page,daynumtabletext,0,0,0);
		    rowcounter1 = rowcounter1 + 1;
		     }
	            }
		  // fillin the rest till end of current month
		  int startc1=rowcounter1+1;
		  for (int daynum1=startc1;daynum1<daysleft+startc1+1;daynum1++)
		    {
		     daynumtext1 = "C"+daynum1;
		       //System.out.println("Row ID= " + daynumtext1);
		     daynumtabletext1 = "S"+daynum1;
		       //System.out.println("Row table ID= " + daynumtabletext1);
		       replacehref2(page,daynumtext1,actualdaynum,currentmonth,currentyear);
		       rowcounter1 = rowcounter1 +1;
		     fillin_table (page,daynumtabletext1,currentyear,currentmonth,actualdaynum);
		      actualdaynum = actualdaynum + 1;
		    }   
		    //fillin the part of the next month
		    int nofdayfm = 21 - rowcounter1;
		    int startc2 = rowcounter1+1;
		    //reset the date
		      actualdaynum=1;
		      //increment the month
		      currentmonth = currentmonth+1;
		      // if the month is 12 current month has to be 0 and increment current year
		      if (currentmonth == 12)
		      {
		      currentmonth=0;
		      currentyear= currentyear+1;
		      }
		    for (int daynum2=startc2;daynum2<nofdayfm+startc2;daynum2++)
		     {
		   
		     daynumtext1 = "C"+daynum2;
		       //System.out.println("Row ID= " + daynumtext1);
		     daynumtabletext1 = "S"+daynum2;
		       //System.out.println("Row table ID= " + daynumtabletext1);
		       //System.out.println("*** Current Month *** " + currentmonth);
		       
		  replacehref2(page,daynumtext1,actualdaynum,currentmonth,currentyear);
		       rowcounter1 = rowcounter1 +1;
		     fillin_table (page,daynumtabletext1,currentyear,currentmonth,actualdaynum);
		      actualdaynum = actualdaynum + 1;
		     }
		    
  }
  
  
  
 /* 
*build the problem listing table	
*/
	  // Generate the person's Proj list and create the HTML template references
	    HTMLTableRowElement templateRow1 = page.getElementTemplateRow1();
       
        Node ProbTable = templateRow1.getParentNode();
      
	    		
	    // Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
	    templateRow1.removeAttribute("id");
       
        
        // Remove id attributes from the cells in the template row
        HTMLElement postdateCellTemplate = page.getElementPostdate();
	HTMLElement posttimeCellTemplate = page.getElementPosttime();
	/*    HTMLElement describCellTemplate = page.getElementDescrip();*/
	    HTMLElement statuscodeCellTemplate = page.getElementStatuscode();
	   
        
	    postdateCellTemplate.removeAttribute("id");
	 /*   describCellTemplate.removeAttribute("id");*/
	    statuscodeCellTemplate.removeAttribute("id");
	      String postpm = null;
	    int postday=0;
	    int postmonth=0;
	    int postyear=0;
	    int posth=0;
	    int postm=0;
	   int  scode=0;
	   int  ccode=0;
	   int sevcode=0;
	   
	String  postd = null;
	String postt = null;
	String 	problemID = null;
	String herftxt = null;
	String  describ = null;
	String postms = null;
	  
	    try {
	         ProbList = ProblemFactory.getProblemsList();
	       } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding PROJECT: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding PROJECT", ex);
        }
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
       for(int numProbs = 0; numProbs < ProbList.length; numProbs++) {	
                Problem currentProb = ProbList[numProbs];
		      	// set text of new cells to values from string array
			
			try {
			postday = currentProb.getPostday();
			postmonth = currentProb.getPostmonth();
			postyear = currentProb.getPostyear();
			posth = currentProb.getPosth();
			postm = currentProb.getPostm();
			postpm = currentProb.getPostpm();
			problemID = currentProb.getHandle();
			scode = currentProb.getStatuscode();
			describ = currentProb.getDescrib();
			sevcode = currentProb.getSeveritycode();
			ccode = currentProb.getClasscode();
			
			 } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding PROJECT: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding PROBLEM", ex);
        }

	      
if ((scode == 1) || (scode == 2)) 
{
postd=Integer.toString(postmonth)+"-"+Integer.toString(postday)+"-"+Integer.toString(postyear);
if (postm<10)
  postms = "0"+Integer.toString(postm);
else
   postms = Integer.toString(postm);
    
postt=Integer.toString(posth)+":"+postms+" "+postpm;

//System.out.println("Post Date"+postd);

herftxt=buildhref (problemID);

//System.out.println("herf Text"+herftxt);

Element link = page.getElementById("Descrip");

        HTMLAnchorElement mylink = (HTMLAnchorElement) link;
	mylink.setHref(herftxt);
	Text t = XMLCUtil.getFirstText(link);
	t.setData(describ);

page.setTextPostdate(postd);
	        	/*page.setTextDescrip(currentProb.getDescrib());*/
page.setTextPosttime(postt);	        
page.setTextStatuscode(statuscode[scode]);
page.setTextSeveritycode(severitycode[sevcode]);
page.setTextClasscode(classcode[ccode]);
                
	        // Add a deep clone of the row to the DOM
                Node clonedNode = templateRow1.cloneNode(true);
                ProbTable.appendChild(clonedNode);
               
	        // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProbTable.insertBefore(clonedNode, templateRow);
                
                // Now populate the select list
                // This algorithm is obscure because options are not normal 
                //  HTML elements
                // First populate the option value (the Proj database ID).
                //  Then append a text child as the option
                // text, which is what is displayed as the text
                // in each row of the select box
                
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjSelect.insertBefore(clonedOption, templateOption);
		}       
	
	}    
 
       
        // Finally remove the template row and template select option 
        //  from the page
	    templateRow1.getParentNode().removeChild(templateRow1);

	

	    return page.toDocument();
    }
}


