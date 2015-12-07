package com.mdo.domainobject;

public enum ApplicationType {
	
	G(1),
	P(2),
	C(3),
	A(4);
	
	private ApplicationType(int val){
		this.val=val;
	}
	int val;
	
	public int getVal(){
		return val;
	}
}
