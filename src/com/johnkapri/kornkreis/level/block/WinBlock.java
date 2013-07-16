package com.johnkapri.kornkreis.level.block;

import com.johnkapri.kornkreis.entities.*;

public class WinBlock extends Block {
	public void addEntity(Entity entity) {
		super.addEntity(entity);
		if (entity instanceof Player) {
			((Player)entity).win();
		}
	}
}
