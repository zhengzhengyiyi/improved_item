package com.zhengzhengyiyimc.renderer;

import com.zhengzhengyiyimc.entity.ThrowingAxeEntity;

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
    
    public ThrowingAxeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ThrowingAxeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        itemRenderer.renderItem(new ItemStack(Items.IRON_AXE), ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, null, 0);
    }
    
    @Override
    public Identifier getTexture(ThrowingAxeEntity entity) {
        return new Identifier("improved_item", "textures/entity/throwing_axe.png");
    }
}