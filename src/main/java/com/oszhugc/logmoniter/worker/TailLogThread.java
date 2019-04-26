package com.oszhugc.logmoniter.worker;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 监控log文件的线程
 *
 * @author oszhugc
 * @Date 2019\4\25 0025 21:43
 **/
public class TailLogThread  extends Thread{

    private BufferedReader reader;
    private Session session;

    public TailLogThread(InputStream in,Session session){
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.session = session;
    }


    @Override
    public void run() {
        String line;
        try {
            while ((line = reader.readLine()) != null){
                //将实施日志通过websockect发送给客户端,给每一行添加一个换行
                session.getBasicRemote().sendText(line + "<br/>");
            }
            session.getBasicRemote().sendText( "没有读取到数据!!!");
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
