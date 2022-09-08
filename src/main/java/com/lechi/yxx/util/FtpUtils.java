package com.lechi.yxx.util;


import com.lechi.yxx.config.FtpConfig;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

//import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
//import com.ruoyi.common.exception.file.InvalidExtensionException;
//import com.ruoyi.common.utils.StringUtils;
//import com.ruoyi.common.utils.file.MimeTypeUtils;
//import com.ruoyi.common.utils.uuid.IdUtils;


@Component
public class FtpUtils {

    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;
    /** 此处静态方法不能用注入的方式初始化 否则为null 因为@Atuowire在初始化此对象之后执行 而调用静态方法不会初始化对象*/
    static FtpConfig ftpConfig;

    @Autowired
    FtpConfig ftp;

    @PostConstruct
    public void init(){
        ftpConfig=this.ftp;
    }

    /**
     * @param baseDir 上传的路径 为相对路径
     * @param  file 要上传到ftp服务器的文件
     */
    public static String upLoad(String baseDir, MultipartFile file) throws Exception {
        FTPClient ftp = new FTPClient();
        try {
            /** 1. 检查文件大小和扩展名是否符合要求*/
            //assertFile(file);
            /** 2. 产生新的文件名，目的使得文件名统一为英文字符加数字;fileName包含文件后缀名*/
            String fileName=reBuildFileName(file);
            /** 3. 连接ftp服务器*/
            ftp.connect(ftpConfig.getIp(),ftpConfig.getPort());
            ftp.login(ftpConfig.getUsername(),ftpConfig.getPassword());
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                // 不合法时断开连接
                ftp.disconnect();
                throw  new IOException("ftp连接异常，异常码为:"+ftp.getReplyCode());
            }
            String path=ftpConfig.getUploadPath()+baseDir;
            String[] dirs=path.split("/");
            ftp.changeWorkingDirectory("/");
            /** 4. 判断ftp服务器目录是否存在  不存在则创建*/
            /*for(int i=0; i<dirs.length && dirs != null;i++){
                if(! ftp.changeWorkingDirectory(dirs[i])){
                    if(ftp.makeDirectory(dirs[i])){
                        if(! ftp.changeWorkingDirectory(dirs[i])) {
                            throw new Exception("打开文件夹" + dirs[i] + "失败");
                        }
                    }else{
                        throw  new Exception("创建文件夹"+dirs[i]+"失败");
                    }
                }
            }*/
            /** 5.切换ftp文件操作目录*/
            ftp.changeWorkingDirectory(path);
            /** 6.上传文件*/
            // 设置文件类型，二进制
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 设置缓冲区大小
            ftp.setBufferSize(3072);
            // 上传文件
            ftp.storeFile(fileName, file.getInputStream());
            // 登出服务器
            ftp.logout();
            return baseDir+"/"+fileName;
        }catch (Exception e){
            throw  new Exception(e.getMessage(), e);
        }finally {
            /** 关闭*/
            // 判断连接是否存在
            if (ftp.isConnected()) {
                // 断开连接
                ftp.disconnect();
            }
        }

    }

    /**
     * @description: 从ftp服务器上下载文件到本地
     * @param baseDir ftp服务器文件路径 为相对路径 使用数据库中的url路径（ftp存在共享文件夹）
     * @param fileName 文件名
     * @param localDir web服务器本地存储路径 为相对路径 使用数据库中的url路径
     * */
   /* public static boolean downLoad(String localDir,String baseDir,String fileName){
        boolean result = false;
        String localPath=ftpConfig.getDownPath()+localDir;
        String ftpPath=ftpConfig.getUploadPath()+baseDir;
        FTPClient ftp = new FTPClient();
        OutputStream os = null;
        try {
            // 连接至服务器，端口默认为21时，可直接通过URL连接
            ftp.connect(ftpConfig.getIp(), ftpConfig.getPort());
            // 登录服务器
            ftp.login(ftpConfig.getUsername(), ftpConfig.getPassword());
            // 判断返回码是否合法
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                // 不合法时断开连接
                ftp.disconnect();
                // 结束程序
                return result;
            }
            // 设置文件操作目录
            ftp.changeWorkingDirectory(ftpPath);
            // 设置文件类型，二进制
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 设置缓冲区大小
            ftp.setBufferSize(3072);
            // 设置字符编码
            ftp.setControlEncoding("UTF-8");
            // 构造本地文件夹
            File localFilePath = new File(localPath);
            if (!localFilePath.exists()) {
                localFilePath.mkdirs();
            }
            // 构造本地文件对象
            File localFile = new File(localPath + "/" + fileName);
            // 获取文件操作目录下所有文件名称
            String[] remoteNames = ftp.listNames();
            // 循环比对文件名称，判断是否含有当前要下载的文件名
            for (String remoteName : remoteNames) {
                if (fileName.equals(remoteName)) {
                    result = true;
                }
            }
            // 文件名称比对成功时，进入下载流程
            if (result) {
                // 构造文件输出流
                os = new FileOutputStream(localFile);
                // 下载文件 写入到输出流中
                result = ftp.retrieveFile(fileName, os);
                // 关闭输出流
                os.close();
            }
            // 登出服务器
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 判断输出流是否存在
                if (null != os) {
                    // 关闭输出流
                    os.close();
                }
                // 判断连接是否存在
                if (ftp.isConnected()) {
                    // 断开连接
                    ftp.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }*/
    /**
     * @desecription： 删除web服务器本地文件
     * @param realFile: web服务器本地文件
     * */
    public static boolean  delrealFile(String realFile){
        File file =new File(realFile);
        if( file.exists()&&file.isFile()){
            if(file.delete()){
                //System.out.println("删除成功");
                return true;
            }else {
                System.out.println("删除失败");
                return  false;
            }
        }else {
            System.out.println("删除"+realFile+"文件不存在或者不是一个文件类型");
            return  false;
        }
    }
    /**
     * @Description: 判断文件大小是否超过50M 以及判断文件扩展名是否是image类型
     * */
    /*public static void  assertFile(MultipartFile file)throws FileSizeLimitExceededException,InvalidExtensionException{
        long size=file.getSize();
        if(size>DEFAULT_MAX_SIZE){
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE/1024/1024);
        }
        String fileName=file.getOriginalFilename();
        String extension=getExtension(file);
        if(! isAllowedExtension(extension)){
            throw new InvalidExtensionException.InvalidImageExtensionException(MimeTypeUtils.IMAGE_EXTENSION, extension,
                    fileName);
        }
    }*/

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) throws Exception
    {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (StringUtils.isEmpty(extension))
            {
                extension = extension.substring(extension.lastIndexOf("."));
            }
            return extension;
        }catch (Exception e){
            return "jpg";
        }
    }


    /**
     * 编码文件名
     */
    public static final String reBuildFileName(MultipartFile file) throws Exception
    {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        fileName =  UUID.randomUUID().toString() + "." + extension;
        return fileName;
    }

    /**
     * 判断MIME类型是否是允许的Image类型
     *
     * @param extension
     * @return
     */
    /*public static final boolean isAllowedExtension(String extension)
    {
        for (String str : MimeTypeUtils.IMAGE_EXTENSION)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }*/



}
