package com.mythredz.cache.providers;

import com.mythredz.cache.providers.jboss.JbossTreeCacheAOPProvider;
import com.mythredz.cache.providers.oscache.OsCacheProvider;
import com.mythredz.cache.providers.oscache.OsCacheClusteredProvider;

/**
 * Factory class to get a cache provider
 */
public class CacheFactory {

    public static CacheProvider getCacheProvider(){
        //return getCacheProvider("OsCacheProvider");
        return getCacheProvider("JbossTreeCacheAOPProvider");
    }

    public static CacheProvider getCacheProvider(String providername){
        if (providername.equals("JbossTreeCacheAOPProvider")){
            return new JbossTreeCacheAOPProvider();
        } else if (providername.equals("OsCacheProvider")){
            return new OsCacheProvider();
        } else if (providername.equals("OsCacheClusteredProvider")){
            return new OsCacheClusteredProvider();
        } else {
            return getCacheProvider();
        }
    }

}
