package webschedule.presentation;

import com.lutris.appserver.server.httpPresentation.HttpPresentationException;
import com.lutris.util.ChainedException;

public class webschedulePresentationException extends HttpPresentationException
{
    /**
     * Public constructor to initialize an exception with a user message
     * and an exception chain to follow
     */
    public webschedulePresentationException(String msg, Exception ex)
    {
        super(msg, ex);   
    }
}
