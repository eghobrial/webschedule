/**--------------------------------------------------------------
* webschedule
*
* @class: ucsdprojectsedit
* @version
* @author Eman Ghobrial
* @since  April 2009
* @update 
*
*--------------------------------------------------------------*/


package webschedule.presentation.projectMgmt;

import webschedule.presentation.BasePO;
import webschedule.presentation.s_eventMgmt.*;
import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.presentation.webschedulePresentationException;
import webschedule.business.webscheduleBusinessException;
import com.lutris.appserver.server.httpPresentation.*;
import com.lutris.appserver.server.session.*;
import com.lutris.util.*;
import com.lutris.xml.xmlc.*;
import com.lutris.xml.xmlc.html.*;
import org.w3c.dom.*;
import org.w3c.dom.html.*;
import java.util.Calendar;
import java.util.Date;
import java.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter; 

/**
 * projCatalog handles the proj management of the webschedule app
 * *
 */
public class ucsdprojectsedit extends BasePO 
{    
    /**
     * Constants representing HTTP parameters passed in from 
     *  the form submission
     */
     
      private static String PROJ_ID = "proj_id";  
    private static String PROJ_NAME = "proj_name";
    private static String DISCRIB = "discrib";
    private static String THOURS = "thours";
    private static String DHOURS = "dhours";
    private static String INDEXNUM = "indexnum";
    private static String PASSWORD = "password";
    private static String CODEOFPAY =  "codeofpay";
   
    private static String INVALID_ID = "invalidID";
    private static String EDIT_COMMAND = "edit";
    private static String PERSONID = "personID";
    private static String CONTACTNAME = "contactname";
    private static String CONTACTPHONE = "contactphone";
     private static String CONTACTEMAIL = "contactemail";
     
    private static String BILLADDR1 = "billaddr1";
    private static String BILLADDR2 = "billaddr2";
    private static String BILLADDR3 = "billaddr3";
    private static String CITY = "city";
    private static String STATE = "state";
    private static String ZIP = "zip";
   // private static String ACCOUNTID = "accountid";
   // private static String OUTSIDE  = "outside";
    // private static String EXP  = "exp";
    private static String  EXPDAY = "expday";
    private static String  EXPMONTH = "expmonth";
    private static String  EXPYEAR = "expyear";
    private static String NOTIFYCONTACT = "notifycontact";
   private static String  IEXPDAY = "iexpday";
    private static String  IEXPMONTH = "iexpmonth";
    private static String  IEXPYEAR = "iexpyear";
    
    private static String  MODBY = "Modby";
     private static String  MODDATE = "Moddate";
     private static String  NOTES = "notes";
     
     private static String  IRBNUM = "irbnum";

    /**
     * Superclass method override
     */
    public boolean loggedInUserRequired()
    {
        return true;
    }

    /** 
     *  Default event. Just show the page populated with any proj
     *  parameters that were passed along.
     */
    public String handleDefault() 
        throws HttpPresentationException 
    {
	    return showPage(null);
    }
    
    
    
    
     public String handleUcprojectinfo()
        throws HttpPresentationException, webschedulePresentationException
    {
    	String proj_id = this.getComms().request.getParameter(PROJ_ID);
	System.out.println("Handle Display Project Info visited  ");
	System.out.println("project id  =   "+proj_id);
	try
	{
            if (null == proj_id) {
            this.getSessionData().setUserMessage(proj_id + "  Please choose a valid project ");
                 throw new ClientPageRedirectException(UCSDPROJLIST_PAGE);
                 // Show error message that project not found
	} else{
		 
	  this.setProjectID(proj_id);
            throw new ClientPageRedirectException(UCSDPROJECTSEDIT_PAGE);
	     	}   
	    } catch (Exception ex) {
         	throw new webschedulePresentationException("Error getting PROJECT INFO", ex);
          }	
      }

    
    
