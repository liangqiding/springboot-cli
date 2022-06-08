## 使用示例
- 进入html目录
- 在文件中双击`index.html`选择浏览器打开
- 账号密码 admin 123456

## API

- 登录

```json
method: post
url:   /login
# 请求参数
# params（可选）
"params":
{
  "username":"admin",
  "password":"123456"
}
# body（可选）
"form-data":
{
  "username":"admin",
  "password":"123456"  
 }
```

- 获取用户信息

```json
method: get
url:   /info
# 请求头
headers:{
    "token":"xxxxx"
}
```

- 退出登录

```json
method: get
url:   /logout
# 请求头
headers:{
    "token":"xxxxx"
}
```



- 测试接口

```json
method: test
url:   /info
# 请求头
headers:{
    "token":"xxxxx"
}
```



