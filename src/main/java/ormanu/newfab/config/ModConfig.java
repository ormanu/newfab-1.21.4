package ormanu.newfab.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "newfab")
@Config.Gui.Background("minecraft:textures/block/gray_concrete.png")
public class ModConfig implements ConfigData {
    public int backslotX = 0;
    public int backslotY = 0;
    public int backslotSwapSoundVolume = 100;
    public boolean flipBackslotDisplay = true;
    public boolean goodTridentReturn = true;
}
