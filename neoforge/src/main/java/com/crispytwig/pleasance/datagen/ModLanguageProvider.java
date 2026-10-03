package com.crispytwig.pleasance.datagen;

import com.crispytwig.pleasance.Pleasance;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, Pleasance.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("tooltip.pleasance.poplar_sapling.orange", "Orange Poplar");
        add("tooltip.pleasance.poplar_sapling.red", "Red Poplar");
        add("tooltip.pleasance.poplar_sapling.yellow", "Yellow Poplar");

        add("item.pleasance.moist", "Moist %s");

        add("block.pleasance.patchy_grass", "Patchy Grass");
        add("block.pleasance.orange_poplar_leaf_layer", "Orange Poplar Leaf Layer");
        add("block.pleasance.patchy_podzol", "Patchy Podzol");
        add("block.pleasance.red_poplar_leaf_layer", "Red Poplar Leaf Layer");
        add("block.pleasance.regolith", "Regolith");
        add("block.pleasance.worm_bin", "Worm Bin");
        add("block.pleasance.yellow_poplar_leaf_layer", "Yellow Poplar Leaf Layer");

        add("entity.pleasance.worm", "Worm");

        add("item.pleasance.baited_rod", "Baited Rod");
        add("item.pleasance.worm", "Worm");
        add("item.pleasance.worm_bucket", "Worm Bucket");
        add("item.pleasance.worm_spawn_egg", "Worm Spawn Egg");

        add("subtitles.pleasance.item.worm.bait", "Worm baited");
        add("subtitles.pleasance.item.worm.hook", "Worm wriggles");
    }
}
