package sdu.edu.learn.scene;

import sdu.edu.learn.obj.Sphere;

public class Rotation {
	private float previousX;
	private float previousY;
	private float presentX;
	private float presentY;
	private float zNear;
	private float foxy;
	private float viewPort[] = new float[4];

	public void setViewPort(float[] viewPort, float zNear, float foxy) {
		this.viewPort[0] = viewPort[0];
		this.viewPort[1] = viewPort[1];
		this.viewPort[2] = viewPort[2];
		this.viewPort[3] = viewPort[3];
		this.zNear = zNear;
		this.foxy = foxy;
	}

	public void setPrevious(float x, float y) {
		previousX = x;
		previousY = y;
	}

	public void setPresent(float x, float y) {
		previousX = presentX;
		previousY = presentY;
		presentX = x;
		presentY = y;
	}

	/**
	 * 前三个值是旋转轴，最后一个是旋转角
	 * 
	 * @return
	 */
	public float[] getRotationAxisAndAngle() {
		float axis[] = new float[4];
		float halfOfFoxy = foxy / 360 * Sphere.pi;
		float proportion = 2 * ((float) Math.tan(halfOfFoxy)) * zNear
				/ (viewPort[3] - viewPort[1]);
		axis[0] = proportion * (presentX - previousX);
		axis[1] = proportion * (previousY - presentY);
		axis[2] = 0;

		float axisLength = (presentX - previousX) * (presentX - previousX)
				+ (previousY - presentY) * (previousY - presentY);
		axisLength = (float) Math.sqrt(axisLength);
		axis[3] = axisLength / (viewPort[3] - viewPort[1]) * 360;

		float x = axis[0];
		float y = axis[1];
		if (Math.abs(x) > Math.abs(y)) {
			axis[0] = 0;
			axis[1] = 1;
			if (x < 0)
				axis[3] = -axis[3];
		} else {
			axis[0] = 1;
			axis[1] = 0;
			if (y < 0)
				axis[3] = -axis[3];
		}

		return axis;
	}

	public void clear(float x, float y) {
		previousX = x;
		previousY = y;
		presentX = x;
		presentY = y;
	}
}
