/**
 * 
 */
package org.rabbitmq.routing;

import java.io.IOException;

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
public class ReceiveLogsDirect {

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
			String queueName = channel.queueDeclare().getQueue();
			System.out.println("Queue Name:::"+queueName);
			
			if(args.length<1){
				System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
			      System.exit(1);
			}
			for(String severity:args){
				channel.queueBind(queueName, EXCHANGE_NAME, severity);
			}
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			Consumer consumer = new DefaultConsumer(channel){
				@Override
				public void handleDelivery(String consumerTag,
						Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body,"UTF-8");
					 System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
				}
				
			};
			
			channel.basicConsume(queueName,true,consumer);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
