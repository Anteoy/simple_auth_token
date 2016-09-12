package com.mofang.common.api.token;

/**
 * TokenModelRepository
 *
 * 隐藏tokenmodel储存的具体实现
 *
 * @author doob
 * @date 16/9/9
 */
public interface TokenModelRepository {

    /**
     * 根据用户名获取tokenmodel
     * @param userId
     * @return
     */
    TokenModel get(String userId);

    /**
     * 保存一个tokenmodel
     *
     * @param tokenModel
     * @return
     */
    boolean save(TokenModel tokenModel);

    /**
     * 删除一个用户tokenmodel
     *
     * @param userId
     * @return
     */
    TokenModel delete(String userId);

    /**
     * 设置token超时时间
     *
     * @param seconds   多少秒
     * @return
     */
    boolean setTokenTimeout(long seconds);

}
