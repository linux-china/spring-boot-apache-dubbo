Dubbo Protocol
==============


## hessian
org.apache.dubbo.rpc.protocol.dubbo.DubboCodec.encodeRequestData
```
protected void encodeRequestData(Channel channel, ObjectOutput out, Object data, String version) throws IOException {
        RpcInvocation inv = (RpcInvocation) data;

        out.writeUTF(version);
        out.writeUTF(inv.getAttachment(Constants.PATH_KEY));
        out.writeUTF(inv.getAttachment(Constants.VERSION_KEY));

        out.writeUTF(inv.getMethodName());
        out.writeUTF(ReflectUtils.getDesc(inv.getParameterTypes()));
        Object[] args = inv.getArguments();
        if (args != null)
            for (int i = 0; i < args.length; i++) {
                out.writeObject(encodeInvocationArgument(channel, inv, i));
            }
        out.writeObject(RpcUtils.getNecessaryAttachments(inv));
    }
```

### Hessian2 string解析

x00-x1f表示0-31位字符串，如短类名，函数名等
x30开头 + (x00-xFF): 0-255
x31开头 + (x00-xFF): 256 + (0-255)
x32开头 + (x00-xFF): 256 * 2 +(0-255)
x33开头 + (x00-xFF): 256 * 3 +(0-255)
```
           # UTF-8 encoded character string split into 64k chunks
string     ::= x52 b1 b0 <utf8-data> string  # non-final chunk
           ::= 'S' b1 b0 <utf8-data>         # string of length
                                             #  0-65535
           ::= [x00-x1f] <utf8-data>         # string of length
                                             #  0-31
           ::= [x30-x34] <utf8-data>         # string of length
                                             #  0-1023
```

# References

* hessian2协议： https://github.com/cytle/blog/blob/master/source/_posts/Hessian-2-0%E5%BA%8F%E5%88%97%E5%8C%96%E5%8D%8F%E8%AE%AE%E8%A7%84%E8%8C%83.md
* https://dubbo.incubator.apache.org/en-us/docs/dev/implementation.html
* http://www.spring4all.com/article/1070

