package net.satisfyu.meadow.entity.custom.sheep.patched;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.satisfyu.meadow.Meadow;
import net.satisfyu.meadow.entity.ModEntities;
import net.satisfyu.meadow.entity.custom.sheep.MeadowSheepEntity;
import net.satisfyu.meadow.registry.ObjectRegistry;

public class PatchedSheepEntity extends MeadowSheepEntity {
    public PatchedSheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world, ObjectRegistry.PATCHED_WOOL.get(), new Identifier(Meadow.MOD_ID, "entities/sheep/patched"));
    }
    @Override
    public SheepEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return ModEntities.PATCHED_SHEEP.get().create(serverWorld);
    }
}
