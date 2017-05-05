/**
 * author: loserStar
 * date: 2017年5月5日下午5:19:25
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.dataagg.util;

/**
 * author: loserStar
 * date: 2017年5月5日下午5:19:25
 * remarks:本系统的文件生成辅助类
 */
public class FileUtil {
	/**
	 * 生成一个以uuid命名的新文件名，保留原后缀
	 * test.doc   ->    uuid.doc
	 * test    ->    uud
	 * @param sourceFilename
	 * @return
	 */
	public static String generateFileName(String sourceFileName){
//		long time = System.currentTimeMillis();
		String newFileName = IdGen.uuid() + getFileNameSuffix(sourceFileName);// 构成新文件名。
		return newFileName;
	}
	
	/**
	 * 提取文件的文件名，不要后缀
	 * test.doc   ->    test
	 * test    ->    test
	 * @param sourceFileName
	 * @return
	 */
	public static String getFileNameNotSuffix(String sourceFileName){
		String oldFileName = sourceFileName.indexOf(".") != -1 ? sourceFileName.substring(0, sourceFileName.lastIndexOf(".")) : sourceFileName;
		return oldFileName;
	}
	
	/**
	 * 提取文件的后缀，如果没有后缀返回空字符穿
	 * test.doc   ->    .doc
	 * test    ->    
	 * @param sourceFileName
	 * @return
	 */
	public static String getFileNameSuffix(String sourceFileName){
		String suffix = sourceFileName.indexOf(".") != -1 ? sourceFileName.substring(sourceFileName.lastIndexOf("."), sourceFileName.length()) : "";
		return suffix;
	}
}
