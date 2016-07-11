package webschedule.business.downtime;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.DowntimeDO;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;


public class Downtimec
{
    protected DowntimeDO myDO = null;

    public Downtimec()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = DowntimeDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty Downtime", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating empty Downtime", ex);
        }
    }

    protected Downtimec (DowntimeDO theDowntime)
        throws webscheduleBusinessException
    {
        this.myDO = theDowntime;
    }

    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting Downtime's handle", ex);
        }
    }

    public int getStarth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStarth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Downtime's start hour", ex);
        }
    }

     public int getStartm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStartm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Downtime's start min", ex);
        }
    }

     public int getEndh()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndh();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Downtime's end hour", ex);
        }
    }

     public int getEndm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Downtime's end min", ex);
        }
    }

     public int getDay()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDay();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Downtime's singing date day", ex);
        }
    }

     public int getMonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getMonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Downtime's singing month", ex);
        }
    }

      public int getYear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getYear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Downtime's signing year", ex);
        }
    }

   public String getDescription ()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDescription();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Downtime's description", ex);
        }
    }

    public void setStarth(int starth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStarth(starth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Downtime's start hour", ex);
        }
    }

    public void setStartm(int startm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStartm(startm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Downtime's start min", ex);
        }
    }

     public void setEndh(int endh)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndh(endh);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Downtime's end hour", ex);
        }
    }

    public void setEndm(int endm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndm(endm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Downtime's end min", ex);
        }
    }



       public void setDay(int day)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setDay(day);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting downtime day", ex);
        }
    }

       public void setMonth(int month)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setMonth(month);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting downtime month", ex);
        }
    }

       public void setYear(int year)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setYear(year);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting downtime year", ex);
        }
    }

   public void setDescription(String description)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setDescription(description);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Downtime's description", ex);
        }
    }

     public void save()
        throws webscheduleBusinessException
    {
        try {
            this.myDO.commit();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error saving downtime info", ex);
        }
    }

    public void delete()
        throws webscheduleBusinessException
    {
        try {
            this.myDO.delete();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error deleting downtime info", ex);
        }
    }
}