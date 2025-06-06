package com.zhengzhengyiyimc.entity;

import com.zhengzhengyiyimc.Improved_item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;

public class ThrowingAxeEntity extends TridentEntity {
    private boolean isReturning = false;
    public ItemStack axeStack = new ItemStack(Items.IRON_AXE);

    public ThrowingAxeEntity(EntityType<? extends ThrowingAxeEntity> type, World world) {
        super(type, world);

        pickupType = PickupPermission.ALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected float getDragInWater() {
        return 0.95F;
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        // if (this.pickupType == PickupPermission.ALLOWED) {
        //     return player.getInventory().insertStack(this.asItemStack());
        // }
        return false;
    }

    public ThrowingAxeEntity(World world, LivingEntity owner) {
        super(Improved_item.THROWING_AXE, world);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult)hitResult;
            Entity entity = entityHitResult.getEntity();
            if (entity.getType().isIn(EntityTypeTags.REDIRECTABLE_PROJECTILE) && entity instanceof ProjectileEntity) {
                ProjectileEntity projectileEntity = (ProjectileEntity)entity;
                projectileEntity.deflect(ProjectileDeflection.REDIRECTED, this.getOwner(), this.getOwner(), true);
            }

            this.onEntityHit(entityHitResult);
            this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(), Emitter.of(this, (BlockState)null));
        } else if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult)hitResult;
            this.onBlockHit(blockHitResult);
            BlockPos blockPos = blockHitResult.getBlockPos();
            this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, Emitter.of(this, this.getWorld().getBlockState(blockPos)));
        }
    }

    public void setAxeStack(ItemStack axeStack) {
        this.axeStack = axeStack;
    }

    @Override
    protected void onEntityHit(EntityHitResult hit) {
        if (!getWorld().isClient) {
            float damage = isReturning ? 3.0f : 6.0f;
            hit.getEntity().damage(getWorld().getDamageSources().thrown(this, getOwner()), damage);
            
            if (isReturning) {
                this.discard();
            }
        }
    }

    public void recall() {
        this.isReturning = true;
        this.setNoGravity(true);
    }

    @Override
    protected ItemStack asItemStack() {
        return axeStack;
    }
}
