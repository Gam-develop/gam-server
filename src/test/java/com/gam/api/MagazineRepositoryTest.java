package com.gam.api;

import com.gam.api.domain.magazine.entity.Magazine;
import com.gam.api.domain.magazine.repository.MagazineRepository;
import com.gam.api.domain.user.entity.MagazineScrap;
import com.gam.api.domain.user.entity.Role;
import com.gam.api.domain.user.entity.User;
import com.gam.api.domain.user.entity.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("dev")
public class MagazineRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MagazineRepository magazineRepository;

    @Test
    public void testFindTopMagazinesWithScrapStatus() {
        long startTime = System.nanoTime();

        // 페이징 처리
        Pageable pageable = PageRequest.of(0, 3);

        // 테스트 실행
        List<Magazine> results = magazineRepository.findTop3ByOrderByViewCountDesc();

        long endTime = System.nanoTime();  // 쿼리 완료 시간
        System.out.println("Query execution time: " + (endTime - startTime) / 1_000_000_000.0 + " seconds");
        // 검증
        assertEquals(3, results.size());
    }
}
