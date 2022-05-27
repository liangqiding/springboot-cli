package com.springboot.cli;

import com.springboot.cli.async.AsyncTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DomeApplicationTests {

    @Autowired
    AsyncTask asyncTask;

    @Test
    void test() {
        for (int i = 0; i < 10; i++) {
            asyncTask.run();
        }
    }
}
