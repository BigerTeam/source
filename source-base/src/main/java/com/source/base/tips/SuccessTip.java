package com.source.base.tips;

/**
 * 返回给前台的成功提示
 */
public class SuccessTip extends Tip{
	
	public SuccessTip(){
		super.code = 200;
		super.message = "操作成功";
	}
}
