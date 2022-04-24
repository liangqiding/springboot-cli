## 定时任务封装
本例中已对定时器做了封装，具体请查看接口`ScheduleService.java`及`ScheduleRunner.java`,这样做的好处就是
- 统一执行定时任务
- 代码整洁好管理
- 统一线程池管理，减轻我们系统的开销

## 使用
在我们需要使用定时任务时，只要重写`ScheduleService.java`接口就可以啦
- 如每秒执行使用示例
```java
@Component
@Slf4j
public class Test1TaskImpl implements ScheduleService {

    @Override
    public void everySecond() {
        log.info("（每秒）定时任务执行了");
    }

}
```
- 当我们需要添加更多自定义定时任务时（如：每分钟）
只需要在`ScheduleService.java`中添加方法

```java
 /**
 * 每分钟
 */
default void everyMinute(){}

```
然后在`ScheduleRunner.java`中添加执行逻辑
```java
 /**
 * 每分钟
 */
@Scheduled(cron = "0 */1 * * * ?")
public void everyMinute() {
     scheduleServiceList.forEach(ScheduleService::everyMinute);
}
```
最后我们编写实现类`Test2TaskImpl.java`
```java
@Component
@Slf4j
public class Test2TaskImpl implements ScheduleService {

    @Override
    public void everyMinute() {
        log.info("（每分钟）定时任务执行了");
    }
    
}
```
- 更多示例请看源码