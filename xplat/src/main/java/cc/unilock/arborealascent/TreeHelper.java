package cc.unilock.arborealascent;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Iterator;

import static cc.unilock.arborealascent.ArborealAscent.CONFIG;

public class TreeHelper {
	private final BlockGetter level;

	private final int maxDepth;
	private final int minLeaves;
	private final int minLogs;

	private final HashSet<BlockPos> scanned = new HashSet<>();
	private int leaves = 0;
	private int logs = 0;

	private TreeHelper(BlockGetter level) {
		this.level = level;
		this.maxDepth = CONFIG.maxDepth.value();
		this.minLeaves = CONFIG.minLeaves.value();
		this.minLogs = CONFIG.minLogs.value();
	}

	public static TreeHelper create(BlockGetter level) {
		return new TreeHelper(level);
	}

	public boolean isTreeBranch(BlockPos center) {
		if (!this.level.getBlockState(center).is(BlockTags.LEAVES)) return false;

		return CONFIG.skipTreeCheck.value() || isTree(center);
	}

	public boolean isTreeTrunk(BlockPos center) {
		if (!this.level.getBlockState(center).is(BlockTags.LOGS)) return false;

		return CONFIG.skipTreeCheck.value() || isTree(center);
	}

	private boolean isTree(BlockPos center) {
		if (!this.level.getBlockState(center).is(BlockTags.LOGS)) return false;

		HashSet<BlockPos> scanning = new HashSet<>();
		HashSet<BlockPos> todo = new HashSet<>();
		this.add(center, scanning);

		int depth = 0;
		while (!scanning.isEmpty() && depth < maxDepth) {
			depth++;

			Iterator<BlockPos> it = scanning.iterator();
			while (it.hasNext()) {
				BlockPos scan = it.next();
				this.scanned.add(scan);

				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						for (int z = -1; z <= 1; z++) {
							if (x == 0 && y == 0 && z == 0)
								continue;

							BlockPos pos = scan.offset(x, y, z);
							if (!this.scanned.contains(pos)) {
								this.add(pos, todo);
							}
						}
					}
				}

				it.remove();
			}

			if (this.leaves >= minLeaves && this.logs >= minLogs) {
				return true;
			}

			scanning.addAll(todo);
			todo.clear();
		}

		return false;
	}

	private void add(BlockPos pos, HashSet<BlockPos> set) {
		BlockState state = this.level.getBlockState(pos);

		if (state.is(BlockTags.LEAVES)) {
			set.add(pos);
			this.leaves++;
		}
		if (state.is(BlockTags.LOGS)) {
			set.add(pos);
			this.logs++;
		}
	}
}
