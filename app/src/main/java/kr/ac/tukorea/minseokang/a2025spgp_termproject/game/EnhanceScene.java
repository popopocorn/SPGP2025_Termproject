package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class EnhanceScene extends Scene {
    private ArrayList<Enhance> enhancePool=new ArrayList<>();
    public enum Layer {
        bg, title, touch
    }
    private float cnt=0;
    public EnhanceScene(){
        initLayers(Layer.values().length);
        float w = Metrics.width, h = Metrics.height;
        enhancePool=MainScene.getEnhanceManager().getEnhanceList();
    }

    // Game Loop Functions
    @Override
    public void update() {
        super.update();
        cnt += GameView.frameTime;

        if(cnt > 3){
            pop();
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    // Overridables
    @Override
    public boolean isTransparent() {
        return true;
    }
}
