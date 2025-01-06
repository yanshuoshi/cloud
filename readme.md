# 技术概要
1. 使用gateway配置路由
2. 使用openfeign调用服务
3. 使用nacos作为配置中心
4. 使用rabbitmq作为消息队列
5. 使用security + oauth2 + jwt实现用户认证和权限控制 支持授权码登录和密码模式多端登录
6. 使用sentinel实现流量防护
7. 使用seata 实现分布式事务
8. 使用es + logstash + Kibana + filebeat 实现日志收集

# 服务概要：
1. uaa：授权服务
2. gateway：实现路由转发
3. common：公共模块，包含实体类、工具类等
4. base-service：基础服务，包含用户、角色、菜单等
5. work: 业务服务

# 使用版本
seata-server-1.3.0
nacos-server-2.4.0.1
sentinel-dashboard-1.8.1
logstash-7.6.2
elasticsearch-7.6.2
filebeat-7.6.2-windows-x86_64
kibana-7.6.2-windows-x86_64