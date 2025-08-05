package cc.unilock.treeclimbing.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(RotatedPillarBlock.class)
public class RotatedPillarBlockMixin extends Block {
	@Unique
	private static final VoxelShape LOG = Shapes.create(new AABB(0, 0, 0, 1, 1, 1).contract(0.03125, 0, 0.03125));

	public RotatedPillarBlockMixin(BlockBehaviour.Properties properties) {
		super(properties);
		throw new AssertionError();
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (state.is(BlockTags.LOGS)) {
			return LOG;
		}
		return super.getCollisionShape(state, level, pos, context);
	}
}
