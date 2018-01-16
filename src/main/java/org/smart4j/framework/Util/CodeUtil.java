package org.smart4j.framework.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.commons.codec.digest. DigestUtils;

public final  class CodeUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(CodeUtil.class);

    public static String encodeURL(String source){
        String target;
        try{
            target= URLEncoder.encode(source,"UTF-8");

        }catch (Exception e){
            LOGGER.error("encode url failure",e);
        throw new RuntimeException(e);
      }
      return target;
    }

    public static String decodeURL(String source){
        String target;
        try{
            target= URLDecoder.decode(source,"UTF-8");
        }catch(Exception e){
            LOGGER.error("decode url failure",e);
            throw new RuntimeException(e);
        }
        return target;
    }

   public static String md5(String source){
        return DigestUtils.md5Hex(source);
   }

   public static void main(String[] args){
       System.out.println(DigestUtils.md5Hex("12"));
   }
}
