package com.ferri.arnus.winteressentials.models;
// Made with Blockbench 4.0.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;

public class SnowShoesModel<T extends LivingEntity> extends HumanoidModel<T> {

	public SnowShoesModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F + 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F).extend(0.5F)), PartPose.offset(0.0F, 0.0F + 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F + 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F + 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F + 0.0F, 0.0F));
		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F + 0.0F, 0.0F));
		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F + 0.0F, 0.0F));
		
		PartDefinition rightshoe = right_leg.addOrReplaceChild("rightshoe", CubeListBuilder.create().texOffs(18, 26).addBox(-4.0F, -1.0F, 6.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 27).addBox(-6.0F, -1.0F, 2.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 28).addBox(-6.0F, -1.0F, -4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(17, 28).addBox(-4.0F, -1.0F, -7.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(17, 28).addBox(-5.0F, -1.0F, -6.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 22).addBox(-7.0F, -1.0F, -3.0F, 10.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 25).addBox(-5.0F, -1.0F, 4.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 19).addBox(-5.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition leftshoe = left_leg.addOrReplaceChild("leftshoe", CubeListBuilder.create().texOffs(18, 26).addBox(-4.0F, -1.0F, 6.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 27).addBox(-6.0F, -1.0F, 2.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 28).addBox(-6.0F, -1.0F, -4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(17, 28).addBox(-4.0F, -1.0F, -7.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(17, 28).addBox(-5.0F, -1.0F, -6.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 22).addBox(-7.0F, -1.0F, -3.0F, 10.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 25).addBox(-5.0F, -1.0F, 4.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 19).addBox(-5.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T p_102866_, float p_102867_, float p_102868_, float p_102869_, float p_102870_,
			float p_102871_) {
		super.setupAnim(p_102866_, p_102867_, p_102868_, p_102869_, p_102870_, p_102871_);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		leftLeg.render(poseStack, buffer, packedLight, packedOverlay);
		rightLeg.render(poseStack, buffer, packedLight, packedOverlay);
	}
	
	
}