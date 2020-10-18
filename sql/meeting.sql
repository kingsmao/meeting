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
