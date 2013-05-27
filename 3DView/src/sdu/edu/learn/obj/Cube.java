package sdu.edu.learn.obj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import sdu.edu.learn.scene.Ray;
import sdu.edu.learn.scene.RayFactory;
import sdu.edu.learn.util.Matrix;

/**
 * 立方体
 * 
 * @author lhy
 * 
 */
public class Cube extends Multilateral {

	private int polygon_Type = GL10.GL_TRIANGLE_STRIP;;
	private int textures[];

	private float center[] = new float[3];
	private float translateCoordinats[] = new float[3];
	private float scales[] = new float[3];
	private float vertices[];
	private float textureCoordinats[];
	private float rotateMatrix[];

	private FloatBuffer textCoordinats;
	private FloatBuffer vertexs;

	private Matrix matrix;

	public Cube(float x, float y, float z) {
		center[0] = x;
		center[1] = y;
		center[2] = z;
		for (int i = 0; i < 3; i++) {
			translateCoordinats[i] = 0;
			scales[i] = 1;
		}
		init();
	}

	public Cube(float vs[]) {
		// a float is 4 bytes, therefore we multiply the
		// number if vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vs.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexs = vbb.asFloatBuffer();
		vertexs.put(vs);
		vertexs.position(0);
		for (int i = 0; i < 3; i++) {
			translateCoordinats[i] = 0;
			scales[i] = 1;
		}
	}

