package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Canvas;
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
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.RectUtil;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.R;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.game.MainScene;

public class Bullet extends Sprite implements IRecyclable, IBoxCollidable, ILayerProvider<MainScene.Layer> {
    private float SPEED = 1000f;
    private float power;

    private float ratio = 1.0f;

    private boolean e=false;

    private HashSet<Enemy> hitEnemies= new HashSet<>();

    public static Bullet get(float x, float y, float power, float[] direction, boolean Enhanced) {
        return Scene.top().getRecyclable(Bullet.class).init(x, y, power, direction, Enhanced);
    }
    public Bullet() {
        super(R.mipmap.shoot);

    }
    private Bullet init(float x, float y, float power, float[] direction, boolean Enhanced) {
        setPosition(x, y, bitmap.getWidth()/5, bitmap.getHeight()/5);


        dx = direction[0] * SPEED;
        dy = direction[1] * SPEED;
        this.power = power;
        //this.power = 0;
        hitEnemies.clear();

        if(Enhanced){
            setImageResourceId(R.mipmap.fireball2);
            ratio=1.2f;
            SPEED=1200f;
            e=true;
        }
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

    @Override
    public void draw(Canvas canvas) {

        if (dx > 0) {
            canvas.save();
            canvas.scale(-1, 1, dstRect.centerX(), dstRect.centerY());
            canvas.drawBitmap(bitmap, null, dstRect, null);
            canvas.restore();
        } else {
            super.draw(canvas);
        }

    }

    public float getPower() {
        return ratio * power;
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
