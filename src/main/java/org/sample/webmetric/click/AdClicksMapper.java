package org.sample.webmetric.click;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sample.webmetric.EntityMapper;

@Mapper(componentModel = "spring")
public interface AdClicksMapper extends EntityMapper<AdClickDto, AdClick> {

    @Mapping(source = "impressionId", target = "impression.id")
    AdClick toEntity(AdClickDto dto);

    AdClickDto toViewModel(AdClick entity);
}
