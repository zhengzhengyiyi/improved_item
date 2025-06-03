package com.zhengzhengyiyimc.renderer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class ThrowingAxeModel extends Model {
    // private final ModelPart axeHead;
    // private final ModelPart axeHandle;
    private final ModelPart root;
    
    public ThrowingAxeModel(ModelPart root) {
        super(RenderLayer::getEntityCutout);
        this.root = root;

        // axeHead = root.getChild("head");
        // axeHandle = root.getChild("handle");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        
        root.addChild("head",
            ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F),
            ModelTransform.NONE);
        
        root.addChild("handle",
            ModelPartBuilder.create()
                .uv(24, 0)
                .cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 10.0F, 2.0F),
            ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, 
                    int light, int overlay, float red, float green, 
                    float blue, float alpha) {
        root.render(matrices, vertices, light, overlay);
    }
}
