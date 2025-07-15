package de.cyn2021.cursed.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cyn2021.cursed.Cursed;
import de.cyn2021.cursed.client.model.SadAppleEntityModel;
import de.cyn2021.cursed.SadAppleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SadAppleEntityRenderer extends MobRenderer<SadAppleEntity, SadAppleEntityModel<SadAppleEntity>> {
    public SadAppleEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SadAppleEntityModel<>(context.bakeLayer(SadAppleEntityModel.LAYER_LOCATION)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(SadAppleEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Cursed.MOD_ID, "textures/entity/sad_apple.png");
    }

    @Override
    protected void scale(SadAppleEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(0.7F, 0.7F, 0.7F);
        super.scale(entity, poseStack, partialTick);
    }
}