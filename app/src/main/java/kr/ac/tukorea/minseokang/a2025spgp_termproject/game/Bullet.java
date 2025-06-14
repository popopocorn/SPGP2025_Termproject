package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.RectF;
import android.util.Log;

import java.util.HashSet;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.R;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.game.MainScene;

public class Bullet extends Sprite implements IRecyclable, IBoxCollidable, ILayerProvider<MainScene.Layer> {
    private static final float BULLET_WIDTH = 68f;
    private static final float BULLET_HEIGHT = BULLET_WIDTH * 40 / 28;
    private static final float SPEED = 1000f;
    private float power;

    private float damage;

    private HashSet<Enemy> hitEnemies= new HashSet<>();

    public static Bullet get(float x, float y, float power, float[] direction) {
        return Scene.top().getRecyclable(Bullet.class).init(x, y, power, direction);
    }
    public Bullet() {
        super(R.mipmap.shoot);

    }
    private Bullet init(float x, float y, float power, float[] direction) {
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);


        dx = direction[0] * SPEED;
        dy = direction[1] * SPEED;
        this.power = power;
        hitEnemies.clear();
        return this;
    }
    @Override
    public void update() {

        float[] postion = MainScene.getPlayer().getPosition();
        super.update();
        if (dstRect.bottom < 0 || dstRect.top > 1600 || dstRect.left < -100 || dstRect.right > 1000 ) {
            Scene.top().remove(this);
        }

    }

    public float getPower() {
        return power;
    }
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {
    }

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.bullet;
    }
    @Override
    public void DoCollision(IGameObject other) {
        if(other instanceof Enemy){
            hitEnemies.add((Enemy) other);
            //Log.d("bullet", "hit");
        }
    }
    public boolean hasHit(IGameObject other){
        //Log.d("bullet", String.valueOf(hitEnemies.contains(other)));
        return hitEnemies.contains(other);
    }
}
