package co.uk.squishling.courageous.tiles;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTiles {
    public static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Reference.MOD_ID);

    public static final RegistryObject<TileEntityType<TileFluidPot>> FLUID_POT = TILES.register("fluid_pot",
            () -> TileEntityType.Builder.create(TileFluidPot::new, ModBlocks.FLUID_POT.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<TileDistiller>> DISTILLER = TILES.register("distiller",
            () -> TileEntityType.Builder.create(TileDistiller::new, ModBlocks.DISTILLER.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<TileFaucet>> FAUCET = TILES.register("faucet",
            () -> TileEntityType.Builder.create(TileFaucet::new, ModBlocks.FAUCET.get()).build(null)
    );
}
