package com.vgdc.merge.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.Entity;
import com.vgdc.merge.MainGame;
import com.vgdc.merge.Renderer;
import com.vgdc.merge.World;
import com.vgdc.merge.test.TestPhysicsBody;

public class GameScreen extends AbstractScreen {
	private SpriteBatch batch;
	
	private World myWorld;

	public GameScreen(MainGame game) {
		super(game);
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		myWorld.onUpdate();
		myWorld.onRender(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		myWorld = new World();
		myWorld.setCamera(new OrthographicCamera(w, h));
		myWorld.setAssets(game.getAssets());
		
		batch = new SpriteBatch();
		
//		ArrayList<Ability> abilities = new ArrayList<Ability>();
//		abilities.add(new TestAbility());
//		abilities.add(null);
//		
//		Texture animTexture = new Texture(Gdx.files.internal("data/animation_sheet.png"));
//		TextureRegion[] regions = new TextureRegion[30];
//		int x = animTexture.getWidth()/6;
//		int y = animTexture.getHeight()/5;
//		for(int i = 0; i < 5; i++)
//		{
//			for(int j = 0; j < 6; j++)
//			{
//				regions[i*6 + j] = new TextureRegion(animTexture, j*x, i*y, x, y);
//			}
//		}
//		Animation idle = new Animation(10, new TextureRegion(animTexture, 0, 0, x, y));
//		Animation run = new Animation(0.01f, regions);
//		
//		ArrayList<Animation> animations = new ArrayList<Animation>();
//		animations.add(idle);
//		animations.add(run);
//		
//		Controls controls = new Controls();
//		controls.down = Keys.S;
//		controls.up = Keys.W;
//		controls.left = Keys.A;
//		controls.right = Keys.D;
//		controls.useAbility = Buttons.LEFT;
//		controls.toggleAbility = Buttons.RIGHT;
//		
//		EntityData testData = new EntityData();
//		testData.jumpHeight = 5;
//		testData.moveSpeed = 5;
//		testData.maxHealth = 1;
//		testData.defaultAbilities = abilities;
//		testData.controller = new PlayerController(controls);
//		testData.animations = animations;
//		
//		EntityData testEnemy = new EntityData();
//		testEnemy.jumpHeight = 5;
//		testEnemy.moveSpeed = 1;
//		testEnemy.maxHealth = 1;
//		testEnemy.defaultAbilities = abilities;
//		testEnemy.controller = new TestAIController();
//		testEnemy.animations = animations;
		
		Entity testEntity = new Entity(game.getAssets().entityDataMap.get("testplayer"), myWorld);
		testEntity.setPhysicsBody(new TestPhysicsBody());
		testEntity.setRenderer(new Renderer());
		testEntity.setPosition(new Vector2(58, 58));
		myWorld.getEntityManager().addEntity(testEntity);
		testEntity = new Entity(game.getAssets().entityDataMap.get("testenemy"), myWorld);
		testEntity.setPhysicsBody(new TestPhysicsBody());
		testEntity.setRenderer(new Renderer());
		testEntity.setPosition(new Vector2(58, 58));
		myWorld.getEntityManager().addEntity(testEntity);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}
}
