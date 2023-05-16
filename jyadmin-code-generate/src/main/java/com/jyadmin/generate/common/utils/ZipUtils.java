package com.jyadmin.generate.common.utils;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-16 21:10 <br>
 * @description: ZipUtils <br>
 */
import java.io.*;
import java.util.zip.*;
import javax.servlet.http.HttpServletResponse;

public class ZipUtils {

    /**
     * 压缩指定目录下的所有文件到指定zip文件，并通过response返回
     * @param sourceDirPath 要压缩的目录路径
     * @param zipFileName   压缩后的zip文件名
     * @param response      HttpServletResponse对象
     */
    public static void zipDirectory(String sourceDirPath, String zipFileName, HttpServletResponse response) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        ZipOutputStream zos = null;
        try {
            // 设置response相关信息
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(zipFileName.getBytes(), "ISO8859-1"));

            // 创建输出流
            OutputStream os = response.getOutputStream();
            zos = new ZipOutputStream(os);

            // 递归压缩目录下的所有文件
            File file = new File(sourceDirPath);
            zipFile(file, "", zos);

            // 完成压缩
            zos.closeEntry();
            zos.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归压缩文件
     * @param file       当前要压缩的文件或者目录
     * @param parentPath 父级路径，用于构建压缩包内部子目录结构
     * @param zos        输出流
     * @throws IOException
     */
    private static void zipFile(File file, String parentPath, ZipOutputStream zos) throws IOException {
        if (file.isDirectory()) {
            // 如果是目录，则递归压缩目录下的文件
            String path = parentPath + file.getName() + "/";
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    zipFile(f, path, zos);
                }
            }
        } else {
            // 如果是文件，则压缩文件
            String path = parentPath + file.getName();
            ZipEntry entry = new ZipEntry(path);
            zos.putNextEntry(entry);

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            bis.close();
            fis.close();
        }
    }
}
