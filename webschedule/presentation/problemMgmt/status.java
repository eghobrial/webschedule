/**--------------------------------------------------------------
* Webschedule
*
* @class:status
* @version
* @author: Eman Ghobrial
* @since: May 2004
*
*--------------------------------------------------------------*/

package webschedule.presentation.problemMgmt;

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

/**
 * status.java shows the status page
 *
 */
public class status extends BasePO
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
       String temps = "problemdetails.po?event=showdetails&Prob_id="+prob_id;
       return temps;
     }

    


   public String handleshowdetails()
        throws HttpPresentationException
    {
    	String prob_id = this.getComms().request.getParameter(PROBLEM_ID);
	System.out.println("problem id  =   "+prob_id);
            if (null == prob_id) {
            this.getSessionData().setUserMessage(prob_id + "  Please choose a valid report ");
                 throw new ClientPageRedirectException(STATUS_PAGE);
                 // Show error message that project not found
            } else {
	    this.setProb_id(prob_id);
            	
	  throw new ClientPageRedirectException(PROBLEMDETAILS_PAGE);
		
           }
      }

    /**
     stat
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
        statusHTML page = new statusHTML();
	String statuscode[] = {"closed","open","in progress"};
	  Problem[] ProbList;
	

    //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }
	
	
	  // Generate the person's Proj list and create the HTML template references
	    HTMLTableRowElement templateRow = page.getElementTemplateRow();
       
        Node ProbTable = templateRow.getParentNode();
      
	    		
	    // Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
	    templateRow.removeAttribute("id");
       
        
        // Remove id attributes from the cells in the template row
        HTMLElement postdateCellTemplate = page.getElementPostdate();
	HTMLElement posttimeCellTemplate = page.getElementPosttime();

	/*    HTMLElement describCellTemplate = page.getElementDescrip();*/
	    HTMLElement statuscodeCellTemplate = page.getElementStatuscode();
	   
        
	    postdateCellTemplate.removeAttribute("id");
	posttimeCellTemplate.removeAttribute("id");
	 /*   describCellTemplate.removeAttribute("id");*/
	    statuscodeCellTemplate.removeAttribute("id");
	  
	    int postday=0;
	    int postmonth=0;
	    int postyear=0;
	    int posth=0;
	    int postm=0;
	    String postpm = null;
	    int  scode=0;
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
            throw new webschedulePresentationException("System error finding PROBLEM List", ex);
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
			
			 } catch(webscheduleBusinessException ex) {
            this.writeDebugMsg("System error finding PROJECT: " + ex.getMessage());
            throw new webschedulePresentationException("System error finding PROJECT", ex);
        }
	        	
postd=Integer.toString(postmonth)+"-"+Integer.toString(postday)+"-"+Integer.toString(postyear);
if (postm<10)
  postms = "0"+Integer.toString(postm);
else
   postms = Integer.toString(postm);
    
postt=Integer.toString(posth)+":"+postms+" "+postpm;



//System.out.println("Post Date"+postd);

herftxt=buildhref (problemID);

System.out.println("herf Text"+herftxt);

Element link = page.getElementById("Descrip");

        HTMLAnchorElement mylink = (HTMLAnchorElement) link;
	mylink.setHref(herftxt);
	Text t = XMLCUtil.getFirstText(link);
	t.setData(describ);

page.setTextPostdate(postd);
page.setTextPosttime(postt);
	        	/*page.setTextDescrip(currentProb.getDescrib());*/
	        
page.setTextStatuscode(statuscode[scode]);
                
	        // Add a deep clone of the row to the DOM
                Node clonedNode = templateRow.cloneNode(true);
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
	    
       
        // Finally remove the template row and template select option 
        //  from the page
	    templateRow.getParentNode().removeChild(templateRow);

	

	    return page.toDocument();
    
}

}
