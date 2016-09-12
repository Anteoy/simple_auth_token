package com.mofang.common.api.token;

import com.mofang.common.api.signature.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * MemoryTokenModelRepository
 *
 * tokenmodel储存在内存中的的具体实现
 *
 * @author doob
 * @date 16/9/9
 */
public class MemoryTokenModelRepository implements TokenModelRepository {

    private static Logger logger = LoggerFactory.getLogger(TokenModelRepository.class);

    private static volatile long timeout = 10*60; //token有限期
    private static final long checkTime = 60;

    private transient static final ConcurrentHashMap<String,TokenModel> tokenRepository = new ConcurrentHashMap<>();

    static {
        /**
         * 定期检查token,实现token过期时间
         */
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate( () -> { //lambda
            logger.debug("start scan user token , token repository length is {}",tokenRepository.size());
            Iterator<Map.Entry<String,TokenModel>> iterator = tokenRepository.entrySet().iterator();
            while (iterator.hasNext()){
                long now = Timestamp.nowSeconds();
                Map.Entry<String,TokenModel> token = iterator.next();
                long tokenLastTime = token.getValue().getLastTime();
                if (now - tokenLastTime > timeout){
                    String k = token.getKey();
                    TokenModel v = token.getValue();
                    tokenRepository.remove(k);
                    logger.debug("user[{}] token is timeout , user token lasttime is {}s , total user {}s",
                            k, v.getLastTime(), v.getLastTime() - v.getCreateTime());
                }
            }
        }, checkTime, checkTime, TimeUnit.SECONDS);
    }

    /**
     * 单列模式
     */
    private MemoryTokenModelRepository() {
    }
    private static volatile MemoryTokenModelRepository memoryTokenModelRepository;
    public static MemoryTokenModelRepository getMemoryTokenModelRepository(){
        if (memoryTokenModelRepository == null) {
            synchronized (MemoryTokenModelRepository.class) {
                if (memoryTokenModelRepository == null) {
                    memoryTokenModelRepository = new MemoryTokenModelRepository();
                }
            }
        }
        return memoryTokenModelRepository;
    }



    @Override
    public TokenModel get(String userId) {
        return tokenRepository.get(userId);
    }

    @Override
    public boolean save(TokenModel tokenModel) {
        tokenRepository.put(tokenModel.getUserId(),tokenModel);
        return true;
    }

    @Override
    public TokenModel delete(String userId) {
        return this.tokenRepository.remove(userId);
    }

    @Override
    public boolean setTokenTimeout(long seconds) {
        this.timeout = seconds;
        return false;
    }
}
