package com.fredal.executer;

import java.lang.reflect.Method;

public class JavaClassExecuter {

	public static String execute(byte[] classByte){
		HackSystem.clearBuffer();
		ClassModifier cm=new ClassModifier(classByte);
		byte[] modiBytes=cm.modifyUTF8Constant("java/lang/System", "com/fredal/executer/HackSystem");
		HotSwapClassLoader loader=new HotSwapClassLoader();
		Class clazz=loader.loadByte(modiBytes);
		try {
			Method method=clazz.getMethod("mian", new Class[]{String[].class});
			method.invoke(null, new String[]{null});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(HackSystem.out);
		}
		return HackSystem.getBuffeString();
	}
}
