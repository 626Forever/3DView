package sdu.edu.learn.activity;

import sdu.edu.learn.R;
import sdu.edu.learn.scene.Scene;
import sdu.edu.learn.view.MainView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * »ù´¡Activity
 * 
 * @author hzy
 * 
 */
public class MainActivity extends Activity {
	public final static int MOVE_MODE = 1;
	public final static int ROTATE_MODE = 2;
	MainView view;
	Button rotate;
	Button move;
	LinearLayout mainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setContentView(R.layout.activity_main);
		rotate = (Button) this.findViewById(R.id.rotateButton);
		move = (Button) this.findViewById(R.id.moveButton);
		mainLayout = (LinearLayout) this.findViewById(R.id.mainViewLayout);
		rotate.setText("Ðý×ª");
		move.setText("ÒÆ¶¯");
		Scene scene = new Scene(this);
		view = new MainView(this, scene);
		mainLayout.addView(view);
		addListener();
	}

	public void addListener() {
		rotate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				view.setMode(ROTATE_MODE);
			}
		});
		move.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				view.setMode(MOVE_MODE);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
