package webschedule.business.blocktime;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.BlocktimeDO;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;


public class Blocktimec
{
    protected BlocktimeDO myDO = null;

    public Blocktimec()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = BlocktimeDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty Blocktime", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating empty Blocktime", ex);
        }
    }

    protected Blocktimec (BlocktimeDO theBlocktime)
        throws webscheduleBusinessException
    {
        this.myDO = theBlocktime;
    }

    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting Blocktime's handle", ex);
        }
    }

    public int getStarth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStartth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Blocktime's start hour", ex);
        }
    }

     public int getStartm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStarttm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Blocktime's start min", ex);
        }
    }

     public int getEndh()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Blocktime's end hour", ex);
        }
    }

     public int getEndm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndtm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Blocktime's end min", ex);
        }
    }

     public int getDay()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDay();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Blocktime's singing date day", ex);
        }
    }

     public int getMonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getMonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Blocktime's singing month", ex);
        }
    }

      public int getYear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getYear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Blocktime's signing year", ex);
        }
    }


    public int getFlag()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getFlag();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Blocktime's flag", ex);
        }
    }

   public String getDescription ()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDescription();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Blocktime's description", ex);
        }
    }

    public void setStarth(int starth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStartth(starth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Blocktime's start hour", ex);
        }
    }

    public void setStartm(int startm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStarttm(startm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Blocktime's start min", ex);
        }
    }

     public void setEndh(int endh)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndth(endh);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Blocktime's end hour", ex);
        }
    }

    public void setEndm(int endm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndtm(endm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Blocktime's end min", ex);
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
            throw new webscheduleBusinessException("Error setting Blocktime's description", ex);
        }
    }

     public void setFlag(int flag)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setFlag(flag);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Blocktime's flag", ex);
        }
    }
     public void save()
        throws webscheduleBusinessException
    {
        try {
              System.out.println("IN the save module");
            this.myDO.commit();
            System.out.println("After commit");
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