package netty.web.scoket.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import netty.web.scoket.server.webSocket.WebSocketServerHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * description : 模拟测试
 *
 * @author : qiDing
 * date: 2021-04-16 11:47
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin
public class TestC {

    @RequestMapping("test/real/position")
    public String position(Integer num) throws InterruptedException {
        if (num == null) {
            num = 10;
        }
        double x = 113.90966940475894;
        double y = 22.966997257581667;
        List<List<Double>> paths = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int setX = new Random().nextInt(10);
            int setY = new Random().nextInt(10);
            x = x + setX * 0.00001;
            y = y + setY * 0.00001;
            ArrayList<Double> doubles = new ArrayList<>();
            doubles.add(x);
            doubles.add(y);
            paths.add(doubles);
        }
        for (int i = 1; i <= paths.size(); i++) {
            double angle = 0;
            try {
                angle = getAngle(paths.get(i - 1), paths.get(i));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            JSONObject resp = new JSONObject();
            resp.put("reqType", 3);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("position", paths.get(i - 1));
            jsonObject.put("angle", angle);
            resp.put("data", jsonObject);
            WebSocketServerHandler.channels.writeAndFlush(new TextWebSocketFrame(resp.toJSONString()));
            Thread.sleep(500);
        }
        return "success";
    }


    /**
     * 计算转角
     */
    private static double getAngle(List<Double> p1, List<Double> p2) {
        double dx = p2.get(0) - p1.get(0);
        double dy = p2.get(1) - p1.get(1);
        return Math.toDegrees(Math.atan2(dy, dx));
    }

    @RequestMapping("test/task/com")
    public String taskCompleter() throws InterruptedException {
        String json = "{\n" +
                "    \"vehicleWorkStatus\":5,\n" +
                "    \"gpsStatus\":0,\n" +
                "    \"imuStatus\":0,\n" +
                "    \"lineSpeed\":1.5,\n" +
                "    \"angularSpeed\":\"0.5\",\n" +
                "    \"voltage\":\"10\",\n" +
                "    \"chassisStatus\":\"0\",\n" +
                "    \"mode\":\"0\",\n" +
                "    \"faults\":[\n" +
                "        0,\n" +
                "        0,\n" +
                "        0\n" +
                "    ]\n" +
                "}";
        JSONObject data = JSON.parseObject(json);
        JSONObject resp = new JSONObject();
        resp.put("reqType", 4);
        resp.put("data",data);
        Optional.ofNullable(WebSocketServerHandler.channels).ifPresentOrElse(channel ->
                        channel.writeAndFlush(new TextWebSocketFrame(resp.toJSONString())),
                () -> log.error("没有在线的socket客户端"));
        return "success";
    }
}
