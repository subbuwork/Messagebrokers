/**
 * 
 */
package org.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * @author subbu
 *
 */
public class Receive {

	/**
	 * @param args
	 */
	private final static String QUEUE_NAME="test1";
	public static void main(String[] args) {

		
		try {
			ConnectionFactory connectionFactory  = new ConnectionFactory();
			connectionFactory.setHost("localhost");
			Connection connection = connectionFactory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME,false,false,false,null);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			Consumer consumer = new DefaultConsumer(channel){
				@Override
				public void handleDelivery(String consumerTag,
						Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					
					String message = new String(body,"UTF-8");
					System.out.println(" [x] Received '" + message + "'");
			
				}
			};
			
			channel.basicConsume(QUEUE_NAME,true,consumer);
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
