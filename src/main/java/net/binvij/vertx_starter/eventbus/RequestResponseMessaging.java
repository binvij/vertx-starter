package net.binvij.vertx_starter.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class RequestResponseMessaging {
	
	static final String ADDRESS = "net.binvij.request";
	private static final Logger LOG = LoggerFactory.getLogger(RequestResponseMessaging.class);
	
	public static void main(String[] args) {
		LOG.debug("main()");
		var vertx = Vertx.vertx();
		vertx.deployVerticle(new RequestVerticle());
		vertx.deployVerticle(new ResponseVerticle());
		LOG.debug("finished!");

	}
	
	
	static class RequestVerticle extends AbstractVerticle {
		
		private static final Logger log = LoggerFactory.getLogger(RequestVerticle.class);
		
		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			System.out.println("complete");
			String message = "hello world";
			log.debug("sending {}", message);
			var eventBus = vertx.eventBus();
			eventBus.<String>request(ADDRESS, message, reply -> {
				log.debug("Response {}", reply.result().body());
			});
			
		}
	}
	
	
	static class ResponseVerticle extends AbstractVerticle {
		
		private static final Logger log = LoggerFactory.getLogger(ResponseVerticle.class);
		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			
			startPromise.complete();
			
			vertx.eventBus().<String>consumer(ADDRESS,message -> {
				log.debug("received:" + message.body());
				message.reply("Received your message, thanks!");
			});
			
			
		}
	}

}







