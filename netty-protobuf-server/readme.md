## 简介

附件doc中已包含相应的文件及文档，也可以自行到官网下载最新版
[参考官网中文文档](https://lixiangyun.gitbook.io/protobuf3/)

#### 1. 下载解压文件

[官网下载地址](https://github.com/protocolbuffers/protobuf/releases/)

#### 2. 编写proto文件

`message.proto`

   ```go
   // 声明使用proto3
   syntax = "proto3";
   // 包名
   option java_package = "com.netty.client.procotol";
   // 类名
   option java_outer_classname = "MessageBuf";
    
   // 消息整合，便于netty导入解码器
   message Message {
     // 包类型
     PackType packType = 1;
     // 根据包类型多选1
     oneof Pack{
         LoginRequest loginRequest = 2;
   	  LoginResponse loginResponse = 3;
   	  MessageRequest messageRequest = 4;
   	  MessageResponse messageResponse = 5;
     }
     // 包类型枚举
     enum PackType {
        LOGIN_REQ = 0;
   	 LOGIN_RESP = 1;
   	 MESSAGE_REQ = 2;
   	 MESSAGE_RESP = 3;
     }
   }
    
   // 登录请求,包含用户名
   message LoginRequest {
     string username = 1;
     string password = 2;
   }
   
   // 登录响应
   message LoginResponse {
     int32 code = 1;
     string message = 2;
   }
    
   // 消息请求
   message MessageRequest {
     int32 messageId = 1;
     int32 type = 2;
     string data = 3;
   }
   
   // 消息响应
   message MessageResponse {
     int32 messageId = 1;
     int32 code = 2;
     string message = 3;
   }
   ```

#### 3 命令行执行

   ```shell
   # --java_out 输出路径、message.proto 要执行的文件
   protoc.exe --java_out=E:\lqd\protoc-3.20.1-rc-1-win64\bin message.proto
   ```

#### 4 netty引入协议文件

在 `ChannelInit.java` 中添加解码器

```java
.addLast(new ProtobufDecoder(MessageBuf.Message.getDefaultInstance()))
```

