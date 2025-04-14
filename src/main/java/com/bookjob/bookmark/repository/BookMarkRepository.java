package com.bookjob.bookmark.repository;

import com.bookjob.bookmark.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    List<BookMark> findByMemberId(Long id);
}
