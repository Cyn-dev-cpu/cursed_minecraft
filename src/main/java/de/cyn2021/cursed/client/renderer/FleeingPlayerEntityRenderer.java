package de.cyn2021.cursed.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cyn2021.cursed.Cursed;
import de.cyn2021.cursed.client.model.SadAppleEntityModel;
import de.cyn2021.cursed.entity.FleeingPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FleeingPlayerEntityRenderer extends MobRenderer<FleeingPlayerEntity, SadAppleEntityModel<FleeingPlayerEntity>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Cursed.MOD_ID, "textures/entity/sad_apple.png");

    public FleeingPlayerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SadAppleEntityModel<>(context.bakeLayer(SadAppleEntityModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(FleeingPlayerEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(FleeingPlayerEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.0F, 1.0F, 1.0F);
        super.scale(entity, poseStack, partialTick);
    }
}