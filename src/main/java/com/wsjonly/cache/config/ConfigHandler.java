package com.wsjonly.cache.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;


@Deprecated
public class ConfigHandler {
	
	/**
	 * ��java bean�־û���XML�����ļ�
	 * @param t instance of bean
	 * @throws JAXBException
	 * @throws FileNotFoundException
     * @return
     * @see JaxbBinder
     * @Deprecated 
     * replaced by <code>JaxbBinder toXML</code>
	 */
    @Deprecated
	@SuppressWarnings("unchecked")
	public static <T> String toXML(T t) throws JAXBException, FileNotFoundException {
		
		JAXBContext jc = JAXBContext.newInstance((Class<T>)t.getClass());
		Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
		
		StringWriter writer = new StringWriter();
		marshaller.marshal(t, writer);
		return writer.toString();
	}
	
	/**
	 * ��XML�����ļ�ע�뵽java bean
	 * @param fileName
	 * @return
	 * @throws JAXBException
     * @see JaxbBinder
     * @Deprecated
     * replaced by <code>JaxbBinder fromXML</code>
	 */
    @Deprecated
	public static <T> T fromXML(Class<T> entityClass, String fileName) throws JAXBException   {
		return fromXML(entityClass, new File(fileName));
	}

    /**
     * @param entityClass
     * @param file
     * @param <T>
     * @return
     * @throws JAXBException
     * @See jaxbBinder
     * @Deprecated
     * replaced by <code>JaxbBinder fromXML</code>
     */
    @Deprecated
	@SuppressWarnings("unchecked")
	public static <T> T fromXML(Class<T> entityClass, File file) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(entityClass);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		
		return (T) unmarshaller.unmarshal(file);
	}


    /**
     * @param entityClass
     * @param stream
     * @param <T>
     * @return
     * @throws JAXBException
     * @see JaxbBinder
     * @Deprecated
     * replaced by <code>JaxbBinder fromXML</code>
     */
    @Deprecated
	@SuppressWarnings("unchecked")
	public static <T> T fromXML(Class<T> entityClass, InputStream stream) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(entityClass);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		
		return (T) unmarshaller.unmarshal(stream);
	}
}
