package webschedule.business;

import com.lutris.util.ChainedException;

public class webscheduleBusinessException extends ChainedException
{
    /**
     * Public constructor to initialize an exception with a user message
     * and the exception that spawned it
     */
    public webscheduleBusinessException(String msg, Throwable ex)
    {
        super(msg, ex);
    }
    
    /**
     * Public constructor to initialize an exception with a user message
     */
    public webscheduleBusinessException(String msg)
    {
        super(msg);
    }
}
