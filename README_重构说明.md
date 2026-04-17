# OA办公自动化系统 - 重构说明

## 项目重构概述

已完成传统SSM + JSP项目重构为 **Spring Boot + Vue3 前后端分离架构**

---

## 项目结构

```
seed/
├── oa-backend/          # Spring Boot 后端
│   ├── src/main/java/com/cd/oa/
│   │   ├── entity/      # 实体类（使用Lombok简化）
│   │   ├── mapper/      # MyBatis Mapper接口
│   │   ├── service/     # 业务逻辑层
│   │   │   └── impl/    # 业务实现类
│   │   ├── controller/  # RESTful API控制器
│   │   ├── config/      # 配置类（跨域等）
│   │   ├── common/      # 通用工具（统一返回、全局异常）
│   │   ├── dto/         # 数据传输对象
│   │   └── OaApplication.java
│   ├── src/main/resources/
│   │   ├── mapper/      # MyBatis XML映射文件
│   │   └── application.yml
│   └── pom.xml
│
└── oa-frontend/         # Vue3 前端
    ├── src/
    │   ├── api/         # API接口封装
    │   ├── views/       # 页面组件
    │   ├── router/      # 路由配置
    │   ├── store/       # Pinia状态管理
    │   ├── utils/       # 工具类（axios封装）
    │   ├── components/  # 公共组件
    │   ├── App.vue
    │   └── main.js
    ├── index.html
    ├── vite.config.js
    └── package.json
```

---

## 重构优化点

### 后端优化
1. **架构升级**：SSM → Spring Boot 2.7.12
2. **ORM优化**：优化MyBatis映射，SQL添加排序、条件判断优化
3. **命名规范**：统一驼峰命名，Dao → Mapper
4. **代码简化**：使用Lombok的@Data消除getter/setter冗余
5. **接口规范**：提供标准RESTful API，统一返回结果封装
6. **异常处理**：全局异常捕获，友好错误提示
7. **跨域配置**：支持前后端分离部署的CORS配置
8. **连接池**：集成Druid数据库连接池
9. **事务管理**：@Transactional声明式事务

### 前端优化
1. **架构升级**：JSP → Vue3 + Vite + Element Plus
2. **路由管理**：Vue Router实现SPA单页应用
3. **状态管理**：Pinia管理用户登录状态
4. **请求封装**：Axios拦截器统一处理请求/响应
5. **组件化**：Element Plus提供现代化UI组件
6. **权限控制**：路由守卫实现登录验证
7. **样式统一**：保持原有系统风格的同时提升美观度

---

## 功能模块完整保留

1. ✅ 登录认证 + 自动登录检测
2. ✅ 个人中心（个人信息、修改密码）
3. ✅ 操作日志管理
4. ✅ 部门管理（增删改查）
5. ✅ 员工管理（增删改查、默认密码）
6. ✅ 报销单完整流程：
   - 新建报销单
   - 修改报销单
   - 提交审批
   - 部门经理审核
   - 总经理复审（金额≥10000）
   - 财务打款
   - 打回修改
   - 处理记录追踪

---

## 快速启动指南

### 一、准备工作
1. 安装 JDK 8+
2. 安装 Maven 3.6+ 并配置环境变量
3. 安装 Node.js 16+
4. 安装 MySQL 5.7/8.0
5. 执行 `OA-master/oa.sql` 初始化数据库

### 二、修改数据库配置
编辑 `oa-backend/src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/oa?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: 你的数据库账号
    password: 你的数据库密码
```

### 三、启动后端服务
```bash
cd oa-backend
mvn clean package
java -jar target/oa-backend-1.0.0.jar
```
后端启动后访问：http://localhost:8080

### 四、启动前端服务
```bash
cd oa-frontend
npm install
npm run dev
```
前端启动后访问：http://localhost:3000

---

## 测试账号

| 工号 | 密码 | 姓名 | 职位 | 权限 |
|------|------|------|------|------|
| z1001 | 123456 | 李世民 | 总经理 | 复审大额报销单 |
| y1003 | 123456 | 忽必烈 | 部门经理 | 审核本部门报销 |
| y1004 | 123456 | 朱元璋 | 员工 | 创建报销单 |
| c1002 | 123456 | 赵匡胤 | 财务 | 执行打款操作 |
| x1005 | 123456 | 爱新觉罗.福临 | 部门经理 | 销售部经理 |

---

## 报销单审批流程

```
员工创建 → 提交 → 部门经理审核
                           ↓
              金额<10000   │   金额≥10000
                   ↓       ↓
                  财务      总经理复审
                     ↓
                    打款完成
```
