package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class Enemy extends AnimSprite implements IRecyclable, IBoxCollidable, ILayerProvider<MainScene.Layer> {

    protected float speed = 320f;
    protected float ad;
    protected float hp;
    protected float exp;
    protected RectF collisionRect = new RectF();
    private float[] playerPosition;

    public static Enemy get(int level, int index) {
        return Scene.top().getRecyclable(Enemy.class).init(level, index);
    }
    private Enemy init(int level, int idx){
        return this;
    }
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
