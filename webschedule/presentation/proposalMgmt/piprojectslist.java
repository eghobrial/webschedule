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
public class piprojectslist extends BasePO
{

  private static String PERSON_ID = "person_id";      
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


 public String buildhrefuc (String project_id)
    {
       String temps = "ucprojectinfo.po?event=ucprojectinfo&proj_id="+project_id;
       return temps;
     }

  public String buildhrefnonuc (String project_id)
    {
       String temps = "nonucprojectinfo.po?event=projectinfo&proj_id="+project_id;
       return temps;
     }

  public String buildhref (String project_id)
    {
       String temps = "ucprojectinfo.po?event=ucprojectinfo&proj_id="+project_id;
       return temps;
     }


 public String handleShowplist()
        throws HttpPresentationException, webschedulePresentationException
    {
    	String person_id = this.getComms().request.getParameter(PERSON_ID);
	System.out.println("Handle Quick View visited  ");
	System.out.println("PERSON id  =   "+person_id);
	try
	{
            if (null == person_id) {
            this.getSessionData().setUserMessage(person_id + "  Please choose a valid PI ");
                 throw new ClientPageRedirectException(PILIST_PAGE);
                 // Show error message that project not found
	} else{
		 
	  this.setPiID(person_id);
            throw new ClientPageRedirectException(PIPROJECTSLIST_PAGE);
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
    	 piprojectslistHTML page = new piprojectslistHTML();
//	String  oneop = this.getComms().request.getParameter(ONEOP);
        // Person Parameters
	/*String  user_email ;
	String firstname;
	String lastname;
	
	String piname;*/
	
	//Project parameters
	
	String institute;
        String proj_name , project_id, person_id;
	String proj_describ;
	
	/*String indexnum ;

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
	double dhours ;*/
	
	int expday ;
	int expmonth;
	int expyear;
	
	String edst, emst, eyst, expst, hreftxt;
	
      
        person_id =  this.getPiID();
	
	
	   //  String errorMsg = this.getSessionData().getAndClearUserMessage();
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	
	    // Generate the project's Proj list and create the HTML template references
	    HTMLTableRowElement templateRow = page.getElementTemplateRow();
      
        Node projectTable = templateRow.getParentNode();
      
	    		
	    // Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
	    templateRow.removeAttribute("id");
   //     templateOption.removeAttribute("id");
        
        // Remove id attributes from the cells in the template row
	 HTMLElement instituteCellTemplate = page.getElementInstitute();
	
	 
      HTMLElement expdateCellTemplate = page.getElementExpdate();
	
        
	 
	    
	instituteCellTemplate.removeAttribute("id");
	expdateCellTemplate.removeAttribute("id");
	
	  
        // Remove the dummy storyboard text from the prototype HTML
        // (e.g., "Van Halen One") from this option
        //templateOption.removeChild(templateOption.getFirstChild());
	
	    try {
	 Project[] projectList = ProjectFactory.findProjectsForPerson(PersonFactory.findPersonByID(person_id));
	        
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	        for(int numps = 0; numps < projectList.length; numps++) {	
                Project theproject = projectList[numps];
		
		
	if (!(theproject.isExp())) 
		 {		
		
		proj_name = theproject.getProj_name();
		institute = theproject.getInstitute();
		
		
            expday = theproject.getExpday();	    
	    expmonth = theproject.getExpmonth();	    
	    expyear = theproject.getExpyear();	 
	
	
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
		   		
		project_id = theproject.getHandle();
		
		System.out.println(" **** Current project ID off projectstatus ****"+ project_id);
					   
		
		
		      	// set text of new cells to values from string array
	  page.setTextInstitute(institute);
	
	 page.setTextExpdate(expst);
	 
	 if (!(theproject.isOutside())&&!(theproject.getCodeofpay()==0)) 
		 {		
	 	  hreftxt = buildhrefuc(project_id);
		 }
	else if	 ((theproject.isOutside())&&!(theproject.getCodeofpay()==0)) 
		 {		
	 	  hreftxt = buildhrefnonuc(project_id);	
		 } 
	else
	      hreftxt = buildhref(project_id);	 
     		 
	Element link = page.getElementById("Projectname");
	System.out.println(" **** Hi after Element off projectstatus ****");
	HTMLAnchorElement mylink = (HTMLAnchorElement) link;
	System.out.println(" **** Hi after HTMLElement off projectstatus ****");
	System.out.println(" **** Current project  hreftxt off projectstatus ****"+ hreftxt);		 
	mylink.setHref(hreftxt);
	System.out.println(" **** Hi after set href off projectstatus ****");
	Text t = XMLCUtil.getFirstText(link);
	System.out.println(" **** Hi after getting text off projectstatus ****");
	t.setData(proj_name);

	System.out.println(" **** Hi after setting txt off projectstatus ****");
			
			
        // Add a deep clone of the row to the DOM
               Node clonedNode = templateRow.cloneNode(true);
                projectTable.appendChild(clonedNode);
		}
               
		}       
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating project catalog: " + ex);
            throw new webschedulePresentationException("Error getting projects listr: ", ex);
	    }
    
    
    
        // Finally remove the template row and template select option 
        //  from the page
	    templateRow.getParentNode().removeChild(templateRow);
//        templateOption.getParentNode().removeChild(templateOption);
      	
	    // write out HTML
	    return page.toDocument();
    }
    
    
    
   
}
	
	
	
	
	
