package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.R;

public class EnemyGauge extends Sprite implements IGameObject {
    private Enemy owner;
    private Sprite gaugeBg;
    private Sprite gaugeFg;
    private static final float GAUGE_WIDTH_RATIO = 0.8f; // 적 너비의 80%
    private static final float GAUGE_HEIGHT = Metrics.height * 0.05f;
    private float hpRatio;
    public EnemyGauge(Enemy owner) {
        super(R.mipmap.gaugebg);

        gaugeBg= new Sprite(R.mipmap.gaugebg);
        gaugeFg= new Sprite(R.mipmap.gaugefg);
        this.owner=owner;
        width=owner.getWidth();
    }

    @Override
    public void update() {
        if(owner == null){
            Scene.top().remove(MainScene.Layer.ui, this);
        }

        float[] ownerloc = owner.getLocation();
        x=ownerloc[0];
        y=ownerloc[1] + 5.0f;
        hpRatio=owner.getRatio();
        gaugeBg.setPosition(x, y, width, GAUGE_HEIGHT);
        gaugeFg.setPosition(x, y, width, hpRatio);

    }

    @Override
    public void draw(Canvas canvas) {
        gaugeBg.draw(canvas);
        gaugeFg.draw(canvas);
    }
}
