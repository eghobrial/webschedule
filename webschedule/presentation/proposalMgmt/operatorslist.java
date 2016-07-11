/**--------------------------------------------------------------
* Webschedule
*
* @class: operatorslist
* @version
* @author: Eman Ghobrial
* @since: Oct 2008
*
*--------------------------------------------------------------*/

package webschedule.presentation.proposalMgmt;

import webschedule.business.proposal.*;
import webschedule.business.operator.*;
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
 * operatorslist.java shows the list of Operators
 */
 
public class operatorslist extends BasePO
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



    

   public String buildhref (String operator_id)
    {
       String temps = "operatorinfo.po?event=operatorinfo&operator_id="+operator_id;
       return temps;
     }

 




    /**
     *
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
        operatorslistHTML page = new operatorslistHTML();
	
	String	firstname, lastname , operator_id, hreftxt, email;
	int lastscanday, lastscanmonth, lastscanyear;
	Timestamp sftts;
	String operator_name,lsdst,lsmst,lsyst,lastscanst;

      
      //  String errorMsg = this.getSessionData().getAndClearUserMessage();
        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
	
	    // Generate the operator's Proj list and create the HTML template references
	    HTMLTableRowElement templateRow = page.getElementTemplateRow();
      
        Node operatorTable = templateRow.getParentNode();
      
	    		
	    // Remove ids to prevent duplicates 
        //  (browsers don't care, but the DOM does)
	    templateRow.removeAttribute("id");
   //     templateOption.removeAttribute("id");
        
        // Remove id attributes from the cells in the template row
	 HTMLElement opemailCellTemplate = page.getElementOpemail();
	
	 
      HTMLElement sdateCellTemplate = page.getElementScandate();
	
        
	    
        HTMLElement sfttestCellTemplate = page.getElementSfttest();
	 
	 
	    
	  opemailCellTemplate.removeAttribute("id");
	  sdateCellTemplate.removeAttribute("id");
	   sfttestCellTemplate.removeAttribute("id");
	  
        // Remove the dummy storyboard text from the prototype HTML
        // (e.g., "Van Halen One") from this option
        //templateOption.removeChild(templateOption.getFirstChild());
	
	    try {
	        Operator[] operatorList = OperatorFactory.getOperatorsList();
	        
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	        for(int numOps = 0; numOps < operatorList.length; numOps++) {	
                Operator theoperator = operatorList[numOps];
		
		
	if (!(theoperator.isExp()))
		 {		
		firstname = theoperator.getFirstName();
		lastname = theoperator.getLastName();
		email = theoperator.getEmail();
		lastscanday = theoperator.getlastscanday();
		lastscanmonth = theoperator.getlastscanmonth();
		lastscanyear = theoperator.getlastscanyear();
		sftts = theoperator.getSFTTS();
		
		   		
		operator_id = theoperator.getHandle();
		
		System.out.println(" **** Current operator ID off operatorstatus ****"+ operator_id);
		
		if (lastscanday < 10) 
		  lsdst = "0"+Integer.toString(lastscanday);
	        else 
		
		   lsdst = Integer.toString(lastscanday);
		
		if (lastscanmonth < 10) 
		  lsmst = "0"+Integer.toString(lastscanmonth);
	        else 
		   lsmst = Integer.toString(lastscanmonth);
		  
		   lsyst = Integer.toString( lastscanyear);
		   
		operator_name = firstname+" "+lastname;
		lastscanst = lsyst+"-"+lsmst+"-"+lsdst;
		
		      	// set text of new cells to values from string array
	  page.setTextOpemail(email);
	  page.setTextSfttest(sftts.toString());
	 page.setTextScandate(lastscanst);
	        	
	 
			hreftxt = buildhref(operator_id);
			
			
			

			
			Element link = page.getElementById("Opname");
			System.out.println(" **** Hi after Element off operatorstatus ****");
			 HTMLAnchorElement mylink = (HTMLAnchorElement) link;
			 System.out.println(" **** Hi after HTMLElement off operatorstatus ****");
	 System.out.println(" **** Current operator  hreftxt off operatorstatus ****"+ hreftxt);		 
	mylink.setHref(hreftxt);
	System.out.println(" **** Hi after set href off operatorstatus ****");
	Text t = XMLCUtil.getFirstText(link);
	System.out.println(" **** Hi after getting text off operatorstatus ****");
	t.setData(operator_name);

	System.out.println(" **** Hi after setting txt off operatorstatus ****");
			
			
        // Add a deep clone of the row to the DOM
               Node clonedNode = templateRow.cloneNode(true);
                operatorTable.appendChild(clonedNode);
		}
               
		}       
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating operator catalog: " + ex);
            throw new webschedulePresentationException("Error getting operators listr: ", ex);
	    }
    
    
    
        // Finally remove the template row and template select option 
        //  from the page
	    templateRow.getParentNode().removeChild(templateRow);
//        templateOption.getParentNode().removeChild(templateOption);
      	
	    // write out HTML
	    return page.toDocument();
    }
    
    
    
   
}


