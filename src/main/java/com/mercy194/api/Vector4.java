package com.mercy194.api;

public class Vector4 {

	public int x, y, z, w;
	public Vector4(int x, int y, int z, int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	public Vector4(String x, String y, String z, String w) {
		this(Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z),  Integer.valueOf(w));
	}
}
