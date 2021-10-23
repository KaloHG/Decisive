package moe.kayla.decisive.client.connection;

import moe.kayla.decisive.Decisive;
import net.theprism.ramiel.api.client.RamielConnection;
import net.theprism.ramiel.api.common.ClientType;

import java.net.UnknownHostException;
import java.util.UUID;
import java.util.regex.MatchResult;

public class ServerConnection {
    //Server Creds
    private UUID clientUuid;
    private String clientKey;
    private String serverHost;
    private int serverPort;

    private RamielConnection connection;

    /**
     * Server Connection
     * @param uuid - Client UUID
     * @param key - Client Key
     * @param host - Ramiel Server Host
     * @param port - Ramiel Server Port
     */
    public ServerConnection(UUID uuid, String key, String host, int port) {
        clientUuid = uuid;
        clientKey = key;
        serverHost = host;
        serverPort = port;

        try {
            connection = new RamielConnection(ClientType.MINECRAFT, clientUuid, clientKey, serverHost, serverPort);
        } catch(UnknownHostException ex) {
            Decisive.LOGGER.error("Unable to connect to Ramiel Server... Unknown host: " + ex.getMessage());
            ex.printStackTrace();
        }
        connection.addListener2(ServerListener.class);
    }

    /**
     * Starts Ramiel Session
     */
    public void startConnection() {
        connection.start();
    }

    public void sendServerMessage(String message) {
        if(connection.isConnected()) {
            connection.sendMessage(message);
        }
    }

    /**
     * Closes the Ramiel Session.
     */
    public void closeConnection() {
        connection.disconnect();
    }

    public void sendSnitchMessage(MatchResult result) {
        connection.sendSnitchHit(result);
    }

    public boolean isConnected() { return connection.isConnected(); }
}
