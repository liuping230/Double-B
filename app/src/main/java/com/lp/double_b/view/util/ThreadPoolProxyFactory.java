package com.lp.double_b.view.util;


public class ThreadPoolProxyFactory {
    static ThreadPoolProxy mNormalThreadPoolProxy;
    static ThreadPoolProxy mDownloadThreadPoolProxy;

    /**
     * 创建一个普通线程池的代理对象
     */
    public static ThreadPoolProxy createNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5, 5);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 创建一个下载线程池的代理对象
     */
    public static ThreadPoolProxy createDownloadThreadPoolProxy() {
        if (mDownloadThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mDownloadThreadPoolProxy == null) {
                    mDownloadThreadPoolProxy = new ThreadPoolProxy(3, 3);
                }
            }
        }
        return mDownloadThreadPoolProxy;
    }

}
