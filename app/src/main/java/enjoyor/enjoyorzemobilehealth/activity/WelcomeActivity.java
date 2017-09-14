package enjoyor.enjoyorzemobilehealth.activity;



import android.content.Context;
import android.view.animation.Animation;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.iv_welcome)
    ImageView imageView;
    Animation animator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        startAnimator();
        //judgeBackTaskState();

    }

    private void judgeBackTaskState() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appList = activityManager.getRunningTasks(1000);
        if (!(appList.get(0).baseActivity.getClassName().equals("com.enjoyor.activity.WelcomeActivity"))) {
            finish();
        }
        for (ActivityManager.RunningTaskInfo running : appList) {
            Log.d("activity", running.baseActivity.getClassName());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
    }

    private void startAnimator() {
        animator= AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.welcome_anim);
        imageView.startAnimation(animator);
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
