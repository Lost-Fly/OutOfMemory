package com.outofmemory.game.client;

import android.os.Bundle;

import com.outofmemory.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outofmemory.game.client.ws.EventListenerCallback;
import com.outofmemory.game.client.ws.WebSocketClient;
import com.outofmemory.game.client.ws.WebSocketListener;
import com.outofmemory.game.client.ws.WsEvent;

import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class AndroidLauncher extends AndroidApplication {
    private MessageProcessor messageProcessor;
    private static final int MAX_RECONNECT_ATTEMPTS = 5;

    public void connectSocket(WebSocketClient webSocketClient) {
        int reconnectAttempts = 0;
        while (!webSocketClient.isOpen() && reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
            try {
                webSocketClient.connect();
                return;
            } catch (Exception e) {
                Gdx.app.error("ERROR SOCKET CONNECT", "Attempt " + (reconnectAttempts + 1) + " failed: " + e.getMessage());
                reconnectAttempts++;
            }
        }

        Gdx.app.error("ERROR SOCKET CONNECT", "Maximum reconnection attempts reached");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        EventListenerCallback callback = event -> {
            messageProcessor.processEvent(event);
        };

        String wsUri = "readWsUriFromProperties()";
        WebSocketClient webSocketClient = new WebSocketClient(getUri(wsUri), getWebsocketListener(callback));


        Main main = new Main();

        messageProcessor = new MessageProcessor(main);

//        main.setMessageSender(message -> {
//            webSocketClient.send(toJson(message));
//        });

        initialize(main, config);

        connectSocket(webSocketClient);

    }

    private URI getUri(String strUri) {
        try {
            return new URI(strUri);
        } catch (URISyntaxException e) {
            Gdx.app.error("CONNECTION ERROR", "INCORRECT URL: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private WebSocketListener getWebsocketListener(EventListenerCallback callback) {
        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onMessageReceived(String message) {
                Gdx.app.log("MESSAGE RECEIVED", "MESSAGE: " + message);
                WsEvent wsEvent = new WsEvent();
                wsEvent.setData(message);
                callback.onEvent(wsEvent);
            }

            @Override
            public void onConnect(ServerHandshake handshake) {
                Gdx.app.log("CONNECTION CREATED", "HTTP_STATUS: " + handshake.getHttpStatusMessage());
                WsEvent wsEvent = new WsEvent();
                wsEvent.setData("CONNECTION_OPENED");
                callback.onEvent(wsEvent);
            }

            @Override
            public void onClose(int code, String reason) {
                Gdx.app.log("CONNECTION CLOSED", "CODE: " + code + " REASON: " + reason);
                WsEvent wsEvent = new WsEvent();
                wsEvent.setData("CONNECTION_CLOSED");
                callback.onEvent(wsEvent);
            }

            @Override
            public void onError(Exception ex) {
                Gdx.app.error("CONNECTION ERROR", "ERROR_MESSAGE: " + ex.getMessage());
                WsEvent wsEvent = new WsEvent();
                wsEvent.setData("ERROR_OCCURRED");
                callback.onEvent(wsEvent);
            }
        };

        return webSocketListener;
    }

    private String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


//    private String readWsUriFromProperties() {
//        Properties properties = new Properties();
//        try {
//            InputStream inputStream = getAssets().open("env.properties");
//            properties.load(inputStream);
//            return properties.getProperty("WS_URI");
//        } catch (IOException e) {
//            Gdx.app.error("ERROR ENV", "Failed to read WS_URI from properties file: " );
////            throw new RuntimeException(e);
//        }
//    }


    public AndroidApplicationConfiguration getConfig() {
        return new AndroidApplicationConfiguration();
    }

}
