package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

public abstract class AbstractPlayer implements Player {

    private double x;
    private double y;
    private Vector2D velocity = new Vector2D(0, 0);
    private boolean onGround = true;

    protected AbstractPlayer(final Position startPos) {
        this.x = startPos.x();
        this.y = startPos.y();
    }

    @Override public double getX() {
        return this.x; 
    }

    @Override public double getY() {
        return this.y;
    }

    @Override public double getVelocityY() {
        return this.velocity.y();
    }

    @Override public boolean isOnGround() {
        return this.onGround;
    }

    @Override
    public void move(final double dx) {
        this.velocity = new Vector2D(dx, this.velocity.y());
        this.x += this.velocity.x();
    }
    
    @Override
    public void applyGravity(final double gravity) {
        if (!this.onGround) {
            this.velocity = this.velocity.add(new Vector2D(0, gravity));
            this.y += this.velocity.y();
        }
    }
    
    @Override
    public void jump(final double strength) {
        if (this.onGround) {
            this.velocity = this.velocity.add(new Vector2D(0, -strength));
            this.onGround = false;
        }
    }
    
    @Override
    public void landOn(final double groundY) {
        this.y = groundY;
        this.velocity = new Vector2D(this.velocity.x(), 0);
        this.onGround = true;
    }
    
    @Override
    public void stopJump(final double ceilingY) {
        this.y = ceilingY;
        this.velocity = new Vector2D(this.velocity.x(), 0);
    }

    @Override public void setAirborne() {
        this.onGround = false;
    }

    @Override
    public void setVelocityX(final double vx) {
        this.velocity = new Vector2D(vx, this.velocity.y());
    }

    @Override
    public void update(final double deltaTime, final InputController input) {
        handleInput(input);

        if (!this.onGround) {
            this.velocity = this.velocity.add(new Vector2D(0, GRAVITY * deltaTime));
        }

        this.x += this.velocity.x() * deltaTime;
        this.y += this.velocity.y() * deltaTime;
    }
    

    private void handleInput(final InputController input) {
        final PlayerType type = this.getPlayerType(); 

        final boolean left = input.isMoveLeftPressed(type);
        final boolean right = input.isMoveRightPressed(type);

        if (left == right) { 
            this.setVelocityX(0);
        } else if (left) {
            this.setVelocityX(-RUN_SPEED);
        } else { 
            this.setVelocityX(RUN_SPEED);
        }

        if (input.isJumpPressed(type)) {
            this.jump(JUMP_STRENGTH);
        }
    }

    @Override
    public HitBox getHitBox() {
        return new HitBoxImpl(
            new Position(this.x, this.y),
            getHeight(),
            getWidth()
        );
    }

    @Override
    public void correctPhysicsCollision(final double penetration, final Vector2D normal) {

        final double POSITION_SLOP = 0.001;
        final double CORRECTION_PERCENT = 0.8;
    
        if (penetration <= 0) {
            return;
        }

        final double depth = Math.max(penetration - POSITION_SLOP, 0.0);
        final Vector2D correction = normal.multiply(CORRECTION_PERCENT * depth);
        this.x += correction.x();
        this.y += correction.y();

        final double velocityNormal = this.velocity.dot(normal);
        if (velocityNormal < 0) {
            this.velocity = this.velocity.subtract(normal.multiply(velocityNormal));
        }

        final double normalY = normal.y();
        if (normalY < -0.5) {
            this.onGround = true;
            this.velocity = new Vector2D(this.velocity.x(), 0);
        } else if (normalY > 0.5) {
            this.velocity = new Vector2D(this.velocity.x(), 0);
        }
    }

}