package com.sohu.mp.datacache.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

import org.apache.log4j.Logger;

public class SerializeUtil {

	private static final Logger log = Logger.getLogger(SerializeUtil.class);
	
	public static byte[] serialize(Object o) {
		if (o == null) {
			throw new NullPointerException("Can't serialize null");
		}
		byte[] rv = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		try {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(o);
			rv = bos.toByteArray();
		} catch (IOException e) {
			throw new IllegalArgumentException("Non-serializable object", e);
		} finally{
			if(os != null){
				try{
					os.close();
				}catch(IOException ie){
				}
			}
			if(bos != null){
				try{
					bos.close();
				}catch(IOException ie){
				}
			}
		}
		return rv;
	}
 
    public static Object deserialize(byte[] in) {
		Object rv = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if (in != null) {
				bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis) {
                    @Override
                    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                        try {
                            //When class is not found,try to load it from context class loader.
                            return super.resolveClass(desc);
                        } catch (ClassNotFoundException e) {
                            return Thread.currentThread().getContextClassLoader().loadClass(desc.getName());
                        }
                    }
                };
                rv = is.readObject();
			}
		} catch (IOException e) {
			log.error("Caught IOException decoding " + in.length
					+ " bytes of data", e);
		} catch (ClassNotFoundException e) {
			log.error("Caught CNFE decoding " + in.length + " bytes of data", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// ignore
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return rv;
	}
}
