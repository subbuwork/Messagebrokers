/**
 * 
 */
package org.rabbitmq.pubsub;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @author subbu
 *
 */
public class ReceiveLogs {

	/**
	 * @param args
	 */
	public final static String EXCHANGE_NAME = "logs";
	public static void main(String[] args) {

		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, null);
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
			channel.basicConsume(queueName,true,consumer);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
