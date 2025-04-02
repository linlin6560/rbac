# RBAC权限管理系统

## 项目介绍

RBAC（Role-Based Access Control）权限管理系统是一个基于角色的访问控制系统，采用前后端分离架构设计。系统实现了用户、角色、权限的精细化管理，支持动态权限分配、多环境部署等特性，适用于企业级应用的权限管理需求。

## 系统架构

项目采用前后端分离架构：
- 前端：Vue3 + Vite + Element Plus
- 后端：Spring Boot + MyBatis-Plus + Sa-Token

### 目录结构
rbac/
├── rbac-api/             # 后端接口服务
│   ├── document/         # 数据库文档
│   ├── src/              # 源代码
│   └── pom.xml           # Maven配置
└── rbac-vue3/            # 前端项目
├── src/              # 源代码
├── public/           # 静态资源
└── package.json      # 依赖配置


## 核心功能

### 用户管理
- 用户的增删改查
- 用户状态管理
- 密码重置
- 个人信息维护

### 角色管理
- 角色的增删改查
- 角色权限分配
- 角色状态管理

### 权限管理
- 基于RBAC模型的权限控制
- 菜单权限管理
- 按钮权限管理
- 数据权限管理

### 部门管理
- 部门的增删改查
- 部门树形结构管理

### 岗位管理
- 岗位的增删改查
- 岗位状态管理

### 系统监控
- 登录日志记录
- 操作日志记录
- 系统性能监控

## 技术栈

### 前端技术
- Vue 3：渐进式JavaScript框架
- Vite：现代前端构建工具
- Element Plus：基于Vue 3的组件库
- Vuex：状态管理
- Vue Router：路由管理
- Axios：HTTP客户端
- ECharts：数据可视化图表库

### 后端技术
- Spring Boot：应用开发框架
- MyBatis-Plus：ORM框架
- Sa-Token：权限认证框架
- Redis：缓存数据库
- MySQL：关系型数据库
- Druid：数据库连接池
- Kaptcha：验证码生成工具

## 环境支持

### 开发环境
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Redis 5.0+
- Node.js 14+

### 支持的浏览器
- Chrome
- Firefox
- Safari
- Edge

## 快速开始

### 后端部署
1. 导入数据库脚本：`rbac-api/document/rbac-api.sql`
2. 修改配置文件：`application-dev.yml`中的数据库和Redis连接信息
3. 启动后端服务：
```bash
cd rbac-api
mvn spring-boot:run

### 前端部署
1. 安装依赖：
cd rbac-vue3
npm install

2.开发环境运行：
npm run dev

3.生产环境构建：
npm run build:pro

## 多环境配置
系统支持多环境部署：

- 开发环境(dev)
- 测试环境(test)
- 生产环境(pro)
可通过修改对应的配置文件进行环境切换。

## 系统预览
系统内置账号：

- 管理员账号：admin
- 默认密码：123456
登录后可以看到完整的系统功能和界面。

## 安全特性
- 基于Sa-Token的认证授权
- 密码加密存储
- 验证码登录保护
- Redis缓存Token
- 防SQL注入
- XSS防御
## 贡献指南
欢迎提交问题和功能需求，也欢迎提交Pull Request。

## 许可证
MIT License
