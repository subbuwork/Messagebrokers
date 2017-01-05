/**
 * 
 */
package org.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author subbu
 *
 */
public class EmitLogDirect {

	/**
	 * @param args
	 */
	private final static String EXCHANGE_NAME="direct_logs";
	
	public static void main(String[] args) {
      try {
    	  
    	  ConnectionFactory factory = new ConnectionFactory();
    	  factory.setHost("localhost");
    	  Connection connection = factory.newConnection();
    	  
    	  Channel channel = connection.createChannel();
    	  channel.exchangeDeclare(EXCHANGE_NAME, "direct");
    	  //String queueName = channel.queueDeclare().getQueue();
    	  String severity = getSeverity(args);
    	  String message = getMessage(args);
    	  System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
    	  channel.basicPublish(EXCHANGE_NAME, severity, null,message.getBytes("UTF-8"));
    	  
          channel.close();
          connection.close();
	} catch (Exception e) {
		// TODO: handle exception
	}
		
		
		
		
	}
	private static String getSeverity(String[] strings){
	    if (strings.length < 1)
	    	    return "info";
	    return strings[0];
	  }
	public static String getMessage(String[] strings){
		System.out.println("MEssage from args::"+strings);
		if(strings.length<2)
			return "Hellow world!";
		return joinStrings(strings,"",1);	
		
	}
	private static String joinStrings(String[] strings, String delimiter, int startIndex) {
	    int length = strings.length;
	    if (length == 0 ) return "";
	    if (length < startIndex ) return "";
	    StringBuilder words = new StringBuilder(strings[startIndex]);
	    for (int i = startIndex + 1; i < length; i++) {
	        words.append(delimiter).append(strings[i]);
	    }
	    return words.toString();
	  }
}
