package sdu.edu.learn.scene;

/**
 * 射线工厂
 * 
 * @author lhy & hzy
 * 
 */
public class RayFactory {
	public static float foxy;

	public static float zNear;
	public static float zFar;

	public static float viewPort[] = new float[4];
	public static float touchPostion[] = new float[2];

	public static boolean pickful = false;

	public static boolean isPickful() {
		return pickful;
	}

	public static void setPickful(boolean pick) {
		RayFactory.pickful = pick;
	}

	public static float getFoxy() {
		return foxy;
	}

	public static void setFoxy(float foxy) {
		RayFactory.foxy = foxy;
	}

	public static float getzNear() {
		return zNear;
	}

	public static void setzNear(float zNear) {
		RayFactory.zNear = zNear;
	}

	public static float getzFar() {
		return zFar;
	}

	public static void setzFar(float zFar) {
		RayFactory.zFar = zFar;
	}

	public static void setViewPort(float vp[]) {
		viewPort[0] = vp[0];
		viewPort[1] = vp[1];
		viewPort[2] = vp[2];
		viewPort[3] = vp[3];
	}

	public static void setTouchPostion(float tp[]) {
		touchPostion[0] = tp[0];
		touchPostion[1] = tp[1];
	}

	/**
	 * 获取一个射线
	 * 
	 * @return
	 */
	public static Ray getRay() {
		Ray ray = new Ray();
		ray.setTouchPostion(touchPostion);
		ray.setViewPort(viewPort, foxy, zNear);
		ray.produce();
		return ray;
	}
}
