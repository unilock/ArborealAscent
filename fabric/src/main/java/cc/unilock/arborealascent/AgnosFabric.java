package cc.unilock.arborealascent;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class AgnosFabric extends Agnos {
	static {
		Agnos.delegate = new AgnosFabric();
	}

	@Override
	protected Path getConfigDirectoryAgnos() {
		return FabricLoader.getInstance().getConfigDir();
	}
}
