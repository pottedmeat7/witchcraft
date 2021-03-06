package com.anythingmachine.aiengine;

import com.anythingmachine.agents.npcs.NonPlayer;
import com.anythingmachine.animations.AnimationManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.spine.SkeletonData;

public class StateMachine {
	public AnimationManager animate;
	public NonPlayer npc;

	public boolean hitnpc;
	public boolean facingleft;
	public boolean grounded;
	public boolean hitleftwall;
	public boolean hitrightwall;

	public StateMachine(String name, Vector3 pos, Vector2 scl, boolean flip,
			SkeletonData sd) {
//		tests = new HashMap<String, Boolean>();
		animate = new AnimationManager(name, pos, scl, flip, sd);
	}

	public void update(float dt) {

	}

//	public void addTest(String name, boolean val) {
//		tests.put(name, val);
//	}
//
//	public void setTestVal(String name, boolean val) {
//		tests.put(name, val);
//	}
//
//	public boolean test(String name) {
//		return tests.get(name);
//	}
//
//	public boolean testANDtest(String test1, String test2) {
//		return tests.get(test1) && tests.get(test2);
//	}
//
//	public boolean testORtest(String test1, String test2) {
//		return tests.get(test1) || tests.get(test2);
//	}

}
