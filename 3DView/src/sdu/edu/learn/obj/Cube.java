package sdu.edu.learn.obj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube implements PolygonObject {

	private int polygon_Type;
	private int textures[];

	private float translateCoordinats[] = new float[3];
	private float rotateAngles[] = new float[3];
	private float scales[] = new float[3];

	private FloatBuffer textCoordinats;
	private FloatBuffer vertexs;

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
			rotateAngles[i] = 0;
			scales[i] = 1;
		}
	}

	@Override
	public void onDraw(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glPushMatrix();
		gl.glTranslatef(translateCoordinats[0], translateCoordinats[1],
				translateCoordinats[2]);
		gl.glRotatef(rotateAngles[0], 1, 0, 0);
		gl.glRotatef(rotateAngles[1], 0, 1, 0);
		gl.glRotatef(rotateAngles[2], 0, 0, 1);
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
		ByteBuffer vbb = ByteBuffer.allocateDirect(vs.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexs = vbb.asFloatBuffer();
		vertexs.put(vs);
		vertexs.position(0);
	}

	@Override
	public void setTextureCoordinates(float[] textureCoords) {
		// TODO Auto-generated method stub

		// float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer byteBuf = ByteBuffer
				.allocateDirect(textureCoords.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textCoordinats = byteBuf.asFloatBuffer();
		textCoordinats.put(textureCoords);
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
		translateCoordinats[0] += x;
		translateCoordinats[1] += y;
		translateCoordinats[2] += z;
	}

	@Override
	public void scale(float xs, float ys, float zs) {
		// TODO Auto-generated method stub
		scales[0] *= xs;
		scales[1] *= ys;
		scales[2] *= zs;
	}

	@Override
	public void rotateX(float angle) {
		// TODO Auto-generated method stub
		rotateAngles[0] += angle;
	}

	@Override
	public void rotateY(float angle) {
		// TODO Auto-generated method stub
		rotateAngles[1] += angle;
	}

	@Override
	public void rotateZ(float angle) {
		// TODO Auto-generated method stub
		rotateAngles[2] += angle;
	}

}
