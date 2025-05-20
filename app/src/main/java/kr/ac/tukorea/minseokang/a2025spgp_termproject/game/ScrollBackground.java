package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;


public class ScrollBackground extends Sprite {
    private float cw;
    private float ch;
    private float w;
    private float h;
    private float windowW;
    private float windowH;

    private static int extend = 5;
    private Mushmom mushmom;
    private float[] playerPosition;

    public ScrollBackground(int mipmapId) {
        super(mipmapId);
        cw = Metrics.width;
        ch = Metrics.height;
        w = (float)bitmap.getWidth();
        h = (float)bitmap.getHeight();
        mushmom = MainScene.getPlayer();
    }

    @Override
    public void update() {
        super.update();
        playerPosition = mushmom.getPosition();
        setPosition(cw - playerPosition[0], ch - playerPosition[1], w * extend, h * extend);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
