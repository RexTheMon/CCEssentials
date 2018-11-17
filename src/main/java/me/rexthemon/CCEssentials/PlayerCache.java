package me.rexthemon.CCEssentials;

import java.util.UUID;

import org.bukkit.Location;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class PlayerCache {

	private final UUID id;

	@Setter
	private Location primaryLoc, secondaryLoc;
}

