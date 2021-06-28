<template>
  <div class="app-container">
    <el-container>
      <el-header>
        <h1 class="line">webSocket控制面板</h1>
      </el-header>
      <!-- 地址输入框 -->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="ws地址">
          <el-input
            v-model="wsUrl"
            placeholder="ws://127.0.0.1:5001/"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="connect" size="mini"
            >连接</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="disconnect" size="mini"
            >断开</el-button
          >
        </el-form-item>
        <el-form-item>
          <div class="tags">
            状态 ：
            <span style="color: green" v-if="status == '连接成功'">{{
              status
            }}</span>
            <span style="color: red" v-else>{{ status }}</span>
          </div>
        </el-form-item>
      </el-form>
      <!-- 连接状态显示 -->
      <el-form label-position="left" label-width="80px">
          <el-switch
          v-model="accept_lint"
          active-color="#13ce66"
          inactive-color="#909399"
          inactive-text="json格式检查"
        >
        </el-switch>
        <!-- 发送内容json框 -->
        <el-divider content-position="left">发送区</el-divider>
        <b-code-editor
          ref="editor-input"
          v-model="form.input"
          :indent-unit="4"
          height="auto"
          :lint="input_lint"
        />
        <el-link type="primary" @click="$refs['editor-input'].formatCode()"
          >格式化</el-link
        >
      </el-form>
      <br />
      <el-form label-position="left" label-width="80px" :model="form">
        <el-button type="primary" plain @click="sendMsg">发送</el-button>
      </el-form>
      <br />
      <!-- 接收内容json框 -->
      <el-divider content-position="left">接收区</el-divider>
      <el-form label-position="left" label-width="80px">
        <el-tabs> </el-tabs>
        <b-code-editor
          ref="editor-accept"
          v-model="jsonStr"
          :indent-unit="4"
          height="auto"
          :lint="accept_lint"
        />
        <el-link type="primary" @click="$refs['editor-accept'].formatCode()"
          >格式化</el-link
        >
      </el-form>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      wsUrl: process.env.VUE_APP_WEBSOCKET_API,
      form: {
        input: "{}",
      },
      jsonStr: "{}",
      status: "连接关闭",
      accept_lint: true,
      input_lint: true,
    };
  },
  computed: {
    // 重连接监听
    reconnectSum: function () {
      return this.$webSocket.state.reconnectSum;
    },
    statusStore: function () {
      return this.$webSocket.state.status;
    },
  },
  watch: {
    reconnectSum: function (value) {
      console.log("重连了------------------------", value);
      this.websocketMsgInit();
    },
    statusStore: function (value) {
      this.status = value;
    },
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.websocketInit();
    },
    websocketInit() {
      this.$webSocket.dispatch("WEBSOCKET_INIT", this.wsUrl);
    },
    websocketMsgInit() {
      this.$webSocket.state.ws.onmessage = (e) => {
        const obj = JSON.parse(e.data);
        console.log("##### 页面收到 #####", obj);
        this.jsonStr = JSON.stringify(obj);
      };
    },
    /* 消息发送 */
    sendMsg() {
      this.$webSocket.dispatch("WEBSOCKET_SEND", this.form.input);
    },
    /* 连接 */
    connect() {
      this.websocketInit();
    },
    /* 断开连接 */
    disconnect() {
      this.$webSocket.dispatch("WEBSOCKET_CLOSE");
    },
  },
};
</script>

<style scoped>
.line {
  text-align: center;
}
.tags {
  padding-left: 20px;
}
.demo-form-inline {
  margin-top: 30px;
}
</style>

