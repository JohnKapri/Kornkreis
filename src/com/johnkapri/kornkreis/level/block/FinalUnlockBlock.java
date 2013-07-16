package com.johnkapri.kornkreis.level.block;

import com.johnkapri.kornkreis.Sound;
import com.johnkapri.kornkreis.entities.Item;
import com.johnkapri.kornkreis.level.Level;

public class FinalUnlockBlock extends SolidBlock {
	private boolean pressed = false;

	public FinalUnlockBlock() {
		tex = 8 + 3;
	}

	public boolean use(Level level, Item item) {
		if (pressed) return false;
		if (level.player.keys < 4) return false;

		Sound.click1.play();
		pressed = true;
		level.trigger(id, true);

		return true;
	}
}
