package ormanu.newfab.networking;

import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import ormanu.newfab.NewFab;

public record BackSlotCreativeClientPacketPayload(int entityId, int slotId, ItemStack itemStack) implements CustomPayload {

    public static final Id<BackSlotCreativeClientPacketPayload> BACKSLOT_CREATIVE_CLIENT_PACKET_ID = new Id<>(Identifier.of(NewFab.MOD_ID, "backslot_creative_client_packet"));

    public static final PacketCodec<RegistryByteBuf, BackSlotCreativeClientPacketPayload> CODEC = PacketCodec.of(BackSlotCreativeClientPacketPayload::write, BackSlotCreativeClientPacketPayload::new);

    public BackSlotCreativeClientPacketPayload(RegistryByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readBoolean() ? ItemStack.EMPTY : ItemStack.PACKET_CODEC.decode(buf));
    }

    public void write(RegistryByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(slotId);
        buf.writeBoolean(itemStack.isEmpty());
        if (!itemStack.isEmpty()) {
            ItemStack.PACKET_CODEC.encode(buf, itemStack);
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return BACKSLOT_CREATIVE_CLIENT_PACKET_ID;
    }
}
