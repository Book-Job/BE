package com.bookjob.member.service;

import com.bookjob.common.exception.UnAuthorizedException;
import com.bookjob.member.domain.Member;
import com.bookjob.member.repository.MemberRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MemberReadServiceTest {

    @InjectMocks
    private MemberReadService memberReadService;

    @Mock
    private MemberRepository memberRepository;

    @Nested
    class getActiveMemberById {

        @Test
        void Member_PK_를_이용해_활성_유저를_조회한다() {
            // given
            Member activeUser = mock(Member.class);
            when(activeUser.getIsBlocked()).thenReturn(Boolean.FALSE);
            when(activeUser.getDeletedAt()).thenReturn(null);
            when(memberRepository.findByIdAndIsBlockedFalseAndDeletedAtIsNull(1L)).thenReturn(Optional.of(activeUser));

            // when
            Member foundMember = memberReadService.getActiveMemberById(1L);

            // then
            assertThat(foundMember).isEqualTo(activeUser);
            assertThat(foundMember.getDeletedAt()).isNull();
        }

        @Test
        void 비활성_유저_조회_시_에러를_반환한다() {
            // given
            when(memberRepository.findByIdAndIsBlockedFalseAndDeletedAtIsNull(eq(1L))).thenReturn(Optional.empty());

            // when
            assertThatThrownBy(
                    () -> memberReadService.getActiveMemberById(1L)
            ).isInstanceOf(UnAuthorizedException.class)
                    .hasMessage(UnAuthorizedException.deactivatedMemberUnauthorized().getMessage());
        }
    }
}
