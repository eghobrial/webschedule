/**--------------------------------------------------------------
* Webschedule
*
* @class: modlog
* @version
* @author: Eman Ghobrial
* @since: May 2009
*
*--------------------------------------------------------------*/
package webschedule.presentation.projectMgmt;


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

import java.io.*;


/**
 * selectdate_help.java handles the help page display
 *
 */
public class modlog extends BasePO
{

	
    /**
     * Superclass method override
     */
    public boolean loggedInUserRequired()
    {
        return false;
    }

    /**
     *  Default event. Just show the page.
     */
    public String handleDefault()
        throws HttpPresentationException
    {
	    return showPage(null);
    }



    public String handleThrowException()
        throws Exception
    {
        throw new Exception("This is a test exception thrown from Login.java handleThrowException()");
    }



    /**
     *
     */
    public String showPage(String errorMsg)
    {
        modlogHTML page = new modlogHTML();

	String mymodlog = null;
	
try {

	FileInputStream inStream = new FileInputStream ("/home/emang/webschlog/projmodlog.txt");
	int inBytes = inStream.available();
	System.out.println("inStream has " + inBytes + " available bytes");
	byte inBuf[] = new byte[inBytes];
	int bytesRead = inStream.read(inBuf, 0, inBytes);
	System.out.println(bytesRead + " bytes were read");
	mymodlog = new String(inBuf);
	System.out.println("They are: " + mymodlog);
	//System.out.println("They are: " + new String(inBuf));
	inStream.close(); 

} catch (IOException ioe) {
	System.out.println("IO Exception");
	}
	
	page.setTextMlog(mymodlog); 

	    return page.toDocument();
    }
}


