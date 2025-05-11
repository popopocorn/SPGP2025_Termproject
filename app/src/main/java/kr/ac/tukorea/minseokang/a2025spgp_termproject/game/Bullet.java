package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
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
    private int power;

    private JoyStick joyStick = MainScene.getJoyStick();

    private float angle = joyStick.angle_radian;

    public static Bullet get(float x, float y, int power) {
        return Scene.top().getRecyclable(Bullet.class).init(x, y, power);
    }
    public Bullet() {
        super(R.mipmap.shoot);

    }
    private Bullet init(float x, float y, int power) {
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);

        angle = joyStick.angle_radian;
        dx = (float)Math.cos(angle) * SPEED;
        dy = (float)Math.sin(angle)* SPEED;
        this.power = power;
        return this;
    }
    @Override
    public void update() {

        super.update();
        if (dstRect.bottom < 0) {
            Scene.top().remove(this);
        }

    }

    public int getPower() {
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
}
