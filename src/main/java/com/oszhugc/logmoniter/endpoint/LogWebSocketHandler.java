package com.oszhugc.logmoniter.endpoint;

import com.oszhugc.logmoniter.worker.TailLogThread;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 通过webSocket和浏览器实现长连接
 *
 * @author oszhugc
 * @Date 2019\4\25 0025 21:36
 **/
@Component
@ServerEndpoint("/log")
public class LogWebSocketHandler {

    private Process process;
    private InputStream inputStream;

    /**
     * 处理webSocket连接请求
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        try {
            //执行tail -f 命令
            //process = Runtime.getRuntime().exec("tail -f /var/log/syslog");
            //process = Runtime.getRuntime().exec("type C:\\Users\\Administrator\\Desktop\\logmoniter\\pom.xml");
            //inputStream = process.getInputStream();
            File file = new File("C:\\Users\\Administrator\\Desktop\\logmoniter\\pom.xml");
            inputStream = new FileInputStream(file);
            //启动新的线程,防止InputStream阻塞处理webSocket的线程
            new TailLogThread(inputStream,session).start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        try {
            if(inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(process != null)
            process.destroy();
    }

    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }

}
