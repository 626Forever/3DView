package sdu.edu.learn.scene;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import sdu.edu.learn.R;
import sdu.edu.learn.obj.Cube;
import sdu.edu.learn.obj.Multilateral;
import sdu.edu.learn.obj.Sphere;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.MotionEvent;

/**
 * ��ά����
 * 
 * @author lhy
 * 
 */
public class Scene implements Renderer {
	private Context parent;
	/**
	 * ���������������Ա�
	 */
	private ArrayList<Multilateral> objs = new ArrayList<Multilateral>();
	// Polygon p;
	/**
	 * ����һЩ���õ�����id
	 */
	int[] textures = new int[10];

	// ���廷����
	private FloatBuffer lightAmbient = FloatBuffer.wrap(new float[] {1.0f,
			1.0f, 1.0f, 1.0f });
	// ���������
	private FloatBuffer lightDiffuse = FloatBuffer.wrap(new float[] { 1.0f,
			1.0f, 1.0f, 1.0f });

	private FloatBuffer lightSpecular = FloatBuffer.wrap(new float[] { 1.0f,
			1.0f, 1.0f, 1.0f });
	// �����Դ��λ��
	private FloatBuffer lightPosition = FloatBuffer.wrap(new float[] { 2.0f,
			2.0f, 2.0f, 0.0f });

	public Scene(Context parent) {
		this.parent = parent;

		Cube c = new Cube(0, 0, -4);
		c.rotateX(45);
		c.rotateY(45);
		c.scale(0.5f, 0.5f, 0.5f);
		objs.add(c);

		c = new Cube(3, -1, -8);
		c.rotateX(65);
		c.rotateY(75);
		c.rotateZ(109);
		c.scale(0.5f, 0.5f, 0.5f);
		objs.add(c);

		Sphere s = new Sphere(new float[] { 1, 1, -6 }, 0.4f);
		s.rotateX(-90);
		objs.add(s);
		s = new Sphere(new float[] { -5, -4, -10 }, 0.4f);
		s.rotateX(-90);
		objs.add(s);

	}

	/**
	 * װ��λͼ������ͼ����������
	 * 
	 * @param gl
	 *            GL����
	 * @param bitmap
	 *            ͼ���ļ�
	 */
	public void loadTexture(GL10 gl, Bitmap bitmap) {
		gl.glGenTextures(1, textures, 0);// ��ȡ����id
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		/**
		 * ���������ϸ��Ч��
		 */
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
	 * ��������
	 */
	@SuppressLint("WrongCall")
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		// ������Դ
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		for (int i = 0; i < objs.size(); i++) {
			objs.get(i).onDraw(gl);
		}
		updatePick();// ���ʰȡ

	}

	/**
	 * ʰȡ��⺯��
	 */
	public void updatePick() {
		if (!RayFactory.isPickful()) {
			return;
		}
		boolean found = false;
		float zDeapth = -10000f;// �����Ϣ
		int objLoc = 0; // ʰȡ���Ķ����id
		final Ray ray = RayFactory.getRay();// ��ȡ��Ӧ��Ļ�����������
		for (int i = 0; i < objs.size(); i++) {
			Multilateral m = objs.get(i);
			/**
			 * ���������ö�����������ཻ�����Խ�����һ�����
			 */
			if (ray.intersectWithSphere(m.getCenter(), m.getSphereRadius())) {
				Ray ray1 = m.invert(ray);
				int face = m.intersect(ray1);// ��������������Ǹ����ཻ
				if (face != -1) {
					if (!found) {
						found = true;
						zDeapth = m.getCenter()[2];
						objLoc = i;
					} else {
						// ����ǰ�Ķ���������Ϣ������˵����ǰ�������ѡ���Ķ�������������
						if (zDeapth < m.getCenter()[2]) {
							zDeapth = m.getCenter()[2];
							objLoc = i;
						}
					}
				}
			}
		}
		if (zDeapth != -10000f) {// ���ж���ѡ��
			objs.get(objLoc).setPicked(true);
		}
	}

	/**
	 * ���ڱ仯,ͬʱ�������
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		// Sets the current view port to the new size.
		RayFactory.setViewPort(new float[] { 0, 0, width, height });
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		float foxy = 90.0f;
		float zNear = 0.1f;
		float zFar = 100.0f;
		GLU.gluPerspective(gl, foxy, (float) width / (float) height, zNear,
				zFar);
		RayFactory.setFoxy(foxy);
		RayFactory.setzNear(zNear);
		RayFactory.setzFar(zFar);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	/**
	 * ���ڴ���,�Լ�������س�ʼ��
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

		// ���û�����
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient);
		// ���������
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, lightSpecular);
		// ���ù�Դ��λ��
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition);
		// ����ID��ΪGL_LIGHT1�Ĺ�Դ
		gl.glEnable(GL10.GL_LIGHT1);

		Bitmap bitmap = BitmapFactory.decodeResource(parent.getResources(),
				R.drawable.fn);
		loadTexture(gl, bitmap);
		for (int i = 0; i < objs.size(); i++) {
			objs.get(i).setTextures(textures);
		}
	}

	/**
	 * scene�Դ����¼�����Ӧ
	 * 
	 * @param event
	 * @return
	 */
	public boolean onTouch(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		/**
		 * �������߹������µ���Ļ����
		 */
		RayFactory.setTouchPostion(new float[] { x, y });
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			RayFactory.setPickful(true);// ���ʰȡ
			break;
		case MotionEvent.ACTION_UP:
			RayFactory.setPickful(false);// ȡ��ʰȡ
			for (int i = 0; i < objs.size(); i++) {
				objs.get(i).setPicked(false);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			RayFactory.setPickful(false);// ȡ��ʰȡ���ƶ���ʰȡ���Ķ���
			for (int i = 0; i < objs.size(); i++) {
				Multilateral m = objs.get(i);
				if (m.isPicked()) {
					m.move(RayFactory.getRay());
				}
			}
			break;
		}
		return true;

	}

	/**
	 * scene�ĳ����¼�����,������
	 * 
	 * @param event
	 * @return
	 */
	public boolean onLongPress(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		RayFactory.setTouchPostion(new float[] { x, y });
		RayFactory.setPickful(true);
		for (int i = 0; i < objs.size(); i++) {
			Multilateral m = objs.get(i);
			if (m.isPicked()) {
				if (!m.isScaled()) {
					m.scale(2f, 2f, 2f);
					m.setScaled(true);
				} else {
					m.scale(0.5f, 0.5f, 0.5f);
					m.setScaled(false);
				}
			}
		}
		RayFactory.setPickful(false);
		return false;
	}
}
