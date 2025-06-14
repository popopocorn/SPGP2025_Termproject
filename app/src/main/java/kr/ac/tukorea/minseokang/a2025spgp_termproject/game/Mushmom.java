package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.RectUtil;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;

public class Mushmom extends AnimSprite implements IBoxCollidable {
    private static final String TAG = Mushmom.class.getSimpleName();
    private static final float PLANE_WIDTH = 120;
    private static final int PLANE_SRC_WIDTH = 105;
    private final float SPEED = 350f;

    private float hp;
    private float ad = 100;

    private int level = 0;
    private int exp;
    private float adPerLevel = 30;
    private final float FIRE_INTERVAL = 0.5f;
    private float fireCoolTime = FIRE_INTERVAL;
    private static final float BULLET_OFFSET = 80f;

    private JoyStick joyStick = MainScene.getJoyStick();
    private float angle;

    private float[] targetDir;
    public Mushmom() {
        super(R.mipmap.mushmom, 0);
        setPosition(Metrics.width / 2, Metrics.height / 2, PLANE_WIDTH, PLANE_WIDTH);
        x=0;
        y=0;

    }

    @Override
    public void update() {
        angle = joyStick.angle_radian;
        dx = (float)Math.cos(angle) * joyStick.power * SPEED * GameView.frameTime;
        dy = (float)Math.sin(angle)* joyStick.power* SPEED * GameView.frameTime;
        x+=dx;
        y+=dy;
        x=clamp(-7500, x, 7500);
        y=clamp(-4000, y, 4000);
        //Log.d("Pos", String.format("x: %.2f, y: %.2f", x, y));
        //setPosition(x, y, PLANE_WIDTH, PLANE_WIDTH);


        fireBullet();
        if(exp >= 100){
            exp-=100;
            ++level;
            //Log.d("lv","up");
            new EnhanceScene().push();
        }
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
        int power = 50;
        Bullet bullet = Bullet.get(Metrics.width / 2, Metrics.height / 2, power, targetDir);
        scene.add(MainScene.Layer.bullet, bullet);
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

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    public void setTarget(float[] target) {
        targetDir = new float[]{ target[0] - Metrics.width/2, target[1] - Metrics.height/2};
        float length = (float) Math.sqrt(targetDir[0] * targetDir[0] + targetDir[1] * targetDir[1]);
        if (length != 0) {
            targetDir[0] /= length;
            targetDir[1] /= length;
        }
    }

    @Override
    public void DoCollision(IGameObject other) {

    }
    public void addExp(int exp){
        this.exp+=exp;
    }
}
