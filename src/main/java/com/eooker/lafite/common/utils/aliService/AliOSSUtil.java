package com.eooker.lafite.common.utils.aliService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.StorageClass;


/**
 * 采用阿里的OSS服务作为对象存储
 * 备注：放置在公共存储空间的object是可以通过长链接进行访问，
 * 		放置在私有空间的object是需要请求STS令牌链接进行访问，有相应的有效时间
 * 工程依赖了1个jar包放在pom.xml
 * 1:aliyun-sdk-oss.jar
 */
public class AliOSSUtil {
	private static final String accessKeyId="LTAItgegSLMtOmNE";
	private static final String accessKeySecret="H8konZ3buhiYhKiZf7yn6oRBeTV4Y2";
	private static final String endpoint="http://oss-cn-shenzhen.aliyuncs.com";
	
	/**
	 * 创建存储空间Bucket
	 * @param bucketName    存储空间（Bucket）
	 * @param type          存储空间的类型（0:私有读写，1:公共读私有写，2:公共读写）
	 **/
	public static boolean createBucket(String bucketName,int type){
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
		if(type==0){
			// 设置Bucket权限为私有读写。
			createBucketRequest.setCannedACL(CannedAccessControlList.Private);
		}else if(type==1){
			// 设置Bucket权限为公共读私有写
			createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
		}else if(type==2){
			// 设置Bucket权限为公共读写。
			createBucketRequest.setCannedACL(CannedAccessControlList.PublicReadWrite);
		}
		// 设置Bucket存储类型为低频访问类型，默认是标准类型。
		createBucketRequest.setStorageClass(StorageClass.IA);
		ossClient.createBucket(createBucketRequest);
		ossClient.shutdown();
		return true;
	}
	/**
	 * 获取目前的存储空间Bucket列表
	 * @return List<Bucket> 
	 **/
	public static List<Bucket> listBuckets(){
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 列举Bucket。
		List<Bucket> buckets = ossClient.listBuckets();
		for (Bucket bucket : buckets) {
		    System.out.println(" - " + bucket.getName());
		}
		// 关闭Client。
		ossClient.shutdown();
		return buckets;
	}
	/**
	 * 是否存在该存储空间Bucket
	 * @param bucketName    存储空间（Bucket）
	 **/
	public static boolean isFoundBucket(String bucketName){
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		boolean exists = ossClient.doesBucketExist(bucketName);
		// 关闭client。
		ossClient.shutdown();
		System.out.println(exists);
		return exists;
	}
	
	public static boolean upload(){
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 上传字符串。
		ossClient.createBucket("hongchuyutest1");
		String content = "Hello OSSAA";
		ossClient.putObject("hongchuyutest1", "text1", new ByteArrayInputStream(content.getBytes()));
		// 关闭Client。
		ossClient.shutdown();
		System.out.println("dfdfd");
		return true;
	}
	/**
	 * 上传文件通过Byte流上传
	 * @param file          文件
	 * @param bucketName    存储空间（Bucket）
	 * @param object        文件/对象（Object）
	 **/
	public static boolean uploadByByte(CommonsMultipartFile file,String bucketName,String object){
		byte[] b = file.getBytes();
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ossClient.putObject(bucketName, object, new ByteArrayInputStream(b));
		// 关闭Client。
		ossClient.shutdown();
		return true;
	}
	/**
	 * 上传文件通过文件流上传
	 * @param file          文件
	 * @param bucketName    存储空间（Bucket）
	 * @param object        文件/对象（Object）
	 **/
	public static boolean uploadByFile(CommonsMultipartFile file,String bucketName,String object){
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 上传文件流。
		InputStream inputStream;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		ossClient.putObject(bucketName, object, inputStream);
		// 关闭Client。
		ossClient.shutdown();
		return true;
	}
	/**
	 * 判断文件是否存在
	 * @param bucketName    存储空间（Bucket）
	 * @param object        文件/对象（Object）
	 **/
	public static boolean isFoundFile(String bucketName,String object){
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// Object是否存在。
		boolean found = ossClient.doesObjectExist(bucketName, object);
		System.out.println(found);
		// 关闭Client。
		ossClient.shutdown();
		return found;
	}
	
	/**
	 * 图像处理成大图，小图
	 * @param bucketName    存储空间（Bucket）
	 * @param object        文件/对象（Object）
	 **/
	public static Map<String, Object> imageDeal(String bucketName,String object){
		//?x-oss-process=image/resize,w_200,h_100/rotate,0/quality,q_10
		String maxPictureURL,originalPictureURL,mixPictureURL;
		String common = "http://"+bucketName+".oss-cn-shenzhen.aliyuncs.com/"+object+"?x-oss-process=image/";
		originalPictureURL = common +"resize,w_500/rotate,0/quality,q_100";
		maxPictureURL = common +"resize,w_500/rotate,0/quality,q_70";
		mixPictureURL = common +"resize,w_500/rotate,0/quality,q_30";
		//http://testimagehhh.oss-cn-shenzhen.aliyuncs.com/9fed550e52c34fb3bfe7b343e265b815.jpg?x-oss-process=image/resize,w_2048/rotate,0
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("originalPictureURL", originalPictureURL);
		map.put("maxPictureURL", maxPictureURL);
		map.put("mixPictureURL", mixPictureURL);
		return map;
	}
	
	/**
	 * 获取临时的路径URL
	 * @param second        有效时间（秒为单位）
	 * @param bucketName    存储空间（Bucket）
	 * @param object        文件/对象（Object）
	 * @param type          存储空间的类型（0:私有读写，1:公共读私有写）
	 * @return String 临时路径
	 **/
	public static String getSTS(String bucketName,String object,int second,int type){
		if(type!=1){
			// 创建OSSClient实例。
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			// 设置URL过期时间为1小时
			Date expiration = new Date(new Date().getTime() + second * 1000);
			// 生成URL。
			URL aliURL = ossClient.generatePresignedUrl(bucketName, object, expiration);
			System.out.println(aliURL.toString());
			// 关闭Client。
			ossClient.shutdown();
			return aliURL.toString();
		}else{
			String common = "http://"+bucketName+".oss-cn-shenzhen.aliyuncs.com/"+object;
			return common;
		}
	}
	
//	public static void main(String[] args) {
//		upload();
//		createBucket("cuckoofile", 1);
//		isFoundBucket("cuckoofile");
//		isFoundFile("easytalkimage", "f8ba3aa2682e4d07b8ff7995d4dde4bb_毕设图1.png");
//		getSTS("fd7ef6fb23f749e79dd54d576a0f9346.jpg",3600);
//		isFound("text1");
//	}
	
}
