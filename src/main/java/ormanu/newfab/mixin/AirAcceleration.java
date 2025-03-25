package ormanu.newfab.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ormanu.newfab.NewFab;

@Mixin(LivingEntity.class)
public abstract class AirAcceleration extends Entity {

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract boolean damage(ServerWorld world, DamageSource source, float amount);

    @Shadow protected abstract Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput);

    @Shadow public abstract boolean isUsingRiptide();

    public AirAcceleration(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "travelMidAir", at = @At("HEAD"))
    private void boostHorizontalAcceleration(Vec3d movementInput, CallbackInfo ci) {
        // Get the entity (LivingEntity)
        Entity entity = this;

        if ((LivingEntity) (Object) this instanceof PlayerEntity player && !entity.isOnGround() && player.getGlidingTicks() == 0) {
            // Scale horizontal movement input for better acceleration
            double horizontalBoost = 0.01; // Adjust this value for more/less acceleration
            if (this.isSprinting()) horizontalBoost = 0.015;
            if (this.isUsingRiptide()) horizontalBoost = 0.125;
            if (this.hasStatusEffect(NewFab.FasterEffect)) horizontalBoost = 0.15;
            double maxHorizontalSpeed = 0.50; // Cap horizontal speed to prevent over-speeding
            if (this.isSprinting()) maxHorizontalSpeed = 0.75;


            // Convert movementInput to global (world) coordinates
            float yaw = entity.getYaw(); // Get player yaw in degrees
            double yawRad = Math.toRadians(yaw); // Convert to radians for trigonometric functions

            // Rotate movementInput based on yaw
            double globalX = movementInput.x * Math.cos(yawRad) - movementInput.z * Math.sin(yawRad);
            double globalZ = movementInput.x * Math.sin(yawRad) + movementInput.z * Math.cos(yawRad);

            // Create the global movement vector
            Vec3d boostedInput = new Vec3d(globalX * horizontalBoost, 0, globalZ * horizontalBoost);

            // Apply the adjusted input to the entity's velocity
            Vec3d currentVelocity = this.getVelocity();
            Vec3d newVelocity = currentVelocity.add(boostedInput);

            // Limit the horizontal velocity if necessary
            double horizontalSpeed = Math.sqrt(newVelocity.x * newVelocity.x + newVelocity.z * newVelocity.z);
            if (horizontalSpeed > maxHorizontalSpeed) {
                double scale = maxHorizontalSpeed / horizontalSpeed;
                newVelocity = new Vec3d(newVelocity.x * scale, newVelocity.y, newVelocity.z * scale);
            }

            if (this.hasStatusEffect(NewFab.FasterEffect)) {
                this.setVelocity(newVelocity);
            } else if (movementInput.length() > 0.1) {
                this.setVelocity(newVelocity);
            }
        }
    }

    @Inject(method = "travelInFluid", at = @At("HEAD"))
    private void boostHorizontalAccelerationInWater(Vec3d movementInput, CallbackInfo ci) {
        // Get the entity (LivingEntity)
        Entity entity = this;

        if ((LivingEntity) (Object) this instanceof PlayerEntity player && !entity.isOnGround() && player.getGlidingTicks() == 0) {
            // Scale horizontal movement input for better acceleration
            double horizontalBoost = 0.01; // Adjust this value for more/less acceleration
            if (this.isSprinting()) horizontalBoost = 0.011;
            if (this.isUsingRiptide()) horizontalBoost = 0.125;
            if (this.hasStatusEffect(NewFab.FasterEffect)) horizontalBoost = 0.15;
            double maxHorizontalSpeed = 0.50; // Cap horizontal speed to prevent over-speeding
            if (this.isSprinting()) maxHorizontalSpeed = 0.65;


            // Convert movementInput to global (world) coordinates
            float yaw = entity.getYaw(); // Get player yaw in degrees
            double yawRad = Math.toRadians(yaw); // Convert to radians for trigonometric functions

            // Rotate movementInput based on yaw
            double globalX = movementInput.x * Math.cos(yawRad) - movementInput.z * Math.sin(yawRad);
            double globalZ = movementInput.x * Math.sin(yawRad) + movementInput.z * Math.cos(yawRad);

            // Create the global movement vector
            Vec3d boostedInput = new Vec3d(globalX * horizontalBoost, 0, globalZ * horizontalBoost);

            // Apply the adjusted input to the entity's velocity
            Vec3d currentVelocity = this.getVelocity();
            Vec3d newVelocity = currentVelocity.add(boostedInput);

            // Limit the horizontal velocity
            double horizontalSpeed = Math.sqrt(newVelocity.x * newVelocity.x + newVelocity.z * newVelocity.z);
            if (horizontalSpeed > maxHorizontalSpeed) {
                double scale = maxHorizontalSpeed / horizontalSpeed;
                newVelocity = new Vec3d(newVelocity.x * scale, newVelocity.y, newVelocity.z * scale);
            }

            if (this.hasStatusEffect(NewFab.FasterEffect)) {
                this.setVelocity(newVelocity);
            } else if (movementInput.length() > 0.1) {
                this.setVelocity(newVelocity);
            }
        }
    }
}

