package com.johnkapri.kornkreis.menu;

import com.johnkapri.kornkreis.*;
import com.johnkapri.kornkreis.entities.Player;
import com.johnkapri.kornkreis.gui.Bitmap;

public class LoseMenu extends Menu {
	private int tickDelay = 30;

	private Player player;

	public LoseMenu(Player player) {
		this.player = player;
	}

	public void render(Bitmap target) {
		target.draw(Art.logo, 0, 10, 0, 39, 160, 23, Art.getCol(0xffffff));

		int seconds = player.time / 60;
		int minutes = seconds / 60;
		seconds %= 60;
		String timeString = minutes + ":";
		if (seconds < 10) timeString += "0";
		timeString += seconds;
		target.draw("Trinkets: " + player.loot + "/12", 40, 45 + 10 * 0, Art.getCol(0x909090));
		target.draw("Time: " + timeString, 40, 45 + 10 * 1, Art.getCol(0x909090));

		if (tickDelay == 0) target.draw("-> Continue", 40, target.height - 40, Art.getCol(0xffff80));
	}

	public void tick(Game game, boolean up, boolean down, boolean left, boolean right, boolean use) {
		if (tickDelay > 0) tickDelay--;
		else if (use) {
			Sound.click1.play();
			game.setMenu(new TitleMenu());
		}
	}
}