package com.fren_gor.ultimateAdvancementAPI.advancement.display;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_15_R1.ChatComponentText;
import net.minecraft.server.v1_15_R1.MinecraftKey;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The AdvancementDisplay groups all the graphic components into one class.
 * Describes the aesthetic aspect of the advancement.
 */
public class AdvancementDisplay {

    protected final ItemStack icon;
    protected final BaseComponent[] chatTitle = new BaseComponent[1]; // Make sure only 1 element is used, otherwise the chat bugs
    protected final BaseComponent[] chatDescription = new BaseComponent[1]; // Make sure only 1 element is used, otherwise the chat bugs
    @Getter
    protected final String title, rawTitle;
    @Getter
    @Unmodifiable
    protected final List<String> description;
    @Getter
    protected final String compactDescription;
    @Getter
    protected final AdvancementFrameType frame;
    protected final boolean showToast;
    protected final boolean announceChat;
    @Getter
    protected final float x, y;

    /**
     * AdvancementDisplay main constructor.
     *
     * @param icon - ItemStack - what item will be shown on the tab.
     * @param title - String - the displayed title of the advancement.
     * @param frame - AdvancementFrameType - which shape has the advancement.
     * @param showToast - boolean - if it shows the toast message.
     * @param announceChat - boolean - if it shows the announceChat message.
     * @param x - float - x coordinate.
     * @param y - float - y coordinate.
     * @param defaultColor - ChatColor - description's color.
     * @param description - List<String> - the description title of the advancement.
     */
    public AdvancementDisplay(@NotNull ItemStack icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull ChatColor defaultColor, @NotNull List<String> description) {
        Validate.notNull(icon, "Icon is null.");
        Validate.notNull(title, "Title si null.");
        Validate.notNull(frame, "Frame si null.");
        Validate.notNull(defaultColor, "Default color si null.");
        Validate.notNull(description, "Description is null.");
        Validate.isTrue(isNoElementNull(description), "An element of the description is null.");
        Validate.isTrue(x >= 0, "x is not null or positive.");
        Validate.isTrue(y >= 0, "y is not null or positive.");

        this.icon = icon.clone();
        this.title = title;
        this.description = Collections.unmodifiableList(new ArrayList<>(description));

        // Remove trailing spaces and color codes
        String titleTrimmed = title.trim();
        int toSub = titleTrimmed.length();
        while (titleTrimmed.charAt(toSub - 2) == '§') {
            toSub -= 2;
        }
        this.rawTitle = titleTrimmed.substring(0, toSub).trim();

        this.chatTitle[0] = new TextComponent(rawTitle);
        // Old code, bugged for unknown reasons (found out that BaseComponent[] must have length 1 or it bugs in HoverEvents)
        //this.chatDescription = AdvancementUtils.fromStringList(title, this.description);

        StringJoiner joiner = new StringJoiner("\n" + defaultColor, defaultColor.toString(), "");
        for (String s : this.description)
            joiner.add(s);

        this.compactDescription = joiner.toString();
        if (compactDescription.isEmpty()) {
            this.chatDescription[0] = new TextComponent(rawTitle);
        } else {
            this.chatDescription[0] = new TextComponent(rawTitle + '\n' + compactDescription);
        }

        this.frame = frame;
        this.showToast = showToast;
        this.announceChat = announceChat;
        this.x = x;
        this.y = y;
    }

    /**
     * AdvancementDisplay with Material instead of ItemStack.
     * Varargs of strings as description.
     *
     * @param icon - Material
     * @param title - String
     * @param frame - AdvancementFrameType
     * @param showToast - boolean
     * @param announceChat - boolean
     * @param x - float
     * @param y - float
     * @param description - varargs of strings
     */
    public AdvancementDisplay(@NotNull Material icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull String... description) {
        this(icon, title, frame, showToast, announceChat, x, y, Arrays.asList(description));
    }

    /**
     * AdvancementDisplay with Material instead of ItemStack.
     *
     * @param icon - Material
     * @param title - String
     * @param frame - AdvancementFrameType
     * @param showToast - boolean
     * @param announceChat - boolean
     * @param x - float
     * @param y - float
     * @param description - List<String>
     */
    public AdvancementDisplay(@NotNull Material icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull List<String> description) {
        this(new ItemStack(Objects.requireNonNull(icon, "Icon is null.")), title, frame, showToast, announceChat, x, y, description);
    }

