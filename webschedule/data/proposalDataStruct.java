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
 * /home/emang/myapps/webschedule/webschedule/data/proposalDataStruct.java
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
public class proposalDataStruct implements Cloneable, Serializable {

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
   public int today = 0;

/**
 * 
 */
   public int month = 0;

/**
 * 
 */
   public int year = 0;

/**
 * 
 */
   public boolean Isucsd = false;

/**
 * 
 */
   public boolean Isanimal = false;

/**
 * 
 */
   public boolean Issample = false;

/**
 * 
 */
   public String proj_name = null;

/**
 * 
 */
   public String cname = null;

/**
 * 
 */
   public String cphone = null;

/**
 * 
 */
   public String cemail = null;

/**
 * 
 */
   public boolean CntrOp = false;

/**
 * 
 */
   public String op1lastname = null;

/**
 * 
 */
   public String op1firstname = null;

/**
 * 
 */
   public String op1phone = null;

/**
 * 
 */
   public String op1email = null;

/**
 * 
 */
   public String op2lastname = null;

/**
 * 
 */
   public String op2firstname = null;

/**
 * 
 */
   public String op2phone = null;

/**
 * 
 */
   public String op2email = null;

/**
 * 
 */
   public String indexnum = null;

/**
 * 
 */
   public String baline1 = null;

/**
 * 
 */
   public String baline2 = null;

/**
 * 
 */
   public String baline3 = null;

/**
 * 
 */
   public String bacity = null;

/**
 * 
 */
   public String bast = null;

/**
 * 
 */
   public String bazip = null;

/**
 * 
 */
   public String fpname = null;

/**
 * 
 */
   public String fpphone = null;

/**
 * 
 */
   public String fpemail = null;

/**
 * 
 */
   public int thours = 0;

/**
 * 
 */
   public String writeup = null;

/**
 * 
 */
   public String dataanalysis = null;

/**
 * 
 */
   public boolean IACUCFaxed = false;

/**
 * 
 */
   public boolean RFCoils = false;

/**
 * 
 */
   public boolean restraints = false;

/**
 * 
 */
   public boolean physioeq = false;

/**
 * 
 */
   public boolean anesthetics = false;

/**
 * 
 */
   public boolean ancillary = false;

/**
 * 
 */
   public boolean op1status = false;

/**
 * 
 */
   public boolean op2status = false;

/**
 * 
 */
   public boolean IACUC = false;

/**
 * 
 */
   public int status = 0;

/**
 * 
 */
   public String BioHazard = null;

/**
 * 
 */
   public boolean stimuli = false;

/**
 * 
 */
   public boolean AnimalTrans = false;

/**
 * 
 */
   public String bproj_name = null;

/**
 * 
 */
   public boolean contrast = false;

/**
 * 
 */
   public java.sql.Date AnimTransDate = null;

/**
 * 
 */
   public java.sql.Date IACUCDate = null;

/**
 * 
 */
   public java.sql.Date stdate = null;

/**
 * 
 */
   public java.sql.Date expdate = null;

/**
 * 
 */
   public String comments = null;

/**
 * 
 */
   public boolean nighttime = false;

/**
 * 
 */
   public String copis = null;

/**
 * 
 */
   public String intcomments = null;

/**
 * 
 */
   public String response = null;

/**
 * 
 */
   public String animalq1 = null;

/**
 * 
 */
   public String animalq2 = null;

/**
 * 
 */
   public String animalq3 = null;

/**
 * 
 */
   public String animalq4 = null;

/**
 * 
 */
   public String proposalq1 = null;

/**
 * 
 */
   public String proposalq2 = null;

/**
 * 
 */
   public String proposalq3 = null;

/**
 * 
 */
   public String proposalq4 = null;

/**
 * 
 */
   public String proposalq5 = null;

/**
 * 
 */
   public String proposalq6 = null;

/**
 * 
 */
   public String proposalq7 = null;

/**
 * 
 */
   public String proposalq8 = null;

/**
 * 
 */
   public String bookmark = null;

/**
 * 
 */
   public java.sql.Date approvalDate = null;

/**
 * 
 */
   public String POnum = null;

/**
 * 
 */
   public String institute = null;

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
    public proposalDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        proposalDataStruct ret = new proposalDataStruct ();
 

		
	ret.owner = webschedule.data.PersonDO.createCopy( owner );
	
	
	ret.today = today;
	
	
	ret.month = month;
	
	
	ret.year = year;
	
	
	ret.Isucsd = Isucsd;
	
	
	ret.Isanimal = Isanimal;
	
	
	ret.Issample = Issample;
	
	
	ret.proj_name = proj_name + "";
	
	
	ret.cname = cname + "";
	
	
	ret.cphone = cphone + "";
	
	
	ret.cemail = cemail + "";
	
	
	ret.CntrOp = CntrOp;
	
	
	ret.op1lastname = op1lastname + "";
	
	
	ret.op1firstname = op1firstname + "";
	
	
	ret.op1phone = op1phone + "";
	
	
	ret.op1email = op1email + "";
	
	
	ret.op2lastname = op2lastname + "";
	
	
	ret.op2firstname = op2firstname + "";
	
	
	ret.op2phone = op2phone + "";
	
	
	ret.op2email = op2email + "";
	
	
	ret.indexnum = indexnum + "";
	
	
	ret.baline1 = baline1 + "";
	
	
	ret.baline2 = baline2 + "";
	
	
	ret.baline3 = baline3 + "";
	
	
	ret.bacity = bacity + "";
	
	
	ret.bast = bast + "";
	
	
	ret.bazip = bazip + "";
	
	
	ret.fpname = fpname + "";
	
	
	ret.fpphone = fpphone + "";
	
	
	ret.fpemail = fpemail + "";
	
	
	ret.thours = thours;
	
	
	ret.writeup = writeup + "";
	
	
	ret.dataanalysis = dataanalysis + "";
	
	
	ret.IACUCFaxed = IACUCFaxed;
	
	
	ret.RFCoils = RFCoils;
	
	
	ret.restraints = restraints;
	
	
	ret.physioeq = physioeq;
	
	
	ret.anesthetics = anesthetics;
	
	
	ret.ancillary = ancillary;
	
	
	ret.op1status = op1status;
	
	
	ret.op2status = op2status;
	
	
	ret.IACUC = IACUC;
	
	
	ret.status = status;
	
	
	ret.BioHazard = BioHazard + "";
	
	
	ret.stimuli = stimuli;
	
	
	ret.AnimalTrans = AnimalTrans;
	
	
	ret.bproj_name = bproj_name + "";
	
	
	ret.contrast = contrast;
	
	
	ret.AnimTransDate = new java.sql.Date(AnimTransDate.getTime() );
	
	
	ret.IACUCDate = new java.sql.Date(IACUCDate.getTime() );
	
	
	ret.stdate = new java.sql.Date(stdate.getTime() );
	
	
	ret.expdate = new java.sql.Date(expdate.getTime() );
	
	
	ret.comments = comments + "";
	
	
	ret.nighttime = nighttime;
	
	
	ret.copis = copis + "";
	
	
	ret.intcomments = intcomments + "";
	
	
	ret.response = response + "";
	
	
	ret.animalq1 = animalq1 + "";
	
	
	ret.animalq2 = animalq2 + "";
	
	
	ret.animalq3 = animalq3 + "";
	
	
	ret.animalq4 = animalq4 + "";
	
	
	ret.proposalq1 = proposalq1 + "";
	
	
	ret.proposalq2 = proposalq2 + "";
	
	
	ret.proposalq3 = proposalq3 + "";
	
	
	ret.proposalq4 = proposalq4 + "";
	
	
	ret.proposalq5 = proposalq5 + "";
	
	
	ret.proposalq6 = proposalq6 + "";
	
	
	ret.proposalq7 = proposalq7 + "";
	
	
	ret.proposalq8 = proposalq8 + "";
	
	
	ret.bookmark = bookmark + "";
	
	
	ret.approvalDate = new java.sql.Date(approvalDate.getTime() );
	
	
	ret.POnum = POnum + "";
	
	
	ret.institute = institute + "";
	
	
	ret.IRBnum = IRBnum + "";
	


	return ret;
    }
}
