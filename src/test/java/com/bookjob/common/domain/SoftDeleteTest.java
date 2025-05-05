package com.bookjob.common.domain;

import com.bookjob.common.config.TestJooqConfig;
import com.bookjob.common.config.TestPasswordEncoderConfig;
import com.bookjob.member.domain.Member;
import com.bookjob.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import({TestPasswordEncoderConfig.class, TestJooqConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SoftDeleteTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void softDelete() {
        // given
        Member member = Member.builder()
                .email("email@email.com")
                .loginId("loginId")
                .password(Password.of("asdf", passwordEncoder))
                .nickname("nickname")
                .build();

        member = memberRepository.save(member);

        // when
        memberRepository.delete(member);

        Member deletedEntity = entityManager
                .getEntityManager()
                .createQuery("SELECT e FROM Member e WHERE e.id = :id", Member.class)
                .setParameter("id", member.getId())
                .getSingleResult();

        // then
        assertNotNull(deletedEntity.getDeletedAt());
    }
}
