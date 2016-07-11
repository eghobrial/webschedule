/**--------------------------------------------------------------
* Webschedule
*
* @class: unpaidprojects
* @version
* @author: Eman Ghobrial
* @since: Oct 2008
*
*--------------------------------------------------------------*/

package webschedule.presentation.proposalMgmt;

import webschedule.business.proposal.*;
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
import java.sql.Timestamp;

/**
 * projectslist.java shows the list of Operators
 */
 
public class unpaidprojects extends BasePO
{
 //private static String PROPOSAL_ID = "proposalID";
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



    

   public String buildhref (String project_id)
    {
       String temps = "projectinfo.po?event=projectinfo&proj_id="+project_id;
       return temps;
     }

 




    /**
     *
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
        unpaidprojectsHTML page = new unpaidprojectsHTML();
	
	String	firstname, lastname , project_id, hreftxt, describ, projname;
	
	String pi_name,lsdst,lsmst,lsyst,lastscanst;

      
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
	 HTMLElement pinameCellTemplate = page.getElementPiname();
	
	 
      HTMLElement describCellTemplate = page.getElementDescrib();
	
        
	 
	    
	pinameCellTemplate.removeAttribute("id");
	 describCellTemplate.removeAttribute("id");
	
	  
        // Remove the dummy storyboard text from the prototype HTML
        // (e.g., "Van Halen One") from this option
        //templateOption.removeChild(templateOption.getFirstChild());
	
	    try {
	        Project[] projectList = ProjectFactory.getProjectsList();
	        
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	        for(int numps = 0; numps < projectList.length; numps++) {	
                Project theproject = projectList[numps];
		
		
	if ((!(theproject.isExp()) && (theproject.getCodeofpay()==0)) || ((!(theproject.isExp()) && (theproject.getCodeofpay()==100))))
		 {		
		firstname = theproject.getUserFirstName();
		lastname = theproject.getUserLastName();
		projname = theproject.getProj_name();
		describ = theproject. getDescription();
		
		
		   		
		project_id = theproject.getHandle();
		
		System.out.println(" **** Current project ID off projectstatus ****"+ project_id);
					   
		pi_name = firstname+" "+lastname;
		
		      	// set text of new cells to values from string array
	  page.setTextPiname(pi_name);
	
	 page.setTextDescrib(describ);
	        	
	 
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
	t.setData(projname);

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


