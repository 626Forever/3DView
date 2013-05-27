package sdu.edu.learn.obj;

import javax.microedition.khronos.opengles.GL10;

import sdu.edu.learn.scene.Ray;

/**
 * »ù´¡½Ó¿Ú
 * 
 * @author lhy
 * 
 */
public interface PolygonObject {

	public void onDraw(GL10 gl);

	public void setVertexf(float vs[]);

	public void setTextures(int[] textures);

	public void setTextureCoordinates(float[] textureCoords);

	public void setPolygonTye(int Type);

	public void translate(float x, float y, float z);

	public void rotateX(float angle);

	public void rotateY(float angle);

	public void rotateZ(float angle);
	
	public void rotate(float x,float y,float z,float angle);

	public void scale(float xs, float ys, float zs);

	public int intersect(Ray ray);

	public float[] getCenter();

	public float getSphereRadius();

}
