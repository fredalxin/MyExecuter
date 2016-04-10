package com.fredal.executer;

public class ClassModifier {

	private static final int CONSTANT_POOL_COUNT_INDEX=8;
	private static final int CONSTANT_Utf8_info=1;
	
	private static final int[] CONSTANT_ITEM_LENGTH={-1,-1,-1,5,5,9,9,3,3,5,5,5,5};
	
	private static final int u1=1;
	private static final int u2=2;
	
	private byte[] classByte;
	
	public ClassModifier(byte[] classByte){
		this.classByte=classByte;
	}
	
	public byte[] modifyUTF8Constant(String oldStr,String newStr){
		int cpc=getConstantPoolCount();
		int offset=CONSTANT_POOL_COUNT_INDEX+u2;
		for(int i=0;i<cpc;i++){
			int tag=ByteUtils.bytes2Int(classByte, offset, u1);
			if(tag==CONSTANT_Utf8_info){
				int len=ByteUtils.bytes2Int(classByte, offset+u1,u2);
				offset+=(u1+u2);
				String str=ByteUtils.byte2String(classByte, offset, len);
				if(str.equalsIgnoreCase(oldStr)){
					byte[] stringbytes = ByteUtils.String2bytes(newStr);
					byte[] strLen = ByteUtils.int2bytes(newStr.length(), u2);
					ByteUtils.bytesReplace(classByte, offset-u2, u2, strLen);
					ByteUtils.bytesReplace(classByte, offset, len, stringbytes);
					return classByte;
				}else{
					offset+=len;
				}
			}else{
				offset+=CONSTANT_ITEM_LENGTH[tag];
			}
		}
		return classByte;
	}
	
	public int getConstantPoolCount(){
		return ByteUtils.bytes2Int(classByte,CONSTANT_POOL_COUNT_INDEX,u2);
	}
}
