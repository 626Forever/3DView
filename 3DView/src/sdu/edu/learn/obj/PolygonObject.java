package sdu.edu.learn.obj;

import javax.microedition.khronos.opengles.GL10;

public interface PolygonObject {
	public void onDraw(GL10 gl);

	public void setVertexf(float vs[]);

	public void setTextureCoordinates(float[] textureCoords);

	public void setPolygonTye(int Type);

	public void translate(float x, float y, float z);

	public void rotateX(float angle);

	public void rotateY(float angle);

	public void rotateZ(float angle);

	public void scale(float xs, float ys, float zs);

}