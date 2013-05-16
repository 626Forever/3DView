package sdu.edu.learn.obj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Sphere implements PolygonObject {

	int vCount;
	private float pi = 3.14159265357f;
	private float radius;
	private float[] center;

	private float translateCoordinats[] = new float[3];
	private float rotateAngles[] = new float[3];
	private float scales[] = new float[3];

	private FloatBuffer textCoordinats;
	private FloatBuffer vertexs;
	private FloatBuffer nomalBuffer;

	public Sphere(float[] center, float radius) {
		this.radius = radius;
		this.center = center;
		for (int i = 0; i < 3; i++) {
			translateCoordinats[i] = 0;
			rotateAngles[i] = 0;
			scales[i] = 1;
		}
		this.init();
	}

	private void init() {
		float span = pi / 20;
		ArrayList<Float> alVertex = new ArrayList<Float>();
		for (float angleY = -pi / 2 + span; angleY <= pi / 2; angleY += span) {
			for (float angleXoZ = 0; angleXoZ < 2 * pi; angleXoZ += span) {

				float y = (float) Math.sin(angleY) * radius;
				float xoz = (float) Math.cos(angleY) * radius;
				float x = (float) Math.sin(angleXoZ) * xoz;
				float z = (float) Math.cos(angleXoZ) * xoz;
				alVertex.add(x);
				alVertex.add(y);
				alVertex.add(z);

				y = (float) Math.sin(angleY - span) * radius;
				xoz = (float) Math.cos(angleY - span) * radius;
				x = (float) Math.sin(angleXoZ) * xoz;
				z = (float) Math.cos(angleXoZ) * xoz;
				alVertex.add(x);
				alVertex.add(y);
				alVertex.add(z);

				y = (float) Math.sin(angleY) * radius;
				xoz = (float) Math.cos(angleY) * radius;
				x = (float) Math.sin(angleXoZ + span) * xoz;
				z = (float) Math.cos(angleXoZ + span) * xoz;
				alVertex.add(x);
				alVertex.add(y);
				alVertex.add(z);

				y = (float) Math.sin(angleY - span) * radius;
				xoz = (float) Math.cos(angleY - span) * radius;
				x = (float) Math.sin(angleXoZ + span) * xoz;
				z = (float) Math.cos(angleXoZ + span) * xoz;
				alVertex.add(x);
				alVertex.add(y);
				alVertex.add(z);
			}
		}

		vCount = alVertex.size() / 12;
		float vertices[] = new float[alVertex.size()];
		for (int i = 0; i < alVertex.size(); i++) {
			vertices[i] = alVertex.get(i);
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexs = vbb.asFloatBuffer();
		vertexs.put(vertices);
		vertexs.position(0);

		ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length * 4); // 一个整型是4个字节
		nbb.order(ByteOrder.nativeOrder()); // 设置字节顺序
		nomalBuffer = nbb.asFloatBuffer(); // 转换成int型缓冲
		nomalBuffer.put(vertices); // 想缓冲区放入顶点坐标数据
		nomalBuffer.position(0); // 设置缓冲区起始位置
	}

	@Override
	public void onDraw(GL10 gl) {
		// TODO Auto-generated method stub
		// gl.glRotatef(90, 1, 0, 0); // 沿x轴旋转

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); // 启用顶点坐标数组
		// gl.glEnableClientState(GL10.GL_NORMAL_ARRAY); // 启用顶点向量数组

		// 为画笔指定顶点坐标数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexs);
		// 为画笔指定顶点向量数据
		// gl.glNormalPointer(GL10.GL_FLOAT, 0, vertexs);
		// 绘制图形
		for (int i = 0; i < vCount; i++) {
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i * 4, 4);
		}
	}

	@Override
	public void setVertexf(float[] vs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTextureCoordinates(float[] textureCoords) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPolygonTye(int Type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void translate(float x, float y, float z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateX(float angle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateY(float angle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateZ(float angle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(float xs, float ys, float zs) {
		// TODO Auto-generated method stub

	}

}
