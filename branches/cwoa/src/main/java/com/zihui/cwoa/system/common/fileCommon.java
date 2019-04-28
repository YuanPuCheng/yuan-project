package com.zihui.cwoa.system.common;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传，下载 公共类
 */

public class fileCommon {

    public static final String PATH = "C:/upload/";  //默认路径

    public static final String USER_IMG_PATH="C:/upload/user/img/";//用户头像路径

    public static final String USER_IMG_TYPE = "TX";//类型 用于头像

    public static final String ENCLOSYRE_PATH="C:/upload/activiti/";//流程附件路径

    public static final String ENCLOSYRE_TYPE = "AC";//流程类型

    public static final String INVOICE_PATH="C:/upload/invoice/";//发票附件路径

    public static final String INVOICE_TYPE = "FA";//发票类型

    public static final String CONTRACT_PATH = "C:/upload/contract/";//合同附件类型

    public static final String CONTRACT_TYPE = "HT";//发票类型






    //文件上传
    public static String upload(MultipartFile file) {
        // 如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();//文件名
            String suffixName = filename.substring(filename.lastIndexOf("."));  // 后缀名
            String refilename = UUID.randomUUID() + suffixName; // 新文件名
            File filepath = new File(USER_IMG_PATH, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            // 将上传文件保存到一个目标文件当中

            try {
                file.transferTo(new File(USER_IMG_PATH + File.separator + refilename));
            } catch (IOException e) {

                e.printStackTrace();
            }
            return filename;
        }
        return null;
    }



}
