package com.mofang.common.api.token;

import com.mofang.common.Encryptor;
import com.mofang.common.api.signature.Timestamp;

/**
 * TokenModel
 *
 * token实体类,用于储存token相关信息
 *
 * @author doob
 * @date 16/9/9
 */
public class TokenModel {

    private String userId;

    private long createTime;

    private long lastTime;

    public TokenModel(String userId) {
        this.userId = userId;
        this.createTime = Timestamp.nowSeconds();
        this.lastTime = this.createTime;
    }

    public String getToken(){
        String original = userId+TokenPrimer.getPrimer(createTime)+createTime;
        return Encryptor.input(original).md5().hex();
    }

    public TokenModel updateLastTime(){
        this.lastTime = Timestamp.nowSeconds();
        return this;
    }

    public long getLastTime() {
        return lastTime;
    }

    public long getCreateTime() {

        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}


