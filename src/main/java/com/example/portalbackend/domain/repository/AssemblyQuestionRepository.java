package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.AssemblyQuestion;
import com.example.portalbackend.domain.entity.Convocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssemblyQuestionRepository extends JpaRepository<AssemblyQuestion, Long>{
    List<AssemblyQuestion> findAllByConvocationOrderById(Convocation convocation);
    List<AssemblyQuestion> findAllByConvocationAndEnabledIsTrueOrderById(Convocation convocation);
}



