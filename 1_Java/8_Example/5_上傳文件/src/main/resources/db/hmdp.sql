DROP TABLE IF EXISTS `spring_test`;
create table user_info (
 id INT AUTO_INCREMENT PRIMARY KEY,
 user_id INT(8) NOT NULL COMMENT '使用者帳號',
 user_info VARCHAR(1000) NOT NULL COMMENT '使用者資訊',
 result MEDIUMTEXT NULL DEFAULT NULL COMMENT '結果/錯誤訊息',
 create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
 update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間'
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
INSERT INTO user_info (user_id, user_info, result) VALUES (333, 'John Doe', 'Test successful');