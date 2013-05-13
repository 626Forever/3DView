package sdu.edu.learn.scene;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import sdu.edu.learn.R;
import sdu.edu.learn.obj.Cube;
import sdu.edu.learn.obj.Polygon;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.MotionEvent;

@SuppressLint("WrongCall")
public class Scene implements Renderer {
	Context parent;

	Polygon p;
	Cube c;

	int[] textures = new int[10];

	public Scene(Context parent) {
		this.parent = parent;
		p = new Polygon(new float[] { -1.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f,
				1.0f, 1.0f, 0.0f, 1.0f, -1.0f, 0.0f, });
		p.setPolygonTye(GL10.GL_TRIANGLE_STRIP);

		c = new Cube(new float[] { -1.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f,
				1.0f, 0.0f, 1.0f, -1.0f, 0.0f,

				1.0f, 1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -2.0f, 1.0f,
				-1.0f, -2.0f,

				-1.0f, 1.0f, -2.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, -2.0f, 1.0f,
				1.0f, 0.0f,

				1.0f, 1.0f, -2.0f, 1.0f, -1.0f, -2.0f, -1.0f, 1.0f, -2.0f,
				-1.0f, -1.0f, -2.0f,

				-1.0f, 1.0f, -2.0f, -1.0f, -1.0f, -2.0f, -1.0f, 1.0f, 0.0f,
				-1.0f, -1.0f, 0.0f,

				-1.0f, -1.0f, 0.0f, -1.0f, -1.0f, -2.0f, 1.0f, -1.0f, 0.0f,
				1.0f, -1.0f, -2.0f });
		c.setTextureCoordinates(new float[] { 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0,
				1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1,
				0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1 });
		c.setPolygonTye(GL10.GL_TRIANGLE_STRIP);

	}

	public void loadTexture(GL10 gl, Bitmap bitmap) {
		gl.glGenTextures(1, textures, 0);// 获取材质id
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

	}

	/**
	 * 绘制区域
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -4);
		// p.onDraw(gl);
		c.onDraw(gl);
	}

	/**
	 * 窗口变化
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 90.0f, (float) width / (float) height, 0.1f,
				100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

	}

	/**
	 * 窗口创建
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

		Bitmap bitmap = BitmapFactory.decodeResource(parent.getResources(),
				R.drawable.fn);
		loadTexture(gl, bitmap);
		c.setTextures(textures);
	}

	public boolean onTouch(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			c.rotateX(15);
			break;
		}
		return true;

	}

}
