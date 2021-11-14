package com.fren_gor.ultimateAdvancementAPI.commands;

import com.fren_gor.ultimateAdvancementAPI.AdvancementMain;
import com.fren_gor.ultimateAdvancementAPI.util.Versions;
import net.byteflux.libby.Library;
import net.byteflux.libby.LibraryManager;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <a href="https://github.com/JorelAli/CommandAPI">CommandAPI</a> manager, which loads the correct version of the API
 * and the correct implementation of the commands.
 */
public class CommandAPIManager {

    /**
     * Loads the correct version of the API and the correct implementation of the commands.
     *
     * @param libbyManager The {@link LibraryManager} lo load the <a href="https://github.com/JorelAli/CommandAPI">CommandAPI</a>.
     * @return The {@link ILoadable} to be loaded and enabled, or {@code null} if the NMS version is not supported.
     */
    @Nullable
    public static ILoadable loadManager(LibraryManager libbyManager) {
        CommandAPIVersion ver = CommandAPIVersion.getVersionToLoad(Versions.getNMSVersion());
        if (ver == null) {
            // Skip code down below if nms version is invalid
            return null;
        }

        // Download correct version of CommandAPI
        libbyManager.addJitPack();
        Library commandAPILibrary = Library.builder()
                .groupId("dev{}jorel{}CommandAPI")
                .artifactId("commandapi-shade")
                .version(ver.getVersion()).checksum(ver.getChecksum())
                .relocate("dev{}jorel{}commandapi", "dev.jorel.commandapi") // Should be changed by shading
                .build();
        try {
            libbyManager.loadLibrary(commandAPILibrary);
        } catch (Exception e) {
            Bukkit.getLogger().warning("[UltimateAdvancementAPI-Commands] Can't load library " + commandAPILibrary.toString() + '!');
            e.printStackTrace();
            return null;
        }

        String manager = "com.fren_gor.ultimateAdvancementAPI.commands.commandAPI_v" + ver.getClasspathSuffix() + ".CommandAPIManager_v" + ver.getClasspathSuffix();
        Class<?> clazz;
        try {
            clazz = Class.forName(manager);
        } catch (ClassNotFoundException e) {
            Bukkit.getLogger().info("[UltimateAdvancementAPI-Commands] Can't find CommandAPIManager Class! (" + manager + ")");
            e.printStackTrace();
            return null;
        }

        try {
            return new CommonLoadable((ILoadable) clazz.getDeclaredConstructor().newInstance());
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Interface implemented by loadable object.
     */
    public interface ILoadable {

        /**
         * Loads the loadable object. Should be called into {@link Plugin#onLoad()}.
         *
         * @param main Already loaded {@link AdvancementMain} instance.
         */
        void onLoad(@NotNull AdvancementMain main);

        /**
         * Enables the loadable object. Should be called into {@link Plugin#onEnable()}.
         *
         * @param plugin The plugin that loaded the {@link AdvancementMain} passed to {@link #onLoad(AdvancementMain)}.
         */
        void onEnable(@NotNull Plugin plugin);
    }

    private static final class CommonLoadable implements ILoadable {

        private final ILoadable loadable;
        private Plugin advancementMainOwner;

        public CommonLoadable(@NotNull ILoadable loadable) {
            Validate.notNull(loadable, "ILoadable is null.");
            this.loadable = loadable;
        }

        @Override
        public void onLoad(@NotNull AdvancementMain main) {
            Validate.isTrue(AdvancementMain.isLoaded(), "AdvancementMain is not loaded.");
            this.advancementMainOwner = main.getOwningPlugin();
            loadable.onLoad(main);
        }

        @Override
        public void onEnable(@NotNull Plugin plugin) {
            if (advancementMainOwner == null) {
                throw new IllegalStateException("Not loaded.");
            }
            Validate.isTrue(plugin == advancementMainOwner, "AdvancementMain owning plugin isn't the provided Plugin.");
            Validate.isTrue(plugin.isEnabled(), "Plugin isn't enabled.");
            loadable.onEnable(plugin);
        }
    }
}
