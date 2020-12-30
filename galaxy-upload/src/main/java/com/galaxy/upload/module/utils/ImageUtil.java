package com.galaxy.upload.module.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtil {

    /**
     * 给multipartFile加上图片水印
     * @param multipartFile  需要上传的文件
     * @param markImg  本地水印绝对路径
     * @throws IOException
     */
    public static File uploadImagesNotLogo(MultipartFile multipartFile, String markImg) throws IOException {
        // 获取图片文件名
        String originFileName = multipartFile.getOriginalFilename();
        // 获取原图片后缀
        int lastSplit = originFileName.lastIndexOf(".");
        String suffix = originFileName.substring(lastSplit + 1);
        // 获取图片原始信息
        String dOriginFileName = multipartFile.getOriginalFilename();
        String dContentType = multipartFile.getContentType();
        // 是图片且不是gif才加水印
        if (!suffix.equalsIgnoreCase("gif")) {
            // 获取水印图片
            InputStream inputImg = multipartFile.getInputStream();
            Image img = ImageIO.read(inputImg);
            File file = new File(markImg);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            Image mark = ImageIO.read(bis);
            //加图片水印
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);

            int markWidth = mark.getWidth(null);
            int markHeight = mark.getHeight(null);

            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            //水印的相对位置  ps：这里是右下角  水印宽为底片的四分之一  位置自己可以调整
            markPic(bufImg, img, mark, imgWidth / 64, (imgWidth * markHeight) / (64 * markWidth),
                    imgWidth - imgWidth / 64, imgHeight - (imgWidth * markHeight) / (64 * markWidth));

            File outFile = new File("out_pic.png");
            ImageIO.write(bufImg, "png", outFile);//写图片
            /*ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufImg, suffix, imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());*/
            // 加水印后的文件上传
            return outFile;
        }
        //返回加了水印的上传对象
        return null;
    }

    /**
     * 给multipartFile加上图片水印
     * @param multipartFile  需要上传的文件
     * @param markImg  本地水印绝对路径
     * @throws IOException
     */
    public static File addPicMarkToMutipartFile(MultipartFile multipartFile, String markImg) throws IOException {
        // 获取图片文件名
        String originFileName = multipartFile.getOriginalFilename();
        // 获取原图片后缀
        int lastSplit = originFileName.lastIndexOf(".");
        String suffix = originFileName.substring(lastSplit + 1);
        // 获取图片原始信息
        String dOriginFileName = multipartFile.getOriginalFilename();
        String dContentType = multipartFile.getContentType();
        // 是图片且不是gif才加水印
        if (!suffix.equalsIgnoreCase("gif")) {
            // 获取水印图片
            InputStream inputImg = multipartFile.getInputStream();
            Image img = ImageIO.read(inputImg);
            File file = new File(markImg);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            Image mark = ImageIO.read(bis);
            // 加图片水印
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);

            int markWidth = mark.getWidth(null);
            int markHeight = mark.getHeight(null);

            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            //水印的相对位置  ps：这里是右下角  水印宽为底片的四分之一  位置自己可以调整
            markPic(bufImg, img, mark,
                    imgWidth / 16, (imgWidth * markHeight) / (16 * markWidth),
                    0, imgHeight - (imgWidth * markHeight) / (16 * markWidth));

            File outFile = new File("out_pic.png");
            ImageIO.write(bufImg, "png", outFile);//写图片
            /*ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufImg, suffix, imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());*/
            // 加水印后的文件上传
            return outFile;
        }
        //返回加了水印的上传对象
        return null;
    }

    /*public static MultipartFile addWorkMarkToMutipartFile(MultipartFile multipartFile,
                                                          String text) throws IOException {
        // 获取图片文件名
        String originFileName = multipartFile.getOriginalFilename();
        // 获取原图片后缀
        int lastSplit = originFileName.lastIndexOf(".");
        String suffix = originFileName.substring(lastSplit + 1);
        // 获取图片原始信息
        String dOriginFileName = multipartFile.getOriginalFilename();
        String dContentType = multipartFile.getContentType();
        // 是图片且不是gif才加水印
        if (!suffix.equalsIgnoreCase("gif")) {
            // 获取水印图片
            InputStream inputImg = multipartFile.getInputStream();
            Image img = ImageIO.read(inputImg);
            // 加图片水印
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);

            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight,
                    BufferedImage.TYPE_INT_RGB);
            //设置字体
            Font font = new Font("宋体", Font.PLAIN, 5);
            //调用画文字水印的方法
            markWord(bufImg, img, text, font , Color.RED, 0, imgHeight-50);

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufImg, suffix, imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());
            // 加水印后的文件上传
            multipartFile = new MockMultipartFile(dOriginFileName, dOriginFileName, dContentType,
                    is);
        }
        //返回加了水印的上传对象
        return multipartFile;
    }*/

    //加图片水印
    public static void markPic(BufferedImage bufImg, Image img, Image markImg, int width, int height, int x, int y) {
        //取到画笔
        Graphics2D g = bufImg.createGraphics();
        //画底片
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        //画水印位置
        g.drawImage(markImg, x, y, width, height, null);
        g.dispose();
    }

    //加文字水印
    public static void markWord(BufferedImage bufImg, Image img, String text, Font font, Color color, int x, int y) {
        //取到画笔
        Graphics2D g = bufImg.createGraphics();
        //画底片
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        g.setColor(color);
        g.setFont(font);
        //位置
        g.drawString(text, x, y);
        g.dispose();
    }

}
