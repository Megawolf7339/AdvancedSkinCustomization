package com.mercy194.api;

public class Vector3 {
	
	public int x, y, z;
	public Vector3(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3(String x, String y, String z) {
		this(Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
	}
}
