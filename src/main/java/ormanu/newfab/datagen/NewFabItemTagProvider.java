package ormanu.newfab.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import ormanu.newfab.items.ModItems;
import ormanu.newfab.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class NewFabItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public NewFabItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ItemTags.SWORDS)
                    .add(ModItems.LongSword);
            getOrCreateTagBuilder(ItemTags.CROSSBOW_ENCHANTABLE)
                    .add(ModItems.NewCrossbow);
            getOrCreateTagBuilder(ModTags.Items.AirSpeedCapable)
                    .add(ModItems.LongSword);
            getOrCreateTagBuilder(ModTags.Items.ExplosiveHitCapable)
                    .add(ModItems.NewCrossbow);
    }
}
