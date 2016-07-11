/**--------------------------------------------------------------
* webschedule 
*
* @class: R_event
* @version 
* @author  
* @since   
* @updated April 2005 added operator
* @updated April 2006 added prevowner, prevproj_owner, isgrabbable, reasondropped
*
*
*--------------------------------------------------------------*/



package webschedule.business.r_event;

import webschedule.business.webscheduleBusinessException;
import webschedule.business.person.Person;
import webschedule.business.project.Project;
import webschedule.business.operator.Operator;
import webschedule.data.PersonDO;
import webschedule.data.ProjectDO;
import webschedule.data.R_eventDO;
import webschedule.data.OperatorDO;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;
import java.sql.Date;
     
public class R_event
{
    protected R_eventDO myDO = null;
         
    public R_event()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = R_eventDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty R_event", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating empty R_event", ex);
        }
    }
    
    protected R_event(R_eventDO theR_event)
        throws webscheduleBusinessException
    {
        this.myDO = theR_event;
    }
    
    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting R_event's handle", ex);
        }
    }
    
    public int getCancelday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCancelday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's singing date day", ex);
        }
    }

    public int getCancelmonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCancelmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's scheduling date month", ex);
        }
    }

    public int getCancelyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCancelyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's scheduling date year", ex);
        }
    }

  

    
    public int getStarth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStarth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's start hour", ex);
        }
    }

         public int getStartm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStartm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's start min", ex);
        }
    }



 public int getCancelh()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCancelh();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's Cancel hour", ex);
        }
    }

         public int getCancelm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCanelm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's Cancel min", ex);
        }
    }

     public int getEndh()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndh();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's end hour", ex);
        }
    }

         public int getEndm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's end min", ex);
        }
    }

     public int getEventday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's event day", ex);
        }
    }


    public int getEventmonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's event month", ex);
        }
    }


    public int getEventyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's event year", ex);
        }
    }

    public int getEventdayofw()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventdayofw();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's event year", ex);
        }
    }

    public int isUsed()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsUsed();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Used event", ex);
        }
    }

  public boolean isLast()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsLast();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Last event", ex);
        }
    }
     

     public PersonDO getOwner()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getOwner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's owner", ex);
        }
    }
    
    

    public String getUserID()
        throws webscheduleBusinessException
    {
        try {
            PersonDO owner = getOwner();	
            return owner.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error Project's handle", ex);
        }
    }


    public String getOwnerLogin()
     throws webscheduleBusinessException
    {
    try {
    	PersonDO owner = getOwner();
    	return owner.getLogin();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's owner Login", ex);
        }
    }

    public String getOwnerFirstname()
     throws webscheduleBusinessException
    {
    try {
    	PersonDO owner = getOwner();
    	return owner.getFirstname();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's owner first name", ex);
        }
    }


     public String getOwnerLastname()
     throws webscheduleBusinessException
    {
    try {
    	PersonDO owner = getOwner();
    	return owner.getLastname();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's owner last name", ex);
        }
    }


public String getOwnerEmail()
     throws webscheduleBusinessException
    {
    try {
    	PersonDO owner = getOwner();
    	return owner.getEmail();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's owner email", ex);
        }
    }

     public ProjectDO getProj_owner()
        throws webscheduleBusinessException
    {
        try {

            return myDO.getProj_owner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's project owner", ex);
        }
    }
    
 

    public String getProjectID()
        throws webscheduleBusinessException
    {
        try {
            ProjectDO proj_owner = getProj_owner();	
            return proj_owner.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error Project's handle", ex);
        }
    }


    public String getProj_owner_name()
     throws webscheduleBusinessException
    {
    try {
    	ProjectDO proj_owner = getProj_owner();
    	return proj_owner.getProj_name();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's Project name", ex);
        }
    }


 public int getProj_Codeofpay()
     throws webscheduleBusinessException
    {
    try {
    	ProjectDO proj_owner = getProj_owner();
    	return proj_owner.getCodeofpay();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's Project code of pay", ex);
        }
    }





     public OperatorDO getOperator()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getOperator();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's operator", ex);
        }
    }

    public String getOperatorID()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    }



    public String getOperatorFirstname()
     throws webscheduleBusinessException
    {
    try {
    	OperatorDO operator = getOperator();
    	return operator.getFirstName();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's operator first name", ex);
        }
    }


     public String getOperatorLastname()
     throws webscheduleBusinessException
    {
    try {
    	OperatorDO operator = getOperator();
    	return operator.getLastName();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's operator last name", ex);
        }
    }


public String getOperatorEmail()
     throws webscheduleBusinessException
    {
    try {
    	OperatorDO operator = getOperator();
    	return operator.getEmail();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting R_event's operator email", ex);
        }
    }




    


    public void setCancelday (int Cancelday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setCancelday(Cancelday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's Cancel date", ex);
        }
    }

     public void setCancelmonth (int Cancelmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setCancelmonth(Cancelmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's Cancel month", ex);
        }
    }

    public void setCancelyear (int Cancelyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setCancelyear(Cancelyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's Cancel year", ex);
        }
    }

    
    
	public void setStarth(int starth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStarth(starth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's start hour", ex);
        }
    }

    public void setStartm(int startm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStartm(startm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's start min", ex);
        }
    }

	
    
	public void setCancelh(int Cancelh)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCancelh(Cancelh);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's Cancel hour", ex);
        }
    }

    public void setCancelm(int Cancelm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCanelm(Cancelm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's Cancel min", ex);
        }
    }




    	public void setEndh(int endh)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndh(endh);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's end hour", ex);
        }
    }

    public void setEndm(int endm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndm(endm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's end min", ex);
        }
    }

     public void setEventday(int eventday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventday(eventday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's day", ex);
        }
    }

    public void setEventmonth(int eventmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventmonth(eventmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's month", ex);
        }
    }

    public void setEventyear(int eventyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventyear(eventyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's year", ex);
        }
    }


     public void setEventdayofw(int eventdayofw)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventdayofw(eventdayofw);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's day of week", ex);
        }
    }


    public void setOwner(Person owner) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOwner(PersonDO.createExisting(owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's owner", ex);
        }
    }
    
     public void setProj_owner(Project proj_owner)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProj_owner(ProjectDO.createExisting(proj_owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's project owner", ex);
        }
    }
    
    
     public void setUsed(int isUsed)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsUsed(isUsed);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting used event", ex);
        }
    }

    
     public void setLast(boolean isLast)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsLast(isLast);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Last event", ex);
        }
    }

    
 public void setOperator(Operator operator) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOperator(OperatorDO.createExisting(operator.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting R_event's operator", ex);
        }
    }
    


    public void save() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.commit();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error saving R_event", ex);
        }
    }
    
    public void delete() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.delete();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error deleting R_event", ex);
        }
    }
}
