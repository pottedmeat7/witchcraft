package com.anythingmachine.witchcraft.States.Player;

import com.anythingmachine.aiengine.PlayerStateMachine;
import com.anythingmachine.physicsEngine.TexturedParticle;
import com.anythingmachine.witchcraft.WitchCraft;
import com.anythingmachine.witchcraft.GameStates.Containers.GamePlayManager;
import com.anythingmachine.witchcraft.Util.Util;
import com.anythingmachine.witchcraft.Util.Util.EntityType;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

public class Dead extends PlayerState {
	private float fadeout;
	private TexturedParticle head;

	public Dead(PlayerStateMachine sm, PlayerStateEnum name) {
		super(sm, name);
	}

	@Override
	public void update(float dt) {
		sm.phyState.correctCBody(-8, 64, 0);

		checkGround();

		if (sm.animate.testOverTime(0, .75f)) {
			fadeout -= dt;
		}
		GamePlayManager.player.cape.updatePos(sm.neck.getWorldX() + 12,
				sm.neck.getWorldY() - 8);
		if (fadeout < -3) {
			super.setIdle();
		}

		addWindToCape(dt);
	}

	@Override
	public boolean canAnimate() {
		if (sm.animate.testOverTime(0, .75f)) {
			return false;
		}
		return true;
	}

	@Override
	public void draw(Batch batch) {
		sm.animate.draw(batch);
		head.draw(batch);
	}

	@Override
	public void setInputSpeed() {

	}

	@Override
	public void switchPower() {

	}

	@Override
	public void usePower() {

	}

	@Override
	public void setFlying() {
	}

	@Override
	public void setRun() {
	}

	@Override
	public void setWalk() {
	}

	@Override
	public void setIdle() {

	}

	@Override
	protected void checkGround() {
		if (sm.hitplatform || sm.grounded) {
			Vector3 pos = sm.phyState.body.getPos();
			float groundPoint = sm.elevatedSegment.getHeight(pos.x);
			if (pos.y < groundPoint + 16)
				sm.phyState.body.setY(groundPoint);
			if (head.getY() < groundPoint + 4) {
				head.setY(groundPoint);
				head.setVel(0, 0, 0);
			}

		}
	}

	@Override
	public void transistionIn() {
		sm.animate.bindPose();
		sm.phyState.body.stopOnX();
		sm.animate.setCurrent("ded", true);
		sm.animate.setRegion("hood", "hood", "witch-headstump", false, 1, 1);
		GamePlayManager.player.cape.addWindForce(0, -400);
		fadeout = 1;
		setParent(sm.getState(PlayerStateEnum.IDLE));
		Sprite sprite = WitchCraft.assetManager.get(
				"data/spine/characters.atlas", TextureAtlas.class)
				.createSprite("witch-cuthead");
		sprite.scale(-0.4f);
		head = new TexturedParticle(Util.addVecs(sm.phyState.body.getPos(),
				new Vector3(sm.facingleft ? 16 : -16, 64, 0)),
				EntityType.PARTICLE, sprite,
				new Vector3(0, Util.GRAVITY * 2, 0));
		head.setVel(sm.facingleft ? -60 : 60, 90, 0);
		GamePlayManager.rk4System.addParticle(head);
	}

	@Override
	public void transistionOut() {
		head.destroy();
		sm.animate.setRegion("hood", "hood", "witch-hood", false, 1, 1);
	}

}
