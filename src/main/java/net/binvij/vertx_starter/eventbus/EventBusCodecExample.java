package net.binvij.vertx_starter.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class EventBusCodecExample {
	
	private static final Logger LOG = LoggerFactory.getLogger(EventBusCodecExample.class);
    
	public static void main(String[] args) {
    	var vertx = Vertx.vertx();
    	
    	vertx.deployVerticle(new ProductProducer(), ar -> {
    		if(ar.failed()) {
    			LOG.error("err", ar.cause());
    		}
    		
    	});
    	vertx.deployVerticle(new ProductConsumer(),ar -> {
    		if(ar.failed()) {
    			LOG.error("err", ar.cause());
    		}
    		
    	});  	
        
    }


    public static class ProductConsumer extends AbstractVerticle {
    	
    	private static final Logger LOG = LoggerFactory.getLogger(ProductConsumer.class);
    	
        @Override
        public void start(Promise<Void> startPromise) throws Exception {
        	var eventBus = vertx.eventBus();
        	eventBus.registerDefaultCodec(Order.class, new LocalMessageCodec<>(Order.class));
        	
            eventBus.<Product>consumer(
            		ProductProducer.class.getName(),
            		message -> {
            			LOG.debug("Received message: {}", message.body());
            			//provide a reply 
            			message.reply(new Order(1));
            		}
            ).exceptionHandler(err -> {
            	LOG.debug("Error:", err);
            });
            
        	startPromise.complete();
        }
    }


    public static class ProductProducer extends AbstractVerticle {
    	
    	private static final Logger LOG = LoggerFactory.getLogger(ProductProducer.class);
        
        @Override
        public void start(Promise<Void> startPromise) throws Exception {
        	var eventBus = vertx.eventBus();
        	eventBus.registerDefaultCodec(Product.class, new LocalMessageCodec<>(Product.class));

           
            Product message = new Product(1, "Mac Book Air", 799.99);
            LOG.debug("Sending message: {}", message);
            eventBus.<Product>request(ProductProducer.class.getName(),message, reply -> {
            	if(reply.failed()) {
            		LOG.error("Failed:" + reply.cause());
            		System.out.println("failed");
            		return;
            	}
            	//if not failed 
            	LOG.debug("Response:{}", reply.result().body());
            });
            
           	startPromise.complete();
        }
    }

    
    
}
