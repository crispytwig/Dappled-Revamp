### Entities
- Added **Grey Foxes**.
  - Grey Foxes are skins of basic Foxes, but spawn in Dappled Forests.


- Added **Worms**.
    - Worms spawn in Dappled Forests in groups of 2-4 during rainy weather.
      - When it stops raining, Worms dry up with a 50% chance to leave behind a Worm item.
      - Worms seek out Farmland with "unripe" crops and burrow into it, Bone Meal-ing the crop.
      - Worms drop 1 Worm when killed.
      - **Chickens** kill Worms, and can now be tempted and bred with Worm items.
  - Added **Worm Buckets**.
    - Using an empty Bucket on a Worm captures it - up to 5 Worms can be stored in a single Bucket.
    - Worms can be placed from the Worm Bucket 1 at a time.
  - Added **Baited Rods**.
    - Holding a Worm or Worm Bucket in one hand and using a Fishing Rod in the other baits the Fishing Rod, turning it into a Baited Rod. Using a Fishing Rod on a Worm entity and reeling it in also baits it.
      - Baited Rods can not be cast for 1 second after baiting.
      - ***For mod/pack authors,*** which items count as bait is controlled by the `dappledrevamp:fishing_bait` item tag.
    - Baited Rods act like an early-game form of the **Lure** enchantment, causing quicker bites while fishing.
      - This stacks with real Lure enchantments.
    - When something is reeled in after using the Baited Rod, it turns back into a normal Fishing Rod and consumes durability like normal.
    - Durability & Enchantments carry over from Baited Rod ↔ Fishing Rod.
- Added the **Worm Bin**, crafted from 1 Composter and 1 Worm.
  - Works like a Composter, but the first item always adds a layer and every item after gets 2x the value.
  - The Worms inside slowly compost on their own, adding ~1 layer per in-game day.
  - Hoppers can fill and empty it like a Composter.


### Forest Generation
- **Dappled Forests** are now larger and more consistent, similar to Taigas and Forests.
  - Their placement has changed from being small strips within Plains to spawning between Taigas and Plains as a transition biome.
  - They replace the Forest biome in cold climates; Forests still generate in warmer areas.
- Added Red Mushrooms, Sweet Berry Bushes, and Pumpkins to Dappled Forest generation.


### Poplar Changes
- **Poplar Saplings** now have color-specific variants, all still using a single item.
  - They have been given data components, and colors are contained using item model predicates + tooltips.
  - Poplar Sapling blocks now have a `Color` blockstate that determines what color they'll show and grow as.
  - Poplar Leaves now drop their relevant color.
- **Poplar Trapdoors**' texture has been edited to be solid and tile better even if not completely.
- **Poplar Villages** now spawn in Dappled Forests.
  - Poplar Villages have a 1 in 5 chance to be abandoned.


### Shelf Mushrooms
- **Shelf Mushrooms** now only generate 3-6 blocks above the ground, and require 1 block of Air above to generate.
  - This guarantees they'll spawn higher on trunks instead of near the ground and will not spawn too close underneath logs/leaves _(more of a personal preference)._
- Shelf Mushrooms can now be crafted into Suspicious Stew that gives the **Leaping** effect.



### Moss Moisture
- **Moss Blocks** are now biome-tinted and do not kill Grass underneath them, allowing for "lush grass" looking generation and building.
  - Moss Blocks generate in patches on Dappled Forest surfaces, with Grass Blocks beneath them if exposed to Air.
  - Mossy Cobblestone and Mossy Stone Bricks are also biome-tinted.
  - ***For mod/pack authors,*** mossy blocks can automatically be given tinting and `Moist` variants by duplicating their texture in their base namespace and adding `_tint_overlay` - just make sure to remove any pieces of the texture you do not want tinted!
  - Moss Blocks and tinted mossy blocks can be interacted with Water Bottles or crafted with 8 + 1 Bucket of Water to get `Moist` versions of that block that have their original colors.
    - Unlike Farmland, tinted mossy blocks will not become `Moist` if nearby Water sources.
  - Moist blocks can be dried back to their normal state in Furnaces.

