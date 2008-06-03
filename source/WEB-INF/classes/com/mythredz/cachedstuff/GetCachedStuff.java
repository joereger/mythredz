package com.mythredz.cachedstuff;

import com.mythredz.cache.providers.CacheFactory;
import com.mythredz.htmluibeans.PublicIndexCacheitem;
import com.mythredz.util.DateDiff;

import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 1:57:05 PM
 */
public class GetCachedStuff {

    public static CachedStuff get(CachedStuff cs){
        Logger logger = Logger.getLogger(CachedStuff.class);
        String key = cs.getKey();
        String group = "CachedStuff";
        try{
            Object obj = CacheFactory.getCacheProvider().get(key, group);
            if (obj!=null && (obj instanceof CachedStuff)){
                CachedStuff cachedCs = (CachedStuff)obj;
                int minago = DateDiff.dateDiff("minute", Calendar.getInstance(), cachedCs.refreshedTimestamp());
                if (minago>cs.maxAgeInMinutes()){
                    cs.refresh();
                    CacheFactory.getCacheProvider().put(key, group, cs);
                    return cs;
                } else {
                    return cachedCs;
                }
            } else {
                cs.refresh();
                CacheFactory.getCacheProvider().put(key, group, cs);
                return cs;
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
        return cs;
    }

    public static void refresh(CachedStuff cs){
        Logger logger = Logger.getLogger(CachedStuff.class);
        String key = cs.getKey();
        String group = "CachedStuff";
        try{
            Object obj = CacheFactory.getCacheProvider().get(key, group);
            if (obj!=null && (obj instanceof CachedStuff)){
                CachedStuff cachedCs = (CachedStuff)obj;
                cs.refresh();
                CacheFactory.getCacheProvider().put(key, group, cs);
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

}
