/**--------------------------------------------------------------
* webschedule demo
*
* @class: ProposalFactoryImpl
* @version 
* @author  
* @since   
*
*
*
*
*--------------------------------------------------------------*/

package webschedule.business.proposal;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import webschedule.business.person.Person;
import com.lutris.dods.builder.generator.query.*;
import webschedule.business.person.Person;
/*import webschedule.data.PersonDO;*/
import com.lutris.appserver.server.sql.ObjectId;

public class ProposalFactory
{    
    /** 
     * The findproposalsForPerson method performs a database query to
     * return an array of <CODE>Project</CODE> objects
     * representing all the rows in the <CODE>disc</CODE> table
     * that have and owner matching <CODE>Person owner</CODE>. 
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static Proposal[] findProposalsForPerson(Person owner)
        throws webscheduleBusinessException
    {
	    Proposal[] theProposalArray = null;
        
	    try {
            proposalQuery query = new proposalQuery();
	        // To restrict the set of discs returned
	        // to the that of the specific owner set in the query
	        query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));
            // Order discs alphabetically by artist
            //query.addOrderByArtist();
	        proposalDO[] DOarray = query.getDOArray();
	        theProposalArray = new Proposal[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theProposalArray[i] = new Proposal(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findPropsalsForPerson()", ex);
        }
        
        return theProposalArray;
    }
    
    
    
    /**
     * 
     */
    public static Proposal findProposalByID(String id)
        throws webscheduleBusinessException
    {
        Proposal theProposal = null;
        
	    try {
            proposalQuery query = new proposalQuery();
	        // Require that we only find ONE disc
            query.requireUniqueInstance();
	        query.setQueryOId(new ObjectId(id));
	        proposalDO theProposalDO = query.getNextDO();
	    	theProposal = new Proposal(theProposalDO);
            return theProposal;
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findProposalsForPerson()", ex);
        }
    }


   /* find Proposal by name*/

  public static Proposal findProposalByProj_name(String proj_name)
        throws webscheduleBusinessException
    {


	    try {
            proposalQuery query = new proposalQuery();
            query.setQueryProj_name(proj_name);
	        // Require that we only find ONE proposal
            query.requireUniqueInstance();
	
	        proposalDO[] foundProposal = query.getDOArray();
	        if (foundProposal.length != 0) {
	    	return new Proposal(foundProposal[0]);
	    	} else  {
	    	return null;
	    	}

	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findProposalByProj_name()", ex);
        }
    }


 /* find Proposal by breif name, project name on webschedule*/

  public static Proposal findProposalByYearMonthOwnerBproj_name(int year, int month,Person owner,String Bproj_name)
        throws webscheduleBusinessException
    {


	    try {
            proposalQuery query = new proposalQuery();
	    query.setQueryYear(year);
	    query.setQueryMonth(month);
	    query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));
            query.setQueryBproj_name(Bproj_name);
	        // Require that we only find ONE proposal
            query.requireUniqueInstance();
	
	        proposalDO[] foundProposal = query.getDOArray();
	        if (foundProposal.length != 0) {
	    	return new Proposal(foundProposal[0]);
	    	} else  {
	    	return null;
	    	}

	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findProposalByBproj_name()", ex);
        }
    }



 /* find Proposal by breif name, project name on webschedule*/

  public static Proposal findProposalByOwnerBproj_name(Person owner,String Bproj_name)
        throws webscheduleBusinessException
    {


	    try {
            proposalQuery query = new proposalQuery();
	   
	    query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));
            query.setQueryBproj_name(Bproj_name);
	        // Require that we only find ONE proposal
            query.requireUniqueInstance();
	
	        proposalDO[] foundProposal = query.getDOArray();
	        if (foundProposal.length != 0) {
	    	return new Proposal(foundProposal[0]);
	    	} else  {
	    	return null;
	    	}

	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findProposalByBproj_name()", ex);
        }
    }

   /* find Proposal by breif name, project name on webschedule*/

  public static Proposal findProposalByBproj_name(String Bproj_name)
        throws webscheduleBusinessException
    {


	    try {
            proposalQuery query = new proposalQuery();
            query.setQueryBproj_name(Bproj_name);
	        // Require that we only find ONE proposal
            query.requireUniqueInstance();
	
	        proposalDO[] foundProposal = query.getDOArray();
	        if (foundProposal.length != 0) {
	    	return new Proposal(foundProposal[0]);
	    	} else  {
	    	return null;
	    	}

	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findProposalByBproj_name()", ex);
        }
    }

 
/* find proposal list */
 public static Proposal[] getProposalsList()
 	throws webscheduleBusinessException
 	 {
 	
 		
 	    Proposal[] theProposalArray = null;

	    try {
            proposalQuery query = new proposalQuery();
	
            // Order proposals alphabetically by proposal name
	 //    query.addOrderByOwner();
            query.addOrderByProj_name();
	        proposalDO[] DOarray = query.getDOArray();
	        theProposalArray = new Proposal[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theProposalArray[i] = new Proposal(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getproposalsList()", ex);
        }

        return theProposalArray;
    }
 	

}

