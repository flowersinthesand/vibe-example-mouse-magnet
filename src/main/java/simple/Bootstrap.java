package simple;

import org.atmosphere.vibe.Action;
import org.atmosphere.vibe.atmosphere.AtmosphereBridge;
import org.atmosphere.vibe.runtime.DefaultServer;
import org.atmosphere.vibe.runtime.Server;
import org.atmosphere.vibe.runtime.Socket;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Bootstrap implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        final Server server = new DefaultServer();
        server.socketAction(new Action<Socket>() {
            @Override
            public void on(final Socket socket) {
                socket.on("echo", new Action<Map<String, Object>>() {
                    @Override
                    public void on(Map<String, Object> data) {
                        socket.send("echo", data);
                    }
                });
            }
        });
        
        new AtmosphereBridge(event.getServletContext(), "/vibe").httpAction(server.httpAction()).websocketAction(server.websocketAction());
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
