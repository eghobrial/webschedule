/**--------------------------------------------------------------
* Webschedule
*
* @class: ucsdprojlist
* @version
* @author: Eman Ghobrial
* @since: March 2009
*
*--------------------------------------------------------------*/

package webschedule.presentation.projectMgmt;

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
import java.sql.Timestamp;
import java.util.LinkedList;
import webschedule.UCProjectSort;

/**
 * projectslist.java shows the list of Operators
 */
 
public class ucsdprojlist extends BasePO
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
       String temps = "ucsdprojectsedit.po?event=ucprojectinfo&proj_id="+project_id;
       return temps;
     }

    /**
     *
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
        ucsdprojlistHTML page = new ucsdprojlistHTML();
	String	firstname, lastname , project_id, hreftxt, indexnumber, projname, irbnum;
	String pi_name,lsdst,lsmst,lsyst,lastscanst;
        java.sql.Date iexpdate ;
      
       Project[] projectList = null;
       Person[] PersonList = null;
       LinkedList UCProjects = new LinkedList();
      
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
	
	 
      HTMLElement indexnumCellTemplate = page.getElementIndexnum();
	  HTMLElement irbnumCellTemplate = page.getElementIrbnum();
        
	 HTMLElement iexpdateCellTemplate = page.getElementIexpdate(); 
	    
	pinameCellTemplate.removeAttribute("id");
	 indexnumCellTemplate.removeAttribute("id");
	irbnumCellTemplate.removeAttribute("id");
	iexpdateCellTemplate.removeAttribute("id");
	  
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
	Person currentuser = PersonList[numUser];
	    try {
	         projectList = ProjectFactory.findProjectsForPerson(currentuser);
		 } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Proj catalog: " + ex);
            throw new webschedulePresentationException("Error getting Projs for user: ", ex);
	    }
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	   for(int numps = 0; numps < projectList.length; numps++) {	
                Project theproject = projectList[numps];
        		try {
	      	if (!(theproject.isExp()) && !(theproject.isOutside()) && (theproject.getCodeofpay()!=0) && (theproject.getCodeofpay()!=100))
		    {
		  firstname = theproject.getUserFirstName();
		  lastname = theproject.getUserLastName();
		  projname = theproject.getProj_name();
		  indexnumber = theproject.getIndexnum();
		  irbnum = theproject.getIRBnum();
		 iexpdate = theproject.getIACUCDate();
		 project_id = theproject.getHandle();
		 UCProjects.add(new UCProjectSort(lastname,firstname,projname,project_id,indexnumber,irbnum,iexpdate));
	    		}	
		 } catch(Exception ex) {
	        this.writeDebugMsg("Error populating project catalog: " + ex);
            throw new webschedulePresentationException("Error getting projects listr: ", ex);
	    }	
	}//project list
}   // person list    
	   
  	System.out.println(" **** ?? Projects List size ?? ****"+ Integer.toString(UCProjects.size()));
	    
	UCProjectSort theproject = null;    
             // Get collection of Projs and loop through collection
	      // to add each Proj as a row in the table.
	   for(int c = 0; c < UCProjects.size() ; c++) {	
                Object list_proj = UCProjects.get(c);
		theproject = (UCProjectSort) list_proj;
						
		firstname = theproject.getFirstName();
		lastname = theproject.getLastName();
		projname = theproject.getName();
		indexnumber = theproject.getIndexnum();
		irbnum = theproject.getIrbnum();
		iexpdate = theproject.getIexpdate();
		project_id = theproject.getPid();
		
		System.out.println(" **** Current project ID off projectstatus ****"+ project_id);
						   
		pi_name = lastname+", "+firstname;
		
		      	// set text of new cells to values from string array
	  page.setTextPiname(pi_name);
	
	 page.setTextIndexnum(indexnumber);
	 page.setTextIrbnum(irbnum);	        	
	  page.setTextIexpdate(iexpdate.toString());
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
    
        // Finally remove the template row and template select option 
        //  from the page
	    templateRow.getParentNode().removeChild(templateRow);
//        templateOption.getParentNode().removeChild(templateOption);
      	
	    // write out HTML
	    return page.toDocument();
    }
    
    
    
   
}


