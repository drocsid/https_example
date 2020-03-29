Example of `Segmentation Fault` using `http4s` with `native-image`. Run `native-image-build-cmd.sh` to build the image. Execute the `native-image` binary ./weatherdemo after creation. Finally run `425.sh` to make a request trigger the application code. The last client request execution gives a segfault.

https://github.com/drocsid/https_example/blob/fc2203815f1f5f03547971048bef929b6634cff0/src/main/scala/com/example/weatherdemo/WeatherdemoRoutes.scala#L75

From the logs:

```
[scala-execution-context-global-11] INFO  c.e.w.WeatherdemoRoutes - request IO(Request(method=POST, uri=https://api.flowroute.com/v2/messages, headers=Headers(Content-Type: application/json, Content-Length: 95))) 
[ioapp-compute-0] DEBUG o.h.c.PoolManager - Requesting connection: curAllocated=1 idleQueues.size=1 waitQueue.size=0 maxWaitQueueLimit=256 closed=false 
[ioapp-compute-0] DEBUG o.h.c.PoolManager - Active connection not found. Creating new one. curAllocated=1 idleQueues.size=1 waitQueue.size=0 maxWaitQueueLimit=256 closed=false

Segmentation Fault
```
