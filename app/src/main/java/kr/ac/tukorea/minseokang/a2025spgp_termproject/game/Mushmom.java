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
    private float SPEED = 350f;

    private float hp = 600;
    private float ad = 50;

    private int level = 0;
    private int exp;
    private float FIRE_INTERVAL = 0.6f;
    private float fireCoolTime = FIRE_INTERVAL;
    private static final float BULLET_OFFSET = 80f;

    private boolean bulletUpgrade=false;

    private JoyStick joyStick = MainScene.getJoyStick();
    private float angle;
    private float hitFrame = 0.1f;
    private float hittime = 0.0f;
    private boolean mujeok = false;
    private float[] targetDir;

    private boolean isgod=false;
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
        if(mujeok){
            hittime+=GameView.frameTime;
            if(hittime>hitFrame){
                hittime=0;
                mujeok=false;
            }
        }

        if(hp<0){
            new DieScene().push();
        }
        Log.d("hp", String.valueOf(hp));

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

        Bullet bullet = Bullet.get(Metrics.width / 2, Metrics.height / 2, ad, targetDir, bulletUpgrade);
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
        if(other instanceof Enemy){
            if(!mujeok){
                mujeok=true;
                Enemy e = (Enemy) other;
                hp -= e.Damage();
                //Log.d("d", "oh");
            }
        }
    }
    public void addExp(int exp){
        this.exp+=exp;
    }
    public void addEnhance(Enhance enhance, boolean done){
        if(done){
            ad+=5;
            return;
        }else{
            switch (enhance){
                case AdUp:
                    ad+=20;
                    break;
                case SizeUp:
                    bulletUpgrade=true;
                    break;
                case AsUp:
                    FIRE_INTERVAL-=0.1f;
                    break;
                case SpeedUp:
                    SPEED +=20;
                    break;
                case nonHitUp:
                    hitFrame+=0.1f;
                    break;
                default:
                    break;
            }
        }
    }

    public void setGot(){ isgod=!isgod;}
}
