package com.crispytwig.dappledrevamp.world.entity.animal.worm;

import com.crispytwig.dappledrevamp.registry.ModItems;
import com.crispytwig.dappledrevamp.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Prediction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class Worm extends PathfinderMob {
    private boolean rainSpawned;
    private int rainTicksLeft;

    public Worm(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2.0).add(Attributes.MOVEMENT_SPEED, 0.1);
    }

    public static boolean checkWormSpawnRules(
        EntityType<Worm> type, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random
    ) {
        return level.getLevel().isRaining() && level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BurrowIntoCropGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(
        ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData
    ) {
        this.rainSpawned = spawnReason == EntitySpawnReason.NATURAL || spawnReason == EntitySpawnReason.CHUNK_GENERATION;
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.BUCKET)) {
            if (!this.level().isClientSide()) {
                ItemStack wormBucket = new ItemStack(ModItems.WORM_BUCKET.get());
                if (itemStack.getCount() == 1) {
                    player.setItemInHand(hand, wormBucket);
                } else {
                    itemStack.shrink(1);
                    player.getInventory().placeItemBackInInventory(wormBucket, Prediction.SERVER_ONLY);
                }
                this.level().playSound(null, this.blockPosition(), ModSoundEvents.WORM_HOOK.get(), this.getSoundSource(), 1.0F, 1.0F);
                this.discard();
            }
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if (!(this.level() instanceof ServerLevel level) || !this.rainSpawned) {
            return;
        }
        if (level.isRaining()) {
            this.rainTicksLeft = 0;
        } else if (this.rainTicksLeft <= 0) {
            this.rainTicksLeft = this.random.nextInt(41) + 40;
        } else if (--this.rainTicksLeft <= 0) {
            this.burrowAway(level);
        }
    }

    private void burrowAway(ServerLevel level) {
        for (int i = 0; i < 10; i++) {
            level.sendParticles(
                ParticleTypes.CLOUD,
                this.getX() + (this.random.nextDouble() - 0.5) * this.getBbWidth(),
                this.getY() + this.random.nextDouble() * this.getBbHeight(),
                this.getZ() + (this.random.nextDouble() - 0.5) * this.getBbWidth(),
                1, 0.0, 0.02, 0.0, 0.0
            );
        }
        this.playSound(SoundEvents.HONEYCOMB_WAX_ON, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        if (this.random.nextFloat() < 0.5F) {
            this.spawnAtLocation(level, ModItems.WORM.get());
        }
        this.discard();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("RainSpawned", this.rainSpawned);
        output.putInt("RainTicksLeft", this.rainTicksLeft);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.rainSpawned = input.getBooleanOr("RainSpawned", false);
        this.rainTicksLeft = input.getIntOr("RainTicksLeft", 0);
    }

    @Override
    public boolean removeWhenFarAway(double distSqr) {
        return false;
    }

    static class BurrowIntoCropGoal extends MoveToBlockGoal {
        BurrowIntoCropGoal(Worm worm) {
            super(worm, 1.0, 16, 1);
        }

        @Override
        protected boolean isValidTarget(LevelReader level, BlockPos pos) {
            if (!level.getBlockState(pos).is(Blocks.FARMLAND)) {
                return false;
            }
            BlockState crop = level.getBlockState(pos.above());
            return crop.getBlock() instanceof CropBlock cropBlock && !cropBlock.isMaxAge(crop);
        }

        @Override
        public double acceptedDistance() {
            return 1.5;
        }

        @Override
        public void tick() {
            super.tick();
            if (!this.isReachedTarget() || !(this.mob.level() instanceof ServerLevel level)) {
                return;
            }
            BlockPos cropPos = this.blockPos.above();
            BlockState crop = level.getBlockState(cropPos);
            if (crop.getBlock() instanceof CropBlock cropBlock && !cropBlock.isMaxAge(crop)) {
                cropBlock.performBonemeal(level, this.mob.getRandom(), cropPos, crop, BonemealSource.MOB);
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, cropPos, 15);
            }
            level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_DESTROY_BLOCK, this.blockPos, Block.getId(level.getBlockState(this.blockPos)));
            this.mob.discard();
        }
    }
}
