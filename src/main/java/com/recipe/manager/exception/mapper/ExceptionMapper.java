package com.recipe.manager.exception.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.recipe.manager.exception.RecipeException.NotFoundException;
import com.recipe.manager.exception.model.ExceptionResponse;
import org.mapstruct.Mapper;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface ExceptionMapper {
  ExceptionResponse mapNotFoundException(NotFoundException exception, int errorCode);
}
