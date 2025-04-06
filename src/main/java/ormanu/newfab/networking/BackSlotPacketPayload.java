package ormanu.newfab.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import static ormanu.newfab.NewFab.MOD_ID;

public record BackSlotPacketPayload(BlockPos blockPos) implements CustomPayload {
    public static final Identifier BACKSLOT_PACKET_ID = Identifier.of(MOD_ID, "backslot_packet");

    public static final CustomPayload.Id<BackSlotPacketPayload> ID = new CustomPayload.Id<>(BACKSLOT_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, BackSlotPacketPayload> CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, BackSlotPacketPayload::blockPos, BackSlotPacketPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}