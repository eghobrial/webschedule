/**--------------------------------------------------------------
* webschedule
*
* @class: Edit
* @version
* @author Eman Ghobrial
* @since  Sept 2000
* @update April 2005 added notifycontact field
*
*--------------------------------------------------------------*/


package webschedule.presentation.projectMgmt;

import webschedule.presentation.BasePO;
import webschedule.presentation.s_eventMgmt.*;
import webschedule.business.person.*;
import webschedule.business.project.*;
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
public class Edit extends BasePO 
{    
    /**
     * Constants representing HTTP parameters passed in from 
     *  the form submission
     */
    private static String PROJ_NAME = "proj_name";
    private static String DISCRIB = "discrib";
    private static String THOURS = "thours";
    private static String DHOURS = "dhours";
    private static String INDEXNUM = "indexnum";
    private static String PASSWORD = "password";
    private static String CODEOFPAY =  "codeofpay";
    private static String PROJ_ID = "projID";
    private static String INVALID_ID = "invalidID";
    private static String EDIT_COMMAND = "edit";
    private static String PERSONID = "personID";
    private static String CONTACTNAME = "contactname";
    private static String CONTACTPHONE = "contactphone";
    private static String BILLADDR1 = "billaddr1";
    private static String BILLADDR2 = "billaddr2";
    private static String BILLADDR3 = "billaddr3";
    private static String CITY = "city";
    private static String STATE = "state";
    private static String ZIP = "zip";
    private static String ACCOUNTID = "accountid";
    private static String OUTSIDE  = "outside";
     private static String EXP  = "exp";
    private static String  EXPDAY = "expday";
    private static String  EXPMONTH = "expmonth";
    private static String  EXPYEAR = "expyear";
    private static String NOTIFYCONTACT = "notifycontact";

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
	    return showEditPage(null);
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
     * Edits an existing proj in  the proj database
     */
    public String handleEdit()
        throws webschedulePresentationException, HttpPresentationException
    {		   
        String projID = this.getComms().request.getParameter(PROJ_ID);
        Project project = null;

        System.out.println(" trying to edit a project "+ projID);
        
        // Try to get the proj by its ID
        try {
	        project = ProjectFactory.findProjectByID(projID);
	         System.out.println(" trying to edit a project 2"+ projID);
	          String title = project.getProj_name();
	          System.out.println("project title: "+title);
	
        } catch(Exception ex) {
            this.getSessionData().setUserMessage("Please choose a valid PROJECT to edit");
            throw new ClientPageRedirectException(PROJECT_ADMIN_PAGE);
        }
        
        // If we got a valid project then try to save it
        // If any fields were missing then redisplay the edit page, 
        //  otherwise redirect to the project catalog page
        try {
            saveProject(project);
            throw new ClientPageRedirectException(PROJECT_ADMIN_PAGE);
        } catch(Exception ex) {
            return showEditPage("You must fill out all fields to edit this project");
        }    
    }
        
    /*
     * Adds a project to the project database
     *
     */
    public String handleAdd() 
        throws HttpPresentationException, webschedulePresentationException
    {       
	    try {
	        Project project = new Project();
            saveProject(project);
            throw new ClientPageRedirectException(PROJECT_ADMIN_PAGE);
	    } catch(Exception ex) {
            return showAddPage("You must fill out all fields to add this project");
        }
    }
    
    /*
     * Deletes a Project from the Project database
     */
    public String handleDelete()
        throws HttpPresentationException, webschedulePresentationException
    {
        String projectID = this.getComms().request.getParameter(PROJ_ID);
        System.out.println(" trying to delete a project "+ projectID);
        
	    try {
	        Project project = ProjectFactory.findProjectByID(projectID);
            String title = project.getProj_name();
            project.delete();
            this.getSessionData().setUserMessage("The project, " + title +
                                                 ", was deleted");
            // Catch any possible database exception as well as the null pointer
            //  exception if the project is not found and is null after findProjectByID
	    } catch(Exception ex) {
            this.getSessionData().setUserMessage(projectID + " Please choose a valid project to delete");
        }
        // Redirect to the catalog page which will display the error message,
        //  if there was one set by the above exception
        throw new ClientPageRedirectException(PROJECT_ADMIN_PAGE);
    }
            
