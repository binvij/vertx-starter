package net.binvij.vertx_starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import net.binvij.vertx_starter.verticles.VerticleA;
import net.binvij.vertx_starter.verticles.VerticleB;
import net.binvij.vertx_starter.verticles.VerticleN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
    
    private final static Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
//        vertx.createHttpServer().requestHandler(req -> {
//            req.response()
//                    .putHeader("content-type", "text/plain")
//                    .end("Hello from Vert.x!");
//        }).listen(8888, http -> {
//            if (http.succeeded()) {
//                startPromise.complete();
//                System.out.println("HTTP server started on port 8888");
//            } else {
//                startPromise.fail(http.cause());
//            }
//        });
        LOG.debug("start {} ", getClass().getName());
        vertx.deployVerticle(new VerticleA());
        vertx.deployVerticle(new VerticleB());
        vertx.deployVerticle(VerticleN.class.getName(), new DeploymentOptions().setInstances(4));
        startPromise.complete();

    }
}
