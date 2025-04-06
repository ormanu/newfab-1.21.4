package ormanu.newfab;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import ormanu.newfab.entities.ModEntities;
import ormanu.newfab.entities.renderer.NewTridentEntityRenderer;
import ormanu.newfab.networking.BackSlotClientPacket;
import ormanu.newfab.util.ModKeyBindings;

public class NewFabClient implements ClientModInitializer   {
    @Override
    public void onInitializeClient() {
        ModKeyBindings.initialize();
        BackSlotClientPacket.registerClientPacket();
        EntityRendererRegistry.register(ModEntities.NEW_TRIDENT_ENTITY, NewTridentEntityRenderer::new);

    }
}
