package com.tce.newspider.downloader;

import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.http.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ConnectTimeoutException;

import java.io.*;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDownloader implements Downloader {

    private static Log log = LogFactory.getLog(AbstractDownloader.class);
    /**
     * 正则表达式撇配charaset
     */
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");

    protected String getCharset(String requestCharset, String contentType) {
        //1.contentType的编码
        String charset = getCharsetFromContentType(contentType);
        if (charset == null)//2.request的编码
            charset = requestCharset;
        if (charset == null)//3.默认UTF-8
            charset = "UTF-8";
        return charset;
    }

    /**
     * 取contentType的字符集
     */
    private String getCharsetFromContentType(String contentType) {
        if (contentType == null)
            return null;

        Matcher m = charsetPattern.matcher(contentType);
        if (m.find()) {
            return m.group(1).toUpperCase();//.trim()
        }
        return null;
    }

    /**
     * getContent得到的inputStream转为ByteArrayInputStream，重复使用
     *
     * @param in
     * @return
     * @throws IOException
     */
    protected ByteArrayInputStream toByteInputStream(InputStream in) throws IOException {
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            byte[] buffer = new byte[1024];
            int len;

            while ((len = in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            bais = new ByteArrayInputStream(baos.toByteArray());
        }
        //end of file 主动抛出，提示到达文件尾
        catch (EOFException eof) {
            bais = new ByteArrayInputStream(baos.toByteArray());
            log.warn("inputstream " + in.getClass().getName() + " eof!");
        }
        // 读取 org.apache.http.client.entity.LazyDecompressingInputStream 流时会抛出超时异常
        catch (ConnectTimeoutException | SocketTimeoutException e) {
            throw e;//throws IOException
        }
        catch (IOException e) {
            log.warn("inputstream " + in.getClass().getName() + " don't to byte inputstream!");
            return null;
        }
        finally{
            in.close();
            baos.close();
        }

        return bais;
    }


}
