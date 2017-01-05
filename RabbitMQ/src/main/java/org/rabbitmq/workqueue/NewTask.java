/**
 * 
 */
package org.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author subbu
 *
 */
public class NewTask {

	/**
	 * @param args
	 */
	private final static String QUEUE_NAME = "test1";
	public static void main(String[] args) {
		try{
      ConnectionFactory connectionFactory = new ConnectionFactory();
      connectionFactory.setHost("localhost");
      Connection connection = connectionFactory.newConnection();
	  Channel channel = connection.createChannel();	
	  String message = getMessage(args);
	  System.out.println(" [x] Sent '" + message + "'");
	  channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
	  
	  channel.close();
	  connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getMessage(String[] strings){
		System.out.println("MEssage from args::"+strings);
		if(strings.length<1)
			return "Hellow world!";
		return joinStrings(strings,"");	
		
	}
	public static String joinStrings(String[] string,String delimiter){
		int length = string.length;
		if(length == 0)return "";
			StringBuilder words = new StringBuilder(string[0]);
			for(int i = 0; i<length;i++){
				words.append(delimiter).append(string[i]);
			}
			
		System.out.println("Words:::"+words);
		return words.toString();
	}

}
