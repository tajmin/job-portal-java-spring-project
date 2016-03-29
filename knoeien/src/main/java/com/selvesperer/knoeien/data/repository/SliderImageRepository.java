package com.selvesperer.knoeien.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.selvesperer.knoeien.data.domain.SliderImage;
import com.selvesperer.knoeien.data.repository.custom.SliderImageRepositoryCustom;

@Repository
public interface SliderImageRepository extends JpaRepository<SliderImage, String>, SliderImageRepositoryCustom {

	@Query("from SliderImage s")
	List <SliderImage> findSliderImageList();
	
	@Query("from SliderImage s where s.id = :sliderId ")
	SliderImage findSliderImageById(@Param("sliderId") String sliderId);
}