    /** 
     *  Produce HTML for this PO, populated by the project information
     *  that the user wants to edit
     */
    public String showEditPage(String errorMsg) 
        throws HttpPresentationException, webschedulePresentationException
    {        
        String proj_name = this.getComms().request.getParameter(PROJ_NAME);
        String personID = this.getComms().request.getParameter(PERSONID);
        String discrib = this.getComms().request.getParameter(DISCRIB);
        String indexnum = this.getComms().request.getParameter(INDEXNUM);
        String thours = this.getComms().request.getParameter(THOURS);
        String dhours = this.getComms().request.getParameter(DHOURS);
        String projectID = this.getComms().request.getParameter(PROJ_ID);
        String password = this.getComms().request.getParameter(PASSWORD);
        String codeofpay = this.getComms().request.getParameter(CODEOFPAY);
    String contactname = this.getComms().request.getParameter(CONTACTNAME);
    String contactphone = this.getComms().request.getParameter(CONTACTPHONE);
    String billaddr1 = this.getComms().request.getParameter(BILLADDR1);
    String billaddr2 = this.getComms().request.getParameter(BILLADDR2);
    String billaddr3 = this.getComms().request.getParameter(BILLADDR3);
    String city = this.getComms().request.getParameter(CITY);
    String state = this.getComms().request.getParameter(STATE);
    String zip = this.getComms().request.getParameter(ZIP);
    String accountid = this.getComms().request.getParameter(ACCOUNTID);
    String outside = this.getComms().request.getParameter(OUTSIDE);
    String exp = this.getComms().request.getParameter(EXP);
    String expday = this.getComms().request.getParameter(EXPDAY);
    String expmonth = this.getComms().request.getParameter(EXPMONTH);
    String expyear = this.getComms().request.getParameter(EXPYEAR);
    String notifycontact = this.getComms().request.getParameter(NOTIFYCONTACT);
	
	    // Instantiate the page object
	    EditHTML page = new EditHTML();
        Project project = null;
	String  userID = null;

        System.out.println("project Id at show edit page "+projectID);
        try {
            project = ProjectFactory.findProjectByID(projectID);
	    userID = project.getUserID();
            // Catch any possible database exception in findProjectByID()
        } catch(webscheduleBusinessException ex) {
            this.getSessionData().setUserMessage("Please choose a valid project to edit");
            throw new ClientPageRedirectException(PROJECT_ADMIN_PAGE);
        }
        
        try {
            // If we received a valid projectID then try to show the project's contents,
            //  otherwise try to use the HTML input parameters
            page.getElementProjID().setValue(project.getHandle());

            if(null != proj_name && proj_name.length() != 0) {
                page.getElementProj_name().setValue(proj_name);
            } else {
                page.getElementProj_name().setValue(project.getProj_name());
            }

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
			if ((currentPerson.getHandle()).equals(userID))
			  clonedOption.setSelected(true);
			 else 
			    clonedOption.setSelected(false);		
			    
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

          /*  if (personID.equals(INVALID_ID)){
            	page.getElementPersonList().setValue(project.getUserID());
            	
           }else {
           	page.getElementPersonList().setValue(personID);
           }		*/

            if(null != password   && password.length() != 0) {
                page.getElementPassword().setValue(password);
            } else {
                page.getElementPassword().setValue(project.getPassword());
            }

            if(null != discrib   && discrib.length() != 0) {
                page.getElementDiscrib().setValue(discrib);
            } else {
                page.getElementDiscrib().setValue(project.getDescription());
            }
        
            if(null != indexnum && indexnum.length() != 0) {
                page.getElementIndexnum().setValue(indexnum);
            } else {
                page.getElementIndexnum().setValue(project.getIndexnum());
            }

             if(null != thours && thours.length() != 0) {
                page.getElementThours().setValue(thours);
            } else {
                page.getElementThours().setValue(Double.toString(project.getTotalhours()));
            }

              if(null != dhours && dhours.length() != 0) {
                page.getElementDhours().setValue(dhours);
            } else {
                page.getElementDhours().setValue(Double.toString(project.getDonehours()));
            }
              if(null != codeofpay && codeofpay.length() != 0) {
                page.getElementCodeofpay().setValue(codeofpay);
            } else {
                page.getElementCodeofpay().setValue(Integer.toString(project.getCodeofpay()));
            }

        
	
	      if(null != contactname && contactname.length() != 0) {
                page.getElementContactname().setValue(contactname);
            } else {
                page.getElementContactname().setValue(project.getContactname());
            }

	      if(null != billaddr1 && billaddr1.length() != 0) {
                page.getElementBilladdr1().setValue(billaddr1);
            } else {
                page.getElementBilladdr1().setValue(project.getBilladdr1());
            }


	      if(null != billaddr2 && billaddr2.length() != 0) {
                page.getElementBilladdr2().setValue(billaddr2);
            } else {
                page.getElementBilladdr2().setValue(project.getBilladdr2());
            }

	
	 if(null != billaddr3 && billaddr3.length() != 0) {
                page.getElementBilladdr3().setValue(billaddr3);
            } else {
                page.getElementBilladdr3().setValue(project.getBilladdr3());
            }


 if(null != city && city.length() != 0) {
                page.getElementCity().setValue(city);
            } else {
                page.getElementCity().setValue(project.getCity());
            }

	 if(null != state && state.length() != 0) {
                page.getElementState().setValue(state);
            } else {
                page.getElementState().setValue(project.getState());
            }

 if(null != zip && zip.length() != 0) {
                page.getElementZip().setValue(zip);
            } else {
                page.getElementZip().setValue(project.getZip());
            }

if(null != accountid && accountid.length() != 0) {
                page.getElementAccountid().setValue(accountid);
            } else {
                page.getElementAccountid().setValue(project.getAccountid());
            }

	
	if(null != this.getComms().request.getParameter(OUTSIDE)) {
            page.getElementOutsideBox().setChecked(true);
        } else {
            page.getElementOutsideBox().setChecked(project.isOutside());
        }
	
	
	if(null != this.getComms().request.getParameter(EXP)) {
            page.getElementExpBox().setChecked(true);
        } else {
            page.getElementExpBox().setChecked(project.isExp());
        }

  if(null != expday && expday.length() != 0) {
                page.getElementExpday().setValue(expday);
            } else {
                page.getElementExpday().setValue(Integer.toString(project.getExpday()));
            }
 


if(null != expmonth && expmonth.length() != 0) {
                page.getElementExpmonth().setValue(expmonth);
            } else {
                page.getElementExpmonth().setValue(Integer.toString(project.getExpmonth()));
            }
 
if(null != expyear && expyear.length() != 0) {
                page.getElementExpyear().setValue(expyear);
            } else {
                page.getElementExpyear().setValue(Integer.toString(project.getExpyear())); 	 
}


	      if(null != notifycontact && notifycontact.length() != 0) {
                page.getElementNotifycontact().setValue(notifycontact);
            } else {
                page.getElementNotifycontact().setValue(project.getNotifycontact());
            }



  if(null == errorMsg) {

            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
	    } else {
                page.setTextErrorText(errorMsg);
            }
        } catch(webscheduleBusinessException ex) {
            throw new webschedulePresentationException("Error populating page for project editing", ex);
        }
        
        page.getElementEventValue().setValue(EDIT_COMMAND);
	    return page.toDocument();
    }
    
