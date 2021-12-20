package com.fren_gor.ultimateAdvancementAPI.advancement.display;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.nms.wrappers.advancement.AdvancementDisplayWrapper;
import com.google.common.base.Preconditions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The {@code AdvancementDisplay} class contains the graphical information of the advancement.
 * <p>It contains the title, description, icon, etc. etc.
 */
public class AdvancementDisplay {

    /**
     * The icon of the advancement in the advancement GUI.
     */
    protected final ItemStack icon;

    /**
     * The fancy title used by {@link Advancement#getAnnounceMessage(Player)}.
     */
    protected final BaseComponent[] chatTitle = new BaseComponent[1]; // Make sure only 1 element is used, otherwise the chat bugs

    /**
     * The fancy description used by {@link Advancement#getAnnounceMessage(Player)}.
     */
    protected final BaseComponent[] chatDescription = new BaseComponent[1]; // Make sure only 1 element is used, otherwise the chat bugs

    /**
     * The title of the advancement.
     */
    protected final String title;

    /**
     * The trimmed title of the advancement.
     */
    protected final String rawTitle;

    /**
     * The description of the advancement.
     */
    @Unmodifiable
    protected final List<String> description;

    /**
     * The description of the advancement compacted as a single {@link String} (with {@code '\n' + defaultColorCode} between lines).
     */
    protected final String compactDescription;

    /**
     * The shape of the advancement frame in the advancement GUI.
     */
    protected final AdvancementFrameType frame;

    /**
     * Whether the toast notification should be sent on advancement grant.
     */
    protected final boolean showToast;

    /**
     * Whether the advancement completion message should be sent on advancement grant.
     */
    protected final boolean announceChat;

    /**
     * The advancement x coordinate.
     */
    protected final float x;

    /**
     * The advancement y coordinate.
     */
    protected final float y;

    /**
     * Creates a new {@code AdvancementDisplay}.
     * <p>The default color of the title and description is {@code frame.getColor()}.
     * <p>The advancement is positioned by the x and y coordinates in the advancement GUI.
     * The x-axis points to the right (as usual), whereas the y-axis points downward.
     *
     * @param icon The material of the advancement's icon in the advancement GUI.
     * @param title The title of the advancement.
     * @param frame The shape of the advancement frame in the advancement GUI.
     * @param showToast Whether the toast notification should be sent on advancement grant.
     * @param announceChat Whether the advancement completion message should be sent on advancement grant.
     * @param x The advancement x coordinate. Must be positive.
     * @param y The advancement y coordinate. Must be positive.
     * @param description The description of the advancement.
     */
    public AdvancementDisplay(@NotNull Material icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull String... description) {
        this(icon, title, frame, showToast, announceChat, x, y, Arrays.asList(description));
    }

    /**
     * Creates a new {@code AdvancementDisplay}.
     * <p>The default color of the title and description is {@code frame.getColor()}.
     * <p>The advancement is positioned by the x and y coordinates in the advancement GUI.
     * The x-axis points to the right (as usual), whereas the y-axis points downward.
     *
     * @param icon The material of the advancement's icon in the advancement GUI.
     * @param title The title of the advancement.
     * @param frame The shape of the advancement frame in the advancement GUI.
     * @param showToast Whether the toast notification should be sent on advancement grant.
     * @param announceChat Whether the advancement completion message should be sent on advancement grant.
     * @param x The advancement x coordinate. Must be positive.
     * @param y The advancement y coordinate. Must be positive.
     * @param description The description of the advancement.
     */
    public AdvancementDisplay(@NotNull Material icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull List<String> description) {
        this(new ItemStack(Objects.requireNonNull(icon, "Icon is null.")), title, frame, showToast, announceChat, x, y, description);
    }

