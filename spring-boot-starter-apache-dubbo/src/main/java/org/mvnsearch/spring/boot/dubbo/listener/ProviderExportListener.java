package org.mvnsearch.spring.boot.dubbo.listener;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.listener.ExporterListenerAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * provider export listener
 *
 * @author linux_china
 */
@Activate
public class ProviderExportListener extends ExporterListenerAdapter {
    /**
     * exported interfaces
     */
    public static Set<Class> exportedInterfaces = new HashSet<>();
    /**
     * exported urls
     */
    public static Set<URL> exportedUrl = new HashSet<>();

    public void exported(Exporter<?> exporter) throws RpcException {
        Class<?> anInterface = exporter.getInvoker().getInterface();
        exportedInterfaces.add(anInterface);
        URL url = exporter.getInvoker().getUrl();
        if (!url.getProtocol().equals("injvm")) {
            exportedUrl.add(url);
        }
    }

    public void unexported(Exporter<?> exporter) {
        exportedInterfaces.remove(exporter.getInvoker().getInterface());
        URL url = exporter.getInvoker().getUrl();
        if (!url.getProtocol().equals("injvm")) {
            exportedUrl.remove(url);
        }
    }
}
