package webschedule.presentation;

import java.util.Date;
import com.lutris.xml.xmlc.*;
import com.lutris.appserver.server.httpPresentation.*;

public class Welcome implements HttpPresentation {


    public void run(HttpPresentationComms comms) 
        throws HttpPresentationException {

        String now = new Date().toString();
        WelcomeHTML welcome = (WelcomeHTML)comms.xmlcFactory.create(WelcomeHTML.class);
        welcome.setTextTime(now);
        comms.response.writeHTML(welcome);
    }

}
