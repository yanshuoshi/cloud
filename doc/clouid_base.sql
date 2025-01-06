/*
SQLyog Ultimate v12.14 (64 bit)
MySQL - 5.7.27-log : Database - cloud_base
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cloud_base` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `cloud_base`;

/*Table structure for table `oauth_access_token` */

DROP TABLE IF EXISTS `oauth_access_token`;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `oauth_access_token` */

/*Table structure for table `oauth_approvals` */

DROP TABLE IF EXISTS `oauth_approvals`;

CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastModifiedAt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `oauth_approvals` */

/*Table structure for table `oauth_client_details` */

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL COMMENT '客户端标识',
  `resource_ids` varchar(255) DEFAULT NULL COMMENT '接入资源列表',
  `client_secret` varchar(255) DEFAULT NULL COMMENT '客户端秘钥',
  `scope` varchar(255) DEFAULT NULL COMMENT '授权范围',
  `authorized_grant_types` varchar(255) DEFAULT NULL COMMENT '允许授权类型',
  `web_server_redirect_uri` varchar(255) DEFAULT NULL COMMENT '客户端的重定向URI',
  `authorities` varchar(255) DEFAULT NULL COMMENT '客户端所拥有的权限值',
  `access_token_validity` int(11) DEFAULT NULL COMMENT '设定客户端的access_token的有效时间值(单位:秒)',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '设定客户端的refresh_token的有效时间值(单位:秒',
  `additional_information` text COMMENT '这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,',
  `create_time` timestamp NULL DEFAULT NULL,
  `archived` tinyint(1) DEFAULT NULL COMMENT '用于标识客户端是否已存档(即实现逻辑删除),默认值为’0’(即未存档)',
  `trusted` tinyint(1) DEFAULT NULL COMMENT '设置客户端是否为受信任的,默认为’0’(即不受信任的,1为受信任的)',
  `autoapprove` varchar(255) DEFAULT NULL COMMENT '设置用户是否自动Approval操作, 默认值为 ‘false’, 可选值包括 ‘true’,‘false’, ‘read’,‘write’. ',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`create_time`,`archived`,`trusted`,`autoapprove`) values 

('client_id','res1,res2','$2a$10$m4fF8ZNZIE7YhpcyTpxs5.UnTL9o1PewCXH4ZARD0HmnHkkq0P/TC','ROLE_ADMIN,ROLE_USER,ROLE_API','client_credentials,password,authorization_code,implicit,refresh_token','http://192.168.1.14:8500',NULL,86400,31536000,NULL,'2020-07-12 21:55:48',0,0,'false'),

('client_id1','res1,res2','$2a$10$m4fF8ZNZIE7YhpcyTpxs5.UnTL9o1PewCXH4ZARD0HmnHkkq0P/TC','ROLE_ADMIN,ROLE_USER,ROLE_API','password,client_credentials,authorization_code,implicit,refresh_token','http://192.168.1.14:8500',NULL,86400,31536000,NULL,'2020-07-12 21:55:48',0,0,'false');

/*Table structure for table `oauth_client_token` */

DROP TABLE IF EXISTS `oauth_client_token`;

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `oauth_client_token` */

/*Table structure for table `oauth_code` */

DROP TABLE IF EXISTS `oauth_code`;

CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `oauth_code` */

/*Table structure for table `oauth_refresh_token` */

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `oauth_refresh_token` */

insert  into `oauth_refresh_token`(`token_id`,`token`,`authentication`) values 

