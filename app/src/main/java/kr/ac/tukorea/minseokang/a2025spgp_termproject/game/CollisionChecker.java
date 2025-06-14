package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final MainScene scene;
    private Mushmom player;
    public CollisionChecker(MainScene mainScene) {
        this.scene = mainScene;
        player=MainScene.getPlayer();
    }
    @Override
    public void update() {
        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        for (int e = enemies.size() - 1; e >= 0; e--) {
            Enemy enemyObject = (Enemy) enemies.get(e);
            RectF enemyRect = enemyObject.getCollisionRect();

            ArrayList<IGameObject> bullets = scene.objectsAt(MainScene.Layer.bullet);
            for (int b = bullets.size() - 1; b >= 0; b--) {
                Bullet bulletObject = (Bullet) bullets.get(b);
                RectF bulletRect = bulletObject.getCollisionRect();

                if (RectF.intersects(enemyRect, bulletRect)) {
                    //Log.d("CollisionDetection", "coll"); // 충돌 발생 시 로그 출력
                    if(bulletObject.hasHit(enemyObject)) {

                    }else{
                        enemyObject.DoCollision(bulletObject);
                        bulletObject.DoCollision(enemyObject);
                    }
                }
            }
            if(RectF.intersects(enemyRect, player.getCollisionRect())){
                player.DoCollision(enemyObject);
            }
        }
    }
    @Override
    public void draw(Canvas canvas) {}
}

