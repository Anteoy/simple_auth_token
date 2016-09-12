package com.mofang.web.support.spring.api;

import com.mofang.common.api.token.DefaultTokenHandlerImpl;
import com.mofang.common.api.token.MemoryTokenModelRepository;
import com.mofang.common.api.token.TokenHandler;
import com.mofang.common.api.token.TokenModelRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * DefaultTokenHandlerBeans
 *
 * @author doob
 * @date 16/9/11
 */
@Component
public class DefaultTokenHandlerBeans {

    @Bean
    public TokenHandler getTokenHandler(){
        return new DefaultTokenHandlerImpl();
    }

    @Bean
    public TokenModelRepository getTokenModelRepository(){
        return MemoryTokenModelRepository.getMemoryTokenModelRepository();
    }
}
