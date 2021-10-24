package moe.kayla.decisive.client;

import moe.kayla.decisive.Decisive;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

/**
 * Client File, handles all of the actual client-related stuff like rendering, in-game messages, etc.
 */
public class DecisiveClient implements ClientModInitializer {
    private static KeyBinding modBind;
    private boolean modEnabled = false;
    private long delayToReconnMs = 10000L;
    private long lastReconn = 0L;
    public static DecisiveClient instance;
    public final String CHAT_PREFIX = Formatting.DARK_GRAY + "[" + Formatting.GOLD + "Decisive" + Formatting.DARK_GRAY + "] ";

    @Override
    public void onInitializeClient() {
        instance = this;
        modBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle Mod", GLFW.GLFW_KEY_RIGHT_BRACKET, "Decisive"));
        modEnabled = true;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            //This should be ran at any time.
            while(modBind.wasPressed()) {
                if(modEnabled) {
                    client.player.sendMessage(new LiteralText(Formatting.DARK_GRAY + "[" + Formatting.GOLD + "Decisive" + Formatting.DARK_GRAY + "] " + Formatting.RED + Formatting.ITALIC + "Mod Disabled"), false);
                    //Close the connection since we are disabling the mod.
                    Decisive.serverConnection.closeConnection();
                    modEnabled = false;
                } else {
                    client.player.sendMessage(new LiteralText(Formatting.DARK_GRAY + "[" + Formatting.GOLD + "Decisive" + Formatting.DARK_GRAY + "] " + Formatting.GREEN + Formatting.ITALIC + "Mod Enabled"), false);
                    modEnabled = true;
                }
            }
            //Returns all if mod is disabled, no reason to connect afterward.
            if(!modEnabled) { return; }
            //Checks if there is no connection and the last connection hasn't been accomplished yet
            /* Disabled Momentarily
            if(client.getNetworkHandler().getConnection() == null) {
                return;
            } else {
                String ip = client.getNetworkHandler().getConnection().getAddress().toString();
                ip = ip.split(":")[0];
                if(!ip.equals(Decisive.allowedServerIp)) {
                    return;
                }
            }
             */
            if((!Decisive.serverConnection.isConnected()) && canReconnect()) {
                if(client.player == null) { return; } //don't connect if not loaded in.
                Decisive.LOGGER.info("Trying to connect to Ramiel.");
                client.player.sendMessage(new LiteralText(CHAT_PREFIX + Formatting.GRAY + "Attempting to connect to Ramiel"), false);
                Decisive.serverConnection.startConnection();
                lastReconn = System.currentTimeMillis();
            } else if(Decisive.serverConnection.isConnected()) {
                if (client.player == null) {
                    Decisive.serverConnection.closeConnection();
                }
            }
        });
    }

    public String getConnMessage() {
        if(Decisive.serverConnection.isConnected()) {
            return Formatting.GREEN + "Connected.";
        } else {
            return Formatting.RED + "Disconnected.";
        }
    }

    private boolean canReconnect() {
        long timeElapsed = System.currentTimeMillis() - lastReconn;
        return timeElapsed > delayToReconnMs;
    }

    public String getChatPrefix() { return CHAT_PREFIX; }

    public void sendClientMessage(String message) {
        if(MinecraftClient.getInstance().player == null) { return; }
        MinecraftClient.getInstance().player.sendMessage(new LiteralText(message), false);
    }

    /**
     * Mod-Enabled boolean
     * @return whether the mod is enabled or not.
     */
    public boolean isModEnabled() { return modEnabled; }
}
