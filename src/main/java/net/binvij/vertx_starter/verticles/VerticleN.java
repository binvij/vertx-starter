
package net.binvij.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author binvij
 */
public class VerticleN extends AbstractVerticle{
        private final static Logger LOG = LoggerFactory.getLogger(VerticleN.class);


    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        LOG.debug("start " + getClass().getName() + " on thread " + Thread.currentThread().getName() );
        startPromise.complete();
    }
    
}
