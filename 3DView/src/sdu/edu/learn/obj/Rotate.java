package sdu.edu.learn.obj;

public class Rotate {
	private float angle;
	private float axis[] = new float[3];

	public Rotate(float axis[], float angle) {
		this.axis[0] = axis[0];
		this.axis[1] = axis[1];
		this.axis[2] = axis[2];
		this.angle = angle;
	}

	public float[] getAxis() {
		float[] a = new float[3];
		a[0] = axis[0];
		a[1] = axis[1];
		a[2] = axis[2];
		return a;
	}

	public float getAngle() {
		return angle;
	}
}
