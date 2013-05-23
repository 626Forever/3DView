package sdu.edu.learn.obj;

import javax.microedition.khronos.opengles.GL10;

import sdu.edu.learn.scene.Ray;

public class Multilateral implements PolygonObject {
	protected boolean picked = false;

	@Override
	public void onDraw(GL10 gl) {
		// TODO Auto-generated method stub

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

	@Override
	public int intersect(Ray ray) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float[] getCenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getSphereRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}

	@Override
	public void setTextures(int[] textures) {
		// TODO Auto-generated method stub

	}

	public void move(Ray ray) {
	}
}
