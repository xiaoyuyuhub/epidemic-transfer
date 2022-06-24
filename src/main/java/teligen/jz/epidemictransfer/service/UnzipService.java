package teligen.jz.epidemictransfer.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import teligen.jz.epidemictransfer.entity.LogEntity;
import teligen.jz.epidemictransfer.mapper.LogMapper;
import teligen.jz.epidemictransfer.utils.GetJarPathUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class UnzipService {

    //路径相关
    @Value("${epidemictransfer.zip-file-path}")
    String ZIP_FILE_PATH;
    @Value("${epidemictransfer.excel-file-path}")
    String EXCEL_FILE_PATH;
    @Value("${epidemictransfer.excel-file-path-bak}")
    String EXCEL_FILE_PATH_BAK;

    @Autowired
    LogMapper logMapper;

    /**
     * 解压压缩文件的job
     * xuyu 20220621
     */
    public void unzipJob() {

        //获取目录下所有zip文件
        List<File> fileStr = Arrays.asList(FileUtil.ls(GetJarPathUtil.JAR_ROOT_PATH + ZIP_FILE_PATH));

        //解压压缩文件到excel目录1
        fileStr.forEach(file -> {

            try {
                //解压当前压缩文件到当前目录下，以压缩文件名为目录,并去除zip后缀名
                File fileDir = ZipUtil.unzip(file, new File(GetJarPathUtil.JAR_ROOT_PATH + EXCEL_FILE_PATH
                        + StrUtil.removeSuffix(file.getName(), ".zip")));

                //构建process4成功日志
                //构建logContentJson
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("fileName", file.getName());
                jsonObject.append("excelNums", FileUtil.ls(GetJarPathUtil.JAR_ROOT_PATH + EXCEL_FILE_PATH).length);

                logMapper.insert(new LogEntity(4, "200", "解压:" +
                        file.getName() + "成功", null, JSONUtil.toJsonStr(jsonObject)));
            } catch (Exception e) {

                logMapper.insert(new LogEntity(4, "400", e.toString(), "解压:" +
                        file.getName() + "失败"));
            }

        });

        //备份一次excel文件到目录2
        fileStr.forEach(file->{
            //复制已拉下文件到备份文件夹
            FileUtil.copy(GetJarPathUtil.JAR_ROOT_PATH + EXCEL_FILE_PATH
                            +StrUtil.removeSuffix(file.getName(), ".zip")
                    , GetJarPathUtil.JAR_ROOT_PATH + EXCEL_FILE_PATH_BAK
                            +StrUtil.removeSuffix(file.getName(), ".zip"), true);
        });

        //解压成功后删除目录1
        fileStr.forEach(file -> {
            FileUtil.del(GetJarPathUtil.JAR_ROOT_PATH + EXCEL_FILE_PATH+file);
        });

        //本地解析完成日志记录process5
//        logMapper.insert(new LogEntity(5, "200", "本次解压zip文件完成"));

    }


}
