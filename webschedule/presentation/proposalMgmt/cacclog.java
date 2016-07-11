/**--------------------------------------------------------------
* Webschedule
*
* @class: cacclog
* @version
* @author: Eman Ghobrial
* @since: Jan 2008
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
 * proposallog.java shows the Proposal Logs with Options to approve and route and create accounts.
 *
 */
public class cacclog extends BasePO
{
  private static String PROPOSAL_ID = "proposalID";
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



 
     public String buildhref (String proj_id)
    {
    String temps;
    String bookmark = returnProposalType(proj_id);
    System.out.println(" **** Hi after set href off accounts proposal my book is****"+bookmark);
    if ((bookmark.equals("humanns"))||(bookmark.equals("humanbi")))
       temps = "quickview.po?event=quickview&proj_id="+proj_id;
     else temps  = "quickviewa.po?event=quickview&proj_id="+proj_id;
       return temps;
     }
    
 
 
  
  

  public String buildacchref (String proj_id)
    {
       String temps = "addaccounts.po?event=showaddpage&proj_id="+proj_id;
       return temps;
     }




    /**
     *
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
        cacclogHTML page = new cacclogHTML();
	int today ,month ,year;
	String	aproj_name , proj_id, comments,hreftxt,hreftxtc,hreftxtchk,hreftxtacc;
	int	status ;
	String cdate;
String statuscode[] = {"Editable","Submitted","Internal Comments","Committe Comments","PI Responded","Approved","Checked","Accounts Added"};
       //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
   /*    	 try {
	        page.setTextOwner(this.getUser().getFirstname() + " " +
	    		              this.getUser().getLastname() );
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error getting user info: " + ex);
	    }
        */
      //  String errorMsg = this.getSessionData().getAndClearUserMessage();
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	
	String lastname;
	Person thePerson = this.getUser();
	int notauth = 0;
	
	try
    	{    	
	
	lastname = thePerson.getLastname();
    	
	
    	} catch  (Exception ex) {
            throw new webschedulePresentationException("Error getting user's email", ex);
        }
	
	if (lastname.equals("Ghobrial"))
	notauth = 1;
		
	if (notauth==0){
	
	//if (lastname != "Ghobrial") {
            this.getSessionData().setUserMessage( "  You are not auth to add accounts ");
                 throw new ClientPageRedirectException(PROPOSALADMINMENU_PAGE);
                 // Show error message that project not found
	} else{
	
	    // Generate the person's Proj list and create the HTML template references
	    HTMLTableRowElement templateRow = page.getElementTemplateRow();
       // HTMLOptionElement templateOption = page.getElementTemplateOption();
        Node ProjTable = templateRow.getParentNode();
        //Node ProjSelect = templateOption.getParentNode();
	    		
	    // Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
	    templateRow.removeAttribute("id");
   //     templateOption.removeAttribute("id");
        
        // Remove id attributes from the cells in the template row
	 HTMLElement projidCellTemplate = page.getElementProj_id();
       // HTMLElement aprojnameCellTemplate = page.getElementAproj_name();
	    HTMLElement datesubCellTemplate = page.getElementDatesub();
	    HTMLElement statusCellTemplate = page.getElementStatus();
	 //   HTMLElement commentsCellTemplate = page.getElementComments();
        
		 projidCellTemplate.removeAttribute("id");	
	   // aprojnameCellTemplate.removeAttribute("id");
	    datesubCellTemplate.removeAttribute("id");
	    statusCellTemplate.removeAttribute("id");
	     //commentsCellTemplate.removeAttribute("id");
	    
        
        // Remove the dummy storyboard text from the prototype HTML
        // (e.g., "Van Halen One") from this option
        //templateOption.removeChild(templateOption.getFirstChild());
	
	    try {
	        Proposal[] ProposalList = ProposalFactory.getProposalsList();
	        
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	        for(int numProjs = 0; numProjs < ProposalList.length; numProjs++) {	
                Proposal theProposal = ProposalList[numProjs];
			
		today = theProposal.getToday();
   		month = theProposal.getMonth();
		year = theProposal.getYear();
		 System.out.println(" **** Current proposal year off proposalstatus ****"+ year);
		aproj_name = theProposal.getBproj_name();
		proj_id = theProposal.getHandle();
		 System.out.println(" **** Current proposal ID off proposalstatus ****"+ proj_id);
		status = theProposal.getStatus();
		comments = theProposal.getComments();
		cdate = Integer.toString(year)+"-"+Integer.toString(month+1)+"-"+Integer.toString(today);
		      	// set text of new cells to values from string array
	  
	if (status == 6)
	{  
	  page.setTextProj_id(proj_id);
	        	//page.setTextAproj_name(aproj_name);
			page.setTextDatesub(cdate);
	        	page.setTextStatus(statuscode[status]);
	
			hreftxt = buildhref(proj_id);
			
System.out.println(" **** Current proposal  aproj_name off proposalstatus ****"+ aproj_name);
			
			Element link = page.getElementById("Testid");
			System.out.println(" **** Hi after Element off proposalstatus ****");
			 HTMLAnchorElement mylink = (HTMLAnchorElement) link;
			 System.out.println(" **** Hi after HTMLElement off proposalstatus ****");
	 System.out.println(" **** Current proposal  hreftxt off proposalstatus ****"+ hreftxt);		 
	mylink.setHref(hreftxt);
	System.out.println(" **** Hi after set href off proposalstatus ****");
	Text t = XMLCUtil.getFirstText(link);
	System.out.println(" **** Hi after getting text off proposalstatus ****");
	t.setData(aproj_name);

	System.out.println(" **** Hi after setting txt off proposalstatus ****");
			
		/*	if (comments==null)
			{
			comments = "No Comments";
			
			 hreftxtc = buildcomhref(proj_id);
			 }
			else {
			comments = "Click to view";
			hreftxtc = buildcomhref(proj_id);
			}*/
			
	// System.out.println(" **** Current proposal  comments off proposalstatus ****"+ comments);		
			
			
		String accounts = "Add accounts";
		
		
		
		hreftxtacc = buildacchref(proj_id);
		
		
	
	
			Element link3 = page.getElementById("Account");
			 HTMLAnchorElement mylink3 = (HTMLAnchorElement) link3;
	mylink3.setHref(hreftxtacc);
	Text t3 = XMLCUtil.getFirstText(link3);
	t3.setData(accounts);
	
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
        /*        HTMLOptionElement clonedOption = (HTMLOptionElement) templateOption.cloneNode(true);
                clonedOption.setValue(theProposal.getHandle());
		System.out.println("Current proposal ID " + theProposal.getHandle());
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(cdate + ": " +
                                        aproj_name);
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                ProjSelect.appendChild(clonedOption);*/
		
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjSelect.insertBefore(clonedOption, templateOption);
	}        
		}       
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Proposal catalog: " + ex);
            throw new webschedulePresentationException("Error getting Proposals for user: ", ex);
	    }
    
    
    
        // Finally remove the template row and template select option 
        //  from the page
	    templateRow.getParentNode().removeChild(templateRow);
//        templateOption.getParentNode().removeChild(templateOption);
     } 	
	    // write out HTML
	    return page.toDocument();
    }
    
    
    
   
}


