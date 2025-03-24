package ormanu.newfab;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import ormanu.newfab.datagen.NewFabItemTagProvider;
import ormanu.newfab.datagen.NewFabRecipeProvider;

public class NewFabDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(NewFabItemTagProvider::new);
		pack.addProvider(NewFabRecipeProvider::new);
	}
}
