package org.sample.webmetric.impression;

import org.mapstruct.Mapper;
import org.sample.webmetric.EntityMapper;

@Mapper(componentModel = "spring")
public interface ImpressionMapper extends EntityMapper<ImpressionDto, Impression> {

}
