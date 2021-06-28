import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	namespaced: true,
	state: {
		/* 主线程 */
		ws: null,
		/* 消息监听 */
		status: null,
		/* 重连延时 */
		timeout: 1000,
		/* 连接地址 */
		url: null,
		/* 重复连接乐观锁 */
		syncVersion: 0,
		/* 连接计数 */
		reconnectSum: 0
	},
	mutations: {
		// 创建一个this.socketTask对象【发送、接收、关闭socket都由这个对象操作】
		WEBSOCKET_INIT(state, wsUrl) {
			// 先关闭旧的连接
			if (state.ws) {
				state.ws.close();
			}
			// 初始化连接地址
			state.url = process.env.VUE_APP_WEBSOCKET_API;
			if (wsUrl) {
				state.url = wsUrl
			}
			// 初始化状态
			state.status == "连接关闭"
			// 初始化锁,版本不一致，禁止重连，避免重连产生多个连接
			state.syncVersion = state.syncVersion + 1
			const syncVersion = state.syncVersion
			// 主入口socket初始化
			const init = function (syncVersion) {
				// 检查版本
				if (syncVersion != state.syncVersion) {
					return;
				}
				state.ws = new WebSocket(state.url);
				state.ws.onopen = (e) => {
					console.log('########Connection open');
					state.status = "连接成功"
					state.reconnectSum = state.reconnectSum + 1
				};
				state.ws.onclose = (e) => {
					console.log('#########Connection close', e);
					state.status = "连接关闭"
					reconnect(syncVersion);
				};
				state.ws.onmessage = (e) => {
					const obj = JSON.parse(e.data);
					console.log('##### websocket message #####', obj);
				}
				state.ws.onerror = (e) => {
					state.status = "连接错误"
					console.log(e);
				};
			}
			// 重连
			const reconnect = function (syncVersion) {
				console.log('准备重连');
				state.status = "重连中..."
				setTimeout(() => {
					// 重新连接
					console.log("############# 断线重连##################")
					init(syncVersion)
				}, state.timeout);
			}
			// 执行
			init(syncVersion);
		},
		WEBSOCKET_SEND(state, p) {
			console.log("ws发送！");
			state.ws.send({
				data: p,
				async success() {
					console.log("消息发送成功");
				},
				async fail() {
					console.log("消息发送失败");
				}
			});
		},
		WEBSOCKET_CLOSE(state) {
			console.log("ws关闭！");
			// 版本+1，目的：废弃之前版本的重连，否则关闭了依旧会自动重连
			state.syncVersion = state.syncVersion + 1
			// 关闭连接
			state.ws.close();
		},
	},
	actions: {
		WEBSOCKET_INIT({
			commit
		}, wsUrl) {
			commit('WEBSOCKET_INIT', wsUrl)
		},
		WEBSOCKET_SEND({
			commit
		}, p) {
			commit('WEBSOCKET_SEND', p)
		},
		WEBSOCKET_CLOSE({
			commit
		}) {
			commit('WEBSOCKET_CLOSE')
		}
	}
})
