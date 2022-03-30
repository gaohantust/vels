package com.cnooc.platform.config;
/**
 * @ClassName WebSocketConfig.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年01月06日 15:35:00
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @program: vels
 * @description: WebSocket配置信息
 * @author: TONG
 * @create: 2021-01-06 15:35
 **/
@Configuration
public class WebSocketConfig {
    /**
     * ServerEndpointExporter 作用
     *
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
