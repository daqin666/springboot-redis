-- src/main/resources/scripts/incrementBy.lua
local key = KEYS[1]               -- 获取第一个参数作为键名
local incrementBy = tonumber(ARGV[1])  -- 获取第二个参数作为增量值，并将其转换为数字类型

-- 存储key和incrementBy的值：仅作debug用
-- redis.call("SET", "debug_key", key)
-- redis.call("SET", "debug_incrementBy", incrementBy)

local currentValue = redis.call("GET", key)  -- 通过GET命令获取键的当前值
if not currentValue then
    currentValue = 0  -- 如果键不存在，将当前值初始化为0
else
    currentValue = tonumber(currentValue)  -- 如果键存在，将当前值转换为数字类型
end

local newValue = currentValue + incrementBy  -- 计算新的值
redis.call("SET", key, newValue)  -- 通过SET命令将新值存储到键中

return newValue  -- 返回新值