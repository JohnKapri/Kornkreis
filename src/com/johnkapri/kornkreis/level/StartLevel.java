package com.johnkapri.kornkreis.level;

import com.johnkapri.kornkreis.level.block.Block;

public class StartLevel extends Level {
	public StartLevel() {
		name = "The Prison";
		wallCol = 0x00FFFF;
		floorCol = 0x00FFFF;
		ceilCol = 0x00FFFF;
	}

	protected void decorateBlock(int x, int y, Block block, int col) {
		super.decorateBlock(x, y, block, col);
	}

	protected Block getBlock(int x, int y, int col) {
		return super.getBlock(x, y, col);
	}

	public void switchLevel(int id) {
		if (id == 1)
			game.switchLevel("overworld", 1);
		if (id == 2)
			game.switchLevel("dungeon", 1);
	}

	public void getLoot(int id) {
		super.getLoot(id);
	}
}