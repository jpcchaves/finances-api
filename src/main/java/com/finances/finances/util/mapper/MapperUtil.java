package com.finances.finances.util.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperUtil {

  private final ModelMapper mapper;

  public MapperUtil(ModelMapper mapper) {
    this.mapper = mapper;
  }

  public <O, D> D mapObject(O origin, Class<D> destination) {
    return mapper.map(origin, destination);
  }

  public <O, D> List<D> mapObjects(Collection<O> origin, Class<D> destination) {
    return origin.stream().map(o -> mapper.map(o, destination)).collect(Collectors.toList());
  }
}
