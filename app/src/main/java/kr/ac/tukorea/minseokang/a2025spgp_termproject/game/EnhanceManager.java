package kr.ac.tukorea.minseokang.a2025spgp_termproject.game;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;


public class EnhanceManager implements IGameObject {
    private ArrayList<Enhance> enhanceList = new ArrayList<>(Arrays.asList(
            Enhance.AdUp,
            Enhance.AdUp,
            Enhance.AdUp,
            Enhance.AdUp,
            Enhance.AdUp,

            Enhance.AsUp,
            Enhance.AsUp,
            Enhance.AsUp,
            Enhance.AsUp,
            Enhance.AsUp,

            Enhance.SizeUp,

            Enhance.SpeedUp,
            Enhance.SpeedUp,
            Enhance.SpeedUp,

            Enhance.nonHitUp

    ));
    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    public ArrayList<Enhance> getEnhanceList() {
        Collections.shuffle(enhanceList, new Random());
        return enhanceList;
    }

    public void popEnhance(Enhance e){
        if(enhanceList.contains(e)){
            enhanceList.remove(e);
        }
    }
}
