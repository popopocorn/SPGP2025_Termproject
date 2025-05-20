package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.RectUtil;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class Mushmom extends Sprite {
    private static final String TAG = Mushmom.class.getSimpleName();
    private static final float PLANE_WIDTH = 120;
    private static final int PLANE_SRC_WIDTH = 105;
    private static final float SPEED = 5f;

    private float hp;
    private float ad;
    private final float FIRE_INTERVAL = 1.0f;
    private float fireCoolTime = FIRE_INTERVAL;
    private static final float BULLET_OFFSET = 80f;

    private JoyStick joyStick = MainScene.getJoyStick();
    private float angle;
    public Mushmom() {
        super(R.mipmap.mushmom);
        setPosition(Metrics.width / 2, Metrics.height / 2, PLANE_WIDTH, PLANE_WIDTH);

        srcRect = new Rect();
    }

    @Override
    public void update() {
        angle = joyStick.angle_radian;
        dx = (float)Math.cos(angle) * joyStick.power * SPEED;
        dy = (float)Math.sin(angle)* joyStick.power* SPEED;
        x+=dx;
        y+=dy;
        x=clamp(-3150, x, 4050);
        y=clamp(-1100, y, 2700);
        Log.d("fda", String.format("x: %.2f, y: %.2f", x, y));
        //setPosition(x, y, PLANE_WIDTH, PLANE_WIDTH);
        fireBullet();
    }

    @Override
    public void draw(Canvas canvas) {
        //Log.d("", String.valueOf(Math.toDegrees(joyStick.angle_radian)));
        if (Math.toDegrees(joyStick.angle_radian) >= 90 || Math.toDegrees(joyStick.angle_radian) <= -90 ){
            canvas.drawBitmap(bitmap, null, dstRect, null);
        }else{
            canvas.save();
            canvas.scale(-1, 1, dstRect.centerX(), dstRect.centerY());
            canvas.drawBitmap(bitmap, null, dstRect, null);
            canvas.restore();

        }

    }

    private void fireBullet() {
        fireCoolTime -= GameView.frameTime;
        if (fireCoolTime > 0) {
            return;
        }
        fireCoolTime = FIRE_INTERVAL;
        MainScene scene = (MainScene) Scene.top();
        if (scene == null) return;

        int score = scene.getScore();
        int power = 10 + score / 1000;
        Bullet bullet = Bullet.get(Metrics.width / 2, Metrics.height / 2, power);
        scene.add(bullet);
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());
                return true;

        }
        return false;
    }
    public float[] getPosition(){return new float[]{x,y};}

    private float clamp( float min, float x, float max){
        if (x<min){
            return min;
        }
        if(x>max){
            return max;
        }
        return x;
    }
}
