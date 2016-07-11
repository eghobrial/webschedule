package webschedule.business.person;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
/*import webschedule.data.disc.DiscDO;
import webschedule.business.disc.Disc;*/
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;
        
public class Person
{
    protected PersonDO myDO = null;
    
    public Person()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = PersonDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty person", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating object ID for person", ex);
        }
    }
    
    public Person(PersonDO thePerson)
    {
        this.myDO = thePerson; 
    }
    
    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting handle for person", ex);
        }
    }
    
    public String getLogin() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getLogin();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting user's login name", ex);
        }
    }
    
    public String getPassword() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPassword();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting user's password", ex);
        }
    }
    
    public String getFirstname() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getFirstname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting user's first name", ex);
        }
    }
    
    public String getLastname() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getLastname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting user's last name", ex);
        }
    }

     public String getEmail()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEmail();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting user's email", ex);
        }
    }

    public String getOffice()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOffice();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting user's office", ex);
        }
    }


    public String getPhone()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPhone();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting user's phone", ex);
        }
    }


     public boolean isAdmin()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsAdmin();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Admin", ex);
        }
    }

     public boolean isDeveloper()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsDeveloper();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Developer", ex);
        }
    }
    
      public boolean isAuth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsAuth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Auth", ex);
        }
    }

 public boolean isPadmin()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsPadmin();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Admin", ex);
        }
    }

    public void setLogin(String login) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setLogin(login);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting user's login name", ex);
        }
    }
    
    public void setPassword(String password) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setPassword(password);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting user's password", ex);
        }
    }
    
    public void setFirstname(String firstname) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setFirstname(firstname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting user's first name", ex);
        }
    }



    public void setLastname(String lastname) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setLastname(lastname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting user's last name", ex);
        }
    }

    public void setEmail(String email)
        throws webscheduleBusinessException
    {
        try {
            myDO.setEmail(email);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting user's email", ex);
        }
    }

    public void setOffice(String office)
        throws webscheduleBusinessException
    {
        try {
            myDO.setOffice(office);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting user's office", ex);
        }
    }

    public void setPhone(String phone)
        throws webscheduleBusinessException
    {
        try {
            myDO.setPhone(phone);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting user's phone", ex);
        }
    }

     public void setAdmin(boolean isAdmin)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsAdmin(isAdmin);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting admin user", ex);
        }
    }
   public void setPadmin(boolean isPadmin)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsPadmin(isPadmin);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting proposal admin user", ex);
        }
    }

    public void setDeveloper(boolean isDeveloper)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsDeveloper(isDeveloper);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting developer user", ex);
        }
    }
public void setAuth(boolean isAuth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsAuth(isAuth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting authorized user", ex);
        }
    }

    public void save() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.commit();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error saving person", ex);
        }
    }
}
