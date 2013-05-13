package sdu.edu.learn.obj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Polygon implements PolygonObject {
	private int polygon_Type;
	private int vertex_nums;

	private FloatBuffer vertexs;

	public Polygon() {

	}

	public Polygon(float vs[]) {
		// a float is 4 bytes, therefore we multiply the
		// number if vertices with 4.
		vertex_nums = vs.length / 3;
		ByteBuffer vbb = ByteBuffer.allocateDirect(vs.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexs = vbb.asFloatBuffer();
		vertexs.put(vs);
		vertexs.position(0);

	}

	@Override
	public void setVertexf(float[] vs) {
		// TODO Auto-generated method stub
		vertex_nums = vs.length / 3;
		ByteBuffer vbb = ByteBuffer.allocateDirect(vs.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexs = vbb.asFloatBuffer();
		vertexs.put(vs);
		vertexs.position(0);
	}

	@Override
	public void setTextureCoordinates(float[] textureCoords) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraw(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexs);
		gl.glDrawArrays(polygon_Type, 0, vertex_nums);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);

	}

	@Override
	public void setPolygonTye(int Type) {
		// TODO Auto-generated method stub
		polygon_Type = Type;
	}

	@Override
	public void translate(float x, float y, float z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(float xs, float ys, float zs) {
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

}
