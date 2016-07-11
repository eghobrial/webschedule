/**--------------------------------------------------------------
* webschedule
*
* @class: Enrolloperator
* @version
* @author Eman Ghobrial
* @since  Sept 2006
* @update 
*
*--------------------------------------------------------------*/


package webschedule.presentation.personMgmt;

import webschedule.presentation.BasePO;
import webschedule.presentation.s_eventMgmt.*;
import webschedule.business.person.*;
import webschedule.business.project.*;
import webschedule.business.operator.*;
import webschedule.business.operates.*;
import webschedule.presentation.webschedulePresentationException;
import webschedule.business.webscheduleBusinessException;
import com.lutris.appserver.server.httpPresentation.*;
import com.lutris.appserver.server.session.*;
import com.lutris.util.*;
import com.lutris.xml.xmlc.*;
import com.lutris.xml.xmlc.html.*;
import org.w3c.dom.*;
import org.w3c.dom.html.*;


/**
 * projCatalog handles the proj management of the webschedule app
 * *
 */
public class Enrolloperator extends BasePO 
{    
    /**
     * Constants representing HTTP parameters passed in from 
     *  the form submission
     */
   
    private static String INVALID_ID = "invalidID";
    private static String EDIT_COMMAND = "edit";
    private static String PERSONID = "personID";
    private static String OPERATORID = "operatorID";

    /**
     * Superclass method override
     */
    public boolean loggedInUserRequired()
    {
        return true;
    }

    /** 
     *  Default event. Just show the page populated with any proj
     *  parameters that were passed along.
     */
   public String handleDefault() 
        throws HttpPresentationException 
    {
	    return showAddPage(null);
    }
    
    /** 
     *  Default event. Just show the page.
     */
    public String handleShowAddPage() 
        throws HttpPresentationException 
    {
	    return showAddPage(null);
    }



   
        
    /*
     * Adds a project to the project database
     *
     */
    public String handleAdd() 
        throws HttpPresentationException, webschedulePresentationException
    {       
	    try {
	        
            saveOperates();
            throw new ClientPageRedirectException(ENROLLOPERATOR_PAGE);
	    } catch(Exception ex) {
            return showAddPage("You must fill out all fields to add this project");
        }
    }
    
    
            
   
    
    /** 
     *  Produce HTML for this PO
     */
    public String showAddPage(String errorMsg) 
        throws HttpPresentationException, webschedulePresentationException
    {        

   

	    // Instantiate the page object
	    EnrolloperatorHTML page = new EnrolloperatorHTML();

	HTMLOptionElement templateOption = page.getElementTemplateOption();
        Node PersonSelect = templateOption.getParentNode();
        templateOption.removeAttribute("id");
        templateOption.removeChild(templateOption.getFirstChild());

   
        try {
        	Person[] PersonList = PersonFactory.getPersonsList();
        	for (int numPersons = 0; numPersons < PersonList.length;  numPersons++) {
        	    Person currentPerson = PersonList[numPersons] ;
        	    HTMLOptionElement clonedOption = (HTMLOptionElement) templateOption.cloneNode(true);
                    clonedOption.setValue(currentPerson.getHandle());
                    Node optionTextNode = clonedOption.getOwnerDocument().
                          createTextNode(currentPerson.getFirstname() + " " +
                                        currentPerson.getLastname());
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                PersonSelect.appendChild(clonedOption);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjSelect.insertBefore(clonedOption, templateOption);
	        }
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Persons List: " + ex);
            throw new webschedulePresentationException("Error getting Persons List: ", ex);
	    }
	
            templateOption.getParentNode().removeChild(templateOption);

HTMLOptionElement templateOption1 = page.getElementTemplateOption1();
        Node OperatorSelect = templateOption1.getParentNode();
        templateOption1.removeAttribute("id");
        templateOption1.removeChild(templateOption1.getFirstChild());

   
        try {
        	Operator[] OperatorList = OperatorFactory.getOperatorsList();
        	for (int numOperators = 0; numOperators < OperatorList.length;  numOperators++) {
        	    Operator currentOperator = OperatorList[numOperators] ;
        	    HTMLOptionElement clonedOption1 = (HTMLOptionElement) templateOption1.cloneNode(true);
                    clonedOption1.setValue(currentOperator.getHandle());
                    Node optionTextNode = clonedOption1.getOwnerDocument().
                          createTextNode(currentOperator.getFirstName() + " " +
                                        currentOperator.getLastName());
                clonedOption1.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                OperatorSelect.appendChild(clonedOption1);
                // Alternative way to insert nodes below
                // insertBefore(newNode, oldNode);
                // ProjSelect.insertBefore(clonedOption1, templateOption1);
	        }
	    } catch(Exception ex) {
	        this.writeDebugMsg("Error populating Operators List: " + ex);
            throw new webschedulePresentationException("Error getting Operators List: ", ex);
	    }
	
            templateOption1.getParentNode().removeChild(templateOption1);





        if(null == errorMsg) {       
	        page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        } else {
            page.setTextErrorText(errorMsg);
        }
        
	    return page.toDocument();
    }





    /**
     * Method to save a new or existing project to the database
     */
    protected void saveOperates()
        throws HttpPresentationException, webschedulePresentationException
    {        

       
        String personID = this.getComms().request.getParameter(PERSONID);
        String operatorID = this.getComms().request.getParameter(OPERATORID);
	Operator operator = null;
	
	try {
            operator = OperatorFactory.findOperatorByID(operatorID);
	    }catch(Exception ex) {
	 this.writeDebugMsg("Error getting operator: " + ex);
            throw new webschedulePresentationException("Error getting operator ", ex);
	    }
	
	try {
	
	 Project[] ProjList =
	 ProjectFactory.findProjectsForPerson(PersonFactory.findPersonByID(personID));
	 	        
            // Get collection of Projs and loop through collection
	        // to add each Proj as a row in the table.
	        for(int numProjs = 0; numProjs < ProjList.length; numProjs++) {	
                Project currentProj = ProjList[numProjs];
		Operates theOperates = new Operates();
		theOperates.setOperator(operator);
		theOperates.setProject(currentProj);
		theOperates.save();
		}
	}catch(Exception ex) {
	 this.writeDebugMsg("Error populating Proj catalog: " + ex);
            throw new webschedulePresentationException("Error getting Projs for user: ", ex);
	    }
	
	

      
    }
}
