package ormanu.newfab.mixin;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ormanu.newfab.networking.BackSlotClientPacketPayload;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

@Mixin(EntityTrackerEntry.class)
public class EntityTrackerEntryMixin {
    @Mutable
    @Final
    @Shadow
    private final Entity entity;

    @Shadow @Final private ServerWorld world;
    @Unique
    private static final HashMap<UUID, Vec3d> previousPositions = new HashMap<>();
    @Unique
    private final Queue<Float> verticalVelocityHistory = new LinkedList<>();
    @Unique
    private long lastWalkingSoundTick = 0;
    @Unique
    private boolean wasOnGroundLastTick = true;

    public EntityTrackerEntryMixin(Entity entity) {
        this.entity = entity;
    }

    @Inject(method = "startTracking", at = @At(value = "TAIL"))
    public void startTrackingMixin(ServerPlayerEntity serverPlayer, CallbackInfo info) {
        if (entity instanceof ServerPlayerEntity player) {
            // Send the back slot item from the player to the new server player
            sendBackSlotUpdate(serverPlayer, player);
            // Send the back slot item from the server player to the player itself
            sendBackSlotUpdate(player, player);
        }
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void onTick(CallbackInfo info) {
        if (entity instanceof PlayerEntity player) {
            UUID playerId = player.getUuid();

            Vec3d currentPosition = player.getPos();
            Vec3d previousPosition = previousPositions.getOrDefault(playerId, currentPosition);
            Vec3d velocity = currentPosition.subtract(previousPosition);

            Item backStack = player.getInventory().getStack(41).getItem();

            if (!(backStack instanceof BlockItem) && player.getInventory().getStack(41) != ItemStack.EMPTY) {}

            int VELOCITY_HISTORY_SIZE = 5;
            if (verticalVelocityHistory.size() >= VELOCITY_HISTORY_SIZE) {
                verticalVelocityHistory.poll(); // Remove the oldest velocity
            }
            verticalVelocityHistory.offer((float) velocity.y); // Add the current velocity

            // Update previous position
            previousPositions.put(playerId, currentPosition);
            wasOnGroundLastTick = player.isOnGround();
        }
    }

    @Unique
    private void sendBackSlotUpdate(ServerPlayerEntity recipient, ServerPlayerEntity sourcePlayer) {
        ItemStack backSlotItem = sourcePlayer.getInventory().getStack(41);

        // Send the back slot item (or an empty item stack if it's empty)
        ServerPlayNetworking.send(recipient,
                new BackSlotClientPacketPayload(sourcePlayer.getId(), 41, backSlotItem.isEmpty() ? ItemStack.EMPTY : backSlotItem));
        ServerPlayNetworking.send(recipient,
                new BackSlotClientPacketPayload(sourcePlayer.getId(), 42, sourcePlayer.getInventory().getStack(42).isEmpty() ? ItemStack.EMPTY : sourcePlayer.getInventory().getStack(42)));
    }

    // Detect landing based on velocity history
    @Unique
    private boolean detectLanding(PlayerEntity playerEntity) {
        if (playerEntity.isOnGround() && !wasOnGroundLastTick) {
            // Check if recent velocities indicate falling
            for (float velocity : verticalVelocityHistory) {
                if (velocity < -0.1F) { // Threshold for downward motion
                    return true;
                }
            }
        }
        return false;
    }

}
