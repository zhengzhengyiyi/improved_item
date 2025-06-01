package com.zhengzhengyiyimc.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class ThrowingAxeEntity extends ThrownItemEntity {
    public ThrowingAxeEntity(EntityType<ThrowingAxeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.IRON_AXE;
    }

    @Override
    protected void onEntityHit(EntityHitResult hit) {
        super.onEntityHit(hit);
        hit.getEntity().damage(getDamageSources().thrown(this, this.getOwner()), 6.0F);
    }
    
    @Override
    protected void onBlockHit(BlockHitResult hit) {
        World world = getWorld();
        if (!world.isClient) {
            world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), 
                new ItemStack(Items.IRON_AXE)));
            this.discard();
        }
    }
}