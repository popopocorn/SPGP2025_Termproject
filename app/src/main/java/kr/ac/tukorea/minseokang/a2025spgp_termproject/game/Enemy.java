package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.Arrays;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.RectUtil;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.R;

public class Enemy extends AnimSprite implements IRecyclable, IBoxCollidable, ILayerProvider<MainScene.Layer> {

    protected float speed = 250;
    protected float ad;
    protected float hp;
    protected float Maxhp;
    protected int exp = 0;
    protected int level;
    protected RectF collisionRect = new RectF();
    protected float[] playerPosition;

    private float screenx;
    private float screeny;

    protected static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_fg);

    public static Enemy get(int level, int index, float initx, float inity) {
        return Scene.top().getRecyclable(Enemy.class).init(level, index, initx, inity);
    }
    private Enemy init(int level, int idx, float initx, float inity){
        if( (level % 5) == 0 && idx == 4){
            setImageResourceId(R.mipmap.mano);
            this.hp = this.Maxhp = (float)(((float)level * 0.1 + 1) * 100) * 5;
            //Log.d("ploc", String.valueOf(bitmap.hasMipMap()));
            exp = 50;
        }else{
            setImageResourceId(R.mipmap.toben);
            this.hp = this.Maxhp = (float)(((float)level * 0.1 + 1) * 100);
            ad = (float)(((float)level * 0.1 + 1) * 5);
            exp = 5;
        }
        x=initx;
        y=inity;
        updateCollisionRect();
        this.level = level;

        return this;
    }
    public Enemy() {
        super(0, 0, 1);
    }
    public Enemy(int mipmapId, float fps, float initx, float inity) {
        super(mipmapId, fps);
        x=initx;
        y=inity;
    }

    @Override
    public void update() {
        playerPosition = MainScene.getPlayer().getPosition();


        float toX = playerPosition[0] - x;
        float toY = playerPosition[1] - y;
        float distance = (float) Math.sqrt(toX*toX + toY*toY);

        if( distance > 0.3) {
            float[] norm = normalize(toX, toY);
            dx = norm[0] * speed;
            dy = norm[1] * speed;
            super.update();

            //Log.d("eloc", Arrays.toString(new float[]{toY, toX}));
            //setPosition(x, y, bitmap.getWidth(), bitmap.getHeight());
            screenx=-toX + Metrics.width/2;
            screeny=-toY + Metrics.height/2;
            RectUtil.setRect(dstRect,screenx, screeny, bitmap.getWidth(), bitmap.getHeight());
        }
        if(hp<0){
            Scene.top().remove(this);
            MainScene.getPlayer().addExp(exp);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
        float gauge_width = width;
        float gauge_x = screenx - gauge_width / 2;
        float gauge_y = dstRect.bottom;
        gauge.draw(canvas,gauge_x, gauge_y, gauge_width, (float)hp / Maxhp);


    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.enemy;
    }

    @Override
    public void onRecycle() {

    }
    public float[] normalize(float x, float y){
        float length = (float) Math.sqrt(x * x + y * y);
        if (length != 0) {
            x /= length;
            y /= length;
        }
        return new float[]{x,y};
    }

    private void updateCollisionRect() {
        collisionRect.set(dstRect);
        collisionRect.inset(11f, 11f);
    }
    public float[] getLocation(){
        return new float[]{screenx, screeny};
    }

    @Override
    public void DoCollision(IGameObject other) {

        if(other instanceof Bullet){
            hp-=((Bullet) other).getPower();
            //Log.d("bullet", Float.toString(hp));
        }
    }
}
