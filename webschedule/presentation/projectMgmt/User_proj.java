/**--------------------------------------------------------------
* webschedule
*
* @class: User_proj
* @version 
* @author   Eman Ghobrial
* @since  March 2005
*
*--------------------------------------------------------------*/

package webschedule.presentation.projectMgmt;

import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.presentation.BasePO;
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
import webschedule.DOMTableRow;


/**
 * ProjCatalog.java handles the main page functionality of the webschedule app.
 *
 */
public class User_proj extends BasePO
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
        // Swap in our real run-time JavaScript to repalce the storyboard JavaScript
	    User_projHTML page = new User_projHTML();
	Calendar eventinfo = Calendar.getInstance();
       
	int currentyear = eventinfo.get(eventinfo.YEAR);
	
	int codeofpay = 0;	
	
	Project[] ProjList = null;
	Person[]  PersonList = null;
	    	
	 
        String errorMsg = this.getSessionData().getAndClearUserMessage();
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	
	
	//Generate the users' list of projects
	HTMLElement templateRowhl = page.getElementTemplateRowhl();
        HTMLElement templateRow = page.getElementTemplateRow();
	 
            		
	// Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
//	    templateRowhl.removeAttribute("id");
	// Remove id attributes from the cells in the template row
        HTMLElement ownerCellTemplate = page.getElementOwner();
	HTMLElement owneremailCellTemplate = page.getElementOwneremail();
	

/*        ownerCellTemplate.removeAttribute("id");
	owneremailCellTemplate.removeAttribute("id");*/


        HTMLElement projnameCellTemplate = page.getElementProj_name();
        HTMLElement discribCellTemplate = page.getElementDiscrib();
	HTMLElement thoursCellTemplate = page.getElementThours();
	HTMLElement dhoursCellTemplate = page.getElementDhours();
        HTMLElement expCellTemplate = page.getElementExp();
	
	/*projnameCellTemplate.removeAttribute("id");
	discribCellTemplate.removeAttribute("id");
	thoursCellTemplate.removeAttribute("id");
	dhoursCellTemplate.removeAttribute("id");
       	expCellTemplate.removeAttribute("id");*/
	    
        // Remove the dummy storyboard text from the prototype HTML
        // (e.g., "Van Halen One") from this option
        //templateOption.removeChild(templateOption.getFirstChild());
	
	try {
	    PersonList = PersonFactory.getPersonsList();
	    
	  } catch(Exception ex) {
	        this.writeDebugMsg("Error getting person list " + ex);

	    }  
	    
    for (int numUser=0; numUser < PersonList.length;numUser++)
	{
	
	DOMTableRow row = new DOMTableRow (templateRowhl, templateRow);
	
	Person currentuser = PersonList[numUser];
	double totalh = 0.0;
	double doneh = 0.0;
	double doneha = 0.0;
	int projyear = 0;
	String lastname=null, firstname=null, email=null;
	 
	
	    try {
	        ProjList = ProjectFactory.findProjectsForPerson(currentuser);
	        
		 } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Proj catalog: " + ex);
            throw new webschedulePresentationException("Error getting Projs for user: ", ex);
	    }
	int	numofProjs = ProjList.length;
	//if (numofProjs > 0)
	//{
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	   for(int numProjs = 0; numProjs < ProjList.length; numProjs++) {	
                Project currentProj = ProjList[numProjs];
		       
	       try {
		        codeofpay = currentProj.getCodeofpay();
			firstname = currentProj.getUserFirstName();
			lastname = currentProj.getUserLastName();
			email = currentProj.getUserEmail();
			page.setTextOwner(firstname+" "+lastname);
			page.setTextOwneremail(email);
			// set text of new cells to values from string array
	        	page.setTextProj_name(currentProj.getProj_name());
			System.out.println("Project name "+currentProj.getProj_name());
	        	page.setTextDiscrib(currentProj.getDescription());
	        	totalh = currentProj.getTotalhours();
	        	doneh = currentProj.getDonehours();
			doneha = getActDonehours(currentProj);
			if (doneh != doneha )
			   updateproject(currentProj,doneha);
			page.setTextThours(Double.toString(totalh));
          		page.setTextDhours(Double.toString(doneh));
			page.setTextADhours(Double.toString(doneha));
			projyear = currentProj.getExpyear();
			System.out.println("Project total hours "+Double.toString(totalh));
			System.out.println("Project done hours "+Double.toString(doneh));
			
			if (projyear < currentyear)
			page.setTextExp("Yes");
			else
			page.setTextExp("No");
			
			} catch(Exception ex) {
	        this.writeDebugMsg("Error populating Proj info: " + ex);
            throw new webschedulePresentationException("Error populating Proj info: ", ex);
	    }
	
	/*try {
	page.setTextOwner(currentuser.getFirstname()+" "+ currentuser.getLastname());
	page.setTextOwneremail(currentuser.getEmail());
	System.out.println("Owner Email "+currentuser.getEmail());
	
	} catch(Exception ex) {
	        this.writeDebugMsg("Error getting person information: " + ex);
            throw new webschedulePresentationException("Error getting info for user: ", ex);
	    }*/	        	
			
                row.addCell((HTMLElement)templateRow.cloneNode(true));

   
		}       
		
	//	}   	
	   
    
     } //for PersonList
    templateRowhl.getParentNode().removeChild(templateRowhl);
	    // write out HTML
	    return page.toDocument();
    }



