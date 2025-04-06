package ormanu.newfab.mixin;

import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ormanu.newfab.util.ItemSlotSoundHandler;

@Mixin(Item.class)
public class ItemMixin implements ItemSlotSoundHandler {

    @Unique
    SoundEvent sheathe = SoundEvents.INTENTIONALLY_EMPTY;
    @Unique
    SoundEvent unsheathe = SoundEvents.INTENTIONALLY_EMPTY;

    @Override
    public void newfab$setSheatheSound(SoundEvent sound)     {
        this.sheathe = sound;
    }

    @Override
    public void newfab$setUnsheatheSound(SoundEvent sound) {
        this.unsheathe = sound;
    }

    @Override
    public SoundEvent newfab$getSheatheSound() {
        return this.sheathe;
    }

    @Override
    public SoundEvent newfab$getUnsheatheSound(SoundEvent swordUnsheath) {
        return null;
    }

}
