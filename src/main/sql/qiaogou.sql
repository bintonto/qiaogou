-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
USE seckill;

-- 只有InnoDB支持事物
-- 创建秒杀库存表
CREATE TABLE seckill(
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '秒杀商品id',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET='utf8' COMMENT="秒杀库存表";


-- 初始化数据
-- Mysql支持字符串转换时间
INSERT INTO
  seckill(name, number, start_time, end_time)
VALUES
  ('1000元秒杀iphone6', 100, '2018-04-30 00:00:00', '2018-05-01 00:00:00'),
  ('500元秒杀ipad2', 200, '2018-04-30 00:00:00', '2018-05-01 00:00:00'),
  ('2000元秒杀小米8', 300, '2018-04-30 00:00:00', '2018-05-01 00:00:00'),
  ('1元秒杀iphoneX', 50, '2018-04-30 00:00:00', '2018-05-01 00:00:00');



-- 创建秒杀记录明细表
-- 关于联合主键 同一个用户必须对同一产品做秒杀，秒杀记录与产品关联
-- 可以做过滤，同一个用户不能对同一个产品重复的秒杀
CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标示：-1：无效 0：成功 1：已付款',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id, user_phone), /* 联合主键 */
  KEY idx_create_time(create_time)
)ENGINE = InnoDB CHARSET = utf8 COMMENT = "秒杀成功明细表";


-- 连接控制台
mysql -uroot -p