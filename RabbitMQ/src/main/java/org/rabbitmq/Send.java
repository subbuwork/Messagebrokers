package org.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

public class Send {
	private final static String QUEUE_NAME="test1";
	
	public static void main(String[] args) {
		
		
		try {
			ConnectionFactory connectionFactory = new ConnectionFactory();
			connectionFactory.setHost("localhost");
			Connection connection = connectionFactory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false,null);
			String message = "Hello Rabbit MQ...4";
			channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
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
	
	

}
