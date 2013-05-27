package sdu.edu.learn.obj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import sdu.edu.learn.scene.Ray;

/**
 * 球体
 * 
 * @author hzy
 * 
 */
public class Sphere extends Multilateral {

	private int vCount;
	public static float pi = 3.14159265357f;
	private float radius;

	private float[] center;
	private float translateCoordinats[] = new float[3];
	private float scales[] = new float[3];
	private float rotateMatrix[];
	private float textureCoordinats[];

	private FloatBuffer textCoordinats;
	private FloatBuffer vertexs;
	private FloatBuffer nomalBuffer;

	public Sphere(float[] center, float radius) {
		this.radius = radius;
		this.center = new float[3];
		this.center[0] = center[0];
		this.center[1] = center[1];
		this.center[2] = center[2];
		for (int i = 0; i < 3; i++) {
			translateCoordinats[i] = 0;
			scales[i] = 1;
		}
		this.init();
	}

	private void init() {
		rotateMatrix = new float[] { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0,
				0, 1 };

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
		textureCoordinats = new float[alVertex.size()];

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

		ByteBuffer nb = ByteBuffer.allocateDirect(textureCoordinats.length * 4); // 一个整型是4个字节
		nb.order(ByteOrder.nativeOrder()); // 设置字节顺序
		textCoordinats = nb.asFloatBuffer(); // 转换成int型缓冲
		textCoordinats.put(vertices); // 想缓冲区放入顶点坐标数据
		textCoordinats.position(0); // 设置缓冲区起始位置
	}

	@Override
	public void onDraw(GL10 gl) {
		// TODO Auto-generated method stub

		gl.glPushMatrix();
		gl.glTranslatef(translateCoordinats[0], translateCoordinats[1],
				translateCoordinats[2]);
		gl.glTranslatef(center[0], center[1], center[2]);
		gl.glMultMatrixf(rotateMatrix, 0);
		gl.glScalef(scales[0], scales[1], scales[2]);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); // 启用顶点坐标数组
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY); // 启用顶点向量数组

		// 为画笔指定顶点坐标数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexs);
		// 为画笔指定顶点向量数据
		gl.glNormalPointer(GL10.GL_FLOAT, 0, vertexs);
		// 绘制图形
		for (int i = 0; i < vCount; i++) {
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i * 4, 4);
		}
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glPopMatrix();
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
		this.translateCoordinats[0] = x;
		this.translateCoordinats[1] = y;
		this.translateCoordinats[2] = z;
	}

	@Override
	public void rotate(float x, float y, float z, float angle) {
		// TODO Auto-generated method stub
		float len = x * x + y * y + z * z;
		len = (float) Math.sqrt(len);
		float a = angle / 180 * Sphere.pi;
		float cosA = (float) Math.cos(a);
		float sinA = (float) Math.sin(a);
		float x1 = x / len;
		float y1 = y / len;
		float z1 = z / len;
		float m00 = cosA + (1 - cosA) * x1 * x1;
		float m01 = (1 - cosA) * x1 * y1 - sinA * z1;
		float m02 = (1 - cosA) * x1 * z1 + sinA * y1;

		float m10 = (1 - cosA) * x1 * y1 + sinA * z1;
		float m11 = cosA + (1 - cosA) * y1 * y1;
		float m12 = (1 - cosA) * y1 * z1 - sinA * x1;

		float m20 = (1 - cosA) * x1 * z1 - sinA * y1;
		float m21 = (1 - cosA) * y1 * z1 + sinA * x1;
		float m22 = cosA + (1 - cosA) * z1 * z1;

		float M00 = rotateMatrix[0];
		float M01 = rotateMatrix[1];
		float M02 = rotateMatrix[2];

		float M10 = rotateMatrix[4];
		float M11 = rotateMatrix[5];
		float M12 = rotateMatrix[6];

		float M20 = rotateMatrix[8];
		float M21 = rotateMatrix[9];
		float M22 = rotateMatrix[10];

		rotateMatrix[0] = m00 * M00 + m01 * M10 + m02 * M20;
		rotateMatrix[1] = m00 * M01 + m01 * M11 + m02 * M21;
		rotateMatrix[2] = m00 * M02 + m01 * M12 + m02 * M22;
		rotateMatrix[3] = 0;
		rotateMatrix[4] = m10 * M00 + m11 * M10 + m12 * M20;
		rotateMatrix[5] = m10 * M01 + m11 * M11 + m12 * M21;
		rotateMatrix[6] = m10 * M02 + m11 * M12 + m12 * M22;
		rotateMatrix[7] = 0;
		rotateMatrix[8] = m20 * M00 + m21 * M10 + m22 * M20;
		rotateMatrix[9] = m20 * M01 + m21 * M11 + m22 * M21;
		rotateMatrix[10] = m20 * M02 + m21 * M12 + m22 * M22;
		rotateMatrix[11] = 0;
		rotateMatrix[12] = 0;
		rotateMatrix[13] = 0;
		rotateMatrix[14] = 0;
		rotateMatrix[15] = 1;
	}

	@Override
	public void scale(float xs, float ys, float zs) {
		// TODO Auto-generated method stub
		scale(xs);
	}

	private void scale(float ra) {
		scales[0] *= ra;
		scales[1] *= ra;
		scales[2] *= ra;
	}

	@Override
	public int intersect(Ray ray) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Ray invert(Ray ray) {
		return ray;
	}

	public float[] getCenter() {
		float c[] = new float[3];
		c[0] = center[0] + translateCoordinats[0];
		c[1] = center[1] + translateCoordinats[1];
		c[2] = center[2] + translateCoordinats[2];
		return c;
	}

	public float getSphereRadius() {
		return this.radius * scales[0];
	}

	public void move(Ray ray) {
		float direct[] = ray.getDirectVector();
		float origin[] = ray.getOriginVector();
		float a = (center[2] - origin[2]) / direct[2];
		float x = a * direct[0] + origin[0] - center[0];
		float y = a * direct[1] + origin[1] - center[1];
		float z = 0;
		this.translate(x, y, z);
	}
}
