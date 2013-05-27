package sdu.edu.learn.scene;

import sdu.edu.learn.obj.Sphere;
import sdu.edu.learn.obj.Vertex;

/**
 * ����,��ʾ��ʽΪ��¼���ߵ�Դ���Լ����ߵķ�������
 * 
 * @author lhy
 * 
 */
public class Ray {

	private float foxy;
	private float zNear;
	/**
	 * ���ߵķ�������
	 */
	private float directVector[] = new float[3];
	/**
	 * ���ߵ�Դ��
	 */
	private float origin[] = new float[3];
	private float[] viewPort = new float[4];
	private float[] touchPostion = new float[2];

	public void setViewPort(float[] viewPort, float foxy, float zNear) {
		this.viewPort[0] = viewPort[0];
		this.viewPort[1] = viewPort[1];
		this.viewPort[2] = viewPort[2];
		this.viewPort[3] = viewPort[3];
		this.foxy = foxy;

		this.zNear = zNear;
	}

	public void setTouchPostion(float[] touchPostion) {
		this.touchPostion[0] = touchPostion[0];
		this.touchPostion[1] = touchPostion[1];

	}

	/**
	 * ȷ�����ߵķ�������
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

	/**
	 * ��ָ���������ײ���
	 * 
	 * @param SphereCenter
	 *            ���������
	 * @param SphereRadius
	 *            ����İ뾶
	 * @return
	 */
	public boolean intersectWithSphere(float[] SphereCenter, float SphereRadius) {
		float v1[] = new float[3];
		v1[0] = SphereCenter[0] - origin[0];
		v1[1] = SphereCenter[1] - origin[1];
		v1[2] = SphereCenter[2] - origin[2];
		float distance = v1[0] * v1[0] + v1[1] * v1[1] + v1[2] * v1[2];
		float distance1 = directVector[0] * v1[0] + directVector[1] * v1[1]
				+ directVector[2] * v1[2];
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

	/**
	 * ��ָ��������ɵ������ײ���
	 * 
	 * @param vs
	 *            ��������
	 * @param locations
	 *            ����Щ������ɵ���Ľ���
	 * @return
	 */
	public boolean intersectWithPolygon(Vertex[] vs, float[] locations) {
		float[] v0 = vs[0].getVertex();
		float[] v1 = vs[1].getVertex();
		float[] v2 = vs[2].getVertex();

		float x0 = v0[0];
		float y0 = v0[1];
		float z0 = v0[2];

		float x1 = v1[0];
		float y1 = v1[1];
		float z1 = v1[2];

		float x2 = v2[0];
		float y2 = v2[1];
		float z2 = v2[2];

		float[] vec1 = new float[3];
		vec1[0] = x1 - x0;
		vec1[1] = y1 - y0;
		vec1[2] = z1 - z0;

		float[] vec2 = new float[3];
		vec2[0] = x2 - x0;
		vec2[1] = y2 - y0;
		vec2[2] = z2 - z0;

		/**
		 * �����ķ�����
		 */
		float normal[] = new float[3];
		normal[0] = vec1[1] * vec2[2] - vec1[2] * vec2[1];
		normal[1] = vec1[2] * vec2[0] - vec1[0] * vec2[2];
		normal[2] = vec1[0] * vec2[1] - vec1[1] * vec2[0];
		/**
		 * ���÷����������ߵķ�������������ཻ��
		 */
		float t = ((x0 - origin[0]) * normal[0] + (y0 - origin[1]) * normal[1] + (z0 - origin[2])
				* normal[2])
				/ (directVector[0] * normal[0] + directVector[1] * normal[1] + directVector[2]
						* normal[2]);
		/**
		 * ֱ����ƽ��Ľ���
		 */
		locations[0] = directVector[0] * t + origin[0];
		locations[1] = directVector[1] * t + origin[1];
		locations[2] = directVector[2] * t + origin[2];

		float[] vec3 = new float[3];
		vec3[0] = locations[0] - x0;
		vec3[1] = locations[1] - y0;
		vec3[2] = locations[2] - z0;

		float a = 0, b = 0;
		/**
		 * ����ཻ���Ƿ���ָ������Χ�ɵ��ı����ڣ������ı���Ϊ�����Σ�����������������ߵĶ�Ӧ����Ϊv1��v2��
		 * v1v2��һ����ͬ��Դ�㣬���ཻ����Դ�㹹�ɵ��������Ա�ʾΪv3 = a*v1+b*v2��a��bС�ڵ���1, ���ڵ���0
		 */
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

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public float[] getDirectVector() {
		float d[] = new float[3];
		d[0] = directVector[0];
		d[1] = directVector[1];
		d[2] = directVector[2];
		return d;
	}

	public void setDirectVector(float[] d) {
		this.directVector[0] = d[0];
		this.directVector[1] = d[1];
		this.directVector[2] = d[2];
	}

	/**
	 * ��ȡԴ��
	 * 
	 * @return
	 */
	public float[] getOriginVector() {
		float o[] = new float[3];
		o[0] = origin[0];
		o[1] = origin[1];
		o[2] = origin[2];
		return o;
	}

	public void setOriginVector(float[] o) {
		this.origin[0] = o[0];
		this.origin[1] = o[1];
		this.origin[2] = o[2];
	}
}