    /**
     * AdvancementDisplay constructor without defaultcolor. It will be chosen based on AdvancementFrameType.
     * Varargs of strings as description.
     *
     * @param icon - ItemStack
     * @param title - String
     * @param frame - AdvancementFrameType
     * @param showToast - boolean
     * @param announceChat - boolean
     * @param x - float
     * @param y - float
     * @param description - varargs of strings
     */
    public AdvancementDisplay(@NotNull ItemStack icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull String... description) {
        this(icon, title, frame, showToast, announceChat, x, y, Arrays.asList(description));
    }

    /**
     * AdvancementDisplay constructor without defaultcolor. It will be chosen based on AdvancementFrameType.
     *
     * @param icon - ItemStack
     * @param title - String
     * @param frame - AdvancementFrameType
     * @param showToast - boolean
     * @param announceChat - boolean
     * @param x - float
     * @param y - float
     * @param description - List<String>
     */
    public AdvancementDisplay(@NotNull ItemStack icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull List<String> description) {
        this(icon, title, frame, showToast, announceChat, x, y, Objects.requireNonNull(frame, "AdvancementFrameType is null.").getColor(), description);
    }

    /**
     * AdvancementDisplay constructor with varargs of strings as description.
     *
     * @param icon - ItemStack
     * @param title - String
     * @param frame - AdvancementFrameType
     * @param showToast - boolean
     * @param announceChat - boolean
     * @param x - float
     * @param y - float
     * @param defaultColor - ChatColor
     * @param description - varargs of strings
     */
    public AdvancementDisplay(@NotNull ItemStack icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull ChatColor defaultColor, @NotNull String... description) {
        this(icon, title, frame, showToast, announceChat, x, y, defaultColor, Arrays.asList(description));
    }

    /**
     * Getter of showToast.
     *
     * @return boolean
     */
    public boolean doesShowToast() {
        return showToast;
    }

    /**
     * Getter of announceToChat.
     *
     * @return
     */
    public boolean doesAnnounceToChat() {
        return announceChat;
    }

    /**
     * This method returns the BaseComponent array of title for the chat.
     *
     * @return BaseComponent[]
     */
    public BaseComponent[] getChatTitle() {
        return chatTitle.clone();
    }

    /**
     * This method returns the BaseComponent array of description for the chat.
     *
     * @return BaseComponent[]
     */
    public BaseComponent[] getChatDescription() {
        return chatDescription.clone();
    }

    /**
     * Getter for the Icon
     * @return
     */
    @NotNull
    public ItemStack getIcon() {
        return icon.clone();
    }

    /**
     * Given an advancement, its display is returned based on the minecraft version. It is used in packets.
     *
     * @param advancement
     * @return net.minecraft.server.v1_15_R1.AdvancementDisplay
     */
    @NotNull
    public net.minecraft.server.v1_15_R1.AdvancementDisplay getMinecraftDisplay(@NotNull Advancement advancement) {
        Validate.notNull(advancement, "Advancement is null.");
        MinecraftKey bg = null;
        if (advancement instanceof RootAdvancement) {
            bg = new MinecraftKey(((RootAdvancement) advancement).getBackgroundTexture());
        }

        net.minecraft.server.v1_15_R1.AdvancementDisplay advDisplay = new net.minecraft.server.v1_15_R1.AdvancementDisplay(getNMSIcon(), new ChatComponentText(title), new ChatComponentText(compactDescription), bg, frame.getMinecraftFrameType(), false, false, false);
        advDisplay.a(x, y);
        return advDisplay;
    }

    /**
     * Returns the icon according to the minecraft version. It is used in packets
     *
     * @return net.minecraft.server.v1_15_R1.ItemStack
     */
    @NotNull
    public net.minecraft.server.v1_15_R1.ItemStack getNMSIcon() {
        return CraftItemStack.asNMSCopy(icon);
    }

    /**
     * This method checks if all elements of a list are non-null
     * @param list
     * @param <T>
     * @return boolean
     */
    private static <T> boolean isNoElementNull(@NotNull List<T> list) {
        for (T t : list) {
            if (t == null) {
                return false;
            }
        }
        return true;
    }

}
