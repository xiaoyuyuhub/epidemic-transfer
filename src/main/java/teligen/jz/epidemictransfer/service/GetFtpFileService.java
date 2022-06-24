package teligen.jz.epidemictransfer.service;


import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teligen.jz.epidemictransfer.entity.LogEntity;
import teligen.jz.epidemictransfer.mapper.LogMapper;
import teligen.jz.epidemictransfer.utils.GetJarPathUtil;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 疫情流转系统主要service
 * xuyu 20220622
 */
@Service
public class GetFtpFileService {

    //ftp相关参数
    @Value("${epidemictransfer.jz-ftp.ip}")
    String IP;
    @Value("${epidemictransfer.jz-ftp.port}")
    Integer PORT;
    @Value("${epidemictransfer.jz-ftp.username}")
    String USERNAME;
    @Value("${epidemictransfer.jz-ftp.password}")
    String PASSWORD;
    @Value("${epidemictransfer.jz-ftp.path}")
    String PATH;

    //路径相关
    @Value("${epidemictransfer.zip-file-path}")
    String ZIP_FILE_PATH;
    @Value("${epidemictransfer.zip-file-path-bak}")
    String ZIP_FILE_PATH_BAK;


    //根目录相对路径
    public static final String ROOT = "./";

    //建立ftp
    public static Ftp ftp = null;

    @Autowired
    LogMapper logMapper;

    /**
     * 处理拉取ftp上压缩文件的job
     * xuyu 20220621
     */
    public void getFtpFileJob() {

        //建立ftp连接
        try {
            ftp = new Ftp(IP, PORT, USERNAME, PASSWORD);
            //切换到压缩文件目录
            ftp.cd(PATH);

            //构建process1成功日志
//            logMapper.insert(new LogEntity(1, "200", "连接ftp成功"));
        } catch (Exception e) {
            //构建process1失败日志
//            logMapper.insert(new LogEntity(1, "400", e.getMessage(), "连接ftp失败"));
        }

        //正则表达式拉取文件到本地目录1
        //遍历文件所有zip文件
        List<String> fileList = ftp.ls("").stream().filter(file -> file.contains(".zip"))
                .collect(Collectors.toList());

        //下载zip文件到本地zip目录1
        fileList.forEach(file -> {
            try {
                ftp.download(file, new File(GetJarPathUtil.JAR_ROOT_PATH + ZIP_FILE_PATH
                +file));
                //process2下载压缩文件成功日志
//                logMapper.insert(new LogEntity(2, "200"
//                        , "下载zip文件:" + file.toString() + "成功")
//                );
            } catch (Exception e) {
                //process2下载压缩文件失败日志
//                logMapper.insert(new LogEntity(2, "400",
//                        e.toString(), "下载zip文件:" + file.toString() + "失败")
//                );
            }
        });

        fileList.forEach(file->{
            //复制已拉下文件到备份文件夹
            FileUtil.copy(GetJarPathUtil.JAR_ROOT_PATH + ZIP_FILE_PATH+file
                    , GetJarPathUtil.JAR_ROOT_PATH + ZIP_FILE_PATH_BAK+file, true);
        });

        //job完成后删除ftp上文件
//        fileList.forEach(ftpFile -> ftp.delFile(ftpFile));

        //本地解析完成日志记录process3
//        logMapper.insert(new LogEntity(3, "200", "本次拉取ftp文件完成"));
    }

    /**
     * 解析excel文件的job
     * xuyu 20220621
     */
    public void analysisExcelJob() {

        //解压压缩文件到excel目录

        //记录解压记录与文件数

    }


}
