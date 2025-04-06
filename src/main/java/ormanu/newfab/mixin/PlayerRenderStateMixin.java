package ormanu.newfab.mixin;

import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ormanu.newfab.client.PlayerEntityRenderStateAccess;

@Mixin(PlayerEntityRenderState.class)
public abstract class PlayerRenderStateMixin implements PlayerEntityRenderStateAccess {
    @Unique
    private PlayerEntity playerEntity;

    @Override
    public void newfab$setPlayerEntity(PlayerEntity player) {
        this.playerEntity = player;
    }

    @Override
    public PlayerEntity newfab$getPlayerEntity() {
        return this.playerEntity;
    }

}
