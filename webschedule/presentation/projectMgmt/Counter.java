/**--------------------------------------------------------------
* Webschedule
*
* @class: 
* @version
* @author: Eman Ghobrial
* @since: May 2006
* counter class to be used with LinkedHashMap
*---------------------------------------------------------------------------*/

package webschedule.presentation.projectMgmt;

import java.util.*;


public class Counter {
  private int count;
  // initialize a Counter
        public Counter() {
            count = 0;
        }
    
        // increment counter
        public void bumpCount() {
            count++;
        }
    
        // retrieve the current count
        public int getCount() {
            return count;
        }
    
        // convert to a string
        public String toString() {
            return Integer.toString(count);
        }
    }
