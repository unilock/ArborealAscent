package cc.unilock.arborealascent;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class AgnosForge extends Agnos {
	static {
		Agnos.delegate = new AgnosForge();
	}

	@Override
	protected Path getConfigDirectoryAgnos() {
		return FMLPaths.CONFIGDIR.get();
	}
}
