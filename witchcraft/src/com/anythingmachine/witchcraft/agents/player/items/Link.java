package com.anythingmachine.witchcraft.agents.player.items;

import com.anythingmachine.witchcraft.Util.Util;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Link {
	public Link parent;
	public Body body;
	public Sprite sprite;
	private boolean isRotated = false;

	public Link( World world, Vector2 pos, Texture texture ) {
		sprite = new Sprite( texture );
		constructBody( pos.cpy(), world, sprite.getWidth()/2.f, sprite.getHeight()/2.f );
		sprite.setScale(1.5f);
		parent = null;
	}

	public void createLinkJoint( Link link, World world ) {
		parent = link;
		sprite.setScale(1f);
		RevoluteJointDef revoluteJointDef = new RevoluteJointDef( );
//		revoluteJointDef.bodyA = link.body;
//		revoluteJointDef.bodyB = body;
		revoluteJointDef.initialize( link.body, body,
				new Vector2( body.getPosition().x, body.getPosition().y 
						+ (sprite.getHeight()/2.f)*Util.PIXEL_TO_BOX) );
//		revoluteJointDef.enableMotor = false;
//		revoluteJointDef.collideConnected = false;	
//		revoluteJointDef.referenceAngle = 0f;
//		revoluteJointDef.enableLimit = true;
//		revoluteJointDef.upperAngle = -Util.PI/6.f;
//		revoluteJointDef.lowerAngle = Util.PI/6.f;
		world.createJoint( revoluteJointDef );
		sprite.setScale(1.5f);
	}
	
	public void updatePos( Vector2 pos ) {
		body.setTransform(pos.mul(Util.PIXEL_TO_BOX), 0.0f);
	}
	
	public void setVelocity( Vector2 vel ) {
		body.setLinearVelocity(vel);
	}
	
	public void setBodyType( BodyType type ) {
		body.setType(type);
	}
	
	public Vector2 getPositionPixels( ) {
		return body.getPosition().cpy().mul(Util.PIXELS_PER_METER);
	}
	
	public void flipSprite() {
		sprite.flip(true, false);
	}
	
	public void rotateSprite(float degree ) {
		sprite.rotate(degree);
		isRotated = !isRotated;
	}
	
	public void setGravityScale(float scale ) {
		body.setGravityScale(scale);
	}
	
	public void draw( SpriteBatch batch ) {
		Vector2 pos =  body.getPosition().mul(Util.PIXELS_PER_METER)
				.sub(sprite.getWidth()/4.f, sprite.getHeight()/4.f);
		this.sprite.setPosition( pos.x, pos.y );
		if( !isRotated )
			this.sprite.setRotation( Util.RAD_TO_DEG * body.getAngle( ) );
		this.sprite.draw( batch );
	}

	private void constructBody( Vector2 pos, World world, float width, float height ) {
		BodyDef bodyDef = new BodyDef( );
		bodyDef.position.set( pos.mul(Util.PIXEL_TO_BOX) );
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.allowSleep = false;
		//bodyDef.gravityScale = 1f;
		CircleShape shape = new CircleShape( );
		shape.setRadius(((width/2.0f))*Util.PIXEL_TO_BOX);
		FixtureDef fixtureDef = new FixtureDef( );
		fixtureDef.density = 0.05f;
		//fixtureDef.restitution = 0f;
		fixtureDef.shape = shape;
		//fixtureDef.friction = 0.95f;
		fixtureDef.isSensor = true;
		//fixtureDef.filter.categoryBits = Util.CATEGORY_CAPE;
		//fixtureDef.filter.maskBits = Util.CATEGORY_CAPE;
		body = world.createBody( bodyDef );
		body.setLinearDamping(1f);
		body.setAngularDamping(1f);
		body.createFixture( fixtureDef );
	}
}