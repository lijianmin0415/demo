//package com.example.demo.Controller;
//
//
//import com.example.demo.bean.Reduction;
//import com.example.demo.bean.entity.SysUpload;
//import com.example.demo.bean.enums.StatusEnum;
//import com.example.demo.service.UploadService;
//import com.example.demo.utils.SQApiResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @Description
// * @Author sgl
// * @Date 2018-05-15 14:04
// */
//@RestController
//@Slf4j
//public class UploadController {
//    private static String hqType = "宏桥";
//    private static String ydType = "缘达";
//    @Value("${UPLOAD_PATH}")
//    private String uploadPath;
//    @Value("${IP}")
//    private String ip;
//    @Value("${PORT}")
//    private String port;
//    private String username = "sa";
//    @Value("${PASSWORD}")
//    private String password;
//
//    @Autowired
//    private UploadService uploadService;
//
//
//
//    @GetMapping("/upload")
//    public String upload() {
//        return "upload";
//    }
//
//    @PostMapping("/upload")
//    public SQApiResponse<String> upload(@RequestParam("type") String type, @RequestParam("file") MultipartFile file) {
//        //判断文件是否为空
//        if (file.isEmpty()) {
//            return SQApiResponse.error("上传失败，请选择文件");
//        }
//        //判断库里是否有正在执行的
//        List<SysUpload> sysUploads = uploadService.queryAll();
//        if (!CollectionUtils.isEmpty(sysUploads)) {
//            List<String> status = sysUploads.stream().filter(t -> t.getStatus() != null).map(t -> t.getStatus()).filter(t -> t.equals(StatusEnum.ING.getStatus())).collect(Collectors.toList());
//            if (!CollectionUtils.isEmpty(status)) {
//                return SQApiResponse.error("正在有还原操作，稍后在进行");
//            }
//        }
//        //判断后缀名
//        if (!file.getOriginalFilename().toLowerCase().endsWith("bak") &&
//                !file.getOriginalFilename().toLowerCase().endsWith("mdf")) {
//            return SQApiResponse.error("文件类型错误");
//        }
//        //判断上传文件大小
//        if (file.getSize() > 1024 * 1024 * 1024) {
//            return SQApiResponse.error("请上传小于1024M的文件");
//        }
//        String fileName = "";
//        String ku = "";
//        String fileSuffix = file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length - 1];
//        if (type.trim().equals(hqType)) {
//            fileName = "联合外经宏桥." + fileSuffix;
//            ku = "联合外经宏桥";
//            System.out.println(fileName);
//        } else {
//            fileName = file.getOriginalFilename();
//            ku = file.getOriginalFilename().split("\\.")[0];
//        }
//
//        System.out.println(uploadPath);
//        //判断文件夹石村存在
//        if (new File(uploadPath).getParentFile().exists()) {
//            new File(uploadPath).mkdirs();
//        }
//        File dest = new File(uploadPath, fileName);
//        try {
//            file.transferTo(dest);
//            log.info("上传成功");
//            Reduction reduction = new Reduction(ip, port, username, password);
//            String flag = uploadService.getReducte(reduction, ku, uploadPath, fileName);
//            if (null == flag) {
//                return SQApiResponse.error("上传失败");
//            }
//            Thread.sleep(1000);
//            dest.delete();
//            //邮件通知服务放到mq队列里面
////            msgProducer.sendMsg("lijianmin0415@163.com");
//            log.info("上传成功哈哈哈");
//            return SQApiResponse.success("上传成功");
//        } catch (IOException e) {
//            log.error(e.toString(), e);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return SQApiResponse.error("上传失败！");
//    }
//
//    @GetMapping("/multiUpload")
//    public String multiUpload() {
//        return "multiUpload";
//    }
//
//    @PostMapping("/multiUpload")
//    @ResponseBody
//    public String multiUpload(HttpServletRequest request) {
//        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
//        String filePath = "/Users/itinypocket/workspace/temp/";
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            if (file.isEmpty()) {
//                return "上传第" + (i++) + "个文件失败";
//            }
//            String fileName = file.getOriginalFilename();
//
//            File dest = new File(filePath + fileName);
//            try {
//                file.transferTo(dest);
//                log.info("第" + (i + 1) + "个文件上传成功");
//            } catch (IOException e) {
//                log.error(e.toString(), e);
//                return "上传第" + (i++) + "个文件失败";
//            }
//        }
//
//        return "上传成功";
//
//    }
//}
