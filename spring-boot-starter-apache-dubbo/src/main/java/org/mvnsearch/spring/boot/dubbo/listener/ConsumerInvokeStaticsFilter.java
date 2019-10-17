package org.mvnsearch.spring.boot.dubbo.listener;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * consumer invoke statics filter
 *
 * @author linux_china
 */
@Activate(group = "consumerInvokeStatics", order = -110000)
public class ConsumerInvokeStaticsFilter implements Filter {
    private MeterRegistry metrics = Metrics.globalRegistry;

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String key = invoker.getInterface().getCanonicalName() + "." + invocation.getMethodName();
        metrics.counter(key).increment();
        return invoker.invoke(invocation);
    }
}
