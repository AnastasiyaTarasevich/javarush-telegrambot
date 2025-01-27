-- V00004__change_chat_id_type_to_long.sql

-- Удаляем внешний ключ перед изменением типа столбца
ALTER TABLE group_x_user DROP FOREIGN KEY group_x_user_ibfk_1;

-- Меняем тип данных столбца chat_id с VARCHAR на BIGINT
ALTER TABLE tg_user MODIFY chat_id INT;

-- Меняем тип данных столбца user_id на BIGINT (если это необходимо)
ALTER TABLE group_x_user MODIFY user_id INT;

-- Восстанавливаем внешний ключ
ALTER TABLE group_x_user
    ADD CONSTRAINT group_x_user_ibfk_1 FOREIGN KEY (user_id) REFERENCES tg_user(chat_id);