('7b468a7a4547cee6a9ef40f41fc20119','��\0sr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/�Gc��ɷ\0L\0\nexpirationt\0Ljava/util/Date;xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens�\ncT�^\0L\0valuet\0Ljava/lang/String;xpt\0$e3ea7dab-e1a3-4c68-9492-7bf46f79812dsr\0java.util.Datehj�KYt\0\0xpw\0\0���z�x','��\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2Authentication�@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenӪ(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList�%1��\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0��^�\0L\0cq\0~\0xpsr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0studentsq\0~\0\rt\0teacherxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>�qi�\0L\0clientIdq\0~\0L\0requestParametersq\0~\0L\0scopeq\0~\0xpt\0	client_idsr\0%java.util.Collections$UnmodifiableMap��t�B\0L\0mq\0~\0xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rresponse_typet\0codet\0codet\0boPCaPt\0\ngrant_typet\0authorization_codet\0	client_idq\0~\0xsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xq\0~\0	sr\0java.util.LinkedHashSet�l�Z��*\0\0xr\0java.util.HashSet�D�����4\0\0xpw\0\0\0?@\0\0\0\0\0t\0ROLE_APIt\0	ROLE_USERt\0\nROLE_ADMINxsq\0~\0(w\0\0\0?@\0\0\0\0\0\0xsq\0~\0?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xt\0http://192.168.1.14:8500psq\0~\0(w\0\0\0?@\0\0\0\0\0t\0res1xsq\0~\0(w\0\0\0?@\0\0\0\0\0q\0~\0xsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0q\0~\0xq\0~\06sr\0Horg.springframework.security.web.authentication.WebAuthenticationDetails\0\0\0\0\0\0\0L\0\rremoteAddressq\0~\0L\0	sessionIdq\0~\0xpt\0192.168.1.14t\0 F62B4258CD1684008BA1E86057997A82psr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0L\0passwordq\0~\0L\0usernameq\0~\0xpsq\0~\0%sr\0java.util.TreeSetݘP���[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0\0\0xpw\0\0\0q\0~\0q\0~\0xpt\0}{\"fullname\":\"卡卡西\",\"id\":3,\"password\":\"$2a$10$iZUdtZaTO/RV1ingOv5KJOV0UDYBYFBc6nAMsJFEWOFdKQgcZ.sc2\",\"username\":\"kakaxi\"}');

/*Table structure for table `sys_dict_data` */

DROP TABLE IF EXISTS `sys_dict_data`;

