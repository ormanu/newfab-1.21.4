package ormanu.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import ormanu.items.ModItems;

import java.util.concurrent.CompletableFuture;

public class NewFabItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public NewFabItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ItemTags.SWORDS)
                    .add(ModItems.TEST);

    }
}
