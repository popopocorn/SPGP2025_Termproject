package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;

public class Enemy extends AnimSprite implements IRecyclable, IBoxCollidable, ILayerProvider<MainScene.Layer> {

    protected float speed;
    protected float ad;
    protected float hp;
    protected float exp;
    protected RectF collisionRect = new RectF();
    private float[] playerPosition;

    public Enemy(int mipmapId, float fps) {
        super(mipmapId, fps);
    }

    @Override
    public void update() {
        super.update();
        playerPosition = MainScene.getPlayer().getPosition();
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public MainScene.Layer getLayer() {
        return null;
    }

    @Override
    public void onRecycle() {

    }
}