     /*
     * Edits an existing proj in  the proj database
     */
    public String handleEdit()
        throws webschedulePresentationException, HttpPresentationException
    {		   
      
        Project project = null;

      
        
        // Try to get the proj by its ID
        try {
	        project = ProjectFactory.findProjectByID(this.getProjectID());
	     
	          String title = project.getProj_name();
	          System.out.println("project title: "+title);
	
        } catch(Exception ex) {
            this.getSessionData().setUserMessage("Please choose a valid PROJECT to edit");
            throw new ClientPageRedirectException(UCSDPROJECTSEDIT_PAGE);
        }
        
        // If we got a valid project then try to save it
        // If any fields were missing then redisplay the edit page, 
        //  otherwise redirect to the project catalog page
        try {
            saveProject(project);
            throw new ClientPageRedirectException(UCSDPROJECTSEDIT_PAGE);
        } catch(Exception ex) {
            return showPage("You must fill out all fields to edit this project");
        }    
    }
    
    
   
    /** 
     *  Produce HTML for this PO, populated by the project information
     *  that the user wants to edit
     */
    public String showPage(String errorMsg) 
        throws HttpPresentationException, webschedulePresentationException
    {        
        String proj_name = this.getComms().request.getParameter(PROJ_NAME);
        String personID = this.getComms().request.getParameter(PERSONID);
        String discrib = this.getComms().request.getParameter(DISCRIB);
        String indexnum = this.getComms().request.getParameter(INDEXNUM);
        String thours = this.getComms().request.getParameter(THOURS);
       // String dhours = this.getComms().request.getParameter(DHOURS);
      //  String projectID = this.getComms().request.getParameter(PROJ_ID);
        String password = this.getComms().request.getParameter(PASSWORD);
        String codeofpay = this.getComms().request.getParameter(CODEOFPAY);
    String contactname = this.getComms().request.getParameter(CONTACTNAME);
    String contactphone = this.getComms().request.getParameter(CONTACTPHONE);
    String contactemail = this.getComms().request.getParameter(CONTACTEMAIL);
    String billaddr1 = this.getComms().request.getParameter(BILLADDR1);
    String billaddr2 = this.getComms().request.getParameter(BILLADDR2);
    String billaddr3 = this.getComms().request.getParameter(BILLADDR3);
    String city = this.getComms().request.getParameter(CITY);
    String state = this.getComms().request.getParameter(STATE);
    String zip = this.getComms().request.getParameter(ZIP);
 //   String accountid = this.getComms().request.getParameter(ACCOUNTID);
  //  String outside = this.getComms().request.getParameter(OUTSIDE);
   // String exp = this.getComms().request.getParameter(EXP);
    String expday = this.getComms().request.getParameter(EXPDAY);
    String expmonth = this.getComms().request.getParameter(EXPMONTH);
    String expyear = this.getComms().request.getParameter(EXPYEAR);
    String notifycontact = this.getComms().request.getParameter(NOTIFYCONTACT);
   // String iexpday = this.getComms().request.getParameter(IEXPDAY);
   // String iexpmonth = this.getComms().request.getParameter(IEXPMONTH);
    //String iexpyear = this.getComms().request.getParameter(IEXPYEAR);
    
    String  modby= this.getComms().request.getParameter(MODBY);
    String  moddate= this.getComms().request.getParameter(MODDATE);
    String  notes= this.getComms().request.getParameter(NOTES);
    
    String irbnum = this.getComms().request.getParameter(IRBNUM);
    
     // Instantiate the page object
    ucsdprojectseditHTML page = new ucsdprojectseditHTML();
    
     HTMLSelectElement	expday_select, expmonth_select, expyear_select;
	HTMLCollection		expday_selectCollection, expmonth_selectCollection, expyear_selectCollection;
	HTMLOptionElement	expday_option, expmonth_option, expyear_option;
	String			expday_optionName, expmonth_optionName, expyear_optionName;
		
		
	expday_select = (HTMLSelectElement)page.getElementExpday();
	expday_selectCollection = expday_select.getOptions();	
			
        expmonth_select = (HTMLSelectElement)page.getElementExpmonth();
	expmonth_selectCollection = expmonth_select.getOptions();	

	expyear_select = (HTMLSelectElement)page.getElementExpyear();
	expyear_selectCollection = expyear_select.getOptions();	
	
    
    
    HTMLSelectElement	iexpday_select, iexpmonth_select, iexpyear_select;
	HTMLCollection		iexpday_selectCollection, iexpmonth_selectCollection, iexpyear_selectCollection;
	HTMLOptionElement	iexpday_option, iexpmonth_option, iexpyear_option;
	String			iexpday_optionName, iexpmonth_optionName, iexpyear_optionName;
		
		
	iexpday_select = (HTMLSelectElement)page.getElementIexpday();
	iexpday_selectCollection = iexpday_select.getOptions();	
			
        iexpmonth_select = (HTMLSelectElement)page.getElementIexpmonth();
	iexpmonth_selectCollection = iexpmonth_select.getOptions();	

	iexpyear_select = (HTMLSelectElement)page.getElementIexpyear();
	iexpyear_selectCollection = iexpyear_select.getOptions();	
	
	
	   
	 
	    
        Project theProject = null;
	String  userID = null;
	
	String  proj_describ, firstname, lastname, piname, user_email, proj_notes;
	int expdayi, expmonthi, expyeari;
	
	double dhours;
	 Date iacucdate = new  Date();
	 java.sql.Date moddated ;
	 
String projectID = this.getProjectID();

        System.out.println("project Id at show edit page "+projectID);
        try {
            theProject = ProjectFactory.findProjectByID(projectID);
	//    userID = project.getUserID();
	    
	 //     proj_name = theProject.getProj_name();
	    proj_describ = theProject.getDescription();
	    
	    firstname = theProject.getUserFirstName();	    
	    lastname = theProject.getUserLastName();	
	    user_email = theProject.getUserEmail();	
	    piname = firstname+" "+lastname;
	      
	    //cname = theProject.getCname();
	    //cphone = theProject.getCphone();
	    //cemail = theProject.getCemail();
	   
	//    indexnum = theProject.getIndexnum();
	
	   
	//    Baline1 = theProject.getBilladdr1();
	//    Baline2 = theProject.getBilladdr2();
	//    Baline3 = theProject.getBilladdr3();
	 //   Bacity = theProject.getCity();
	 //   Bast = theProject.getState();
	 //   Bazip = theProject.getZip();
	    
	    expdayi = theProject.getExpday();	    
	    expmonthi = theProject.getExpmonth();	    
	    expyeari = theProject.getExpyear();	    
	    
	  //  fpname = theProject.getContactname();
	 //   fpphone = theProject.getContactphone();
	   
	     moddated = theProject.getModDate();
	   // thours = theProject.getTotalhours();
	    dhours = theProject.getDonehours();
	    iacucdate = theProject.getIACUCDate();
	    proj_notes = theProject.getNotes();
	    
       /*     // Catch any possible database exception in findProjectByID()
        } catch(webscheduleBusinessException ex) {
            this.getSessionData().setUserMessage("Please choose a valid project to edit");
            throw new ClientPageRedirectException(PROJECT_ADMIN_PAGE);
        }
        */
  int iexpyeari = iacucdate.getYear();
     iexpyeari = iexpyeari + 1900;
  
     
     int iexpmonthi = iacucdate.getMonth();
      //add one
     iexpmonthi = iexpmonthi+1;
     int iexpdatei = iacucdate.getDate();
      String iexpyears = Integer.toString(iexpyeari);
     String iexpmonths = Integer.toString(iexpmonthi);
     String iexpdates = Integer.toString(iexpdatei);		
	
String expyears = Integer.toString(expyeari);
     String expmonths = Integer.toString(expmonthi);
     String expdays = Integer.toString(expdayi);		
 
     //   try {
            // If we received a valid projectID then try to show the project's contents,
            //  otherwise try to use the HTML input parameters
            //page.getElementProjID().setValue(project.getHandle());

page.setTextPiname(piname);
page.setTextEmail(user_email);
            if(null != proj_name && proj_name.length() != 0) {
                page.getElementProj_name().setValue(proj_name);
            } else {
                page.getElementProj_name().setValue(theProject.getProj_name());
            }
	    
	
            if(null != discrib   && discrib.length() != 0) {
                page.getElementDiscrib().setValue(discrib);
            } else {
                page.getElementDiscrib().setValue(proj_describ);
            }
        
            if(null != indexnum && indexnum.length() != 0) {
                page.getElementIndexnum().setValue(indexnum);
            } else {
                page.getElementIndexnum().setValue(theProject.getIndexnum());
            }

             if(null != thours && thours.length() != 0) {
                page.getElementThours().setValue(thours);
            } else {
                page.getElementThours().setValue(Double.toString(theProject.getTotalhours()));
            }

page.setTextDhour(Double.toString(theProject.getDonehours()));

/*              if(null != dhours && dhours.length() != 0) {
                page.getElementDhours().setValue(dhours);
            } else {
                page.getElementDhours().setValue(Double.toString(project.getDonehours()));
            }*/
	    
              if(null != codeofpay && codeofpay.length() != 0) {
                page.getElementCodeofpay().setValue(codeofpay);
            } else {
                page.getElementCodeofpay().setValue(Integer.toString(theProject.getCodeofpay()));
            }

        
	
	      if(null != contactname && contactname.length() != 0) {
                page.getElementContactname().setValue(contactname);
            } else {
                page.getElementContactname().setValue(theProject.getContactname());
            }
	    
	       if(null != contactphone && contactphone.length() != 0) {
                page.getElementContactphone().setValue(contactphone);
            } else {
                page.getElementContactphone().setValue(theProject.getContactphone());
            }
	    
	       if(null != contactemail && contactemail.length() != 0) {
                page.getElementContactemail().setValue(contactemail);
            } else {
                page.getElementContactemail().setValue(theProject.getFpemail());
            }

	      if(null != billaddr1 && billaddr1.length() != 0) {
                page.getElementBilladdr1().setValue(billaddr1);
            } else {
                page.getElementBilladdr1().setValue(theProject.getBilladdr1());
            }


	  /*    if(null != billaddr2 && billaddr2.length() != 0) {
                page.getElementBilladdr2().setValue(billaddr2);
            } else {
                page.getElementBilladdr2().setValue(theProject.getBilladdr2());
            }
*/
	
	 if(null != billaddr3 && billaddr3.length() != 0) {
                page.getElementBilladdr3().setValue(billaddr3);
            } else {
                page.getElementBilladdr3().setValue(theProject.getBilladdr3());
            }


 if(null != city && city.length() != 0) {
                page.getElementCity().setValue(city);
            } else {
                page.getElementCity().setValue(theProject.getCity());
            }

	 if(null != state && state.length() != 0) {
                page.getElementState().setValue(state);
            } else {
                page.getElementState().setValue(theProject.getState());
            }

 if(null != zip && zip.length() != 0) {
                page.getElementZip().setValue(zip);
            } else {
                page.getElementZip().setValue(theProject.getZip());
            }

	
	
/*	if(null != this.getComms().request.getParameter(EXP)) {
            page.getElementExpBox().setChecked(true);
        } else {
            page.getElementExpBox().setChecked(project.isExp());
        }
*/

  if(null != expday && expday.length() != 0) {
                page.getElementExpday().setValue(expday);
            } else {
                page.getElementExpday().setValue(Integer.toString(theProject.getExpday()));
            }
 


if(null != expmonth && expmonth.length() != 0) {
                page.getElementExpmonth().setValue(expmonth);
            } else {
                page.getElementExpmonth().setValue(Integer.toString(theProject.getExpmonth()));
            }
 
if(null != expyear && expyear.length() != 0) {
                page.getElementExpyear().setValue(expyear);
            } else {
                page.getElementExpyear().setValue(Integer.toString(theProject.getExpyear())); 	 
}


	      if(null != notifycontact && notifycontact.length() != 0) {
                page.getElementNotifycontact().setValue(notifycontact);
            } else {
                page.getElementNotifycontact().setValue(theProject.getNotifycontact());
            }

   if(null != notes && notes.length() != 0) {
	Node    notesText = page.getElementNotes().getOwnerDocument().createTextNode(notes);  
                page.getElementNotes().appendChild(notesText);
            } else {
	  Node    notesText = page.getElementNotes().getOwnerDocument().createTextNode(theProject.getNotes());  
                page.getElementNotes().appendChild(notesText);  
            }
	    
	    System.out.println(" *** Notes of UCSD project Edit *** "+notes);


if (expyeari > 2000)
         {
         int expday_optionlen = expday_selectCollection.getLength();
         for (int i=0; i< expday_optionlen; i++) {
	  expday_option = (HTMLOptionElement)expday_selectCollection.item(i);
	  expday_optionName = expday_option.getValue();
	  if (expday_optionName.equals(expdays))
	     expday_option.setSelected(true);
	  else
	     expday_option.setSelected(false);
	    }  

         int expmonth_optionlen = expmonth_selectCollection.getLength();
         for (int i=0; i< expmonth_optionlen; i++) {
	  expmonth_option = (HTMLOptionElement)expmonth_selectCollection.item(i);
	  expmonth_optionName = expmonth_option.getValue();
	  if (expmonth_optionName.equals(expmonths))
	     expmonth_option.setSelected(true);
	  else
	     expmonth_option.setSelected(false);
	    }  
	    
	      int expyear_optionlen = expyear_selectCollection.getLength();
	for (int i=0; i< expyear_optionlen; i++) {
	  expyear_option = (HTMLOptionElement)expyear_selectCollection.item(i);
	  expyear_optionName = expyear_option.getValue();
	  if (expyear_optionName.equals(expyears))
	     expyear_option.setSelected(true);
	  else
	     expyear_option.setSelected(false);
	    }  

           }


if (iexpyeari > 2000)
         {
         int iexpday_optionlen = iexpday_selectCollection.getLength();
         for (int i=0; i< iexpday_optionlen; i++) {
	  iexpday_option = (HTMLOptionElement)iexpday_selectCollection.item(i);
	  iexpday_optionName = iexpday_option.getValue();
	  if (iexpday_optionName.equals(iexpdates))
	     iexpday_option.setSelected(true);
	  else
	     iexpday_option.setSelected(false);
	    }  

         int iexpmonth_optionlen = iexpmonth_selectCollection.getLength();
         for (int i=0; i< iexpmonth_optionlen; i++) {
	  iexpmonth_option = (HTMLOptionElement)iexpmonth_selectCollection.item(i);
	  iexpmonth_optionName = iexpmonth_option.getValue();
	  if (iexpmonth_optionName.equals(iexpmonths))
	     iexpmonth_option.setSelected(true);
	  else
	     iexpmonth_option.setSelected(false);
	    }  
	    
	      int iexpyear_optionlen = iexpyear_selectCollection.getLength();
	for (int i=0; i< iexpyear_optionlen; i++) {
	  iexpyear_option = (HTMLOptionElement)iexpyear_selectCollection.item(i);
	  iexpyear_optionName = iexpyear_option.getValue();
	  if (iexpyear_optionName.equals(iexpyears))
	     iexpyear_option.setSelected(true);
	  else
	     iexpyear_option.setSelected(false);
	    }  

           }


if(null != irbnum && irbnum.length() != 0) {
                page.getElementIrbnum().setValue(irbnum);
            } else {
	                     page.getElementIrbnum().setValue(theProject.getIRBnum());
            }


	 page.setTextModdate(moddated.toString());   
	page.setTextModby(theProject.getModifiedby());   




  if(null == errorMsg) {

            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
	    } else {
                page.setTextErrorText(errorMsg);
            }
        } catch(webscheduleBusinessException ex) {
            throw new webschedulePresentationException("Error populating page for project editing", ex);
        }
        
   //     page.getElementEventValue().setValue(EDIT_COMMAND);
	    return page.toDocument();
    }
    
  

