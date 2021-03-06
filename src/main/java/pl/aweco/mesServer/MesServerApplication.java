package pl.aweco.mesServer;


import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.http.server.HttpServer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


public class MesServerApplication {

	public static void main(String[] args) {
		RouterFunction route = route( GET("/"),
				request -> {
				String welcome = "HAllo na stronie aweco";
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
				String time = "\nCzas to " + now.format(myFormatter);
				return ServerResponse.ok().body(fromObject(welcome + time));
				});

		HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
		HttpServer server = HttpServer.create("localhost", 8080);
		ReactorHttpHandlerAdapter myReactorHandler = new ReactorHttpHandlerAdapter(httpHandler);
		server.startAndAwait(myReactorHandler);

	}
}
