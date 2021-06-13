package com.study.microservices.studyapplication.api.controller.swagger;

import com.study.microservices.studyapplication.domain.dto.CityDto;
import com.study.microservices.studyapplication.exceptionhandler.ErrorDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Api(tags = "City")
public interface CityControllerSwagger {

    @ApiOperation("List all the cities")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true,
                      paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public ResponseEntity<List<CityDto>> list();

    @ApiOperation("Get details of a city by Id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid id from city", response = ErrorDto.class),
            @ApiResponse(code = 422, message = "City not found", response = ErrorDto.class)
    })
    public ResponseEntity<CityDto> search(@ApiParam(value = "The city id to search", example = "1", required = true) Long cityId);

    @ApiOperation("Get details of a city by Id with just the fields informed on a request")
    public MappingJacksonValue searchFilter(Long cityId, String fields);

    @ApiOperation("Create a new city")
    public void include(@ApiParam(name = "city", value = "A representation of a new city to include.") CityDto cityDto);

    @ApiOperation("Update city that exist by Id")
    public void update(@ApiParam(name = "city", value = "A representation of a city with the new values to update") CityDto cityDto,
                       @ApiParam(value = "The city id to update", example = "1", required = true) Long cityId);

    @ApiOperation("Delete city by Id")
    public void delete(@ApiParam(value = "The city id to delete", example = "1", required = true) Long cityId);
}
