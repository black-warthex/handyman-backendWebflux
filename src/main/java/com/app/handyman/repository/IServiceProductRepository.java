package com.app.handyman.repository;



import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.app.handyman.entity.ServiceReportEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public interface IServiceProductRepository extends ReactiveMongoRepository<ServiceReportEntity,Long> {
	
	public Mono<ServiceReportEntity> findByTechnicalIdAndServiceId(String technicalId,String serviceId);
	
	public Flux<ServiceReportEntity>findByTechnicalId(String technicalId);
	
	public Flux<ServiceReportEntity> findByTechnicalIdAndWeekNumber(String technicalId,String weekNumber);
}
