package me.rexthemon.CCEssentials.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import me.rexthemon.CCEssentials.CCEssentials;
import me.rexthemon.lib.Core;

public class RandomTPCommand extends PlayerCommand {

	private static Random rand = new Random();
	private static final int COOLDOWN_IN_SECONDS = 10;
	private static final int TRIES = 10;

	private static final Cache<UUID, Long> cache = CacheBuilder.newBuilder().expireAfterWrite(COOLDOWN_IN_SECONDS, TimeUnit.SECONDS).build();

	public RandomTPCommand() {
		super("randomtp");

		setAliases(Arrays.asList("rtp"));
		setDescription("Teleport to a random location.");
	}

	@Override
	protected void run(Player player, String[] args) {

		checkArgsStrict(1, "&cPlease specify the range for the teleportation.");

		final int range = getNumber(0, 10, 10_000, "&cPlease specify a valid whole number from {min} - {max}.");

		final long lastTry = Core.getOrDefault(cache.getIfPresent(player.getUniqueId()), 0L);
		final long diff = (System.currentTimeMillis() - lastTry) / 1000;

		if (lastTry != 0)
			returnMsg("&cPlease wait " + (COOLDOWN_IN_SECONDS - diff) + " second(s) before teleporting again.");
		else
			cache.put(player.getUniqueId(), System.currentTimeMillis());

		final Location loc = findLocation(player.getWorld().getSpawnLocation(), range);

		checkNotNull(loc, "&cCould not find a safe location. &aPlease try again later!");

		if (!loc.getChunk().isLoaded())
			loc.getChunk().load(true);

		msg("&aPlease wait while a random location is being found...");

		loc.add(0.5, 0, 0.5);
		loc.setYaw(0);
		loc.setPitch(90);

		player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, (float) Math.random());
		player.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, null);

		new BukkitRunnable() {

			private boolean told = false;
			private int startingHeight = loc.getWorld().getMaxHeight();
			private final int goingDown = 80;
			private float prevWalkSpeed, prevFlySpeed;

			@Override
			public void run() {

				if (!told) {
					told = true;

					prevWalkSpeed = player.getWalkSpeed();
					prevFlySpeed = player.getFlySpeed();

					player.setWalkSpeed(0);
					player.setFlySpeed(0);
					player.setAllowFlight(true);
					player.setFlying(true);
					player.setGravity(false);
					player.setInvulnerable(true);

					msg("&aYou are being teleported to a random location.");
				}

				if (startingHeight <= goingDown) {
					cancel();

					final Location finalLoc = player.getLocation().clone().add(0, -startingHeight, 0);
					finalLoc.setYaw(90);
					finalLoc.setPitch(0);

					player.teleport(finalLoc);

					player.setAllowFlight(false);
					player.setFlying(false);
					player.setGravity(true);
					player.setInvulnerable(false);
					player.setWalkSpeed(prevWalkSpeed);
					player.setFlySpeed(prevFlySpeed);

					player.playSound(player.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_PREPARE_ATTACK, 1, 1);

					return;
				}

				startingHeight -= goingDown;

				player.teleport(loc.clone().add(0, startingHeight, 0));
				player.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, null);
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, (float) Math.random());
			}
		}.runTaskTimer(CCEssentials.getInstance(), 20 * 2, 15);
	}

	private final Location findLocation(Location center, int range) {

		for (int i = 0; i < TRIES; i++) {
			final int x = center.getBlockX() + generateOffset(range), z = center.getBlockZ() + generateOffset(range);
			final int y = getHighestPoint(center.getWorld(), x, z);



			final Block above = center.getWorld().getBlockAt(x, y, z);
			final Block ground = above.getRelative(BlockFace.DOWN);
			final Block above2 = above.getRelative(BlockFace.UP);

			if (y != -1 && above.getType() == Material.AIR && ground.getType().isSolid() && above2.getType() == Material.AIR)
				return new Location(center.getWorld(), x, y, z);
		}

		return null;
	}

	private final int getHighestPoint(World w, int x, int z) {
		final List<Material> allowedMaterial = Arrays.asList(Material.AIR, Material.LEAVES, Material.LEAVES_2);

		for (int y = w.getMaxHeight(); y > 0; y--) {
			final Material m = w.getBlockAt(x, y, z).getType();

			if (!allowedMaterial.contains(m))
				return y + 1;
		}

		return -1;
	}

	private final int generateOffset(int range) {
		final int number = rand.nextInt(range);

		return rand.nextBoolean() ? number : -number;
	}
}