    /**
     * Creates a new {@code AdvancementDisplay}.
     * <p>The default color of the title and description is {@code frame.getColor()}.
     * <p>The advancement is positioned by the x and y coordinates in the advancement GUI.
     * The x-axis points to the right (as usual), whereas the y-axis points downward.
     *
     * @param icon The advancement's icon in the advancement GUI.
     * @param title The title of the advancement.
     * @param frame The shape of the advancement frame in the advancement GUI.
     * @param showToast Whether the toast notification should be sent on advancement grant.
     * @param announceChat Whether the advancement completion message should be sent on advancement grant.
     * @param x The advancement x coordinate. Must be positive.
     * @param y The advancement y coordinate. Must be positive.
     * @param description The description of the advancement.
     */
    public AdvancementDisplay(@NotNull ItemStack icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull String... description) {
        this(icon, title, frame, showToast, announceChat, x, y, Arrays.asList(description));
    }

    /**
     * Creates a new {@code AdvancementDisplay}.
     * <p>The default color of the title and description is {@code frame.getColor()}.
     * <p>The advancement is positioned by the x and y coordinates in the advancement GUI.
     * The x-axis points to the right (as usual), whereas the y-axis points downward.
     *
     * @param icon The advancement's icon in the advancement GUI.
     * @param title The title of the advancement.
     * @param frame The shape of the advancement frame in the advancement GUI.
     * @param showToast Whether the toast notification should be sent on advancement grant.
     * @param announceChat Whether the advancement completion message should be sent on advancement grant.
     * @param x The advancement x coordinate. Must be positive.
     * @param y The advancement y coordinate. Must be positive.
     * @param description The description of the advancement.
     */
    public AdvancementDisplay(@NotNull ItemStack icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull List<String> description) {
        this(icon, title, frame, showToast, announceChat, x, y, Objects.requireNonNull(frame, "AdvancementFrameType is null.").getColor(), description);
    }

    /**
     * Creates a new {@code AdvancementDisplay}.
     * <p>The advancement is positioned by the x and y coordinates in the advancement GUI.
     * The x-axis points to the right (as usual), whereas the y-axis points downward.
     *
     * @param icon The advancement's icon in the advancement GUI.
     * @param title The title of the advancement.
     * @param frame The shape of the advancement frame in the advancement GUI.
     * @param showToast Whether the toast notification should be sent on advancement grant.
     * @param announceChat Whether the advancement completion message should be sent on advancement grant.
     * @param x The advancement x coordinate. Must be positive.
     * @param y The advancement y coordinate. Must be positive.
     * @param defaultColor The default color of the title and description.
     * @param description The description of the advancement.
     */
    public AdvancementDisplay(@NotNull ItemStack icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull ChatColor defaultColor, @NotNull String... description) {
        this(icon, title, frame, showToast, announceChat, x, y, defaultColor, Arrays.asList(description));
    }

