# OA办公自动化系统（前后端分离版）

本项目是对原有OA系统的重构，采用前后端分离架构：
- **后端**: Spring Boot + MyBatis + MySQL
- **前端**: Vue 2 + Element UI

## 项目结构

```
oa-system/
├── oa-backend/          # Spring Boot后端项目
│   ├── src/main/java/com/oa/backend/
│   │   ├── config/      # 配置类
│   │   ├── controller/  # 控制器层
│   │   ├── dto/         # 数据传输对象
│   │   ├── entity/      # 实体类
│   │   ├── enums/       # 枚举类
│   │   ├── mapper/      # MyBatis Mapper接口
│   │   ├── service/     # 服务层
│   │   └── utils/       # 工具类
│   └── src/main/resources/mapper/  # MyBatis XML映射文件
│
└── oa-frontend/         # Vue前端项目
    ├── src/
    │   ├── api/         # API接口
    │   ├── assets/      # 静态资源
    │   ├── components/  # 组件
    │   ├── router/      # 路由配置
    │   ├── store/       # Vuex状态管理
    │   ├── utils/       # 工具函数
    │   └── views/       # 页面视图
    └── public/

```

## 功能模块

1. **登录认证** - 用户登录、登出、修改密码
2. **员工管理** - 员工的增删改查
3. **部门管理** - 部门的增删改查
4. **报销单管理** - 报销单的创建、提交、审核、打款流程
5. **操作日志** - 记录用户操作

## 数据库表结构

- employee: 员工表
- department: 部门表
- claim_voucher: 报销单表
- claim_voucher_item: 报销单明细表
- deal_record: 处理记录表
- log: 操作日志表

## 运行项目

### 后端
```bash
cd oa-backend
mvn spring-boot:run
```

### 前端
```bash
cd oa-frontend
npm install
npm run serve
```

## 默认账号

- 员工: y1004 / 123456
- 部门经理: y1003 / 123456
- 总经理: z1001 / 123456
- 财务: c1002 / 123456
