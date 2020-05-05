package org.mvnsearch.spring.boot.dubbo.listener;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * provider invoke statics filter
 *
 * @author linux_china
 */
@Activate(group = "providerInvokeStatics")
public class ProviderInvokeStaticsFilter implements Filter {
    private final MeterRegistry metrics = Metrics.globalRegistry;

    @SuppressWarnings("Duplicates")
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String key = invoker.getInterface().getCanonicalName() + "." + invocation.getMethodName();
        Timer.Sample sample = Timer.start(metrics);
        try {
            return invoker.invoke(invocation);
        } finally {
            sample.stop(metrics.timer(key));
            metrics.counter(key + ".count").increment();
        }
    }
}
