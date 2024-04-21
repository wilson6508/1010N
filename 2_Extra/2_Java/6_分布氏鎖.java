// https://www.bilibili.com/video/BV1Mi4y1z7UX/
// 單進程可用: Synchronized ReentrantLock (在JVM中的記憶體標記)
// 分布式鎖 用於多"進程"
// 1.互斥性:
// 2.可重入:
// 3.防死鎖: 掛了必須釋放
// 4.高性能: 分布式鎖會有很多伺服器來取用
// 5.高可用: 不能因為一個分布式鎖掛了 導致全服務掛
// 6.非阻塞: 取鎖失敗 -> 等待或直接返回失敗

// https://www.bilibili.com/video/BV1HV4y1f7Uy/
// Redisson
// 1.互斥性: 同一個時間 只能有一個線程取鎖成功(setnx)
// 2.防死鎖: 設置鎖的過期時間(expire)
// 3.可重入: 同一個線程可以再次取鎖
// 4.高性能: 盡可能鎖短一點時間
// 根據lua腳本取鎖 默認鎖失效的時間是30s watchdog每隔10s檢查 如果還持有鎖就會延長鎖期限