	/**
	 * 初始化
	 */
	private void init() {
		rotateMatrix = new float[] { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0,
				0, 1 };
		vertices = new float[] { -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f,
				1.0f, 1.0f, 1.0f, -1.0f, 1.0f,

				1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f,
				-1.0f, -1.0f,

				-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f,
				1.0f, 1.0f,

				1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f,
				-1.0f, -1.0f, -1.0f,

				-1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f,
				-1.0f, -1.0f, 1.0f,

				-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f,
				1.0f, -1.0f, -1.0f };
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexs = vbb.asFloatBuffer();
		vertexs.put(vertices);
		vertexs.position(0);

		this.textureCoordinats = new float[] { 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0,
				1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1,
				0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1 };

		ByteBuffer byteBuf = ByteBuffer
				.allocateDirect(textureCoordinats.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textCoordinats = byteBuf.asFloatBuffer();
		textCoordinats.put(textureCoordinats);
		textCoordinats.position(0);

	}

	@Override
	public void onDraw(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glPushMatrix();
		/**
		 * 变换遵循先缩放，在旋转，最后移动的顺序
		 */
		gl.glTranslatef(translateCoordinats[0], translateCoordinats[1],
				translateCoordinats[2]);
		gl.glTranslatef(center[0], center[1], center[2]);
		// gl.glRotatef(rotateAngles[2], 0, 0, 1);
		// gl.glRotatef(rotateAngles[1], 0, 1, 0);
		// gl.glRotatef(rotateAngles[0], 1, 0, 0);
		gl.glMultMatrixf(rotateMatrix, 0);
		gl.glScalef(scales[0], scales[1], scales[2]);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexs);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textCoordinats);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		for (int i = 0; i < 6; i++) {
			gl.glDrawArrays(polygon_Type, i * 4, 4);
		}
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glPopMatrix();
		gl.glDisable(GL10.GL_CULL_FACE);
	}

	public void setTextures(int[] textures) {
		this.textures = textures;
	}

	@Override
	public void setVertexf(float[] vs) {
		// TODO Auto-generated method stub
		this.vertices = vs;
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexs = vbb.asFloatBuffer();
		vertexs.put(vertices);
		vertexs.position(0);
	}

	@Override
	public void setTextureCoordinates(float[] textureCoords) {
		// TODO Auto-generated method stub

		// float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		this.textureCoordinats = textureCoords;
		ByteBuffer byteBuf = ByteBuffer
				.allocateDirect(textureCoordinats.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textCoordinats = byteBuf.asFloatBuffer();
		textCoordinats.put(textureCoordinats);
		textCoordinats.position(0);

	}

	@Override
	public void setPolygonTye(int Type) {
		// TODO Auto-generated method stub
		polygon_Type = Type;
	}

	@Override
	public void translate(float x, float y, float z) {
		// TODO Auto-generated method stub
		translateCoordinats[0] = x;
		translateCoordinats[1] = y;
		translateCoordinats[2] = z;
	}

	@Override
	public void scale(float xs, float ys, float zs) {
		// TODO Auto-generated method stub
		scales[0] *= xs;
		scales[1] *= ys;
		scales[2] *= zs;
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
	public int intersect(Ray ray) {
		// TODO Auto-generated method stub
		if (ray == null) {
			return -1;
		}
		boolean bfound = false;
		float zDeapth = 0.0f;
		int surfaceNum = -1;
		float location[] = new float[3];

		Vertex v0, v1, v2, v3;
		/**
		 * 依次检测立方体的每个面
		 */
		for (int i = 0; i < 6; i++) {
			float v[] = new float[3];
			v[0] = vertices[i * 12];
			v[1] = vertices[i * 12 + 1];
			v[2] = vertices[i * 12 + 2];
			v0 = new Vertex(v);

			v[0] = vertices[i * 12 + 3];
			v[1] = vertices[i * 12 + 4];
			v[2] = vertices[i * 12 + 5];
			v1 = new Vertex(v);

			v[0] = vertices[i * 12 + 6];
			v[1] = vertices[i * 12 + 7];
			v[2] = vertices[i * 12 + 8];
			v2 = new Vertex(v);

			v[0] = vertices[i * 12 + 9];
			v[1] = vertices[i * 12 + 10];
			v[2] = vertices[i * 12 + 11];
			v3 = new Vertex(v);
			/**
			 * 射线是否与该面相交
			 */
			if (ray.intersectWithPolygon(new Vertex[] { v0, v1, v2, v3 },
					location)) {
				if (!bfound) {
					bfound = true;
					zDeapth = location[2];
					surfaceNum = i;
				} else {
					if (zDeapth < location[2]) {
						zDeapth = location[2];
						surfaceNum = i;
					}
				}
			}

		}
		if (bfound) {
			return surfaceNum;
		} else
			return -1;
	}

	/**
	 * 转置射线，这里的思想是：由于射线与立方体的空间相对位置是不会变的，如果把立方体倒转为最初的状态，以此倒转矩阵处理
	 * 射线，则可以得到与立方体空间相对位置不变的射线，在求出该射线与立方体的交点后，由于立方体的最初状态是事先定义好的
	 * 利用得到的交点可以轻松地判断该交点在不在立方体的某个面上，从而确定该射线是否与立方体相交
	 */
	public Ray invert(Ray ray) {
		Ray ray1 = RayFactory.getRay();
		float direct[] = ray.getDirectVector();
		float origin[] = ray.getOriginVector();
		/**
		 * 还原移动
		 */
		origin[0] -= translateCoordinats[0] + center[0];
		origin[1] -= translateCoordinats[1] + center[1];
		origin[2] -= translateCoordinats[2] + center[2];

		matrix = new Matrix(rotateMatrix, 4);
		Matrix invMatrix = matrix.matrixInv();
		float[] inv = invMatrix.getData();

		// float rotateX = rotateAngles[0] / 180 * Sphere.pi;
		// float rotateY = rotateAngles[1] / 180 * Sphere.pi;
		// float rotateZ = rotateAngles[2] / 180 * Sphere.pi;

		float directX = direct[0];
		float directY = direct[1];
		float directZ = direct[2];

		float originX = origin[0];
		float originY = origin[1];
		float originZ = origin[2];

		// /**
		// * 绕z轴倒转
		// */
		//
		// direct[0] = (float) Math.cos(rotateZ) * directX
		// + (float) Math.sin(rotateZ) * directY;
		// direct[1] = -(float) Math.sin(rotateZ) * directX
		// + (float) Math.cos(rotateZ) * directY;
		//
		// origin[0] = (float) Math.cos(rotateZ) * originX
		// + (float) Math.sin(rotateZ) * originY;
		// origin[1] = -(float) Math.sin(rotateZ) * originX
		// + (float) Math.cos(rotateZ) * originY;
		//
		// directX = direct[0];
		// directY = direct[1];
		// directZ = direct[2];
		//
		// originX = origin[0];
		// originY = origin[1];
		// originZ = origin[2];
		//
		// /**
		// * 绕y轴倒转
		// */
		// direct[0] = (float) Math.cos(rotateY) * directX
		// - (float) Math.sin(rotateY) * directZ;
		// direct[2] = (float) Math.sin(rotateY) * directX
		// + (float) Math.cos(rotateY) * directZ;
		//
		// origin[0] = (float) Math.cos(rotateY) * originX
		// - (float) Math.sin(rotateY) * originZ;
		// origin[2] = (float) Math.sin(rotateY) * originX
		// + (float) Math.cos(rotateY) * originZ;
		//
		// directX = direct[0];
		// directY = direct[1];
		// directZ = direct[2];
		//
		// originX = origin[0];
		// originY = origin[1];
		// originZ = origin[2];
		//
		// /**
		// * 绕x轴倒转
		// */
		// direct[1] = (float) Math.cos(rotateX) * directY
		// + (float) Math.sin(rotateX) * directZ;
		// direct[2] = (float) -Math.sin(rotateX) * directY
		// + (float) Math.cos(rotateX) * directZ;
		//
		// origin[1] = (float) Math.cos(rotateX) * originY
		// + (float) Math.sin(rotateX) * originZ;
		// origin[2] = (float) -Math.sin(rotateX) * originY
		// + (float) Math.cos(rotateX) * originZ;

		direct[0] = inv[0] * directX + inv[1] * directY + inv[2] * directZ;
		direct[1] = inv[4] * directX + inv[5] * directY + inv[6] * directZ;
		direct[2] = inv[8] * directX + inv[9] * directY + inv[10] * directZ;

		origin[0] = inv[0] * originX + inv[1] * originY + inv[2] * originZ;
		origin[1] = inv[4] * originX + inv[5] * originY + inv[6] * originZ;
		origin[2] = inv[8] * originX + inv[9] * originY + inv[10] * originZ;

		/**
		 * 还原缩放
		 */
		direct[0] = direct[0] / scales[0];
		direct[1] = direct[1] / scales[1];
		direct[2] = direct[2] / scales[2];

		origin[0] = origin[0] / scales[0];
		origin[1] = origin[1] / scales[1];
		origin[2] = origin[2] / scales[2];

		ray1.setDirectVector(direct);
		ray1.setOriginVector(origin);
		return ray1;
	}

	public float[] getCenter() {
		float c[] = new float[3];
		c[0] = center[0] + translateCoordinats[0];
		c[1] = center[1] + translateCoordinats[1];
		c[2] = center[2] + translateCoordinats[2];
		return c;
	}

	/**
	 * 获取外切球半径
	 */
	public float getSphereRadius() {
		float r = (float) Math.sqrt(scales[0] * scales[0] + scales[1]
				* scales[1] + scales[2] * scales[2]);
		return r;
	}

	/**
	 * 移动到ray指向的对应位置，其中z坐标无变化
	 * 
	 * @param ray
	 */
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
