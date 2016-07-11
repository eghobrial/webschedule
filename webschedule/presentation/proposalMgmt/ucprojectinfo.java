/**--------------------------------------------------------------
* Webschedule
*
* @class:ucprojectinfo.java
* @version
* @author: Eman Ghobrial
* @since: Jan 2008
*
*--------------------------------------------------------------*/

package webschedule.presentation.proposalMgmt;

import webschedule.business.proposal.*;
import webschedule.business.person.*;
import webschedule.business.project.*;
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

import java.sql.Date;

/**
 *preview.java shows the Proposal Menu Options
 *
 */
public class ucprojectinfo extends BasePO
{

  private static String PROJ_ID = "proj_id";      
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


 public String handleUcprojectinfo()
        throws HttpPresentationException, webschedulePresentationException
    {
    	String proj_id = this.getComms().request.getParameter(PROJ_ID);
	System.out.println("Handle Quick View visited  ");
	System.out.println("project id  =   "+proj_id);
	try
	{
            if (null == proj_id) {
            this.getSessionData().setUserMessage(proj_id + "  Please choose a valid project ");
                 throw new ClientPageRedirectException(UCSDPROJECTS_PAGE);
                 // Show error message that project not found
	} else{
		 
	  this.setProjectID(proj_id);
            throw new ClientPageRedirectException(UCPROJECTINFO_PAGE);
	     	}   
	    } catch (Exception ex) {
         	throw new webschedulePresentationException("Error getting user login name", ex);
          }	
      }

    /**
     *
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
    	
//	String  oneop = this.getComms().request.getParameter(ONEOP);
        // Person Parameters
	String  user_email ;
	String firstname;
	String lastname;
	
	String piname;
	
	//Project parameters
	
        String proj_name ;
	String proj_describ;
	
	String indexnum ;

	String Baline1 ;
	String Baline2 ;
	String Baline3 ;
	String Bacity ;
	String Bast ;
	String Bazip;

	String fpname ;
	String fpphone;
   //   	String  fpemail ;
	
	double thours ;
	double dhours ;
	
	int expday ;
	int expmonth;
	int expyear;
	
	String edst, emst, eyst, expst;
	
      
        ucprojectinfoHTML page = new ucprojectinfoHTML();
	
	
	Project  theProject ;
	
	
	
	String projectID = this.getProjectID();
	
	//page.setTextPidnum(proposalID);
		
	  try {
            theProject = ProjectFactory.findProjectByID(projectID);
	   
	    proj_name = theProject.getProj_name();
	    proj_describ = theProject.getDescription();
	    
	    firstname = theProject.getUserFirstName();	    
	    lastname = theProject.getUserLastName();	
	    user_email = theProject.getUserEmail();	
	      
	    //cname = theProject.getCname();
	    //cphone = theProject.getCphone();
	    //cemail = theProject.getCemail();
	   
	    indexnum = theProject.getIndexnum();
	
	   
	    Baline1 = theProject.getBilladdr1();
	    Baline2 = theProject.getBilladdr2();
	    Baline3 = theProject.getBilladdr3();
	    Bacity = theProject.getCity();
	    Bast = theProject.getState();
	    Bazip = theProject.getZip();
	    
	    expday = theProject.getExpday();	    
	    expmonth = theProject.getExpmonth();	    
	    expyear = theProject.getExpyear();	    
	    
	    fpname = theProject.getContactname();
	    fpphone = theProject.getContactphone();
	   
	    
	    thours = theProject.getTotalhours();
	    dhours = theProject.getDonehours();
	        

if (expday < 10) 
		  edst = "0"+Integer.toString(expday);
	        else 
		
		   edst = Integer.toString(expday);
		
		if (expmonth < 10) 
		  emst = "0"+Integer.toString(expmonth);
	        else 
		   emst = Integer.toString(expmonth);
		  
		   eyst = Integer.toString( expyear);
		   
		
		expst = eyst+"-"+emst+"-"+edst;
		    
        } catch(webscheduleBusinessException ex) {
           // this.writeDebugMsg("System error finding Proposal: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding Proposal", ex);
        }
	

	piname = firstname +" "+lastname;
	
	  page.setTextProjname(proj_name);
	    page.setTextPdescrib(proj_describ);	

System.out.println(" PI NAME off Preview "+ piname);
	page.setTextPiname(piname);

	page.setTextEmail(user_email);
	
	
/*System.out.println(" Contact info off Preview "+ cname+cphone+cemail);
	page.setTextCname(cname);
	page.setTextCphone(cphone);
	page.setTextCemail(cemail);*/
	
	  if (indexnum == null) 
	    page.setTextIndexnum("Not on File");
	    else  page.setTextIndexnum(indexnum);
	
	   if (Baline1 == null) 
	    page.setTextBaline1("Not on File");
	    else  page.setTextBaline1(Baline1);
	    
	     if (Baline2 == null) 
	    page.setTextBaline2("Not on File");
	    else page.setTextBaline2(Baline2);
	    
	     if (Baline3 == null) 
	    page.setTextBaline3("Not on File");
	    else page.setTextBaline3(Baline3);
	    
	     if (Bacity == null) 
	    page.setTextBacity("Not on File");
	    else page.setTextBacity(Bacity);
	    
	     if (Bast == null) 
	    page.setTextBast("Not on File");
	    else page.setTextBast(Bast);   

             if (Bazip == null) 
	    page.setTextBazip("Not on File");
	    else page.setTextBazip(Bazip);
	    
	    if (fpname == null) 
	    page.setTextFpname("Not on File");
	    else page.setTextFpname(fpname);
	    
	     if (fpphone == null) 
	    page.setTextFpphone("Not on File");
	    else page.setTextFpphone(fpphone);
	 	   
	 
	 	
	//First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if (null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }
	
    return page.toDocument();
    }
    
  
    
}


