/*-----------------------------------------------------------------------------
 * Enhydra Java Application Server
 * Copyright 1997-2000 Lutris Technologies, Inc.
 * All rights reserved.
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
 *      This product includes Enhydra software developed by Lutris
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
 * /home/emang/myapps/webschedule/webschedule/data/ProblemDataStruct.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import com.lutris.appserver.server.sql.*;
import java.sql.*;
import java.math.*;
import java.io.Serializable;

/**
 * Data structure for DO class.
 * A container for data members of a DO class.
 * A DO class contains a reference to a DataStruct class.  This reference
 * can be null (a DO whose data has not yet been retrieved from the database),
 * allowing a DO object to be a lightweight placeholder until its data is needed.
 *
 * @version $Revision: 1.3 $
 * @author  emang
 * @since   webschedule
 */
public class ProblemDataStruct implements Cloneable, Serializable {

    /**
     * A DO refers to this DataStruct.
     * readOnly is set to true when the DO is stored in its class cache.
     */
    public boolean readOnly = false;

    private byte[] copyByteArray( byte[] source ) {
	byte[] dest = new byte[ source.length ];
	System.arraycopy( source, 0, dest, 0, source.length );
	return dest;
    }

/**
 * 
 */
   public webschedule.data.PersonDO owner = null;

/**
 * 
 */
   public int severitycode = 0;

/**
 * 
 */
   public int classcode = 0;

/**
 * 
 */
   public int prioritycode = 0;

/**
 * 
 */
   public int statuscode = 0;

/**
 * 
 */
   public int postday = 0;

/**
 * 
 */
   public int postmonth = 0;

/**
 * 
 */
   public int postyear = 0;

/**
 * 
 */
   public int updateday = 0;

/**
 * 
 */
   public int updatemonth = 0;

/**
 * 
 */
   public int updateyear = 0;

/**
 * 
 */
   public int closeday = 0;

/**
 * 
 */
   public int closemonth = 0;

/**
 * 
 */
   public int closeyear = 0;

/**
 * 
 */
   public String reportername = null;

/**
 * 
 */
   public String reporteremail = null;

/**
 * 
 */
   public String describ = null;

/**
 * 
 */
   public boolean GE_called = false;

/**
 * 
 */
   public String problemnum = null;

/**
 * 
 */
   public int posth = 0;

/**
 * 
 */
   public int postm = 0;

/**
 * 
 */
   public String postpm = null;

/**
 * 
 */
   public String problemdetail = null;

/**
 * 
 */
   public String fixdetail = null;
    /**
     * Create a copy of the guts of a DO.
     *
     * @exception DatabaseManagerException 
     *       if createExisting() fails for a contained DO
     * @exception ObjectIdException 
     *       if GenericDO has trouble obtaining a valid id.
     */
    public ProblemDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        ProblemDataStruct ret = new ProblemDataStruct ();
 

		
	ret.owner = webschedule.data.PersonDO.createCopy( owner );
	
	
	ret.severitycode = severitycode;
	
	
	ret.classcode = classcode;
	
	
	ret.prioritycode = prioritycode;
	
	
	ret.statuscode = statuscode;
	
	
	ret.postday = postday;
	
	
	ret.postmonth = postmonth;
	
	
	ret.postyear = postyear;
	
	
	ret.updateday = updateday;
	
	
	ret.updatemonth = updatemonth;
	
	
	ret.updateyear = updateyear;
	
	
	ret.closeday = closeday;
	
	
	ret.closemonth = closemonth;
	
	
	ret.closeyear = closeyear;
	
	
	ret.reportername = reportername + "";
	
	
	ret.reporteremail = reporteremail + "";
	
	
	ret.describ = describ + "";
	
	
	ret.GE_called = GE_called;
	
	
	ret.problemnum = problemnum + "";
	
	
	ret.posth = posth;
	
	
	ret.postm = postm;
	
	
	ret.postpm = postpm + "";
	
	
	ret.problemdetail = problemdetail + "";
	
	
	ret.fixdetail = fixdetail + "";
	


	return ret;
    }
}
