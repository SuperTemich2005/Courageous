package du.squishling.courageous.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MudBlock extends BlockBase {

    public MudBlock() {
        super("mud", Block.Properties.create(Material.EARTH, MaterialColor.BROWN).hardnessAndResistance(0.5F).sound(SoundType.SLIME).slipperiness(0.8f));
    }

    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        double absMotion = Math.abs(entity.getMotion().y);
        if (absMotion < 0.1D && !entity.isSneaking()) {
            double newMotion = 0.4D + absMotion * 0.2D;
            entity.setMotion(entity.getMotion().mul(newMotion, 1.0D, newMotion));
        }

        super.onEntityWalk(world, pos, entity);
    }

}
