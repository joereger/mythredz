package com.mythredz.htmlui;

import java.util.TreeMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * User: Joe Reger Jr
 * Date: Oct 28, 2007
 * Time: 2:48:34 AM
 */
public class BeanMgr {

    private TreeMap<String, Object> beans = new TreeMap<String, Object>();

    public BeanMgr(){
    }

    public Object get(String beanname){
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (beans!=null && beans.containsKey(beanname)){
            //logger.debug("returning previously initialized bean for beanname="+ beanname);
            return beans.get(beanname);
        } else {
            try{
                //Need to instantiate the bean
                Class clazz = Class.forName("com.mythredz.htmluibeans."+ beanname);
                Constructor constructor = clazz.getDeclaredConstructor(new Class[]{});
                constructor.setAccessible(true);
                Object bean = constructor.newInstance(new Object[]{});
                Method meth = clazz.getMethod("initBean", null);
                Object retobj = meth.invoke(bean, null);
                //Add bean to TreeMap
                beans.put(beanname, bean);
                //Return bean to caller
                return bean;

//                //Determine whether or not it implements HtmlUiBean
//                boolean implementsHtmlUiBean = false;
//                Class[] interfaces = bean.getClass().getInterfaces();
//                for (int i=0; i<interfaces.length; i++) {
//                    Class anInterface=interfaces[i];
//                    logger.debug("anInterface.getName()="+anInterface.getName());
//                    if (anInterface.getName().equals("com.mythredz.htmlui.HtmlUiBean")){
//                        logger.debug("implements com.mythredz.htmlui.HtmlUiBean");
//                        implementsHtmlUiBean = true;
//                    } else {
//                        logger.debug("doesn't implement com.mythredz.htmlui.HtmlUiBean");
//                    }
//                }
//                logger.debug("implementsHtmlUiBean="+implementsHtmlUiBean);
//                //Call the initialization required in HtmlUiBean
//                if (implementsHtmlUiBean){
//                    //List the methods
//                    Method methods[] = bean.getClass().getDeclaredMethods();
//                    for (int i=0; i<methods.length; i++) {
//                        Method method=methods[i];
//                        logger.debug("method.getName()="+method.getName());
//                        if (method.getName().equals("initBean")){
//                            logger.debug("fount initBean!");
//                        }
//                    }
//
//                    Class partypes[] = new Class[3];
//                    partypes[0] = HttpServletRequest.class;
//                    partypes[1] = HttpServletResponse.class;
//                    partypes[2] = UserSession.class;
//                    Method meth = clazz.getMethod("initBean", null);
//                    Object arglist[] = new Object[3];
//                    arglist[0] = PageUtil.getRequest();
//                    arglist[1] = PageUtil.getResponse();
//                    arglist[2] = PageUtil.getUserSession();
//                    Object retobj = meth.invoke(bean, null);
//                }


            } catch (Exception ex){
                logger.error("", ex);
            }
        }
        return null;
    }

    public Object getProp(String beandotprop){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("beandotprop="+beandotprop);
        if (beandotprop!=null && beandotprop.indexOf(".")>-1){
            String[] split = beandotprop.split("\\.");
            if (split!=null && split.length>=2){
                String beanname = split[0];
                String propname = split[1];
                Object bean = get(beanname);
                if (bean!=null){
                    logger.debug("got a bean");
                    //Find the property
                    try{
                        return PropertyUtils.getSimpleProperty(bean, propname);
                    } catch (Exception ex){
                        logger.error("", ex);
                    }
                } else {
                    logger.debug("bean iz null");
                }
            } else {
                if (split==null){
                    logger.debug("split is null");
                } else {
                    logger.debug("split.length="+split.length);
                }
            }
        } else {
            logger.debug("no . found in beandotprop");
        }
        return null;
    }



}
