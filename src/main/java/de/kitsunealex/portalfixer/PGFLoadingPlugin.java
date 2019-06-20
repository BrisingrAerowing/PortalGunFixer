package de.kitsunealex.portalfixer;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import sun.misc.URLClassPath;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@IFMLLoadingPlugin.Name("Portal Gun Fixer")
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE + 101)
public class PGFLoadingPlugin implements IFMLLoadingPlugin {

    private static Logger logger = LogManager.getLogger("Portal Gun Fixer");

    static {
        logger.info("Fixing classpath order");
        fixClasspathOrder();
        logger.info("Initializing Mixin");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.portalfixer.client.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    private static void fixClasspathOrder() {
        addExternalClasspathEntry("me.ichun.mods.ichunutil.common.iChunUtil");
        addExternalClasspathEntry("me.ichun.mods.portalgun.common.PortlaGun");
        URL url = PGFLoadingPlugin.class.getProtectionDomain().getCodeSource().getLocation();
        givePriorityInClasspath(url, Launch.classLoader);
        givePriorityInClasspath(url, (URLClassLoader)ClassLoader.getSystemClassLoader());
    }

    private static void addExternalClasspathEntry(String className) {
        File modsDir = new File(Launch.minecraftHome, "mods");
        if(!modsDir.exists()) modsDir.mkdir();
        File[] files = modsDir.listFiles();

        if(files != null) {
            for(File file : files) {
                if(file.getName().endsWith(".jar")) {
                    try {
                        JarFile jar = new JarFile(file);
                        JarEntry entry = jar.getJarEntry(className.replaceAll("\\.", "/"));

                        if(entry != null) {
                            URL url = file.toPath().toUri().toURL();
                            givePriorityInClasspath(url, Launch.classLoader);
                            givePriorityInClasspath(url, (URLClassLoader)ClassLoader.getSystemClassLoader());
                            logger.info("Loading external classpath entry {}", file.getName());
                        }
                    }
                    catch(Exception e) {
                        logger.error("Something went wrong while loading external classpath entry {} - {}", file.getName(), e);
                    }
                }
            }
        }
    }

    private static void givePriorityInClasspath(URL url, URLClassLoader classLoader) {
        try {
            Field field = URLClassLoader.class.getDeclaredField("ucp");
            field.setAccessible(true);
            ArrayList<URL> urls = arrayListOf(classLoader.getURLs());
            urls.remove(url);
            urls.add(0, url);
            URLClassPath classPath = new URLClassPath(urls.toArray(new URL[0]));
            field.set(classLoader, classPath);
        }
        catch(Exception e) {
            logger.error("Something went wrong while re-ordering the classpath - {}", e);
        }
    }

    private static <T> ArrayList<T> arrayListOf(T[] array) {
        ArrayList<T> list = new ArrayList<>();
        for(int i = 0; i < array.length; i++) list.add(i, array[i]);
        return list;
    }

}
