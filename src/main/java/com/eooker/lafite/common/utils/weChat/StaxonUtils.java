package com.eooker.lafite.common.utils.weChat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.eooker.lafite.modules.sunAPI.until.JsonToXmlUtil;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;

import javax.xml.stream.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;


/**
 * XML to JSON
 * JSON to XML
 * Created by zhanzhenchao on 16/4/14.
 */
public class StaxonUtils {

    public static JSONObject xmltoJson(String xml) {
        StringWriter output = new StringWriter();

        JsonXMLConfig jsonXMLConfig = new JsonXMLConfigBuilder()
                .autoArray(true)
                .autoPrimitive(true)
                .prettyPrint(true)
                .build();

        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(new StringReader(xml));
            XMLEventWriter writer = new JsonXMLOutputFactory(jsonXMLConfig).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JSON.parseObject(output.toString());
    }

    public static String jsontoxml(JSONObject jsonObject) {
        StringWriter output = new StringWriter();
        String json = jsonObject.toJSONString();
//        JsonToXmlUtil jsonToXmlUtil = new JsonToXmlUtil();
//        String xml  = jsonToXmlUtil.jsonToXml(json);
//        System.out.println(xml.substring(39));
//        return xml.substring(39);
        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).build();
        try {
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(new StringReader(json));
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            writer = new PrettyXMLEventWriter(writer);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(output.toString().length()>=38){//remove <?xml version="1.0" encoding="UTF-8"?>
            return output.toString().substring(39);
        }
        return output.toString();
        
    }

}
