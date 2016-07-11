/**--------------------------------------------------------------
* webschedule 
*
* @class: S_event
* @version 
* @author  
* @since   
* @updated April 2005 added operator
* @updated April 2006 added prevowner, prevproj_owner, isgrabbable, reasondropped
*
*
*--------------------------------------------------------------*/



package webschedule.business.s_event;

import webschedule.business.webscheduleBusinessException;
import webschedule.business.person.Person;
import webschedule.business.project.Project;
import webschedule.business.operator.Operator;
import webschedule.data.PersonDO;
import webschedule.data.ProjectDO;
import webschedule.data.S_eventDO;
import webschedule.data.OperatorDO;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;
import java.sql.Date;
     
public class S_event
{
    protected S_eventDO myDO = null;
         
    public S_event()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = S_eventDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty S_event", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating empty S_event", ex);
        }
    }
    
    protected S_event(S_eventDO theS_event)
        throws webscheduleBusinessException
    {
        this.myDO = theS_event;
    }
    
    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting S_event's handle", ex);
        }
    }
    
    public int getTodayday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTodayday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's singing date day", ex);
        }
    }

    public int getTodaymonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTodaymonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's scheduling date month", ex);
        }
    }

    public int getTodayyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTodayyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's scheduling date year", ex);
        }
    }
    
    public int getTodayh()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTodayh();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's scheduling date hour", ex);
        }
    }
    
      public int getTodaym()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTodaym();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's scheduling date minute", ex);
        }
    }

    public String getDescription()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDescription();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's description", ex);
        }
    }
    

    
    public int getStarth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStarth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's start hour", ex);
        }
    }

         public int getStartm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStartm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's start min", ex);
        }
    }

     public int getEndh()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndh();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's end hour", ex);
        }
    }

         public int getEndm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's end min", ex);
        }
    }

     public int getEventday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's event day", ex);
        }
    }


    public int getEventmonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's event month", ex);
        }
    }


    public int getEventyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's event year", ex);
        }
    }

    public int getEventdayofw()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventdayofw();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's event year", ex);
        }
    }

    public boolean isDevelop()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsDevelop();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Develop event", ex);
        }
    }


     public boolean isRepeat()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsRepeat();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Repeat event flag", ex);
        }
    }
    
     public boolean isGrabbable()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsGrabbable();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is grab event flag", ex);
        }
    }

     public PersonDO getOwner()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getOwner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's owner", ex);
        }
    }
    
    
     public PersonDO getPrevowner()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getPrevowner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's prev owner", ex);
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
            throw new webscheduleBusinessException("Error getting S_event's owner Login", ex);
        }
    }

    public String getOwnerFirstname()
     throws webscheduleBusinessException
    {
    try {
    	PersonDO owner = getOwner();
    	return owner.getFirstname();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's owner first name", ex);
        }
    }


     public String getOwnerLastname()
     throws webscheduleBusinessException
    {
    try {
    	PersonDO owner = getOwner();
    	return owner.getLastname();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's owner last name", ex);
        }
    }


public String getOwnerEmail()
     throws webscheduleBusinessException
    {
    try {
    	PersonDO owner = getOwner();
    	return owner.getEmail();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's owner email", ex);
        }
    }

     public ProjectDO getProj_owner()
        throws webscheduleBusinessException
    {
        try {

            return myDO.getProj_owner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's project owner", ex);
        }
    }
    
 public ProjectDO getPrevproj_owner()
        throws webscheduleBusinessException
    {
        try {

            return myDO.getPrevproj_owner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's project owner", ex);
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
            throw new webscheduleBusinessException("Error getting S_event's Project name", ex);
        }
    }


 public int getProj_Codeofpay()
     throws webscheduleBusinessException
    {
    try {
    	ProjectDO proj_owner = getProj_owner();
    	return proj_owner.getCodeofpay();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's Project code of pay", ex);
        }
    }






     public OperatorDO getOperator()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getOperator();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's operator", ex);
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
            throw new webscheduleBusinessException("Error getting S_event's operator first name", ex);
        }
    }


     public String getOperatorLastname()
     throws webscheduleBusinessException
    {
    try {
    	OperatorDO operator = getOperator();
    	return operator.getLastName();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's operator last name", ex);
        }
    }


public String getOperatorEmail()
     throws webscheduleBusinessException
    {
    try {
    	OperatorDO operator = getOperator();
    	return operator.getEmail();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's operator email", ex);
        }
    }




   public String getReasondropped()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getReasondropped();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's reason dropped", ex);
        }
    }
    


    public void setTodayday (int todayday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setTodayday(todayday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's today date", ex);
        }
    }

     public void setTodaymonth (int todaymonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setTodaymonth(todaymonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's today month", ex);
        }
    }

    public void setTodayyear (int todayyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setTodayyear(todayyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's today year", ex);
        }
    }

 
 
    public void setTodayh (int todayh)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setTodayh(todayh);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's today hour", ex);
        }
    }


   public void setTodaym (int todaym)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setTodaym(todaym);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's today minute", ex);
        }
    }

    
	public void setDescription(String description)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setDescription(description);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's description", ex);
        }
    }
    
	public void setStarth(int starth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStarth(starth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's start hour", ex);
        }
    }

    public void setStartm(int startm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStartm(startm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's start min", ex);
        }
    }




    	public void setEndh(int endh)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndh(endh);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's end hour", ex);
        }
    }

    public void setEndm(int endm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndm(endm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's end min", ex);
        }
    }

     public void setEventday(int eventday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventday(eventday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's day", ex);
        }
    }

    public void setEventmonth(int eventmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventmonth(eventmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's month", ex);
        }
    }

    public void setEventyear(int eventyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventyear(eventyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's year", ex);
        }
    }


     public void setEventdayofw(int eventdayofw)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventdayofw(eventdayofw);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's day of week", ex);
        }
    }


    public void setOwner(Person owner) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOwner(PersonDO.createExisting(owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's owner", ex);
        }
    }
    
     public void setProj_owner(Project proj_owner)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProj_owner(ProjectDO.createExisting(proj_owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's project owner", ex);
        }
    }
    
    
     public void setPrevowner(Person prevowner) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPrevowner(PersonDO.createExisting(prevowner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's previous owner", ex);
        }
    }
    
     public void setPrevproj_owner(Project prevproj_owner)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPrevproj_owner(ProjectDO.createExisting(prevproj_owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's prev project owner", ex);
        }
    }


     public void setDevelop(boolean isDevelop)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsDevelop(isDevelop);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting develop event", ex);
        }
    }

     public void setRepeat(boolean isRepeat)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsRepeat(isRepeat);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting repeat event flag", ex);
        }
    }


 public void setGrab(boolean isGarb)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsGrabbable(isGarb);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting grab event flag", ex);
        }
    }
 public void setOperator(Operator operator) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOperator(OperatorDO.createExisting(operator.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's operator", ex);
        }
    }
    

public void setReasondropped(String reasondropped)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setReasondropped(reasondropped);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting S_event's reason dropped", ex);
        }
    }

    public void save() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.commit();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error saving S_event", ex);
        }
    }
    
    public void delete() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.delete();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error deleting S_event", ex);
        }
    }
}