    /**
     * Method to save a new or existing project to the database
     */
    protected void saveProject(Project theProject)
        throws HttpPresentationException, webschedulePresentationException
    {        

        String proj_name = this.getComms().request.getParameter(PROJ_NAME);
        String personID = this.getComms().request.getParameter(PERSONID);
        String password = this.getComms().request.getParameter(PASSWORD);
        String discrib = this.getComms().request.getParameter(DISCRIB);
        String indexnum = this.getComms().request.getParameter(INDEXNUM);
        String thours = this.getComms().request.getParameter(THOURS);
        String dhours = this.getComms().request.getParameter(DHOURS);
        String codeofpay = this.getComms().request.getParameter(CODEOFPAY);

String contactname = this.getComms().request.getParameter(CONTACTNAME);
	String contactphone = this.getComms().request.getParameter(CONTACTPHONE);
	String contactemail = this.getComms().request.getParameter(CONTACTEMAIL);
	String billaddr1 = this.getComms().request.getParameter(BILLADDR1);
	String billaddr2 = this.getComms().request.getParameter(BILLADDR2);
	String billaddr3 = this.getComms().request.getParameter(BILLADDR3);
	String city = this.getComms().request.getParameter(CITY);
	String state = this.getComms().request.getParameter(STATE);
	String zip = this.getComms().request.getParameter(ZIP);
	//String accountid = this.getComms().request.getParameter(ACCOUNTID);
	//String isoutside = this.getComms().request.getParameter(OUTSIDE);
	//String exp = this.getComms().request.getParameter(EXP);
	String expday = this.getComms().request.getParameter(EXPDAY);
	String expmonth = this.getComms().request.getParameter(EXPMONTH);
	String expyear = this.getComms().request.getParameter(EXPYEAR);
	
	String iexpday = this.getComms().request.getParameter(IEXPDAY);
	String iexpmonth = this.getComms().request.getParameter(IEXPMONTH);
	String iexpyear = this.getComms().request.getParameter(IEXPYEAR);
	
	String notes = this.getComms().request.getParameter(NOTES);

 String irbnum = this.getComms().request.getParameter(IRBNUM);

 System.out.println(" Notes off save module "+ notes);

String notifycontact = this.getComms().request.getParameter(NOTIFYCONTACT);


java.sql.Date moddatesql;
 //calculation for the time right now
    	Calendar cancelinfo = Calendar.getInstance();
    	int todaydate = cancelinfo.get(cancelinfo.DAY_OF_MONTH);
    	int todaymonth = cancelinfo.get(cancelinfo.MONTH);
	todaymonth= todaymonth+1;
    	int todayyear = cancelinfo.get(cancelinfo.YEAR);
	String tempmod = todayyear+"-"+todaymonth+"-"+todaydate;
moddatesql=java.sql.Date.valueOf(tempmod);

java.sql.Date iacucsql,didate;

String tempiacuc = iexpyear+"-"+iexpmonth+"-"+iexpday;
iacucsql=java.sql.Date.valueOf(tempiacuc);
//get old project information
// Write to the modification file, format Date, Scanner, Who
//Which project Header, Proj_name,Description, 
// Old project record
//New project record

double dthours;

  String dproj_name,dindex,dcname, dcphone, dfemail, dbilladdr1, dbilladdr3,dcity,dstate, dzip,dnemail;
int dcode, dexpday, dexpmonth,dexpyear;


//f.close(); 
  try {
            dproj_name=theProject.getProj_name();
   	    dindex=theProject.getIndexnum();
	    dthours=theProject.getTotalhours();
	    dcode=theProject.getCodeofpay();
	    dcname=theProject.getContactname();
	    dcphone=theProject.getContactphone();
	    dfemail=theProject.getFpemail();
	    dbilladdr1=theProject.getBilladdr1();
           // theProject.setBilladdr2(theProject.getBilladdr2());
 dbilladdr3=theProject.getBilladdr3();
         
            dcity=theProject.getCity();
            dstate=theProject.getState();
            dzip=theProject.getZip();
            //theProject.setAccountid(theProject.getAccountid());
            dnemail=theProject.getNotifycontact();
	  dexpday=theProject.getExpday();
	  dexpmonth=	theProject.getExpmonth();			     
	dexpyear = theProject.getExpyear();				     


	      
didate= theProject.getIACUCDate();

 } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding project", ex);
        }    



String s = "\n proj_name,TotalHours,SchEmail,Code,FCName,FCPhone,FCEmail,BillingAddress,Index,ExpDate,IRBDate";

try {
FileOutputStream f = new FileOutputStream ("/home/emang/webschlog/projmodlog.txt",true);
for (int i = 0; i < s.length(); ++i)
f.write(s.charAt(i));
String s2="\n"+dproj_name+","+Double.toString(dthours)+","+dnemail+","+Integer.toString(dcode)+","+dcname+","+dcphone+","+dfemail+","+dbilladdr1+" "+dbilladdr3+" "+dcity+" "+dstate+" "+dzip+","+dindex+","+",";
for (int j = 0; j < s2.length(); ++j)
f.write(s2.charAt(j));
f.close();
} catch (IOException ioe) {
	System.out.println("IO Exception");
	}
	
