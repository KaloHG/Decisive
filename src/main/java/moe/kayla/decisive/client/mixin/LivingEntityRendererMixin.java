package moe.kayla.decisive.client.mixin;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

//For the future.
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {
    /*
    @Inject(at =@At("HEAD"), method = "setupTransforms")
    public void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, CallbackInfo ci) {
        if(DecisiveClient.instance.isModEnabled()) {
            if(entity instanceof PlayerEntity) {
                Hoop.renderHoop(entity, 0.3, 0.01, tickDelta, new Hoop.FloatColor(10, 10, 10));
            }
        }
    }

     */
}
