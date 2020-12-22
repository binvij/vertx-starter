package net.binvij.vertx_starter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExample {
	
	private static final Logger log = LoggerFactory.getLogger(FuturePromiseExample.class);
	
	@Test
	void promise_success(Vertx vertx, VertxTestContext context) {
		final Promise<String> promise = Promise.promise();
		log.debug("Start");
		
		vertx.setTimer(500, id -> {
			promise.complete("SUCCESS");
			log.debug("promise complete");
			context.completeNow();
		});
		
		log.debug("End");	
	}
	
	@Test 
	void promise_failure(Vertx vertx, VertxTestContext context) {
		final Promise<String> promise = Promise.promise();
		log.debug("Start");
		vertx.setTimer(500, id -> {
			promise.fail(new RuntimeException("failed"));
			log.debug("failed");
			context.completeNow();
		});
		
		log.debug("End");
		
	}
	
	@Test
	void future_success(Vertx vertx, VertxTestContext context) {
		final Promise<String> promise = Promise.promise();
		log.debug("Start");
		vertx.setTimer(500, id -> {
			promise.complete("returned from promise.");
			log.debug("promise complete.");
			//context.completeNow();
		});
		
		var future = promise.future();
		future
		.onSuccess(result -> {
			log.debug("future result:{}", result);
			context.completeNow();
		})
		.onFailure(context::failNow);
		
		log.debug("End");	

	}
	

	@Test
	void future_failure(Vertx vertx, VertxTestContext context) {
		final Promise<String> promise = Promise.promise();
		log.debug("Start");
		vertx.setTimer(500, id -> {
			promise.fail(new RuntimeException("failedRuntimeException!"));
			log.debug("promise complete.");
		});
		
		var future = promise.future();
		future
		.onSuccess(context::failNow)
		.onFailure(error -> {
			log.debug("onFailure:", error);
			context.completeNow();
		});
		
		log.debug("End");	
		
		
	}
	
	
	
	

}
