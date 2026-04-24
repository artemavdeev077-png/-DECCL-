import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;

public class Decclapp {
    public static void main(String[] args) throws IOException {
        // Render дает порт через переменную окружения
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Главная страница для UptimeRobot
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange t) throws IOException {
                String response = "<html><body style='background:#000;color:#00FF80;font-family:monospace;text-align:center;padding:50px;'>" +
                                  "<h1>#DECCL CORE v4.0 ONLINE</h1>" +
                                  "<p style='color:#fff;'>SYSTEM STATUS: <span style='color:#00FF80;'>ACTIVE</span></p>" +
                                  "<p style='color:#555;'>PULSE TIME: " + new Date() + "</p>" +
                                  "</body></html>";
                t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                t.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        // API для проверки
        server.createContext("/ping", t -> {
            String resp = "{\"status\":\"alive\", \"engine\":\"pure_java\"}";
            t.getResponseHeaders().set("Content-Type", "application/json");
            t.sendResponseHeaders(200, resp.length());
            t.getResponseBody().write(resp.getBytes());
            t.getResponseBody().close();
        });

        System.out.println(">>> #DECCL SERVER STARTED ON PORT: " + port);
        server.start();
    }
}
