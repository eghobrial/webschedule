package webschedule.business.reporttrack;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.ReporttrackDO;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;


public class Reporttrack
{
    protected ReporttrackDO myDO = null;

    public Reporttrack()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = ReporttrackDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty Reporttrack", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating empty Reporttrack", ex);
        }
    }

    protected Reporttrack (ReporttrackDO theReporttrack)
        throws webscheduleBusinessException
    {
        this.myDO = theReporttrack;
    }

    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's handle", ex);
        }
    }


 public int getDay()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDay();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's singing date day", ex);
        }
    }

     public int getMonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getMonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's singing month", ex);
        }
    }

      public int getYear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getYear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's signing year", ex);
        }
    }


    public int getStartday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStartday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's start hour", ex);
        }
    }

     public int getStartmonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStartmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's start min", ex);
        }
    }


     public int getStartyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStartyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's start min", ex);
        }
    }
    
     public int getEndday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's end hour", ex);
        }
    }

     public int getEndmonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's end min", ex);
        }
    }

   
        public int getEndyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEndyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's end min", ex);
        }
    }
 

    public int getReportinfo()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getReportinfo();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's info", ex);
        }
    }


public int getInvoice_num()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getInvoice_num();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's flag", ex);
        }
    }


   public String getPreparedby ()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPreparedby();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporttrack's description", ex);
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

    public void setStartday(int startday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStartday(startday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Reporttrack's start day", ex);
        }
    }

    public void setStartm(int startmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStartmonth(startmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Reporttrack's start month", ex);
        }
    }


 public void setStartyear(int startyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStartyear(startyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Reporttrack's start year", ex);
        }
    }
    
     public void setEndday(int endday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndday(endday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Reporttrack's end day", ex);
        }
    }

    public void setEndmonth(int endmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndmonth(endmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Reporttrack's end month", ex);
        }
    }


    public void setEndyear(int endyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setEndyear(endyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Reporttrack's end year", ex);
        }
    }

    
   public void setPreparedby(String preparedby)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPreparedby(preparedby);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Reporttrack's preparedby", ex);
        }
    }

     public void setInvoice_num(int invoice_num)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setInvoice_num(invoice_num);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Reporttrack's invoice number", ex);
        }
    }
    
    public void setReportinfo(int reportinfo)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setReportinfo(reportinfo);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Reporttrack's info", ex);
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
