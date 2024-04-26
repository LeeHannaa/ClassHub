package com.example.classhub.domain.member.repository;

import com.example.classhub.domain.member.ClassHub_Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<ClassHub_Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByUniqueId(String uniqueId);
    Optional<ClassHub_Member> findByUniqueId(String uniqueId);
}

