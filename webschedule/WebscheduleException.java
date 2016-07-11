package webschedule;

import com.lutris.util.ChainedException;

public class WebscheduleException extends ChainedException
{
    /**
     * Public constructor to initialize an exception with a user message
     * and the exception that spawned it
     */
    public WebscheduleException(String msg, Throwable ex)
    {
        super(msg, ex);
    }
}
