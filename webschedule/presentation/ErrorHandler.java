/*-----------------------------------------------------------------------------
 * Copyright (c) 1999
 *      Lutris Technologies, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *      This product includes software developed by Lutris
 *      Technologies, Inc. and its contributors.
 * 4. Neither the name of Lutris Technologies nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY LUTRIS TECHNOLOGIES AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL LUTRIS TECHNOLOGIES OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *-----------------------------------------------------------------------------
 * $Id: ErrorHandler.java,v 1.1 2000/03/06 19:34:33 josh Exp $
 *-----------------------------------------------------------------------------
 */

package webschedule.presentation;

import com.lutris.logging.*;
import com.lutris.appserver.server.httpPresentation.*;
import com.lutris.appserver.server.*;
import java.io.*;
    
/**
 * Class to handle exceptions not caught anywhere else in the framework of
 * our application
 */
public class ErrorHandler implements HttpPresentation
{
    public void run(HttpPresentationComms comms)
        throws HttpPresentationException
    {
        ErrorHTML errorPage = new ErrorHTML();
        
        if(null != comms.exception) {
            StringWriter stringWriter = new StringWriter();
            comms.exception.printStackTrace(new PrintWriter(stringWriter));
	        LogChannel logChannel = Enhydra.getLogChannel();
	        int level = logChannel.getLevel("DEBUG");
            // Use "ERROR" when going into release mode
            // int level = logChannel.getLevel("ERROR");
            logChannel.write(level, "webschedule.presentation.ErrorHandler stack trace = ");
            logChannel.write(level, stringWriter.toString());
            logChannel.write(level, "webschedule.presentation.ErrorHandler caught an exception - "
                                 + comms.exception.toString(), comms.exception);
            errorPage.setTextStackTrace(stringWriter.toString());
            errorPage.setTextErrorMessage(comms.exception.getMessage());
        }
        
        comms.response.writeHTML(errorPage.toDocument());
    }
}
