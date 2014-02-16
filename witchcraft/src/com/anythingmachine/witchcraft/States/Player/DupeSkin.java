package com.anythingmachine.witchcraft.States.Player;

import com.anythingmachine.aiengine.PlayerStateMachine;
import com.badlogic.gdx.math.Matrix4;

public class DupeSkin extends PlayerState {
	private float timeout = 10;
	private float time = 0;
	
	public DupeSkin (PlayerStateMachine sm , PlayerStateEnum name) {
		super(sm,  name);
	}
	
	@Override
	public void updatePower(float dt) {
		time += dt;
	}
	
	public void update(float dt) {
		checkGround();
		
		setInputSpeed();
		
		updatePower(dt);
		
		sm.phyState.correctCBody(-8, 64, 0);

		setAttack();

		sm.animate.setFlipX(sm.facingleft);
	}

	@Override
	public void setIdle() {
		if ( time > timeout ) {
			time = 0;
			sm.animate.switchSkin("player");
			sm.setState(PlayerStateEnum.IDLE);
		} else {
			sm.animate.bindPose();
			sm.animate.setCurrent("idle", true);
			sm.phyState.stop();
		}
	}

	@Override
	public void setDupeSkin() {
		
	}
		
	@Override
	public void usePower() {
		
	}
			
	@Override 
	public void drawCape(Matrix4 cam) {
		
	}
	
	@Override
	public void nextPower() {
		
	}

	@Override
	public void setAttack() {
		if ( sm.input.is("attack") ) {
			if (sm.animate.isSkin("archer")) {
				sm.setState(PlayerStateEnum.ATTACKING);
				sm.state.setParent(this);
				sm.animate.setCurrent("drawbow", true);
				sm.animate.bindPose();
				sm.phyState.stop();
			} else if (!sm.animate.isSkin("player")) {
				sm.setState(PlayerStateEnum.ATTACKING);
				sm.state.setParent(this);
				sm.animate.setCurrent("swordattack", true);
				sm.animate.bindPose();
				sm.phyState.stop();
			}
		}
	}

}
