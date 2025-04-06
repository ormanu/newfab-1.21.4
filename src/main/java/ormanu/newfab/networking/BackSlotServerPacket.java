package ormanu.newfab.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import ormanu.newfab.NewFab;

public class BackSlotServerPacket {

    public static void registerServerPacket() {
        ServerPlayNetworking.registerGlobalReceiver(BackSlotPacketPayload.ID, ((payload, context) -> context.server().execute(() -> {
            PlayerEntity player = context.player();
            if (player == null || player.currentScreenHandler == null) {
                return;
            }

            ItemStack offhandStack = player.getOffHandStack();
            ItemStack handStack = player.getMainHandStack();
            ItemStack backStack = player.getInventory().getStack(41);


            if (!handStack.isEmpty()) {
                player.setStackInHand(Hand.MAIN_HAND, backStack.copy());
                player.getInventory().setStack(41, handStack.copy());
            } else {
                if (backStack.isEmpty()) {
                    player.setStackInHand(Hand.OFF_HAND, backStack.copy());
                    player.getInventory().setStack(41, offhandStack.copy());
                } else {
                    player.setStackInHand(Hand.MAIN_HAND, backStack.copy());
                    player.getInventory().setStack(41, handStack.copy());
                }
            }

            if (!offhandStack.isEmpty() || !handStack.isEmpty() || !backStack.isEmpty()) {
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_CHAIN.value(), SoundCategory.PLAYERS, (NewFab.CONFIG.backslotSwapSoundVolume / 100F), 1F);
            }

            // Sync the player's inventory back to the client
            player.currentScreenHandler.sendContentUpdates();
        })));
    }
}
