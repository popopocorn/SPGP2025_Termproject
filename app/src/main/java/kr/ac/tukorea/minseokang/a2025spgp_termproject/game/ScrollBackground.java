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

    private static int extend = 10;
    private float[] playerPosition;

    public ScrollBackground(int mipmapId) {
        super(mipmapId);
        cw = Metrics.width;
        ch = Metrics.height;
        w = (float)bitmap.getWidth();
        h = (float)bitmap.getHeight();

    }

    @Override
    public void update() {
        super.update();
        playerPosition = MainScene.getPlayer().getPosition();
        setPosition(cw - playerPosition[0] - Metrics.width/2, ch - playerPosition[1] - Metrics.height/2, w * extend, h * extend);
//        srcRect.set(
//                (int)playerPosition[0] - (int)cw/2,
//                (int)playerPosition[1] - (int)ch/2,
//                (int)playerPosition[0] + (int)cw/2,
//                (int)playerPosition[1] + (int)ch/2
//        );
//        dstRect.set(0, 0, cw, ch);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

}
