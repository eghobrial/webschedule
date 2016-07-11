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
 * /home/emang/myapps/webschedule/webschedule/data/ProjectDataStruct.java
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
public class ProjectDataStruct implements Cloneable, Serializable {

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
   public String proj_name = null;

/**
 * 
 */
   public String Description = null;

/**
 * 
 */
   public String indexnum = null;

/**
 * 
 */
   public int codeofpay = 0;

/**
 * 
 */
   public String password = null;

/**
 * 
 */
   public double totalhours = 0;

/**
 * 
 */
   public double donehours = 0;

/**
 * 
 */
   public webschedule.data.PersonDO owner = null;

/**
 * 
 */
   public String contactname = null;

/**
 * 
 */
   public String contactphone = null;

/**
 * 
 */
   public String billaddr1 = null;

/**
 * 
 */
   public String billaddr2 = null;

/**
 * 
 */
   public String billaddr3 = null;

/**
 * 
 */
   public String city = null;

/**
 * 
 */
   public String state = null;

/**
 * 
 */
   public String zip = null;

/**
 * 
 */
   public String accountid = null;

/**
 * 
 */
   public boolean isoutside = false;

/**
 * 
 */
   public int expday = 0;

/**
 * 
 */
   public int expmonth = 0;

/**
 * 
 */
   public int expyear = 0;

/**
 * 
 */
   public String notifycontact = null;

/**
 * 
 */
   public boolean isExp = false;

/**
 * 
 */
   public int canCredit = 0;

/**
 * 
 */
   public String Institute = null;

/**
 * 
 */
   public String proposalID = null;

/**
 * 
 */
   public String fpemail = null;

/**
 * 
 */
   public String POnum = null;

/**
 * 
 */
   public java.sql.Date POexpdate = null;

/**
 * 
 */
   public double POhours = 0;

/**
 * 
 */
   public java.sql.Date IACUCDate = null;

/**
 * 
 */
   public String Modifiedby = null;

/**
 * 
 */
   public java.sql.Date ModDate = null;

/**
 * 
 */
   public String Notes = null;

/**
 * 
 */
   public String IRBnum = null;
    /**
     * Create a copy of the guts of a DO.
     *
     * @exception DatabaseManagerException 
     *       if createExisting() fails for a contained DO
     * @exception ObjectIdException 
     *       if GenericDO has trouble obtaining a valid id.
     */
    public ProjectDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        ProjectDataStruct ret = new ProjectDataStruct ();
 

		
	ret.proj_name = proj_name + "";
	
	
	ret.Description = Description + "";
	
	
	ret.indexnum = indexnum + "";
	
	
	ret.codeofpay = codeofpay;
	
	
	ret.password = password + "";
	
	
	ret.totalhours = totalhours;
	
	
	ret.donehours = donehours;
	
	
	ret.owner = webschedule.data.PersonDO.createCopy( owner );
	
	
	ret.contactname = contactname + "";
	
	
	ret.contactphone = contactphone + "";
	
	
	ret.billaddr1 = billaddr1 + "";
	
	
	ret.billaddr2 = billaddr2 + "";
	
	
	ret.billaddr3 = billaddr3 + "";
	
	
	ret.city = city + "";
	
	
	ret.state = state + "";
	
	
	ret.zip = zip + "";
	
	
	ret.accountid = accountid + "";
	
	
	ret.isoutside = isoutside;
	
	
	ret.expday = expday;
	
	
	ret.expmonth = expmonth;
	
	
	ret.expyear = expyear;
	
	
	ret.notifycontact = notifycontact + "";
	
	
	ret.isExp = isExp;
	
	
	ret.canCredit = canCredit;
	
	
	ret.Institute = Institute + "";
	
	
	ret.proposalID = proposalID + "";
	
	
	ret.fpemail = fpemail + "";
	
	
	ret.POnum = POnum + "";
	
	
	ret.POexpdate = new java.sql.Date(POexpdate.getTime() );
	
	
	ret.POhours = POhours;
	
	
	ret.IACUCDate = new java.sql.Date(IACUCDate.getTime() );
	
	
	ret.Modifiedby = Modifiedby + "";
	
	
	ret.ModDate = new java.sql.Date(ModDate.getTime() );
	
	
	ret.Notes = Notes + "";
	
	
	ret.IRBnum = IRBnum + "";
	


	return ret;
    }
}
