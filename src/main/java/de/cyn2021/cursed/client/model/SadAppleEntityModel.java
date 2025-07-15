package de.cyn2021.cursed.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SadAppleEntityModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath("cursed", "sad_apple_entity"), "main"
	);
	private final ModelPart feet;
	private final ModelPart right;
	private final ModelPart left;
	private final ModelPart bone;

	public SadAppleEntityModel(ModelPart root) {
		this.feet = root.getChild("feet");
		this.right = this.feet.getChild("right");
		this.left = this.feet.getChild("left");
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition feet = partdefinition.addOrReplaceChild("feet", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 3.0F));

		PartDefinition right = feet.addOrReplaceChild("right", CubeListBuilder.create().texOffs(54, 54).addBox(-0.92F, 8.8F, -2.84F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(22, 53).addBox(-0.92F, -0.2F, -1.08F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.92F, 3.2F, 0.24F));

		PartDefinition left = feet.addOrReplaceChild("left", CubeListBuilder.create().texOffs(22, 40).addBox(-0.92F, -0.2F, -1.08F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(46, 54).addBox(-0.92F, 8.8F, -3.04F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.56F, 3.2F, 0.24F));

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(38, 54).addBox(-3.74F, -15.92F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(30, 54).addBox(-1.68F, -13.76F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-4.36F, -7.96F, -5.16F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(36, 18).addBox(4.8F, -7.96F, -5.16F, 2.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(36, 36).addBox(-6.0F, -7.96F, -5.16F, 2.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(0, 18).addBox(-4.36F, 1.2F, -5.16F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(0, 29).addBox(-4.36F, -9.6F, -5.16F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 3.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 40).addBox(0.16F, -4.68F, -2.16F, 2.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.48F, -3.28F, 3.48F, -1.5708F, -1.5708F, 1.5708F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(36, 0).addBox(0.16F, -4.68F, -2.16F, 2.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.48F, -3.28F, -7.32F, -1.5708F, -1.5708F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float rightMax = (float) Math.toRadians(32.5);
		float rightMin = (float) Math.toRadians(-27.5);
		float leftMax = (float) Math.toRadians(37.5);
		float leftMin = (float) Math.toRadians(-27.5);

		this.right.xRot = rightMax * Math.max(0, (float)Math.sin(limbSwing)) * limbSwingAmount;
		this.left.xRot = leftMax * Math.max(0, (float)Math.sin(limbSwing + (float)Math.PI)) * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int packedColor) {
		feet.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
	}
}