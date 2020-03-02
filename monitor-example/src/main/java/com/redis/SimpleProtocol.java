package com.redis;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class SimpleProtocol {

    public static void main(String[] args) throws Exception{
        Socket socket = new Socket();
        //TIME_WAIT状态下可以复用端口
        socket.setReuseAddress(true);
        //空闲时发送数据包，确认服务端状态
        socket.setKeepAlive(true);
        //关闭Nagle算法，尽快发送
        socket.setTcpNoDelay(true);
        //调用close方法立即关闭socket，丢弃所有未发送的数据包
        socket.setSoLinger(true, 0);
        //连接server
        socket.connect(new InetSocketAddress("localhost", 6379), 3000);
        //设置读取时超时时间
        socket.setSoTimeout(3000);

        OutputStream os = socket.getOutputStream();
        /**
         * SET 命令
         * 协议: array 3个元素 SET simpleKey simpleValue
         */
        os.write(getBytes("*3\\r\\n$3\\r\\nSET\\r\\n$9\\r\\nsimpleKey\\r\\n$11\\r\\nsimpleValue\\r\\n"));
        os.flush();

        InputStream is = socket.getInputStream();

        /**
         * 解析SET命令的返回结果
         */
        String result = analysisResult(is);

        System.out.println("SET command response : " + result);
        System.out.println();

        /**
         * GET 命令
         * 协议: array 2个元素 GET simpleKey
         */
        os.write(getBytes("*2\\r\\n$3\\r\\nGET\\r\\n$9\\r\\nsimpleKey\\r\\n"));
        os.flush();

        /**
         * 解析GET命令返回结果
         */
        String value = analysisResult(is);
        System.out.println("GET command response : " + value);

        is.close();
        os.close();
        socket.close();
    }

    /**
     * 解析返回结果
     * @param is
     * @return
     * @throws Exception
     */
    private static String analysisResult(InputStream is) throws Exception{
        /**
         * 第一个字节指定返回的数据结构类型
         */
        byte type = (byte)is.read();

        System.out.println("response type is : " + (char)type);

        if(type == '+'){
            //Simple String类型
            return readCRLF(is);
        }else if(type == '$'){
            //Bulk String类型
            int len = readIntCRLF(is);
            System.out.println("$ value len : " + len);
            return readFixedLen(is, len);
        }

        return null;
    }

    /**
     * 读取int值,直到遇到CRLF
     * @param is
     * @return
     * @throws Exception
     */
    private static int readIntCRLF(InputStream is) throws Exception{
        return Integer.parseInt(readCRLF(is));
    }

    /**
     * 读取字符串,直到遇到CRLF
     * @param is
     * @return
     * @throws Exception
     */
    private static String readCRLF(InputStream is) throws Exception{
        byte b = (byte)is.read();
        StringBuilder sb = new StringBuilder();
        //不是最后一个输入字节时
        while(b != -1){
            //判断是否是CR,如果不是加入sb中
            if(b != '\r'){
                sb.append((char)b);
            }else{
                //如果是CR,继续读取一个字节,如果不是LF,报错
                byte oneMore = (byte)is.read();
                if(oneMore != '\n'){
                    throw new RuntimeException("CRLF error!");
                }else{
                    break;
                }
            }
            b = (byte)is.read();
        }
        return sb.toString();
    }

    /**
     * 读取固定字节长度的字符串
     * @param is
     * @param len
     * @return
     * @throws Exception
     */
    private static String readFixedLen(InputStream is, int len) throws Exception{
        byte[] bytes = new byte[len];
        for(int i = 0; i < len; i++){
            bytes[i] = (byte)is.read();
        }
        //CR
        is.read();
        //LF
        is.read();
        return new String(bytes, "UTF-8");
    }

    private static byte[] getBytes(String str) throws Exception{
        return str.getBytes(Charset.forName("UTF-8"));
    }

}