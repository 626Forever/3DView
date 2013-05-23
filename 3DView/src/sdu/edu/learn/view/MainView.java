package sdu.edu.learn.view;

import sdu.edu.learn.scene.Scene;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainView extends GLSurfaceView {
	private Scene scene;
	private Context context;
	private GestureDetector gestureScanner;

	public MainView(Context context) {
		super(context);

		// TODO Auto-generated constructor stub
	}

	public MainView(Context context, Scene scene) {
		// TODO Auto-generated constructor stub
		super(context);
		this.scene = scene;
		this.setRenderer(scene);
		this.context = context;
		gestureScanner = new GestureDetector(new OnGestureListener() {
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				MainView.this.scene.onLongPress(e);
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		gestureScanner
				.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
					@Override
					public boolean onDoubleTap(MotionEvent arg0) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean onDoubleTapEvent(MotionEvent e) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean onSingleTapConfirmed(MotionEvent e) {
						// TODO Auto-generated method stub
						return false;
					}
				});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		scene.onTouch(event);
		gestureScanner.onTouchEvent(event);
		return true;
	}

}
