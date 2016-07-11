/**--------------------------------------------------------------
* webschedule
*
* @class: SendMailLL
* @version
* @author   Eman Ghobrial
* @since  May 2001
*
*--------------------------------------------------------------*/

package webschedule;

import java.io.*;
import java.net.*;
import java.util.LinkedList;


public class SendMailLL {
	private String from;
	private String to;
	private String subject;
	private LinkedList messagebody;
	
	public SendMailLL (String from, String to, String subject, LinkedList messagebody)
		{
		try {	
			int i;
		  	// Establish a network connection for sending mail
      			URL u = new URL("mailto:" + to);      // Create a mailto: URL
      			URLConnection c = u.openConnection(); // Create a URLConnection for it
      			c.setDoInput(false);                  // Specify no input from this URL
      			c.setDoOutput(true);                  // Specify we'll do output
      			System.out.println("Connecting...");  // Tell the user what's happening
      			System.out.flush();                   // Tell them right now
      			c.connect();                          // Connect to mail host
			PrintWriter out =                     // Get output stream to mail host
			        new PrintWriter(new OutputStreamWriter(c.getOutputStream()));

      			// Write out mail headers.  Don't let users fake the From address
      			out.println("From: \"" + from + "\" <" +
                  		System.getProperty("user.name") + "@" +
                  		InetAddress.getLocalHost().getHostName() + ">");
		        out.println("To: " + to);
			out.println("Subject: " + subject);
		        out.println();  // blank line to end the list of headers


    			// Read message line by line and send it out.
		
                       for(i = 0; i < messagebody.size(); i++) {
                        Object line = messagebody.get(i);
                       out.println((String) line);
		      }

		      // Close the stream to terminate the message
		      out.close();
      		     // Tell the user it was successfully sent.
		      System.out.println("Message sent.");
		      System.out.flush();
	       	           }
      catch (Exception e) {  // Handle any exceptions, print error message.
      System.err.println(e);
         }
     }	
   }