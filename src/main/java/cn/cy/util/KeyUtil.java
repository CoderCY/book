package cn.cy.util;

import java.util.UUID;

/**
 * Created by 30721 on 2019/7/14.
 * @author cy
 */
public class KeyUtil {

    /**
     * 获取小写id
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }
}
