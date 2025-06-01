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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class ThrowingAxeRenderer extends EntityRenderer<ThrowingAxeEntity> {
    @SuppressWarnings("unused")
    private final ItemRenderer itemRenderer;
    private final Model model;
    
    public ThrowingAxeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        ModelData modelData = new ModelData();
        ModelPartData rootData = modelData.getRoot();
        ModelPart root = rootData.createPart(0, 0);
        model = new ThrowingAxeModel(root);
    }

    @Override
    public void render(ThrowingAxeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(
            (entity.age + tickDelta) * 30 % 360));
        
        model.render(matrices, vertexConsumers.getBuffer(model.getLayer(getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
    
    @Override
    public Identifier getTexture(ThrowingAxeEntity entity) {
        return new Identifier("throwingaxe", "textures/entity/throwing_axe.png");
    }
}