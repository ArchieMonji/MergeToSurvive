package com.vgdc.merge.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;

public class PlayerHealthDisplay extends Widget {
	private Entity player;

	private Texture borderTexture;
	private Texture barBgTexture;
	private Texture hpTexture;
	private Texture spacerTexture;

	private float spacerWidth = 4;
	private Vector2 borderSize = new Vector2(4, 4);

	public PlayerHealthDisplay() {
		borderTexture = new Texture("data/ui/player_health_display/border.png");
		barBgTexture = new Texture("data/ui/player_health_display/bar_background.png");
		hpTexture = new Texture("data/ui/player_health_display/health_bar.png");
		spacerTexture = new Texture("data/ui/player_health_display/spacer.png");
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}

	public void setBorderSize(float x, float y) {
		borderSize.x = x;
		borderSize.y = y;
	}

	public void setSpacerWidth(float w) {
		spacerWidth = w;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		batch.setColor(1, 1, 1, parentAlpha);
		float barWidth = getWidth();
		float barHeight = getHeight();

		float pipHeight = barHeight - 2 * borderSize.y;

		// draw border
		batch.draw(borderTexture, getX(), getY(), barWidth, barHeight);

		// draw bar bg (shows when health is below max)
		batch.draw(barBgTexture, getX() + borderSize.x, getY() + borderSize.y, barWidth - 2 * borderSize.x, pipHeight);
		
		if (player != null) {
			EntityData data = player.getData();
			// calculate pip size
			float numSpacers = data.maxHealth - 1;
			float hpPipWidth = (barWidth - 2 * borderSize.x - numSpacers * spacerWidth) / (data.maxHealth);
			
			// draw hp bar
			float currHp = player.getHealth();
			if (currHp > 0) {
				batch.draw(hpTexture, getX() + borderSize.x, getY() + borderSize.y, hpPipWidth * currHp + spacerWidth
						* (currHp - 1), pipHeight);
			}

			// draw spaces between healths pips
			for (int i = 1; i <= numSpacers; i++) {
				batch.draw(spacerTexture, getX() + borderSize.x + i * (hpPipWidth) + (i - 1) * spacerWidth, getY()
						+ borderSize.y, spacerWidth, pipHeight);
			}
		}
	}

	public void dispose() {
		borderTexture.dispose();
		barBgTexture.dispose();
		hpTexture.dispose();
		spacerTexture.dispose();
	}

}
