package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.Common;
import com.zihui.cwoa.system.common.DateUtils;
import com.zihui.cwoa.system.common.fileCommon;
import com.zihui.cwoa.system.pojo.sys_file;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.service.sys_fileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/file")
public class fileController {

    private static Logger log = Logger.getLogger(fileController.class);

    @Resource
    private sys_fileService fileService;

    private final ResourceLoader resourceLoader;

    @Autowired
    public fileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }





    /**
     * 文件上传
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public CallbackResult login(@RequestParam("file")MultipartFile file,@RequestParam String filetype, HttpSession session){
        Map map = new HashMap<>();
        CallbackResult result = new CallbackResult();
        String filename = null;
        String path = fileCommon.USER_IMG_PATH;//默认用户路径
        switch (filetype){
            case "FA" : path= fileCommon.INVOICE_PATH ;break;
            case "TX" : path= fileCommon.USER_IMG_PATH ;break;
            case "AC" : path= fileCommon.ENCLOSYRE_PATH ;break;
        };
        log.info(path+"文件类型"+filetype);
        // 如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            sys_file fileobj = new sys_file();

            filename = file.getOriginalFilename();//文件名
            String suffixName = filename.substring(filename.lastIndexOf("."));  // 后缀名
            String refilename = UUID.randomUUID() + suffixName; // 新文件名
            File filepath = new File(path, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            // 将上传文件保存到一个目标文件当中
            fileobj.setFileName(refilename);
            fileobj.setFileRename(filename);
            fileobj.setFileType(filetype);
            fileobj.setFileUrl(path);
            fileobj.setStatus(0);
            fileobj.setTs(DateUtils.getDate());
            sys_user user = (sys_user) session.getAttribute("user");
            //fileobj.setUserId(user.getUserId());
            try {
                file.transferTo(new File(path + File.separator + refilename));
                fileService.insertSelective(fileobj);
            } catch (IOException e) {
                e.printStackTrace();
                result.setResult(400);
                result.setMessage("上传失败");
                return result;
            }
            map.put("fileID",fileobj.getFileId());
            map.put("filename",refilename);
            log.info("新增返回文件主键"+fileobj.getFileId());
        }

        result.setResult(200);
        result.setMessage("文件上传成功");
        result.setMap(map);
        return result;
    }


    /**
     * 文件下载功能
     * @param filename
     * @param response
     * @throws Exception
     */
    @RequestMapping("/down")
    @ResponseBody
    public void downfile(@RequestParam("filename")String filename,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws ServletException, IOException {
        //获得文件名称
        sys_file fileobj = new sys_file();
        fileobj.setFileName(filename);
        List<sys_file> fileList = fileService.selectFileList(fileobj);

        if(fileList.size()!=0){
            sys_file ff = fileList.get(0);
            log.info(ff.toString());
            String refilename = ff.getFileRename();//文件原名称
            //放文件的目录指定文件名
            File file = new File(ff.getFileUrl()+ff.getFileName());

            try {
                //判断是否是IE11
                Boolean flag= request.getHeader("User-Agent").indexOf("like Gecko")>0;
                //IE11 User-Agent字符串:Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko
                //IE6~IE10版本的User-Agent字符串:Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.0; Trident/6.0)
                if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") >0||flag){
                    refilename = URLEncoder.encode(refilename, "UTF-8");//IE浏览器
                }else {
                    //先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,
                    //这个文件名称用于浏览器的下载框中自动显示的文件名
                    refilename = new String(refilename.replaceAll(" ", "").getBytes("UTF-8"), "ISO8859-1");
                    //firefox浏览器
                    //firefox浏览器User-Agent字符串:
                    //Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0
                }
                InputStream fis = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer;
                buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                response.reset();
                response.addHeader("Content-Disposition", "attachment;filename=" +refilename);
                response.addHeader("Content-Length", "" + file.length());
                OutputStream os = response.getOutputStream();
                response.setContentType("application/octet-stream");
                os.write(buffer);// 输出文件
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @RequestMapping(value = "/show")
    public ResponseEntity user(String fileName) throws IOException {
        sys_file fileobj = new sys_file();
        fileobj.setFileName(fileName);
        List<sys_file> fileList = fileService.selectFileList(fileobj);
        sys_file ff = fileList.get(0);
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:///" + ff.getFileUrl() + ff.getFileName()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping("/queryfilenamebyid")
    @ResponseBody
    public List<Map<String,Object>> queryFileNameById(String idArray){
        return fileService.queryFileNameById(idArray);
    }

}
