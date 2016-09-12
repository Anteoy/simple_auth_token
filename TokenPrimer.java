package com.mofang.common.api.token;

/**
 * TokenPrimer
 *
 * 用户token密文时使用的引子字符串
 *
 * @author doob
 * @date 16/9/10
 */
public class TokenPrimer{

    private static final String[] primers = new String[]{
            "test1","test2","test3"
    };

    /**
     * 根据用户tokenmodel创建时间决定使用那个引子
     * @param createTime
     * @return
     */
    public static String getPrimer(long createTime){
        int index = (int)createTime%primers.length;
        return primers[index];
    }

}
