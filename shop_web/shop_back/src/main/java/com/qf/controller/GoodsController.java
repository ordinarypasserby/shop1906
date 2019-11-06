package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.entity.GoodsSeckill;
import com.qf.feign.GoodsFeign;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author DingYuHui
 * @Date 2019/10/10
 */
@Controller
@RequestMapping("/goodsManager")
public class GoodsController {

    @Autowired
    private GoodsFeign goodsFeign;

    //图片上传的地址
    private String uploadPath = "E:\\imgs";

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping("/list")
    public String goodsList(Model model){
        List<Goods> goods = goodsFeign.goodsList();
        model.addAttribute("goodsList",goods);
        return "goodslist";
    }


    /**
     * 图片上传
     * @param file
     * @return
     */
    @RequestMapping("/uploader")
    @ResponseBody
    public String imgUploader(MultipartFile file){
        /*File outFile = new File(uploadPath);
        //判断上传路径是否存在，不存在则创建
        if(!outFile.exists()){
            boolean mkdirs = outFile.mkdirs();
            //判断创建是否成功，失败则抛出异常
            if(!mkdirs){
                throw new RuntimeException("上传路径为空，且无法创建！");
            }
        }

        //处理文件名称
        String fileName = UUID.randomUUID().toString();
        outFile = new File(outFile,fileName);*/

        //开始上传
        String uploadPath = null;
        try {
            //获得文件后缀名
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            System.out.println("后缀名为："+ suffix);

            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    suffix,
                    null
            );
            uploadPath = storePath.getFullPath();
        }catch (Exception e){
            e.printStackTrace();
        }

        /*try (
                InputStream in = file.getInputStream();
                OutputStream out = new FileOutputStream(outFile);
             ){
            //文件的上传
            IOUtils.copy(in,out);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        uploadPath = "http://49.234.21.180:8080/" + uploadPath;
        System.out.println("上传后的路径：" + uploadPath);

        return "{\"filename\":\"" + uploadPath + "\"}";
    }

    /**
     * 上传图片回显
     * @param filename
     * @param response
     */
    @RequestMapping("/showImg")
    public void showImages(String filename, HttpServletResponse response){
        File imageFile = new File(uploadPath + "\\" + filename);
        try (
                InputStream in = new FileInputStream(imageFile);
                OutputStream out = response.getOutputStream();
             ){
            IOUtils.copy(in,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/insert")
    public String goodsInsert(Goods goods, GoodsSeckill goodsSeckill){
        goods.setGoodsSeckill(goodsSeckill);
        Boolean flag = goodsFeign.goodsInsert(goods);
        return flag ?"redirect:http://localhost:16666/back/goodsManager/list" : "error";
    }
}