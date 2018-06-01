-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE `qiang_gou` default character set utf8 COLLATE utf8_general_ci;
-- 使用数据库
USE qiang_gou;

-- 只有InnoDB支持事物
-- 创建抢购库存表
CREATE TABLE qiang_gou(
  `qianggou_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '抢购商品id',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '抢购开启时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '抢购结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (qianggou_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET='utf8' COMMENT="抢购库存表";


-- 初始化数据
-- Mysql支持字符串转换时间
INSERT INTO
  qiang_gou(name, number, start_time, end_time)
VALUES
  ('1000元抢购iphone6', 100, '2018-04-30 00:00:00', '2018-05-01 00:00:00'),
  ('500元抢购ipad2', 200, '2018-04-30 00:00:00', '2018-05-01 00:00:00'),
  ('2000元抢购小米8', 300, '2018-04-30 00:00:00', '2018-05-01 00:00:00'),
  ('1元抢购iphoneX', 50, '2018-04-30 00:00:00', '2018-05-01 00:00:00');



-- 创建抢购记录明细表
-- 关于联合主键 同一个用户必须对同一产品做抢购，抢购记录与产品关联
-- 可以做过滤，同一个用户不能对同一个产品重复的抢购
CREATE TABLE success_killed(
  `qianggou_id` BIGINT NOT NULL COMMENT '抢购商品id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标示：-1：无效 0：成功 1：已付款',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (qianggou_id, user_phone), /* 联合主键 */
  KEY idx_create_time(create_time)
)ENGINE = InnoDB CHARSET = utf8 COMMENT = "抢购成功明细表";


-- 连接控制台
mysql -uroot -p