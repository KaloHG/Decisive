package moe.kayla.decisive.client.mixin;

import moe.kayla.decisive.Decisive;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.theprism.ramiel.api.utils.RegexResult;
import net.theprism.ramiel.api.utils.RegexUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

/**
 * This Mixin effectively serves as a chat listener ...
 */
@Mixin(InGameHud.class)
public class ChatHandlerMixin {
    @Inject(at = @At("HEAD"), method = "addChatMessage")
    private void addChatMessage(MessageType type, Text text, UUID sender, CallbackInfo info) {
        String formattedText = text.getString();
        String rawText = Formatting.strip(formattedText);

        Decisive.LOGGER.info("Parsing new regex message, raw/unformatted: " +rawText);
        RegexResult regResult = RegexUtils.parseMessage(rawText);
        //send hit
        if(regResult.getType().equals(net.theprism.ramiel.api.utils.MessageType.SNITCH_HIT)) {
            Decisive.LOGGER.info("Parsed valid, sending to server.");
            Decisive.serverConnection.sendSnitchMessage(regResult.getMatchResult());
        }
    }
}
