# 基于netty的websoket客户端

## 简介

- netty是jboss提供的一个java开源框架，netty提供异步的、事件驱动的网络应用程序框架和工具，用以快速开发高性能、高可用性的网络服务器和客户端程序。
- 本项目是基于Netty搭建的基础项目模板。

## 环境

| #    | 环境 | 版本           | 说明            |
| ---- | ---- | -------------- | --------------- |
| 1    | JDK  | openJdk 11.0.8 | 建议JDK11及以上 |

## 项目结构

```
 ├──channel          管道
 ├──config           服务配置
 ├──handler          消息处理器
 ├──server           服务配置
 ├──store            频道存储
 ├──NettyClientApplication.java   主启动类
```