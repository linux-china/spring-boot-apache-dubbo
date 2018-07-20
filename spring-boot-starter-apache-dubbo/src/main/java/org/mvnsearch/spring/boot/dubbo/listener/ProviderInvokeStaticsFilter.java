package org.mvnsearch.spring.boot.dubbo.listener;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * provider invoke statics filter
 *
 * @author linux_china
 */
@Activate(group = Constants.PROVIDER)
public class ProviderInvokeStaticsFilter implements Filter {
    private MeterRegistry metrics = Metrics.globalRegistry;

    @SuppressWarnings("Duplicates")
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String key = invoker.getInterface().getCanonicalName() + "." + invocation.getMethodName();
        metrics.counter(key).increment();
        return invoker.invoke(invocation);
    }
}
