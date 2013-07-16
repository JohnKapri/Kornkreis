package com.johnkapri.kornkreis.level.block;

import com.johnkapri.kornkreis.Sound;
import com.johnkapri.kornkreis.entities.*;
import com.johnkapri.kornkreis.gui.Sprite;

public class PitBlock extends Block {
	private boolean filled = false;

	public PitBlock() {
		floorTex = 1;
		blocksMotion = true;
	}

	public void addEntity(Entity entity) {
		super.addEntity(entity);
		if (!filled && entity instanceof BoulderEntity) {
			entity.remove();
			filled = true;
			blocksMotion = false;
			addSprite(new Sprite(0, 0, 0, 8 + 2, BoulderEntity.COLOR));
			Sound.thud.play();
			canStand = true;
		}
	}

	public boolean blocks(Entity entity) {
		if (entity instanceof BoulderEntity) return false;
		return blocksMotion;
	}
}
