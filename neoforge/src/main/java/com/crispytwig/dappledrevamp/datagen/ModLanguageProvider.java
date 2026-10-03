package com.crispytwig.dappledrevamp.datagen;

import com.crispytwig.dappledrevamp.DappledRevamp;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, DappledRevamp.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("tooltip.dappledrevamp.poplar_sapling.orange", "Orange Poplar");
        add("tooltip.dappledrevamp.poplar_sapling.red", "Red Poplar");
        add("tooltip.dappledrevamp.poplar_sapling.yellow", "Yellow Poplar");

        add("item.dappledrevamp.moist", "Moist %s");

        add("block.dappledrevamp.patchy_grass", "Patchy Grass");
        add("block.dappledrevamp.patchy_podzol", "Patchy Podzol");
        add("block.dappledrevamp.regolith", "Regolith");
        add("block.dappledrevamp.worm_bin", "Worm Bin");

        add("entity.dappledrevamp.worm", "Worm");

        add("item.dappledrevamp.baited_rod", "Baited Rod");
        add("item.dappledrevamp.worm", "Worm");
        add("item.dappledrevamp.worm_bucket", "Worm Bucket");
        add("item.dappledrevamp.worm_spawn_egg", "Worm Spawn Egg");

        add("subtitles.dappledrevamp.item.worm.bait", "Worm baited");
        add("subtitles.dappledrevamp.item.worm.hook", "Worm wriggles");
    }
}
