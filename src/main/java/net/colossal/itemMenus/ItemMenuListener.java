package net.colossal.itemMenus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Passes inventory CorrectNPCRightClick events to their menus for handling.
 */
public class ItemMenuListener implements Listener {
    private static final ItemMenuListener INSTANCE = new ItemMenuListener();
    private Plugin plugin;

    private ItemMenuListener() {}

    /**
     * Gets the ItemMenuListener instance.
     *
     * @return The ItemMenuListener instance.
     */
    public static ItemMenuListener getInstance() {
        return INSTANCE;
    }

    public static Plugin getPlugin() {
        if (INSTANCE.plugin == null) {
            throw new NullPointerException("No plugin has been registered to ItemMenuListener");
        }
        return INSTANCE.plugin;
    }


    /**
     * Closes all {@link net.colossal.itemMenus.ItemMenu}s currently open.
     */
    public static void closeOpenMenus() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory() != null) {
                final Inventory inventory = player.getOpenInventory().getTopInventory();
                if (inventory.getHolder() instanceof ItemMenuHolder) {
                    player.closeInventory();
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public static void onInventoryClick(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() != null &&
            event.getInventory().getHolder() instanceof ItemMenuHolder) {
            event.setCancelled(true);
            ((ItemMenuHolder) event.getInventory().getHolder()).getMenu().onInventoryClick(event);
        }
    }
    /**
     * Registers the events of the ItemMenuListener to a plugin.
     *
     * @param plugin The plugin used to register the events.
     */
    public void register(final JavaPlugin plugin) {
        if (!isRegistered(plugin)) {
            plugin.getServer().getPluginManager().registerEvents(INSTANCE, plugin);
            this.plugin = plugin;
        }
    }
    /**
     * Checks if the ItemMenuListener is registered to a plugin.
     *
     * @param plugin The plugin.
     *
     * @return True if the ItemMenuListener is registered to the
     * plugin, else false.
     */
    public boolean isRegistered(final JavaPlugin plugin) {
        if (plugin.equals(this.plugin)) {
            for (final RegisteredListener listener : HandlerList.getRegisteredListeners(plugin)) {
                if (listener.getListener().equals(INSTANCE)) {
                    return true;
                }
            }
        }
        return false;
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPluginDisable(final PluginDisableEvent event) {
        if (event.getPlugin().equals(this.plugin)) {
            closeOpenMenus();
            this.plugin = null;
        }
    }
}
