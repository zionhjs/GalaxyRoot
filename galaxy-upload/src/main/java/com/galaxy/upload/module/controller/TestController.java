package com.galaxy.upload.module.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestController {

    public static void main(String[] args) {
        //创建父级目录 用于存放分区（分区中存放多个分类）
        String fatherPath = "D:\\洗漱日化区";
        File fatherFile = new File(fatherPath);
        try {
            if (!fatherFile.exists()) {
                fatherFile.mkdir();
            } else {
                fatherFile.delete();
                fatherFile.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //创建分类list 存放分类
        List<String> itemList = new ArrayList<>();
        itemList.add("洗衣粉");
        itemList.add("肥皂");
        itemList.add("牙膏");

        //创建list 存放图片
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("D:\\targetLogoFile .png"));

        for (String item : itemList) {
            //遍历存储图片地址
            String url = fatherPath + "/" + item + ".zip";
            File zipFile = new File(url);
            // 调用压缩方法
            ZipMultiFileUtil.zipFiles(fileList.stream().toArray(File[]::new), zipFile);
        }

        //将项目名称的文件夹 压缩为zip
        /*String fileDir ="D:\\洗漱日化区";
        ZipMultiFileUtil.fileToZip(fatherPath, fileDir, fatherPath + ".zip");*/
    }


}
