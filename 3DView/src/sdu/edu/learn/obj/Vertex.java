package sdu.edu.learn.obj;

public class Vertex {
	private float v[] = new float[3];

	public Vertex(float v[]) {
		this.v[0] = v[0];
		this.v[1] = v[1];
		this.v[2] = v[2];
	}

	public void setVertex(float v[]) {
		this.v[0] = v[0];
		this.v[1] = v[1];
		this.v[2] = v[2];
	}

	public float[] getVertex() {
		return v;
	}
}
