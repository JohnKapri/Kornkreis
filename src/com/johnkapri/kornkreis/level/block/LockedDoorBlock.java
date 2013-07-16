package com.johnkapri.kornkreis.level.block;

import com.johnkapri.kornkreis.entities.*;
import com.johnkapri.kornkreis.level.Level;

public class LockedDoorBlock extends DoorBlock {
	public LockedDoorBlock() {
		tex = 5;
	}

	public boolean use(Level level, Item item) {
		return false;
	}

	public void trigger(boolean pressed) {
		open = pressed;
		canStand = open;
	}

}
