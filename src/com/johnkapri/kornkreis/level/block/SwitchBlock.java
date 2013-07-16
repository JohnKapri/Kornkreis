package com.johnkapri.kornkreis.level.block;

import com.johnkapri.kornkreis.Sound;
import com.johnkapri.kornkreis.entities.Item;
import com.johnkapri.kornkreis.level.Level;

public class SwitchBlock extends SolidBlock {
	private boolean pressed = false;

	public SwitchBlock() {
		tex = 2;
	}

	public boolean use(Level level, Item item) {
		pressed = !pressed;
		if (pressed) tex = 3;
		else tex = 2;
		
		level.trigger(id, pressed);
		if (pressed)
			Sound.click1.play();
		else
			Sound.click2.play();

		return true;
	}
}
