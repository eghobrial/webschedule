/**--------------------------------------------------------------
* Webschedule
*
* @class: Downtime
* @version
* @author: Eman Ghobrial
* @since: May 2001
*
*--------------------------------------------------------------*/

package webschedule.presentation.s_eventMgmt;

import webschedule.business.downtime.*;
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
import webschedule.SendMailLL;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.lang.String;

/**
 *Downtime.java shows the downtime form
 *
 */
public class Downtime extends BasePO
{
   /*
   * Constants
   */
    private static String STARTH = "starth";
    private static String  STARTM = "startm";
    private static String ENDH = "endh";
    private static String  ENDM = "endm";
     private static String DAY ="day";
    private static String MONTH ="month";
    private static String YEAR ="year";
    private static String  DESCRIPTION ="description";


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

    /**
     *
     */
    public String showPage(String errorMsg)
    throws HttpPresentationException, webschedulePresentationException
    {
    	String temptext = null;
    	int temphour;
       DowntimeHTML page = new DowntimeHTML();

    	
       String starth = this.getComms().request.getParameter(STARTH);
       String startm = this.getComms().request.getParameter(STARTM);
       String endh = this.getComms().request.getParameter(ENDH);
       String endm = this.getComms().request.getParameter(ENDM);
       String month = this.getComms().request.getParameter(MONTH);
       String year = this.getComms().request.getParameter(YEAR);
        	
       HTMLOptionElement starthtemplateOption = page.getElementStarthtemplateOption();
       HTMLOptionElement startmtemplateOption = page.getElementStartmtemplateOption();
       HTMLOptionElement endhtemplateOption = page.getElementEndhtemplateOption();
       HTMLOptionElement endmtemplateOption = page.getElementEndmtemplateOption();

      Node starthSelect = starthtemplateOption.getParentNode();
      Node startmSelect = startmtemplateOption.getParentNode();
      Node endhSelect = endhtemplateOption.getParentNode();
      Node endmSelect = endmtemplateOption.getParentNode();

      starthtemplateOption.removeAttribute("id");
      startmtemplateOption.removeAttribute("id");
      endhtemplateOption.removeAttribute("id");
      endmtemplateOption.removeAttribute("id");

      starthtemplateOption.removeChild(starthtemplateOption.getFirstChild());
      startmtemplateOption.removeChild(startmtemplateOption.getFirstChild());
      endhtemplateOption.removeChild(endhtemplateOption.getFirstChild());
      endmtemplateOption.removeChild(endmtemplateOption.getFirstChild());
	

      for (int hour = 0; hour < 25; hour ++)
      {
      	if (hour < 12) temptext = Integer.toString(hour)+"am";
      	else if (hour == 12) temptext = Integer.toString(hour)+"Noon";
      	else if (hour == 24) {
      		temphour = hour-12;
      		temptext = Integer.toString(temphour)+"Mid.";
      		}
      	else {
      		temphour = hour -12;
      		temptext = Integer.toString(temphour)+"pm";
      	     }		

      HTMLOptionElement clonedOption = (HTMLOptionElement) starthtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(hour));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(temptext);
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                starthSelect.appendChild(clonedOption);
         }

      for (int min = 0; min < 60; min+=30)
      {

      HTMLOptionElement clonedOption = (HTMLOptionElement) startmtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(min));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(Integer.toString(min));
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                startmSelect.appendChild(clonedOption);
         }


         for (int min = 0; min < 60; min+=30)
      {

      HTMLOptionElement clonedOption = (HTMLOptionElement) endmtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(min));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(Integer.toString(min));
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                endmSelect.appendChild(clonedOption);
         }

     for (int hour = 0; hour < 25; hour ++)
      {
      if (hour < 12) temptext = Integer.toString(hour)+"am";
      	else if (hour == 12) temptext = Integer.toString(hour)+"Noon";
      	else if (hour == 24) {
      		temphour = hour-12;
      		temptext = Integer.toString(temphour)+"Mid.";
      		}
      	else {
      		temphour = hour -12;
      		temptext = Integer.toString(temphour)+"pm";
      	     }		

      HTMLOptionElement clonedOption = (HTMLOptionElement) endhtemplateOption.cloneNode(true);
                clonedOption.setValue(Integer.toString(hour));
                Node optionTextNode = clonedOption.getOwnerDocument().
                        createTextNode(temptext);
                clonedOption.appendChild(optionTextNode);
                // Do only a shallow copy of the option as we don't want the text child
                // of the node option
                endhSelect.appendChild(clonedOption);
         }


        starthtemplateOption.getParentNode().removeChild(starthtemplateOption);
        startmtemplateOption.getParentNode().removeChild(startmtemplateOption);
        endhtemplateOption.getParentNode().removeChild(endhtemplateOption);
        endmtemplateOption.getParentNode().removeChild(endmtemplateOption);


    	
        //First priority: try to use the passed-in error message
        // Second priority: try to use the session data's error message
        // If there are no error messages then clear the prototype error text
        if(null != errorMsg ||
           null != (errorMsg = this.getSessionData().getAndClearUserMessage())) {
            page.setTextErrorText(errorMsg);
        } else {
            page.getElementErrorText().getParentNode().removeChild(page.getElementErrorText());
        }

	    return page.toDocument();
    }

   public String handleSavedowntime()
        throws HttpPresentationException
	    {
          	
	  Downtimec  downtimec = null;
    	
	try  {
	downtimec = new Downtimec();
	saveDowntime(downtimec);
	  } catch(Exception ex) {
          	throw new webschedulePresentationException("Error getting persons list", ex);
    		 }
    	throw new ClientPageRedirectException(DOWNTIME_PAGE);	
	}
	
protected void saveDowntime (Downtimec theDowntime)
   throws HttpPresentationException, webschedulePresentationException
      {	
        String starth = this.getComms().request.getParameter(STARTH);
   	String startm = this.getComms().request.getParameter(STARTM);
    	String endh = this.getComms().request.getParameter(ENDH);
    	String endm = this.getComms().request.getParameter(ENDM);
	String day = this.getComms().request.getParameter(DAY);    	
        String month = this.getComms().request.getParameter(MONTH);
    	String year = this.getComms().request.getParameter(YEAR);
    	String description = this.getComms().request.getParameter(DESCRIPTION);
    	System.out.println("Month off Down time module "+month);
   try
     { 	
	theDowntime.setStarth(Integer.parseInt(starth));
        theDowntime.setStartm(Integer.parseInt(startm));
    	theDowntime.setEndh(Integer.parseInt(endh));
    	theDowntime.setEndm(Integer.parseInt(endm));
    	theDowntime.setDay(Integer.parseInt(day));
    	theDowntime.setMonth(Integer.parseInt(month));
    	theDowntime.setYear(Integer.parseInt(year));    	
    	theDowntime.save();
    	    		
    	 } catch(Exception ex) {
          throw new webschedulePresentationException("Error adding a down time information", ex);
     }

}
}