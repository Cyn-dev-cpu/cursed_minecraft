package de.cyn2021.cursed.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.cyn2021.cursed.*;
import de.cyn2021.cursed.client.model.SadAppleEntityModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemDisplayContext;

public class SadAppleItemRenderer extends BlockEntityWithoutLevelRenderer {
    private final SadAppleEntityModel<?> model;
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("cursed", "sad_apple_entity"), "main"
    );



    public ResourceLocation getTextureLocation(SadAppleEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Cursed.MOD_ID, "textures/entity/sad_apple.png");
    }


    public SadAppleItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.model = new SadAppleEntityModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SadAppleEntityModel.LAYER_LOCATION));

    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        poseStack.pushPose();
        poseStack.scale(0.7F, 0.7F, 0.7F);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(Cursed.MOD_ID, "textures/entity/sad_apple.png")));
        model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        poseStack.popPose();
    }

        public static float getProperty(ItemStack stack, ClientLevel clientLevel, LivingEntity livingEntity, int seed) {
            // Beispiel: Rückgabe einer Eigenschaft basierend auf der Haltbarkeit des Items
            if (stack.isDamaged()) {
                return 0.5F; // beschädigt
            }
            return 1.0F; // unbeschädigt
        }
    }