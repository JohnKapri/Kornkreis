package com.johnkapri.kornkreis.level.block;

import com.johnkapri.kornkreis.Sound;
import com.johnkapri.kornkreis.entities.Item;
import com.johnkapri.kornkreis.gui.RubbleSprite;
import com.johnkapri.kornkreis.level.Level;

public class VanishBlock extends SolidBlock {
	private boolean gone = false;

	public VanishBlock() {
		tex = 1;
	}

	public boolean use(Level level, Item item) {
		if (gone) return false;

		gone = true;
		blocksMotion = false;
		solidRender = false;
		Sound.crumble.play();
		
		canStand = true;

		for (int i = 0; i < 32; i++) {
			RubbleSprite sprite = new RubbleSprite();
			sprite.col = col;
			addSprite(sprite);
		}

		return true;
	}
}
