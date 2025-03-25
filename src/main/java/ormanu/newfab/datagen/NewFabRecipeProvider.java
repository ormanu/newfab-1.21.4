package ormanu.newfab.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import ormanu.newfab.items.ModItems;

import java.util.concurrent.CompletableFuture;

public class NewFabRecipeProvider extends FabricRecipeProvider {
    public NewFabRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                createShaped(RecipeCategory.MISC, ModItems.LongSword, 1)
                        .pattern("  l")
                        .pattern(" d ")
                        .pattern("s  ")
                        .input('s', Items.NETHERITE_SWORD)
                        .input('d', Items.CRYING_OBSIDIAN)
                        .input('l', Items.END_CRYSTAL)
                        .group("multi_bench") // Put it in a group called "multi_bench" - groups are shown in one slot in the recipe book
                        .criterion(hasItem(ModItems.LongSword), conditionsFromItem(ModItems.LongSword))
                        .offerTo(exporter);
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
                createShaped(RecipeCategory.MISC, ModItems.NewCrossbow, 1)
                        .pattern("  l")
                        .pattern(" d ")
                        .pattern("o  ")
                        .input('o', Items.CROSSBOW)
                        .input('d', Items.CRYING_OBSIDIAN)
                        .input('l', Items.END_CRYSTAL)
                        .group("multi_bench") // Put it in a group called "multi_bench" - groups are shown in one slot in the recipe book
                        .criterion(hasItem(ModItems.NewCrossbow), conditionsFromItem(ModItems.NewCrossbow))
                        .offerTo(exporter);

            }

        };
    }

    @Override
    public String getName() {
        return "FabricDocsReferenceRecipeProvider";
    }
}
