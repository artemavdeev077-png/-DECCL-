import static spark.Spark.*;
import com.google.gson.Gson;
import java.util.*;

/**
 * #DECCL ULTIMATE BACKEND ENGINE - JAVA PRO
 * Предназначено для Render.com + UptimeRobot
 */
public class Decclapp {
    private static final Gson gson = new Gson();
    // Имитация базы данных пользователей
    private static Map<String, String> users = new HashMap<>();

    public static void main(String[] args) {
        // Настройка порта под Render
        String portStr = System.getenv("PORT");
        int port = (portStr != null) ? Integer.parseInt(portStr) : 4567;
        port(port);

        System.out.println(">>> [SYSTEM] #DECCL CORE BOOTING...");
        
        // 1. Мониторинг для UptimeRobot (HTML страница)
        get("/", (req, res) -> {
            res.type("text/html");
            return "<html><head><meta charset='UTF-8'><title>#DECCL DASHBOARD</title></head>" +
                   "<body style='background:#0a0a0a; color:#00FF80; font-family:monospace; padding:40px;'>" +
                   "<div style='border:2px solid #00FF80; border-radius:15px; padding:20px; display:inline-block;'>" +
                   "<h1 style='text-shadow: 0 0 10px #00FF80;'>#DECCL ENGINE v3.0</h1>" +
                   "<p style='color:#fff;'>STATUS: <span style='color:#00FF80;'>ONLINE</span></p>" +
                   "<p style='color:#fff;'>UPTIME ROBOT: <span style='color:#00FF80;'>SYNCHRONIZED</span></p>" +
                   "<hr style='border:1px solid #333;'>" +
                   "<p style='color:#888;'>ACTIVE USERS: " + users.size() + "</p>" +
                   "<p style='color:#444;'>SERVER TIME: " + new Date() + "</p>" +
                   "</div></body></html>";
        });

        // 2. API для входа (JSON)
        post("/api/auth", (req, res) -> {
            res.type("application/json");
            Map<String, String> data = gson.fromJson(req.body(), HashMap.class);
            String nickname = data.get("nickname");
            String email = data.get("email");

            if (nickname != null && !nickname.isEmpty()) {
                users.put(nickname, email);
                System.out.println(">>> [AUTH] User " + nickname + " joined the system.");
                return "{\"status\":\"success\", \"message\":\"Authorized in #DECCL\"}";
            } else {
                res.status(400);
                return "{\"status\":\"error\", \"message\":\"Nickname is required\"}";
            }
        });

        // 3. API для проверки статуса системы
        get("/api/ping", (req, res) -> {
            res.type("application/json");
            return "{\"pong\": true, \"timestamp\":" + System.currentTimeMillis() + "}";
        });

        // Обработка ошибок 404
        notFound((req, res) -> {
            res.type("application/json");
            return "{\"error\":\"Resource not found\", \"system\":\"#DECCL\"}";
        });

        System.out.println(">>> [SUCCESS] #DECCL SERVER IS LIVE ON PORT " + port);
    }
}
