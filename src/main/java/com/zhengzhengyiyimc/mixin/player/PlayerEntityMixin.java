package com.zhengzhengyiyimc.mixin.player;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.zhengzhengyiyimc.Improved_item;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"
        ),
        cancellable = true
    )
    private void onPlayerSwordAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getHealth() <= 5) {
            target.damage(player.getDamageSources().inFire(), 4);
            target.getWorld().setBlockState(target.getBlockPos(), Blocks.FIRE.getDefaultState());
        }

        if (player.getMainHandStack().isIn(ItemTags.SWORDS) && player.getAttackCooldownProgress(0.0F) > 0.0F) {
            Vec3d dashDirection = player.getRotationVector().normalize();
            double dashSpeed = 1.5;
            player.setVelocity(dashDirection.x * dashSpeed, 0, dashDirection.z * dashSpeed);
            player.velocityModified = true;

            World world = player.getWorld();
            Vec3d startPos = player.getPos();
            Vec3d endPos = startPos.add(dashDirection.multiply(3.0));
            Box hitbox = new Box(startPos, endPos).expand(1.0);

            world.getOtherEntities(player, hitbox).forEach(entity -> {
                if (entity instanceof LivingEntity) {
                    if (world instanceof ServerWorld) {
                        entity.damage(player.getDamageSources().playerAttack(player), Improved_item.modConfig.movingHitDamage);
                    }
                    entity.setVelocity(
                        dashDirection.x * 1.2,
                        dashDirection.y * 1.2 + 0.2,
                        dashDirection.z * 1.2
                    );
                    entity.velocityModified = true;
                }
            });

            world.addParticle(ParticleTypes.EFFECT, player.getX(), player.getY(), player.getZ(), dashSpeed, dashSpeed, dashSpeed);

            world.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,
                SoundCategory.PLAYERS,
                1.0f,
                1.0f
            );
        }
    }
}