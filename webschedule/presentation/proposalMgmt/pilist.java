/**--------------------------------------------------------------
* Webschedule
*
* @class: pilist
* @version
* @author: Eman Ghobrial
* @since: Sept 2008
*
*--------------------------------------------------------------*/

package webschedule.presentation.proposalMgmt;

import webschedule.business.proposal.*;
import webschedule.business.person.*;
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

/**
 * pilist.java shows the list of PIs
 */
 
public class pilist extends BasePO
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



public String buildplisthref (String person_id)
    {
       String temps = "piprojectslist.po?event=showplist&person_id="+person_id;
       return temps;
     }
 
    

   public String buildhref (String person_id)
    {
       String temps = "pinfo.po?event=piinfo&person_id="+person_id;
       return temps;
     }

 




    /**
     *
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
        pilistHTML page = new pilistHTML();
	
	String	firstname, lastname , person_id, hreftxt, hreftxtplist,atitle;
	
	String pi_name;

      
      //  String errorMsg = this.getSessionData().getAndClearUserMessage();
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	
	    // Generate the person's Proj list and create the HTML template references
	    HTMLTableRowElement templateRow = page.getElementTemplateRow();
      
        Node PersonTable = templateRow.getParentNode();
      
	    		
	    // Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
	    templateRow.removeAttribute("id");
   //     templateOption.removeAttribute("id");
        
        // Remove id attributes from the cells in the template row
	 HTMLElement atitleCellTemplate = page.getElementAtitle();
      
       atitleCellTemplate.removeAttribute("id");
	    
        
        // Remove the dummy storyboard text from the prototype HTML
        // (e.g., "Van Halen One") from this option
        //templateOption.removeChild(templateOption.getFirstChild());
	
	    try {
	        Person[] PersonList = PersonFactory.getPersonsList();
	        
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	        for(int numPis = 0; numPis < PersonList.length; numPis++) {	
                Person thePerson = PersonList[numPis];
		
		
	if (!(thePerson.isDeveloper()))
		 {		
		firstname = thePerson.getFirstname();
		lastname = thePerson.getLastname();
		atitle = thePerson.getOffice();
		   		
		person_id = thePerson.getHandle();
		
		System.out.println(" **** Current Person ID off Personstatus ****"+ person_id);
		
		pi_name = firstname+" "+lastname;
		
		      	// set text of new cells to values from string array
	  page.setTextAtitle(atitle);
	        	
	 
			hreftxt = buildhref(person_id);
			
			 hreftxtplist = buildplisthref(person_id);
			

			
			Element link = page.getElementById("Piname");
			System.out.println(" **** Hi after Element off Personstatus ****");
			 HTMLAnchorElement mylink = (HTMLAnchorElement) link;
			 System.out.println(" **** Hi after HTMLElement off Personstatus ****");
	 System.out.println(" **** Current Person  hreftxt off Personstatus ****"+ hreftxt);		 
	mylink.setHref(hreftxt);
	System.out.println(" **** Hi after set href off Personstatus ****");
	Text t = XMLCUtil.getFirstText(link);
	System.out.println(" **** Hi after getting text off Personstatus ****");
	t.setData(pi_name);

	System.out.println(" **** Hi after setting txt off Personstatus ****");
			
			String 	mytext1 = "PI Projects List";	
			Element link1 = page.getElementById("Plist");
			 HTMLAnchorElement mylink1 = (HTMLAnchorElement) link1;
	mylink1.setHref(hreftxtplist);
	Text t1 = XMLCUtil.getFirstText(link1);
	t1.setData(mytext1);
        // Add a deep clone of the row to the DOM
               Node clonedNode = templateRow.cloneNode(true);
                PersonTable.appendChild(clonedNode);
		}
               
		}       
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Person catalog: " + ex);
            throw new webschedulePresentationException("Error getting Persons for user: ", ex);
	    }
    
    
    
        // Finally remove the template row and template select option 
        //  from the page
	    templateRow.getParentNode().removeChild(templateRow);
//        templateOption.getParentNode().removeChild(templateOption);
      	
	    // write out HTML
	    return page.toDocument();
    }
    
    
    
   
}


