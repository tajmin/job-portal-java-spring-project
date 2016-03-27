package com.selvesperer.knoeien.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.selvesperer.knoeien.data.domain.SliderImage;
import com.selvesperer.knoeien.data.repository.custom.SliderImageRepositoryCustom;

@Repository
public interface SliderImageRepository extends JpaRepository<SliderImage, String>, SliderImageRepositoryCustom {

}
