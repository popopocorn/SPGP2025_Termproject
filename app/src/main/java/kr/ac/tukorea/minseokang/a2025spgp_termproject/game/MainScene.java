package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.util.Log;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    private static final JoyStick joyStick= new JoyStick(R.mipmap.joystick_bg, R.mipmap.joystick_thumb, 450, 1400, 200, 60, 160);
    private static final Mushmom player = new Mushmom();

    private static EnhanceManager enhanceManager = new EnhanceManager();




    //private final Score score;

    public enum Layer {
        bg1, enemy, bullet, player, bg2, ui, controller;
        public static final int COUNT = values().length;
    }
    public MainScene() {
        //Metrics.setGameSize(900, 1600); default=900x1600
        initLayers(Layer.COUNT);

        add(Layer.bg1, new ScrollBackground(R.mipmap.futsal_court));
        //add(Layer.bg2, new VertScrollBackground(R.mipmap.clouds, 40));


        add(Layer.player, player);

        // this.score = new Score(R.mipmap.number_24x32, 850f, 50f, 60f);
        //score.setScore(0);
        //add(Layer.ui, score);
        add(Layer.ui, joyStick);
        add(Layer.controller, new EnemyGenerator(this));
        add(Layer.controller, new CollisionChecker(this));
    }

    // Game Loop Functions
    @Override
    public void update() {
        super.update();
        float tempDist = Float.MAX_VALUE;
        Enemy target = null;

        for(IGameObject object : layers.get(1)){

            if (object instanceof Enemy) {
                Enemy enemy = (Enemy)object;
                float[] pPosition = new float[]{Metrics.width/2, Metrics.height/2};
                float[] ePosition = enemy.getLocation();
                float curDist = distance(pPosition, ePosition);
                if(curDist < tempDist) {
                    tempDist = curDist;
                    target = enemy;
                }
            }
        }


        if(target != null){
            player.setTarget(target.getLocation());
        }
    }
    private float distance(float[] a, float[] b){
        float dist = (float)Math.hypot(a[0]-b[0], a[1]-b[1]);
        return dist;
    }

    public void addScore(int amount) {
        //score.add(amount);
    }
    public int getScore() {
        //return score.getScore();
        return 0;
    }

    // Overridables

    // Game Loop Functions

    public static JoyStick getJoyStick(){ return joyStick;}
    public static Mushmom getPlayer(){ return player;}
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return joyStick.onTouch(event);
    }
    public static EnhanceManager getEnhanceManager(){ return enhanceManager;}
}
