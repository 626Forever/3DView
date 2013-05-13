package sdu.edu.learn.view;

import sdu.edu.learn.scene.Scene;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MainView extends GLSurfaceView {
	Scene scene;

	public MainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MainView(Context context, Scene scene) {
		super(context);
		this.scene = scene;
		this.setRenderer(scene);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		scene.onTouch(event);
		return true;
	}
	
	
}
