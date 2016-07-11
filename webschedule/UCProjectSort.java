/**--------------------------------------------------------------
* webschedule
*
* @class: UCProjectSort
* @version
* @author   Eman Ghobrial
* @since  August 2009
*
*--------------------------------------------------------------*/

package webschedule;

import java.util.*;
import java.sql.Timestamp;


public class UCProjectSort implements Comparable{

  public String lastName;
  public String firstName;
  public String name;
  public String pid;
  public String indexnum;
 public String irbnum;
  public java.sql.Date iexpdate ;
  
  
   //-----------------------------------------------------------
  public UCProjectSort(String last, String first, String pname, String id, String pindexnum,String pirbnum, java.sql.Date piexpdate )
   {                // constructor
   this.lastName = last;
   this.firstName = first;
   this.name = pname;
   this.pid = id;
   this.indexnum = pindexnum;
   this.irbnum = pirbnum;
   this.iexpdate = piexpdate;
   }
  //-----------------------------------------------------------

 //-----------------------------------------------------------
  public String getLastName()      // get last name
   { return lastName; }
   //-----------------------------------------------------------
  public String getFirstName()      // get first name
   { return firstName; }   
  //-----------------------------------------------------------
  public String getName()      // get project name
   { return name; } 
   //-----------------------------------------------------------
  public String getPid()      // get project id
   { return pid; }
   //-----------------------------------------------------------
  public String getIndexnum()      // get index num
   { return indexnum; }
//-----------------------------------------------------------
  public String getIrbnum()      // get irb num
   { return irbnum; }
   //-----------------------------------------------------------
  public java.sql.Date getIexpdate()      // get IRB exp date
   { return iexpdate; }
//--------------------------------------------------------------

 /* Overload compareTo method */
                       
  public int compareTo(Object obj)
  {
  
  final int BEFORE = -1;
    final int EQUAL = 0;
    final int AFTER = 1;

    UCProjectSort tmp = (UCProjectSort)obj;
  
   int comparison = this.lastName.compareTo(tmp.lastName);
    if ( comparison != EQUAL ) return comparison;

//    if(this.lastName < tmp.lastName)
 //   {
  //    /* instance lt received */
   //   return -1;
   // }   
   // else if(this.lastName > tmp.lastName)
   // {
    //  /* instance gt received */        
    //  return 1;
   // }
   // /* instance == received */
   // return 0;  
 

return EQUAL;
}

     } // end class UCProjectSort
////////////////////////////////////////////////////////////////
