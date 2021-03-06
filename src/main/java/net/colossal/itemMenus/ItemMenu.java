package net.colossal.itemMenus;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import java.util.UUID;

/**
 * A Menu controlled by ItemStacks in an Inventory.
 *
 * @author kh498
 * @author nuclearcat1337
 * @author MrLittleKitty (taken from
 *         <a href="https://github.com/MrLittleKitty/AnnihilationPro">here</a>)
 * @author ampayne2 (orginal author, <a href="https://github.com/ampayne2/AmpMenus">github</a>)
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class ItemMenu {
    /**
     * The {@link net.colossal.itemMenus.StaticMenuItem StaticMenuItem} that appears in empty
     * slots if {@link ItemMenu#fillEmptySlots()}   is
     * called.
     */
    @SuppressWarnings("deprecation") private static final MenuItem EMPTY_SLOT_ITEM =
        new StaticMenuItem(" ", new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData()));
    private String name;
    private Size size;
    private MenuItem[] items;
    private ItemMenu parent;

    /**
     * Creates an ItemMenu.
     *
     * @param name   The name of the inventory.
     * @param size   The {@link Size Size} of the inventory.
     * @param parent The ItemMenu's parent.
     * @param items  The items that this menu contains
     */
    public ItemMenu(final String name, final Size size, @Nullable final ItemMenu parent,
                    @Nullable final MenuItem[] items) {
        this.name = name;
        this.size = size;
        this.parent = parent;
        this.items = items;
    }

    /**
     * Creates an ItemMenu.
     *
     * @param name   The name of the inventory.
     * @param size   The {@link Size Size} of the inventory.
     * @param parent The ItemMenu's parent.
     */
    public ItemMenu(final String name, final Size size, @Nullable final ItemMenu parent) {
        this(name, size, parent, new MenuItem[size.getSize()]);
    }

    /**
     * Creates an ItemMenu with no parent.
     *
     * @param name The name of the inventory.
     * @param size The {@link Size Size} of the inventory.
     */
    public ItemMenu(final String name, final Size size) {
        this(name, size, null);
    }
    /**
     * @return A copy of the original ItemMenu
     */
    private static ItemMenu copy(final ItemMenu orgMenu) {
        return new ItemMenu(orgMenu.name, orgMenu.size, orgMenu.parent, orgMenu.items);
    }
    /**
     * Gets the name of the ItemMenu.
     *
     * @return The ItemMenu's name.
     */
    public String getName() {
        return this.name;
    }
    /**
     * @param name the name to set
     *
     * @return The ItemMenu.
     */
    public ItemMenu setName(final String name) {
        this.name = name;
        return this;
    }
    /**
     * @return The ItemMenu's
     * {@link Size Size}.
     */
    public Size getSize() {
        return this.size;
    }
    /**
     * Checks if the ItemMenu has a parent.
     *
     * @return True if the ItemMenu has a parent,
     * else false.
     */
    public boolean hasParent() {
        return this.parent != null;
    }
    /**
     * Gets the parent of the ItemMenu.
     *
     * @return The ItemMenu's parent.
     */
    public ItemMenu getParent() {
        return this.parent;
    }
    /**
     * Sets the parent of the ItemMenu.
     *
     * @param parent The ItemMenu's parent.
     *
     * @return The ItemMenu.
     */
    public ItemMenu setParent(final ItemMenu parent) {
        this.parent = parent;
        return this;
    }
    /**
     * Sets the {@link net.colossal.itemMenus.MenuItem MenuItem} of a slot.
     *
     * @param position The slot position.
     * @param menuItem The {@link net.colossal.itemMenus.MenuItem MenuItem}.
     *
     * @return The ItemMenu.
     */
    public ItemMenu setItem(final int position, final MenuItem menuItem) {
        this.items[position] = menuItem;
        return this;
    }

    /**
     * Sets the {@link net.colossal.itemMenus.MenuItem MenuItem} of a slot.
     *
     * @param position The common position of the item
     * @param menuItem The {@link net.colossal.itemMenus.MenuItem MenuItem}.
     *
     * @return The ItemMenu.
     */
    public ItemMenu setItem(final CommonPositions position, final MenuItem menuItem) {
        this.items[position.getPosition()] = menuItem;
        return this;
    }

    /**
     * Sets the {@link net.colossal.itemMenus.MenuItem MenuItem} of a slot.
     *
     * @param position The common position of the item
     * @param row      What row of the item to clear
     * @param menuItem The {@link net.colossal.itemMenus.MenuItem MenuItem}.
     *
     * @return The ItemMenu.
     */
    public ItemMenu setItem(final CommonPositions position, final Size row, final MenuItem menuItem) {
        this.items[position.getPosition(row)] = menuItem;
        return this;
    }

    /**
     * @return All {@link net.colossal.itemMenus.MenuItem MenuItem}s in a
     * ItemMenu
     */
    private MenuItem[] getItems() {
        return this.items;
    }
    /**
     * @return A copy of this ItemMenu
     */
    public ItemMenu copy() {
        return ItemMenu.copy(this);
    }

    /**
     * Remove a {@link net.colossal.itemMenus.MenuItem MenuItem} at a certain position in the
     * ItemMenu
     *
     * @param position Position to clear
     *
     * @return The ItemMenu.
     */
    public ItemMenu clearItem(final int position) {
        this.items[position] = new MenuItem("", new ItemStack(Material.AIR));
        return this;
    }

    /**
     * Remove a {@link net.colossal.itemMenus.MenuItem MenuItem} at a common position in the
     * ItemMenu
     *
     * @param position Position to clear
     *
     * @return The ItemMenu.
     */
    public ItemMenu clearItem(final CommonPositions position) {
        this.items[position.getPosition()] = new MenuItem("", new ItemStack(Material.AIR));
        return this;
    }

    /**
     * Remove a {@link net.colossal.itemMenus.MenuItem MenuItem} at a common position in the
     * ItemMenu
     *
     * @param position Position to clear
     * @param row      What row of the item to clear
     *
     * @return The ItemMenu.
     */
    public ItemMenu clearItem(final CommonPositions position, final Size row) {
        this.items[position.getPosition(row)] = new MenuItem("", new ItemStack(Material.AIR));
        return this;
    }

    /**
     * Remove all {@link net.colossal.itemMenus.MenuItem MenuItem}s in the
     * ItemMenu
     *
     * @return The ItemMenu.
     */
    public ItemMenu clearAllItems() {
        this.items = new MenuItem[this.getSize().getSize()];
        return this;
    }

    /**
     * Fills all empty slots in the ItemMenu with a
     * certain {@link net.colossal.itemMenus.MenuItem MenuItem}.
     *
     * @param menuItem The {@link net.colossal.itemMenus.MenuItem MenuItem} to fill with.
     *
     * @return The ItemMenu.
     */
    public ItemMenu fillEmptySlots(final MenuItem menuItem) {
        for (int i = 0; i < this.items.length; i++) {
            if (this.items[i] == null) {
                this.items[i] = menuItem;
            }
        }
        return this;
    }

    /**
     * Fills all empty slots in the ItemMenu with the
     * default {@link net.colossal.itemMenus.ItemMenu#EMPTY_SLOT_ITEM EMPTY_SLOT_ITEM}.
     *
     * @return The ItemMenu.
     */
    public ItemMenu fillEmptySlots() {
        return fillEmptySlots(EMPTY_SLOT_ITEM);
    }

    /**
     * Opens the ItemMenu for a player.
     *
     * @param player The player.
     *
     * @return The ItemMenu.
     */
    public ItemMenu open(final Player player) {
        //check if the the plugin is null
        ItemMenuListener.getPlugin();

        final Inventory inventory = Bukkit
            .createInventory(new ItemMenuHolder(this, Bukkit.createInventory(player, this.size.getSize())),
                             this.size.getSize(), this.name);
        apply(inventory, player);
        player.openInventory(inventory);
        return this;
    }

    /**
     * Updates the ItemMenu for a player.
     *
     * @param player The player to update the ItemMenu for.
     *
     * @return The ItemMenu.
     */
    public ItemMenu update(final Player player) {
        if (player.getOpenInventory() != null) {
            final Inventory inventory = player.getOpenInventory().getTopInventory();
            if (inventory.getHolder() instanceof ItemMenuHolder &&
                ((ItemMenuHolder) inventory.getHolder()).getMenu().equals(this)) {
                apply(inventory, player);
                //noinspection deprecation
                player.updateInventory();
            }
        }
        return this;
    }

    /**
     * Applies the {@link net.colossal.itemMenus.MenuItem MenuItem}s for a player to an
     * Inventory.
     *
     * @param inventory The Inventory.
     * @param player    The Player.
     *
     * @return The ItemMenu.
     */
    private ItemMenu apply(final Inventory inventory, final Player player) {
        for (int i = 0; i < this.items.length; i++) {
            if (this.items[i] != null) {
                inventory.setItem(i, this.items[i].getFinalIcon(player));
            }
        }
        return this;
    }

    /**
     * Handles InventoryClickEvents for the {@link MenuItem}.
     */
    void onInventoryClick(final InventoryClickEvent event) {
        if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT ||
            event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
            final int slot = event.getRawSlot();
            if (slot >= 0 && slot < this.size.getSize() && this.items[slot] != null) {
                final Player player = (Player) event.getWhoClicked();
                final ItemClickEvent itemClickEvent =
                    new ItemClickEvent(player, event.getCurrentItem(), event.getClick(), this, slot);
                this.items[slot].onItemClick(itemClickEvent);
                if (itemClickEvent.willUpdate()) {
                    update(player);
                }
                else {
                    //noinspection deprecation
                    player.updateInventory();
                    if (itemClickEvent.willClose()) {
                        final UUID playerUUID = player.getUniqueId();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(ItemMenuListener.getPlugin(), () -> {
                            final Player p = Bukkit.getPlayer(playerUUID);
                            if (p != null) {
                                p.closeInventory();
                            }
                        }, 1L);
                    }
                    if (itemClickEvent.willGoBack() && hasParent()) {
                        final UUID playerUUID = player.getUniqueId();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(ItemMenuListener.getPlugin(), () -> {
                            final Player p = Bukkit.getPlayer(playerUUID);
                            if (p != null) {
                                p.closeInventory();
                                ItemMenu.this.parent.open(p);
                            }
                        }, 1L);
                    }
                }
            }
        }
    }

    /**
     * Destroys the ItemMenu.
     */
    public void destroy() {
        this.name = null;
        this.size = null;
        this.items = null;
        this.parent = null;
    }
}

