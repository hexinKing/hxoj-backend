# 数据库初始化
-- 创建库
create database if not exists hxoj_db;

-- 切换库
use hxoj_db;

-- 帖子
create table if not exists post
(
    id         bigint auto_increment comment 'id'
    primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
    )
    comment '帖子' collate = utf8mb4_unicode_ci;

create index idx_userId
    on post (userId);

-- 帖子收藏
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id'
    primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '帖子收藏';

create index idx_postId
    on post_favour (postId);

create index idx_userId
    on post_favour (userId);

-- 帖子点赞
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id'
    primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '帖子点赞';

create index idx_postId
    on post_thumb (postId);

create index idx_userId
    on post_thumb (userId);

-- 题目
create table if not exists question
(
    id          bigint auto_increment comment 'id'
    primary key,
    title       varchar(512)                       null comment '标题',
    content     text                               null comment '内容',
    tags        varchar(1024)                      null comment '标签列表（json 数组）',
    answer      text                               null comment '题目答案',
    submitNum   int      default 0                 not null comment '题目提交数',
    acceptedNum int      default 0                 not null comment '题目通过数',
    judgecase   text                               null comment '判题用例(json 数组)',
    judgeConfig text                               null comment '判题配置(json 对象)',
    thumbNum    int      default 0                 not null comment '点赞数',
    favourNum   int      default 0                 not null comment '收藏数',
    userId      bigint                             not null comment '创建用户 id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除'
    )
    comment '题目' collate = utf8mb4_unicode_ci;

create index idx_userId
    on question (userId);

-- 题目提交
create table if not exists question_submit
(
    id         bigint auto_increment comment 'id'
    primary key,
    language   varchar(128)                       not null comment '编程语言',
    code       text                               not null comment '用户代码',
    judgeInfo  text                               not null comment '判题信息（JSON对象）',
    status     int      default 0                 not null comment '判题状态(0 -待判题、1 - 判题中、2 -成功、3 -失败)',
    questionId bigint                             not null comment '题目id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
    )
    comment '题目提交' collate = utf8mb4_unicode_ci;

create index idx_questionId
    on question_submit (questionId);

create index idx_userId
    on question_submit (userId);


-- 用户
create table if not exists user
(
    id           bigint auto_increment comment 'id'
    primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    phone        varchar(11)  default '***********'     null comment '电话',
    gender       varchar(10)  default '男'              null comment '性别: 男、女、其他',
    email        varchar(100) default '***@***'         null comment '邮箱',
    userState    varchar(256) default '正常'            null comment '用户状态：正常、封号、注销',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
    )
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user (unionId);


