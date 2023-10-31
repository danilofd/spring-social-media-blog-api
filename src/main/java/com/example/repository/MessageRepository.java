package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;

@Transactional
public interface MessageRepository extends JpaRepository<Message, Integer>{

    @Modifying
    @Query("DELETE FROM Message m WHERE m.message_id = ?1")
    int deleteByIdCustom(Integer message_id);

    @Modifying
    @Query("UPDATE Message m SET m.message_text = ?2 WHERE m.message_id = ?1")
    int updateMessage(Integer message_id, String message_text);

    @Modifying
    @Query("SELECT m FROM Message m WHERE m.posted_by = ?1")
    List<Message> findByPosted_by(Integer account_id);
}