CREATE TABLE `sys_dict_data` (
  `dict_data_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态（1正常 0停用）',
  `create_user_id` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_data_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

/*Data for the table `sys_dict_data` */

insert  into `sys_dict_data`(`dict_data_id`,`dict_sort`,`dict_label`,`dict_value`,`dict_type`,`status`,`create_user_id`,`create_time`,`remark`) values 

(1,0,'临床医学','CM','student_major',1,'euryd123','2021-12-03 15:20:56','临床医学专业'),

(2,1,'医学影像','Medical_Imaging','student_major',0,'euryd123','2021-12-03 16:59:18','医学影像专业'),

(3,2,'护理医学','nursing_science','student_major',1,'euryd123','2021-12-03 17:01:30','护理医学专业'),

(4,0,'全天占用','0','occupy_status',1,'euryd123','2021-12-06 10:18:12','全天占用'),

(5,1,'时段占用','1','occupy_status',1,'euryd123','2021-12-06 13:57:19','时段占用');

/*Table structure for table `sys_dict_type` */

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '字典类型',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态（1正常 0停用）',
  `create_user_id` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

/*Data for the table `sys_dict_type` */

insert  into `sys_dict_type`(`dict_id`,`dict_name`,`dict_type`,`status`,`create_user_id`,`create_time`,`remark`) values 

(1,'所学专业','student_major',1,'euryd123','2021-12-03 15:19:37','学生所学专业'),

(2,'占用类型','occupy_status',1,'euryd123','2021-12-10 14:36:27','占用类型'),

(7,'dictName_fkrnd','dictType_lnie7',1,'01192121143339928754','2024-09-04 16:38:53','remark_wgjrn'),

(8,'啊的方法','a',1,'01192121143339928754','2024-09-04 16:39:09','remark_wgjrn'),

(9,'啊的23方法','cf',1,'01192121143339928754','2024-09-04 16:39:15','remark_wgjrn');

/*Table structure for table `sys_login_log` */

DROP TABLE IF EXISTS `sys_login_log`;

CREATE TABLE `sys_login_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `real_name` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `ip_address` varchar(50) DEFAULT NULL COMMENT '登录IP地址',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注描述',
  `address` varchar(100) DEFAULT NULL COMMENT '登录地址',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户id',
  `type` int(2) DEFAULT NULL COMMENT '身份 1 老师 2 学生',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='登录日志表';

/*Data for the table `sys_login_log` */

insert  into `sys_login_log`(`log_id`,`real_name`,`ip_address`,`login_time`,`remark`,`address`,`user_id`,`type`) values 

(58,'cs','','2024-08-20 10:03:17','登录成功','192.168.1.14','01192121143339928754',1),

(59,'cs','','2024-08-20 10:04:43','登录成功','192.168.1.14','01192121143339928754',1),

(60,'cs','','2024-08-20 10:05:26','登录成功','192.168.1.14','01192121143339928754',1),

(61,'cs','','2024-08-20 10:09:07','登录成功','192.168.1.14','01192121143339928754',1),

(62,'cs','','2024-08-20 10:10:25','登录成功','192.168.1.14','01192121143339928754',1),

(63,'cs','','2024-08-21 14:21:18','登录成功','192.168.1.14','01192121143339928754',1),

(64,'cs','','2024-08-21 15:47:36','登录成功','192.168.1.14','01192121143339928754',1),

(65,'cs','','2024-08-21 16:25:20','登录成功','192.168.1.14','01192121143339928754',1),

(66,'cs','','2024-08-22 17:27:46','登录成功','192.168.1.14','01192121143339928754',1),

(67,'cs','','2024-08-23 15:51:10','登录成功','0:0:0:0:0:0:0:1','01192121143339928754',1),

(68,'cs','','2024-08-27 13:38:54','登录成功','192.168.1.14','01192121143339928754',1),

(69,'cs','','2024-08-27 13:40:29','登录成功','192.168.1.14','01192121143339928754',1),

(70,'cs','','2024-08-27 13:41:20','登录成功','0:0:0:0:0:0:0:1','01192121143339928754',1),

(71,'cs','','2024-08-27 13:57:08','登录成功','192.168.1.14','01192121143339928754',1),

(72,'cs','','2024-08-27 14:31:36','登录成功','192.168.1.14','01192121143339928754',1),

(73,'cs','','2024-08-27 14:34:13','登录成功','192.168.1.14','01192121143339928754',1),

(74,'cs','','2024-08-27 16:31:17','登录成功','192.168.1.14','01192121143339928754',1),

(75,'cs','','2024-08-28 16:43:18','登录成功','192.168.1.14','01192121143339928754',1),

(76,'cs','','2024-09-02 14:51:33','登录成功','192.168.1.14','01192121143339928754',1),

(77,'cs','','2024-09-02 15:08:51','登录成功','192.168.1.14','01192121143339928754',1),

(78,'cs','','2024-09-03 17:41:39','登录成功','0:0:0:0:0:0:0:1','01192121143339928754',1),

(79,'cs','','2024-09-04 09:41:24','登录成功','0:0:0:0:0:0:0:1','01192121143339928754',1);

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级id',
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(200) DEFAULT NULL COMMENT '路由地址',
  `menu_type` char(1) DEFAULT NULL COMMENT '菜单类型:M-目录;C-菜单;F-按钮）',
  `permission_key` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `sort` int(11) DEFAULT NULL COMMENT '序号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户id',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态:1-正常;0-删除',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`parent_id`,`menu_name`,`path`,`menu_type`,`permission_key`,`sort`,`create_time`,`create_user_id`,`status`) values 

(12,0,'系统用户管理','#','M','sys:sys:sys',0,'2021-11-03 15:10:08','euryd123',1),

(30,0,'组织管理','/organization','M','org:org:org',1,'2021-11-11 13:48:46','euryd123',1),

(31,0,'角色管理','/role','M','role:role:role',1,'2021-11-11 13:49:17','euryd123',1),

(32,0,'菜单管理','/menu','M','menu:menu:menu',2,'2021-11-11 13:49:45','euryd123',1),

(53,0,'日志管理','/log','M','log:log:log',NULL,'2022-05-07 14:13:27','euryd123',1),

(54,0,'字典管理','/dictionaries','M','Dict:Dict:Dict',1,'2022-05-07 14:23:07','euryd123',1),

(77,0,'参数管理','/arguments','M','arg:arg:arg',NULL,'2022-07-26 10:52:40','euryd123',1),

(78,77,'科室管理','/department','C','department',NULL,'2022-07-26 10:53:13','euryd123',1),

(79,77,'疾病库管理','/disease','C','disease',NULL,'2022-07-26 14:55:45','euryd123',1),

(80,77,'检查部位管理','/checkoutPosition','C','checkoutPosition',NULL,'2022-07-26 15:35:07','euryd123',1),

(81,77,'体格检查管理','/checkoutBody','C','checkoutBody',NULL,'2022-07-26 17:40:09','euryd123',1),

(82,77,'辅助检查管理','/checkoutAuxiliary','C','checkoutAuxiliary',NULL,'2022-07-27 10:33:08','euryd123',1),

(83,77,'辅助检查模板','/tCheckoutAuxiliaryTemplate','C','tCheckoutAuxiliaryTemplate',1,'2022-07-27 17:01:28','euryd123',1),

(84,77,'特殊检查管理','/checkoutSpecial','C','checkoutSpecial',NULL,'2022-07-28 11:30:09','euryd123',1),

(85,77,'药品管理','/drugs','C','drugs',NULL,'2022-07-28 11:50:37','euryd123',1),

(86,77,'问诊问题','/enquireQuestion','C','enquireQuestion',NULL,'2022-07-28 14:55:29','euryd123',1),

(87,77,'问诊类型','/enquireType','C','enquireType',NULL,'2022-07-28 15:26:13','euryd123',1),

(88,0,'病例管理','/case','M','case',NULL,'2022-07-28 16:51:20','euryd123',1),

(89,0,'健康人模型管理','/healthModel','M','healthModel',NULL,'2022-08-10 11:05:10','euryd123',1),

(90,0,'考核管理','/exam','M','exam',NULL,'2022-08-10 17:06:22','euryd123',1),

(91,12,'学生管理','/student','C','student',NULL,'2022-08-12 17:08:12','euryd123',1),

(92,77,'治疗方案管理','/plan','C','plan',1,'2022-08-22 10:50:18','euryd123',1),

(93,0,'成绩管理','/grade','M','grade',NULL,'2022-09-21 19:28:57','euryd123',1),

(94,0,'统计分析','/statistics','M','statistics',NULL,'2022-09-23 13:45:31','euryd123',1),

(95,87,'是否显示编辑按钮','-','F','enquireQuestion_isEdit',NULL,'2022-09-28 18:15:38','euryd123',1),

(96,0,'病例训练记录','/tStatisPractise','M','tStatisPractise',NULL,'2022-10-09 13:36:48','euryd123',1),

(99,77,'特殊检查部位管理','/tCheckoutSpecialPosition','C','tCheckoutSpecialPosition',NULL,'2023-03-08 13:18:26','euryd123',1);

/*Table structure for table `sys_organization` */

DROP TABLE IF EXISTS `sys_organization`;

CREATE TABLE `sys_organization` (
  `organization_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织机构id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级id',
  `organization_name` varchar(100) DEFAULT NULL COMMENT '组织机构名称',
  `sort` int(11) DEFAULT NULL COMMENT '序号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户id',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态:1-正常;0-删除',
  PRIMARY KEY (`organization_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='组织机构表(班级表)';

/*Data for the table `sys_organization` */

insert  into `sys_organization`(`organization_id`,`parent_id`,`organization_name`,`sort`,`create_time`,`create_user_id`,`status`) values 

(1,0,'组织机构',1,'2022-08-11 11:13:15','euryd123',1),

(4,1,'临床二班',NULL,'2022-08-11 11:18:15','euryd123',1),

(5,1,'临床三班',NULL,'2022-08-11 11:18:33','euryd123',1),

(6,1,'临床四班',NULL,'2022-08-11 11:18:50','euryd123',1),

(15,1,'临床五班',NULL,'2022-12-01 15:35:47','1578667503757676546',1);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_key` varchar(20) DEFAULT NULL COMMENT '角色标识',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户id',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态:1-正常;0-删除',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role_key`,`role_name`,`create_time`,`create_user_id`,`status`) values 

(110,'admin','系统管理员','2021-11-03 15:15:56','euryd123',1),

(111,'common','普通用户','2021-11-26 14:52:46','euryd123',1),

(122,'teacher','教师','2022-12-01 15:39:22','1578667503757676546',1);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色菜单关联表';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values 

(111,77),

(111,78),

(111,79),

(111,80),

(111,81),

(111,82),

(111,83),

(111,84),

(111,85),

(111,86),

(111,87),

(111,92),

(111,88),

(111,89),

(111,90),

(122,91),

(122,53),

(122,85),

(122,86),

(122,87),

(122,95),

(122,92),

(122,88),

(122,89),

(122,90),

(122,93),

(122,94),

(122,96),

(110,12),

(110,91),

(110,30),

(110,31),

(110,32),

(110,53),

(110,54),

(110,77),

(110,78),

(110,79),

(110,80),

(110,81),

(110,82),

(110,83),

(110,84),

(110,85),

(110,86),

(110,87),

(110,95),

(110,92),

(110,99),

(110,88),

(110,89),

(110,90),

(110,93),

(110,94),

(110,96);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` varchar(50) NOT NULL COMMENT '主键id',
  `real_name` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `username` varchar(50) DEFAULT NULL COMMENT '账号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `sex` char(1) DEFAULT NULL COMMENT '性别:F-女;M-男',
  `mobile` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `headurl` varchar(100) DEFAULT NULL COMMENT '头像:相对地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建用户id',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态:1-正常;0-删除',
  `organization_id` int(11) DEFAULT NULL COMMENT '组织id',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表(老师表)';

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`real_name`,`username`,`password`,`sex`,`mobile`,`headurl`,`create_time`,`create_user`,`status`,`organization_id`,`update_time`) values 

('01192121143339928754','cs','cs','$2a$10$xeE7QWkMsAan3oAzryqDce49s4LZHxRSGFTe6oi7/ZCwsmDK.9sWC','M',NULL,'https://static-legacy.dingtalk.com/media/lADPDg7mSUnikJDNBDrNBDg_1080_1082.jpg','2022-05-09 14:32:04',NULL,1,5,'2023-12-11 13:31:22'),

('1565238709134843906','wy','wy','$2a$10$Mqqi/ZKSGut1pXxVygoLOeJv.x/HWbbV/RQoE6lhb.lDgyg8WLcSK','F','15588923869',NULL,'2022-09-01 15:22:44','euryd123',1,5,'2023-12-11 13:30:49'),

('euryd123','管理员','admin','$2a$10$WehJKEnFYjFqdPNW9V0NDuBOxYrjo1Kx3E9Sz8lnkooKXM.HF4et6','M','15065988781','5ef1bf56022de7972974546797b622e4.jpg','2021-11-02 10:02:35','euryd123',1,5945511,NULL);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values 

('euryd123',110),

('1732923602718994434',110),

('0265430023679505',111),

('010030064454677875',111),

('03040668381334',110),

('02071766556523',110),

('1571762423519358977',110),

('01251946282537859937',110),

('67425955785267',110),

('055130102639894360',110),

('1565238709134843906',110),

('1724300104605159426',111),

('1724300190781329409',110),

('1724300437129580546',111),

('1724300532902318082',111),

('1724300630143062018',111),

('1729333843492130817',110),

('01192121143339928754',110),

('1767008530606903297',110);

/*Table structure for table `t_student` */

DROP TABLE IF EXISTS `t_student`;

CREATE TABLE `t_student` (
  `student_id` varchar(50) NOT NULL COMMENT '学生id',
  `student_number` varchar(50) DEFAULT NULL COMMENT '学号',
  `student_password` varchar(100) DEFAULT NULL COMMENT '密码',
  `student_name` varchar(50) DEFAULT NULL COMMENT '学生姓名',
  `student_sex` char(1) DEFAULT NULL COMMENT '性别:F-女;M-男',
  `student_mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `headurl` varchar(100) DEFAULT NULL COMMENT '头像:相对地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建用户id',
  `organization_id` int(11) DEFAULT NULL COMMENT '组织机构id',
  `status` int(2) DEFAULT NULL COMMENT '状态 1：正常 0：删除',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`student_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='学生表';

/*Data for the table `t_student` */

insert  into `t_student`(`student_id`,`student_number`,`student_password`,`student_name`,`student_sex`,`student_mobile`,`headurl`,`create_time`,`create_user`,`organization_id`,`status`,`update_time`) values 

('1549601531296759810','cs01','$2a$10$gQP6tr/pFIeQpvm39hsTrO.I2a.nP0Ffc20WjW7sfA22/rdeaPVSu','yss','M',NULL,'20221019142950177.png','2022-07-20 11:46:10','euryd123',5,1,'2023-12-11 13:33:58'),

('1825717282088144898','studentNumber_t30xb','$2a$10$qMg6ZF0Z7pUexVYEQL9qKeNfTD4sywwXCfzVp.Fi8qBW5c2ZTB4n2','studentName_nxlcg','F','studentMobile_k2yda','headurl_5e4fq','2024-08-20 10:11:35','01192121143339928754',1,1,'2024-08-20 10:11:35');

/*Table structure for table `undo_log` */

DROP TABLE IF EXISTS `undo_log`;

CREATE TABLE `undo_log` (
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(128) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AT transaction mode undo table';

/*Data for the table `undo_log` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
