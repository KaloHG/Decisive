package moe.kayla.decisive;

import moe.kayla.decisive.client.connection.ServerConnection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

/**
 * Mod file, for anything outside of MC such as connection shenanigans
 */
public class Decisive implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("decisive");
    //temp values
    public static final ServerConnection serverConnection = new ServerConnection(UUID.fromString("00b12f63-f4aa-4ad5-b28c-1586ca6fe295"), "This-Key-Is-Intended-For-Testing", "localhost", 27797);

    @Override
    public void onInitialize() {
        LOGGER.info("Decisive is starting...");
        LOGGER.info("Decisive is a friendly Ramiel Integration Client for Fabric 1.16.5"
             +   ", if you are not using Fabric 1.16.5 I am impressed that this message is here.");
    }
}
