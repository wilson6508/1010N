// https://www.bilibili.com/video/BV1uv4y1t7HS/
// MQ消息堆積 (消息對列: 生產速率 > 消費速率)
// 事前壓測 -> 生產速率:2000條/s 消費速率:500條/s 要開4台消費者
// 上限狀況 -> 生產速率:4000條/s K8S進行消費者擴容
// 事後檢視 -> 1.批量消費 2.跳過非重要消息 3.降低查詢db的次數