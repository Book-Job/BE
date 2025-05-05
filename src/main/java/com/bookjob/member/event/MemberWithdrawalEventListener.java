package com.bookjob.member.event;

import com.bookjob.member.annotation.MemberDataCleanup;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MemberWithdrawalEventListener implements ApplicationListener<MemberWithdrawalEvent> {

    private final Map<Integer, Map<Method, Object>> orderedCleanupMethods = new TreeMap<>();
    private final ApplicationContext applicationContext;

    @Autowired
    public MemberWithdrawalEventListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        scanCleanupMethods();
    }

    private void scanCleanupMethods() {
        Map<String, Object> beans = applicationContext.getBeansOfType(Object.class);

        for (Object bean : beans.values()) {
            Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);

            for (Method method : targetClass.getMethods()) {
                MemberDataCleanup annotation = AnnotationUtils.findAnnotation(method, MemberDataCleanup.class);

                if (annotation != null) {
                    if (method.getParameterCount() != 1 || !method.getParameterTypes()[0].equals(Long.class)) {
                        log.warn("회원 pk를 파라메터로 가져야 합니다. : {}.{}",
                                targetClass.getName(), method.getName());
                        continue;
                    }

                    int order = annotation.order();
                    orderedCleanupMethods.computeIfAbsent(order, k -> new ConcurrentHashMap<>())
                            .put(method, bean);

                    log.info("데이터 정리 메서드 등록: {}.{}, 순서={}",
                            targetClass.getSimpleName(), method.getName(), order);
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(MemberWithdrawalEvent event) {
        Long memberId = event.getMemberId();
        log.info("회원 탈퇴 이벤트 수신: memberId={}", memberId);

        cleanupMemberData(memberId);
    }

    private void cleanupMemberData(Long memberId) {
        // 순서대로 정리 메서드 실행
        for (Map.Entry<Integer, Map<Method, Object>> entry : orderedCleanupMethods.entrySet()) {
            int order = entry.getKey();
            Map<Method, Object> methods = entry.getValue();

            log.debug("데이터 정리 단계 시작: 순서={}", order);

            methods.forEach((method, bean) -> {
                Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);

                try {
                    log.debug("데이터 정리 메서드 호출: {}.{}, 순서={}",
                            targetClass.getSimpleName(), method.getName(), order);

                    method.invoke(bean, memberId);

                } catch (Exception e) {
                    log.error("회원 탈퇴 데이터 정리 중 오류 발생: {}.{}, memberId={}, 순서={}",
                            targetClass.getSimpleName(), method.getName(), memberId, order, e);
                }
            });
        }

        log.info("회원 데이터 정리 완료: memberId={}", memberId);
    }
}