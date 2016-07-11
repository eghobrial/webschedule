/**--------------------------------------------------------------
* webschedule
*
* @class: MailClass
* @version
* @author   Eman Ghobrial
* @since  Dec 2000
*
*--------------------------------------------------------------*/

package webschedule;

import java.io.*;
import java.net.*;


public class SendMail {
	private String from;
	private String to;
	private String subject;
	private String[] messagebody;
	
	public SendMail (String from, String to, String subject, String[] messagebody)
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
		
                       for(i = 0; i < messagebody.length; i++) {
                       out.println(messagebody[i]);
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