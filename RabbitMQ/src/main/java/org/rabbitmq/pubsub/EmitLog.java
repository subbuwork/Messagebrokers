/**
 * 
 */
package org.rabbitmq.pubsub;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author subbu
 *
 */
public class EmitLog {

	/**
	 * @param args
	 */
	public final static String EXCHANGE_NAME = "logs";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectionFactory connf = new ConnectionFactory();
		connf.setHost("localhost");
		try {
			Connection connection = connf.newConnection();
			Channel channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
			
			String message = getMessage(args);
			
			channel.basicPublish(EXCHANGE_NAME, "",null,message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
			channel.close();
			connection.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
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