    /**
     * Creates a new {@code AdvancementDisplay}.
     * <p>The advancement is positioned by the x and y coordinates in the advancement GUI.
     * The x-axis points to the right (as usual), whereas the y-axis points downward.
     *
     * @param icon The advancement's icon in the advancement GUI.
     * @param title The title of the advancement.
     * @param frame The shape of the advancement frame in the advancement GUI.
     * @param showToast Whether the toast notification should be sent on advancement grant.
     * @param announceChat Whether the advancement completion message should be sent on advancement grant.
     * @param x The advancement x coordinate. Must be positive.
     * @param y The advancement y coordinate. Must be positive.
     * @param defaultColor The default color of the title and description.
     * @param description The description of the advancement.
     */
    public AdvancementDisplay(@NotNull ItemStack icon, @NotNull String title, @NotNull AdvancementFrameType frame, boolean showToast, boolean announceChat, float x, float y, @NotNull ChatColor defaultColor, @NotNull List<String> description) {
        Preconditions.checkNotNull(icon, "Icon is null.");
        Preconditions.checkNotNull(title, "Title is null.");
        Preconditions.checkNotNull(frame, "Frame is null.");
        Preconditions.checkNotNull(defaultColor, "Default color is null.");
        Preconditions.checkNotNull(description, "Description is null.");
        for (String line : description)
            Preconditions.checkNotNull(line, "A line of the description is null.");

        this.icon = icon.clone();
        this.title = title;
        this.description = List.copyOf(description);

        // Remove trailing spaces and color codes
        String titleTrimmed = title.trim();
        int toSub = titleTrimmed.length();
        while (titleTrimmed.charAt(toSub - 2) == '§') {
            toSub -= 2;
        }
        this.rawTitle = titleTrimmed.substring(0, toSub).trim();

        this.chatTitle[0] = new TextComponent(defaultColor + rawTitle);
        // Old code, bugged for unknown reasons (found out that BaseComponent[] must have length 1 or it bugs in HoverEvents)
        // this.chatDescription = AdvancementUtils.fromStringList(title, this.description);

        if (this.description.isEmpty()) {
            this.compactDescription = "";
        } else {
            StringJoiner joiner = new StringJoiner("\n" + defaultColor, defaultColor.toString(), "");
            for (String s : this.description)
                joiner.add(s);

            this.compactDescription = joiner.toString();
        }
        if (compactDescription.isEmpty()) {
            this.chatDescription[0] = new TextComponent(defaultColor + rawTitle);
        } else {
            this.chatDescription[0] = new TextComponent(defaultColor + rawTitle + '\n' + compactDescription);
        }

        this.frame = frame;
        this.showToast = showToast;
        this.announceChat = announceChat;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns whether the toast notification should be sent on advancement grant.
     *
     * @return Whether the toast notification should be sent on advancement grant.
     */
    public boolean doesShowToast() {
        return showToast;
    }

    /**
     * Returns whether the advancement completion message should be sent on advancement grant.
     *
     * @return Whether the advancement completion message should be sent on advancement grant.
     */
    public boolean doesAnnounceToChat() {
        return announceChat;
    }

    /**
     * Gets the {@link BaseComponent} array that contains the fancy title. Used by {@link Advancement#getAnnounceMessage(Player)}.
     *
     * @return The {@link BaseComponent} array that contains the fancy title.
     */
    @NotNull
    public BaseComponent[] getChatTitle() {
        return chatTitle.clone();
    }

    /**
     * Gets the {@link BaseComponent} array that contains the fancy description. Used by {@link Advancement#getAnnounceMessage(Player)}.
     *
     * @return The {@link BaseComponent} array that contains the fancy description.
     */
    @NotNull
    public BaseComponent[] getChatDescription() {
        return chatDescription.clone();
    }

    /**
     * Gets a clone of the icon.
     *
     * @return A clone of the icon.
     */
    @NotNull
    public ItemStack getIcon() {
        return icon.clone();
    }

    /**
     * Returns the {@code AdvancementDisplay} NMS wrapper, using the provided advancement for construction (when necessary).
     *
     * @param advancement The advancement used, when necessary, to create the NMS wrapper. Must be not {@code null}.
     * @return The {@code AdvancementDisplay} NMS wrapper.
     */
    @NotNull
    public AdvancementDisplayWrapper getNMSWrapper(@NotNull Advancement advancement) {
        Preconditions.checkNotNull(advancement, "Advancement is null.");
        try {
            AdvancementTab advancementTab = advancement.getAdvancementTab();
            float offsetX = advancementTab.getOffsetX();
            float offsetY = advancementTab.getOffsetY();
            if (advancement instanceof RootAdvancement root) {
                return AdvancementDisplayWrapper.craft(icon, title, compactDescription, frame.getNMSWrapper(), x + offsetX, y + offsetY, root.getBackgroundTexture());
            } else {
                return AdvancementDisplayWrapper.craft(icon, title, compactDescription, frame.getNMSWrapper(), x + offsetX, y + offsetY);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the title of the advancement.
     *
     * @return The title of the advancement.
     */
    @NotNull
    public String getTitle() {
        return title;
    }

    /**
     * Returns the trimmed title of the advancement.
     *
     * @return The trimmed title of the advancement.
     */
    @NotNull
    public String getRawTitle() {
        return rawTitle;
    }

    /**
     * Returns the description of the advancement.
     *
     * @return The description of the advancement.
     */
    @Unmodifiable
    public List<String> getDescription() {
        return description;
    }

    /**
     * Returns the compacted description.
     *
     * @return The compacted description.
     * @see #compactDescription
     */
    @NotNull
    public String getCompactDescription() {
        return compactDescription;
    }

    /**
     * Returns the shape of the advancement frame in the advancement GUI.
     *
     * @return The shape of the advancement frame in the advancement GUI.
     */
    @NotNull
    public AdvancementFrameType getFrame() {
        return frame;
    }

    /**
     * Returns the advancement position relative to the x-axis.
     *
     * @return The x coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the advancement position relative to the y-axis.
     *
     * @return The y coordinate.
     */
    public float getY() {
        return y;
    }

    /**
     * A builder for {@link AdvancementDisplay}.
     */
    public static class Builder extends AdvancementDisplayBuilder<Builder, AdvancementDisplay> {

        /**
         * Construct a new builder for the {@link AdvancementDisplay}.
         * <p>By default, the advancement won't show the toast message, and
         * won't announce the message in the chat upon completion.
         * <p>The default frame is {@link AdvancementFrameType#TASK}.
         *
         * @param icon The material of the advancement's icon in the advancement GUI.
         * @param title The title of the advancement.
         * @param x The advancement x coordinate.
         * @param y The advancement y coordinate.
         * @param description The description of the advancement.
         */
        public Builder(@NotNull Material icon, @NotNull String title, float x, float y, @NotNull String... description) {
            this(new ItemStack(icon), title, x, y, Arrays.asList(description));
        }

        /**
         * Construct a new builder for the {@link AdvancementDisplay}.
         * <p>By default, the advancement won't show the toast message, and
         * won't announce the message in the chat upon completion.
         * <p>The default frame is {@link AdvancementFrameType#TASK}.
         *
         * @param icon The material of the advancement's icon in the advancement GUI.
         * @param title The title of the advancement.
         * @param x The advancement x coordinate.
         * @param y The advancement y coordinate.
         * @param description The description of the advancement.
         */
        public Builder(@NotNull Material icon, @NotNull String title, float x, float y, @NotNull List<String> description) {
            this(new ItemStack(icon), title, x, y, description);
        }

        /**
         * Construct a new builder for the {@link AdvancementDisplay}.
         * <p>By default, the advancement won't show the toast message, and
         * won't announce the message in the chat upon completion.
         * <p>The default frame is {@link AdvancementFrameType#TASK}.
         *
         * @param icon The advancement's icon in the advancement GUI.
         * @param title The title of the advancement.
         * @param x The advancement x coordinate.
         * @param y The advancement y coordinate.
         * @param description The description of the advancement.
         */
        public Builder(@NotNull ItemStack icon, @NotNull String title, float x, float y, @NotNull String... description) {
            this(icon, title, x, y, Arrays.asList(description));
        }

        /**
         * Construct a new builder for the {@link AdvancementDisplay}.
         * <p>By default, the advancement won't show the toast message, and
         * won't announce the message in the chat upon completion.
         * <p>The default frame is {@link AdvancementFrameType#TASK}.
         *
         * @param icon The advancement's icon in the advancement GUI.
         * @param title The title of the advancement.
         * @param x The advancement x coordinate.
         * @param y The advancement y coordinate.
         * @param description The description of the advancement.
         */
        public Builder(@NotNull ItemStack icon, @NotNull String title, float x, float y, @NotNull List<String> description) {
            super(icon, title, x, y, description);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public AdvancementDisplay build() {
            if (frame == null) frame = AdvancementFrameType.TASK;
            if (defaultColor == null) defaultColor = frame.getColor();
            return new AdvancementDisplay(icon, title, frame, showToast, announceChat, x, y, description);
        }
    }
}
