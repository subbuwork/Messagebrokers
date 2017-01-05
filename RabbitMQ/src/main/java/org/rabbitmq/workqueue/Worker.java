/**
 * 
 */
package org.rabbitmq.workqueue;

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
public class Worker {

	/**
	 * @param args
	 */
	public final static String QUEUE_NAME="test1";
	
	public static void main(String[] args) throws IOException, TimeoutException {
        
		ConnectionFactory connf = new ConnectionFactory();
		connf.setHost("localhost");
		Connection connection = connf.newConnection();
		Channel channel = connection.createChannel();
		final Consumer consumer = new DefaultConsumer(channel){
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body,"UTF-8");
				System.out.println(" [x] Received '" + message + "'");
				try{
					doWork(message);
			
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					System.out.println(" [x] Done");
				}
		
			}
			
	
		};
		boolean autoAck = true;
		channel.basicConsume(QUEUE_NAME,autoAck,consumer);
	}
	
	public static void doWork(String task)throws InterruptedException{
		for(char ch: task.toCharArray()){
			if(ch=='.')Thread.sleep(1000);
		}
	}

}
