package com.mofang.common.api.token;

/**
 * TokenHandler
 *
 * 生产token和校验token
 *
 * @author doob
 * @date 16/9/10
 */
public interface TokenHandler {

    /**
     * 添加一个用户的tokenmodel,并返回给用户token
     * @param tokenModel
     * @return   登录后反馈给用户端的token
     */
    String addToken(TokenModel tokenModel);

    /**
     * 检查token是否有效
     * @param userId  用户唯一标示
     * @param token   请求上传token
     * @return
     */
    boolean checkToken(String userId , String token);

}
