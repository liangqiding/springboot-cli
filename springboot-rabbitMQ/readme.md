## 简介

概念解释：
- Broker： 消息队列服务器实体
- 生产者（Producer）：发送消息的应用
- 消费者（Consumer）：接收消息的应用
- 队列（Queue）：保存消息并将它们转发给消费者
- 消息（Message）：服务与应用程序之间传送的数据，由消息头和消息体组成。消息体是不透明的，消息头由一些列可选属性组成，这些属性包括：routing-key（路由键）、priority（优先级）、delivery-mode（消息是否可持久性存储）
- 连接（Connection）：连接RabbitMQ和应用服务器的TCP连接
- 通道（Channel）：网络信道，几乎所有的操作都在Channel中进行，Channel是进行消息读写的通道，客户端可以建立对各Channel，每个Channel代表一个会话任务
- 交换机（Exchange）：从生产者那里接收消息，并根据交换类型分发到对应的消息列队里，要实现消息的接收，一个队列必须绑定一个交换机，不具备消息存储的能力
- 绑定（Binding）：绑定是队列和交换机的一个链接。
- 路由键（Routing Key）：路由键是供交换机查看并根据键的值来决定如何分发消息到列队的一个键。
- 用户（Users）：在RabbitMQ里，是可以通过指定的用户名和密码来进行连接的。每个用户可以分配不同的权限，例如读权限，写权限以及在实例里进行配置的权限
- 虚拟主机（Virtual Host）：用于进行逻辑隔离，最上层的消息路由，一个虚拟主机可以有若干个Exchange和Queue，同一个虚拟主机里不能有相同名字的Exchange

工作模式：

- 简单模式(Simple )：P - > Q ->C，单对单。
- 工作队列模式（Work）：多个消费端，消费同一个队列中的消息，采用轮询分发或公平分发
- 轮询分发：服务器的处理能力没有影响，一人一条按均分配
- 公平分发：根据消费者能力进行分发，多劳多得
- 发布订阅 (Fanout )：给绑定得所有Queue发送消息
- 路由模式 (Direct)：发布订阅模式+Routing Key ，给指定路由的key发送消息
- 主题模式(topic) ：模糊匹配路由Key
  – # ：0个或者多个，多级(.xx.xx.xx)，如 #.com 匹配，com、aa.com、aa.bb.com
  – * : 至少>1 满足，必须要有一级(.xx) 如 *.com 匹配，a.com
- 根据参数条件匹配(header模式)：取消routingkey，使用header中的 key/value（键值对）匹配队列
- RPC：消息主动拉取
- publish confirm： 发布确认模式

## 使用示例

- 打开生产模块`producer`
- 找到测试模块下的测试类

路径：`src\test\java\com\springboot\cli\ProducerTest`

- 运行测试方法


