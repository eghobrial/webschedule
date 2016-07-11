package webschedule.business.c_event;

import webschedule.business.webscheduleBusinessException;
import webschedule.business.person.Person;
import webschedule.business.project.Project;
/*import webschedule.data.*;*/
import webschedule.data.PersonDO;
import webschedule.data.ProjectDO;
import webschedule.data.C_eventDO;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;
import java.sql.Date;
     
public class C_event
{
    protected C_eventDO myDO = null;
         
    public C_event()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = C_eventDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty C_event", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating empty C_event", ex);
        }
    }
    
    protected C_event(C_eventDO theC_event)
        throws webscheduleBusinessException
    {
        this.myDO = theC_event;
    }
    
    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting C_event's handle", ex);
        }
    }
    
    public int getTodayd()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTodayd();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's singing date day", ex);
        }
    }

    public int getTodaym()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTodaym();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's scheduling date month", ex);
        }
    }

    public int getTodayy()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTodayy();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's scheduling date year", ex);
        }
    }


 public int getTodayh()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTodayh();
        } catch(DataObjectException ex) {
         throw new webscheduleBusinessException("Error getting C_event's singing date hour", ex);
        }
    }

    public int getTodaymin()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTodaymin();
        } catch(DataObjectException ex) {
       throw new webscheduleBusinessException("Error getting C_event's scheduling date min", ex);
        }
    }

    
    public int getStarth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStarth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's start hour", ex);
        }
    }

         public int getStartm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStartm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's start min", ex);
        }
    }

     public int getEndh()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndh();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's end hour", ex);
        }
    }

         public int getEndm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's end min", ex);
        }
    }

     public int getEventday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's event day", ex);
        }
    }


    public int getEventm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's event month", ex);
        }
    }


    public int getEventy()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEventy();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's event year", ex);
        }
    }

    public int getCancelc()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCancelc();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's event cancel code", ex);
        }
    }


     public PersonDO getOwner()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getOwner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's owner", ex);
        }
    }

    public String getOwnerFirstname()
     throws webscheduleBusinessException
    {
    try {
    	PersonDO owner = getOwner();
    	return owner.getFirstname();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's owner first name", ex);
        }
    }


     public String getOwnerLastname()
     throws webscheduleBusinessException
    {
    try {
    	PersonDO owner = getOwner();
    	return owner.getLastname();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's owner last name", ex);
        }
    }


     public ProjectDO getProj_owner()
        throws webscheduleBusinessException
    {
        try {

            return myDO.getProj_owner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's project owner", ex);
        }
    }


    public String getProj_owner_name()
     throws webscheduleBusinessException
    {
    try {
    	ProjectDO proj_owner = getProj_owner();
    	return proj_owner.getProj_name();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's Project name", ex);
        }
    }


 /*   public String getProjectID()
     throws webscheduleBusinessException
    {
    try {
    	ProjectDO proj_owner = getProj_owner();
    	return proj_owner.getProjectID();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting C_event's Project ID", ex);
        }
    }
   */
    public void setTodayd (int todayd)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setTodayd(todayd);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's today date", ex);
        }
    }

     public void setTodaym (int todaym)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setTodaym(todaym);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's today month", ex);
        }
    }

    public void setTodayy (int todayy)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setTodayy(todayy);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's today year", ex);
        }
    }

 public void setTodayh (int todayh)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setTodayh(todayh);
        } catch(DataObjectException ex) {
         throw new webscheduleBusinessException("Error setting C_event's today hour", ex);
        }
    }

     public void setTodaymin (int todaymin)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setTodaym(todaymin);
        } catch(DataObjectException ex) {
        throw new webscheduleBusinessException("Error setting C_event's today min", ex);
        }
    }

      public void setCancelc (int cancelcode)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setCancelc(cancelcode);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's today year", ex);
        }
    }

	
    
	public void setStarth(int starth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStarth(starth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's start hour", ex);
        }
    }

    public void setStartm(int startm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStartm(startm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's start min", ex);
        }
    }




    	public void setEndh(int endh)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndh(endh);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's end hour", ex);
        }
    }

    public void setEndm(int endm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndm(endm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's end min", ex);
        }
    }

     public void setEventday(int eventday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventday(eventday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's day", ex);
        }
    }

    public void setEventm(int eventmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventm(eventmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's month", ex);
        }
    }

    public void setEventy(int eventyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEventy(eventyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's year", ex);
        }
    }





    public void setOwner(Person owner) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOwner(PersonDO.createExisting(owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's owner", ex);
        }
    }
    
     public void setProj_owner(Project proj_owner)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProj_owner(ProjectDO.createExisting(proj_owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting C_event's project owner", ex);
        }
    }



    public void save() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.commit();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error saving C_event", ex);
        }
    }
    
    public void delete() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.delete();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error deleting C_event", ex);
        }
    }
}
