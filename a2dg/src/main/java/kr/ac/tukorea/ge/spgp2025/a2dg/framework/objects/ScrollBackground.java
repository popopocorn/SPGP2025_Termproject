package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
public class ScrollBackground extends Sprite{
    private int cw = (int)Metrics.width;
    private int ch = (int)Metrics.height;
    private int w = bitmap.getWidth();
    private int h = bitmap.getHeight();

    public ScrollBackground(int mipmapId) {
        super(mipmapId);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
