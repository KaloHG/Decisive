package moe.kayla.decisive.client.connection;

import moe.kayla.decisive.Decisive;
import moe.kayla.decisive.client.DecisiveClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;
import net.theprism.events2.Receiver;
import net.theprism.ramiel.api.client.RamielAPIDisconnectEvent;
import net.theprism.ramiel.api.client.RamielAPIReadyEvent;

public class ServerListener {

    @Receiver(source = "RamielAPI")
    public void onRamielAPIReady(RamielAPIReadyEvent event) {
        DecisiveClient.instance.sendClientMessage(DecisiveClient.instance.CHAT_PREFIX + Formatting.GREEN + "Ramiel successfully connected!");
        Decisive.serverConnection.sendServerMessage(event.getUsername() + " has connected to Ramiel via Decisive");
    }

    @Receiver(source = "RamielAPI")
    public void onRamielAPIDisconnect(RamielAPIDisconnectEvent event) {
        DecisiveClient.instance.sendClientMessage(DecisiveClient.instance.CHAT_PREFIX + Formatting.RED + "Disconnected from Ramiel server...");
    }
}
