package com.fren_gor.ultimateAdvancementAPI.advancement;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.multiParents.MultiParentsAdvancement;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.util.AfterHandle;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_15_R1.AdvancementProgress;
import net.minecraft.server.v1_15_R1.ChatComponentText;
import net.minecraft.server.v1_15_R1.Criterion;
import net.minecraft.server.v1_15_R1.MinecraftKey;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils.ADV_REWARDS;
import static com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils.getAdvancementCriteria;
import static com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils.getAdvancementRequirements;

/**
 * FakeAdvancements are advancements that are created and used only to recreate visible lines and connect multiple advancements.
 * <p>They are used in the API for {@link MultiParentsAdvancement} management. These are not saved to the database.
 * <p>Many of the methods are overridden and cannot be used.
 */
public final class FakeAdvancement extends BaseAdvancement {

    private static final AtomicInteger FAKE_NUMBER = new AtomicInteger(1);

    private net.minecraft.server.v1_15_R1.Advancement mcAdvancement;

    /**
     * Create a new FakeAdvancement.
     *
     * @param parent The parent of the advancement.
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public FakeAdvancement(@NotNull Advancement parent, float x, float y) {
        this(parent, new FakeAdvancementDisplay(Material.GRASS_BLOCK, "FakeAdvancement", AdvancementFrameType.TASK, x, y));
    }

    /**
     * Create a new FakeAdvancement.
     *
     * @param parent The parent of the advancement.
     * @param display The {@link FakeAdvancementDisplay}.
     */
    public FakeAdvancement(@NotNull Advancement parent, @NotNull FakeAdvancementDisplay display) {
        super("fakeadvancement._-.-_." + FAKE_NUMBER.getAndIncrement(), display, parent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public FakeAdvancementDisplay getDisplay() {
        return (FakeAdvancementDisplay) super.getDisplay();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public net.minecraft.server.v1_15_R1.Advancement getMinecraftAdvancement() {
        if (mcAdvancement != null) {
            return mcAdvancement;
        }

        Map<String, Criterion> advCriteria = getAdvancementCriteria(maxCriteria);
        return mcAdvancement = new net.minecraft.server.v1_15_R1.Advancement(getMinecraftKey(), parent.getMinecraftAdvancement(), display.getMinecraftDisplay(this), ADV_REWARDS, advCriteria, getAdvancementRequirements(advCriteria));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code 0} every time.
     */
    @Override
    public int getTeamCriteria(@NotNull Player player) {
        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code 0} every time.
     */
    @Override
    public int getTeamCriteria(@NotNull UUID uuid) {
        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code 0} every time.
     */
    @Override
    public int getTeamCriteria(@NotNull TeamProgression progression) {
        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true} every time.
     */
    @Override
    @Contract("_ -> true")
    public boolean isVisible(@NotNull Player player) {
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true} every time.
     */
    @Override
    @Contract("_ -> true")
    public boolean isVisible(@NotNull UUID uuid) {
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true} every time.
     */
    @Override
    @Contract("_ -> true")
    public boolean isVisible(@NotNull TeamProgression progression) {
        return true;
    }

    //TODO
    // Since isVisible() returns true for every input we can use the super method
    @Override
    public void onUpdate(@NotNull TeamProgression teamProgression, @NotNull Set<net.minecraft.server.v1_15_R1.Advancement> advancementList, @NotNull Map<MinecraftKey, AdvancementProgress> progresses, @NotNull Set<MinecraftKey> added) {
        // Keep to avoid accidental inclusion in UnsupportedOperationException methods down below
        super.onUpdate(teamProgression, advancementList, progresses, added);
    }
    // instead of this version down below
    /*@Override
    public void onUpdate(@NotNull TeamProgression teamProgression, @NotNull Set<net.minecraft.server.v1_15_R1.Advancement> advancementList, @NotNull Map<MinecraftKey, AdvancementProgress> progresses, @NotNull Set<MinecraftKey> added) {
        net.minecraft.server.v1_15_R1.Advancement adv = getMinecraftAdvancement();
        advancementList.add(adv);

        // Inlining of getAdvancementProgress()
        AdvancementProgress advPrg = new AdvancementProgress();
        advPrg.a(adv.getCriteria(), adv.i());

        MinecraftKey key = getMinecraftKey();
        added.add(key);
        progresses.put(key, advPrg);
    }*/

    /**
     * The display for the {@link FakeAdvancement}.
     *
     * @see AdvancementDisplay
     */
    public static final class FakeAdvancementDisplay extends AdvancementDisplay {

        /**
         * Create a new FakeAdvancementDisplay.
         *
         * @param icon What item will be shown on the GUI.
         * @param title The title of the advancement.
         * @param frame Which shape has the advancement.
         * @param x X coordinate.
         * @param y Y coordinate.
         */
        public FakeAdvancementDisplay(@NotNull Material icon, @NotNull String title, @NotNull AdvancementFrameType frame, float x, float y) {
            super(icon, title, frame, false, false, x, y, Collections.emptyList());
        }

        /**
         * Create a new FakeAdvancementDisplay.
         *
         * @param icon What item will be shown on the GUI.
         * @param title The title of the advancement.
         * @param frame Which shape has the advancement.
         * @param x X coordinate.
         * @param y Y coordinate.
         */
        public FakeAdvancementDisplay(@NotNull ItemStack icon, @NotNull String title, @NotNull AdvancementFrameType frame, float x, float y) {
            super(icon, title, frame, false, false, x, y, Collections.emptyList());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NotNull
        public net.minecraft.server.v1_15_R1.AdvancementDisplay getMinecraftDisplay(@NotNull Advancement advancement) {
            net.minecraft.server.v1_15_R1.AdvancementDisplay advDisplay = new net.minecraft.server.v1_15_R1.AdvancementDisplay(getNMSIcon(), new ChatComponentText(title), new ChatComponentText(compactDescription), null, frame.getMinecraftFrameType(), false, false, true);
            advDisplay.a(x, y);
            return advDisplay;
        }
    }

    // ============ Overridden methods which throw an UnsupportedOperationException ============

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public boolean isGranted(@NotNull Player player) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public boolean isGranted(@NotNull UUID uuid) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public boolean isGranted(@NotNull TeamProgression progression) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public @Nullable BaseComponent[] getAnnounceMessage(@NotNull Player player) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int incrementTeamCriteria(@NotNull UUID uuid) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int incrementTeamCriteria(@NotNull UUID uuid, boolean giveReward) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int incrementTeamCriteria(@NotNull UUID uuid, @Range(from = 0, to = Integer.MAX_VALUE) int increment) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int incrementTeamCriteria(@NotNull UUID uuid, @Range(from = 0, to = Integer.MAX_VALUE) int increment, boolean giveReward) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int incrementTeamCriteria(@NotNull Player player) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int incrementTeamCriteria(@NotNull Player player, boolean giveReward) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int incrementTeamCriteria(@NotNull Player player, @Range(from = 0, to = Integer.MAX_VALUE) int increment) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int incrementTeamCriteria(@NotNull Player player, @Range(from = 0, to = Integer.MAX_VALUE) int increment, boolean giveReward) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    protected @Range(from = 0, to = Integer.MAX_VALUE) int incrementTeamCriteria(@NotNull TeamProgression pro, @Nullable Player player, @Range(from = 0, to = Integer.MAX_VALUE) int increment, boolean giveRewards) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public void setCriteriaTeamProgression(@NotNull UUID uuid, @Range(from = 0, to = Integer.MAX_VALUE) int criteria) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public void setCriteriaTeamProgression(@NotNull UUID uuid, @Range(from = 0, to = Integer.MAX_VALUE) int criteria, boolean giveReward) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public void setCriteriaTeamProgression(@NotNull Player player, @Range(from = 0, to = Integer.MAX_VALUE) int criteria) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public void setCriteriaTeamProgression(@NotNull Player player, @Range(from = 0, to = Integer.MAX_VALUE) int criteria, boolean giveReward) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    protected void setCriteriaTeamProgression(@NotNull TeamProgression pro, @Nullable Player player, @Range(from = 0, to = Integer.MAX_VALUE) int criteria, boolean giveRewards) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    protected void handlePlayer(@NotNull TeamProgression pro, @Nullable Player player, int criteria, int old, boolean giveRewards, @Nullable AfterHandle afterHandle) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public void displayToastToPlayer(@NotNull Player player) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public boolean isShownTo(Player player) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public void onGrant(@NotNull Player player, boolean giveRewards) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public void giveReward(@NotNull Player player) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public void grant(@NotNull Player player) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public void grant(@NotNull Player player, boolean giveRewards) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException Always when the method is called.
     */
    @Override
    public void revoke(@NotNull Player player) {
        throw new UnsupportedOperationException();
    }
}
