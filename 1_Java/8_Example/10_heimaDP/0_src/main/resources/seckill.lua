local voucherId = ARGV[1]
local userId = ARGV[2]
local orderId = ARGV[3]

local stockKey = 'seckill:stock:' .. voucherId
local orderKey = 'seckill:order:' .. voucherId

-- 判斷庫存不足
if(tonumber(redis.call('get', stockKey)) <= 0) then
    return 1
end
-- 判斷用戶是否已下過單 sismember orderKey userId
if(redis.call('sismember', orderKey, userId) == 1) then
    return 2
end
-- 扣庫存
redis.call('incrby', stockKey, -1)
-- 保存用戶id
redis.call('sadd', orderKey, userId)
-- 發送消息到隊列
redis.call('xadd', 'stream.orders', '*', 'userId', userId, 'voucherId', voucherId, 'id', orderId)
return 0