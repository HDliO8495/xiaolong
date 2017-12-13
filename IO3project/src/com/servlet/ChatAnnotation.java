package com.servlet;

        import java.io.IOException;
        import java.util.Set;
        import java.util.concurrent.CopyOnWriteArraySet;
        import java.util.logging.Logger;

        import javax.websocket.OnClose;
        import javax.websocket.OnMessage;
        import javax.websocket.OnOpen;
        import javax.websocket.Session;
        import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chatServlet")
public class ChatAnnotation {

    private Session session;
    private Logger logger = Logger.getLogger(ChatAnnotation.class.getName());
    private static final Set<ChatAnnotation> connections =
            new CopyOnWriteArraySet<ChatAnnotation>();

    @OnOpen
    public void open(Session session) {
        this.session = session;
        connections.add(this);
        logger.info("*** WebSocket opened from sessionId " + session.getId());
    }

    @OnMessage
    public void inMessage(String message) {
        String countMsg = connections.size() + "人同时在线";
        int startIndex = message.indexOf("!@#$%");
        String nikeName = message.substring(0, startIndex);
        String textMsg = message.substring(startIndex + 5);
        String msg1 = nikeName + ": " + textMsg;
//        String msg2 = "我: " + textMsg;
        logger.info("***"+countMsg+"  WebSocket Received from sessionId " + this.session.getId() + ": " + msg1);
        broadcast(msg1);
    }

    @OnClose
    public void end() {
        logger.info("*** WebSocket closed from sessionId " + this.session.getId());
        connections.remove(this);
    }

    private  void broadcast(String msg) {
        for (ChatAnnotation client : connections) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                logger.info("Chat Error: Failed to send message to client");
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
//                String message = String.format("* %s %s",
//                        client.nickname, "has been disconnected.");
//                broadcast(message);
            }
        }
    }

}
