package sdu.edu.learn.scene;

import sdu.edu.learn.obj.Sphere;
import sdu.edu.learn.obj.Vertex;

/**
 * 射线方程 aX+bY+cZ = d
 * 
 * @author lhy
 * 
 */
public class Ray {

	private float foxy;
	private float zNear;
	private float zFar;

	private float directVector[] = new float[3];
	private float origin[] = new float[3];
	private float[] viewPort = new float[4];
	private float[] touchPostion = new float[2];

	public void setViewPort(float[] viewPort, float foxy, float zNear,
			float zFar) {
		this.viewPort[0] = viewPort[0];
		this.viewPort[1] = viewPort[1];
		this.viewPort[2] = viewPort[2];
		this.viewPort[3] = viewPort[3];
		this.foxy = foxy;
		this.zFar = zFar;
		this.zNear = zNear;
	}

	public void setTouchPostion(float[] touchPostion) {
		this.touchPostion[0] = touchPostion[0];
		this.touchPostion[1] = touchPostion[1];

	}

	/**
	 * 确定射线的方向向量
	 */
	public void produce() {
		float halfOfFoxy = foxy / 360 * Sphere.pi;
		float proportion = 2 * ((float) Math.tan(halfOfFoxy)) * zNear
				/ (viewPort[3] - viewPort[1]);
		directVector[0] = proportion
				* (touchPostion[0] - (viewPort[2] + viewPort[0]) / 2);
		directVector[1] = proportion
				* ((viewPort[1] + viewPort[3]) / 2 - touchPostion[1]);
		directVector[2] = -zNear;
		origin[0] = 0;
		origin[1] = 0;
		origin[2] = 0;
	}

	public boolean intersectWithSphere(float[] SphereCenter, float SphereRadius) {
		float distance = SphereCenter[0] * SphereCenter[0] + SphereCenter[1]
				* SphereCenter[1] + SphereCenter[2] * SphereCenter[2];
		float distance1 = directVector[0] * SphereCenter[0] + directVector[1]
				* SphereCenter[1] + directVector[2] * SphereCenter[2];
		distance = distance
				- distance1
				* distance1
				/ (directVector[0] * directVector[0] + directVector[1]
						* directVector[1] + directVector[2] * directVector[2]);
		if (distance >= SphereRadius * SphereRadius) {
			return false;
		} else {
			return true;
		}

	}

	public boolean intersectWithPolygon(Vertex[] vs, float[] locations) {
		float[] v0 = vs[0].getVertex();
		float[] v1 = vs[1].getVertex();
		float[] v2 = vs[2].getVertex();
		float[] v3 = vs[3].getVertex();

		float x0 = v0[0];
		float y0 = v0[1];
		float z0 = v0[2];

		float x1 = v1[0];
		float y1 = v1[1];
		float z1 = v1[2];

		float x2 = v2[0];
		float y2 = v2[1];
		float z2 = v2[2];

		float x3 = v3[0];
		float y3 = v3[1];
		float z3 = v3[2];

		float[] vec1 = new float[3];
		vec1[0] = x1 - x0;
		vec1[1] = y1 - y0;
		vec1[2] = z1 - z0;

		float[] vec2 = new float[3];
		vec2[0] = x2 - x0;
		vec2[1] = y2 - y0;
		vec2[2] = z2 - z0;

		float normal[] = new float[3];
		normal[0] = vec1[1] * vec2[2] - vec1[2] * vec2[1];
		normal[1] = vec1[2] * vec2[0] - vec1[0] * vec2[2];
		normal[2] = vec1[0] * vec2[1] - vec1[1] * vec2[0];

		float t = ((x0 - origin[0]) * normal[0] + (y0 - origin[1]) * normal[1] + (z0 - origin[2])
				* normal[2])
				/ (directVector[0] * normal[0] + directVector[1] * normal[1] + directVector[2]
						* normal[2]);
		/**
		 * 直线与平面的交点
		 */
		locations[0] = directVector[0] * t + origin[0];
		locations[1] = directVector[1] * t + origin[1];
		locations[2] = directVector[2] * t + origin[2];

		// System.out.println(locations[0] + "   " + locations[1] + "    "
		// + locations[2]);

		float[] vec3 = new float[3];
		vec3[0] = locations[0] - x0;
		vec3[1] = locations[1] - y0;
		vec3[2] = locations[2] - z0;

		float a = 0, b = 0;

		if (vec3[2] == 0) {
			a = (vec2[1] * vec3[0] - vec2[0] * vec3[1])
					/ (vec1[0] * vec2[1] - vec2[0] * vec1[1]);

			b = (vec1[1] * vec3[0] - vec1[0] * vec3[1])
					/ (vec1[1] * vec2[0] - vec2[1] * vec1[0]);
		}
		if (vec3[0] == 0) {
			a = (vec2[1] * vec3[2] - vec2[2] * vec3[1])
					/ (vec1[2] * vec2[1] - vec2[2] * vec1[1]);

			b = (vec1[1] * vec3[2] - vec1[2] * vec3[1])
					/ (vec1[1] * vec2[2] - vec2[1] * vec1[2]);
		}
		if (vec3[1] == 0) {
			a = (vec2[0] * vec3[2] - vec2[2] * vec3[0])
					/ (vec1[2] * vec2[0] - vec2[2] * vec1[0]);

			b = (vec1[0] * vec3[2] - vec1[2] * vec3[0])
					/ (vec1[0] * vec2[2] - vec2[0] * vec1[2]);
		}
		if (a > 0 && a < 1 && b > 0 && b < 1) {
			return true;
		} else {
			locations = null;
			return false;
		}
	}

	public float[] getDirectVector() {
		return this.directVector;
	}

	public void setDirectVector(float[] d) {
		this.directVector = d;
	}

	public float[] getOriginVector() {
		return this.origin;
	}

	public void setOriginVector(float[] o) {
		this.origin = o;
	}
}