        try {
	System.out.println(" trying to saving a project one "+ proj_name);
            theProject.setProj_name(proj_name);
     System.out.println(" trying to saving a project one "+ proj_name);
            theProject.setPassword( theProject.getPassword());
	    theProject.setDescription(discrib);
	    theProject.setIndexnum(indexnum);
	    theProject.setTotalhours(Double.parseDouble(thours));
	    theProject.setDonehours( theProject.getDonehours());
            theProject.setCodeofpay(Integer.parseInt(codeofpay));
	
	    theProject.setContactname(contactname);
	    theProject.setContactphone(contactphone);
	    theProject.setFpemail(contactemail);
	    theProject.setBilladdr1(billaddr1);
            theProject.setBilladdr2(theProject.getBilladdr2());

            theProject.setBilladdr3(billaddr3);
            theProject.setCity(city);
            theProject.setState(state);
            theProject.setZip(zip);
            theProject.setAccountid(theProject.getAccountid());
            theProject.setNotifycontact(notifycontact);
	    theProject.setOutside(false);
	    theProject.setExp(false);
	    theProject.setInstitute("UCSD");
	    theProject.setNotes(notes);
theProject.setIRBnum(irbnum);

//theProject.setCancredit(theProject.getCancredit());
/*	            if(null != this.getComms().request.getParameter(OUTSIDE)) {
                	theProject.setOutside(true);
            	    } else {
                	theProject.setOutside(false);
            	     }
 if(null != this.getComms().request.getParameter(EXP)) {
                	theProject.setExp(true);
            	    } else {
                	theProject.setExp(false);
			         	     }*/
					     
					     
theProject.setExpday(Integer.parseInt(expday));
theProject.setExpmonth(Integer.parseInt(expmonth));
theProject.setExpyear(Integer.parseInt(expyear));

	      personID = theProject.getOwner().getHandle();   
		theProject.setOwner(PersonFactory.findPersonByID(personID));
		
//theProject.setOwner(theProject.getOwner());


theProject.setModDate(moddatesql);

theProject.setIACUCDate(iacucsql);
theProject.setModifiedby(this.getUser().getLastname());



System.out.println(" trying to saving a project two "+ proj_name);
	        theProject.save();	
System.out.println(" trying to saving a project three "+ proj_name);
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding project", ex);
        }    
    }
}