    /** 
     *  Produce HTML for this PO
     */
    public String showAddPage(String errorMsg) 
        throws HttpPresentationException, webschedulePresentationException
    {        

        String proj_name = this.getComms().request.getParameter(PROJ_NAME);
        String password = this.getComms().request.getParameter(PASSWORD);
        String discrib = this.getComms().request.getParameter(DISCRIB);
        String indexnum = this.getComms().request.getParameter(INDEXNUM);
        String thours = this.getComms().request.getParameter(THOURS);
        String dhours = this.getComms().request.getParameter(DHOURS);
        String projectID = this.getComms().request.getParameter(PROJ_ID);
        String codeofpay = this.getComms().request.getParameter(CODEOFPAY);
	String contactname = this.getComms().request.getParameter(CONTACTNAME);
	String contactphone = this.getComms().request.getParameter(CONTACTPHONE);
	String billaddr1 = this.getComms().request.getParameter(BILLADDR1);
	String billaddr2 = this.getComms().request.getParameter(BILLADDR2);
	String billaddr3 = this.getComms().request.getParameter(BILLADDR3);
	String city = this.getComms().request.getParameter(CITY);
	String state = this.getComms().request.getParameter(STATE);
	String zip = this.getComms().request.getParameter(ZIP);
	String accountid = this.getComms().request.getParameter(ACCOUNTID);
	String isoutside = this.getComms().request.getParameter(OUTSIDE);
	String exp = this.getComms().request.getParameter(EXP);
	String expday = this.getComms().request.getParameter(EXPDAY);
	String expmonth = this.getComms().request.getParameter(EXPMONTH);
	String expyear = this.getComms().request.getParameter(EXPYEAR);
	String notifycontact = this.getComms().request.getParameter(NOTIFYCONTACT);


	    // Instantiate the page object
	    EditHTML page = new EditHTML();

	HTMLOptionElement templateOption = page.getElementTemplateOption();
        Node PersonSelect = templateOption.getParentNode();
        templateOption.removeAttribute("id");
        templateOption.removeChild(templateOption.getFirstChild());

        if(null != this.getComms().request.getParameter(PROJ_NAME)) {
            page.getElementProj_name().setValue(this.getComms().request.getParameter(PROJ_NAME));
        }

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

        if(null != this.getComms().request.getParameter(PASSWORD)) {
            page.getElementPassword().setValue(this.getComms().request.getParameter(PASSWORD));
        }

        if(null != this.getComms().request.getParameter(DISCRIB)) {
            page.getElementDiscrib().setValue(this.getComms().request.getParameter(DISCRIB));
        }
        if(null != this.getComms().request.getParameter(INDEXNUM)) {
            page.getElementIndexnum().setValue(this.getComms().request.getParameter(INDEXNUM));
        }

        if(null != this.getComms().request.getParameter(THOURS)) {
            page.getElementThours().setValue(this.getComms().request.getParameter(THOURS));
        }

        if(null != this.getComms().request.getParameter(DHOURS)) {
            page.getElementDhours().setValue(this.getComms().request.getParameter(DHOURS));
        }

        if(null != this.getComms().request.getParameter(CODEOFPAY)) {
            page.getElementCodeofpay().setValue(this.getComms().request.getParameter(CODEOFPAY));
        }

  if(null != this.getComms().request.getParameter(CONTACTNAME)) {
            page.getElementContactname().setValue(this.getComms().request.getParameter(CONTACTNAME));
        }

 if(null != this.getComms().request.getParameter(CONTACTPHONE)) {
           
page.getElementContactphone().setValue(this.getComms().request.getParameter(CONTACTPHONE));
        }


if(null != this.getComms().request.getParameter(BILLADDR1)) {
           
page.getElementBilladdr1().setValue(this.getComms().request.getParameter(BILLADDR1));
        }



if(null != this.getComms().request.getParameter(BILLADDR2)) {
           
page.getElementBilladdr2().setValue(this.getComms().request.getParameter(BILLADDR2));
        }


if(null != this.getComms().request.getParameter(BILLADDR3)) {
           
page.getElementBilladdr3().setValue(this.getComms().request.getParameter(BILLADDR3));
        }

if(null != this.getComms().request.getParameter(STATE)) {
           
page.getElementState().setValue(this.getComms().request.getParameter(STATE));
        }

if(null != this.getComms().request.getParameter(ZIP)) {
           
page.getElementZip().setValue(this.getComms().request.getParameter(ZIP));
        }

 if(null != this.getComms().request.getParameter(OUTSIDE)) {
            page.getElementOutsideBox().setChecked(true);
        } else {
            page.getElementOutsideBox().setChecked(false);
        }
	
if(null != this.getComms().request.getParameter(EXP)) {
            page.getElementExpBox().setChecked(true);
        } else {
            page.getElementExpBox().setChecked(false);
        }	
	
if(null != this.getComms().request.getParameter(ACCOUNTID)) {
           
page.getElementAccountid().setValue(this.getComms().request.getParameter(ACCOUNTID));
        }
	
	
if(null != this.getComms().request.getParameter(EXPDAY)) {
           
page.getElementExpday().setValue(this.getComms().request.getParameter(EXPDAY));
        }


if(null != this.getComms().request.getParameter(EXPMONTH)) {
           
page.getElementExpmonth().setValue(this.getComms().request.getParameter(EXPMONTH));
        }
if(null != this.getComms().request.getParameter(EXPYEAR)) {
           
page.getElementExpyear().setValue(this.getComms().request.getParameter(EXPYEAR));
        }

 if(null != this.getComms().request.getParameter(NOTIFYCONTACT)) {
            page.getElementNotifycontact().setValue(this.getComms().request.getParameter(NOTIFYCONTACT));
        }

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
    protected void saveProject(Project theProject)
        throws HttpPresentationException, webschedulePresentationException
    {        

        String proj_name = this.getComms().request.getParameter(PROJ_NAME);
        String personID = this.getComms().request.getParameter(PERSONID);
        String password = this.getComms().request.getParameter(PASSWORD);
        String discrib = this.getComms().request.getParameter(DISCRIB);
        String indexnum = this.getComms().request.getParameter(INDEXNUM);
        String thours = this.getComms().request.getParameter(THOURS);
        String dhours = this.getComms().request.getParameter(DHOURS);
        String codeofpay = this.getComms().request.getParameter(CODEOFPAY);

String contactname = this.getComms().request.getParameter(CONTACTNAME);
	String contactphone = this.getComms().request.getParameter(CONTACTPHONE);
	String billaddr1 = this.getComms().request.getParameter(BILLADDR1);
	String billaddr2 = this.getComms().request.getParameter(BILLADDR2);
	String billaddr3 = this.getComms().request.getParameter(BILLADDR3);
	String city = this.getComms().request.getParameter(CITY);
	String state = this.getComms().request.getParameter(STATE);
	String zip = this.getComms().request.getParameter(ZIP);
	String accountid = this.getComms().request.getParameter(ACCOUNTID);
	String isoutside = this.getComms().request.getParameter(OUTSIDE);
	String exp = this.getComms().request.getParameter(EXP);
	String expday = this.getComms().request.getParameter(EXPDAY);

String expmonth = this.getComms().request.getParameter(EXPMONTH);
String expyear = this.getComms().request.getParameter(EXPYEAR);

String notifycontact = this.getComms().request.getParameter(NOTIFYCONTACT);

        try {
	System.out.println(" trying to saving a project one "+ proj_name);
            theProject.setProj_name(proj_name);
System.out.println(" trying to saving a project one "+ proj_name);
            theProject.setPassword(password);
	    theProject.setDescription(discrib);
	    theProject.setIndexnum(indexnum);
	    theProject.setTotalhours(Double.parseDouble(thours));
	    theProject.setDonehours(Double.parseDouble(dhours));
            theProject.setCodeofpay(Integer.parseInt(codeofpay));
	
	    theProject.setContactname(contactname);
	    theProject.setContactphone(contactphone);
	    theProject.setBilladdr1(billaddr1);
            theProject.setBilladdr2(billaddr2);

theProject.setBilladdr3(billaddr3);
theProject.setCity(city);
theProject.setState(state);
theProject.setZip(zip);
theProject.setAccountid(accountid);
theProject.setNotifycontact(notifycontact);

	            if(null != this.getComms().request.getParameter(OUTSIDE)) {
                	theProject.setOutside(true);
            	    } else {
                	theProject.setOutside(false);
            	     }
 if(null != this.getComms().request.getParameter(EXP)) {
                	theProject.setExp(true);
            	    } else {
                	theProject.setExp(false);
            	     }
theProject.setExpday(Integer.parseInt(expday));
theProject.setExpmonth(Integer.parseInt(expmonth));
theProject.setExpyear(Integer.parseInt(expyear));

	        theProject.setOwner(PersonFactory.findPersonByID(personID));


theProject.setInstitute("UCSD");
theProject.setFpemail(" ");
theProject.setPOnum("0");
theProject.setPOexpdate(java.sql.Date.valueOf("2010-09-31"));
theProject.setPOhours(0);
theProject.setIACUCDate(java.sql.Date.valueOf("2010-01-01"));
theProject.setModifiedby("Ghobrial");
theProject.setModDate(java.sql.Date.valueOf("2010-04-30"));
theProject.setNotes("*");
theProject.setIRBnum("0");

//theProject.set();

System.out.println(" Person ID "+ personID);
System.out.println(" contactname "+ contactname);
System.out.println(" contact phone"+ contactphone);
System.out.println(" isoutside "+ isoutside);
System.out.println(" exp day "+expday);
System.out.println(" exp month "+ expmonth);
System.out.println(" expyear "+ expyear);





System.out.println(" trying to saving a project two "+ proj_name);
	        theProject.save();	
System.out.println(" trying to saving a project three "+ proj_name);
	    } catch(Exception ex) {
            throw new webschedulePresentationException("Error adding project", ex);
        }    
    }
}
