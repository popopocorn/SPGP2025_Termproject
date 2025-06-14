package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class EnemyGenerator implements IGameObject {
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private final Random random = new Random();
    public static final float GEN_INTERVAL = 10.0f;
    private final MainScene scene;
    private float enemyTime = 0;
    private int wave;
    public EnemyGenerator(MainScene mainScene) {
        this.scene = mainScene;
    }

    @Override
    public void update() {
        enemyTime -= GameView.frameTime;

        if (enemyTime < 0) {
            generate();
            enemyTime = GEN_INTERVAL;
            Log.d("gen","");

        }
    }

    private void generate() {
        wave++;

        //StringBuilder enemies = new StringBuilder(); // for debug
        float[] playerPosition = MainScene.getPlayer().getPosition();
        float centerX = playerPosition[0];
        float centerY = playerPosition[1];
        float radius = 3000.0f;

        for (int i = 0; i < 10; i++) {

            //if (level > Enemy.MAX_LEVEL) level = Enemy.MAX_LEVEL;

            double angle = 2 * Math.PI / 10 * i; // 360도를 5등분
            float spawnX = centerX + (float)(radius * Math.cos(angle));
            float spawnY = centerY + (float)(radius * Math.sin(angle));

            scene.add(MainScene.Layer.enemy,Enemy.get(wave, i, spawnX, spawnY));
            //enemies.append(level); // for debug
        }
        //Log.v(TAG, "Generating: wave " + wave + " : " + enemies.toString());
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
