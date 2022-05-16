package uz.pdp.hrmanagementsystem.mappers;

import org.mapstruct.Mapping;

import java.util.List;

public interface GenericMapper<E, M> {
    E toEntity(M model);

    M toModel(E entity);

    List<M> toModelList(List<E> entities);

    List<E> toEntityList(List<M> models);
}
