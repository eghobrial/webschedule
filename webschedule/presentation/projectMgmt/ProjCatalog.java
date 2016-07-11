/**--------------------------------------------------------------
* webschedule
*
* @class: ProjCatalog
* @version 
* @author   Eman Ghobrial
* @since  Sept 2000
* updated May 2004 added expired project with code -1
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

/**
 * ProjCatalog.java handles the main page functionality of the webschedule app.
 *
 */
public class ProjCatalog extends BasePO
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
	    ProjCatalogHTML page = new ProjCatalogHTML();
        HTMLScriptElement script = new ProjCatalogScriptHTML().getElementRealScript();
        XMLCUtil.replaceNode(script, page.getElementDummyScript());	
	int codeofpay = 0;	
    	
	    try {
	        page.setTextOwner(this.getUser().getFirstname() + " " +
	    		              this.getUser().getLastname() );
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error getting user info: " + ex);
	    }
        
        String errorMsg = this.getSessionData().getAndClearUserMessage();
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	
	    // Generate the person's Proj list and create the HTML template references
	    HTMLTableRowElement templateRow = page.getElementTemplateRow();
        HTMLOptionElement templateOption = page.getElementTemplateOption();
        Node ProjTable = templateRow.getParentNode();
        Node ProjSelect = templateOption.getParentNode();
	    		
	    // Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
	    templateRow.removeAttribute("id");
        templateOption.removeAttribute("id");
        
        // Remove id attributes from the cells in the template row
        HTMLElement projnameCellTemplate = page.getElementProj_name();
	    HTMLElement discribCellTemplate = page.getElementDiscrib();
	    HTMLElement thoursCellTemplate = page.getElementThours();
	    HTMLElement dhoursCellTemplate = page.getElementDhours();
        
	    projnameCellTemplate.removeAttribute("id");
	    discribCellTemplate.removeAttribute("id");
	    thoursCellTemplate.removeAttribute("id");
	    dhoursCellTemplate.removeAttribute("id");
        
        // Remove the dummy storyboard text from the prototype HTML
        // (e.g., "Van Halen One") from this option
        templateOption.removeChild(templateOption.getFirstChild());
	
	    try {
	        Project[] ProjList = ProjectFactory.findProjectsForPerson(this.getUser());
	        
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	        for(int numProjs = 0; numProjs < ProjList.length; numProjs++) {	
                Project currentProj = ProjList[numProjs];
		codeofpay = currentProj.getCodeofpay();
		
		if (!(currentProj.isExp()))
		 {
	        	// set text of new cells to values from string array
	        	page.setTextProj_name(currentProj.getProj_name());
	        	page.setTextDiscrib(currentProj.getDescription());
	        	double totalh = currentProj.getTotalhours();
			double doneh = currentProj.getDonehours();
	        	//double doneh = getActDonehours(currentProj);
	        	page.setTextThours(Double.toString(totalh));
          		page.setTextDhours(Double.toString(doneh));
                
	        // Add a deep clone of the row to the DOM
                Node clonedNode = templateRow.cloneNode(true);
                ProjTable.appendChild(clonedNode);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjTable.insertBefore(clonedNode, templateRow);
                
                // Now populate the select list
                // This algorithm is obscure because options are not normal 
                //  HTML elements
                // First populate the option value (the Proj database ID).
                //  Then append a text child as the option
                // text, which is what is displayed as the text
                // in each row of the select box
                HTMLOptionElement clonedOption = (HTMLOptionElement) templateOption.cloneNode(true);
                clonedOption.setValue(currentProj.getHandle());
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(currentProj.getProj_name() + ": " +
                                        currentProj.getDescription());
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                ProjSelect.appendChild(clonedOption);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjSelect.insertBefore(clonedOption, templateOption);
	        }
		}       
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Proj catalog: " + ex);
            throw new webschedulePresentationException("Error getting Projs for user: ", ex);
	    }
    
    
    
        // Finally remove the template row and template select option 
        //  from the page
	    templateRow.getParentNode().removeChild(templateRow);
        templateOption.getParentNode().removeChild(templateOption);
      	
	    // write out HTML
	    return page.toDocument();
    }

     public String handleSelectproj()
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
    }

}

