package ormanu.newfab.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import static ormanu.newfab.networking.BackSlotCreativeClientPacketPayload.BACKSLOT_CREATIVE_CLIENT_PACKET_ID;

public class BackSlotCreativeClientPacket {

    public static void registerClientPacket() {
        ServerPlayNetworking.registerGlobalReceiver(BACKSLOT_CREATIVE_CLIENT_PACKET_ID, (payload, context) -> {
            int entityId = payload.entityId();
            int slotId = payload.slotId();
            ItemStack itemStack = payload.itemStack();

            context.server().execute(() -> {
                PlayerEntity player = (PlayerEntity) context.player().getWorld().getEntityById(entityId);
                if (player instanceof PlayerEntity) {
                    // Set the stack to the correct item or empty stack
                    player.getInventory().setStack(slotId, itemStack);
                }
            });
        });
    }
}
