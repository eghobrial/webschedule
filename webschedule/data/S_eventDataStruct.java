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
 * /home/emang/myapps/webschedule/webschedule/data/S_eventDataStruct.java
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
public class S_eventDataStruct implements Cloneable, Serializable {

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
   public String description = null;

/**
 * 
 */
   public int starth = 0;

/**
 * 
 */
   public int startm = 0;

/**
 * 
 */
   public int endh = 0;

/**
 * 
 */
   public int endm = 0;

/**
 * 
 */
   public int eventday = 0;

/**
 * 
 */
   public int eventmonth = 0;

/**
 * 
 */
   public int eventyear = 0;

/**
 * 
 */
   public int eventdayofw = 0;

/**
 * 
 */
   public webschedule.data.PersonDO owner = null;

/**
 * 
 */
   public webschedule.data.ProjectDO proj_owner = null;

/**
 * 
 */
   public int todayday = 0;

/**
 * 
 */
   public int todaymonth = 0;

/**
 * 
 */
   public int todayyear = 0;

/**
 * 
 */
   public boolean isDevelop = false;

/**
 * 
 */
   public boolean isRepeat = false;

/**
 * 
 */
   public webschedule.data.OperatorDO Operator = null;

/**
 * 
 */
   public webschedule.data.PersonDO prevowner = null;

/**
 * 
 */
   public webschedule.data.ProjectDO prevproj_owner = null;

/**
 * 
 */
   public boolean IsGrabbable = false;

/**
 * 
 */
   public String reasondropped = null;

/**
 * 
 */
   public int todayh = 0;

/**
 * 
 */
   public int todaym = 0;
    /**
     * Create a copy of the guts of a DO.
     *
     * @exception DatabaseManagerException 
     *       if createExisting() fails for a contained DO
     * @exception ObjectIdException 
     *       if GenericDO has trouble obtaining a valid id.
     */
    public S_eventDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        S_eventDataStruct ret = new S_eventDataStruct ();
 

		
	ret.description = description + "";
	
	
	ret.starth = starth;
	
	
	ret.startm = startm;
	
	
	ret.endh = endh;
	
	
	ret.endm = endm;
	
	
	ret.eventday = eventday;
	
	
	ret.eventmonth = eventmonth;
	
	
	ret.eventyear = eventyear;
	
	
	ret.eventdayofw = eventdayofw;
	
	
	ret.owner = webschedule.data.PersonDO.createCopy( owner );
	
	
	ret.proj_owner = webschedule.data.ProjectDO.createCopy( proj_owner );
	
	
	ret.todayday = todayday;
	
	
	ret.todaymonth = todaymonth;
	
	
	ret.todayyear = todayyear;
	
	
	ret.isDevelop = isDevelop;
	
	
	ret.isRepeat = isRepeat;
	
	
	ret.Operator = webschedule.data.OperatorDO.createCopy( Operator );
	
	
	ret.prevowner = webschedule.data.PersonDO.createCopy( prevowner );
	
	
	ret.prevproj_owner = webschedule.data.ProjectDO.createCopy( prevproj_owner );
	
	
	ret.IsGrabbable = IsGrabbable;
	
	
	ret.reasondropped = reasondropped + "";
	
	
	ret.todayh = todayh;
	
	
	ret.todaym = todaym;
	


	return ret;
    }
}
