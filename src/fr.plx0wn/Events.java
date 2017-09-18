package fr.lucluc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class Events implements Listener {

	Plugin plugin = Eggrenade.instance;
	
	public static ItemStack eggGrenade(){
		ItemStack is = new ItemStack(Material.EGG);
		ItemMeta ismet = is.getItemMeta();
		ismet.setDisplayName("censured-name"); // Sorry, the name is censured.
 		is.setItemMeta(ismet);
		return is;
	}

	@EventHandler
	public void onEgg(PlayerEggThrowEvent e) {

		BukkitScheduler scheduler = plugin.getServer().getScheduler();
		Player player = e.getPlayer();
		Entity egg = e.getEgg();
		final World w = egg.getWorld();
		final Location l = egg.getLocation();
		final double x = l.getX();
		final double y = l.getY();
		final double z = l.getZ();

		if (player.hasPermission("grenade.use")) {

			final Item grenade = w.dropItem(l.add(new Vector(0.0, 0.0, 0.0)), eggGrenade());
			if(plugin.getConfig().getBoolean("messages.enable")){
				Eggrenade.sm(player, plugin.getConfig().getString("messages.throw"));
			}

			scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					w.createExplosion(x, y, z, plugin.getConfig().getInt("explosion.radius"), false, plugin.getConfig().getBoolean("damage.block"));
					grenade.remove();

				}
			}, 20);
		}

	}
	
//	@EventHandler
//	public void onEntityDamage(EntityDamageEvent e){
//		if(e.getEntity() instanceof Player){
//			if(e.getCause().equals(DamageCause.BLOCK_EXPLOSION)){
//				e.setCancelled(!plugin.getConfig().getBoolean("damage.player"));
//			}
//		} else {
//			if(e.getCause().equals(DamageCause.BLOCK_EXPLOSION)){
//				e.setCancelled(!plugin.getConfig().getBoolean("damage.other-entity"));
//			}
//		}
//	}

	@EventHandler
	public void onChickSpawn(CreatureSpawnEvent e) {
		Entity entity = e.getEntity();
		if (entity.getType() == EntityType.CHICKEN) {
			e.setCancelled(plugin.getConfig().getBoolean("disable-chicken"));
		}
	}

	@EventHandler
	public void onPick(PlayerPickupItemEvent e) {
		if (e.getItem().getItemStack().isSimilar(eggGrenade())) {
			e.setCancelled(true);
		}
	}

}
