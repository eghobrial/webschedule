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
 * /home/emang/myapps/webschedule/webschedule/data/proposalBDO.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import java.sql.*;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;

import com.lutris.dods.builder.generator.query.*;

/**
 * proposalBDO contains the same set and get methods as
 * the proposalDO class.
 * Business Object (BO) classes typically need these set and get methods.
 * So by deriving a BO from a BDO, or by implementing a BO that 
 * contains a BDO, the developer of the BO is spared some work.
 *
 * @author emang
 * @version $Revision: 1.5 $
 */
public class proposalBDO implements java.io.Serializable {

    /**
     * The proposalDO object upon which the set and get methods operate.
     * This member is protected so that classes derived from proposalBDO
     * can access the underlying Data Object.
     */
    protected proposalDO DO;

    /**
     * Note:  This method is intended for use only by other BDO classes.
     * Presentation Layer classes should (theoretically) always use the
     * Business Layer (BDO) to create/access Data Layer (DO) objects.
     * The overhead for using BDO objects is small
     * (the BDO classes are fairly lightweight.)
     *
     * @return The DO object held by this BDO object.
     */
    public proposalDO getDO() { 
	return DO;
    }

    /**
     * Like the class <CODE>proposalDO</CODE>, 
     * this class acts as a factory.
     * Business Object (BO) classes typically need these set and get methods.
     * So by deriving a BO from a BDO, or by implementing a BO that 
     * contains one or more BDOs, the developer of the BO is spared some work.
     *
     * @exception Exception
     *   If an error occurs.
     */
    public static proposalBDO createVirgin() throws Exception { 
	proposalBDO bdo = new proposalBDO ();
	return bdo;
    }

    /**
     * Constructor for use by classes derived from <CODE>proposalBDO</CODE>.
     * Example usage:<CODE>
     *      class CustomerBO extends CustomerBDO {
     *          // a BDO class is commonly extended in order to implement:
     *          public void beforeAnySet() throws Exception {
     *              if ( CustomerDO should not be altered )
     *                  throw new ApplicationException( "ERROR" );
     *          }
     *          public CustomerBO( CustomerDO DO ) {
     *              super( DO );
     *          }
     *      }
     *      class SomePresentationLayerClass {
     *          public CustomerBO findCustomer( String name ) {
     *              CustomerQuery q = new CustomerQuery();
     *              q.setQueryName( name );
     *              CustomerDO DO = q.getNextDO();
     *              // Here the CustomerBO ctor fires the CustomerBDO ctor.
     *              return new CustomerBO( DO );
     *          }
     *      }
     * </CODE>
     */
    public proposalBDO( proposalDO DO ) { 
	this.DO = DO;
    }

    /**
     * Constructor required by <CODE>proposalBDO.create</CODE> methods.
     */
    public proposalBDO() throws Exception { 
	this.DO = proposalDO.createVirgin();
    }

    /**
     * The createExisting method is used to create a <CODE>proposalBDO</CODE>
     * from a <CODE>proposalDO</CODE> that was returned by 
     * the <CODE>proposalQuery</CODE> class.
     */
    public static proposalBDO createExisting( proposalDO DO ) { 
	proposalBDO bdo = new proposalBDO ( DO );
	return bdo;
    }

    /** 
     * The getBDOarray method performs a database query to
     * return an array of <CODE>proposalBDO</CODE> objects
     * representing all the rows in the <CODE>proposal</CODE> table.
     *
     * This method is a minimal example of using a Query class.
     * To restrict the set of objects returned,  you could
     * invoke <CODE>query.setXxx()</CODE>, where <CODE>Xxx</CODE>
     * is an Attribute of <CODE>proposalDO</CODE> which was 
     * marked as "Can be queried" in the DODS Attribute Editor.
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static proposalBDO[] getBDOarray()
    throws DataObjectException {
        proposalDO[] DOarray = null;
        try {
            proposalQuery query = new proposalQuery();
	    // To restrict the set of objects returned,
	    // you could invoke query.setXxx(), where Xxx
	    // is an Attribute of <CODE>proposalDO</CODE> which was 
	    // marked as "Can be queried" in the DODS Attribute Editor.
            DOarray = query.getDOArray();
        } catch ( NonUniqueQueryException e1 ) {
            // proposalQuery will throw NonUniqueQueryException
            // only if query.requireUniqueInstance() is called
	    // and more than one DO was found.
        } catch ( DataObjectException e2 ) {
            // This could happen if the database server dies, etc.
            throw e2;
        }
        proposalBDO[] BDOarray = new proposalBDO[ DOarray.length ];
        for ( int i = 0; i < DOarray.length; i++ )
            BDOarray[i] = proposalBDO.createExisting( DOarray[i] );
 
        return BDOarray;
    }


    /**
     * The developer of a Business Object that derives from this class
     * can override the methods:<CODE>
     *     beforeAnyGet
     *     beforeAnySet
     *     afterAnySet
     * </CODE> to handle any general assertions or cleanup needed
     * for <CODE>get</CODE> and <CODE>set</CODE> methods.
     */
    protected void beforeAnyGet() {
    }
    // beforeAnySet throws plain Exception to allow overriding implementations
    // to throw any flavor needed.
    protected void beforeAnySet() throws Exception {
	if ( DO.isReadOnly() )
	    throw new DataObjectException( DO + " is read-only." ); 
    }
    protected void afterAnySet() {
    }

