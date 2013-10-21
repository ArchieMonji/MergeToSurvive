package com.vgdc.merge.entities.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.BaseEntity;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.EntityType;
import com.vgdc.merge.world.World;

public class HealthBarRenderer {

	private World world;
	private SpriteBatch batch;
	
	private Texture borderTexture;
	private Texture barBgTexture;
	private Texture hpTexture;
	private Texture spacerTexture;
	private boolean barWidthRelativeToSize = true;

	private float spacerWidth = 2;
	private Vector2 barSize = new Vector2(64, 8);
	private Vector2 borderSize = new Vector2(2, 2);
	private float hoverHeight = 8;
	private float minPipWidth = 16;
	private boolean useFixedPipWidth = false;

	public HealthBarRenderer(World world) {
		this.world = world;
		batch = new SpriteBatch();
		borderTexture = new Texture("data/images/hp bars/border.png");
		barBgTexture = new Texture("data/images/hp bars/BarBg.png");
		hpTexture = new Texture("data/images/hp bars/hp.png");
		spacerTexture = new Texture("data/images/hp bars/spacer.png");
	}

	public void setBarSize(float x, float y) {
		barSize.x = x;
		barSize.y = y;
	}

	public void setBorderSize(float x, float y) {
		borderSize.x = x;
		borderSize.y = y;
	}

	public void setSpacerWidth(float w) {
		spacerWidth = w;
	}

	public void setHoverHeight(float hoverHeight) {
		this.hoverHeight = hoverHeight;
	}

	public void setFixedPipWidth(boolean useFixedPipWidth){
		this.useFixedPipWidth = useFixedPipWidth;
	}
	
	public void setPipWidth(float minPipWidth){
		this.minPipWidth = minPipWidth;
	}
	
	public void onRender(float delta) {
		batch.begin();
		float pipHeight = barSize.y - 2 * borderSize.y;

		for (BaseEntity e : world.getEntityManager().getEntities()) {
			if (e.getEntityType() == EntityType.Entity) {
				Entity entity = (Entity) e;
				if (entity.getHealth() >= 0) {
					EntityData data = entity.getData();
					
					float barWidth = 0;
					if (barWidthRelativeToSize) {
						barWidth = entity.getPhysicsBody().getSize().x;
					} else {
						barWidth = barSize.x;
					}
					
					//calculate pip size
					float numSpacers = data.maxHealth - 1;
					float hpPipWidth = (barWidth - 2 * borderSize.x - numSpacers
							* spacerWidth)
							/ (data.maxHealth);
					
					//if Pips are too small, resize bar to fit
					if(useFixedPipWidth  || hpPipWidth < minPipWidth ){
						hpPipWidth = minPipWidth;
						barWidth = 2 * borderSize.x + numSpacers
								* spacerWidth + hpPipWidth * data.maxHealth;
					}

					float h = entity.getPhysicsBody().getSize().y;

					//determine hp bar location
					float x = entity.getPosition().x - barWidth / 2;
					float y = entity.getPosition().y + h / 2 + hoverHeight;

					// draw border
					batch.draw(borderTexture, x, y, barWidth, barSize.y);

					// draw bar bg (shows when health is below max)
					if (entity.getHealth() < data.maxHealth) {
						batch.draw(barBgTexture, x + borderSize.x, y
								+ borderSize.y, barWidth - 2 * borderSize.x,
								pipHeight);
					}



					// draw hp bar
					float currHp = entity.getHealth();
					if (currHp > 0) {
						batch.draw(hpTexture, x + borderSize.x, y
								+ borderSize.y, hpPipWidth * currHp
								+ spacerWidth * (currHp - 1), pipHeight);
					}

					// draw spaces between healths pips
					for (int i = 1; i <= numSpacers; i++) {
						batch.draw(spacerTexture, x + borderSize.x + i
								* (hpPipWidth) + (i - 1) * spacerWidth, y
								+ borderSize.y, spacerWidth, pipHeight);
					}
				}
			}
		}
		batch.end();
	}

	public void dispose() {
		batch.dispose();
		borderTexture.dispose();
		barBgTexture.dispose();
		hpTexture.dispose();
		spacerTexture.dispose();
	}

}
