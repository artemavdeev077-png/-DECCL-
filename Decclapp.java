import static spark.Spark.*;
import java.util.Date;

/**
 * #DECCL ULTRA SERVER ENGINE
 * Специально для Render + UptimeRobot + Android IDE
 */
public class Decclapp {
    public static void main(String[] args) {
        // 1. Настройка порта для Render
        String portStr = System.getenv("PORT");
        int port = (portStr != null) ? Integer.parseInt(portStr) : 4567;
        port(port);

        System.out.println("--- #DECCL SYSTEM STARTING ---");
        System.out.println("PORT: " + port);

        // 2. Главная страница (то, что увидит UptimeRobot)
        get("/", (req, res) -> {
            res.type("text/html");
            return "<html><head><title>#DECCL CORE</title></head>" +
                   "<body style='background:#050505; color:#00FF80; font-family:sans-serif; text-align:center; padding-top:100px;'>" +
                   "<h1 style='font-size:50px;'>#DECCL IS ONLINE</h1>" +
                   "<p style='color:#fff;'>Server Status: Synchronized</p>" +
                   "<p style='color:#555;'>Last Pulse: " + new Date() + "</p>" +
                   "</body></html>";
        });

        // 3. API точка для твоего будущего приложения в Android IDE
        get("/api/status", (req, res) -> {
            res.type("application/json");
            return "{\"status\":\"active\", \"engine\":\"Java\", \"parallax\":\"ready\"}";
        });

        // 4. Лог в консоль Render
        System.out.println("--- SERVER IS LIVE AND BREATHING ---");
    }
}
