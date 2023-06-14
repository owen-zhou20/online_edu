package com.sv.oss.service.impl;

import com.sv.oss.service.OssService;
import com.sv.oss.utils.ConstantPropertiesUtils;
import com.sv.servicebase.exceptionhandler.SvException;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    //Upload teacher avatar to Ali Oss
    @Override
    public String uploadFile(MultipartFile file) {
        // file url in Ali OSS for return
        String url = null;
        // In this example, the endpoint of the China (Hangzhou) region is used. Specify the actual endpoint.
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // The AccessKey pair of an Alibaba Cloud account has permissions on all API operations. Using these credentials to perform operations in OSS is a high-risk operation. We recommend that you use a RAM user to call API operations or perform routine O&M. To create a RAM user, log on to the RAM console.
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        // Specify the name of the bucket. Example: examplebucket.
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        // Specify the full path of the object. The path cannot contain the bucket name. Example: exampledir/exampleobject.txt.
        String fileName = file.getOriginalFilename();

        //1. use UUID to add a random string to the head of file name
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        fileName = uuid + fileName;

        //2. put file in the folder named by data
        String datePath = new DateTime().toString("yyyy/MM/dd");
        fileName = datePath + "/" + fileName;

        // Specify the full path of the local file. Example: D:\\localpath\\examplefile.txt.
        // By default, if the path of the local file is not specified, the local file is uploaded from the path of the project to which the sample program belongs.
        //String filePath= "D:\\localpath\\examplefile.txt";

        // Create an OSSClient instance.
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();

            // Create a PutObject request.
            ossClient.putObject(bucketName, fileName, inputStream);
            url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            //return url;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            throw new SvException(20001,"Failed to upload avatar to Ali OSS. "+oe);
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            throw new SvException(20001,"Failed to upload avatar to Ali OSS. "+ce);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SvException(20001,"Failed to upload avatar to Ali OSS. "+e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            return url;
        }
    }
}
