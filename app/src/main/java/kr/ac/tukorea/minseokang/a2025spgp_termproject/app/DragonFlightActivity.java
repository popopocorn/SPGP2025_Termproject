package kr.ac.tukorea.minseokang.a2025spgp_termproject.app;

import android.os.Bundle;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.BuildConfig;
import kr.ac.tukorea.minseokang.a2025spgp_termproject.game.MainScene;

public class DragonFlightActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}