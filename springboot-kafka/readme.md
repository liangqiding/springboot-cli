## 基础配置

- 提供者producer
  `yml`中修改`spring.kafka.bootstrap-servers`为你实际的kafka地址

- 消费者consumer
  `yml`中修改`spring.kafka.bootstrap-servers`为你实际的kafka地址
  `yml`中修改`kafka.topic`为你实际需要监听的topic
  `yml`中修改`kafka.group`为你的消费分组，相同组内的消费者不会重复消费同一条消息，起到负载均衡的作用

## 测试接口

> 基础地址 http://localhost:9999

```go
1. 普通消息
localhost:9999/send?message=hello&topic=test

2. 发送json
```json
Request URL:  http://localhost:9999/send
Request Method: POST
Request Headers:
{
   "Content-Type":"application/json"
}
Request Body:
{
    "topic": "test",
    "message": {
        "data": "hello"
    }
}
```