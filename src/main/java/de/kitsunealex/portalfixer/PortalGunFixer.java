package de.kitsunealex.portalfixer;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static de.kitsunealex.portalfixer.util.Constants.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPS, acceptedMinecraftVersions = MC_VERSION, useMetadata = true, clientSideOnly = true)
public class PortalGunFixer {

    @Instance
    public static PortalGunFixer instance;
    private static Logger logger = LogManager.getLogger("Portal Gun Fixer");

}
