CREATE TABLE `meeting` (
  `id` int  NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '会议名称/主题',
  `user_id` int NOT NULL COMMENT '用户id',
  `room_id` int NOT NULL COMMENT '会议室id',
  `department_id` int NOT NULL COMMENT '参会部门id',
  `begin_time` timestamp NOT NULL COMMENT '会议开始时间',
  `end_time` timestamp NOT NULL COMMENT '会议结束时间',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '0=未开始；1=进行中；2=已结束',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='会议室预定表';


CREATE TABLE `user` (
  `id` int  NOT NULL AUTO_INCREMENT,
  `open_id` varchar(255) NOT NULL COMMENT '小程序用户唯一标识',
  `department_id` int NOT NULL COMMENT '用户所属部门，暂时没用',
  `user_name` varchar(255) NOT NULL DEFAULT '0' COMMENT '用户真实名字',
  `nick_name` varchar(255) NOT NULL DEFAULT '0' COMMENT '用户昵称名字',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id_UNIQUE` (`open_id`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';


CREATE TABLE `room` (
  `id` int  NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '房间名字',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '房间描述，以|竖线分隔，例如30|白板|投影仪',
  `workplace` tinyint NOT NULL DEFAULT '1' COMMENT '1 蓝海 2 嘉华',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='会议室房间表';


CREATE TABLE `department` (
  `id` int  NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '部门名字',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '部门描述',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='公司部门表';

INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('SaaS-研发','SaaS-研发',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('SaaS-研究院','SaaS-研究院',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('SaaS-产品运营','SaaS-产品运营',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('SaaS-UED','SaaS-UED',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('大客户部','大客户部',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('WaaS','WaaS',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('TED-大前端','TED-大前端',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('TED-测试部','TED-测试部',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('运维部','运维部',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('客户服务部','客户服务部',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('大中华销售','大中华销售',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('市场部','市场部',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('金色算力云','金色算力云',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('矿池','矿池',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('人力行政部','人力行政部',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('财务部','财务部',now(),now());
INSERT INTO `meeting`.`department`(`name`,description,ctime,mtime)VALUES('总裁办','总裁办',now(),now());

INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('Japan 会议室','6人   白板      投影仪',1,NOW(),NOW());
INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('Singapore 会议室','6人   白板      投影仪     沙发',1,NOW(),NOW());
INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('Korea 会议室','6人   白板      投影仪',1,NOW(),NOW());
INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('Australia 会议室','6人   白板      投影仪     沙发',1,NOW(),NOW());
INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('HongKong 会议室','6人   白板      投影仪',1,NOW(),NOW());
INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('大会议室','30人   白板      投影仪     沙发',1,NOW(),NOW());
INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('BTC会议室','15人   投影仪   椅子   玻璃墙',2,NOW(),NOW());
INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('ETH会议室','6人   电视   椅子   玻璃墙',2,NOW(),NOW());
INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('EOS会议室','4人   无投影仪   玻璃墙',2,NOW(),NOW());
INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('LTC会议室','4人   无投影仪   无玻璃墙',2,NOW(),NOW());
INSERT INTO `meeting`.`room`(`name`,description,workplace,ctime,mtime)VALUES('USDT会议室','6人   电视   无玻璃墙   茶具',2,NOW(),NOW());
