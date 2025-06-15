package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.R;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.app.DragonFlightActivity;

public class DieScene extends Scene {
    private boolean cantouch = false;

    public enum Layer {
        bg, title, touch
    }

    private float cnt=0;
    public DieScene(){

        initLayers(Layer.values().length);
        float w = Metrics.width, h = Metrics.height;
            add(Layer.touch, new Button(R.mipmap.exit, Metrics.width/3, Metrics.height/2, 200f, 100f, new Button.OnTouchListener(){
                @Override
                public boolean onTouch(boolean pressed) {
                    //Log.d("btn", "pressed1");

                    popAll();
                    new MainScene().push();


                    return false;
                }
            }));

    }

    // Game Loop Functions
    @Override
    public void update() {
        cnt+=GameView.frameTime;
        if(cnt>0.5){
            cantouch = true;
        }
    }

    // Overridables
    @Override
    public boolean isTransparent() {
        return true;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        ArrayList<IGameObject> objs = this.objectsAt(Layer.touch);
//        for(IGameObject gobjs : objs){
//            ITouchable btn = (ITouchable)gobjs;
//            if(btn.onTouchEvent(event)){
//                return true;
//            }
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    protected int getTouchLayerIndex() {
        if(cantouch)
            return Layer.touch.ordinal();
        else
            return -1;
    }
}
