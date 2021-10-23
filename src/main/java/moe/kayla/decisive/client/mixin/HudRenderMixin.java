package moe.kayla.decisive.client.mixin;

import moe.kayla.decisive.client.DecisiveClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This overrides certain in-game HUD elements
 */
@Mixin(InGameHud.class)
public class HudRenderMixin {

    private MatrixStack matrix;

    @Inject(at=@At("HEAD"), method = "render")
    public void render(MatrixStack matrices,float tickDelta, CallbackInfo c) {
        matrix = matrices;
    }

    @Inject(at=@At("HEAD"), method="tick")
    public void tick(CallbackInfo c) {
        //HUD hasn't been rendered yet so no matrices are available.
        if(matrix == null) { return; }
        MinecraftClient client = MinecraftClient.getInstance();
        client.textRenderer.drawWithShadow(matrix, DecisiveClient.instance.CHAT_PREFIX +  DecisiveClient.instance.getConnMessage(), 10, 10, 0);
    }
}
