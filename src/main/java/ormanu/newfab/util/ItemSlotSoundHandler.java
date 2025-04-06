package ormanu.newfab.util;

import net.minecraft.sound.SoundEvent;

public interface ItemSlotSoundHandler {
    void newfab$setSheatheSound(SoundEvent sound);
    void newfab$setUnsheatheSound(SoundEvent sound);

    SoundEvent newfab$getSheatheSound();
    SoundEvent newfab$getUnsheatheSound(SoundEvent swordUnsheath);
}
