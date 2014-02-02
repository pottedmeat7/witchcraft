package com.anythingmachine.witchcraft.States;

import com.anythingmachine.aiengine.StateMachine;
import com.anythingmachine.witchcraft.Util.Util;

public class Jumping extends State {

		public Jumping(StateMachine sm, StateEnum name) {
			super(sm, name);
		}

		@Override
		public void update(float dt) {
			super.update(dt);
			
			sm.animate.applyTotalTime(true, dt);
			if ( sm.animate.testOverTime(0f, 0.95f) ) {
				setFlying();
			}
		}
		
		@Override
		public void setJumping() {
			sm.phyState.setYVel(150f);
		}
		
		@Override 
		public void setIdle() {
			
		}
		
		@Override
		public void setInputSpeed() {
			int axisVal = sm.input.axisRange2();
			boolean facingleft = sm.test("facingleft");
			if (axisVal > 0) {
				sm.setTestVal("facingleft", false);
				facingleft = false;
				sm.setTestVal("hitleftwall", false);
			} else if (axisVal < 0) {
				facingleft = true;
				sm.setTestVal("facingleft", true);
				sm.setTestVal("hitrightwall", false);
			}
			if ( facingleft && !sm.test("hitleftwall")) {
				sm.phyState.setXVel(-Util.PLAYERFLYSPEED*0.75f);
			} else 	if (!sm.test("hitrightwall")) {
				sm.phyState.setXVel(Util.PLAYERFLYSPEED*0.75f);
			}

		}

		@Override
		public void setFlying() {
			sm.setState(StateEnum.FLYING);
		}
		
		@Override
		public void setDupeSkin() {
			
		}
		
		@Override
		public void setCastSpell() {
			
		}
		
		@Override
		public void setAttack() {
			
		}
		@Override
		public void setWalk() {
		}
		@Override
		public void setRun() {
		}
		@Override
		public void checkGround() {
		}

}