    /**
     * The methods <CODE>
     *     getHandle
     *     hasMatchingHandle
     * </CODE> are used by Presentation Objects that need to populate
     * HTML select lists with <CODE>proposalBDO</CODE> objects as options.
     *
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>
     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * This proposalBDO object holds a reference to a proposalDO object.
     * The id of this proposalBDO is the id of its proposalDO.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @return id of this BDO as a string
     *   If an object id can't be allocated for this object.
     */
    public String getHandle()
    throws DatabaseManagerException {
	return DO.getHandle();
    }

    /**
     * @param handle
     *   <CODE>String</CODE> representation of the id for this BDO
     * @return boolean
     *   True if the string version of the id of this DO matches passed handle
     * @see getHandle
     */
    public boolean hasMatchingHandle( String handle ) {
	return DO.hasMatchingHandle( handle );
    }


    /**
     * for debugging
     */
    public String toString() {
	if ( null == DO )
		return "NULL";
	return DO.toString();
    }

   /**
    * Get owner of the proposalDO
    *
    * @return owner of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.PersonDO getOwner () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOwner ();
   }

   
   /**
    * Set owner of the proposalDO
    *
    * @param owner of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOwner ( webschedule.data.PersonDO owner ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOwner ( owner );
      afterAnySet();	// business actions/assertions after data assignment
   }

   

   /**
    * Get BDO-wrapped owner of the proposalDO
    *
    * @return BDO-wrapped owner of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.PersonBDO getOwnerBDO () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      webschedule.data.PersonBDO b = webschedule.data.PersonBDO.createExisting(
					  DO.getOwner () );
      return b;
   }

   /**
    * Set owner of the proposalDO
    *
    * @param BDO-wrapped owner of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOwner ( webschedule.data.PersonBDO owner ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      if ( null == owner ) {
	  if ( false )
	      DO.setOwner ( null );
	  else 
	      throw new DataObjectException( 
		  "proposalBDO.setOwner does not allow NULL." );
      } else {
          DO.setOwner ( owner.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get today of the proposalDO
    *
    * @return today of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getToday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getToday ();
   }

   
   /**
    * Set today of the proposalDO
    *
    * @param today of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setToday ( int today ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setToday ( today );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get month of the proposalDO
    *
    * @return month of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getMonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getMonth ();
   }

   
   /**
    * Set month of the proposalDO
    *
    * @param month of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setMonth ( int month ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setMonth ( month );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get year of the proposalDO
    *
    * @return year of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getYear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getYear ();
   }

   
   /**
    * Set year of the proposalDO
    *
    * @param year of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setYear ( int year ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setYear ( year );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Isucsd of the proposalDO
    *
    * @return Isucsd of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsucsd () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsucsd ();
   }

   
   /**
    * Set Isucsd of the proposalDO
    *
    * @param Isucsd of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIsucsd ( boolean Isucsd ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIsucsd ( Isucsd );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Isanimal of the proposalDO
    *
    * @return Isanimal of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsanimal () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsanimal ();
   }

   
   /**
    * Set Isanimal of the proposalDO
    *
    * @param Isanimal of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIsanimal ( boolean Isanimal ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIsanimal ( Isanimal );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Issample of the proposalDO
    *
    * @return Issample of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIssample () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIssample ();
   }

   
   /**
    * Set Issample of the proposalDO
    *
    * @param Issample of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIssample ( boolean Issample ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIssample ( Issample );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get proj_name of the proposalDO
    *
    * @return proj_name of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProj_name () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProj_name ();
   }

   
   /**
    * Set proj_name of the proposalDO
    *
    * @param proj_name of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProj_name ( String proj_name ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProj_name ( proj_name );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get cname of the proposalDO
    *
    * @return cname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getCname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCname ();
   }

   
   /**
    * Set cname of the proposalDO
    *
    * @param cname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setCname ( String cname ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setCname ( cname );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get cphone of the proposalDO
    *
    * @return cphone of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getCphone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCphone ();
   }

   
   /**
    * Set cphone of the proposalDO
    *
    * @param cphone of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setCphone ( String cphone ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setCphone ( cphone );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get cemail of the proposalDO
    *
    * @return cemail of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getCemail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCemail ();
   }

   
   /**
    * Set cemail of the proposalDO
    *
    * @param cemail of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setCemail ( String cemail ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setCemail ( cemail );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get CntrOp of the proposalDO
    *
    * @return CntrOp of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getCntrOp () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCntrOp ();
   }

   
   /**
    * Set CntrOp of the proposalDO
    *
    * @param CntrOp of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setCntrOp ( boolean CntrOp ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setCntrOp ( CntrOp );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get op1lastname of the proposalDO
    *
    * @return op1lastname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp1lastname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOp1lastname ();
   }

   
   /**
    * Set op1lastname of the proposalDO
    *
    * @param op1lastname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOp1lastname ( String op1lastname ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOp1lastname ( op1lastname );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get op1firstname of the proposalDO
    *
    * @return op1firstname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp1firstname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOp1firstname ();
   }

   
   /**
    * Set op1firstname of the proposalDO
    *
    * @param op1firstname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOp1firstname ( String op1firstname ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOp1firstname ( op1firstname );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get op1phone of the proposalDO
    *
    * @return op1phone of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp1phone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOp1phone ();
   }

   
   /**
    * Set op1phone of the proposalDO
    *
    * @param op1phone of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOp1phone ( String op1phone ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOp1phone ( op1phone );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get op1email of the proposalDO
    *
    * @return op1email of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp1email () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOp1email ();
   }

   
   /**
    * Set op1email of the proposalDO
    *
    * @param op1email of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOp1email ( String op1email ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOp1email ( op1email );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get op2lastname of the proposalDO
    *
    * @return op2lastname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp2lastname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOp2lastname ();
   }

   
   /**
    * Set op2lastname of the proposalDO
    *
    * @param op2lastname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOp2lastname ( String op2lastname ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOp2lastname ( op2lastname );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get op2firstname of the proposalDO
    *
    * @return op2firstname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp2firstname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOp2firstname ();
   }

   
   /**
    * Set op2firstname of the proposalDO
    *
    * @param op2firstname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOp2firstname ( String op2firstname ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOp2firstname ( op2firstname );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get op2phone of the proposalDO
    *
    * @return op2phone of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp2phone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOp2phone ();
   }

   
   /**
    * Set op2phone of the proposalDO
    *
    * @param op2phone of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOp2phone ( String op2phone ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOp2phone ( op2phone );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get op2email of the proposalDO
    *
    * @return op2email of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp2email () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOp2email ();
   }

   
   /**
    * Set op2email of the proposalDO
    *
    * @param op2email of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOp2email ( String op2email ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOp2email ( op2email );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get indexnum of the proposalDO
    *
    * @return indexnum of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getIndexnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIndexnum ();
   }

   
   /**
    * Set indexnum of the proposalDO
    *
    * @param indexnum of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIndexnum ( String indexnum ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIndexnum ( indexnum );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get baline1 of the proposalDO
    *
    * @return baline1 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBaline1 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBaline1 ();
   }

   
   /**
    * Set baline1 of the proposalDO
    *
    * @param baline1 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBaline1 ( String baline1 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBaline1 ( baline1 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get baline2 of the proposalDO
    *
    * @return baline2 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBaline2 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBaline2 ();
   }

   
   /**
    * Set baline2 of the proposalDO
    *
    * @param baline2 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBaline2 ( String baline2 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBaline2 ( baline2 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get baline3 of the proposalDO
    *
    * @return baline3 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBaline3 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBaline3 ();
   }

   
   /**
    * Set baline3 of the proposalDO
    *
    * @param baline3 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBaline3 ( String baline3 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBaline3 ( baline3 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get bacity of the proposalDO
    *
    * @return bacity of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBacity () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBacity ();
   }

   
   /**
    * Set bacity of the proposalDO
    *
    * @param bacity of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBacity ( String bacity ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBacity ( bacity );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get bast of the proposalDO
    *
    * @return bast of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBast () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBast ();
   }

   
   /**
    * Set bast of the proposalDO
    *
    * @param bast of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBast ( String bast ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBast ( bast );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get bazip of the proposalDO
    *
    * @return bazip of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBazip () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBazip ();
   }

   
   /**
    * Set bazip of the proposalDO
    *
    * @param bazip of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBazip ( String bazip ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBazip ( bazip );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get fpname of the proposalDO
    *
    * @return fpname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFpname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getFpname ();
   }

   
   /**
    * Set fpname of the proposalDO
    *
    * @param fpname of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setFpname ( String fpname ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setFpname ( fpname );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get fpphone of the proposalDO
    *
    * @return fpphone of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFpphone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getFpphone ();
   }

   
   /**
    * Set fpphone of the proposalDO
    *
    * @param fpphone of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setFpphone ( String fpphone ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setFpphone ( fpphone );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get fpemail of the proposalDO
    *
    * @return fpemail of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFpemail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getFpemail ();
   }

   
   /**
    * Set fpemail of the proposalDO
    *
    * @param fpemail of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setFpemail ( String fpemail ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setFpemail ( fpemail );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get thours of the proposalDO
    *
    * @return thours of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getThours () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getThours ();
   }

   
   /**
    * Set thours of the proposalDO
    *
    * @param thours of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setThours ( int thours ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setThours ( thours );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get writeup of the proposalDO
    *
    * @return writeup of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getWriteup () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getWriteup ();
   }

   
   /**
    * Set writeup of the proposalDO
    *
    * @param writeup of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setWriteup ( String writeup ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setWriteup ( writeup );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get dataanalysis of the proposalDO
    *
    * @return dataanalysis of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getDataanalysis () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getDataanalysis ();
   }

   
   /**
    * Set dataanalysis of the proposalDO
    *
    * @param dataanalysis of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setDataanalysis ( String dataanalysis ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setDataanalysis ( dataanalysis );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get IACUCFaxed of the proposalDO
    *
    * @return IACUCFaxed of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIACUCFaxed () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIACUCFaxed ();
   }

   
   /**
    * Set IACUCFaxed of the proposalDO
    *
    * @param IACUCFaxed of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIACUCFaxed ( boolean IACUCFaxed ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIACUCFaxed ( IACUCFaxed );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get RFCoils of the proposalDO
    *
    * @return RFCoils of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getRFCoils () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getRFCoils ();
   }

   
   /**
    * Set RFCoils of the proposalDO
    *
    * @param RFCoils of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setRFCoils ( boolean RFCoils ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setRFCoils ( RFCoils );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get restraints of the proposalDO
    *
    * @return restraints of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getRestraints () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getRestraints ();
   }

   
   /**
    * Set restraints of the proposalDO
    *
    * @param restraints of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setRestraints ( boolean restraints ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setRestraints ( restraints );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get physioeq of the proposalDO
    *
    * @return physioeq of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getPhysioeq () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPhysioeq ();
   }

   
   /**
    * Set physioeq of the proposalDO
    *
    * @param physioeq of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPhysioeq ( boolean physioeq ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPhysioeq ( physioeq );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get anesthetics of the proposalDO
    *
    * @return anesthetics of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getAnesthetics () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getAnesthetics ();
   }

   
   /**
    * Set anesthetics of the proposalDO
    *
    * @param anesthetics of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setAnesthetics ( boolean anesthetics ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setAnesthetics ( anesthetics );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get ancillary of the proposalDO
    *
    * @return ancillary of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getAncillary () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getAncillary ();
   }

   
   /**
    * Set ancillary of the proposalDO
    *
    * @param ancillary of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setAncillary ( boolean ancillary ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setAncillary ( ancillary );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get op1status of the proposalDO
    *
    * @return op1status of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getOp1status () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOp1status ();
   }

   
   /**
    * Set op1status of the proposalDO
    *
    * @param op1status of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOp1status ( boolean op1status ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOp1status ( op1status );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get op2status of the proposalDO
    *
    * @return op2status of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getOp2status () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOp2status ();
   }

   
   /**
    * Set op2status of the proposalDO
    *
    * @param op2status of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOp2status ( boolean op2status ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOp2status ( op2status );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get IACUC of the proposalDO
    *
    * @return IACUC of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIACUC () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIACUC ();
   }

   
   /**
    * Set IACUC of the proposalDO
    *
    * @param IACUC of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIACUC ( boolean IACUC ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIACUC ( IACUC );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get status of the proposalDO
    *
    * @return status of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStatus () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getStatus ();
   }

   
   /**
    * Set status of the proposalDO
    *
    * @param status of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setStatus ( int status ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setStatus ( status );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get BioHazard of the proposalDO
    *
    * @return BioHazard of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBioHazard () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBioHazard ();
   }

   
   /**
    * Set BioHazard of the proposalDO
    *
    * @param BioHazard of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBioHazard ( String BioHazard ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBioHazard ( BioHazard );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get stimuli of the proposalDO
    *
    * @return stimuli of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getStimuli () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getStimuli ();
   }

   
   /**
    * Set stimuli of the proposalDO
    *
    * @param stimuli of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setStimuli ( boolean stimuli ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setStimuli ( stimuli );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get AnimalTrans of the proposalDO
    *
    * @return AnimalTrans of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getAnimalTrans () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getAnimalTrans ();
   }

   
   /**
    * Set AnimalTrans of the proposalDO
    *
    * @param AnimalTrans of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setAnimalTrans ( boolean AnimalTrans ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setAnimalTrans ( AnimalTrans );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get bproj_name of the proposalDO
    *
    * @return bproj_name of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBproj_name () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBproj_name ();
   }

   
   /**
    * Set bproj_name of the proposalDO
    *
    * @param bproj_name of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBproj_name ( String bproj_name ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBproj_name ( bproj_name );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get contrast of the proposalDO
    *
    * @return contrast of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getContrast () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getContrast ();
   }

   
   /**
    * Set contrast of the proposalDO
    *
    * @param contrast of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setContrast ( boolean contrast ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setContrast ( contrast );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get AnimTransDate of the proposalDO
    *
    * @return AnimTransDate of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getAnimTransDate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getAnimTransDate ();
   }

   
   /**
    * Set AnimTransDate of the proposalDO
    *
    * @param AnimTransDate of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setAnimTransDate ( java.sql.Date AnimTransDate ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setAnimTransDate ( AnimTransDate );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get IACUCDate of the proposalDO
    *
    * @return IACUCDate of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getIACUCDate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIACUCDate ();
   }

   
   /**
    * Set IACUCDate of the proposalDO
    *
    * @param IACUCDate of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIACUCDate ( java.sql.Date IACUCDate ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIACUCDate ( IACUCDate );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get stdate of the proposalDO
    *
    * @return stdate of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getStdate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getStdate ();
   }

   
   /**
    * Set stdate of the proposalDO
    *
    * @param stdate of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setStdate ( java.sql.Date stdate ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setStdate ( stdate );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get expdate of the proposalDO
    *
    * @return expdate of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getExpdate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getExpdate ();
   }

   
   /**
    * Set expdate of the proposalDO
    *
    * @param expdate of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setExpdate ( java.sql.Date expdate ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setExpdate ( expdate );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get comments of the proposalDO
    *
    * @return comments of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getComments () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getComments ();
   }

   
   /**
    * Set comments of the proposalDO
    *
    * @param comments of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setComments ( String comments ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setComments ( comments );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get nighttime of the proposalDO
    *
    * @return nighttime of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getNighttime () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getNighttime ();
   }

   
   /**
    * Set nighttime of the proposalDO
    *
    * @param nighttime of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setNighttime ( boolean nighttime ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setNighttime ( nighttime );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get copis of the proposalDO
    *
    * @return copis of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getCopis () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCopis ();
   }

   
   /**
    * Set copis of the proposalDO
    *
    * @param copis of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setCopis ( String copis ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setCopis ( copis );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get intcomments of the proposalDO
    *
    * @return intcomments of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getIntcomments () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIntcomments ();
   }

   
   /**
    * Set intcomments of the proposalDO
    *
    * @param intcomments of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIntcomments ( String intcomments ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIntcomments ( intcomments );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get response of the proposalDO
    *
    * @return response of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getResponse () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getResponse ();
   }

   
   /**
    * Set response of the proposalDO
    *
    * @param response of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setResponse ( String response ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setResponse ( response );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get animalq1 of the proposalDO
    *
    * @return animalq1 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getAnimalq1 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getAnimalq1 ();
   }

   
   /**
    * Set animalq1 of the proposalDO
    *
    * @param animalq1 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setAnimalq1 ( String animalq1 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setAnimalq1 ( animalq1 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get animalq2 of the proposalDO
    *
    * @return animalq2 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getAnimalq2 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getAnimalq2 ();
   }

   
   /**
    * Set animalq2 of the proposalDO
    *
    * @param animalq2 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setAnimalq2 ( String animalq2 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setAnimalq2 ( animalq2 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get animalq3 of the proposalDO
    *
    * @return animalq3 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getAnimalq3 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getAnimalq3 ();
   }

   
   /**
    * Set animalq3 of the proposalDO
    *
    * @param animalq3 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setAnimalq3 ( String animalq3 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setAnimalq3 ( animalq3 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get animalq4 of the proposalDO
    *
    * @return animalq4 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getAnimalq4 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getAnimalq4 ();
   }

   
   /**
    * Set animalq4 of the proposalDO
    *
    * @param animalq4 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setAnimalq4 ( String animalq4 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setAnimalq4 ( animalq4 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get proposalq1 of the proposalDO
    *
    * @return proposalq1 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq1 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProposalq1 ();
   }

   
   /**
    * Set proposalq1 of the proposalDO
    *
    * @param proposalq1 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProposalq1 ( String proposalq1 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProposalq1 ( proposalq1 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get proposalq2 of the proposalDO
    *
    * @return proposalq2 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq2 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProposalq2 ();
   }

   
   /**
    * Set proposalq2 of the proposalDO
    *
    * @param proposalq2 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProposalq2 ( String proposalq2 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProposalq2 ( proposalq2 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get proposalq3 of the proposalDO
    *
    * @return proposalq3 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq3 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProposalq3 ();
   }

   
   /**
    * Set proposalq3 of the proposalDO
    *
    * @param proposalq3 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProposalq3 ( String proposalq3 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProposalq3 ( proposalq3 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get proposalq4 of the proposalDO
    *
    * @return proposalq4 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq4 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProposalq4 ();
   }

   
   /**
    * Set proposalq4 of the proposalDO
    *
    * @param proposalq4 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProposalq4 ( String proposalq4 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProposalq4 ( proposalq4 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get proposalq5 of the proposalDO
    *
    * @return proposalq5 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq5 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProposalq5 ();
   }

   
   /**
    * Set proposalq5 of the proposalDO
    *
    * @param proposalq5 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProposalq5 ( String proposalq5 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProposalq5 ( proposalq5 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get proposalq6 of the proposalDO
    *
    * @return proposalq6 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq6 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProposalq6 ();
   }

   
   /**
    * Set proposalq6 of the proposalDO
    *
    * @param proposalq6 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProposalq6 ( String proposalq6 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProposalq6 ( proposalq6 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get proposalq7 of the proposalDO
    *
    * @return proposalq7 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq7 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProposalq7 ();
   }

   
   /**
    * Set proposalq7 of the proposalDO
    *
    * @param proposalq7 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProposalq7 ( String proposalq7 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProposalq7 ( proposalq7 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get proposalq8 of the proposalDO
    *
    * @return proposalq8 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq8 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProposalq8 ();
   }

   
   /**
    * Set proposalq8 of the proposalDO
    *
    * @param proposalq8 of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProposalq8 ( String proposalq8 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProposalq8 ( proposalq8 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get bookmark of the proposalDO
    *
    * @return bookmark of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBookmark () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBookmark ();
   }

   
   /**
    * Set bookmark of the proposalDO
    *
    * @param bookmark of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBookmark ( String bookmark ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBookmark ( bookmark );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get approvalDate of the proposalDO
    *
    * @return approvalDate of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getApprovalDate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getApprovalDate ();
   }

   
   /**
    * Set approvalDate of the proposalDO
    *
    * @param approvalDate of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setApprovalDate ( java.sql.Date approvalDate ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setApprovalDate ( approvalDate );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get POnum of the proposalDO
    *
    * @return POnum of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPOnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPOnum ();
   }

   
   /**
    * Set POnum of the proposalDO
    *
    * @param POnum of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPOnum ( String POnum ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPOnum ( POnum );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get institute of the proposalDO
    *
    * @return institute of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getInstitute () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getInstitute ();
   }

   
   /**
    * Set institute of the proposalDO
    *
    * @param institute of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setInstitute ( String institute ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setInstitute ( institute );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get IRBnum of the proposalDO
    *
    * @return IRBnum of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getIRBnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIRBnum ();
   }

   
   /**
    * Set IRBnum of the proposalDO
    *
    * @param IRBnum of the proposalDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIRBnum ( String IRBnum ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIRBnum ( IRBnum );
      afterAnySet();	// business actions/assertions after data assignment
   }


   



  /**
   * Inserts/Updates the DO into its table.
   *
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  public void commit() 
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
    modifyDO( null, false );
  }

  /**
   * Inserts/Updates the DO into its table.
   * The transaction is likely provided by the commit() method of another BDO
   * whose DO references this DO.
   * 
   * @param dbt The transaction object to use for this operation.
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  public void commit(DBTransaction dbt)
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
    modifyDO( dbt, false );
  }

  /**
   * Deletes the DO from its table.
   *
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  public void delete() 
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
    modifyDO( null, true );
  }

  /**
   * Deletes the DO from its table.
   * The transaction is likely provided by the delete() method of another BDO
   * whose DO references this DO.
   *
   * @param dbt The transaction object to use for this operation.
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  public void delete(DBTransaction dbt)
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
    modifyDO( dbt, true );
  }

      /**
     * A stub method for implementing pre-commit assertions 
     * for the owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where owner is not valid for writing to the database.
     */
    protected void okToCommitOwner( webschedule.data.PersonDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where owner is not valid for deletion from the database.
     */
    protected void okToDeleteOwner( webschedule.data.PersonDO member ) 
    throws RefAssertionException { }



  /**
   * Modifies the DO within its table.
   * Performs recursive commit/delete on referenced DOs;
   * all operations occur within a single transaction
   * to allow rollback in the event of error.
   * Only the creator of the transaction releases it.
   *
   * @param dbt The transaction object to use for this operation.
   * @param delete True if doing a delete, otherwise doing insert/update.
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  protected void modifyDO( DBTransaction dbt, boolean delete )
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
    boolean ownTransaction = false;
    try {
      if ( null == dbt ) {
	DatabaseManager dbm = Enhydra.getDatabaseManager();
        dbt = dbm.createTransaction();      // create a transaction
	ownTransaction = true;
      }
      if ( null == dbt )
	throw new DatabaseManagerException(
		"DatabaseManager.createTransaction returned null." );
      if ( delete ) {
	  // Code to perform cascading deletes is generated here
	  // if cascading deletes are not supported by the database.      
	  
          // The following line keeps the compiler happy
          // when the CASCADING_DELETES tag is empty.
          if ( false )
              throw new QueryException("XXX");
      } else {
	  // commit referenced DOs.
	  	webschedule.data.PersonDO owner_DO = DO.getOwner();
	if ( null != owner_DO ) {
	    if ( owner_DO.isLoaded() ) {
		okToCommitOwner( owner_DO );
		webschedule.data.PersonBDO b = 
		    webschedule.data.PersonBDO.createExisting(
						    owner_DO );
		b.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit proposalBDO ( " + toString() +
		    " ) because owner is not allowed to be null." );
	}

      }
      if ( false ) {
	  // This throw is here to keep the compiler happy
	  // in the case of a DO that does not refer to other DOs.
	  // In that case, the above delete/commit code blocks will be empty
	  // and throw nothing.
	  throw new DataObjectException( "foo" );	  
      }
      if ( delete ) {
          dbt.delete( DO );
      } else {
	  if ( DO.isLoaded() )
	      dbt.insert( DO );   // dbt.insert() handles insertions and updates
      }
      if (ownTransaction) {
	  dbt.commit(); // commit the transaction
      }
    } catch (DataObjectException doe) {
      throw doe;
    } catch (SQLException sqle) {
      StringBuffer message = new StringBuffer("Failed to insert/update DO: ");
      message.append(sqle.getMessage());

      // rollback, if necessary
      if (ownTransaction) {
        try {
          dbt.rollback();
        } catch (SQLException sqle2) {
          message.insert(0,"\n");
          message.insert(0,sqle2.getMessage());
          message.insert(0,"Rollback failed: ");
        }
      }
      throw new SQLException(message.toString());
    } finally {
      // release the transaction, if any
      if (ownTransaction) {
        dbt.release();
      }
    }
  }

}
