package com.zhengzhengyiyimc.renderer;

import com.zhengzhengyiyimc.entity.ThrowingAxeEntity;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ThrowingAxeRenderer extends EntityRenderer<ThrowingAxeEntity> {
    private final ItemRenderer itemRenderer;
    @SuppressWarnings("unused")
    private final Model model;
    
    public ThrowingAxeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        ModelData modelData = new ModelData();
        ModelPartData rootData = modelData.getRoot();
        ModelPart root = rootData.createPart(10, 10);
        model = new ThrowingAxeModel(root);
    }

    @Override
    public void render(ThrowingAxeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // matrices.push();
        
        // matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(
        //     (entity.age + tickDelta) * 30 % 360));
        
        // model.render(matrices, vertexConsumers.getBuffer(model.getLayer(getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        
        // matrices.pop();
        // super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        itemRenderer.renderItem(new ItemStack(Items.IRON_AXE), ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, null, 0);
    }
    
    @Override
    public Identifier getTexture(ThrowingAxeEntity entity) {
        return new Identifier("improved_item", "textures/entity/throwing_axe.png");
    }
}