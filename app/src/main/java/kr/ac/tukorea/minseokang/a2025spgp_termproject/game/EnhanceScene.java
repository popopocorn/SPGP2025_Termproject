package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.R;

public class EnhanceScene extends Scene {
    private ArrayList<Enhance> enhancePool=new ArrayList<>();
    private EnhanceManager manager;
    private boolean selected= false;
    private Mushmom player;
    private boolean cantouch = false;

    public enum Layer {
        bg, title, touch
    }
    private static final HashMap<Enhance, Integer> resIds =  new HashMap<>();
    static {
        resIds.put(Enhance.AdUp, R.mipmap.adup);
        resIds.put(Enhance.SizeUp, R.mipmap.sizeup);
        resIds.put(Enhance.AsUp, R.mipmap.asup);
        resIds.put(Enhance.SpeedUp, R.mipmap.speedup);
        resIds.put(Enhance.nonHitUp, R.mipmap.nonhitup);
    }
    private float cnt=0;
    public EnhanceScene(){
        player = MainScene.getPlayer();
        initLayers(Layer.values().length);
        float w = Metrics.width, h = Metrics.height;
        manager=MainScene.getEnhanceManager();
        enhancePool=manager.getEnhanceList();
        selected=false;
        if(enhancePool.size()>1) {
            Enhance e1 = enhancePool.get(0);
            Enhance e2 = enhancePool.get(1);
            add(Layer.touch, new Button(resIds.get(e1), Metrics.width/3, Metrics.height/2, 200f, 100f, new Button.OnTouchListener(){
                @Override
                public boolean onTouch(boolean pressed) {
                    //Log.d("btn", "pressed1");
                    player.addEnhance(e1, false);
                    manager.popEnhance(e1);
                    pop();


                    return false;
                }
            }));

            add(Layer.touch, new Button(resIds.get(e2), Metrics.width*2/3, Metrics.height/2, 200f, 100f, new Button.OnTouchListener(){
                @Override
                public boolean onTouch(boolean pressed) {
                    //Log.d("btn", "pressed2");

                    player.addEnhance(e2, false);
                    manager.popEnhance(e2);

                    pop();

                    return false;
                }
            }));
        }else if(enhancePool.size()==1){
            Enhance e = enhancePool.get(0);
            add(Layer.touch, new Button(resIds.get(e), Metrics.width/2, Metrics.height/2, 200f, 100f, new Button.OnTouchListener(){
                @Override
                public boolean onTouch(boolean pressed) {
                    //Log.d("btn", "pressed");
                    player.addEnhance(e, false);
                    manager.popEnhance(e);

                    pop();

                    return false;
                }
            }));
        }else{
            add(Layer.touch, new Button(resIds.get(Enhance.AdUp), Metrics.width/2, Metrics.height/2, 200f, 100f, new Button.OnTouchListener(){
                @Override
                public boolean onTouch(boolean pressed) {
                    //Log.d("btn", "pressed");
                    player.addEnhance(null, true);
                    pop();

                    return false;
                }
            }));
        }



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
