package com.mofang.common.api.token;

import com.mofang.common.Encryptor;

/**
 * DefaultTokenHandlerImpl
 *
 * @author doob
 * @date 16/9/10
 */
public class DefaultTokenHandlerImpl implements TokenHandler {

    static TokenModelRepository tokenModelRepository = MemoryTokenModelRepository.getMemoryTokenModelRepository();

    public DefaultTokenHandlerImpl(){}

    public DefaultTokenHandlerImpl(TokenModelRepository tmr){
        this.tokenModelRepository = tmr;
    }

    @Override
    public String addToken(TokenModel tokenModel) {
        if (tokenModelRepository.get(tokenModel.getUserId()) == null){
            tokenModelRepository.save(tokenModel);
        }
        return createTokenString(tokenModel);
    }

    @Override
    public boolean checkToken(String userId, String token) {
        if (userId == null ) return false;
        TokenModel tokenModel = tokenModelRepository.get(userId);
        boolean result = tokenModel == null ? false : createTokenString(tokenModel).equals(token);
        if (result == true){
            tokenModel.updateLastTime();
        }
        return result;
    }

    /**
     * 生成密文token的逻辑
     * @param tokenModel
     * @return
     */
    private String createTokenString(TokenModel tokenModel){
        return tokenModel == null ? null : Encryptor.input(tokenModel.getUserId()
                + TokenPrimer.getPrimer(tokenModel.getCreateTime())
                + tokenModel.getCreateTime()).sha256().hex();
    }


}