/**
     * Method to save a new or existing project to the database
     */
    protected void updateproject(Project theProject, double doneha)
        throws HttpPresentationException, webschedulePresentationException
    {        

      String proj_name;
      String personID;
        try {
	proj_name = theProject.getProj_name();
	personID = theProject.getUserID();
	System.out.println(" trying to saving a project one "+ proj_name);
            theProject.setProj_name(theProject.getProj_name());
System.out.println(" trying to saving a project one "+ proj_name);
            theProject.setPassword(theProject.getPassword());
	    theProject.setDescription(theProject.getDescription());
	    theProject.setIndexnum(theProject.getIndexnum());
	    theProject.setTotalhours(theProject.getTotalhours());
	    theProject.setDonehours(doneha);
            theProject.setCodeofpay(theProject.getCodeofpay());
	
	    theProject.setContactname(theProject.getContactname());
	    theProject.setContactphone(theProject.getContactphone());
	    theProject.setBilladdr1(theProject.getBilladdr1());
            theProject.setBilladdr2(theProject.getBilladdr2());

theProject.setBilladdr3(theProject.getBilladdr3());
theProject.setCity(theProject.getCity());
theProject.setState(theProject.getState());
theProject.setZip(theProject.getZip());
theProject.setAccountid(theProject.getAccountid());
theProject.setNotifycontact(theProject.getNotifycontact());
theProject.setOutside( theProject.isOutside());
theProject.setExp(theProject.isExp());
            	   
theProject.setExpday(theProject.getExpday());
theProject.setExpmonth(theProject.getExpmonth());
theProject.setExpyear(theProject.getExpyear());

theProject.setOwner(PersonFactory.findPersonByID(personID));



System.out.println(" Person ID "+ personID);




System.out.println(" trying to saving a project two "+ proj_name);
	        theProject.save();	
System.out.println(" trying to saving a project three "+ proj_name);
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding project", ex);
        }    
    }

   /*  public String handleSelectproj()
        throws HttpPresentationException
    {

        String proj_id = this.getComms().request.getParameter(PROJ_ID);
	Project project = null;

      try {
            project = ProjectFactory.findProjectByID(proj_id);
            if((null == project) || (proj_id == "invalidID")){
            	this.getSessionData().setUserMessage("Please choose a valid project ");
                 throw new ClientPageRedirectException(PROJECT_CATALOG_PAGE);
                 // Show error message that project not found
            } else {
            	this.setProject(project);
            	throw new ClientPageRedirectException(PROJ_PASSWD_CAT_PAGE);
		
            }
        } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding PROJECT: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding PROJECT", ex);
        }
    }*/

}

