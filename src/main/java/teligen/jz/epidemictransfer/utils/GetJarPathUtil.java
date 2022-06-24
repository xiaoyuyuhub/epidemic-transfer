package teligen.jz.epidemictransfer.utils;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;

public class GetJarPathUtil {

    static {
        ApplicationHome home = new ApplicationHome(GetJarPathUtil.class);
        File jarFile = home.getSource();
        JAR_ROOT_PATH= jarFile.getParentFile().toString()+"/";
//        JAR_ROOT_PATH= "./";
    }

    public static final String JAR_ROOT_PATH;

}
