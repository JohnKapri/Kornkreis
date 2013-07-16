package com.johnkapri.kornkreis.level.block;

import com.johnkapri.kornkreis.Art;
import com.johnkapri.kornkreis.entities.*;
import com.johnkapri.kornkreis.gui.Sprite;

public class SpiritWallBlock extends Block {
	public SpiritWallBlock() {
		floorTex = 7;
		ceilTex = 7;
		blocksMotion = true;
		for (int i = 0; i < 6; i++) {
			double x = (random.nextDouble() - 0.5);
			double y = (random.nextDouble() - 0.7) * 0.3;
			double z = (random.nextDouble() - 0.5);
			addSprite(new Sprite(x, y, z, 4 * 8 + 6 + random.nextInt(2), Art.getCol(0x202020)));
		}
	}

	public boolean blocks(Entity entity) {
		if (entity instanceof Bullet) return false;
		return super.blocks(entity);
	}
}
