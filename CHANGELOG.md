- Added **Grey Foxes**.
  - Grey Foxes are skins of basic Foxes, but spawn in Dappled Forests.


- **Dappled Forests** are now larger and more consistent, similar to Taigas and Forests.
  - Their placement has changed from being small strips within Plains to spawning between Taigas and Plains as a transition biome.
  - They replace the Forest biome in cold climates; Forests still generate in warmer areas.


- **Poplar Saplings** now have color-specific variants, all still using a single item.
  - They have been given data components, and colors are contained using item model predicates + tooltips.
  - Poplar Sapling blocks now have a `Color` blockstate that determines what color they'll show and grow as.
  - Poplar Leaves now drop their relevant color.


- **Shelf Mushrooms** now only generate 3-6 blocks above the ground, and require 1 block of Air above to generate.
  - This guarantees they'll spawn higher on trunks instead of near the ground and will not spawn too close underneath logs/leaves _(more of a personal preference)._


- Added Red Mushrooms, Sweet Berry Bushes, and Pumpkins to Dappled Forest generation.


- **Moss Blocks** are now biome-tinted and do not kill Grass underneath them, allowing for "lush grass" looking generation and building.
  - Moss Blocks generate in patches on Dappled Forest surfaces, with Grass Blocks beneath them if exposed to Air.
  - Mossy Cobblestone and Mossy Stone Bricks are also biome-tinted.
  - ***For mod/pack authors,*** mossy blocks can automatically be given tinting and `Moist` variants by duplicating their texture in their base namespace and adding `_tint_overlay` - just make sure to remove any pieces of the texture you do not want tinted!
  - Moss Blocks and tinted mossy blocks can be interacted with Water Bottles or crafted with 8 + 1 Bucket of Water to get `Moist` versions of that block that have their original colors.
    - Unlike Farmland, tinted mossy blocks will not become `Moist` if nearby Water sources.